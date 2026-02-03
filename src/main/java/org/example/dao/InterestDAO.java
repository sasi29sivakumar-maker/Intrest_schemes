package org.example.dao;

import org.example.Exception.DataException;
import org.example.dbconfiguration.DbConfig;
import org.example.model.Account;
import org.example.model.Interest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterestDAO {


    private static final Logger log= LoggerFactory.getLogger(InterestDAO.class);

    static final int INSERT_ACCOUNT_ID=1;
    static final int INSERT_INTEREST_AMOUNT=2;

    static  final int GET_INTEREST_BY_ACCOUNTID=1;

    String InsertSQL="Insert into account(account_id,interest_amount)" +
            "VALUES(?,?)";

    String GetByAccountIdSQL = "SELECT * FROM interest WHERE account_id = ?";

    public void insert(Interest interest){

        try(Connection con= DbConfig.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(InsertSQL)){

            ps.setLong(INSERT_ACCOUNT_ID,interest.getAccountId());
            ps.setLong(INSERT_INTEREST_AMOUNT,interest.getInterestamount());

            int changedRows=ps.executeUpdate();
            if(changedRows==0){
                log.error("Insert failed,fields are not inserted in account:{}",interest.getInterestId());
            }
            else{
                log.info("sucessfully values are inserted in account:{}",interest.getInterestId());
            }
        } catch (SQLException e) {
            throw new DataException("Failed to insert",e);
        }
    }

    public List<Interest> getInterestsByAccountId(long accountID) throws SQLException {
        List<Interest> InterestList =new ArrayList<>();
        try(Connection con= DbConfig.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(GetByAccountIdSQL)){

            ps.setLong(GET_INTEREST_BY_ACCOUNTID,accountID);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                InterestList.add(mapping(rs));
            }
            log.info("successfully fetched customer details,count={}", InterestList.size());
            return InterestList;
        } catch (SQLException e) {
            throw new DataException("Failed to fetch customer details",e);
        }
    }

    public Interest mapping(ResultSet rs) throws SQLException {
        Interest interest =new Interest();

        interest.setInterestId(rs.getLong("interest_id"));
        interest.setAccountId(rs.getLong("account_id"));
        interest.setInterestamount(rs.getLong("interest_amount"));
        return interest;
    }
}