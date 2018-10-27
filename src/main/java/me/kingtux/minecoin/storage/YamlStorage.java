package me.kingtux.minecoin.storage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Future;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.kingtux.minecoin.MineCoinMain;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlStorage extends Storage {

    private MineCoinMain mineCoinMain;
    private FileConfiguration fileConfiguration;
    private File playerFile;

    public YamlStorage(MineCoinMain mineCoinMain) {
        super("yaml");
        this.mineCoinMain = mineCoinMain;
        playerFile = new File(mineCoinMain.getDataFolder(), "playerData.yml");
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileConfiguration = YamlConfiguration
                .loadConfiguration(playerFile);

    }

    @Override
    public Future<Integer> getBalance(OfflinePlayer player) {
        return getExecutor().submit(() -> fileConfiguration.getInt("players." + player.getUniqueId().toString()));
    }

    @Override
    public Future<Void> setBalance(OfflinePlayer player, int balance) {
        return getExecutor().submit(() -> {
            fileConfiguration.set("players." + player.getUniqueId().toString(),
                    balance);
            saveAndReload();
            return null;
        });
    }

    @Override
    public Future<Boolean> hasAccount(OfflinePlayer player) {
        return getExecutor().submit(() -> fileConfiguration.contains("players." + player.getUniqueId().toString()));
    }


    @Override
    public Future<Void> createAccount(OfflinePlayer player) {

        return getExecutor().submit(() -> {
            fileConfiguration.set("players." + player.getUniqueId().toString(),
                    mineCoinMain.getConfigSettings().getDefaultBalance());
            saveAndReload();
            return null;
        });
    }

    private void saveAndReload() {
        saveAndClose();
        try {
            fileConfiguration.load(playerFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAndClose() {
        try {
            fileConfiguration.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
