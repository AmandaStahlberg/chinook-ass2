package com.example.chinookass2.repositories;

import com.example.chinookass2.models.Customer;
import com.example.chinookass2.models.CustomerCountry;
import com.example.chinookass2.models.CustomerInvoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private final String url;
    private final String username;
    private final String password;

    public CustomerRepositoryImpl(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;

    }

    @Override
    public Collection<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        List<Customer> customers = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username,password)) {
            // Write statement
            PreparedStatement statement = conn.prepareStatement(sql);
            // Execute statement
            ResultSet result = statement.executeQuery();
            // Handle result
            while(result.next()) {
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        Customer customer = null;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Write statement
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            // Execute statement
            ResultSet result = statement.executeQuery();
            // Handle result
            while (result.next()) {
                customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

     @Override
    public Collection<Customer> findByName(String search) {
        String sql = "SELECT * FROM customer WHERE first_name LIKE ? OR last_name LIKE ?";
        List<Customer> customers = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username,password)) {
            // Write statement
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, search);
            statement.setString(2, search);
            // Execute statement
            ResultSet result = statement.executeQuery();
            // Handle result
            while(result.next()) {
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }


@Override
    public  CustomerCountry getCountryByMostCustomers() {
        String sql = "SELECT country, count(*) as num_customers FROM customer GROUP BY country ORDER BY num_customers DESC LIMIT 1";
        CustomerCountry customerCountry = null;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                customerCountry = new CustomerCountry(result.getString("country"), result.getInt("num_customers"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerCountry;
    }

    public CustomerInvoice getHighestSpender() {
        String sql = "SELECT customer_id, Sum(total) AS invoice_total FROM invoice" +
                " GROUP BY customer_id ORDER BY invoice_total LIMIT 1";
        CustomerInvoice customerSpender = null;
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                customerSpender = new CustomerInvoice(rs.getInt("customer_id"), rs.getInt("invoice_total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerSpender;
    }





    @Override
    public int insert(Customer object) {
        return 0;
    }

    @Override
    public int update(Customer object) {
        return 0;
    }

    @Override
    public int delete(Customer object) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public CustomerCountry returnCountryWithMostCustomers() {
        return getCountryByMostCustomers();
    }

    @Override
    public CustomerInvoice returngetHighestSpender() {
        return getHighestSpender();
    }

}
