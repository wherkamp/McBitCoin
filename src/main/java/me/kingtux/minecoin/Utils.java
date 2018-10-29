package me.kingtux.minecoin;

import me.kingtux.minecoin.placeholders.PlaceHolderAPIPlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

    public static String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String format(CommandSender sender, Player reciever, int balance, int amount,
                                final String message) {
        String newMessage = message.replace("{balance}", String.valueOf(balance));
        newMessage = newMessage.replace("{sender}", sender.getName());
        newMessage = newMessage.replace("{receiver}", reciever.getDisplayName());
        newMessage = newMessage.replace("{amount}", String.valueOf(amount));
        return newMessage;
    }

    public static String placeholderAPI(String what, OfflinePlayer player) {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceHolder.placeHolderAPI(what, player);
        }
        return what;
    }
}
