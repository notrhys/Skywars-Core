package cc.flycode.skywars.manager.listeners;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.config.ConfigValues;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Created by FlyCode on 12/08/2019 Package cc.flycode.skywars.manager.listeners
 */
public class PreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
    //    if (Bukkit.getOnlinePlayers().size() >= ConfigValues.maxPlayers) e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.AQUA + "Server is full!");
        if (Skywars.instance.dataManager.denyLogin) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.GOLD + "Skywars is still being setup on this server! please wait.");
        }
    }
}
