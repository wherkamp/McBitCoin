package me.kingtux.minecoin.api;

import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author KingTux
 */
public class MineCoinAPI {
    private static MineCoinMain mineCoinMain;

    /**
     * @param plugin MineCoinMain
     */
    public MineCoinAPI(MineCoinMain plugin) {
        mineCoinMain = plugin;
    }

    /**
     * @return The instance of MineCoinAPI
     */
    public static MineCoinAPI getInstance() {
        return mineCoinMain.getAPIManager();
    }

    /**
     * @return The MineCoinMain instance
     */
    public MineCoinMain getMinecoinMain() {
        return mineCoinMain;
    }

    /**
     * @param p The player to get the balance of
     * @return The balance of the player
     */
    public int getBalance(OfflinePlayer p) {
        if (p == null) {
            return 0;
        }
        if (mineCoinMain.getConfigSettings().useMySql()) {
            return mineCoinMain.getConnectionManager().getPlayerAccountBalance(p);
        } else {
            return mineCoinMain.getConfigSettings().getConfigManager().getPlayerConfig().getInt("Players." + p.getUniqueId().toString());
        }
    }

    /**
     * @param p      The player
     * @param amount The new Balance
     * @return If it was a success
     */
    public boolean setBalance(OfflinePlayer p, int amount) {
        if (p == null) {
            return false;
        }
        if (mineCoinMain.getConfigSettings().useMySql()) {
            mineCoinMain.getConnectionManager().setMoney(p, amount);
            return true;
        } else {
            mineCoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set("Players." + p.getUniqueId().toString(), amount);
            mineCoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
            return true;
        }

    }

    /**
     * @param p      The player
     * @param amount The amount
     * @return True or false if was success
     */
    public boolean addBalance(OfflinePlayer p, int amount) {
        if (p == null) {
            return false;
        }

        if (mineCoinMain.getConfigSettings().useMySql()) {
            mineCoinMain.getConnectionManager().addMoney(p, amount);
            return true;
        } else {
            mineCoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set("Players." + p.getUniqueId().toString(), getBalance(p) + amount);
            mineCoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
            return true;
        }

    }

    /**
     * @param player The Player to test
     * @return True of false if they have a account
     */
    public boolean hasAccount(OfflinePlayer player) {
        if (player == null) {
            return false;
        }
        if (mineCoinMain.getConfigSettings().useMySql()) {
            return mineCoinMain.getConnectionManager().hasAccount(player.getUniqueId());
        } else {
            if (!mineCoinMain.getConfigSettings().getConfigManager().getPlayerConfig().contains("Players." + player.getUniqueId().toString())) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * @param p      The player
     * @param amount amount to remmove
     * @return true or false if transaction was a success
     */
    public boolean subtractBalance(OfflinePlayer p, int amount) {
        if (p == null) {
            return false;
        }
        if (amount > getBalance(p)) {
            if (mineCoinMain.getConfigSettings().useMySql()) {
                mineCoinMain.getConnectionManager().removeMoney(p, amount);
                return true;
            } else {
                mineCoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set("Players." + p.getUniqueId().toString(), getBalance(p) - amount);
                mineCoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Run hasAccount first so you don't ruin data
     *
     * @param player The player to create an account for
     * @return if was a success
     */
    public boolean createAccount(Player player) {
        if (player == null) {
            return false;
        }
        if (mineCoinMain.getConfigSettings().useMySql()) {
            mineCoinMain.getConnectionManager().createPlayerAccount(player);
            return true;
        } else {
            if (!mineCoinMain.getConfigSettings().getConfigManager().getPlayerConfig().contains("Players." + player.getUniqueId().toString())) {
                mineCoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set("Players." + player.getUniqueId().toString(), 0);
                mineCoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
            }
            return true;
        }
    }
}
