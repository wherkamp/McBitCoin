package me.kingtux.minecoin.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.OfflinePlayer;

/**
 * @author KingTux CREATE TABLE `Player_Balances` ( `PlayerID` INT NOT NULL AUTO_INCREMENT,
 * `PlayerUUID` VARCHAR(255) NOT NULL, `PlayerBalance` INT NOT NULL, PRIMARY KEY (`PlayerID`) );
 */
public class MysqlStorage implements Storage {

  private Connection connection;
  private Statement statement;
  private MineCoinMain mineCoinMain;

  public MysqlStorage(MineCoinMain mineCoinMain) {
    this.mineCoinMain = mineCoinMain;

    try {
      connection = createConnection(mineCoinMain.getConfigSettings().getHost(),
          mineCoinMain.getConfigSettings().getPort()
          , mineCoinMain.getConfigSettings().getDatabase(),
          mineCoinMain.getConfigSettings().getUsername(),
          mineCoinMain.getConfigSettings().getPassword());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      statement = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `Player_Balances` ( `PlayerID` INT NOT NULL AUTO_INCREMENT,`PlayerUUID` VARCHAR(255) NOT NULL, `PlayerBalance` INT NOT NULL, PRIMARY KEY (`PlayerID`) );");
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  private Connection createConnection(String host, String port, String database, String username,
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
      connection = DriverManager
          .getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false",
              username, password);
    }
    return connection;
  }


  @Override
  public int getBalance(OfflinePlayer player) {
    int PlayerBalance = 0;
    UUID pUUID = player.getUniqueId();
    ResultSet result = null;
    try {
      result = statement.executeQuery(
          "SELECT * FROM `Player_Balances` WHERE PlayerUUID='" + pUUID.toString() + "';");
      if (result != null) {
        while (result.next()) {
          UUID tempUUID = UUID.fromString(result.getString("PlayerUUID"));
          if (pUUID.equals(tempUUID)) {
            PlayerBalance = result.getInt("PlayerBalance");
            break;
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return PlayerBalance;
  }

  @Override
  public boolean setBalance(OfflinePlayer player, int balance) {
    return false;
  }

  @Override
  public boolean addBalance(OfflinePlayer player, int amount) {
    return false;
  }

  @Override
  public boolean hasAccount(OfflinePlayer player) {
    return false;
  }

  @Override
  public boolean subtractBalance(OfflinePlayer player, int amount) {
    return false;
  }

  @Override
  public boolean createAccount(OfflinePlayer player) {
    UUID pUUID = player.getUniqueId();
    try {
      ResultSet rs = statement
          .executeQuery(
              "SELECT PlayerUUID FROM Player_Balances WHERE PlayerUUID='" + pUUID.toString() + "'");
      if (!rs.next()) {
        statement.execute(
            "INSERT INTO PlayerData (PlayerUUID, Player_Balances) VALUES ('" + pUUID.toString()
                + "', " + mineCoinMain.getConfigSettings().getDefaultBalance() + ");");
        return true;
      } else {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public String getName() {
    return "mysql";
  }

  @Override
  public void saveAndClose() {

  }
}
