package dao;

import db.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
    public static boolean doesExist(String email) throws Exception
    {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT EMAIL FROM USERS");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String getEmail = rs.getString(1);
            if (getEmail.equals(email)){
                return true;
            }
        }
        return false;
    }

    public static int saveUser(User user) throws Exception{
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS VALUES (default, ?, ? )");
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        return ps.executeUpdate();
    }
}
