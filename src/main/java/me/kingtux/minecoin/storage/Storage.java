package me.kingtux.minecoin.storage;

import org.bukkit.OfflinePlayer;

/**
 * @author KingTux
 */
public interface Storage {

  public int getBalance(OfflinePlayer player);

  public boolean setBalance(OfflinePlayer player, int balance);

  public boolean addBalance(OfflinePlayer player, int amount);

  public boolean hasAccount(OfflinePlayer player);

  public boolean subtractBalance(OfflinePlayer player, int amount);

  public boolean createAccount(OfflinePlayer player);

  public String getName();
}
