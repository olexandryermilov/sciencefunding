package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.yermilov.domain.Admin;
import com.yermilov.domain.Scientist;
import com.yermilov.domain.User;
import com.yermilov.transaction.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DAOFactory implements IDAOFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(DAOFactory.class);
    private final static DAOFactory DAO_FACTORY = new DAOFactory();
    public static DAOFactory getInstance() {
        return DAO_FACTORY;
    }

    private AdminDAO adminDao;
    private UserDAO userDao;
    private ScientistDAO scientistDAO;
    private DAOFactory(){
        try{
            adminDao = new AdminDAO();
            userDao = new UserDAO();
            scientistDAO = new ScientistDAO();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public AdminDAO getAdminDAO(){
        return adminDao;
    }

    @Override
    public UserDAO getUserDAO(){
        return userDao;
    }

    @Override
    public ScientistDAO getScientistDAO() {
        return scientistDAO;
    }

    public void setScientistDAO(ScientistDAO scientistDAO) {
        this.scientistDAO = scientistDAO;
    }

    public void setAdminDao(AdminDAO adminDao) {
        this.adminDao = adminDao;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }
}
