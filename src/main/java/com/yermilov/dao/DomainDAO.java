package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.yermilov.domain.Domain;
import com.yermilov.domain.Organisation;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;

/**
 * Wrapper for dao provided by ORMLite
 */
public class DomainDAO {
    private Dao<Domain, Integer> domainDao;

    public DomainDAO() throws SQLException {
        domainDao = DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Domain.class);
    }
    public void setDomainDao(Dao<Domain,Integer> domainDao) {
        this.domainDao = domainDao;
    }
    public int create(Domain domain) throws DAOException {
        try {
            return domainDao.create(domain);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Dao<Domain, Integer> getDomainDao() {
        return domainDao;
    }
}
