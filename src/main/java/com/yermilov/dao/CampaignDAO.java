package com.yermilov.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.yermilov.domain.*;
import com.yermilov.exception.DAOException;
import com.yermilov.transaction.DatabaseConnector;

import java.sql.SQLException;

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
            return create(new Campaign(organiser,needToRaise,name,new Domain(),description));
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

}
