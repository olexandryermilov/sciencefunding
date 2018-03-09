package com.yermilov.tableworkers;

import com.j256.ormlite.table.TableUtils;
import com.yermilov.dao.DAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class TableCleaner {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableCleaner.class);
    public static void cleanUserTable(){
        try {
            TableUtils.dropTable(DAOFactory.getInstance().getUserDAO().getUserDao(),true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
    public static void cleanAdminTable(){
        try {
            TableUtils.dropTable(DAOFactory.getInstance().getAdminDAO().getAdminDao(),true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
    public static void cleanOrganisationTable(){
        try {
            TableUtils.dropTable(DAOFactory.getInstance().getOrganisationDAO().getOrganisationDao(),true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void cleanOrganiserTable(){
        try {
            TableUtils.dropTable(DAOFactory.getInstance().getOrganiserDAO().getOrganiserDao(),true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void cleanScientistTable(){
        try {
            TableUtils.dropTable(DAOFactory.getInstance().getScientistDAO().getScientistDao(),true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
    public static void cleanDomainTable(){
        try {
            TableUtils.dropTable(DAOFactory.getInstance().getDomainDAO().getDomainDao(),true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void cleanCampaignTable(){
        try {
            TableUtils.dropTable(DAOFactory.getInstance().getCampaignDAO().getCampaignDao(),true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void cleanDonationTable(){
        try {
            TableUtils.dropTable(DAOFactory.getInstance().getDonationDAO().getDonationDao(),true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
