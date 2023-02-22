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
