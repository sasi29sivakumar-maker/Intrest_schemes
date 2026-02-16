package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Exception.DataException;
import org.example.model.Customer;
import org.example.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    private static final Logger log= LoggerFactory.getLogger(CustomerServlet.class);
    private final CustomerService customerService=new CustomerService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        try{
            Customer customer=objectMapper.readValue(request.getInputStream(), Customer.class);
            customerService.insert(customer);
            response.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Customer created successfully:{}",customer.getCustomerId());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            log.error("Failed to insert value in Customer, Exceptiom : ",e);
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("application/json");
            mapCustomer(response);
            log.info("Fetched All customer details successfully");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error("failed to get customer,Expection: ",e);
        }

    }

    private void mapCustomer(HttpServletResponse response){
        try {
            objectMapper.writeValue(response.getWriter(), customerService.findAll());

        } catch (Exception e) {
            throw new DataException("Failed to fetch customer",e);
        }
    }
}