package com.yermilov.transaction;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class H2ConnectionPool {
    private final static H2ConnectionPool connectionPool = new H2ConnectionPool();
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private ConnectionSource connectionSource;

    private H2ConnectionPool(){
        try{
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(DB_CONNECTION);
        try {
            connectionSource =
                    new DataSourceConnectionSource(dataSource, DB_CONNECTION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public static H2ConnectionPool getInstance() {
        return connectionPool;
    }

}
