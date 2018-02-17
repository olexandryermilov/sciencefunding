package com.yermilov.sciencefunding.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.yermilov.sciencefunding.domain.Admin;
import com.yermilov.sciencefunding.exception.DAOException;
import com.yermilov.sciencefunding.transaction.DatabaseConnector;

import java.sql.SQLException;

public class DAOFactory {
    private final static DAOFactory DAO_FACTORY = new DAOFactory();
    public static DAOFactory getInstance() {
        return DAO_FACTORY;
    }
    private DAOFactory(){
    }

    public Dao<Admin,Long> getAdminDAO() throws DAOException {
        try{
            return DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Admin.class);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
