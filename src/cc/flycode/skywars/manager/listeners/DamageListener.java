package cc.flycode.skywars.manager.listeners;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.game.GameHandler;
import cc.flycode.skywars.manager.user.User;
import cc.flycode.skywars.util.CoinUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.manager.listeners
 */
public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (!Skywars.instance.dataManager.hasStarted) e.setCancelled(true);
            User user = Skywars.instance.userManager.getUser(e.getEntity().getUniqueId());
            if (user != null) {
                if (user.spectator) e.setCancelled(true);
                if (user.disableDamage) {
                    e.setCancelled(true);
                    return;
                }
                if (!Skywars.instance.dataManager.hasStarted) {
                    if (e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true);
                } else {
                    if (!Skywars.instance.dataManager.inGame.contains(((Player) e.getEntity()).getPlayer()) || !Skywars.instance.dataManager.allowDamage) e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Skywars.instance.dataManager.deaths++;
        if (e.getEntity().getKiller() != null) {
            User killerUser = Skywars.instance.userManager.getUser(e.getEntity().getKiller().getUniqueId());
            if (killerUser != null) killerUser.kills++;
            e.setDeathMessage(ChatColor.RED + e.getEntity().getName() + " " + ChatColor.GRAY + "was killed by " + ChatColor.RED + e.getEntity().getKiller().getName());
            new BukkitRunnable() {
                @Override
                public void run() {
                    User user = Skywars.instance.userManager.getUser(e.getEntity().getUniqueId());
                    if (user != null) {
                        e.getEntity().teleport((user.lastGroundLocation != null ? user.lastGroundLocation : user.player.getLocation()));
                    }
                    this.cancel();
                }
            }.runTaskTimer(Skywars.instance, 5L, 5L);

        } else {
            e.setDeathMessage(ChatColor.RED + e.getEntity().getName() + " died");
            new BukkitRunnable() {
                @Override
                public void run() {
                    User user = Skywars.instance.userManager.getUser(e.getEntity().getUniqueId());
                    if (user != null && user.currentCageLocation != null) {
                        user.player.teleport(user.currentCageLocation);
                    }
                    this.cancel();
                }
            }.runTaskTimer(Skywars.instance, 5L, 5L);
        }
        if (Skywars.instance.dataManager.inGame.contains(e.getEntity().getPlayer()))
            Skywars.instance.dataManager.inGame.remove(e.getEntity().getPlayer());
        User user1 = Skywars.instance.userManager.getUser(e.getEntity().getUniqueId());
        if (user1 != null) user1.spectator = true;
        e.getEntity().setAllowFlight(true);
        e.getEntity().setFlying(true);
        e.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true));
        e.getEntity().setHealth(20);
        Skywars.instance.userManager.getUsers().stream().filter(user -> Skywars.instance.dataManager.inGame.contains(user.player)).forEach(user -> user.player.hidePlayer(e.getEntity().getPlayer()));
        Skywars.instance.userManager.getUsers().stream().filter(user -> !Skywars.instance.dataManager.inGame.contains(user.player)).forEach(user -> user.player.showPlayer(e.getEntity().getPlayer()));
        Skywars.instance.userManager.getUsers().stream().filter(users -> !Skywars.instance.dataManager.inGame.contains(users.player)).forEach(users -> {
            users.player.showPlayer(e.getEntity());
            e.getEntity().showPlayer(users.player);
        });
        if (!Skywars.instance.dataManager.wonGame && Skywars.instance.dataManager.inGame.size() < 2) {
            Skywars.instance.dataManager.wonGame = true;
            if (e.getEntity().getKiller() != null) {
                Skywars.instance.coinUtil.updateCoins(e.getEntity().getKiller(), CoinUtil.Types.ADD, 5.00);
                Bukkit.broadcastMessage(ChatColor.RED + e.getEntity().getKiller().getName() + ChatColor.GREEN + " has won the game!");
            } else {
                Player player = null;
                for (Player player1 : Skywars.instance.dataManager.inGame) {
                    player = player1;
                    break;
                }
                Skywars.instance.coinUtil.updateCoins(player, CoinUtil.Types.ADD, 5.00);
                Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.GREEN + " has won the game!");
            }
            new GameHandler().wonGame(e.getEntity());
        }
    }
    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (!Skywars.instance.dataManager.inGame.contains(((Player) e.getDamager()).getPlayer())) e.setCancelled(true);
        }
    }
}
