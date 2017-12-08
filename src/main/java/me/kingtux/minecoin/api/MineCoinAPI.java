package me.kingtux.minecoin.api;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.entity.Player;

public class MineCoinAPI {
    private static MinecoinMain minecoinMain;

    public MineCoinAPI(MinecoinMain plugin) {
        minecoinMain = plugin;
    }

    public static MineCoinAPI getIntance() {
        return minecoinMain.getInstance();
    }
    public MinecoinMain getMinecoinMain() {
        return minecoinMain;
    }

    public int getBalance(Player p) {
        if (minecoinMain.getConfigSettings().isUseMySql()) {
            return minecoinMain.getConnectionManager().getPlayerAccountBalance(p);
        } else {
            return minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().getInt(p.getUniqueId().toString());
        }
    }

    public boolean setBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().getCoinsLeft() >= minecoinMain.getConfigSettings().getCoins()) {
            if (minecoinMain.getConfigSettings().isUseMySql()) {
                minecoinMain.getConnectionManager().setMoney(p, amount);
                return true;
            } else {
                minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set(p.getUniqueId().toString(), amount);
                minecoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean addBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().getCoinsLeft() >= minecoinMain.getConfigSettings().getCoins()) {

            if (minecoinMain.getConfigSettings().isUseMySql()) {
                minecoinMain.getConnectionManager().addMoney(p, amount);
                return true;
            } else {
                minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set(p.getUniqueId().toString(), getBalance(p) + amount);
                minecoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean subtractBalance(Player p, int amount) {
        if (minecoinMain.getConfigSettings().getCoinsLeft() >= minecoinMain.getConfigSettings().getCoins()) {
            if (minecoinMain.getConfigSettings().isUseMySql()) {
                minecoinMain.getConnectionManager().removeMoney(p, amount);
                return true;
            } else {
                minecoinMain.getConfigSettings().getConfigManager().getPlayerConfig().set(p.getUniqueId().toString(), getBalance(p) - amount);
                minecoinMain.getConfigSettings().getConfigManager().savePlayerConfig();
                return true;
            }
        } else {
            return false;
        }
    }
}
