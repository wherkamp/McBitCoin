package me.kingtux.minecoin;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import me.kingtux.minecoin.api.MineCoinAPI;
import me.kingtux.minecoin.commands.BalanceCommand;
import me.kingtux.minecoin.commands.CoinecoCommand;
import me.kingtux.minecoin.commands.PayCommand;
import me.kingtux.minecoin.config.ConfigManager;
import me.kingtux.minecoin.config.ConfigSettings;
import me.kingtux.minecoin.listeners.PlayerEvents;
import me.kingtux.minecoin.metrics.Metrics;
import me.kingtux.minecoin.mysqlmanager.ConnectionManager;
import me.kingtux.minecoin.placeholders.MVdWPlaceholder;
import me.kingtux.minecoin.placeholders.PlaceHolderAPIPlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;
import java.util.logging.Level;


public final class MinecoinMain extends JavaPlugin {
    private ConfigSettings configSettings;
    private ConnectionManager connectionManager;
    private ConfigManager configManager;
    private MineCoinAPI MinecoinAPI;

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.setupConfig();

        if (configSettings.useMySql() == true) {
            connectionManager = new ConnectionManager(this);
        } else {
            getLogger().log(Level.INFO, "You are not using Mysql. I recommend you change to Mysql");
        }
        registerCommands();
        registerEvents();
        MinecoinAPI = new MineCoinAPI(this);
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("used_storage_type", new Callable<String>() {
            @Override
            public String call() {
                if (configSettings.useMySql()) {
                    return "Mysql";
                } else {
                    return "YAML";
                }
            }
        }));
        loadPlaceHolders();
    }

    private void loadPlaceHolders() {
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            PlaceholderAPI.registerPlaceholder(this, "mcbalance", new MVdWPlaceholder(this));
        }
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(this, new PlaceHolderAPIPlaceHolder(this));
        }
    }

    @Override
    public void onDisable() {
        if (!configSettings.useMySql()) {
            configManager.savePlayerConfig();
        }

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
}
