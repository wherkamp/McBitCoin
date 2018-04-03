package me.kingtux.minecoin.storage;

import org.bukkit.OfflinePlayer;

public class YamlStorage implements Storage {

  @Override
  public int getBalance(OfflinePlayer player) {
    return 0;
  }

  @Override
  public boolean setBalance(OfflinePlayer player) {
    return false;
  }

  @Override
  public boolean addBalance(OfflinePlayer player) {
    return false;
  }

  @Override
  public boolean hasAccount(OfflinePlayer player) {
    return false;
  }

  @Override
  public boolean subtractBalance(OfflinePlayer player) {
    return false;
  }

  @Override
  public boolean createAccount(OfflinePlayer player) {
    return false;
  }
}
