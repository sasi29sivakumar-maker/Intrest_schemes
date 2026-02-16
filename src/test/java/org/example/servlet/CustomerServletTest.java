package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Customer;
import org.example.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServletTest {

    private CustomerServlet customerServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CustomerService customerService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {

        customerServlet = new CustomerServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        customerService = mock(CustomerService.class);

        objectMapper = new ObjectMapper();

        Field serviceField =
                CustomerServlet.class.getDeclaredField("customerService");
        serviceField.setAccessible(true);
        serviceField.set(customerServlet, customerService);
    }

    @Test
    void testDoPostSuccess() throws Exception {

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("Bharani");

        String json = objectMapper.writeValueAsString(customer);

        when(request.getInputStream()).thenReturn(mockInputStream(json));

        customerServlet.doPost(request, response);

        verify(customerService).insert(any(Customer.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }


    @Test
    void testDoPostFailure() throws Exception {

        when(request.getInputStream()).thenThrow(IOException.class);

        customerServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(customerService, never()).insert(any());
    }


    @Test
    void testDoGetSuccess() throws Exception {

        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(customerService.findAll()).thenReturn(Collections.emptyList());

        customerServlet.doGet(request, response);

        verify(customerService).findAll();
        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }


    @Test
    void testDoGetFailure() throws Exception {

        when(customerService.findAll()).thenThrow(RuntimeException.class);

        customerServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    private ServletInputStream mockInputStream(String json) {

        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(json.getBytes());

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }
}