package com.example.chinookass2.repositories;

import com.example.chinookass2.models.Customer;
import com.example.chinookass2.models.CustomerCountry;
import com.example.chinookass2.models.CustomerGenre;
import com.example.chinookass2.models.CustomerInvoice;

import java.util.Collection;

public interface CustomerRepository extends CRUDRepository<Customer, Integer> {

    Collection<Customer> findByName(String search);



    CustomerCountry getCountryByMostCustomers();


    Collection<CustomerGenre> getMostPopularGenre(int customerId);

    //CustomerGenre getCustomerMostPopularGenre(int customerId);

    CustomerInvoice returngetHighestSpender();



    Collection<Customer> pageCustomers(int limit, int offset);


}
