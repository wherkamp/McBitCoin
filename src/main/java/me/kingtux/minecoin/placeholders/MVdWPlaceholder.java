package me.kingtux.minecoin.placeholders;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import me.kingtux.minecoin.MinecoinMain;

public class MVdWPlaceholder implements PlaceholderReplacer {
    private MinecoinMain plugin;

    public MVdWPlaceholder(MinecoinMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
        if (placeholderReplaceEvent.getOfflinePlayer() != null) {
            if (plugin.getAPIManager().hasAccount(placeholderReplaceEvent.getOfflinePlayer())) {
                return String.valueOf(plugin.getAPIManager().getBalance(placeholderReplaceEvent.getOfflinePlayer()));
            }
        }
        return "0";
    }
}
