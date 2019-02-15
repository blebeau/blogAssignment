/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author c0592682
 */

@Named
@ApplicationScoped
public class users {
    
    private List<User> users;
    private static users instance;
    
    public users() {
        getUsersFromDatabase();
        instance = this;
    }
    
    public List<User> getUsers(){
        return users;
    }
    
    public String getUsernameById(int id) {
        for (User u : users){
            if (u.getId() == id) {
                return u.getUsername();
            }
        }
        return null;
    }
    
    
    private void getUsersFromDatabase() {
    try {
    Connection conn = DBUtils.getConnection();
    Statement stmt;
    stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM users");
    users = new ArrayList<>();
    while (rs.next()) {
        User u = new User(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("passhash")
        );
        users.add(u);
    }
    } catch (SQLException ex) {
            Logger.getLogger(users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
