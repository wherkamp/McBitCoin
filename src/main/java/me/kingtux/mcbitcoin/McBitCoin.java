package me.kingtux.mcbitcoin;

import me.kingtux.mcbitcoin.commands.BalanceCommand;
import me.kingtux.mcbitcoin.config.ConfigManager;
import me.kingtux.mcbitcoin.config.ConfigSettings;
import me.kingtux.mcbitcoin.listeners.PlayerEvents;
import me.kingtux.mcbitcoin.mysqlmanager.ConnectionManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class McBitCoin extends JavaPlugin {
    private ConfigSettings configSettings;
    private ConnectionManager connectionManager;
    private ConfigManager configManager;

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.setupConfig();

        if (configSettings.isUseMySql() == true) {
            connectionManager = new ConnectionManager(this);
            System.out.println(connectionManager.toString());
        }else{
            System.out.println("You are not using Mysql");
        }
        System.out.println(configSettings.toString());
        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void registerCommands(){
        getCommand("balance").setExecutor(new BalanceCommand(this));
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
    }

    public ConfigSettings getConfigSettings() {
        return configSettings;
    }

    public void setConfigSettings(ConfigSettings configSettings) {
        this.configSettings = configSettings;
    }
}
