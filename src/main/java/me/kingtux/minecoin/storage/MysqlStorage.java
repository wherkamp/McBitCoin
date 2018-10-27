package me.kingtux.minecoin.storage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.*;
import java.util.UUID;
import java.util.concurrent.Future;

import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.OfflinePlayer;

/**
 * @author KingTux
 */
public class MysqlStorage extends Storage {

    private Connection connection;
    private Statement statement;
    private MineCoinMain mineCoinMain;

    public MysqlStorage(MineCoinMain mineCoinMain) {
        super("mysql");
        this.mineCoinMain = mineCoinMain;

        try {
            connection = createConnection(mineCoinMain.getConfigSettings().getHost(),
                    mineCoinMain.getConfigSettings().getPort()
                    , mineCoinMain.getConfigSettings().getDatabase(),
                    mineCoinMain.getConfigSettings().getUsername(),
                    mineCoinMain.getConfigSettings().getPassword());
        } catch (ClassNotFoundException | SQLException e) {
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

        synchronized (this) {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false",
                                username, URLEncoder.encode(password, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    @Override
    public Future<Integer> getBalance(OfflinePlayer player) {
        return getExecutor().submit(() -> {
            String query = SqlQueries.getUser.getQuery();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, player.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    return resultSet.getInt("balance");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        });

    }

    @Override
    public Future<Void> setBalance(OfflinePlayer player, int balance) {
        return getExecutor().submit(() -> {
            String query = SqlQueries.UPDATE_BALANCE.getQuery();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, balance);
                preparedStatement.setString(2, player.getUniqueId().toString());
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        });

    }

    @Override
    public Future<Boolean> hasAccount(OfflinePlayer player) {
        return getExecutor().submit(() -> {
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
        });
    }


    @Override
    public Future<Void> createAccount(OfflinePlayer player) {
        int defaultBalance = mineCoinMain.getConfigSettings().getDefaultBalance();
        return getExecutor().submit(() -> {
            String query = SqlQueries.CREATE_TABLE.getQuery();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.setInt(2, defaultBalance);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        });
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
