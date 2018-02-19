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

    private AdminDAO adminDao;
    private UserDAO userDao;
    private ScientistDAO scientistDAO;
    private CampaignDAO campaignDAO;
    private OrganiserDAO organiserDAO;
    private DAOFactory(){
        try{
            adminDao = new AdminDAO();
            userDao = new UserDAO();
            scientistDAO = new ScientistDAO();
            campaignDAO=new CampaignDAO();
            organiserDAO =new OrganiserDAO();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public AdminDAO getAdminDAO(){
        return adminDao;
    }

    @Override
    public UserDAO getUserDAO(){
        return userDao;
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

    public void setScientistDAO(ScientistDAO scientistDAO) {
        this.scientistDAO = scientistDAO;
    }

    public void setAdminDao(AdminDAO adminDao) {
        this.adminDao = adminDao;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    public void setOrganiserDAO(OrganiserDAO organiserDAO) {
        this.organiserDAO = organiserDAO;
    }
}
