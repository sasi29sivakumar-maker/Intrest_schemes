package org.example.service;

import org.example.dao.InterestDAO;
import org.example.model.Account;
import org.example.model.Interest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InterestService {


    InterestDAO interestDAO=new InterestDAO();
    private static final double INTEREST_RATE=7.2;
    public void insert(Account account) {

        LocalDate today = LocalDate.now();
        LocalDate createdDate = account.getCreatedat();
        int period = account.getTenure();
        LocalDate maturityDate = createdDate.plusYears(period);

        if (today.isBefore(maturityDate)) {
            if (today.getDayOfMonth() == createdDate.getDayOfMonth()) {

                Interest interest = new Interest();
                double interests = account.getAmount() * INTEREST_RATE / 100;
                double monthly = interests / 12;

                interest.setInterestamount(monthly);
                interest.setInteresrapplieddate(today);
                interest.setAccountId(account.getAccountId());

                interestDAO.insert(interest);
            }
        }
    }

    public List<Interest>getInterestsByAccountId(long id) throws SQLException {
        return interestDAO.getInterestsByAccountId(id);
    }
}