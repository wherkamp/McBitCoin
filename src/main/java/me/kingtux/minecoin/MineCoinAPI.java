package me.kingtux.minecoin;

import me.kingtux.minecoin.storage.Storage;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.IllegalPluginAccessException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author KingTux
 */
public class MineCoinAPI {

    private MineCoinMain mineCoinMain;
    private static MineCoinAPI mineCoinAPI;

    public MineCoinAPI() {
        mineCoinMain = null;
        try {
            throw new IllegalAccessException("You may not access this please use MineCoinAPI#getInstane()");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param plugin MineCoinMain
     */
    protected MineCoinAPI(MineCoinMain plugin) {
        mineCoinMain = plugin;
        mineCoinAPI = this;
    }

    /**
     * @return The instance of MineCoinAPI
     */
    public static MineCoinAPI getInstance() {
        if (mineCoinAPI == null) throw new IllegalPluginAccessException("Minecoin isn't ready yet!");
        return mineCoinAPI;
    }

    /**
     * @return The MineCoinMain instance
     */

    /**
     * @param p The player to get the balance of
     * @return The balance of the player
     */
    public Future<Integer> getBalance(OfflinePlayer p) {
        return storage().getBalance(p);

    }

    /**
     * @param p      The player
     * @param amount The new Balance
     * @return If it was a success
     */
    public Future<Void> setBalance(OfflinePlayer p, int amount) {
        return storage().setBalance(p, amount);
    }

    /**
     * @param p      The player
     * @param amount The amount
     * @return True or false if was success
     */
    public Future<Void> addBalance(OfflinePlayer p, int amount) {
        return storage().addBalance(p, amount);

    }

    private Storage storage() {
        return mineCoinMain.getStorage();
    }

    /**
     * @param player The Player to test
     * @return True of false if they have a account
     */
    public Future<Boolean> hasAccount(OfflinePlayer player) {
        return storage().hasAccount(player);
    }

    /**
     * @param p      The player
     * @param amount amount to remmove
     * @return true or false if transaction was a success
     */
    public Future<Boolean> subtractBalance(OfflinePlayer p, int amount) {
        return storage().subtractBalance(p, amount);
    }

    /**
     * @param p      The player
     * @param amount amount to check
     * @return true or false if player can afford it
     */

    public Future<Boolean> canAfford(OfflinePlayer p, int amount) {
        return storage().canAfford(p, amount);
    }

    /**
     * Run hasAccount first so you don't ruin data
     *
     * @param player The player to create an account for
     */
    public Future<Void> createAccount(OfflinePlayer player) {
        return storage().createAccount(player);
    }
}
