package me.kingtux.minecoin.mysqlmanager;

import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class ConnectionManager {
    private MineCoinMain mineCoinMain;
    private Statement statement;
    private Connection connection;

    public ConnectionManager(MineCoinMain mineCoinMain) {
        this.mineCoinMain = mineCoinMain;
        try {
            connection = createConnection(mineCoinMain.getConfigSettings().getHost(), mineCoinMain.getConfigSettings().getPort()
                    , mineCoinMain.getConfigSettings().getDatabase(), mineCoinMain.getConfigSettings().getUsername(), mineCoinMain.getConfigSettings().getPassword());
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
                mineCoinMain.getLogger().log(Level.INFO, "Table already created");
            } else {
                statement.execute("CREATE TABLE IF NOT EXISTS `PlayerData` (" +
                        "`uuid` TEXT," +
                        "`balance` INT" +
                        ");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mineCoinMain.getLogger().log(Level.INFO, "Mysql loaded! Good Job!");

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

    public int getPlayerAccountBalance(OfflinePlayer p) {
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

    public void addMoney(OfflinePlayer p, int amount) {
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

    public void removeMoney(OfflinePlayer p, int amount) {
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

    public void setMoney(OfflinePlayer p, int amount) {
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


    public boolean hasAccount(UUID uuid) {
        ResultSet result = null;
        try {
            result = statement.executeQuery("SELECT * FROM PlayerData WHERE uuid=" + uuid + ";");
            if (result != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
