package cc.flycode.skywars.manager.listeners;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.config.ConfigValues;
import cc.flycode.skywars.manager.user.User;
import cc.flycode.skywars.util.LocationUtil;
import cc.flycode.skywars.util.MathUtil;
import cc.flycode.skywars.util.file.MapFile;
import cc.flycode.staff.events.StaffToggleEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by FlyCode on 11/06/2019 Package cc.flycode.skywars.manager.listeners
 */

public class JoinListener implements Listener {

    @EventHandler
    public void onStaffUpdate(StaffToggleEvent e) {
        if (e.getType() == StaffToggleEvent.Type.OFF) {
          if (e.getPlayer().getScoreboard() != null) e.getPlayer().setScoreboard(Skywars.instance.emptScoreboard);
            doJoinThing(e.getPlayer());
            User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
            if (user != null) user.giveKitSelectorItem();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
        if (user == null) {
            Skywars.instance.userManager.addUser(new User(e.getPlayer()));
        }
       doJoinThing(e.getPlayer());
       new BukkitRunnable() {
           @Override
           public void run() {
               if (Skywars.instance.staffUtil.isStaffEnabled(e.getPlayer())) {
                   doSpecThing(e.getPlayer());
               } else {
                   if (!Skywars.instance.dataManager.hasStarted) {
                       User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
                       if (user != null && !Skywars.instance.staffUtil.isStaffEnabled(user.player)) user.giveKitSelectorItem();
                   }
               }
               this.cancel();
           }
       }.runTaskTimer(Skywars.instance, 5L, 5L);
    }

    private void doJoinThing(Player player) {
        Skywars.instance.executorData.miscExecutor.execute(() -> {
            if (Skywars.instance.jedis != null && !ConfigValues.developerMode)
                Skywars.instance.jedis.set(ConfigValues.serverName, "true:" + (Bukkit.getOnlinePlayers().size()) + ":" + (Skywars.instance.dataManager.hasStarted));
        });
        User user = Skywars.instance.userManager.getUser(player.getUniqueId());
        if (user != null) {
            if (!Skywars.instance.dataManager.hasStarted) {
                doAction(player);
                if (user != null) user.giveKitSelectorItem();
            } else {
                doSpecThing(player);
            }
        }
    }


    public void doSpecThing(Player p) {
        User user1 = Skywars.instance.userManager.getUser(p.getUniqueId());
        if (user1 != null) user1.spectator = true;
        Skywars.instance.dataManager.inGame.forEach(current -> current.hidePlayer(p));
        p.getPlayer().getInventory().clear();
        p.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
        p.getPlayer().getActivePotionEffects().forEach(pot -> p.getPlayer().removePotionEffect(pot.getType()));
        p.getPlayer().setHealth(20);
        p.getPlayer().setFoodLevel(20);
        p.getPlayer().setAllowFlight(true);
        p.getPlayer().setFlying(true);
        p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true));
        new BukkitRunnable() {
            @Override
            public void run() {
                int cage = MathUtil.getRandomInteger(ConfigValues.minPlayers, Skywars.instance.dataManager.maxPlayers);
                MapFile.getInstance().setup(Skywars.instance, Skywars.instance.dataManager.mapName);
                Location spawnLocation = new Location(Bukkit.getWorld(MapFile.getInstance().getData().getString("cage." + cage + ".World")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + cage + ".X")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + cage + ".Y")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + cage + ".Z")));
                p.teleport(spawnLocation.clone().add(0,-2,0));
              if (!Skywars.instance.staffUtil.isStaffEnabled(p))  p.sendMessage(ChatColor.GREEN + "The game has already started! but you can spectate the game.");
                this.cancel();
            }
        }.runTaskTimer(Skywars.instance, 5L, 5L);
    }

    public void doAction(Player p) {
        if (Skywars.instance.dataManager.hasStarted) return;
        if (!Skywars.instance.dataManager.inGame.contains(p.getPlayer()))
            Skywars.instance.dataManager.inGame.add(p.getPlayer());

        p.getPlayer().getInventory().clear();
        p.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
        p.getPlayer().getActivePotionEffects().forEach(pot -> p.getPlayer().removePotionEffect(pot.getType()));
        p.getPlayer().setHealth(20);
        p.getPlayer().setFoodLevel(20);
        p.getPlayer().setAllowFlight(false);
        p.getPlayer().setFlying(false);

        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(new Location(Bukkit.getWorld(Skywars.instance.dataManager.mapName), 0, 50, 0));
                this.cancel();
            }
        }.runTaskTimer(Skywars.instance, 2L, 2L);

        long time = 5L;
        if (!p.getPlayer().getWorld().getName().equalsIgnoreCase(Skywars.instance.dataManager.mapName)) {
            time = 10L;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                User user = Skywars.instance.userManager.getUser(p.getPlayer().getUniqueId());
                if (user != null) {
                    if (!Skywars.instance.staffUtil.isStaffEnabled(user.player)) {
                        user.onJoin();
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(Skywars.instance, time, time);
    }
}
