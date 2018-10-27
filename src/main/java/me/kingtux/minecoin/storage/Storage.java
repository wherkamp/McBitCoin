package me.kingtux.minecoin.storage;

import org.bukkit.OfflinePlayer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author KingTux
 */
public abstract class Storage {
    private ExecutorService executor
            = Executors.newSingleThreadExecutor();
    private String name;

    public Storage(String name) {
        this.name = name;
    }

    public abstract Future<Integer> getBalance(OfflinePlayer player);

    public abstract Future<Void> setBalance(OfflinePlayer player, int balance);

    public abstract Future<Boolean> hasAccount(OfflinePlayer player);

    public abstract Future<Void> createAccount(OfflinePlayer player);

    public Future<Boolean> subtractBalance(OfflinePlayer player, int amount) {
        if (player == null) executor.submit(() -> false);
        return executor.submit(() -> {
            if (!(canAfford(player, amount).get())) {
                return false;
            }
            setBalance(player, getBalance(player).get() - amount);
            return true;
        });
    }

    public Future<Boolean> canAfford(OfflinePlayer p, int amount) {
        return getExecutor().submit(() -> getBalance(p).get() > amount);
    }

    public Future<Void> addBalance(OfflinePlayer player, int amount) {
        return executor.submit(() -> {
            setBalance(player, getBalance(player).get() + amount);
            return null;
        });
    }


    protected ExecutorService getExecutor() {
        return executor;
    }


    public String getName() {
        return name;
    }

   public abstract void saveAndClose();
}
