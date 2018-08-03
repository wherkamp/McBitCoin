package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MineCoinMain;
import me.kingtux.minecoin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

  private MineCoinMain mineCoinMain;

  public PayCommand(MineCoinMain pl) {
    mineCoinMain = pl;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("messages.not-a-player");
      return true;
    }
    if (!sender.hasPermission("minecoin.pay")) {
      sender.sendMessage(Utils.color(getString("messages.you-lack-permissions")));
      return true;
    }
    if (!(args.length >= 2)) {
      sender.sendMessage(
          getString("messages.not-enough-args").replace("{format}", command.getUsage()));
      return true;
    }
    if (isStringInt(args[1])) {
      sender.sendMessage(
          Utils.color(getString("messages.argument-must-be-an-int").replace("arg", "Amount")));
      return true;
    }
    Player recieverPlayer = Bukkit.getPlayer(args[0]);
    if (recieverPlayer == null) {
      sender.sendMessage(
          Utils.color(getString("messages.player-not-online").replace("name", args[0])));
      return true;
    }

    Player senderPlayer = (Player) sender;

    int amount = Integer.parseInt(args[0]);
    if (amount > mineCoinMain.getAPIManager().getBalance(senderPlayer)) {
      sender.sendMessage(
          getString("messages.low-on-funds.you"));
      return true;
    }
    mineCoinMain.getAPIManager().subtractBalance(senderPlayer, amount);

    mineCoinMain.getAPIManager().

        addBalance(recieverPlayer, amount);
    return false;
  }

  private String getString(String key) {
    return mineCoinMain.getConfig().getString(key);
  }

  private boolean isStringInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }
}
