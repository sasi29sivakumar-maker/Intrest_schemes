package org.example.dao;
import org.example.Exception.DataException;
import org.example.dbconfiguration.DbConfig;
import org.example.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO {

    private static final Logger log= LoggerFactory.getLogger(CustomerDAO.class);

    static final int INSERT_USER_ID=1;
    static final int INSERT_CUSTOMER_NAME=2;
    static final int INSERT_GENDER=3;
    static final int INSERT_PHONE_NO=4;
    static final int INSERT_EMAIL=5;
    static final int INSERT_ADDRESS=6;
    static final int INSERT_AADHAR_NO=7;

    String InsertSQL="Insert into customer(user_id,customer_name,gender,phone_no,email,address,aadhar_no)" +
            "VALUES(?,?,?,?,?,?,?)";

    String SelectAllSQL="Select * from customer";


    public void insert(Customer customer){

        try(Connection con= DbConfig.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(InsertSQL)){
            ps.setLong(INSERT_USER_ID,customer.getUserId());
            ps.setString(INSERT_CUSTOMER_NAME,customer.getCustomerName());
            ps.setString(INSERT_GENDER,customer.getGender());
            ps.setString(INSERT_PHONE_NO,customer.getPhoneNo());
            ps.setString(INSERT_EMAIL,customer.getEmail());
            ps.setString(INSERT_ADDRESS,customer.getAddress());
            ps.setString(INSERT_AADHAR_NO,customer.getAadharNo());

            int changedRows=ps.executeUpdate();

            if(changedRows==0){
                log.error("Insert failed,fields are not inserted in customer:{}",customer.getCustomerId());
            }

            else{
                log.info("sucessfully values are inserted in customer:{}",customer.getCustomerId());
            }

        } catch (SQLException e) {
            throw new DataException("failed to insert",e);
        }
    }

    public List<Customer> findAll() throws SQLException {
        List<Customer> customerList=new ArrayList<>();
        try(Connection con= DbConfig.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(SelectAllSQL)){

            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                customerList.add(mapping(rs));
            }
            log.info("successfully fetched customer details,count={}", customerList.size());
            return  customerList;
        } catch (SQLException e) {
            throw new DataException("Failed to fetch customer details",e);
        }
    }


    public Customer mapping(ResultSet rs) throws SQLException {
        Customer customer=new Customer();

        customer.setUserId(rs.getLong("user_id"));
        customer.setCustomerId(rs.getLong("customer_id"));
        customer.setCustomerName(rs.getString("customer_name"));
        customer.setGender(rs.getString("gender"));
        customer.setPhoneNo(rs.getString("phone_no"));
        customer.setEmail(rs.getString("email"));
        customer.setAddress(rs.getString("address"));
        customer.setAadharNo(rs.getString("aadhar_no"));
        customer.setCustomerStatus(rs.getString("customer_status"));

        return customer;
    }
}