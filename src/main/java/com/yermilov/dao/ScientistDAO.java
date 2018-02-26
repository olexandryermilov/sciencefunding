package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.yermilov.domain.Scientist;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;
import java.util.List;

/**
 * Wrapper for dao provided by ORMLite
 */
public class ScientistDAO {
    private Dao<Scientist,Long> scientistDao;

    public ScientistDAO() throws SQLException {
        scientistDao = DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Scientist.class);
    }
    public void setScientistDao(Dao<Scientist, Long> scientistDao) {
        this.scientistDao = scientistDao;
    }
    public int create(Scientist scientist) throws DAOException {
        try {
            return scientistDao.create(scientist);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public int update(Scientist scientist) throws DAOException{
        try{
           return scientistDao.update(scientist);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Scientist queryForUserId(long userid) throws DAOException{
        try{
            QueryBuilder<Scientist, Long> queryBuilder = scientistDao.queryBuilder();
            queryBuilder.where().eq("user_id",userid);
            PreparedQuery<Scientist> preparedQuery = queryBuilder.prepare();
            Scientist scientist = scientistDao.queryForFirst(preparedQuery);
            if(scientist==null)return null;
            DAOFactory.getInstance().getUserDAO().getUserDao().refresh(scientist.getUser());
            DAOFactory.getInstance().getDomainDAO().getDomainDao().refresh(scientist.getDomain());
            DAOFactory.getInstance().getOrganisationDAO().getOrganisationDao().refresh(scientist.getOrganisation());
            return scientist;
        }catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public Dao<Scientist, Long> getScientistDao() {
        return scientistDao;
    }
}

