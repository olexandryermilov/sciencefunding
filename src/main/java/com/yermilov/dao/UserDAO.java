package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.*;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;
import java.util.List;

/**
 * Wrapper for dao provided by ORMLite
 */
public class UserDAO {
    private Dao<User,Long> userDao;

    public UserDAO() throws SQLException {
        userDao = DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),User.class);
    }
    public void setUserDao(Dao<User, Long> userDao) {
        this.userDao = userDao;
    }
    public int create(User user) throws DAOException {
        try {
            return userDao.create(user);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public User queryForEmail(String email) throws DAOException {
        try {
            QueryBuilder<User,Long> queryBuilder = userDao.queryBuilder();
            Where<User,Long> where = queryBuilder.where().
                    eq(User.EMAIL_FIELD_NAME, email);
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();

            return userDao.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public long getSize() throws DAOException{
        try {
            return userDao.countOf();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public List<User> getLimitedAmountOfUsers(int limit, int skip) throws DAOException {
        try {
            QueryBuilder<User,Long> queryBuilder = userDao.queryBuilder();
            PreparedQuery<User> preparedQuery =queryBuilder.limit((long) limit).offset((long) (skip)).prepare();
            return userDao.query(preparedQuery);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public int changeUserState(long userid) throws DAOException {
        try{
            QueryBuilder<User,Long> queryBuilder = userDao.queryBuilder();
            PreparedQuery<User>preparedQuery = queryBuilder.where().eq("id",userid).prepare();
            User user = queryBuilder.queryForFirst();
            UpdateBuilder<User, Long> updateBuilder =userDao.updateBuilder();
            updateBuilder.updateColumnValue(User.IS_ACTIVE_FIELD_NAME, 1-user.getIsActive());
            updateBuilder.where().idEq(userid);
            updateBuilder.update();
            return 1;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public User queryForId(long userid) throws DAOException{
        try {
            return userDao.queryForId(userid);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}

