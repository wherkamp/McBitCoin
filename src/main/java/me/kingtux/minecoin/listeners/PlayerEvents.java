package me.kingtux.minecoin.listeners;

import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

  private MineCoinMain mineCoinMain;

  public PlayerEvents(MineCoinMain pl) {
    mineCoinMain = pl;
  }

  @EventHandler
  public void playerJoin(PlayerJoinEvent e) {
    if (mineCoinMain.getAPIManager().hasAccount(e.getPlayer())) {
      mineCoinMain.getAPIManager().createAccount(e.getPlayer());
    }
  }
}
