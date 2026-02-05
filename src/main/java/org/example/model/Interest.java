package org.example.model;

import java.sql.Date;
import java.time.LocalDate;

public class Interest {

    private long interestId;
    private long accountId;
    private double Interestamount;

    public LocalDate getInterestapplieddate() {
        return interestapplieddate;
    }

    public void setInterestapplieddate(LocalDate interestapplieddate) {
        this.interestapplieddate = interestapplieddate;
    }

    private LocalDate interestapplieddate;

    public void setInterestamount(double interestamount) {
        Interestamount = interestamount;
    }


    public double getInterestamount() {
        return Interestamount;
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