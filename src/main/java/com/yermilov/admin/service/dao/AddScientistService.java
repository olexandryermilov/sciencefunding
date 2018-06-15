package com.yermilov.admin.service.dao;

import com.yermilov.admin.command.ChangeUserStateCommand;
import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.dao.UserDAO;
import com.yermilov.domain.*;
import com.yermilov.exception.AddScientistException;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Service for deleting specific user record from database and related to it Client and Driver records
 *  @see ChangeUserStateCommand
 */
public class AddScientistService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddScientistService.class);
    private final static AddScientistService ADD_SCIENTIST_SERVICE = new AddScientistService();
    private IDAOFactory daoFactory;
    private AddScientistService(){
        daoFactory= DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static AddScientistService getAddScientistService(){
        return ADD_SCIENTIST_SERVICE;
    }

    /**
     * Deletes user and related to him client and driver entities from database
     * @param userid id of user that attempted to become a scientist
     * @return true if deletes successfully
     * @throws DAOException Re-throws DAOException from UserDAO, ClientDAO or DriverDAO methods
     */
    public boolean registerAsScientist(long userid) throws DAOException, AddScientistException {
        UserDAO userDAO = daoFactory.getUserDAO();
        User user = userDAO.queryForId(userid);
        Scientist scientist;
        scientist = daoFactory.getScientistDAO().queryForUserId(userid);
        if(scientist!=null){
            throw new AddScientistException("User is already a scientist");
        }
        scientist = new Scientist();
        scientist.setUser(user);
        daoFactory.getScientistDAO().create(scientist);
        Organiser organiser = new Organiser();
        organiser.setScientist(scientist);
        daoFactory.getOrganiserDAO().create(organiser);
        return true;
    }

    public boolean registerAsScientist(int userid,int domainid, int organisationid) throws DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        User user = userDAO.queryForId(userid);
        Scientist scientist = new Scientist();
        scientist.setUser(user);
        daoFactory.getScientistDAO().create(scientist);
        return true;
    }
}
