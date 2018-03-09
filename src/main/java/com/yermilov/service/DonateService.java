package com.yermilov.service;

import com.j256.ormlite.stmt.query.In;
import com.yermilov.admin.command.ChangeUserStateCommand;
import com.yermilov.dao.*;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  Service for making donation from user to campaign
 *  @see ChangeUserStateCommand
 */
public class DonateService {
    private final static Logger LOGGER = LoggerFactory.getLogger(DonateService.class);
    private final static DonateService DONATE_SERVICE = new DonateService();
    private IDAOFactory daoFactory;
    private DonateService(){
        daoFactory= DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static DonateService getDonateService(){
        return DONATE_SERVICE;
    }

    public boolean donateMoney(long userId, int campaignId,int money, String comment) throws DAOException {
        DonationDAO donationDAO = DAOFactory.getInstance().getDonationDAO();
        donationDAO.create(userId, campaignId, money, comment);
        return true;
    }

}
