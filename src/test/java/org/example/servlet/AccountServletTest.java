package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Account;
import org.example.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountServletTest {

    private AccountServlet accountServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AccountService accountService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {

        accountServlet = new AccountServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        accountService = mock(AccountService.class);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Field serviceField =
                AccountServlet.class.getDeclaredField("accountService");
        serviceField.setAccessible(true);
        serviceField.set(accountServlet, accountService);
    }


    @Test
    void testDoPostSuccess() throws Exception {

        Account account = new Account();
        account.setAccountId(1L);
        account.setCustomerId(10L);
        account.setAccountNumber("ACC123");
        account.setAccountStatus("ACTIVE");
        account.setCreatedat(LocalDate.now());
        account.setAmount(10000L);
        account.setTenure(12);

        String json = objectMapper.writeValueAsString(account);

        when(request.getInputStream()).thenReturn(mockInputStream(json));

        accountServlet.doPost(request, response);

        verify(accountService).insert(any(Account.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }


    @Test
    void testDoPostFailure() throws Exception {

        when(request.getInputStream()).thenThrow(IOException.class);

        accountServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(accountService, never()).insert(any());
    }

    @Test
    void testDoGetSuccess() throws Exception {

        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(accountService.findAll()).thenReturn(Collections.emptyList());

        accountServlet.doGet(request, response);

        verify(accountService).findAll();
        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }


    @Test
    void testDoGetFailure() throws Exception {

        when(accountService.findAll()).thenThrow(RuntimeException.class);

        accountServlet.doGet(request, response);

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