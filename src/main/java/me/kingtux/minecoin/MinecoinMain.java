package me.kingtux.minecoin;

import me.kingtux.minecoin.api.MineCoinAPI;
import me.kingtux.minecoin.commands.BalanceCommand;
import me.kingtux.minecoin.config.ConfigManager;
import me.kingtux.minecoin.config.ConfigSettings;
import me.kingtux.minecoin.listeners.PlayerEvents;
import me.kingtux.minecoin.mysqlmanager.ConnectionManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public final class MinecoinMain extends JavaPlugin {
    private ConfigSettings configSettings;
    private ConnectionManager connectionManager;
    private ConfigManager configManager;
    private MineCoinAPI instance;

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.setupConfig();

        if (configSettings.isUseMySql() == true) {
            connectionManager = new ConnectionManager(this);
        } else {
            getLogger().log(Level.INFO, "You are not using Mysql. I recommend you change to Mysql");
        }
        System.out.println(configSettings.toString());
        registerCommands();
        registerEvents();
        if (configSettings.getCoinsLeft() > configSettings.getCoins()) {
            getLogger().log(Level.WARNING, "Lack of Coins in System! Increase coins by "
                    + String.valueOf(configSettings.getCoinsLeft() - configSettings.getCoins()));

        }
        instance = new MineCoinAPI(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getCommand("balance").setExecutor(new BalanceCommand(this));
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

    public MineCoinAPI getInstance() {
        return instance;
    }
}
