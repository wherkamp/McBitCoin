package me.kingtux.minecoin;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

class PlaceHolder {
    static String placeHolderAPI(String what, OfflinePlayer p) {
        return PlaceholderAPI.setPlaceholders(p, what);
    }
}
