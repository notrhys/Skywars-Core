package cc.flycode.skywars.manager.listeners;

import cc.flycode.skywars.Skywars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by FlyCode on 13/06/2019 Package cc.flycode.skywars.manager.listeners
 */
public class ItemEvent implements Listener {
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        if (!Skywars.instance.dataManager.inGame.contains(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onItrmDrop(PlayerDropItemEvent e) {
        if (!Skywars.instance.dataManager.inGame.contains(e.getPlayer())) e.setCancelled(true);
    }

}
