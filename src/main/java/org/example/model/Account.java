package org.example.model;

import java.time.LocalDate;

public class Account {

    private long accountId;
    private long customerId;
    private String accountNumber;
    private String accountStatus;
    private LocalDate createdat;

    public LocalDate getCreatedat() {
        return createdat;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    private double amount;
    private int tenure;
    public Account() {
        this.accountStatus = "FIXED";
        this.createdat=LocalDate.now();
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}