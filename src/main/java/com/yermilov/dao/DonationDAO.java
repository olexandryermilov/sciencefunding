package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yermilov.domain.*;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;
import java.util.function.ToIntFunction;

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

    public int create(long userId, int campaignId, int money, String comment) throws DAOException {
        User user = DAOFactory.getInstance().getUserDAO().queryForId(userId);
        Campaign campaign = DAOFactory.getInstance().getCampaignDAO().queryForId(campaignId);
        return create(new Donation(user, campaign, money, comment));
    }

    public int getMoneyForCampaign(int campaignId) throws DAOException {
        try {
            PreparedQuery<Donation> pq = donationDao.queryBuilder().where().eq("to_campaign_id",campaignId).prepare();
            //System.out.println(donationDao.query(pq));
            return donationDao.query(pq).stream().mapToInt(Donation::getValue).sum();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

    }

    public Dao<Donation, Integer> getDonationDao() {
        return donationDao;
    }
}
