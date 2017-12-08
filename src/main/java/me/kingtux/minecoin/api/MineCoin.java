package me.kingtux.minecoin.api;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.entity.Player;

public class MineCoin {
    private MinecoinMain minecoinMain;

    public MineCoin(MinecoinMain plugin) {
        this.minecoinMain = plugin;
    }

    public MinecoinMain getMinecoinMain() {
        return minecoinMain;
    }

    public int getBalance(Player p) {
        if (minecoinMain.getConfigSettings().isUseMySql()) {
            return minecoinMain.getConnectionManager().getPlayerAccountBalance(p);
        } else {
            return 0;
        }
    }

    public void setBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().isUseMySql()) {
            minecoinMain.getConnectionManager().setMoney(p, amount);
        } else {
        }
    }

    public void addBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().isUseMySql()) {
            minecoinMain.getConnectionManager().addMoney(p, amount);
        } else {
        }
    }

    public void subtractBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().isUseMySql()) {
            minecoinMain.getConnectionManager().removeMoney(p, amount);
        } else {
        }
    }
}
