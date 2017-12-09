package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                Player player = (Player) sender;
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        player.sendMessage(ChatColor.GREEN + "Options are below and Comming soon!");
                    }
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("give")) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p != null) {
                            boolean worked = minecoinMain.getAPIManager().addBalance(p, Integer.valueOf(args[2]));
                            if (worked) {
                                player.sendMessage(ChatColor.DARK_GREEN + "Transaction was a success!");
                            } else {
                                player.sendMessage(ChatColor.DARK_RED + "Transaction FAILED!");
                            }
                        } else {
                            player.sendMessage(ChatColor.DARK_RED + "Player not found!");


                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p != null) {
                            boolean worked = minecoinMain.getAPIManager().setBalance(p, Integer.valueOf(args[2]));
                            if (worked) {
                                player.sendMessage(ChatColor.DARK_GREEN + "Transaction was a success!");
                            } else {
                                player.sendMessage(ChatColor.DARK_RED + "Transaction FAILED!");
                            }
                        } else {
                            player.sendMessage(ChatColor.DARK_RED + "Player not found!");


                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p != null) {
                            boolean worked = minecoinMain.getAPIManager().subtractBalance(p, Integer.valueOf(args[2]));
                            if (worked) {
                                player.sendMessage(ChatColor.DARK_GREEN + "Transaction was a success!");
                            } else {
                                player.sendMessage(ChatColor.DARK_RED + "Transaction FAILED!");
                            }
                        } else {
                            player.sendMessage(ChatColor.DARK_RED + "Player not found!");
                        }
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("balance")) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p != null) {
                            String money = String.valueOf(minecoinMain.getAPIManager().getBalance(p));
                            player.sendMessage(ChatColor.DARK_GREEN + "The balance of your partner is: " + money);
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + "Player not found!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid command! Use /coineco help for help!");
                }
            } else {
                sender.sendMessage("You must a player to run this command!");
            }
            return true;
        }
        return false;
    }
}
