package org.example.service;

import org.example.Exception.DataException;
import org.example.dao.AccountDAO;
import org.example.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService;
    private AccountDAO accountDAO;

    @BeforeEach
    void setUp() throws Exception {

        accountService = new AccountService();
        accountDAO = mock(AccountDAO.class);

        Field field =
                AccountService.class.getDeclaredField("accountDAO");
        field.setAccessible(true);
        field.set(accountService, accountDAO);
    }


    @Test
    void testInsertSuccess() {

        Account account = new Account();
        account.setAmount(200000L);
        account.setTenure(5);

        accountService.insert(account);

        verify(accountDAO).insert(account);
    }

    @Test
    void testInsertInvalidAmount() {

        Account account = new Account();
        account.setAmount(50000L); // less than 1 lakh
        account.setTenure(5);

        DataException ex = assertThrows(DataException.class,
                () -> accountService.insert(account));

        assertEquals("amount should be greater than one lakh ruppess",
                ex.getMessage());

        verify(accountDAO, never()).insert(any());
    }

    @Test
    void testInsertInvalidTenure() {

        Account account = new Account();
        account.setAmount(200000L);
        account.setTenure(2); // less than 3

        DataException ex = assertThrows(DataException.class,
                () -> accountService.insert(account));

        assertEquals("tenure should be greater than 3 years",
                ex.getMessage());

        verify(accountDAO, never()).insert(any());
    }

    @Test
    void testFindAllSuccess() throws Exception {

        when(accountDAO.findAll()).thenReturn(Collections.emptyList());

        List<Account> result = accountService.findAll();

        assertNotNull(result);
        verify(accountDAO).findAll();
    }

    @Test
    void testFindAllFailure() throws Exception {

        when(accountDAO.findAll()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> accountService.findAll());
    }
}