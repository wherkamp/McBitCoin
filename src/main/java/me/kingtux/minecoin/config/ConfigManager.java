package me.kingtux.minecoin.config;

import me.kingtux.minecoin.MinecoinMain;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigManager {
    private MinecoinMain mainclass;
    private File mainConfig;

    private File playerFile;

    public FileConfiguration getPlayerConfig() {
        return playerConfig;
    }

    private FileConfiguration playerConfig;


    public ConfigManager(MinecoinMain mainclass) {
        this.mainclass = mainclass;
    }

    public void setupConfig() {
        mainConfig = new File(mainclass.getDataFolder(), "config.yml");
        if (!(mainConfig.exists())) {
            //Create The Config
            mainclass.saveDefaultConfig();
            mainclass.getLogger().log(Level.WARNING, "Created Main Config");
        }
        playerFile = new File(mainclass.getDataFolder(), "playerData.yml");

        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playerConfig = new YamlConfiguration();
        try {
            playerConfig.load(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        setupConfigSettings();
    }

    public void setupConfigSettings() {
        mainclass.setConfigSettings(new ConfigSettings(this));
    }

    public FileConfiguration getMainConfig() {
        return mainclass.getConfig();
    }

    public void reloadConfig(ConfigSettings configSettings) {
        try {
            getMainConfig().load(mainConfig);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            mainclass.getConfigSettings().reloadConfig();
            e.printStackTrace();
        }
    }

}
