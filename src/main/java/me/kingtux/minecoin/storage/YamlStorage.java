package me.kingtux.minecoin.storage;

import java.io.File;
import java.io.IOException;
import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlStorage implements Storage {

  private MineCoinMain mineCoinMain;
  private FileConfiguration fileConfiguration;
  private File playerFile;

  public YamlStorage(MineCoinMain mineCoinMain) {
    this.mineCoinMain = mineCoinMain;
    playerFile = new File(mineCoinMain.getDataFolder(), "playerData.yml");
    if (!playerFile.exists()) {
      try {
        playerFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    fileConfiguration = YamlConfiguration
        .loadConfiguration(playerFile);

  }

  @Override
  public int getBalance(OfflinePlayer player) {
    return fileConfiguration.getInt("players." + player.getUniqueId().toString());
  }

  @Override
  public boolean setBalance(OfflinePlayer player, int balance) {
    fileConfiguration.set("players." + player.getUniqueId().toString(),
        balance);
    try {
      fileConfiguration.save(playerFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  @Override
  public boolean hasAccount(OfflinePlayer player) {
    return fileConfiguration.contains("players." + player.getUniqueId().toString());
  }


  @Override
  public boolean createAccount(OfflinePlayer player) {
    fileConfiguration.set("players." + player.getUniqueId().toString(),
        mineCoinMain.getConfigSettings().getDefaultBalance());
    try {
      fileConfiguration.save(playerFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  @Override
  public String getName() {
    return "yaml";
  }

  @Override
  public void saveAndClose() {
    try {
      fileConfiguration.save(playerFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
