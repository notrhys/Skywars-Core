package cc.flycode.skywars.manager.listeners;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.config.ConfigValues;
import cc.flycode.skywars.manager.user.User;
import cc.flycode.staff.events.StaffToggleEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by FlyCode on 11/06/2019 Package cc.flycode.skywars.manager.listeners
 */
public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        doQuitThing(e.getPlayer());
        Skywars.instance.dataManager.hasScoreboard.remove(e.getPlayer());
        User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Skywars.instance.userManager.removeUser(user);
                }
            }.runTask(Skywars.instance);
        }
    }

    @EventHandler
    public void onStaffUpdate(StaffToggleEvent e) {
        if (e.getType() == StaffToggleEvent.Type.ON) {
            if (e.getPlayer().getScoreboard() != null) e.getPlayer().setScoreboard(Skywars.instance.emptScoreboard);
            doQuitThing(e.getPlayer());
        }
    }

    public void doQuitThing(Player player) {
        Skywars.instance.dataManager.currentCages--;
        Skywars.instance.dataManager.takenCages--;
        Skywars.instance.executorData.miscExecutor.execute(() -> {
            if (Skywars.instance.jedis != null && !ConfigValues.developerMode)
                Skywars.instance.jedis.set(ConfigValues.serverName, "true:" + (Bukkit.getOnlinePlayers().size() - 1) + ":" + (Skywars.instance.dataManager.hasStarted));
        });
        doAction(player);
        User user = Skywars.instance.userManager.getUser(player.getUniqueId());
        if (user != null) {
            //      Bukkit.broadcastMessage("added back: " + user.selectedCage);
            Skywars.instance.avdCages.add(user.selectedCage);

        }

    }

    public void doAction(Player p) {

        Skywars.instance.dataManager.inGame.remove(p.getPlayer());
        try {
            if (Skywars.instance.dataManager.takenCages > 1) Skywars.instance.dataManager.takenCages--;
            User user = Skywars.instance.userManager.getUser(p.getPlayer().getUniqueId());
            if (user != null) {
                user.deleteCage();
                if (Skywars.instance.dataManager.randomNumbers.contains(user.cage))
                    Skywars.instance.dataManager.randomNumbers.remove(user.cage);

            }

        } catch (Exception ignored) {

        }
        User user = Skywars.instance.userManager.getUser(p.getPlayer().getUniqueId());
        if (user != null) {
            if (!Skywars.instance.dataManager.wonGame && Skywars.instance.dataManager.inGame.size() < 2 && Skywars.instance.dataManager.hasStarted) {
                Skywars.instance.dataManager.wonGame = true;
                Player player = null;
                for (Player player1 : Skywars.instance.dataManager.inGame) {
                    player = player1;
                    break;
                }
                Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.GREEN + " has won the game!");
                new BukkitRunnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        i++;
                        if (i > 3) {
                            Bukkit.getOnlinePlayers().forEach(players -> players.kickPlayer(ChatColor.RED + "Server is restarting"));
                        }
                        if (i > 5) {
                            Bukkit.getServer().shutdown();
                            this.cancel();
                        }
                    }
                }.runTaskTimer(Skywars.instance, 20L, 20L);
            }
        }
    }
}
