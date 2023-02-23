package com.example.chinookass2.runners;

import com.example.chinookass2.models.Customer;
import com.example.chinookass2.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public ApplicationRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }

    public static void menu(String[] options){

            for (String option : options){
                System.out.println(option);
            }
            System.out.print("Choose an option 1-" + options.length + ": ");
        }

    public void searchById(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("write customer id: ");
        boolean founded = true;
        while (founded){
            try {
                System.out.println(customerRepository.findById(scanner.nextInt()));
                founded = false;
            }catch (InputMismatchException ex){
                System.out.print("the customer id should be a number: ");
                scanner.next();
            }
        }
    }

    public void searchByName(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("write the customer name: ");
        boolean founded = true;
        while (founded){
            try {
                customerRepository.findByName(scanner.nextLine()).stream().forEach(customer -> System.out.println(customer.toString()));
                founded = false;
            }catch (InputMismatchException ex){
                System.out.print("You need to enter a name: ");
                scanner.next();
            }
        }
    }

    public void getAPageOfCustomers() {
        Scanner scanner = new Scanner(System.in);
        boolean printed = true;
        int rowsStart = 0;
        int rowsAmount = 0;
        while (printed) {
            try {
                System.out.print("write the number of the rows: ");
                rowsAmount = scanner.nextInt();
                System.out.print("write the start row number : ");
                rowsStart = scanner.nextInt();
                customerRepository.pageCustomers(rowsAmount, rowsStart).stream()
                        .forEach(customer -> System.out.println(customer.toString()));
                printed = false;
            } catch (InputMismatchException ex) {
                System.out.println(" you need to write numbers ");
                scanner.next();
            }
        }
    }

    public void AddCustomer(){
        Scanner scanner = new Scanner(System.in);
        boolean inserted = true;
        int customerID=0;
        String firstName;
        String lastName;
        String country;
        String postalCode;
        String phoneNumber;
        String email;

        while (inserted){
            try {
                System.out.print("write the first name: ");
                firstName = scanner.nextLine();
                System.out.print("write the last name : ");
                lastName = scanner.nextLine();
                System.out.print("write the country : ");
                country = scanner.nextLine();
                System.out.print("write the postalCode : ");
                postalCode = scanner.nextLine();
                System.out.print("write the phoneNumber : ");
                phoneNumber = scanner.nextLine();
                System.out.print("write the email : ");
                email = scanner.nextLine();
                Customer newCustomer = new Customer(0,firstName, lastName, country, postalCode, phoneNumber, email );
                customerRepository.add(newCustomer);
                System.out.println(" the custumer "+ firstName + " " + lastName+ " is added to the db");
                System.out.println(newCustomer);
                inserted = false;
            }catch (InputMismatchException ex){
                System.out.print("You need to write a value: ");
                scanner.next();
            }
        }
    }

    public void UppdateCustomer(){
        Scanner scanner = new Scanner(System.in);
        boolean inserted = true;
        int customerID;
        String firstName;
        String lastName;
        String country;
        String postalCode;
        String phoneNumber;
        String email;

        while (inserted){
            try {
                System.out.print("write the customer id that you want to uppdate: ");
                customerID = scanner.nextInt();
                System.out.println("write the first name: ");
                firstName = scanner.nextLine();
                firstName = scanner.nextLine();
                System.out.print("write the last name : ");
                lastName = scanner.nextLine();
                System.out.print("write the country : ");
                country = scanner.nextLine();
                System.out.print("write the postalCode : ");
                postalCode = scanner.nextLine();
                System.out.print("write the phoneNumber : ");
                phoneNumber = scanner.nextLine();
                System.out.print("write the email : ");
                email = scanner.nextLine();
                Customer updatedCustomer = new Customer(customerID,firstName, lastName, country, postalCode, phoneNumber, email );
                customerRepository.update(updatedCustomer);
                System.out.println(" the custumer "+ firstName + " " + lastName+ " is uppdate to the db");
                System.out.println(updatedCustomer);
                inserted = false;
            }catch (InputMismatchException ex){
                System.out.print("You need to write a value: ");
                scanner.next();
            }
        }
    }


    public void getMostPopularGenre(){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Give the customer an id: ");
            boolean cont = true;
            while (cont){
                try {
                    System.out.println(customerRepository.returngetCustomerMostPopularGenre(scanner.nextInt()).genre());
                    cont = false;
                }catch (InputMismatchException ex){
                    System.out.print("the customer id should be a number: ");
                    scanner.next();
                }
            }
        }



    @Override
    public void run(ApplicationArguments args) throws Exception {

        String[] options = {
                "0. Exit",
                "1. Read all customers",
                "2. Search customer by the id",
                "3. Search customer by the name",
                "4. Read a page of customers",
                "5. Add a new customer",
                "6. Update an existing customer",
                "7. find the country with most customers",
                "8. find the highest spender",
                "9. Show most popular genre for a given customer"
        };
        Scanner scanner = new Scanner(System.in);
        int option=-1;
        while (option!=0){
            menu(options);
            try {
                option = scanner.nextInt();
                switch (option){
                    case 0:
                        option=0;
                        break;
                    case 1:             // Print out all customers from the db
                        customerRepository.findAll().stream().forEach(customer -> System.out.println(customer.toString()));
                        break;

                    case 2:
                        searchById();
                        break;

                    case 3:
                        searchByName();
                        break;
                    case 4:
                        getAPageOfCustomers();
                        break;
                    case 5:
                        AddCustomer();
                        break;
                    case 6:
                        UppdateCustomer();
                        break;
                       case 7:
                        System.out.println(customerRepository.returnCountryWithMostCustomers().country() + " (" +
                                customerRepository.returnCountryWithMostCustomers().numberOfCustomers() + " customers)");
                        break;
                    case 8:
                        System.out.println(
                                customerRepository.findById(customerRepository.returngetHighestSpender().customerId()).firstName() +
                                        " " + customerRepository.findById(customerRepository.returngetHighestSpender().customerId()).lastName() +
                                        " (total: " + customerRepository.returngetHighestSpender().toString() + ")");
                        break;
                    case 9:
                        getMostPopularGenre();
                        break;
                }
            }catch (InputMismatchException ex){
                System.out.println("Choose option: 1-" + options.length);
                scanner.next();
            }
        }


    }
}
