package edu.school21.Annotations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class OrmManager {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(ConnectionData.url );
        config.setUsername(ConnectionData.user);
        config.setPassword(ConnectionData.passwd);
        ds = new HikariDataSource(config);
    }

    public OrmManager(Connection connection) throws SQLException {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}
