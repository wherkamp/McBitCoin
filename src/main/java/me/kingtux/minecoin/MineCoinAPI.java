package me.kingtux.minecoin;

import org.bukkit.OfflinePlayer;

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
    return mineCoinAPI;
  }

  /**
   * @return The MineCoinMain instance
   */

  /**
   * @param p The player to get the balance of
   * @return The balance of the player
   */
  public int getBalance(OfflinePlayer p) {
    if (p == null) {
      return 0;
    }
    return mineCoinMain.getStorage().getBalance(p);
  }

  /**
   * @param p The player
   * @param amount The new Balance
   * @return If it was a success
   */
  public boolean setBalance(OfflinePlayer p, int amount) {
    if (p == null) {
      return false;
    }
    return mineCoinMain.getStorage().setBalance(p, amount);
  }

  /**
   * @param p The player
   * @param amount The amount
   * @return True or false if was success
   */
  public boolean addBalance(OfflinePlayer p, int amount) {
    if (p == null) {
      return false;
    }
    return setBalance(p, getBalance(p) + amount);
  }

  /**
   * @param player The Player to test
   * @return True of false if they have a account
   */
  public boolean hasAccount(OfflinePlayer player) {
    if (player == null) {
      return false;
    }
    return mineCoinMain.getStorage().hasAccount(player);
  }

  /**
   * @param p The player
   * @param amount amount to remmove
   * @return true or false if transaction was a success
   */
  public boolean subtractBalance(OfflinePlayer p, int amount) {
    if (p == null) {
      return false;
    }
    if (getBalance(p) < amount) {
      return false;
    }
    return setBalance(p, getBalance(p) - amount);
  }

  /**
   * @param p The player
   * @param amount amount to check
   * @return true or false if player can afford it
    */
  
  public boolean canAfford(OfflinePlayer p, int amount){
    return (getBalance(p) >= amount);
  }
  
  /**
   * Run hasAccount first so you don't ruin data
   *
   * @param player The player to create an account for
   * @return if was a success
   */
  public boolean createAccount(OfflinePlayer player) {
    if (player == null) {
      return false;
    }
    return mineCoinMain.getStorage().createAccount(player);
  }
}
