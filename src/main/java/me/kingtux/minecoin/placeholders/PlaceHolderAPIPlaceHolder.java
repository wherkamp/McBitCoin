package me.kingtux.minecoin.placeholders;

import me.clip.placeholderapi.PlaceholderHook;
import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.entity.Player;

public class PlaceHolderAPIPlaceHolder extends PlaceholderHook {
    private MinecoinMain plugin;

    public PlaceHolderAPIPlaceHolder(MinecoinMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        if (s.equalsIgnoreCase("mcbalance")) {
            if (player != null) {
                if (plugin.getAPIManager().hasAccount(player)) {
                    return String.valueOf(plugin.getAPIManager().getBalance(player));
                }
            }
            return "0";
        }
        return null;
    }
}
