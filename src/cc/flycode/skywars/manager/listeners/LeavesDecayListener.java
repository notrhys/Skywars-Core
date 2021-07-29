package cc.flycode.skywars.manager.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Created by FlyCode on 23/08/2019 Package cc.flycode.skywars.manager.listeners
 */
public class LeavesDecayListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public static void LeavesDecayEvent(org.bukkit.event.block.LeavesDecayEvent event) {
        event.setCancelled(true);
    }
}
