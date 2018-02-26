package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.yermilov.domain.Admin;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;

/**
 * Wrapper for dao provided by ORMLite
 */
public class AdminDAO {
    private Dao<Admin, Long> adminDao;

    public AdminDAO() throws SQLException {
        adminDao = DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Admin.class);
    }
    public void setAdminDao(Dao<Admin,Long> adminDao) {
        this.adminDao = adminDao;
    }
    public Dao<Admin,Long> getAdminDao(){
        return this.adminDao;
    }
    public int create(Admin admin) throws DAOException {
        try {
            return adminDao.create(admin);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public Admin queryForEmail(String email) throws DAOException {
        try {
            QueryBuilder<Admin,Long> queryBuilder = adminDao.queryBuilder();
            Where<Admin,Long> where = queryBuilder.where().
                    eq(Admin.EMAIL_FIELD_NAME, email);
            PreparedQuery<Admin> preparedQuery = queryBuilder.prepare();
            return adminDao.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
