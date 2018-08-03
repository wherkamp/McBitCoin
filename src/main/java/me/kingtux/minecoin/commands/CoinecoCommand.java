package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MineCoinMain;
import me.kingtux.minecoin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinecoCommand implements CommandExecutor {

  private MineCoinMain mineCoinMain;

  public CoinecoCommand(MineCoinMain pl) {
    mineCoinMain = pl;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length < 3) {
      sender.sendMessage(
          getString("messages.not-enough-args").replace("{format}", command.getUsage()));
      return true;
    }
    if (!sender.hasPermission("minecoin.coineco.use")) {
      sender.sendMessage(Utils.color(getString("messages.you-lack-permissions")));
      return true;
    }
    Player reciever = Bukkit.getPlayer(args[1]);
    if (reciever == null) {
      sender.sendMessage(
          Utils.color(getString("messages.player-not-online").replace("name", args[0])));
      return true;
    }
    if (args[0].equalsIgnoreCase("set")) {
      mineCoinMain.getAPIManager().setBalance(reciever, Integer.parseInt(args[2]));
      sendMessage(sender, reciever, Integer.parseInt(args[2]), "set");

    } else if (args[0].equalsIgnoreCase("add")) {
      mineCoinMain.getAPIManager().addBalance(reciever, Integer.parseInt(args[2]));
      sendMessage(sender, reciever, Integer.parseInt(args[2]), "add");
    } else if (args[0].equalsIgnoreCase("subtract")) {
      boolean result = mineCoinMain.getAPIManager()
          .subtractBalance(reciever, Integer.parseInt(args[2]));
      if (result) {
        sendMessage(sender, reciever, Integer.parseInt(args[2]), "subtract");
      } else {
//low-on-funds
        sender.sendMessage(
            getString("messages.low-on-funds.other")
                .replace("{player}", reciever.getDisplayName()));
      }
    } else {
      //invalid-format
      sender.sendMessage(
          getString("messages.invalid-format").replace("{format}", command.getUsage()));
    }
    return true;
  }

  private void sendMessage(CommandSender sender, Player reciever, int amount, String job) {
    sender.sendMessage(Utils
        .color(Utils
            .format(sender, reciever, amount, getString("messages.coineco." + job + ".sender"))));
    reciever.sendMessage(Utils
        .color(Utils
            .format(sender, reciever, amount, getString("messages.coineco." + job + ".receiver"))));
  }

  private String getString(String key) {
    return mineCoinMain.getConfig().getString(key);
  }
}
