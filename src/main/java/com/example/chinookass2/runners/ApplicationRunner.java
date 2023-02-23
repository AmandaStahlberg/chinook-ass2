package com.example.chinookass2.runners;

import com.example.chinookass2.models.Customer;
import com.example.chinookass2.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public ApplicationRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
=======
        System.out.println(customerRepository.findByName("Tr"));
        // Requirement 4, page 2 customers from id 5
        System.out.println(customerRepository.pageCustomers(2, 4));
        // Requirement 5, add newCustomer to customer table
        Customer newCustomer = new Customer(0,"Amanda", "Ståhlberg", "Sweden", "123 45", "123 456 66 77", "hej@email.com" );
        System.out.println(customerRepository.add(newCustomer));
        System.out.println(newCustomer);
        // Requirement 6, find customer on id 67 and update findCustomer to updatedCustomer
        Customer findCustomer = customerRepository.findById(67);
        Customer updatedCustomer = new Customer(findCustomer.id(),"Updated Amanda", findCustomer.lastName() , findCustomer.country(), findCustomer.postalCode(), findCustomer.phoneNumber(), "new@email.com");
        System.out.println(customerRepository.update(updatedCustomer));
        System.out.println(updatedCustomer);

    }
}
