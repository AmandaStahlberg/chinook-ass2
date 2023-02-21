package com.example.chinookass2.runners;

import com.example.chinookass2.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ChinookAppRunner implements ApplicationRunner{

    private final CustomerRepository customerRepository;

    @Autowired
    public ChinookAppRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Test");

    }
}
