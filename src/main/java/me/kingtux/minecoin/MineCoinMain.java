package me.kingtux.minecoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import me.kingtux.minecoin.commands.BalanceCommand;
import me.kingtux.minecoin.commands.CoinecoCommand;
import me.kingtux.minecoin.commands.PayCommand;
import me.kingtux.minecoin.config.ConfigManager;
import me.kingtux.minecoin.config.ConfigSettings;
import me.kingtux.minecoin.lang.ConfigEntry;
import me.kingtux.minecoin.lang.ConfigValue;
import me.kingtux.minecoin.lang.LangFile;
import me.kingtux.minecoin.listeners.PlayerEvents;
import me.kingtux.minecoin.placeholders.PlaceholderLoader;
import me.kingtux.minecoin.storage.MysqlStorage;
import me.kingtux.minecoin.storage.Storage;
import me.kingtux.minecoin.storage.YamlStorage;
import me.kingtux.simpleannotation.AnnotationFinder;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public final class MineCoinMain extends JavaPlugin {

    private ConfigSettings configSettings;
    private ConfigManager configManager;
    private MineCoinAPI MinecoinAPI;
    private Storage storage;


    @Override
    public void onEnable() {
        try {
            if (isUpTodate()) {
                getLogger().log(Level.INFO, "You are ok and up to date");
            } else {
                getLogger().log(Level.WARNING, "You are not up to date. I recommend updating.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        configManager = new ConfigManager(this);
        configManager.setupConfig();

        storage = loadStorage();

        registerCommands();
        registerEvents();
        MinecoinAPI = new MineCoinAPI(this);

        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("used_storage_type", () -> MineCoinMain.this.getStorage().getName()));

        getServer().getScheduler().runTaskLater(this, () -> loadPlaceHolders(), 1);
        try {
            loadLang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLang() throws Exception {
        File langFile = new File(getDataFolder(), "lang.yml");
        Field[] fields = AnnotationFinder.getFieldsWithAnnotation(LangFile.class, ConfigEntry.class);
        //There should only be one of them.
        Field editibleThing = AnnotationFinder.getFieldsWithAnnotation(LangFile.class, ConfigValue.class)[0];
        editibleThing.setAccessible(true);
        if (!langFile.exists()) {
            try {
                langFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(langFile);
        for (Field field : fields) {
            field.setAccessible(true);
            ConfigEntry configEntry = field.getAnnotationsByType(ConfigEntry.class)[0];
            if (!(fileConfiguration.contains(configEntry.path())) || fileConfiguration.get(configEntry.path()) == null) {
                fileConfiguration.set(configEntry.path(), "'" + editibleThing.get(LangFile.valueOf(field.getName())) + "'");
            }
        }
        fileConfiguration.save(langFile);
        for (Field field : fields) {
            field.setAccessible(true);
            ConfigEntry configEntry = field.getAnnotationsByType(ConfigEntry.class)[0];
            if (fileConfiguration.get(configEntry.path()) != null) {
                editibleThing.set(LangFile.valueOf(field.getName()), fileConfiguration.get(configEntry.path()));
            }

        }
    }

    private void loadPlaceHolders() {
        //Run on first tick to make sure plugin is loaded

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().log(Level.INFO, "PlayerHolderAPI found");
            new PlaceholderLoader(this).loadPlaceHolderAPI();
        } else {
            getLogger().log(Level.INFO, "PlaceHolderAPI not found");
        }
        getLogger().log(Level.INFO, "Placeholders loaded");


    }


    @Override
    public void onDisable() {
        storage.saveAndClose();
    }

    private Storage loadStorage() {
        if (configSettings.getConfigType().equalsIgnoreCase("mysql")) {
            return new MysqlStorage(this);
        } else if (configSettings.getConfigType().equalsIgnoreCase("yaml")) {
            return new YamlStorage(this);
        } else {
            return new YamlStorage(this);
        }
    }

    private boolean isUpTodate() throws IOException {
        URL url = null;
        url = new URL("https://api.spigotmc.org/legacy/update.php?resource=53646");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String reply;
        while ((reply = br.readLine()) != null) {
            if (Double.parseDouble(reply) == Double.parseDouble(this.getDescription().getVersion())) {
                return true;
            } else if (Double.parseDouble(reply) < Double
                    .parseDouble(this.getDescription().getVersion())) {
                getLogger().log(Level.INFO, "You are using a dev Version");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void registerCommands() {
        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("coineco").setExecutor(new CoinecoCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
    }

    public ConfigSettings getConfigSettings() {
        return configSettings;
    }

    public void setConfigSettings(ConfigSettings configSettings) {
        this.configSettings = configSettings;
    }

    public MineCoinAPI getAPIManager() {
        return MinecoinAPI;
    }


    public Storage getStorage() {
        return storage;
    }
}


