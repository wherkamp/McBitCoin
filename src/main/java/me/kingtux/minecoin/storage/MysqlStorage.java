package me.kingtux.minecoin.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.OfflinePlayer;

/**
 * @author KingTux
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
      statement.execute(SqlQueries.CREATE_TABLE.getQuery());
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
    int balance = 0;
    String query = SqlQueries.getUser.getQuery();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, player.getUniqueId().toString());
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        balance = resultSet.getInt("balance");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return balance;
  }

  @Override
  public boolean setBalance(OfflinePlayer player, int balance) {
    String query = SqlQueries.UPDATE_BALANCE.getQuery();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, balance);
      preparedStatement.setString(2, player.getUniqueId().toString());
      ResultSet resultSet = preparedStatement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean hasAccount(OfflinePlayer player) {
    String query = SqlQueries.getUser.getQuery();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, player.getUniqueId().toString());
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }


  @Override
  public boolean createAccount(OfflinePlayer player) {
    int defaultBalance = mineCoinMain.getConfigSettings().getDefaultBalance();
    String query = SqlQueries.CREATE_TABLE.getQuery();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, player.getUniqueId().toString());
      preparedStatement.setInt(2, defaultBalance);
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public String getName() {
    return "mysql";
  }

  @Override
  public void saveAndClose() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
