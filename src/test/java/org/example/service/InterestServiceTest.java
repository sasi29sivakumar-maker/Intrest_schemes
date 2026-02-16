package org.example.service;



import org.example.dao.InterestDAO;
import org.example.model.Account;
import org.example.model.Interest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InterestServiceTest {

    private InterestService interestService;
    private InterestDAO interestDAO;

    @BeforeEach
    void setUp() throws Exception {

        interestService = new InterestService();
        interestDAO = mock(InterestDAO.class);

        Field field =
                InterestService.class.getDeclaredField("interestDAO");
        field.setAccessible(true);
        field.set(interestService, interestDAO);
    }

    @Test
    void testInsertSuccess() {

        LocalDate today = LocalDate.now();

        Account account = new Account();
        account.setAccountId(1L);
        account.setAmount(120000L);
        account.setTenure(5);
        account.setCreatedat(today);

        interestService.insert(account);

        verify(interestDAO).insert(any(Interest.class));
    }

    @Test
    void testInsertAfterMaturity() {

        LocalDate createdDate = LocalDate.now().minusYears(6);

        Account account = new Account();
        account.setAccountId(1L);
        account.setAmount(120000L);
        account.setTenure(5);
        account.setCreatedat(createdDate);

        interestService.insert(account);

        verify(interestDAO, never()).insert(any());
    }


    @Test
    void testInsertDayNotMatching() {

        LocalDate today = LocalDate.now();
        LocalDate createdDate = today.minusDays(1); // Different day

        Account account = new Account();
        account.setAccountId(1L);
        account.setAmount(120000L);
        account.setTenure(5);
        account.setCreatedat(createdDate);

        interestService.insert(account);

        verify(interestDAO, never()).insert(any());
    }

    @Test
    void testGetInterestsSuccess() throws Exception {

        when(interestDAO.getInterestsByAccountId(1L))
                .thenReturn(Collections.emptyList());

        List<Interest> result =
                interestService.getInterestsByAccountId(1L);

        assertNotNull(result);
        verify(interestDAO).getInterestsByAccountId(1L);
    }


    @Test
    void testGetInterestsFailure() throws Exception {

        when(interestDAO.getInterestsByAccountId(1L))
                .thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> interestService.getInterestsByAccountId(1L));
    }
}