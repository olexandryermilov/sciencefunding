package com.yermilov.service;

import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.dao.UserDAO;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.exception.RegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service for authorization
 * @see com.yermilov.command.LoginCommand
 */
public class RegistrationService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);
    private final static RegistrationService REGISTRATION_SERVICE = new RegistrationService();
    private IDAOFactory daoFactory;
    private RegistrationService(){
        daoFactory=DAOFactory.getInstance();
    }

    /**
     *
     * @return Instance of this class
     */
    public static RegistrationService getRegistrationService(){
        return REGISTRATION_SERVICE;
    }

    /**
     *
     * @param email Email that user entered
     * @param password Password that user entered (not encrypted)
     * @param name Name of user
     * @param surname Surname of user
     * @throws RegistrationException When smth went wrong
     */
    public void register(String email, String password, String name, String surname) throws RegistrationException {
        UserDAO userDAO = daoFactory.getUserDAO();
        User user = null;
        try {
            user = userDAO.queryForEmail(email);
            if(user!=null){
                throw new RegistrationException("Email already occupied");
            }
            user = new User(email,password,name,surname);
            userDAO.create(user);
            LOGGER.info("User "+email+" registered.");
        } catch (DAOException e) {
            throw new RegistrationException(e.getMessage());
        }

    }
    public void setDaoFactory(IDAOFactory daoFactory){
        this.daoFactory=daoFactory;
    }
}
