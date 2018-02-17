package com.yermilov.sciencefunding.transaction;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.yermilov.sciencefunding.dao.DAOFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;

public class DatabaseConnector {
    private final static String DATABASE_URL= "jdbc:mysql://localhost:3306/science_funding?user=root&pass=root&autoReconnect=true&useSSL=false&useUnicode=true" +
            "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private ConnectionSource ORMLiteConnectionSource;
    private final static DatabaseConnector DATABASE_CONNECTOR= new DatabaseConnector();
    public static DatabaseConnector getInstance() {
        return DATABASE_CONNECTOR;
    }
    private DatabaseConnector()  {
        try {
            initConnectionSource();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void initConnectionSource() throws SQLException {
        ORMLiteConnectionSource = new DataSourceConnectionSource(MySQLConnectionPool.getDataSource(),DATABASE_URL);
    }

    public ConnectionSource getORMLiteConnectionSource() {
        return ORMLiteConnectionSource;
    }
}
