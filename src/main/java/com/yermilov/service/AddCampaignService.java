package com.yermilov.service;

import com.yermilov.dao.CampaignDAO;
import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.domain.*;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCampaignService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddCampaignService.class);
    private final static AddCampaignService ADD_CAMPAIGN_SERVICE = new AddCampaignService();
    private IDAOFactory daoFactory;
    private AddCampaignService(){
        daoFactory= DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static AddCampaignService getAddCampaignService(){
        return ADD_CAMPAIGN_SERVICE;
    }

    public void addCampaign(User user, int needToRaise, String name, int domainId, String description) throws DAOException{
        CampaignDAO campaignDAO = daoFactory.getCampaignDAO();
        campaignDAO.create( user,needToRaise, name, domainId, description);
    }
}
