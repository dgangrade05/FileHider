package service;

import dao.userDAO;
import model.User;

import java.sql.SQLException;

public class userService {
    public static Integer saveUser(User user){
        try{
            if(userDAO.doesExist(user.getEmail())){
                return 0;
            }
            else{
                return userDAO.saveUser(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
