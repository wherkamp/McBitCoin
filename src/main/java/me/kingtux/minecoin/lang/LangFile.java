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
    BALANCE_MESSAGE_OTHER("{player} balance is {balance}"),
    @ConfigEntry(path = "messages.coin-eco.add.sender")
    COIN_ECO_ADD_SENDER("You have added {amount} to {receiver}"),
    @ConfigEntry(path = "messages.coin-eco.set.sender")
    COIN_ECO_SET_SENDER("You set {receiver} balance to {balance}"),
    @ConfigEntry(path = "messages.coin-eco.subtract.sender")
    COIN_ECO_SUBTRACT_SENDER("You removed {amount} from {receiver}"),
    @ConfigEntry(path = "messages.coin-eco.add.receiver")
    COIN_ECO_ADD_RECEIVER("{sender} sent you {amount}"),
    @ConfigEntry(path = "messages.coin-eco.set.receiver")
    COIN_ECO_SET_RECEIVER("{sender} set your balance to {amount}"),
    @ConfigEntry(path = "messages.coin-eco.subtract.receiver")
    COIN_ECO_SUBTRACT_RECEIVER("{sender} removed {amount}"),
    @ConfigEntry(path = "messages.funding.lack.you")
    LACK_OF_FUNDING("You lack the money to do that!"),
    @ConfigEntry(path = "messages.pay.format")
    PAY_INVALID_FORMAT("Invalid format please use /pay {who} amount"),
    @ConfigEntry(path = "messages.must-be-an-int")
    MUST_BE_AN_INT("Amount must be an int"),

    @ConfigEntry(path = "messages.coin-eco.format")
    COIN_ECO_FORMAT("Invalid Format please use /coineco <add,set,subtract> {who} {amount}"),

    @ConfigEntry(path = "messages.coin-eco.format")
    PAY_SENT("You sent {amount} to {receiver}"),
    @ConfigEntry(path = "messages.pay.recieve")
    PAY_RECEIVE("{sender} sent you {amount}"),

    @ConfigEntry(path = "messages.funding.lack.other")
    LACK_OF_FUNDING_OTHER("{player} lack the money to do that!");

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
