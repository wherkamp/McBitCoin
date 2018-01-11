package me.kingtux.minecoin.config;


public class ConfigSettings {
    private boolean useMySql;
    private String host, port, username, password, database;
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
                ", configManager=" + configManager +
                '}';
    }

    public void reloadConfig() {
    }

    public String getDatabase() {
        return database;
    }

    public boolean isUseMySql() {
        return useMySql;
    }

}
