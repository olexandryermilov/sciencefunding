package com.yermilov.admin.service;


import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.DonationDAO;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.domain.Donation;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service for getting specific amount of donations to display it
 * @see DonationsCommand
 */
public class DonationService {
    private final static Logger LOGGER = LoggerFactory.getLogger(DonationService.class);
    private final static DonationService DONATION_SERVICE = new DonationService();
    private IDAOFactory daoFactory = DAOFactory.getInstance();
    private DonationService(){
        daoFactory = DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static DonationService getDonationService(){
        return DONATION_SERVICE;
    }

    /**
     *
     * @return Total number of user records in database
     * @throws DAOException Re-throws DAOException from DonationDAO
     * @see DonationDAO#getSize()
     */
    public long getTableSize() throws DAOException {
        return DAOFactory.getInstance().getDonationDAO().getSize();
    }

    /**
     *
     * @param from How much records to skip
     * @param limit How much records should be returned
     * @return List of donations to display
     * @throws DAOException Re-throws DAOException from DonationDAO
     * @see DonationDAO#getLimitedAmountOfDonations(int, int)
     */
    public List<Donation> getDonations(int from, int limit) throws DAOException{
        DonationDAO donationDAO = daoFactory.getDonationDAO();
        List<Donation> donations=donationDAO.getLimitedAmountOfDonations(limit,from);
        return donations;
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
