package com.example.chinookass2.repositories;

import com.example.chinookass2.models.CustomerCountry;

import java.util.Collection;
import java.util.List;

public interface CRUDRepository<T, U> {
    Collection<T> findAll();
    T findById(U id);
    int add(T object);
    int update(T object);
    int delete(T object);
    int deleteById(U id);

    CustomerCountry returnCountryWithMostCustomers();
}
