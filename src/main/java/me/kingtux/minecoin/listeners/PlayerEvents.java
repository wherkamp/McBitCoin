package me.kingtux.minecoin.listeners;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {
    private MinecoinMain minecoinMain;

    public PlayerEvents(MinecoinMain pl) {
        minecoinMain = pl;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        minecoinMain.getInstance().createAccount(e.getPlayer());
    }
}
