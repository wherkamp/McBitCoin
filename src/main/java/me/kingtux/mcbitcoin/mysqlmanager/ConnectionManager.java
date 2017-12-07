package me.kingtux.mcbitcoin.mysqlmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public Connection createConnection(String host, String port, String database, String username,
                                       String password) throws ClassNotFoundException, SQLException {
        Connection connection = null;

            if (connection != null && !connection.isClosed()) {
                return null;
            }


        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return null;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host+ ":" + port + "/" + database, username, password);
        }
    return connection;
    }
}
