/*
 *  
 */
package data.databaseAccessObjects.dataSources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * THIS IS THE CURRENTLY USED CONNECTION
 * HikariCP.
 * https://github.com/brettwooldridge/HikariCP
 * @author 
 */
public class HikariDS
{
    private static final String IP = "207.154.233.238"; // 207.154.233.238
    private static final int PORT = 3306; // 3306
    private static final String DATABASE = "carportdb"; // carportdb
    private static final String USERNAME = "admin"; // admin
    private static final String PASSWORD = "1234"; // 1234
    private static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?autoReconnect=true&useSSL=false";

    private static HikariDataSource dataSource;
    static{

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);  
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaxLifetime(300000); // 5 minutes, 30 minutes default
        config.setIdleTimeout(150000); // 2.5 minutes, 10 minutes default
        config.setMaximumPoolSize(3); // 10 default
        config.setMinimumIdle(2); // Default same as maximumpoolsize.

        dataSource = new HikariDataSource(config);
    }

    /**
     * 
     * @return HikariDataSource
     */
    public static HikariDataSource getDataSource()
    {
        return dataSource;
    }
}
