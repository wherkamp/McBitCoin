package me.kingtux.minecoin.storage;

import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.OfflinePlayer;

public class YamlStorage implements Storage {

  private MineCoinMain mineCoinMain;

  public YamlStorage(MineCoinMain mineCoinMain) {
    this.mineCoinMain = mineCoinMain;
  }

  @Override
  public int getBalance(OfflinePlayer player) {
    return 0;
  }

  @Override
  public boolean setBalance(OfflinePlayer player, int balance) {
    return false;
  }

  @Override
  public boolean hasAccount(OfflinePlayer player) {
    return false;
  }


  @Override
  public boolean createAccount(OfflinePlayer player) {
    return false;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public void saveAndClose() {

  }
}
