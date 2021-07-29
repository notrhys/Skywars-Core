package cc.flycode.skywars.manager.thread.handles;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.user.User;
import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

/**
 * Created by FlyCode on 14/06/2019 Package cc.flycode.skywars.manager.thread.handles
 */
public class ScoreboardThread {
    public void init() {
        Skywars.instance.executorData.scoreboardExecutor.scheduleAtFixedRate(() -> Bukkit.getOnlinePlayers().forEach(player -> {
            User user = Skywars.instance.userManager.getUser(player.getUniqueId());
            if (user != null) {
                Skywars.instance.scoreboardManager.update(user);
            } else {
            }
        }), 1L, 1L, TimeUnit.MILLISECONDS);
    //    Skywars.instance.executorData.scoreboardExecutor.scheduleAtFixedRate(() -> Skywars.instance.userManager.getUsers().forEach(user -> Skywars.instance.scoreboardManager.update(user)), 1L, 1L, TimeUnit.SECONDS);
    }
}
