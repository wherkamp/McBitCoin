package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MineCoinMain;
import me.kingtux.minecoin.Utils;
import me.kingtux.minecoin.lang.LangFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class PayCommand implements CommandExecutor {

    private MineCoinMain mineCoinMain;

    public PayCommand(MineCoinMain pl) {
        mineCoinMain = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LangFile.NOT_A_PLAYER.getColorValue());
            return true;
        }
        if (!sender.hasPermission("minecoin.pay")) {
            sender.sendMessage(LangFile.LACK_PERMS.getColorValue());
            return true;
        }
        if (!(args.length >= 2)) {
            sender.sendMessage(LangFile.PAY_INVALID_FORMAT.getColorValue());
            return true;
        }
        if (isStringInt(args[1])) {
            sender.sendMessage(LangFile.MUST_BE_AN_INT.getColorValue());
            return true;
        }
        Player recieverPlayer = Bukkit.getPlayer(args[0]);
        if (recieverPlayer == null) {
            sender.sendMessage(
                    Utils.color(LangFile.MISSING_PLAYER.getColorValue().replace("{name}", args[0])));
            return true;
        }

        Player senderPlayer = (Player) sender;

        int amount = Integer.parseInt(args[0]);
        //Prevent Little Fuckers
        amount = Math.abs(amount);
        int finalAmount = amount;
        Runnable runnable = () -> {
            try {
                if (finalAmount > mineCoinMain.getAPIManager().getBalance(senderPlayer).get()) {
                    sender.sendMessage(LangFile.LACK_OF_FUNDING.getColorValue());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            try {
                mineCoinMain.getAPIManager().subtractBalance(senderPlayer, finalAmount).get();
                mineCoinMain.getAPIManager().addBalance(recieverPlayer, finalAmount).get();
                sendMessage(sender, recieverPlayer, finalAmount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Bukkit.getScheduler().runTask(mineCoinMain, runnable);
        return true;
    }

    private void sendMessage(CommandSender sender, Player receiver, int amount) {
        try {
            sender.sendMessage(Utils.placeholderAPI(Utils
                    .color(Utils
                            .format(sender, receiver, mineCoinMain.getAPIManager().getBalance(((Player) sender)).get(), amount, LangFile.PAY_SENT.getColorValue())), (OfflinePlayer) sender));
            receiver.sendMessage(Utils.placeholderAPI(Utils
                    .color(Utils
                            .format(sender, receiver, mineCoinMain.getAPIManager().getBalance(((Player) sender)).get(), amount, LangFile.PAY_RECEIVE.getColorValue())), (OfflinePlayer) sender));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
