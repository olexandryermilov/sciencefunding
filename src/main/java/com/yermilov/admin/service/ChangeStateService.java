package com.yermilov.admin.service;

import com.yermilov.admin.command.ChangeUserStateCommand;
import com.yermilov.dao.*;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Service for changing state of user and campign activeness
 *  @see ChangeUserStateCommand
 *  @see com.yermilov.admin.command.ChangeCampaignStateCommand
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
     * Changes state of activeness of user
     * @param idToDelete Id of user
     * @return true if changes successfully
     * @throws DAOException Re-throws DAOException from UserDAO
     */
    public boolean changeUserState(long idToDelete) throws DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        userDAO.changeUserState(idToDelete);
        return true;
    }
    /**
     * Changes state of activeness of user
     * @param idToDelete Id of user
     * @return true if changes successfully
     * @throws DAOException Re-throws DAOException from UserDAO
     */
    public boolean changeCampaignState(int idToDelete) throws DAOException {
        CampaignDAO campaignDAO = daoFactory.getCampaignDAO();
        campaignDAO.changeCampaignState(idToDelete);
        return true;
    }
}
