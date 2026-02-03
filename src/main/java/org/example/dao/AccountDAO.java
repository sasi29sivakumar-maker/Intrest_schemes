package org.example.dao;

import org.example.Exception.DataException;
import org.example.dbconfiguration.DbConfig;
import org.example.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final Logger log= LoggerFactory.getLogger(AccountDAO.class);

    static final int INSERT_CUSTOMER_ID=1;
    static final int INSERT_ACCOUNT_NUMBER=2;
    static final int INSERT_ACCOUNT_STATUS=3;
    static final int INSERT_ACCOUNT_CREATEDAT=4;
    static final int INSERT_ACCOUNT_AMOUNT=5;
    static final int INSERT_ACCOUNT_TENURE=6;

    static  final int GET_ACCOUNT_BY_CUSTOMERID=1;

    String InsertSQL="Insert into account(customer_id,account_number,account_status,createdat,amount,tenure)" +
            "VALUES(?,?,?,?,?,?)";

    String GetByCustomerIdSQL = "SELECT * FROM account WHERE customer_id = ?";

    public void insert(Account account){

        try(Connection con= DbConfig.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(InsertSQL)){

            ps.setLong(INSERT_CUSTOMER_ID,account.getCustomerId());
            ps.setString(INSERT_ACCOUNT_NUMBER,account.getAccountNumber());
            ps.setString(INSERT_ACCOUNT_STATUS, account.getAccountStatus());
            ps.setDate(INSERT_ACCOUNT_CREATEDAT,java.sql.Date.valueOf(account.getCreatedat()));
            ps.setLong(INSERT_ACCOUNT_AMOUNT,account.getAmount());
            ps.setInt(INSERT_ACCOUNT_TENURE,account.getTenure());

            int changedRows=ps.executeUpdate();

            if(changedRows==0){
                log.error("Insert failed,fields are not inserted in account:{}",account.getAccountId());
            }

            else{
                log.info("sucessfully values are inserted in account:{}",account.getAccountId());
            }

        } catch (SQLException e) {
            throw new DataException("Failed to insert",e);
        }
    }

    public List<Account> getAccountsByCustomerId(long customerID) throws SQLException {
        List<Account> AccountList=new ArrayList<>();
        try(Connection con= DbConfig.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(GetByCustomerIdSQL)){

            ps.setLong(GET_ACCOUNT_BY_CUSTOMERID,customerID);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                AccountList.add(mapping(rs));
            }
            log.info("successfully fetched customer details,count={}", AccountList.size());
            return  AccountList;
        } catch (SQLException e) {
            throw new DataException("Failed to fetch customer details",e);
        }
    }

    public Account mapping(ResultSet rs) throws SQLException {
        Account account =new Account();

        account.setAccountId(rs.getLong("account_id"));
        account.setCustomerId(rs.getLong("customer_id"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setAccountStatus(rs.getString("account_status"));
        account.setCreatedat(rs.getDate("createdat").toLocalDate());
        account.setAmount(rs.getLong("amount"));
        account.setTenure(rs.getInt("tenure"));
        return account;
    }
}