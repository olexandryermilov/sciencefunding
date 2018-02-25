package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.yermilov.domain.*;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;
import java.util.List;

/**
 * Wrapper for dao provided by ORMLite
 */
public class OrganisationDAO {
    private Dao<Organisation, Integer> organisationDao;

    public OrganisationDAO() throws SQLException {
        organisationDao = DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Organisation.class);
    }
    public void setOrganisationDao(Dao<Organisation,Integer> organisationDao) {
        this.organisationDao = organisationDao;
    }
    public int create(Organisation organisation) throws DAOException {
        try {
            return organisationDao.create(organisation);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Dao<Organisation, Integer> getOrganisationDao() {
        return organisationDao;
    }
}
