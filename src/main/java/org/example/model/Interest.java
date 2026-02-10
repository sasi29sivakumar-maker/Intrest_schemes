package org.example.model;

import java.time.LocalDate;

public class Interest {

    private long interestId;
    private long accountId;
    private double Interestamount;
    private LocalDate interesrapplieddate;

    public void setInterestamount(double interestamount) {
        Interestamount = interestamount;
    }

    public double getInterestamount() {
        return Interestamount;
    };

    public LocalDate getInteresrapplieddate() {
        return interesrapplieddate;
    }

    public void setInteresrapplieddate(LocalDate interesrapplieddate) {
        this.interesrapplieddate = interesrapplieddate;
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