package org.example.scheduler;

import org.example.Exception.DataException;
import org.example.model.Account;
import org.example.model.Interest;
import org.example.service.AccountService;
import org.example.service.InterestService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.SQLException;
import java.util.List;

public class Jobs implements Job {

    private final AccountService accountService=new AccountService();
    private final InterestService interestService=new InterestService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            List<Account> accounts =  accountService.findAll();

            for(Account account : accounts){
                interestService.insert(account);
            }
        } catch (SQLException e) {
            throw new DataException("failed to get the details",e);
        }
    }
}