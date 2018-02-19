package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.yermilov.domain.Organisation;
import com.yermilov.domain.Organiser;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;

/**
 * Wrapper for dao provided by ORMLite
 */
public class OrganiserDAO {
    private Dao<Organiser, Integer> organiserDao;

    public OrganiserDAO() throws SQLException {
        organiserDao = DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Organiser.class);
    }
    public void setOrganiserDao(Dao<Organiser,Integer> organiserDao) {
        this.organiserDao = organiserDao;
    }
    public int create(Organiser organiser) throws DAOException {
        try {
            return organiserDao.create(organiser);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Dao<Organiser, Integer> getOrganiserDao() {
        return organiserDao;
    }
}
