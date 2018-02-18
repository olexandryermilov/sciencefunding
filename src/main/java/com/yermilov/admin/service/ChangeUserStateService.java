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
public class ChangeUserStateService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChangeUserStateService.class);
    private final static ChangeUserStateService CHANGE_USER_STATE_SERVICE = new ChangeUserStateService();
    private IDAOFactory daoFactory;
    private ChangeUserStateService(){
        daoFactory= DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static ChangeUserStateService getChangeUserStateService(){
        return CHANGE_USER_STATE_SERVICE;
    }

    /**
     * Deletes user and related to him client and driver entities from database
     * @param idToDelete Id of user that admin tried to delete
     * @return true if deletes successfully
     * @throws DAOException Re-throws DAOException from UserDAO, ClientDAO or DriverDAO methods
     */
    public boolean delete(int idToDelete) throws DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        userDAO.changeUserState(idToDelete);
        return true;
    }
}
