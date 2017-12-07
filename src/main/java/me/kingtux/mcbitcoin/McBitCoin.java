package me.kingtux.mcbitcoin;

import me.kingtux.mcbitcoin.config.ConfigSettings;
import me.kingtux.mcbitcoin.mysqlmanager.ConnectionManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public final class McBitCoin extends JavaPlugin {
    private Connection connection;
    private ConfigSettings configSettings;


    @Override
    public void onEnable() {
        if(configSettings.useMySql()){
            try {
                connection = new ConnectionManager().createConnection(null, null, null, null, null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("You are not using Mysql");
        }
        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void registerCommands(){

    }
    private void registerEvents(){

    }

}
