package org.example.model;

import java.sql.Date;

public class Interest {

    private long interestId;
    private long accountId;
    private long Interestamount;

    public long getInterestamount() {
        return Interestamount;
    }

    public void setInterestamount(long interestamount) {
        Interestamount = interestamount;
    }

    public long getInterestId() {
        return interestId;
    }

    public void setInterestId(long interestId) {
        this.interestId = interestId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}