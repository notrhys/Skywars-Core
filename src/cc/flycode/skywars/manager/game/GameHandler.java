package cc.flycode.skywars.manager.game;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.config.ConfigValues;
import cc.flycode.skywars.util.InventoryUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by FlyCode on 14/06/2019 Package cc.flycode.skywars.manager.game
 */
public class GameHandler {
    public GameHandler() {
        Skywars.instance.dataManager.secondsToStart = ConfigValues.startCountdown;
        new BukkitRunnable() {
            int countdown = ConfigValues.startCountdown, defaultTime = ConfigValues.startCountdown, split, current = 0;
            @Override
            public void run() {
                int online = Math.abs(Bukkit.getServer().getOnlinePlayers().size() - Skywars.instance.staffUtil.getStaffList().size());
                if (online>= ConfigValues.minPlayers) {
                    if (!Skywars.instance.dataManager.hasStarted && (System.currentTimeMillis() - Skywars.instance.lastFailedCageGeneration) > 500L) {
                        if (countdown < 1) {
                            Skywars.instance.dataManager.hasStarted = true;
                            Skywars.instance.executorData.miscExecutor.execute(() -> {
                                if (Skywars.instance.jedis != null && !ConfigValues.developerMode) Skywars.instance.jedis.set(ConfigValues.serverName, "true:" + (Bukkit.getOnlinePlayers().size()) + ":true");
                            });
                            Bukkit.broadcastMessage(ChatColor.GREEN + "The game has started, Good Luck!");
                            Skywars.instance.userManager.getUsers().forEach((user -> {
                                user.deleteCage();
                                user.player.sendTitle(ChatColor.GREEN + "The game has started", ChatColor.RED + "Good Luck!");
                                user.player.closeInventory();
                                if (!Skywars.instance.staffUtil.isStaffEnabled(user.player)) {
                                    user.player.getInventory().clear();
                                }
                                Skywars.instance.kitManager.action(user);
                            }));
                            //Normal chests
                            for (String normalLocations : Skywars.instance.dataManager.normalLocationsForCurrentMap) {
                                double x = Double.parseDouble(normalLocations.split(":")[0]);
                                double y = Double.parseDouble(normalLocations.split(":")[1]);
                                double z = Double.parseDouble(normalLocations.split(":")[2]);
                                Location location = new Location(Bukkit.getWorld(Skywars.instance.dataManager.mapName), x, y + 1, z);
                                Block block = Bukkit.getWorld(Skywars.instance.dataManager.mapName).getBlockAt(location);
                                if (block != null && (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST)) {
                                    Chest chest = (Chest) block.getState();
                                    if (chest != null) {
                                        InventoryUtils.update(chest.getBlockInventory());
                                    }
                                }
                            }

                            //OP chests
                            for (String normalLocations : Skywars.instance.dataManager.opLocationsForCurrentMap) {
                                double x = Double.parseDouble(normalLocations.split(":")[0]);
                                double y = Double.parseDouble(normalLocations.split(":")[1]);
                                double z = Double.parseDouble(normalLocations.split(":")[2]);
                                Location location = new Location(Bukkit.getWorld(Skywars.instance.dataManager.mapName), x, y + 1, z);
                                Block block = Bukkit.getWorld(Skywars.instance.dataManager.mapName).getBlockAt(location);
                                if (block != null && (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST)) {
                                    Chest chest = (Chest) block.getState();
                                    if (chest != null) {
                                        InventoryUtils.updateOP(chest.getBlockInventory());
                                    }
                                }
                            }
                        }
                        if (split % 10 == 0 && countdown > 0) {
                            split = 0;
                            Skywars.instance.userManager.getUsers().forEach((user) -> {
                                user.player.playSound(user.player.getLocation(), Sound.LEVEL_UP, 10, 10);
                                user.player.sendTitle(ChatColor.GREEN + "Game will start in", ChatColor.RED + String.valueOf(countdown));
                            });
                            Bukkit.broadcastMessage(ChatColor.RED + "Starting in " + ChatColor.DARK_RED + countdown + ChatColor.RED + "...");
                        }
                        if (countdown > 0 && countdown < 6) {
                            Skywars.instance.userManager.getUsers().forEach((user) -> {
                                user.player.playSound(user.player.getLocation(), Sound.LEVEL_UP, 10, 10);
                                user.player.sendTitle(ChatColor.GREEN + "Game will start in", ChatColor.RED + String.valueOf(countdown));
                            });
                            Bukkit.broadcastMessage(ChatColor.RED + "Starting in " + ChatColor.DARK_RED + countdown + ChatColor.RED + "...");
                        }
                        split++;
                    }
                    if (countdown > 0) countdown--;
                    if (Skywars.instance.dataManager.secondsToStart > 0) Skywars.instance.dataManager.secondsToStart--;
                } else {
                    countdown = defaultTime;
                    split = 0;
                    Skywars.instance.dataManager.secondsToStart = defaultTime;
                }
                if (Skywars.instance.dataManager.hasStarted) {
                    current++;
                    if (current > 5) {
                        Skywars.instance.dataManager.allowTabUpdate = true;
                        Skywars.instance.dataManager.allowDamage = true;
                    }
                }
            }
        }.runTaskTimer(Skywars.instance, 20L, 20L);
    }
    public void wonGame(Player player) {
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
