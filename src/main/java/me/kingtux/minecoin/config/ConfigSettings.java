package me.kingtux.minecoin.config;


public class ConfigSettings {
    private boolean useMySql;
    private String host, port, username, password, database;
    private int coins;
    private int coinsLeft;
    private ConfigManager configManager;

    public ConfigSettings(ConfigManager configManager) {
        this.configManager = configManager;
        setUpBasicConfig();
    }

    public void setUpBasicConfig() {
        useMySql = configManager.getMainConfig().getBoolean("Use-Mysql");
        if (useMySql == true) {
            System.out.println("Using Mysql");
            host = configManager.getMainConfig().getString("Host");
            port = configManager.getMainConfig().getString("Port");
            database = configManager.getMainConfig().getString("Database");
            username = configManager.getMainConfig().getString("User");
            password = configManager.getMainConfig().getString("Password");

        } else {
            coins = configManager.getMainConfig().getInt("Coins");
            int i = 0;
            for (String s : configManager.getPlayerConfig().getConfigurationSection("Players").getKeys(false)) {
                i = i + configManager.getPlayerConfig().getInt("Players." + s);
            }
            coinsLeft = coins - i;
        }

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

    public int getCoins() {
        return coins;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public String toString() {
        return "ConfigSettings{" +
                "useMySql=" + useMySql +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", database='" + database + '\'' +
                ", coins=" + coins +
                ", configManager=" + configManager +
                '}';
    }

    public int getCoinsLeft() {
        return coinsLeft;
    }

    public void reloadConfig() {
    }

    public String getDatabase() {
        return database;
    }

    public boolean isUseMySql() {
        return useMySql;
    }

    public void setCoinsLeft(int coinsLeft) {
        coinsLeft = coinsLeft;
    }
}
