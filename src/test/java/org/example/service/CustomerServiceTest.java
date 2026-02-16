package org.example.service;

import org.example.dao.CustomerDAO;
import org.example.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() throws Exception {

        customerService = new CustomerService();
        customerDAO = mock(CustomerDAO.class);

        Field field =
                CustomerService.class.getDeclaredField("customerDAO");
        field.setAccessible(true);
        field.set(customerService, customerDAO);
    }


    @Test
    void testInsert() {

        Customer customer = new Customer();
        customer.setCustomerName("Bharani");

        customerService.insert(customer);

        verify(customerDAO).insert(customer);
    }


    @Test
    void testFindAllSuccess() throws Exception {

        List<Customer> mockList = Arrays.asList(new Customer(), new Customer());

        when(customerDAO.findAll()).thenReturn(mockList);

        List<Customer> result = customerService.findAll();

        assertEquals(2, result.size());
        verify(customerDAO).findAll();
    }


    @Test
    void testFindAllFailure() throws Exception {

        when(customerDAO.findAll()).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> customerService.findAll());
    }
}