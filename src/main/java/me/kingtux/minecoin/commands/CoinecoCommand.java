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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ExecutionException;

public class CoinecoCommand implements CommandExecutor {

    private MineCoinMain mineCoinMain;

    public CoinecoCommand(MineCoinMain pl) {
        mineCoinMain = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(LangFile.COIN_ECO_FORMAT.getColorValue());
            return true;
        }
        if (!sender.hasPermission("minecoin.coineco.use")) {
            sender.sendMessage(LangFile.LACK_PERMS.getColorValue());
            return true;
        }
        Player reciever = Bukkit.getPlayer(args[1]);
        if (reciever == null) {
            sender.sendMessage(
                    Utils.color(LangFile.MISSING_PLAYER.getColorValue().replace("name", args[0])));
            return true;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (args[0].equalsIgnoreCase("set")) {
                    mineCoinMain.getAPIManager().setBalance(reciever, Integer.parseInt(args[2]));
                    sendMessage(sender, reciever, Integer.parseInt(args[2]), "set");

                } else if (args[0].equalsIgnoreCase("add")) {
                    mineCoinMain.getAPIManager().addBalance(reciever, Integer.parseInt(args[2]));
                    sendMessage(sender, reciever, Integer.parseInt(args[2]), "add");
                } else if (args[0].equalsIgnoreCase("subtract")) {
                    boolean result = false;
                    try {
                        result = mineCoinMain.getAPIManager()
                                .subtractBalance(reciever, Integer.parseInt(args[2])).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    if (result) {
                        sendMessage(sender, reciever, Integer.parseInt(args[2]), "subtract");
                    } else {
//low-on-funds
                        sender.sendMessage(
                                LangFile.LACK_OF_FUNDING_OTHER.getColorValue()
                                        .replace("{player}", reciever.getDisplayName()));
                    }
                } else {
                    //invalid-format
                    sender.sendMessage(LangFile.COIN_ECO_FORMAT.getColorValue());
                }
            }
        };
        Bukkit.getScheduler().runTask(mineCoinMain, runnable);
        return true;
    }

    private void sendMessage(CommandSender sender, Player receiver, int amount, String job) {
        int senderBalance = 0;
        if (sender instanceof Player) {
            try {
                senderBalance = mineCoinMain.getAPIManager().getBalance(((Player) sender)).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        sender.sendMessage(Utils.placeholderAPI(Utils
                .color(Utils
                        .format(sender, receiver, senderBalance, amount, getSFormat(job))), (OfflinePlayer) sender));
        try {
            receiver.sendMessage(Utils.placeholderAPI(Utils
                    .color(Utils
                            .format(sender, receiver, mineCoinMain.getAPIManager().getBalance(receiver).get(), amount, getRFormat(job))), (OfflinePlayer) sender));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    private String getRFormat(String job) {
        switch (job) {
            case "add":
                return LangFile.COIN_ECO_ADD_RECEIVER.getColorValue();
            case "subtract":
                return LangFile.COIN_ECO_SUBTRACT_RECEIVER.getColorValue();
            case "set":
                return LangFile.COIN_ECO_SET_RECEIVER.getColorValue();
        }
        return "";
    }

    private String getSFormat(String job) {
        switch (job) {
            case "add":
                return LangFile.COIN_ECO_ADD_SENDER.getColorValue();
            case "subtract":
                return LangFile.COIN_ECO_SUBTRACT_SENDER.getColorValue();
            case "set":
                return LangFile.COIN_ECO_SET_SENDER.getColorValue();
        }
        return "";
    }

}
