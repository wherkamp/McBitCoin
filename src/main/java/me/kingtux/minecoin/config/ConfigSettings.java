package me.kingtux.minecoin.config;


public class ConfigSettings {

  private String storageType;
  private String host, port, username, password, database;
  private ConfigManager configManager;
  private int defaultBalance;

  public ConfigSettings(ConfigManager configManager) {
    this.configManager = configManager;
    setUpBasicConfig();
  }

  public void setUpBasicConfig() {
    storageType = configManager.getMainConfig().getString("StorageType");
    host = configManager.getMainConfig().getString("Host");
    port = configManager.getMainConfig().getString("Port");
    database = configManager.getMainConfig().getString("Database");
    username = configManager.getMainConfig().getString("User");
    password = configManager.getMainConfig().getString("Password");
    defaultBalance = configManager.getMainConfig().getInt("Default-Balance");
  }

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public ConfigManager getConfigManager() {
    return configManager;
  }


  public void reloadConfig() {
  }

  public String getDatabase() {
    return database;
  }


  public String getConfigType() {
    return storageType;
  }

  public int getDefaultBalance() {
    return defaultBalance;
  }
}
