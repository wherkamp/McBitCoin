package me.kingtux.minecoin.commands;

import me.kingtux.minecoin.MinecoinMain;
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
        if (command.getName().equalsIgnoreCase("balance")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                minecoinMain.getConnectionManager().addMoney(p, 15);
                p.sendMessage(String.valueOf(minecoinMain.getConnectionManager().getPlayerAccountBalance(p)));
            }
        }
        return false;
    }
}
