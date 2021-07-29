package cc.flycode.skywars.util.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFile {

    private ConfigFile() { }

    static ConfigFile instance = new ConfigFile();

    public static ConfigFile getInstance() {
        return instance;
    }

    Plugin p;

    FileConfiguration config;
    File cfile;

    FileConfiguration data;
    File dfile;

    public void setup(Plugin p) {
        config = p.getConfig();
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
         dfile = new File("plugins/Skywars/config.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            }
            catch (IOException e) {
            }
        }

        data = YamlConfiguration.loadConfiguration(dfile);
        defaults();
    }

    public FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(dfile);
        }
        catch (IOException e) {
        }
    }

    public void defaults() {
        if (!data.contains("Maps")) {
            List<String> array = new ArrayList<>();
            array.add("skywars1");
            array.add("skywars2");
            array.add("skywars3");
            data.set("Maps",array);
        }
        if (!data.contains("Max.seconds")) data.set("Max.seconds",60);
        if (!data.contains("Max.minutes")) data.set("Max.minutes",20);
        if (!data.contains("Max.players")) data.set("Max.players",12);
        if (!data.contains("Min.players")) data.set("Min.players",2);
        if (!data.contains("StartCountdown")) data.set("StartCountdown", 20);
        if (!data.contains("name")) data.set("name", "Skywars-01");
        if (!data.contains("dev-mode")) data.set("dev-mode", false);
        saveData();
    }


    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dfile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        }
        catch (IOException e) {
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}