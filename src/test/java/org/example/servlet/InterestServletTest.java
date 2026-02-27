package org.example.servlet;

import org.example.service.InterestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.lang.reflect.Field;
import java.sql.SQLException;


import static org.mockito.Mockito.*;


class IntrestServletTest {

    private IntrestServlet intrestServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private InterestService interestService;

    @BeforeEach
    void setUp() throws Exception {

        intrestServlet = new IntrestServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        interestService = mock(InterestService.class);

        Field field =
                IntrestServlet.class.getDeclaredField("interestService");
        field.setAccessible(true);
        field.set(intrestServlet, interestService);
    }


    @Test
    void testDoGetInvalidAccountId() throws SQLException {

        when(request.getParameter("accountId")).thenReturn("abc");

        intrestServlet.doGet(request, response);

        verify(interestService, never())
                .getInterestsByAccountId(anyLong());
    }


    @Test
    void testDoGetServiceException() throws SQLException {

        when(request.getParameter("accountId")).thenReturn("10");
        when(interestService.getInterestsByAccountId(10L))
                .thenThrow(RuntimeException.class);

        intrestServlet.doGet(request, response);

        verify(interestService)
                .getInterestsByAccountId(10L);
    }
}