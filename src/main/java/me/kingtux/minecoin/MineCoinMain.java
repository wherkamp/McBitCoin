package me.kingtux.minecoin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import me.kingtux.minecoin.commands.BalanceCommand;
import me.kingtux.minecoin.commands.CoinecoCommand;
import me.kingtux.minecoin.commands.PayCommand;
import me.kingtux.minecoin.config.ConfigManager;
import me.kingtux.minecoin.config.ConfigSettings;
import me.kingtux.minecoin.listeners.PlayerEvents;
import me.kingtux.minecoin.metrics.Metrics;
import me.kingtux.minecoin.placeholders.PlaceholderLoader;
import me.kingtux.minecoin.storage.MysqlStorage;
import me.kingtux.minecoin.storage.StorageTypes;
import org.bukkit.plugin.java.JavaPlugin;


public final class MineCoinMain extends JavaPlugin {

  private ConfigSettings configSettings;
  private MysqlStorage connectionManager;
  private ConfigManager configManager;
  private MineCoinAPI MinecoinAPI;
  private StorageTypes storageType;

  public MysqlStorage getConnectionManager() {
    return connectionManager;
  }

  @Override
  public void onEnable() {
    try {
      if (isUpTodate()) {
        getLogger().log(Level.INFO, "You are ok and up to date");
      } else {
        getLogger().log(Level.WARNING, "You are not up to date. I recommend updating.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    configManager = new ConfigManager(this);
    configManager.setupConfig();
    storageType = StorageTypes.getStorageType(configSettings.getConfigType());

    registerCommands();
    registerEvents();
    MinecoinAPI = new MineCoinAPI(this);
    Metrics metrics = new Metrics(this);
    metrics.addCustomChart(new Metrics.SimplePie("used_storage_type", new Callable<String>() {
      @Override
      public String call() {
        return MineCoinMain.this.storageType.name();
      }
    }));
    getServer().getScheduler().runTaskLater(this, new Runnable() {
      @Override
      public void run() {
        loadPlaceHolders();
      }
    }, 1);

  }

  private void loadPlaceHolders() {
    //Run on first tick to make sure plugin is loaded

    if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      getLogger().log(Level.INFO, "PlayerHolderAPI found");
      new PlaceholderLoader(this).loadPlaceHolderAPI();
    } else {
      getLogger().log(Level.INFO, "PlaceHolderAPI not found");
    }
    getLogger().log(Level.INFO, "Placeholders loaded");


  }


  @Override
  public void onDisable() {

  }

  private boolean isUpTodate() throws IOException {
    URL url = null;
    url = new URL("https://api.spigotmc.org/legacy/update.php?resource=53646");
    URLConnection urlConnection = url.openConnection();
    urlConnection.connect();
    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
    String reply;
    while ((reply = br.readLine()) != null) {
      if (Double.parseDouble(reply) == Double.parseDouble(this.getDescription().getVersion())) {
        return true;
      } else if (Double.parseDouble(reply) < Double
          .parseDouble(this.getDescription().getVersion())) {
        getLogger().log(Level.INFO, "You are using a dev Version");
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  private void registerCommands() {
    getCommand("balance").setExecutor(new BalanceCommand(this));
    getCommand("coineco").setExecutor(new CoinecoCommand(this));
    getCommand("pay").setExecutor(new PayCommand(this));
  }

  private void registerEvents() {
    getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
  }

  public ConfigSettings getConfigSettings() {
    return configSettings;
  }

  public void setConfigSettings(ConfigSettings configSettings) {
    this.configSettings = configSettings;
  }

  public MineCoinAPI getAPIManager() {
    return MinecoinAPI;
  }

  public StorageTypes getStorageType() {
    return storageType;
  }
}


