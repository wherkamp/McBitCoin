package me.kingtux.mcbitcoin.api;

import me.kingtux.mcbitcoin.McBitCoin;
import org.bukkit.entity.Player;

public class McBitCoinAPI {
    private McBitCoin mcBitCoin;

    public McBitCoinAPI(McBitCoin plugin){
        this.mcBitCoin = plugin;
    }

    public McBitCoin getMcBitCoin() {
        return mcBitCoin;
    }

    public int getBalance(Player p) {
        if (mcBitCoin.getConfigSettings().isUseMySql()) {
            return mcBitCoin.getConnectionManager().getPlayerAccountBalance(p);
        } else {
            return 0;
        }
    }

    public void setBalance(Player p, int amount) {
        if (mcBitCoin.getConfigSettings().isUseMySql()) {
            mcBitCoin.getConnectionManager().setMoney(p, amount);
        } else {
        }
    }

    public void addBalance(Player p, int amount) {
        if (mcBitCoin.getConfigSettings().isUseMySql()) {
            mcBitCoin.getConnectionManager().addMoney(p, amount);
        } else {
        }
    }

    public void subtractBalance(Player p, int amount) {
        if (mcBitCoin.getConfigSettings().isUseMySql()) {
            mcBitCoin.getConnectionManager().removeMoney(p, amount);
        } else {
        }
    }
}
