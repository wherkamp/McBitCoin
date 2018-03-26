package me.kingtux.minecointest;

import me.clip.placeholderapi.PlaceholderAPI;
import me.kingtux.minecoin.api.MineCoinAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineCoinTest extends JavaPlugin implements Listener {
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MineCoinAPI mineCoinAPI = MineCoinAPI.getInstance();
        mineCoinAPI.addBalance(e.getPlayer(), 2);
        mineCoinAPI.subtractBalance(e.getPlayer(), 1);
        e.getPlayer().sendMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(), "Your Balance is %minecoin_balance% - PlaceHolderAPI"));
        e.getPlayer().sendMessage(be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(e.getPlayer(), "Your Balance is {balance} - MVdWPlaceholderAPI"));
    }
}
