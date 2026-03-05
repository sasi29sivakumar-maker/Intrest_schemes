package org.example.dao;

import org.example.model.Account;
import org.example.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class AccountDAOTest {

    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;

    @BeforeEach
    void setup() throws Exception {

        accountDAO = new AccountDAO();
        customerDAO = new CustomerDAO();

        try (
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/Intrest_schemes",
                        "root",
                        "250408");
        ) {

            Statement stmt = con.createStatement();

            stmt.execute("DELETE FROM interest");
            stmt.execute("DELETE FROM account");
            stmt.execute("DELETE FROM customer");
        }
    }

    private long createCustomer() throws SQLException {

        Customer customer = new Customer();

        customer.setUserId(1);
        customer.setCustomerName("sasi");
        customer.setGender("MALE");
        customer.setPhoneNo("6938727120");
        customer.setEmail("sasi29@gmail.com");
        customer.setAddress("Chennai");
        customer.setAadharNo("26671541044");
        customer.setCustomerStatus("ACTIVE");

        customerDAO.insert(customer);

        List<Customer> customers = customerDAO.findAll();

        return customers.get(customers.size() - 1).getCustomerId();
    }

    private Account createAccount(long customerId) {

        Account account = new Account();

        account.setCustomerId(customerId);
        account.setAccountNumber("ACC123456");
        account.setAccountStatus("ACTIVE");
        account.setCreatedat(LocalDate.now());
        account.setAmount(10000);
        account.setTenure(12);

        return account;
    }

    @Test
    void testInsert() throws SQLException {

        long customerId = createCustomer();

        Account account = createAccount(customerId);

        accountDAO.insert(account);

        List<Account> accounts = accountDAO.findAll();

        assertEquals(1, accounts.size());
        assertEquals("ACC123456", accounts.get(0).getAccountNumber());
    }

    @Test
    void testFindAll() throws SQLException {

        long customerId = createCustomer();

        accountDAO.insert(createAccount(customerId));

        List<Account> accounts = accountDAO.findAll();

        assertFalse(accounts.isEmpty());
    }
}