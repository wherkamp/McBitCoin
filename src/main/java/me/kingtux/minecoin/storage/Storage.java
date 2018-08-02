package me.kingtux.minecoin.storage;

import org.bukkit.OfflinePlayer;

/**
 * @author KingTux
 */
public interface Storage {

  public int getBalance(OfflinePlayer player);

  public boolean setBalance(OfflinePlayer player, int balance);

  public boolean hasAccount(OfflinePlayer player);

  public boolean createAccount(OfflinePlayer player);

  public String getName();

  public void saveAndClose();
}
