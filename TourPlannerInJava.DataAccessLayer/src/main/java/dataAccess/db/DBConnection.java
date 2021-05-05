package dataAccess.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dataAccess.ConfigurationManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Logger logger;
    private static Connection _connection = null;
    public static Connection getConnection() throws IOException, SQLException {
        if(_connection == null){
            logger = LogManager.getLogger(DBConnection.class);
            logger.info("Create DataBase Connection");
            String url = ConfigurationManager.GetConfigPropertyValue("databaseUrl");
            String user = ConfigurationManager.GetConfigPropertyValue("databaseUser");
            String pwd = ConfigurationManager.GetConfigPropertyValue("databasePassword");
            openConnection(url, user, pwd);
        }
        return _connection;
    }
    private static void openConnection(String url, String user, String password) throws SQLException {
        _connection = DriverManager.getConnection(url, user, password);
    }
    private void closeConnection() throws SQLException {
        _connection.close();
        _connection = null;
    }
    private DBConnection(){}
}
