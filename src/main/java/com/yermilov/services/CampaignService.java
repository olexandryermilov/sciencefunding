package com.yermilov.services;

import com.yermilov.dao.CampaignDAO;
import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.dao.UserDAO;
import com.yermilov.domain.Campaign;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service for getting specific amount of users to display it
 * @see com.yermilov.admin.command.UsersCommand
 */
public class CampaignService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);
    private final static CampaignService CAMPAIGN_SERVICE = new CampaignService();
    private IDAOFactory daoFactory = DAOFactory.getInstance();
    private CampaignService(){
        daoFactory = DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static CampaignService getCampaignService(){
        return CAMPAIGN_SERVICE;
    }

    /**
     *
     * @return Total number of user records in database
     * @throws DAOException Re-throws DAOException from UserDAO
     *  UserDAO#findSize()
     */
    public long getTableSize() throws DAOException {
        CampaignDAO campaignDAO = daoFactory.getCampaignDAO();
        return campaignDAO.getSize();
    }

    /**
     *
     * @param from How much records to skip
     * @param limit How much records should be returned
     * @return List of users to display
     * @throws DAOException Re-throws DAOException from CampaignDAO
     * @see CampaignDAO#getLimitedAmountOfCampaigns(int, int)
     */
    public List<Campaign> getCampaigns(int from, int limit) throws DAOException{
        CampaignDAO campaignDAO = daoFactory.getCampaignDAO();
        List<Campaign> campaigns=campaignDAO.getLimitedAmountOfCampaigns(limit,from);
        return campaigns;
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
