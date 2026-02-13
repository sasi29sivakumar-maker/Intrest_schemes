package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Exception.DataException;
import org.example.model.Account;
import org.example.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    private static final Logger log= LoggerFactory.getLogger(AccountServlet.class);

    private final ObjectMapper objectMapper ;
    public AccountServlet() {
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }
    private final AccountService accountService=new AccountService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        try{
            Account account=objectMapper.readValue(request.getInputStream(), Account.class);
            accountService.insert(account);
            response.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Customer created successfully:{}",account.getAccountId());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            log.error("Failed to insert value in Account, Exceptiom : ",e);
        }


    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("application/json");
            mapAccount(response);
            log.info("Fetched All account details successfully");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error("failed to get account,Expection: ",e);
        }

    }
    private void mapAccount(HttpServletResponse response){
        try {
            objectMapper.writeValue(response.getWriter(), accountService.findAll());

        } catch (Exception e) {
            throw new DataException("Failed to fetch account",e);
        }
    }
}