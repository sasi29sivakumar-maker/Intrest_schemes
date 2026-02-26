package org.example.filter;

import org.example.util.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    private static final Logger log =LoggerFactory.getLogger(LoginFilter.class);

    private static final int SUB_STRING=7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws
            IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (uri.contains("/user/register") ||(uri.contains("/user/login"))) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(SUB_STRING);

        try {

            String username = Jwt.extractUsername(token);

            if (Jwt.validateToken(token, username)) {
                chain.doFilter(request, response);
            }

        } catch (Exception e) {
            log.warn("Invalid Jwt token",e);
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid token");

        }
    }
}