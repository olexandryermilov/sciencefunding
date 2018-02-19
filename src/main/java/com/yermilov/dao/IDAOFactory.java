package com.yermilov.dao;

public interface IDAOFactory {
    UserDAO getUserDAO();
    AdminDAO getAdminDAO();
    ScientistDAO getScientistDAO();
    OrganiserDAO getOrganiserDAO();
    CampaignDAO getCampaignDAO();
}
