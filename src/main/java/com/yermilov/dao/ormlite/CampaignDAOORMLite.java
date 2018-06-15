package com.yermilov.dao.ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.yermilov.dao.CampaignDAO;
import com.yermilov.dao.DAOFactory;
import com.yermilov.domain.*;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Wrapper for dao provided by ORMLite
 */
public class CampaignDAOORMLite implements CampaignDAO {
    private Dao<Campaign, Integer> campaignDao;

    public CampaignDAOORMLite() throws SQLException {
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

    public long getSize(boolean isActive) throws DAOException{
        try {
            if(!isActive)return campaignDao.countOf();
            else {
                QueryBuilder<Campaign, Integer> queryBuilder = campaignDao.queryBuilder();
                queryBuilder.setCountOf(true);
                PreparedQuery<Campaign> preparedQuery =queryBuilder.where().eq(Campaign.IS_ACTIVE_FIELD_NAME,1).prepare();
                return campaignDao.countOf(preparedQuery);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public List<Campaign> getLimitedAmountOfActiveCampaigns(int limit, int skip) throws DAOException {
        try {
            QueryBuilder<Campaign, Integer> queryBuilder = campaignDao.queryBuilder();
            PreparedQuery<Campaign> preparedQuery =queryBuilder.
                    limit((long) limit).offset((long) (skip)).where().eq(Campaign.IS_ACTIVE_FIELD_NAME,1).prepare();
            List<Campaign> campaigns =campaignDao.query(preparedQuery);
            campaigns.forEach(campaign -> {
                try {
                    DAOFactory.getInstance().getDomainDAO().getDomainDao().refresh(campaign.getDomain());
                    DAOFactory.getInstance().getOrganiserDAO().getOrganiserDao().refresh(campaign.getOrganiser());
                } catch (SQLException e) {

                }
            });
            return campaigns;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    public List<Campaign> getLimitedAmountOfCampaigns(int limit, int skip) throws DAOException {
        try {
            QueryBuilder<Campaign, Integer> queryBuilder = campaignDao.queryBuilder();
            PreparedQuery<Campaign> preparedQuery =queryBuilder.
                    limit((long) limit).offset((long) (skip)).prepare();
            List<Campaign> campaigns =campaignDao.query(preparedQuery);
            campaigns.forEach(campaign -> {
                try {
                    DAOFactory.getInstance().getDomainDAO().getDomainDao().refresh(campaign.getDomain());
                    DAOFactory.getInstance().getOrganiserDAO().getOrganiserDao().refresh(campaign.getOrganiser());
                } catch (SQLException ignored) {
                }
            });
            return campaigns;
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
            Campaign campaign = campaignDao.queryForId(campaignId);
            DAOFactory.getInstance().getOrganiserDAO().getOrganiserDao().refresh(campaign.getOrganiser());
            DAOFactory.getInstance().getScientistDAO().getScientistDao().refresh(campaign.getOrganiser().getScientist());
            if(campaign.getOrganiser().getScientist()!=null)DAOFactory.getInstance().getUserDAO().getUserDao().refresh(campaign.getOrganiser().getScientist().getUser());
            DAOFactory.getInstance().getOrganisationDAO().getOrganisationDao().refresh(campaign.getOrganiser().getOrganisation());
            return campaign;
        }catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public List<Campaign> getCampaignsLike(String text,boolean isActive) throws DAOException {
        try {
            List<Campaign>campaigns = campaignDao.queryForAll();
            campaigns.forEach(campaign->{
                try {
                    DAOFactory.getInstance().getDomainDAO().getDomainDao().refresh(campaign.getDomain());
                } catch (SQLException e) {

                }
                try {
                    DAOFactory.getInstance().getOrganiserDAO().getOrganiserDao().refresh(campaign.getOrganiser());
                    DAOFactory.getInstance().getScientistDAO().getScientistDao().refresh(campaign.getOrganiser().getScientist());
                    DAOFactory.getInstance().getUserDAO().getUserDao().refresh(campaign.getOrganiser().getScientist().getUser());
                } catch (SQLException e) {

                }
            });
            return campaigns.stream().filter(campaign -> (campaign.getOrganiser().toString().toLowerCase().contains(text)
                    ||campaign.getName().toLowerCase().contains(text)
                    ||campaign.getDescription().toLowerCase().contains(text)
                    ||String.valueOf(campaign.getNeedToRaise()).contains(text)
                    ||campaign.getDomain().getName().toLowerCase().contains(text))
                    &&(!isActive ||campaign.getIsActive()==1)).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Dao<Campaign, Integer> getCampaignDao() {
        return campaignDao;
    }
}
