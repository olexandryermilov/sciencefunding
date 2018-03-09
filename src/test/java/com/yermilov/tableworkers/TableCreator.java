package com.yermilov.tableworkers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.yermilov.dao.DAOFactory;
import com.yermilov.domain.*;
import com.yermilov.transaction.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableCreator.class);
    private static List<User> users;
    private static List<Admin> admins;
    private static List<Scientist> scientists;
    private static List<Organisation> organisations;
    private static List<Domain> domains;
    private static List<Organiser> organisers;
    private static List<Campaign> campaigns;
    private static List<Donation> donations;
    public static List<User> initUserTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),User.class);
            users= new ArrayList<>();
            users.add(new User("petroivanov@gmail.com","pass1","Petro", "Ivanov"));
            users.add(new User("yuriikovalenko@gmail.com","qwerty","Yurii", "Kovalenko"));
            users.add(new User("olexandryermilov@gmail.com","root","Olexandr", "Yermilov"));
            users.add(new User("ivansidorenko@gmail.com","poiuyt","Ivan", "Sidorenko"));
            users.get(2).setIsActive(0);
            Dao<User,Long> userDAO = DAOFactory.getInstance().getUserDAO().getUserDao();
            for(User user:users){
                userDAO.create(user);
                userDAO.refresh(user);
            }
            return users;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
    public static List<Admin> initAdminTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Admin.class);
            admins= new ArrayList<>();
            admins.add(new Admin("olexandryermilov@gmail.com","root"));
            admins.add(new Admin("ivansidorenko@gmail.com","poiuyt"));
            Dao<Admin,Long> adminDAO = DAOFactory.getInstance().getAdminDAO().getAdminDao();
            for(Admin admin:admins){
                adminDAO.create(admin);
                adminDAO.refresh(admin);
            }
            return admins;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
    public static List<Organisation> initOrganisationTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Organisation.class);
            organisations = new ArrayList<>();
            organisations.add(new Organisation("NAU",domains.get(0)));
            organisations.add(new Organisation("KNU",domains.get(1)));
            Dao<Organisation,Integer> organisationDAO = DAOFactory.getInstance().getOrganisationDAO().getOrganisationDao();
            for(Organisation organisation: organisations){
                organisationDAO.create(organisation);
                organisationDAO.refresh(organisation);
            }
            return organisations;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
    public static List<Scientist> initScientistTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Scientist.class);
            scientists= new ArrayList<>();
            scientists.add(new Scientist(users.get(0), domains.get(0),organisations.get(0)));
            scientists.add(new Scientist(users.get(1), domains.get(0),organisations.get(0)));
            scientists.add(new Scientist(users.get(0), domains.get(1),organisations.get(1)));
            Dao<Scientist, Long> scientistDAO = DAOFactory.getInstance().getScientistDAO().getScientistDao();
            for(Scientist scientist:scientists){
                scientistDAO.create(scientist);
                scientistDAO.refresh(scientist);
            }
            return scientists;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Domain> initDomainTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Domain.class);
            domains= new ArrayList<>();
            domains.add(new Domain("Physics"));
            domains.add(new Domain("Maths"));
            Dao<Domain, Integer> scientistDAO = DAOFactory.getInstance().getDomainDAO().getDomainDao();
            for(Domain domain :domains){
                scientistDAO.create(domain);
                scientistDAO.refresh(domain);
            }
            return domains;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Organiser> initOrganiserTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Organiser.class);
            organisers= new ArrayList<>();
            organisers.add(new Organiser(scientists.get(0)));
            organisers.add(new Organiser(scientists.get(1)));
            organisers.add(new Organiser(scientists.get(2)));
            organisers.add(new Organiser(organisations.get(0)));
            organisers.add(new Organiser(organisations.get(1)));
            Dao<Organiser, Integer> organiserDAO = DAOFactory.getInstance().getOrganiserDAO().getOrganiserDao();
            for(Organiser organiser :organisers){
                organiserDAO.create(organiser);
                organiserDAO.refresh(organiser);
            }
            return organisers;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Campaign> initCampaignTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Campaign.class);
            campaigns= new ArrayList<>();
            campaigns.add(new Campaign(organisers.get(2),10000,"For Algorithms",domains.get(1),"For lectures"));
            campaigns.add(new Campaign(organisers.get(3),11000,"Fraud",domains.get(0),"Give me all your money"));
            campaigns.add(new Campaign(organisers.get(1),100000,"For Lectures",domains.get(1),"For lectures"));
            campaigns.add(new Campaign(organisers.get(4),100000,"For KNU",domains.get(1),"For money"));
            campaigns.get(1).setIsActive(0);
            Dao<Campaign, Integer> campaignDAO = DAOFactory.getInstance().getCampaignDAO().getCampaignDao();
            for(Campaign campaign :campaigns){
                campaignDAO.create(campaign);
                campaignDAO.refresh(campaign);
            }
            return campaigns;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Donation> initDonationTable(){
        try {
            TableUtils.createTableIfNotExists(DatabaseConnector.getInstance().getORMLiteConnectionSource(),Donation.class);
            donations= new ArrayList<>();
            Dao<Donation, Integer> donationDAO = DAOFactory.getInstance().getDonationDAO().getDonationDao();
            donations.add(new Donation(users.get(0),campaigns.get(0),100, "Thanks"));
            donations.add(new Donation(users.get(1),campaigns.get(0),150, "Thanks"));
            donations.add(new Donation(users.get(1),campaigns.get(1),100, "Thanks"));
            donations.add(new Donation(users.get(2),campaigns.get(1),1000, "Thanks"));
            donations.add(new Donation(users.get(2),campaigns.get(0),13, "Thanks"));
            for(Donation donation : donations){
                donationDAO.create(donation);
                donationDAO.refresh(donation);
            }
            return donations;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

}
