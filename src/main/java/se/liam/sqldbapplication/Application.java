package se.liam.sqldbapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    public Application(DBConnection db){

        initializeApplication(db);
    }
    public void initializeApplication(DBConnection db){

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println(
                    "1. List all customers\n"+
                            "2. Create Customer\n"+
                            "3. List orders for employee\n"+
                            "4. Create order\n"+
                            "5. List orders with customer name for specified employee\n"+
                            "6. List order with details for employee\n"+
                            "7. Update customer address\n" );
            System.out.print("Option: ");
            String userInput = scanner.nextLine();
            System.out.println();
            switch(userInput){
                case "1" : {

                    ArrayList<Customer> customers = (ArrayList<Customer>) db.listAllCustomers();
                    for(Customer c : customers){
                        System.out.println(c.firstName());
                    }
                    System.out.println();
                    break;
                }
                case "2" : {

                    System.out.println();
                    System.out.print("Enter customer address: ");
                    String address = scanner.nextLine();
                    System.out.println();
                    System.out.print("Enter customer birth date: ");
                    String birth_date = scanner.nextLine();
                    System.out.println();
                    System.out.print("Enter customer city: ");
                    String city = scanner.nextLine();
                    System.out.println();
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    System.out.println();
                    System.out.print("Enter customer lastname: ");
                    String last_name = scanner.nextLine();
                    System.out.println();
                    System.out.print("Enter customer postal code: ");
                    String postal_code = scanner.nextLine();
                    System.out.println();
                    db.createCustomer(new Customer(address, birth_date, city, name, last_name, postal_code));
                    break;
                }
                case "3" : {

                    System.out.print("Enter employee id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println();
                    ArrayList<Order_head> h = (ArrayList<Order_head>) db.listOrdersForEmployee(id);
                    for(Order_head h1 : h){
                        System.out.println("| Order id: "+h1.id()+" | Order date: "+h1.orderDate()+" | Customer id: "+h1.customerId()+" | Employee id: "+ h1.employeeId()+" |");
                    }
                    System.out.println();
                    break;
                }
                case "4" : {

                    System.out.print("Enter order date: ");
                    String orderDate = scanner.nextLine();
                    System.out.println();
                    System.out.print("Enter customer id: ");
                    String customerId = scanner.nextLine();
                    System.out.println();
                    System.out.print("Enter employee id: ");
                    String employeeId = scanner.nextLine();
                    System.out.println();
                    Order_head orderHead = new Order_head(orderDate, Long.parseLong(customerId), Long.parseLong(employeeId));

                    System.out.print("How many items are contained in the order: ");
                    int orderItemQuantity = scanner.nextInt();
                    System.out.println();
                    scanner.nextLine();

                    List<Order_line> orderLineList = new ArrayList<>();

                    for(int i = 0; i<orderItemQuantity; i++){
                        System.out.print("Furniture id: ");
                        long furnitureId = scanner.nextLong();
                        scanner.nextLine();

                        System.out.print("Quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                        orderLineList.add(new Order_line(furnitureId, quantity));

                    }
                    System.out.println();
                    db.createOrder(orderHead, orderLineList);

                    break;}
                case "5" : {

                    System.out.print("Enter employee id: ");
                    int employeeId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println();
                    List<OrderDetail> orderDetails =  db.listOrdersWithCustomerNameForEmployee(employeeId);
                    for(OrderDetail o : orderDetails){

                        System.out.println("| Order id: "+o.id()+" | Order date: "+o.orderDate()+" | Customer id: "+ o.customerId()+" | Customer name: "+o.customerName()+" |");
                    }
                    System.out.println();

                    break;
                }
                case "6": {

                    System.out.print("Please enter employee id: ");
                    int employeeId = scanner.nextInt();
                    scanner.nextLine();

                    List<OrderDetail> orderDetails = db.listOrdersWithDetailsForEmployee(employeeId);

                    for(OrderDetail o : orderDetails){
                        System.out.println("| Order id: "+o.id()+"| Order date: "+o.orderDate()+" | Customer Id: "+o.customerId()+" | Employee id: "+o.employeeId()
                        +"| Customer name: "+o.customerName()+" |");
                        for(OrderLineDetail od : o.orderLines()){
                            System.out.println("    | Order line id: "+od.id()+" | Quantity: "+od.quantity()+" | Furniture id: "+od.furnitureId()+" | Furniture name: "+od.furnitureName()+" |");
                        }
                    }
                    System.out.println();

                    break;
                }
                case "7":{

                    System.out.print("Please enter customer id: ");
                    long customerId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println();
                    System.out.print("Please enter new address: ");
                    String newAddress = scanner.nextLine();
                    System.out.println();

                    db.updateCustomerAddress(newAddress, customerId);
                    System.out.println();
                }
            }
        }
    }

}
