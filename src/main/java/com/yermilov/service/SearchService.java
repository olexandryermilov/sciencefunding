package com.yermilov.service;

import com.yermilov.dao.*;
import com.yermilov.domain.Campaign;
import com.yermilov.domain.Donation;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.exception.RegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service for authorization
 * @see com.yermilov.command.LoginCommand
 */
public class SearchService {
    private final static Logger LOGGER = LoggerFactory.getLogger(SearchService.class);
    private final static SearchService REGISTRATION_SERVICE = new SearchService();
    private IDAOFactory daoFactory;
    private SearchService(){
        daoFactory=DAOFactory.getInstance();
    }

    /**
     *
     * @return Instance of this class
     */
    public static SearchService getRegistrationService(){
        return REGISTRATION_SERVICE;
    }

    public List<User> searchUser(String text) throws DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        return userDAO.getUsersLike(text);
    }
    public List<Donation> searchDonation(String text, User user) throws DAOException {
        DonationDAO donationDAO = daoFactory.getDonationDAO();
        return donationDAO.getDonationsLike(text,user);
    }

    public List<Campaign> searchCampaign(String text, boolean isActive) throws DAOException{
        CampaignDAO campaignDAO = daoFactory.getCampaignDAO();
        return campaignDAO.getCampaignsLike(text,isActive);
    }
    public void setDaoFactory(IDAOFactory daoFactory){
        this.daoFactory=daoFactory;
    }
}
