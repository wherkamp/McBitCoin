package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private MinecoinMain minecoinMain;

    public BalanceCommand(MinecoinMain pl) {
        minecoinMain = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("balance") || command.getName().equalsIgnoreCase("bal")) {
            if (sender instanceof Player) {
                //The basic command for /balance
                Player p = (Player) sender;
                String balanceMessage = minecoinMain.getConfigSettings().getConfigManager().getMainConfig().getString("Balance-Message");
                balanceMessage = balanceMessage.replace("{balance}", String.valueOf(minecoinMain.getAPIManager().getBalance(p)));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', balanceMessage));
            }
        }
        return false;
    }
}
