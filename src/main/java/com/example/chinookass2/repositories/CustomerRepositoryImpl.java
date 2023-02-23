package com.example.chinookass2.repositories;

import com.example.chinookass2.models.Customer;
import com.example.chinookass2.models.CustomerCountry;
import com.example.chinookass2.models.CustomerGenre;
import com.example.chinookass2.models.CustomerInvoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public  class CustomerRepositoryImpl implements CustomerRepository {
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
            statement.setString(1, "%" + search + "%");
            statement.setString(2, "%" + search + "%");
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
    public Collection<Customer> pageCustomers(int limit, int offset) {
        String sql = "SELECT * FROM customer ORDER BY customer_id LIMIT ? OFFSET ?";
        List<Customer> customers = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username,password)) {
            // Write statement
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
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
        String sql = "SELECT customer_id FROM invoice GROUP BY customer_id  ORDER BY SUM(total) DESC LIMIT 1; ";
        CustomerInvoice customerSpender = null;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                customerSpender = new CustomerInvoice(result.getInt("customer_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerSpender;
    }

    @Override
    public Collection<CustomerGenre> getMostPopularGenre(int customerId) {
        String sql = "SELECT i.customer_id, g.name, count(g.name) AS genre_count\n" +
                // Start at invoice to get track with customer_id
                "FROM invoice AS i\n" +
                // Join with invoice line to get track_id
                "INNER JOIN invoice_line AS il\n" +
                "ON il.invoice_id = i.invoice_id\n" +
                // Join on track to get genre_id
                "INNER JOIN track AS t\n" +
                "ON il.track_id = t.track_id\n" +
                // Join on genre to get name
                "INNER JOIN genre AS g\n" +
                "ON t.genre_id = g.genre_id\n" +
                // Clauses...
                "WHERE i.customer_id = ?\n" +
                "GROUP BY g.name, i.customer_id\n" +
                "ORDER BY genre_count DESC\n" +
                "FETCH FIRST 1 ROWS WITH TIES";
        Set<CustomerGenre> customerGenres = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                customerGenres.add(new CustomerGenre(
                        result.getInt("customer_id"),
                        result.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.out.printf(e.getMessage());
        }
        return customerGenres;
    }


    @Override
    public int add(Customer customer) {
        String sql = "INSERT INTO customer(first_name, last_name, country, postal_code, phone, email) VALUES (?,?,?,?,?,?)";
        int newCustomer = 0;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Write statement
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.firstName());
            statement.setString(2, customer.lastName());
            statement.setString(3, customer.country());
            statement.setString(4, customer.postalCode());
            statement.setString(5, customer.phoneNumber());
            statement.setString(6, customer.email());
            // Execute statement
            newCustomer = statement.executeUpdate();
            // Handle result

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCustomer;
    }
    @Override
    public int update(Customer customer) {
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, country = ?, postal_code = ?, phone = ?, email = ? WHERE customer_id = ?";
        int updatedCustomer = 0;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Write statement
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.firstName());
            statement.setString(2, customer.lastName());
            statement.setString(3, customer.country());
            statement.setString(4, customer.postalCode());
            statement.setString(5, customer.phoneNumber());
            statement.setString(6, customer.email());
            statement.setInt(7, customer.id());
            // Execute statement
            updatedCustomer = statement.executeUpdate();
            // Handle result

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedCustomer;
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
