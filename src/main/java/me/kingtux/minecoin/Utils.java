package me.kingtux.minecoin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

  public static String color(final String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }

  public static String format(CommandSender sender, Player reciever, int balance,
      final String message) {
    String newMessage = message.replace("{balance}", String.valueOf(balance));
    newMessage = message.replace("{sender}", sender.getName());
    newMessage = message.replace("{receiver}", reciever.getDisplayName());
    return newMessage;
  }
}
