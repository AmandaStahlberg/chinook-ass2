package com.example.chinookass2.repositories;

import com.example.chinookass2.models.Customer;

import java.util.Collection;

public interface CustomerRepository extends CRUDRepository<Customer, Integer> {

    Collection<Customer> findByName(String search);

    Collection<Customer> pageCustomers(int limit, int offset);

}
