package me.kingtux.mcbitcoin.listeners;

import me.kingtux.mcbitcoin.McBitCoin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {
    private McBitCoin mcBitCoin;

    public PlayerEvents(McBitCoin pl) {
        mcBitCoin = pl;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        mcBitCoin.getConnectionManager().createPlayerAccount(e.getPlayer());
    }
}
