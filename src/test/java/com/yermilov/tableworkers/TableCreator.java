package com.yermilov.tableworkers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.UserDAO;
import com.yermilov.domain.Admin;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableCreator.class);
    private static List<User> users;
    private static List<Admin> admins;
    public static List<User> initUserTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),User.class);
            users= new ArrayList<>();
            users.add(new User("petroivanov@gmail.com","pass1","Petro", "Ivanov"));
            users.add(new User("yuriikovalenko@gmail.com","qwerty","Yurii", "Kovalenko"));
            users.add(new User("olexandryermilov@gmail.com","root","Olexandr", "Yermilov"));
            users.add(new User("ivansidorenko@gmail.com","poiuyt","Ivan", "Sidorenko"));
            users.get(2).setIsActive(0);
            Dao<User,Long> userDAO = DAOFactory.getInstance().getUserDAO().getUserDao();
            for(User user:users){
                userDAO.create(user);
                userDAO.refresh(user);
            }
            return users;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
    public static List<Admin> initAdminTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Admin.class);
            admins= new ArrayList<>();
            admins.add(new Admin("olexandryermilov@gmail.com","root"));
            admins.add(new Admin("ivansidorenko@gmail.com","poiuyt"));
            Dao<Admin,Long> adminDAO = DAOFactory.getInstance().getAdminDAO().getAdminDao();
            for(Admin admin:admins){
                adminDAO.create(admin);
                adminDAO.refresh(admin);
            }
            return admins;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
    public static List<User> getUsers(){
        return users;
    }
}
