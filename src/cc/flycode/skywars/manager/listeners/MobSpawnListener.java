package cc.flycode.skywars.manager.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Created by FlyCode on 13/08/2019 Package cc.flycode.skywars.manager.listeners
 */
public class MobSpawnListener implements Listener {
    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }
}
