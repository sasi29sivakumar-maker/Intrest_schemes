package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.util.Hashing;
import org.example.util.Jwt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws Exception {

        userService = new UserService();
        userDAO = mock(UserDAO.class);


        Field field =
                UserService.class.getDeclaredField("userDAO");
        field.setAccessible(true);
        field.set(userService, userDAO);
    }


    @Test
    void testAddUser() {

        User user = new User();
        user.setUsername("bharani");
        user.setPassword("plainPassword");

        try (MockedStatic<Hashing> hashingMock =
                     mockStatic(Hashing.class)) {

            hashingMock.when(() ->
                            Hashing.hashPassword("plainPassword"))
                    .thenReturn("hashedPassword");

            userService.addUser(user);

            assertEquals("hashedPassword", user.getPassword());
            verify(userDAO).adduser(user);
        }
    }


    @Test
    void testVerifySuccess() {

        when(userDAO.getPass("bharani"))
                .thenReturn("hashedPassword");

        try (MockedStatic<Hashing> hashingMock =
                     mockStatic(Hashing.class);
             MockedStatic<Jwt> jwtMock =
                     mockStatic(Jwt.class)) {

            hashingMock.when(() ->
                            Hashing.verifyPassword("password123",
                                    "hashedPassword"))
                    .thenReturn(true);

            jwtMock.when(() ->
                            Jwt.generateToken("bharani"))
                    .thenReturn("sampleToken");

            String result =
                    userService.verify("bharani", "password123");

            assertEquals("sampleToken", result);
        }
    }


    @Test
    void testVerifyWrongPassword() {

        when(userDAO.getPass("bharani"))
                .thenReturn("hashedPassword");

        try (MockedStatic<Hashing> hashingMock =
                     mockStatic(Hashing.class)) {

            hashingMock.when(() ->
                            Hashing.verifyPassword("wrong",
                                    "hashedPassword"))
                    .thenReturn(false);

            String result =
                    userService.verify("bharani", "wrong");

            assertEquals("Password wrong", result);
        }
    }

    @Test
    void testVerifyUserNotFound() {

        when(userDAO.getPass("bharani"))
                .thenReturn(null);

        String result =
                userService.verify("bharani", "password");

        assertEquals(
                "No username found in this user please register",
                result);
    }
}