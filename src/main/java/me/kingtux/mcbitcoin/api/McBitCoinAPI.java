package me.kingtux.mcbitcoin.api;

import me.kingtux.mcbitcoin.McBitCoin;

public class McBitCoinAPI {
    private McBitCoin McBitCoin;

    public McBitCoinAPI(McBitCoin plugin){
        this.McBitCoin = plugin;
    }

    public McBitCoin getMcBitCoin() {
        return McBitCoin;
    }
}
