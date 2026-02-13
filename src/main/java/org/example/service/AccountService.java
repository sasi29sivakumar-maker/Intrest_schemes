package org.example.service;

import org.example.Exception.DataException;
import org.example.dao.AccountDAO;
import org.example.model.Account;
import org.example.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class AccountService {

    AccountDAO accountDAO=new AccountDAO();
    public void insert(Account account){

        if(account.getAmount()<100000 ) {
            throw new DataException("amount should be greater than one lakh ruppess");
        } else if (account.getTenure()<3) {
            throw new DataException("tenure should be greater than 3 years");
        } else{
            accountDAO.insert(account);
        }
    }

    public List<Account> findAll() throws SQLException {
        return accountDAO.findAll();
    }

}