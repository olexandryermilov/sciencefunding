package com.yermilov.dao;

import com.yermilov.dao.ormlite.CampaignDAOORMLite;

public interface IDAOFactory {
    UserDAO getUserDAO();
    AdminDAO getAdminDAO();
    ScientistDAO getScientistDAO();
    OrganiserDAO getOrganiserDAO();
    CampaignDAOORMLite getCampaignDAO();
    OrganisationDAO getOrganisationDAO();
    DomainDAO getDomainDAO();
    DonationDAO getDonationDAO();
}
