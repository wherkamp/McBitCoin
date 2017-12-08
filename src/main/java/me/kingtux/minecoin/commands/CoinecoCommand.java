package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinecoCommand implements CommandExecutor {
    private MinecoinMain minecoinMain;

    public CoinecoCommand(MinecoinMain pl) {
        minecoinMain = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("coineco")) {
            if (sender instanceof Player) {
                if (args[0].equalsIgnoreCase("help")) {

                }
                if (args.length >= 3) {
                    if (args[0].equalsIgnoreCase("give")) {

                    } else if (args[0].equalsIgnoreCase("set")) {

                    } else if (args[0].equalsIgnoreCase("remove")) {

                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("balance")) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p != null) {
                            minecoinMain.getInstance().getBalance(p);
                        }
                    }
                }
            } else {
                sender.sendMessage("You must a player to run this command!");
            }
            return true;
        }
        return false;
    }
}
