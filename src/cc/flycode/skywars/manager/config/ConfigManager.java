package cc.flycode.skywars.manager.config;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.util.file.ConfigFile;
import cc.flycode.skywars.util.file.MapRotationFile;
import cc.flycode.skywars.util.file.ScoreboardFile;

/**
 * Created by FlyCode on 14/06/2019 Package cc.flycode.skywars.manager.config
 */
public class ConfigManager {
    public void load() {
        ConfigFile.getInstance().setup(Skywars.instance);
        ScoreboardFile.getInstance().setup(Skywars.instance);
        MapRotationFile.getInstance().setup(Skywars.instance);
        ConfigValues.maps = MapRotationFile.getInstance().getData().getStringList("Maps");
        ConfigValues.maxMinutes = ConfigFile.getInstance().getData().getInt("Max.minutes");
        ConfigValues.maxSeconds = ConfigFile.getInstance().getData().getInt("Max.gzconds");
   //     ConfigValues.maxPlayers = ConfigFile.getInstance().getData().getInt("Max.players");
        ConfigValues.minPlayers = ConfigFile.getInstance().getData().getInt("Min.players");
        ConfigValues.startCountdown = ConfigFile.getInstance().getData().getInt("StartCountdown");
        ConfigValues.serverName = ConfigFile.getInstance().getData().getString("name");
        ConfigValues.developerMode = ConfigFile.getInstance().getData().getBoolean("dev-mode");

        ConfigValues.preGameScoreboard = ScoreboardFile.getInstance().getData().getStringList("PreGame");
    }
}
