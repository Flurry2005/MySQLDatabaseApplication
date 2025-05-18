package se.liam.sqldbapplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class DBConnection {
    private final String url, user, password;
    private Connection con;

    public DBConnection(String address, int port, String dbName,String user, String password){

        url = String.format("%s%s:%d/%s","jdbc:mysql://", address, port,  dbName);
        this.user = user;
        this.password = password;

        this.con = EstablishConnection();

    }

    private Connection EstablishConnection (){

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            //Check if connection has been established
            if(con != null){
                System.out.println("Connection established to SQL Database.");
            }
            return con;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderDetail> listOrdersWithDetailsForEmployee(int employeeId){
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<Long> orderIds = new ArrayList<>();
        String sqlQuery = "SELECT " +
                "oh.id AS order_id, " +
                "oh.order_date, " +
                "c.id AS customer_id," +
                "c.first_name AS customer_first_name, " +
                "c.last_name AS customer_last_name, " +
                "ol.id AS order_line_id, "+
                "ol.quantity, "+
                "f.id AS furniture_id, "+
                "f.name AS furniture_name "+
                "FROM " +
                "order_head oh " +
                "JOIN " +
                "customer c ON oh.customer_id = c.id " +
                "JOIN "+
                "order_line ol ON oh.id = ol.order_id "+
                "JOIN "+
                "furniture f ON ol.furniture_id = f.id "+
                "WHERE " +
                "oh.employee_id = "+employeeId;

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)){

            //While more rows
            while(resultSet.next()){

                if(!orderIds.contains(resultSet.getLong("order_id"))){
                    orderIds.add(resultSet.getLong("order_id"));

                    List<OrderLineDetail> lines = new ArrayList<>();
                    lines.add(new OrderLineDetail(
                                    resultSet.getLong("order_line_id"),resultSet.getInt("quantity"),
                                    resultSet.getLong("furniture_id"), resultSet.getString("furniture_name")));
                    OrderDetail orderDetail = new OrderDetail(resultSet.getLong("order_id"),
                            resultSet.getString("order_date"),
                            resultSet.getLong("customer_id"),
                            employeeId,
                            resultSet.getString("customer_first_name"),
                            lines
                    );
                    orderDetails.add(orderDetail);
                }else{
                    for(OrderDetail o : orderDetails){
                        if(o.id() == resultSet.getLong("order_id")){
                            o.orderLines().add(new OrderLineDetail(
                                    resultSet.getLong("order_line_id"),resultSet.getInt("quantity"),
                                    resultSet.getLong("furniture_id"), resultSet.getString("furniture_name")));
                        }
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderDetails;


    }

    public List<OrderDetail>
    listOrdersWithCustomerNameForEmployee(int employeeId){
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sqlQuery = "SELECT " +
                "o.id AS order_id, " +
                "o.order_date, " +
                "c.id AS customer_id," +
                "c.first_name AS customer_first_name, " +
                "c.last_name AS customer_last_name " +
                "FROM " +
                "order_head o " +
                "JOIN " +
                "customer c ON o.customer_id = c.id " +
                "WHERE " +
                "o.employee_id = "+employeeId;

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)){

            //While more rows
            while(resultSet.next()){
                OrderDetail orderDetail = new OrderDetail(resultSet.getLong("order_id"),
                        resultSet.getString("order_date"),
                        resultSet.getLong("customer_id"),
                        employeeId,
                        resultSet.getString("customer_first_name")
                );
                orderDetails.add(orderDetail);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderDetails;

    }

    public void createOrder(Order_head order, List<Order_line> orderLines){
        String sqlQuery = "INSERT INTO order_head(id, order_date, customer_id, employee_id)"+
                "VALUES("+
                String.format("'%d', '%s', '%d', '%d'",order.id(), order.orderDate(), order.customerId(), order.employeeId())+
                ")";
        ResultSet orderHeadResultSet = null;
        try (PreparedStatement statement = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)){
            statement.executeUpdate();
            orderHeadResultSet = statement.getGeneratedKeys();

            long orderId = -1;
            if(orderHeadResultSet.next()){
                orderId = orderHeadResultSet.getLong(1);
            }
            assert orderId!=-1;
            for(Order_line o : orderLines){
                try (Statement statement1 = con.createStatement()){
                    String sqlQuery_orderLines = "INSERT INTO order_line(id, furniture_id, order_id, quantity)"+
                            "VALUES("+
                            String.format("'%d', '%d', '%d', '%d'",o.id(), o.furnitureId(), orderId, o.quantity())+
                            ")";
                    statement1.executeUpdate(sqlQuery_orderLines);

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public List<Order_head> listOrdersForEmployee(int employeeId){
        List<Order_head> orders = new ArrayList<>();

        String sqlQuery = "SELECT * FROM order_head WHERE employee_id = "+employeeId;

        try(Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery)){
            if(!resultSet.isBeforeFirst()){
                System.out.println("No orders found for employee with id: "+employeeId);
            }

            while(resultSet.next()){
                Order_head orderHead = new Order_head(
                        resultSet.getLong("id"),
                        resultSet.getString("order_date"),
                        resultSet.getLong("customer_id"),
                        resultSet.getLong("employee_id")
                );

                orders.add(orderHead);
            }
            return orders;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public void createCustomer(Customer customer){
        String sqlQuery = "INSERT INTO customer(id, address, birth_date, city, first_name, last_name, postal_code)" +
                "VALUES("+
                String.format("'%d', '%s', '%s', '%s', '%s', '%s', '%s'",customer.id(), customer.address(), customer.birthDate(), customer.city(), customer.firstName(), customer.lastName(), customer.postalCode())+
                ")";
        try (Statement statement = con.createStatement()){
            statement.executeUpdate(sqlQuery);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Customer> listAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        String sqlQuery = "SELECT * FROM customer";

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)){

            //While more rows
            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getLong("id"),
                        resultSet.getString("address"),
                        resultSet.getString("birth_date"),
                        resultSet.getString("city"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("postal_code")
                );
                customers.add(customer);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

}
