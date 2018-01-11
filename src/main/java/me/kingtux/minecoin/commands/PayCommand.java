package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    private MinecoinMain minecoinMain;

    public PayCommand(MinecoinMain pl) {
        minecoinMain = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("pay")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length <= 0) {
                    p.sendMessage(ChatColor.DARK_RED + "The Command format is this /pay {player} {amount}");
                } else {
                    Player recieveingPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if (recieveingPlayer == null) {
                        p.sendMessage(ChatColor.DARK_RED + "That player is not online");
                        return false;
                    }
                    int payment = 0;
                    try {
                        payment = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        p.sendMessage(ChatColor.DARK_RED + "Second Arg must be a integer.");
                    }
                    if (minecoinMain.getAPIManager().getBalance(p) < payment) {
                        p.sendMessage(ChatColor.DARK_RED + "You lack the money to give");
                        return false;
                    }

                    boolean b = minecoinMain.getAPIManager().subtractBalance(p, payment);
                    if (b == true) {
                        minecoinMain.getAPIManager().addBalance(recieveingPlayer, payment);


                        String receiverMessage = minecoinMain.getConfigSettings().getConfigManager().getMainConfig().getString("Pay-Message.Receive");
                        String payMessage = minecoinMain.getConfigSettings().getConfigManager().getMainConfig().getString("Pay-Message.Pay");
                        p.sendMessage(translateMessage(payMessage, p.getDisplayName(), recieveingPlayer.getDisplayName(), payment));
                        recieveingPlayer.sendMessage(translateMessage(
                                receiverMessage, p.getDisplayName(), recieveingPlayer.getDisplayName(), payment));
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "You lack the funds to do this");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "You must be a player to run this command");
            }
        }
        return false;
    }

    private String translateMessage(String message, String payerName, String receiverName, int amount) {
        message = message.replace("{payer}", payerName);
        message = message.replace("{receiver}", receiverName);
        message = message.replace("{amount}", String.valueOf(amount));
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
