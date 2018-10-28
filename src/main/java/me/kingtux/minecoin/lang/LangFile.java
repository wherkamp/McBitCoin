package me.kingtux.minecoin.lang;

import me.kingtux.minecoin.Utils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public enum LangFile {
    @ConfigEntry(path = "messages.missing-permission")
    LACK_PERMS("You lack the permissions to do that!"),
    @ConfigEntry(path = "messages.you-are-not-a-player")
    NOT_A_PLAYER("You are not a player!"),
    @ConfigEntry(path = "messages.player-not-found")
    MISSING_PLAYER("{name} does not exist"),
    @ConfigEntry(path = "messages.balance.personal")
    BALANCE_MESSAGE_YOU("Your balance is {balance}"),
    @ConfigEntry(path = "messages.balance.other")
    BALANCE_MESSAGE_OTHER("{player} balance is {balance}");
    @ConfigValue
    private String value;

    LangFile(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getColorValue() {
        return ChatColor.translateAlternateColorCodes('&', getValue());
    }

    public String getFormattedValue(OfflinePlayer player) {
        return Utils.placeholderAPI(getColorValue(), player);
    }
}
