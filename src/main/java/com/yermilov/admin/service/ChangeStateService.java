package com.yermilov.admin.service;

import com.yermilov.admin.command.ChangeUserStateCommand;
import com.yermilov.dao.*;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Service for changing state of user's activeness
 *  @see ChangeUserStateCommand
 */
public class ChangeStateService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChangeStateService.class);
    private final static ChangeStateService CHANGE_STATE_SERVICE = new ChangeStateService();
    private IDAOFactory daoFactory;
    private ChangeStateService(){
        daoFactory= DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static ChangeStateService getChangeStateService(){
        return CHANGE_STATE_SERVICE;
    }

    /**
     * Deletes user and related to him client and driver entities from database
     * @param idToDelete Id of user that admin tried to delete
     * @return true if deletes successfully
     * @throws DAOException Re-throws DAOException from UserDAO, ClientDAO or DriverDAO methods
     */
    public boolean deleteUser(int idToDelete) throws DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        userDAO.changeUserState(idToDelete);
        return true;
    }

    public boolean deleteCampaign(int idToDelete) throws DAOException {
        CampaignDAO campaignDAO = daoFactory.getCampaignDAO();
        campaignDAO.changeCampaignState(idToDelete);
        return true;
    }
}
