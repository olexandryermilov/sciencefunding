package com.yermilov.service;

import com.yermilov.dao.ormlite.CampaignDAOORMLite;
import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.DonationDAO;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.domain.Campaign;
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
     * @throws DAOException Re-throws DAOException from CampaignDAOORMLite
     *  UserDAO#findSize()
     */
    public long getTableSize(boolean isActive) throws DAOException {
        CampaignDAOORMLite campaignDAO = daoFactory.getCampaignDAO();
        return campaignDAO.getSize(isActive);
    }



    /**
     *
     * @param from How much records to skip
     * @param limit How much records should be returned
     * @return List of campaigns to display
     * @throws DAOException Re-throws DAOException from CampaignDAOORMLite
     * @see CampaignDAOORMLite#getLimitedAmountOfCampaigns(int, int)
     */
    public List<Campaign> getCampaigns(int from, int limit, boolean onlyActive) throws DAOException{
        CampaignDAOORMLite campaignDAO = daoFactory.getCampaignDAO();
        List<Campaign> campaigns=(onlyActive)?campaignDAO.getLimitedAmountOfActiveCampaigns(limit,from)
                                             :campaignDAO.getLimitedAmountOfCampaigns(limit,from);
        return campaigns;
    }

    public Campaign getCampaign(int campaignId) throws DAOException {
        CampaignDAOORMLite campaignDAO = daoFactory.getCampaignDAO();
        return campaignDAO.queryForId(campaignId);
    }

    public int getRaisedMoneyForCampaign(int campaignId) throws DAOException{
        DonationDAO donationDAO = daoFactory.getDonationDAO();
        return donationDAO.getMoneyForCampaign(campaignId);
    }
    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
