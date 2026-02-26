package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Exception.DataException;
import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginServletTest {

    private LoginServlet loginServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserService userService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {

        loginServlet = new LoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userService = mock(UserService.class);

        objectMapper = new ObjectMapper();


        Field field =
                LoginServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(loginServlet, userService);
    }


    @Test
    void testDoPostSuccess() throws Exception {

        User user = new User();
        user.setUsername("bharani");
        user.setPassword("password123");

        String json = objectMapper.writeValueAsString(user);

        when(request.getInputStream()).thenReturn(mockInputStream(json));
        when(userService.verify("bharani", "password123"))
                .thenReturn("sample-jwt-token");

        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        loginServlet.doPost(request, response);

        verify(userService).verify("bharani", "password123");
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);

        assertTrue(writer.toString().contains("sample-jwt-token"));
    }


    @Test
    void testDoPostInvalidCredentials() throws Exception {

        User user = new User();
        user.setUsername("bharani");
        user.setPassword("wrong");

        String json = objectMapper.writeValueAsString(user);

        when(request.getInputStream()).thenReturn(mockInputStream(json));
        when(userService.verify("bharani", "wrong"))
                .thenThrow(RuntimeException.class);

        assertThrows(DataException.class,
                () -> loginServlet.doPost(request, response));

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void testDoPostInvalidJson() throws Exception {

        when(request.getInputStream())
                .thenThrow(IOException.class);

        assertThrows(DataException.class,
                () -> loginServlet.doPost(request, response));

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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