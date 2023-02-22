package com.example.chinookass2.repositories;

import com.example.chinookass2.models.Customer;
import com.example.chinookass2.models.CustomerCountry;
import com.example.chinookass2.models.CustomerInvoice;

import java.util.Collection;

public interface CustomerRepository extends CRUDRepository<Customer, Integer> {

    Collection<Customer> findByName(String search);


    CustomerCountry getCountryByMostCustomers();

    CustomerInvoice returngetHighestSpender();
}
