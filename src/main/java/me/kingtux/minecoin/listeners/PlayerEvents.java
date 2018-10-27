package me.kingtux.minecoin.listeners;

import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.ExecutionException;

public class PlayerEvents implements Listener {

  private MineCoinMain mineCoinMain;

  public PlayerEvents(MineCoinMain pl) {
    mineCoinMain = pl;
  }

  @EventHandler
  public void playerJoin(PlayerJoinEvent e) {
    try {
      if (mineCoinMain.getAPIManager().hasAccount(e.getPlayer()).get()) {
        mineCoinMain.getAPIManager().createAccount(e.getPlayer());
      }
    } catch (InterruptedException | ExecutionException e1) {
      e1.printStackTrace();
    }
  }
}
