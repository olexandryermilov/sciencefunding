package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.yermilov.domain.Domain;
import com.yermilov.domain.Donation;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;

/**
 * Wrapper for dao provided by ORMLite
 */
public class DonationDAO {
    private Dao<Donation, Integer> donationDao;

    public DonationDAO() throws SQLException {
        donationDao = DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Donation.class);
    }
    public void setDonationDao(Dao<Donation,Integer> donationDao) {
        this.donationDao = donationDao;
    }
    public int create(Donation donation) throws DAOException {
        try {
            return donationDao.create(donation);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Dao<Donation, Integer> getDonationDao() {
        return donationDao;
    }
}
