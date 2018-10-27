package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MineCoinMain;
import me.kingtux.minecoin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class BalanceCommand implements CommandExecutor {

  private MineCoinMain mineCoinMain;

  public BalanceCommand(MineCoinMain pl) {
    mineCoinMain = pl;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("messages.not-a-player");
      return true;
    }
    Player player = (Player) sender;
    if (args.length >= 1) {
      if (!player.hasPermission("minecoin.balance.other")) {
        player.sendMessage(Utils.color(getString("messages.you-lack-permissions")));
        return true;
      }
      Player who = Bukkit.getPlayer(args[0]);
      if (who == null) {
        player.sendMessage(
            Utils.color(getString("messages.player-not-online").replace("name", args[0])));
        return true;
      }
      try {
        player.sendMessage(
            formatBalanceMessage(who, mineCoinMain.getAPIManager().getBalance(who).get(), "other"));
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
      return true;
    } else {
      if (!player.hasPermission("minecoin.balance.me")) {
        player.sendMessage(Utils.color(getString("messages.you-lack-permissions")));
        return true;
      }
      try {
        player.sendMessage(
            formatBalanceMessage(player, mineCoinMain.getAPIManager().getBalance(player).get(), "you"));
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
      return true;
    }
  }

  private String formatBalanceMessage(Player player, int balance, String who) {
    return Utils
        .color(getString("messages.balance." + who).replace("{balance}", String.valueOf(balance))
            .replace("{player}", player.getDisplayName()));

  }

  private String getString(String key) {
    return mineCoinMain.getConfig().getString(key);
  }

}
