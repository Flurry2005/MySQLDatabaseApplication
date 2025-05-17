package se.liam.sqldbapplication;

import java.util.ArrayList;
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
                            "5. List orders with customer name for specified employee\n");
            System.out.print("Option: ");
            String userInput = scanner.nextLine();
            System.out.println();
            switch(userInput){
                case "1" : {

                    ArrayList<Customer> customers = (ArrayList<Customer>) db.listAllCustomers();
                    for(Customer c : customers){
                        System.out.println(c.firstName());
                    }
                    break;
                }
                case "2" : {

                    System.out.print("Enter customer id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
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
                    db.createCustomer(new Customer(id, address, birth_date, city, name, last_name, postal_code));
                    break;
                }
                case "3" : {

                    System.out.print("Enter employee id: ");
                    int id = scanner.nextInt();

                    ArrayList<Order_head> h = (ArrayList<Order_head>) db.listOrdersForEmployee(id);
                    for(Order_head h1 : h){
                        System.out.println(h1.id());
                        System.out.println(h1.orderDate());
                        System.out.println(h1.customerId());
                        System.out.println(h1.employeeId());
                    }
                    break;
                }
                case "4" : break;
                case "5" : break;
            }
        }
    }

}
