package cc.flycode.skywars.manager.listeners;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by FlyCode on 14/08/2019 Package cc.flycode.skywars.manager.listeners
 */
public class InteractListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null && user.spectator) e.setCancelled(true);
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null && user.spectator) e.setCancelled(true);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null && user.spectator) e.setCancelled(true);
    }
}
