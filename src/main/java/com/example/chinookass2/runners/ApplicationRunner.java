package com.example.chinookass2.runners;

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
                        
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

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
        //getMostPopularGenre();
        // Requirement 1
        System.out.println(customerRepository.findAll());
        // Requirement 2, find customer with id 1
        System.out.println(customerRepository.findById(1));
        // Requirement 3, find customer with Tremblay as lastname
        System.out.println(customerRepository.findByName("Tremblay"));
        System.out.println(customerRepository.returnCountryWithMostCustomers());
        System.out.println(customerRepository.returngetHighestSpender());
        System.out.println(customerRepository.returngetCustomerMostPopularGenre(88));
    }
}
