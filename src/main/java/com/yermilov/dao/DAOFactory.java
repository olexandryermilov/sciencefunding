package com.yermilov.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DAOFactory implements IDAOFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(DAOFactory.class);
    private final static DAOFactory DAO_FACTORY = new DAOFactory();
    public static DAOFactory getInstance() {
        return DAO_FACTORY;
    }

    private AdminDAO adminDAO;
    private UserDAO userDAO;
    private ScientistDAO scientistDAO;
    private CampaignDAO campaignDAO;
    private OrganiserDAO organiserDAO;
    private OrganisationDAO organisationDAO;
    private DomainDAO domainDAO;
    private DonationDAO donationDAO;
    public DAOFactory(){
        try{
            adminDAO = new AdminDAO();
            userDAO = new UserDAO();
            scientistDAO = new ScientistDAO();
            campaignDAO=new CampaignDAO();
            organiserDAO =new OrganiserDAO();
            organisationDAO = new OrganisationDAO();
            domainDAO=new DomainDAO();
            donationDAO = new DonationDAO();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public AdminDAO getAdminDAO(){
        return adminDAO;
    }

    @Override
    public UserDAO getUserDAO(){
        return userDAO;
    }

    @Override
    public ScientistDAO getScientistDAO() {
        return scientistDAO;
    }

    @Override
    public OrganiserDAO getOrganiserDAO() {
        return organiserDAO;
    }

    @Override
    public CampaignDAO getCampaignDAO() {
        return campaignDAO;
    }

    @Override
    public OrganisationDAO getOrganisationDAO() {
        return organisationDAO;
    }

    @Override
    public DomainDAO getDomainDAO() {
        return domainDAO;
    }

    @Override
    public DonationDAO getDonationDAO() {
        return donationDAO;
    }

    public void setDonationDAO(DonationDAO donationDAO) {
        this.donationDAO = donationDAO;
    }

    public void setDomainDAO(DomainDAO domainDAO) {
        this.domainDAO = domainDAO;
    }

    public void setOrganisationDAO(OrganisationDAO organisationDAO) {
        this.organisationDAO = organisationDAO;
    }

    public void setScientistDAO(ScientistDAO scientistDAO) {
        this.scientistDAO = scientistDAO;
    }

    public void setAdminDAO(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    public void setOrganiserDAO(OrganiserDAO organiserDAO) {
        this.organiserDAO = organiserDAO;
    }
}
