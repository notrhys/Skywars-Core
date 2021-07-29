package cc.flycode.skywars.manager.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.manager.listeners
 */
public class PreLogin implements Listener {
    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
    //    if (!Skywars.instance.dataManager.hasStarted && Skywars.instance.dataManager.inGame.size() > ConfigValues.maxPlayers) {
      //      e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Game is full");
        //    return;
       // }
   //     if (Skywars.instance.dataManager.hasStarted) e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "The game has already started!");
    }
}
