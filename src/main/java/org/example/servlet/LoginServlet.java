package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Exception.DataException;
import org.example.model.User;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService = new UserService();
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = objectMapper.readValue(request.getInputStream(), User.class);
            String token = userService.verify(user.getUsername(),user.getPassword());
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write("Token: " + token);
            LOG.info("Token Generated");
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new DataException("Failed",e);
        }
    }
}