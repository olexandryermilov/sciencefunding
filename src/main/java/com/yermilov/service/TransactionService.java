package com.yermilov.service;

import com.yermilov.admin.command.ChangeUserStateCommand;
import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 *  Service for making donation from user to campaign
 *  @see ChangeUserStateCommand
 */
public class TransactionService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private final static TransactionService DONATE_SERVICE = new TransactionService();
    private IDAOFactory daoFactory;
    private TransactionService(){
        daoFactory= DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static TransactionService getDonateService(){
        return DONATE_SERVICE;
    }

    /**

     */
    public boolean verifyTransaction() throws DAOException {
        return (new Date().getTime()%71!=0);
    }

}
