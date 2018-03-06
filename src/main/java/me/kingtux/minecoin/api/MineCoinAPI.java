package me.kingtux.minecoin.api;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.entity.Player;

/**
 * @author KingTux
 */
public class MineCoinAPI {
    private static MinecoinMain minecoinMain;

    /**
     * @param plugin MinecoinMain
     */
    public MineCoinAPI(MinecoinMain plugin) {
        minecoinMain = plugin;
    }

    /**
     * @return The instance of MineCoinAPI
     */
    public static MineCoinAPI getInstance() {
        return minecoinMain.getAPIManager();
    }

    /**
     * @return The MineCoinMain instance
     */
    public MinecoinMain getMinecoinMain() {
        return minecoinMain;
    }

    /**
     * @param p The player to get the balance of
     * @return The balance of the player
     */
    public int getBalance(Player p) {
        if (p == null) {
            return 0;
        }
        if (minecoinMain.getConfigSettings().useMySql()) {
            return minecoinMain.getConnectionManager().getPlayerAccountBalance(p);
        } else {
            return minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().getInt("Players." + p.getUniqueId().toString());
        }
    }

    /**
     * @param p      The player
     * @param amount The new Balance
     * @return If it was a success
     */
    public boolean setBalance(Player p, int amount) {
        if (p == null) {
            return false;
        }
        if (minecoinMain.getConfigSettings().useMySql()) {
            minecoinMain.getConnectionManager().setMoney(p, amount);
            return true;
        } else {
            minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set("Players." + p.getUniqueId().toString(), amount);
            minecoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
            return true;
        }

    }

    /**
     * @param p      The player
     * @param amount The amount
     * @return True or false if was success
     */
    public boolean addBalance(Player p, int amount) {
        if (p == null) {
            return false;
        }

        if (minecoinMain.getConfigSettings().useMySql()) {
            minecoinMain.getConnectionManager().addMoney(p, amount);
            return true;
        } else {
            minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set("Players." + p.getUniqueId().toString(), getBalance(p) + amount);
            minecoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
            return true;
        }

    }

    /**
     * @param p      The player
     * @param amount amount to remmove
     * @return true or false if transaction was a success
     */
    public boolean subtractBalance(Player p, int amount) {
        if (p == null) {
            return false;
        }
        if (amount > getBalance(p)) {
            if (minecoinMain.getConfigSettings().useMySql()) {
                minecoinMain.getConnectionManager().removeMoney(p, amount);
                return true;
            } else {
                minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set("Players." + p.getUniqueId().toString(), getBalance(p) - amount);
                minecoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * apiNote Doing this could ruin the data
     *
     * @param player The player to create an account for
     * @return if was a success
     */
    public boolean createAccount(Player player) {
        if (player == null) {
            return false;
        }
        if (minecoinMain.getConfigSettings().useMySql()) {
            minecoinMain.getConnectionManager().createPlayerAccount(player);
            return true;
        } else {
            if (!minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().contains("Players." + player.getUniqueId().toString())) {
                minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set("Players." + player.getUniqueId().toString(), 0);
                minecoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
            }
            return true;
        }
    }
}
