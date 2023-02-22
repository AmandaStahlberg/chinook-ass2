package com.example.chinookass2.runners;

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
    }
}
