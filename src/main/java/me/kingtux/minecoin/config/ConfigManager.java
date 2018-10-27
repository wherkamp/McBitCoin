package me.kingtux.minecoin.config;

import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigManager {

  private MineCoinMain mainclass;
  private File mainConfig;


  public ConfigManager(MineCoinMain mainclass) {
    this.mainclass = mainclass;
  }

  public void setupConfig() {
    mainConfig = new File(mainclass.getDataFolder(), "config.yml");
    if (!(mainConfig.exists())) {
      //Create The Config
      mainclass.saveDefaultConfig();
      mainclass.getLogger().log(Level.WARNING, "Created Main Config");
    }

    setupConfigSettings();
  }

  public void setupConfigSettings() {
    mainclass.setConfigSettings(new ConfigSettings(this));
  }

  public FileConfiguration getMainConfig() {
    return mainclass.getConfig();
  }

  public void reloadConfig(ConfigSettings configSettings) {
    try {
      getMainConfig().load(mainConfig);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      mainclass.getConfigSettings().reloadConfig();
      e.printStackTrace();
    }
  }

}
