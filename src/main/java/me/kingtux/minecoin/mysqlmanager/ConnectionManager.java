package me.kingtux.minecoin.mysqlmanager;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class ConnectionManager {
    private MinecoinMain minecoinMain;
    private Statement statement;
    private Connection connection;

    public ConnectionManager(MinecoinMain minecoinMain) {
        this.minecoinMain = minecoinMain;
        try {
            connection = createConnection(minecoinMain.getConfigSettings().getHost(), minecoinMain.getConfigSettings().getPort()
                    , minecoinMain.getConfigSettings().getDatabase(), minecoinMain.getConfigSettings().getUsername(), minecoinMain.getConfigSettings().getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DatabaseMetaData dbm = null;
        try {
            dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "PlayerData", null);
            if (tables.next()) {
                minecoinMain.getLogger().log(Level.INFO, "Table already created");
            } else {
                statement.execute("CREATE TABLE IF NOT EXISTS `PlayerData` (" +
                        "`uuid` TEXT," +
                        "`balance` INT" +
                        ");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
        }
        return connection;
    }

    public int getPlayerAccountBalance(Player p) {
        int PlayerBalance = 0;
        UUID pUUID = p.getUniqueId();
        ResultSet result = null;
        try {
            result = statement.executeQuery("SELECT * FROM PlayerData;");
            if (result != null) {
                while (result.next()) {
                    UUID tempUUID = UUID.fromString(result.getString("uuid"));
                    if (pUUID.equals(tempUUID)) {
                        PlayerBalance = result.getInt("balance");
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PlayerBalance;
    }

    public void createPlayerAccount(Player p) {
        UUID pUUID = p.getUniqueId();
        try {
            ResultSet rs = statement.executeQuery("SELECT uuid FROM PlayerData WHERE uuid='" + pUUID + "'");
            if (!rs.next()) {
                statement.execute("INSERT INTO PlayerData (uuid, balance) VALUES ('" + p.getUniqueId().toString() + "', 0);");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(Player p, int amount) {
        UUID pUUID = p.getUniqueId();
        amount = amount + getPlayerAccountBalance(p);
        try {
            ResultSet rs = statement.executeQuery("SELECT uuid FROM PlayerData WHERE uuid='" + pUUID + "'");
            if (rs.next()) {
                statement.execute("update PlayerData set balance='" + amount + "' where uuid='" + pUUID + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMoney(Player p, int amount) {
        UUID pUUID = p.getUniqueId();
        amount = getPlayerAccountBalance(p) - amount;
        try {
            ResultSet rs = statement.executeQuery("SELECT uuid FROM PlayerData WHERE uuid='" + pUUID + "'");
            if (rs.next()) {
                statement.execute("update PlayerData set balance='" + amount + "' where uuid='" + pUUID + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMoney(Player p, int amount) {
        UUID pUUID = p.getUniqueId();
        try {
            ResultSet rs = statement.executeQuery("SELECT uuid FROM PlayerData WHERE uuid='" + pUUID + "'");
            if (rs.next()) {
                statement.execute("update PlayerData set balance='" + amount + "' where uuid='" + pUUID + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
