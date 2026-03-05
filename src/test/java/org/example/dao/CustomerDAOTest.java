package org.example.dao;

import org.example.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static junit.framework.Assert.*;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;

    @BeforeEach
    void setup() throws Exception {

        customerDAO = new CustomerDAO();

        try (
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/Intrest_schemes",
                        "root",
                        "250408");
        ) {
            Statement stmt = con.createStatement();

            stmt.execute  ("DELETE FROM interest");
            // delete child table first
            stmt.execute("DELETE FROM account");

            // then delete parent table
            stmt.execute("DELETE FROM customer");
        }
    }

    @Test
    void testInsert() throws SQLException {

        Customer customer = createCustomer();

        customerDAO.insert(customer);

        List<Customer> customers = customerDAO.findAll();

        assertEquals(1, customers.size());
        assertEquals("Bharani", customers.get(0).getCustomerName());
    }

    @Test
    void testFindAll() throws SQLException {

        customerDAO.insert(createCustomer());

        List<Customer> customers = customerDAO.findAll();

        assertFalse(customers.isEmpty());
    }

    private Customer createCustomer() {

        Customer customer = new Customer();

        customer.setUserId(1);
        customer.setCustomerName("sasi");
        customer.setGender("MALE");
        customer.setPhoneNo("6938727120");
        customer.setEmail("sasi29@gmail.com");
        customer.setAddress("Chennai, Tamil Nadu");
        customer.setAadharNo("26671541044");
        customer.setCustomerStatus("ACTIVE");

        return customer;
    }
}