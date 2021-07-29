package cc.flycode.skywars.manager.user;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.kits.KitManager;
import cc.flycode.skywars.util.file.MapFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by FlyCode on 11/06/2019 Package cc.flycode.skywars.manager.user
 */
public class User {
    public Player player;
    public UUID uuid;
    public List<Block> placedBlocks;
    public List<Location> cageBlocks;
    public Location lastGroundLocation, currentCageLocation;
    public boolean build, spectator, onGround, hasCage, genedCage, hasPreScoreboard, hasNormalScoreboard, disableDamage;
    public int cage;
    public Location cageLocation;
    public int kills = 0, selectedCage;
    public KitManager.Kits selectedKit;
    public HashMap<String, HashMap> kitsData = new HashMap<>();

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        placedBlocks = new ArrayList<>();
        cageBlocks = new ArrayList<>();

        if (!Skywars.instance.staffUtil.isStaffEnabled(player)) {
            Collections.shuffle(Skywars.instance.avdCages);
            for (Integer integer : Skywars.instance.avdCages) {
                selectedCage = integer;
                Skywars.instance.avdCages.remove(integer);
                break;
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                addScoreboard();
                this.cancel();
            }
        }.runTaskTimer(Skywars.instance, 20L, 20L);
    }

    public void addScoreboard() {
        Skywars.instance.scoreboardManager.initBoard(this);
    }

    public void giveKitSelectorItem() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack kitSelectionItem = new ItemStack(Material.DIAMOND, 1);
                ItemMeta itemMeta = kitSelectionItem.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Kits");
                kitSelectionItem.setItemMeta(itemMeta);
                player.getInventory().setItem(4, kitSelectionItem);
                this.cancel();
            }
        }.runTaskTimer(Skywars.instance, 30L, 30L);
    }

    public void onJoin() {
        hasNormalScoreboard = false;
        hasPreScoreboard = false;
        createCage();
    }



    public void createCage() {


        Skywars.instance.dataManager.takenCages++;
        Skywars.instance.dataManager.gayCage--;

        //    int min = Skywars.instance.dataManager.cagesOpen, max = Skywars.instance.dataManager.takenCages;
        int randomCage = selectedCage;

         /*   do {
                randomCage = MathUtil.getRandomInteger(min, max);

            } while (Skywars.instance.dataManager.randomNumbers.contains(randomCage));*/

        int finalRandomCage = randomCage;

        cage = Skywars.instance.dataManager.takenCages;
        Skywars.instance.dataManager.randomNumbers.add(finalRandomCage);

        MapFile.getInstance().setup(Skywars.instance, Skywars.instance.dataManager.mapName);

        Location bottomBlock = new Location(Bukkit.getWorld(MapFile.getInstance().getData().getString("cage." + finalRandomCage + ".World")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + finalRandomCage + ".X")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + finalRandomCage + ".Y")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + finalRandomCage + ".Z")));
    //    player.teleport(bottomBlock);
        bottomBlock.setY(bottomBlock.getY() + 2);
        cageLocation = bottomBlock;

        //add cage locations
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY(), bottomBlock.getZ()));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 1, bottomBlock.getZ()));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 1, bottomBlock.getZ() + 1));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 1, bottomBlock.getZ() - 1));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 1, bottomBlock.getZ() - 2 + 2));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 2, bottomBlock.getZ()));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 2, bottomBlock.getZ() + 1));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 2, bottomBlock.getZ() - 1));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 2, bottomBlock.getZ() - 2 + 2));

        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 3, bottomBlock.getZ()));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 3, bottomBlock.getZ() + 1));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 3, bottomBlock.getZ() - 1));
        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 3, bottomBlock.getZ() - 2 + 2));

        cageBlocks.add(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 4, bottomBlock.getZ()));

        //spawn cage
        player.getWorld().getBlockAt(bottomBlock).setType(Material.GLASS);

        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 1, bottomBlock.getZ())).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 1, bottomBlock.getZ() + 1)).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 1, bottomBlock.getZ() - 1)).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 1, bottomBlock.getZ() - 2 + 2)).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 2, bottomBlock.getZ())).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 2, bottomBlock.getZ() + 1)).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 2, bottomBlock.getZ() - 1)).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 2, bottomBlock.getZ() - 2 + 2)).setType(Material.GLASS);

        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 3, bottomBlock.getZ())).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 3, bottomBlock.getZ() + 1)).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 3, bottomBlock.getZ() - 1)).setType(Material.GLASS);
        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 3, bottomBlock.getZ() - 2 + 2)).setType(Material.GLASS);


        player.getWorld().getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 4, bottomBlock.getZ())).setType(Material.GLASS);
        hasCage = true;
        genedCage = true;
        Location location = cageLocation.clone().add(0, 1.5, 0);
        location.setYaw(50.0f);
        location.setPitch(20.0f);
        player.teleport(location);
        player.setVelocity(player.getLocation().getDirection().multiply(-1));
        currentCageLocation = bottomBlock;
    }

    public void deleteCage() {
        try {
            if (cageBlocks.size() > 0) {
                for (Location location : cageBlocks) {
                    Block block = player.getWorld().getBlockAt(location);
                    if (block != null) block.setType(Material.AIR);
                }
            }
        } catch (Exception ignored) {}
    }
}
