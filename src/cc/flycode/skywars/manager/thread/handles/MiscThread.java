package cc.flycode.skywars.manager.thread.handles;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.util.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.manager.thread.handles
 */
public class MiscThread {
    public void init() {
        Skywars.instance.executorData.miscExecutor.scheduleAtFixedRate(() -> Skywars.instance.userManager.getUsers().forEach(user -> {
            if (!Skywars.instance.dataManager.hasStarted || !Skywars.instance.dataManager.inGame.contains(user.player)) {
                user.player.setHealth(20);
                user.player.setFoodLevel(20);
            }
            if (!Skywars.instance.dataManager.inGame.contains(user.player) && Skywars.instance.dataManager.allowTabUpdate) {
                user.player.setPlayerListName(user.player.getName() + " " + ChatColor.RED + "(Spectator)");
            } else {
                user.player.setPlayerListName(user.player.getName());
            }
        }), 1L, 1L, TimeUnit.MILLISECONDS);
        Skywars.instance.executorData.miscExecutor.scheduleAtFixedRate(() -> Bukkit.getServer().getWorlds().forEach(world -> world.setStorm(false)), 1L, 1L, TimeUnit.MILLISECONDS);
    }
}
