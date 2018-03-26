package me.kingtux.minecointest;

import me.clip.placeholderapi.PlaceholderAPI;
import me.kingtux.minecoin.MineCoinAPI;
import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineCoinTest extends JavaPlugin implements Listener {
    private MineCoinMain mineCoinMain;

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        mineCoinMain = (MineCoinMain) getServer().getPluginManager().getPlugin("MineCoin");

    }

    public void onDisable() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MineCoinAPI mineCoinAPI = mineCoinMain.getAPIManager();
        mineCoinAPI.addBalance(e.getPlayer(), 2);
        mineCoinAPI.subtractBalance(e.getPlayer(), 1);
        e.getPlayer().sendMessage("Your balance is " + mineCoinAPI.getBalance(e.getPlayer()));

        //This is test code for the placeholder
        e.getPlayer().sendMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(), "Your Balance is %minecoin_balance% - PlaceHolderAPI"));
        e.getPlayer().sendMessage(be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(e.getPlayer(), "Your Balance is {balance} - MVdWPlaceholderAPI"));
    }
}
