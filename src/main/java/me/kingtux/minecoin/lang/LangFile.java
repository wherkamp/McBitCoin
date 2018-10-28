package me.kingtux.minecoin.lang;

import org.bukkit.ChatColor;

public enum LangFile {
    @ConfigEntry(path = "messages.missing-permission")
    LACK_PERMS("You lack the permissions to do that!"),
    @ConfigEntry(path = "messages.you-are-not-a-player")
    NOT_A_PLAYER("You are not a player!"),
    @ConfigEntry(path="messages.player-not-found")
    MISSING_PLAYER("{name} does not exist");
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
}
