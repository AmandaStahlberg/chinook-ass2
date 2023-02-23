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

    public CustomerGenre getCustomerMostPopularGenre(int customerId) {
        String sql = "SELECT genre.\"name\" as genre_name, count(genre.\"name\") as count " +
                "FROM invoice_line " +
                "JOIN invoice " +
                "ON invoice.invoice_id = invoice_line.invoice_id " +
                "JOIN track " +
                "ON invoice_line.track_id = track.track_id " +
                "JOIN genre " +
                "ON track.genre_id = genre.genre_id " +
                "WHERE customer_id = 1 " +
                "GROUP BY genre.name " +
                "ORDER BY count DESC " +
                "LIMIT 1";

        CustomerGenre customerGenre = null;
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                customerGenre = new CustomerGenre(customerId, result.getString("genre_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerGenre;
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

    @Override
    public CustomerGenre returngetCustomerMostPopularGenre() {
        return null;
    }

    @Override
    public CustomerGenre returngetCustomerMostPopularGenre(int customerId) {
        return getCustomerMostPopularGenre(customerId);
    }


}
