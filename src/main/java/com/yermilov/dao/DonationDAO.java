package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yermilov.domain.*;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

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
    public List<Donation> getDonationsLike(String text,User user) throws DAOException {
        try {
            List<Donation>donations = donationDao.queryForAll();
            donations.forEach(donation->{
                try {
                    DAOFactory.getInstance().getCampaignDAO().getCampaignDao().refresh(donation.getToCampaign());
                } catch (SQLException e) {

                }
                try {
                    DAOFactory.getInstance().getUserDAO().getUserDao().refresh(donation.getFromUser());
                } catch (SQLException e) {

                }
            });
                    return donations.stream().filter(donation -> (donation.getComment().toLowerCase().contains(text)
                    ||donation.getFromUser().getEmail().toLowerCase().contains(text)
                    ||donation.getToCampaign().getName().toLowerCase().contains(text)
                    ||String.valueOf(donation.getValue()).contains(text))&&(user==null|| user.getId().equals(donation.getFromUser().getId()))).collect(Collectors.toList());
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
            return donationDao.query(pq).stream().mapToInt(Donation::getValue).sum();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

    }

    public long getSize() throws DAOException{
        try {
            return donationDao.countOf();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public long getSize(long userId) throws DAOException{
        try {
            QueryBuilder<Donation, Integer> queryBuilder = donationDao.queryBuilder();
            queryBuilder.setCountOf(true);
            PreparedQuery<Donation> preparedQuery =queryBuilder.where().eq("from_user_id",userId).prepare();
            return donationDao.countOf(preparedQuery);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public List<Donation> getLimitedAmountOfDonations(int limit, int skip) throws DAOException {
        try {
            QueryBuilder<Donation, Integer> queryBuilder = donationDao.queryBuilder();
            PreparedQuery<Donation> preparedQuery =queryBuilder.
                    limit((long) limit).offset((long) (skip)).prepare();
            List<Donation> donations =donationDao.query(preparedQuery);
            donations.forEach(donation -> {
                try {
                    DAOFactory.getInstance().getUserDAO().getUserDao().refresh(donation.getFromUser());
                    DAOFactory.getInstance().getCampaignDAO().getCampaignDao().refresh(donation.getToCampaign());
                } catch (SQLException e) {

                }
            });
            return donations;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public List<Donation> getLimitedAmountOfDonations(int limit, int skip,long userId) throws DAOException {
        try {
            QueryBuilder<Donation, Integer> queryBuilder = donationDao.queryBuilder();
            PreparedQuery<Donation> preparedQuery =queryBuilder.limit((long) limit).offset((long) (skip)).
                    where().eq("from_user_id",userId).prepare();
            List<Donation> donations =donationDao.query(preparedQuery);
            System.out.println(donations.size());
            donations.forEach(donation -> {
                try {
                    DAOFactory.getInstance().getUserDAO().getUserDao().refresh(donation.getFromUser());
                    DAOFactory.getInstance().getCampaignDAO().getCampaignDao().refresh(donation.getToCampaign());
                    System.out.println(donation);
                } catch (SQLException ignored) {
                }
            });
            return donations;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Dao<Donation, Integer> getDonationDao() {
        return donationDao;
    }
}
