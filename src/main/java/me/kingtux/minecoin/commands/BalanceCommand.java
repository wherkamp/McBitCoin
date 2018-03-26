package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private MineCoinMain mineCoinMain;

    public BalanceCommand(MineCoinMain pl) {
        mineCoinMain = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("balance") || command.getName().equalsIgnoreCase("bal")) {
            if (sender instanceof Player) {
                //The basic command for /balance
                Player p = (Player) sender;
                String balanceMessage = mineCoinMain.getConfigSettings().getConfigManager().getMainConfig().getString("Balance-Message");
                balanceMessage = balanceMessage.replace("{balance}", String.valueOf(mineCoinMain.getAPIManager().getBalance(p)));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', balanceMessage));
            }
        }
        return false;
    }
}
