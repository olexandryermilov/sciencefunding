package com.yermilov.service;

import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.dao.UserDAO;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service for authorization
 * @see com.yermilov.command.LoginCommand
 */
public class LoginService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final static LoginService LOGIN_SERVICE = new LoginService();
    private IDAOFactory daoFactory;
    private LoginService(){
        daoFactory=DAOFactory.getInstance();
    }

    /**
     *
     * @return Instance of this class
     */
    public static LoginService getLoginService(){
        return LOGIN_SERVICE;
    }

    /**
     *
     * @param email Email that user entered
     * @param password Password that user entered (not encrypted)
     * @return User if there exists a user with such email and password, null otherwise
     */
    public User getUser(String email, String password) throws DAOException, LoginException {
        UserDAO userDAO = daoFactory.getUserDAO();
        User user = userDAO.queryForEmail(email);
        if(user==null){
            return null;
        }
        if(user.getIsActive()==0){
            throw new LoginException("User is deleted, please contact admin to learn how to recover your profile.");
        }
        LOGGER.info("User "+email+" tried to login.");
        return (password.equals(user.getPassword()))?user:null;
    }
    public void setDaoFactory(IDAOFactory daoFactory){
        this.daoFactory=daoFactory;
    }
}
