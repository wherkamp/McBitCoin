package me.kingtux.mcbitcoin.commands;

import me.kingtux.mcbitcoin.McBitCoin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private McBitCoin mcBitCoin;

    public BalanceCommand(McBitCoin pl) {
        mcBitCoin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("balance")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                mcBitCoin.getConnectionManager().addMoney(p, 15);
                p.sendMessage(String.valueOf(mcBitCoin.getConnectionManager().getPlayerAccountBalance(p)));
            }
        }
        return false;
    }
}
