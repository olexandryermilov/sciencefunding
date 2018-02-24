package com.yermilov.tableworkers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.yermilov.dao.DAOFactory;
import com.yermilov.domain.User;
import com.yermilov.transaction.DatabaseConnector;
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
}
