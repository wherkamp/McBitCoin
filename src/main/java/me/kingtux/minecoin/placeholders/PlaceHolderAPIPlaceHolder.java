package me.kingtux.minecoin.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceHolderAPIPlaceHolder extends PlaceholderExpansion {

    private MinecoinMain plugin;

    public PlaceHolderAPIPlaceHolder() {
        plugin = (MinecoinMain) Bukkit.getPluginManager().getPlugin(getPlugin());
    }

    // %minecoin_balance%
    @Override
    public String onPlaceholderRequest(Player player, String s) {
        if (s.equalsIgnoreCase("balance")) {
            if (player != null) {
                if (plugin.getAPIManager().hasAccount(player)) {
                    return String.valueOf(plugin.getAPIManager().getBalance(player));
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
