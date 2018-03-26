package me.kingtux.minecoin.placeholders;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import me.kingtux.minecoin.MineCoinMain;

public class MVdWPlaceholder implements PlaceholderReplacer {
    private MineCoinMain plugin;

    public MVdWPlaceholder(MineCoinMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
        System.out.println("Test");
        if (plugin.getAPIManager().hasAccount(placeholderReplaceEvent.getOfflinePlayer())) {
            return String.valueOf(plugin.getAPIManager().getBalance(placeholderReplaceEvent.getOfflinePlayer()));
        }
        return "0";
    }
}
