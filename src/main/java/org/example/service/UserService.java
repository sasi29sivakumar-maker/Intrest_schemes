package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.util.Hashing;
import org.example.util.Jwt;

public class UserService {

    UserDAO userDAO =new UserDAO();

    public void addUser(User user) {

        String pass = user.getPassword();
        user.setPassword(Hashing.hashPassword(pass));

        userDAO.adduser(user);

    }

    public String verify(String username , String password){
        String hashPassword = userDAO.getPass(username);
        if(hashPassword !=null) {
            boolean isValid = Hashing.verifyPassword(password, hashPassword);
            if (isValid) {
                return Jwt.generateToken(username);
            } else {
                return "Password wrong";
            }
        }
        else {
            return "No username found in this user please register";
        }
    }

}