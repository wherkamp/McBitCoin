package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MineCoinMain;
import me.kingtux.minecoin.Utils;
import me.kingtux.minecoin.lang.LangFile;
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
            sender.sendMessage(LangFile.NOT_A_PLAYER.getColorValue());
            return true;
        }
        Player player = (Player) sender;
        Runnable runnable = () -> {
            if (args.length >= 1) {
                if (!player.hasPermission("minecoin.balance.other")) {
                    player.sendMessage(LangFile.LACK_PERMS.getFormattedValue(player));
                }
                Player who = Bukkit.getPlayer(args[0]);
                if (who == null) {
                    player.sendMessage(
                            LangFile.BALANCE_MESSAGE_OTHER.getColorValue().replace("{name}", args[0]));
                }
                try {
                    player.sendMessage(
                            formatBalanceMessage(who, mineCoinMain.getAPIManager().getBalance(who).get(), "other"));
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                if (!player.hasPermission("minecoin.balance.me")) {
                    player.sendMessage(LangFile.LACK_PERMS.getFormattedValue(player));
                }
                try {
                    player.sendMessage(
                            formatBalanceMessage(player, mineCoinMain.getAPIManager().getBalance(player).get(), "you"));
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        Bukkit.getScheduler().runTask(mineCoinMain, runnable);
        return true;
    }

    private String formatBalanceMessage(Player player, int balance, String who) {
        if (who.equals("other")) {
            return LangFile.BALANCE_MESSAGE_OTHER.getFormattedValue(player).replace("{balance}", String.valueOf(balance)).replace("{player}", player.getDisplayName());
        } else {
            return LangFile.BALANCE_MESSAGE_YOU.getFormattedValue(player).replace("{balance}", String.valueOf(balance)).replace("{player}", player.getDisplayName());
        }
    }
}
