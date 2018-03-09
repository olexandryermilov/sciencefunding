package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.yermilov.domain.*;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;
import java.util.List;

/**
 * Wrapper for dao provided by ORMLite
 */
public class CampaignDAO {
    private Dao<Campaign, Integer> campaignDao;

    public CampaignDAO() throws SQLException {
        campaignDao = DaoManager.createDao(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Campaign.class);
    }
    public void setCampaignDao(Dao<Campaign,Integer> campaignDao) {
        this.campaignDao = campaignDao;
    }
    public int create(Campaign campaign) throws DAOException {
        try {
            return campaignDao.create(campaign);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public int create(User user, int needToRaise, String name, int domainId, String description) throws DAOException {
        try {
            Dao<Scientist, Long> scientistDao = DAOFactory.getInstance().getScientistDAO().getScientistDao();
            QueryBuilder<Scientist,Long> scientistQueryBuilder = scientistDao.queryBuilder();
            scientistQueryBuilder.where().eq("user_id",user.getId());
            Dao<Organiser, Integer> organiserDao = DAOFactory.getInstance().getOrganiserDAO().getOrganiserDao();
            QueryBuilder<Organiser,Integer> organiserQueryBuilder = organiserDao.queryBuilder();
            Organiser organiser = organiserQueryBuilder.join(scientistQueryBuilder).queryForFirst();
            Domain domain = DAOFactory.getInstance().getDomainDAO().queryForId(domainId);
            return create(new Campaign(organiser,needToRaise,name,domain,description));
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public long getSize() throws DAOException{
        try {
            return campaignDao.countOf();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public List<Campaign> getLimitedAmountOfActiveCampaigns(int limit, int skip) throws DAOException {
        try {
            QueryBuilder<Campaign, Integer> queryBuilder = campaignDao.queryBuilder();
            PreparedQuery<Campaign> preparedQuery =queryBuilder.
                    limit((long) limit).offset((long) (skip)).where().eq(Campaign.IS_ACTIVE_FIELD_NAME,1).prepare();
            return campaignDao.query(preparedQuery);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public List<Campaign> getLimitedAmountOfCampaigns(int limit, int skip) throws DAOException {
        try {
            QueryBuilder<Campaign, Integer> queryBuilder = campaignDao.queryBuilder();
            PreparedQuery<Campaign> preparedQuery =queryBuilder.
                    limit((long) limit).offset((long) (skip)).prepare();
            return campaignDao.query(preparedQuery);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public int changeCampaignState(int campaignid) throws DAOException {
        try{
            QueryBuilder<Campaign, Integer> queryBuilder = campaignDao.queryBuilder();
            PreparedQuery<Campaign>preparedQuery = queryBuilder.where().eq("id",campaignid).prepare();
            Campaign campaign = queryBuilder.queryForFirst();
            UpdateBuilder<Campaign, Integer> updateBuilder =campaignDao.updateBuilder();
            updateBuilder.updateColumnValue(Campaign.IS_ACTIVE_FIELD_NAME, 1-campaign.getIsActive());
            updateBuilder.where().idEq(campaignid);
            updateBuilder.update();
            return 1;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Campaign queryForId(int campaignId) throws DAOException {
        try{
            return campaignDao.queryForId(campaignId);
        }catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public Dao<Campaign, Integer> getCampaignDao() {
        return campaignDao;
    }
}
