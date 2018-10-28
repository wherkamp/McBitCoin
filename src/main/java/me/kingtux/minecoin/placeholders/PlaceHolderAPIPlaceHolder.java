package me.kingtux.minecoin.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class PlaceHolderAPIPlaceHolder extends PlaceholderExpansion {

    private MineCoinMain plugin;

    PlaceHolderAPIPlaceHolder(MineCoinMain pl) {
        plugin = pl;
    }

    // %minecoin_balance%
    @Override
    public String onPlaceholderRequest(Player player, String s) {
        if (s.equalsIgnoreCase("balance")) {
            if (player != null) {
                try {
                    if (plugin.getAPIManager().hasAccount(player).get()) {
                        return String.valueOf(plugin.getAPIManager().getBalance(player));
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return "0";
        }
        return null;
    }

    @Override
    public String getIdentifier() {
        return "minecoin";
    }

    @Override
    public String getPlugin() {
        return "MineCoin";
    }

    @Override
    public String getAuthor() {
        return "KingTux";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
