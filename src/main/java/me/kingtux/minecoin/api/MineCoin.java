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
            minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().getInt(p.getUniqueId().toString());
        }
    }

    public void setBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().isUseMySql()) {
            minecoinMain.getConnectionManager().setMoney(p, amount);
        } else {
            minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set(p.getUniqueId().toString(), amount);
        }
    }

    public void addBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().isUseMySql()) {
            minecoinMain.getConnectionManager().addMoney(p, amount);
        } else {
            minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set(p.getUniqueId().toString(), getBalance(p) + amount);
        }
    }

    public void subtractBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().isUseMySql()) {
            minecoinMain.getConnectionManager().removeMoney(p, amount);
        } else {
            minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set(p.getUniqueId().toString(), getBalance(p) - amount);
        }
    }
}
