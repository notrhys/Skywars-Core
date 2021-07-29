package cc.flycode.skywars.util;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.util.file.MapFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by FlyCode on 12/08/2019 Package cc.flycode.skywars.util
 */
public class CageUtils {
    public static void clearAllCages(World world, int cage) {
        List<Location> cageBlocks = new CopyOnWriteArrayList<>();
        MapFile.getInstance().setup(Skywars.instance, world.getName());
        Skywars.instance.getLogger().info("Cleaning cage at world: " + world.getName() + " number: " + cage);
        try {
            Location bottomBlock = new Location(Bukkit.getWorld(MapFile.getInstance().getData().getString("cage." + cage + ".World")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + cage + ".X")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + cage + ".Y")), Double.valueOf(MapFile.getInstance().getData().getString("cage." + cage + ".Z")));
            bottomBlock.setY(bottomBlock.getY() + 2);

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
            world.getBlockAt(bottomBlock).setType(Material.AIR);

            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 1, bottomBlock.getZ())).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 1, bottomBlock.getZ() + 1)).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 1, bottomBlock.getZ() - 1)).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 1, bottomBlock.getZ() - 2 + 2)).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 2, bottomBlock.getZ())).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 2, bottomBlock.getZ() + 1)).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 2, bottomBlock.getZ() - 1)).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 2, bottomBlock.getZ() - 2 + 2)).setType(Material.AIR);

            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() + 1, bottomBlock.getY() + 3, bottomBlock.getZ())).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 3, bottomBlock.getZ() + 1)).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1 + 1, bottomBlock.getY() + 3, bottomBlock.getZ() - 1)).setType(Material.AIR);
            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX() - 1, bottomBlock.getY() + 3, bottomBlock.getZ() - 2 + 2)).setType(Material.AIR);


            world.getBlockAt(new Location(bottomBlock.getWorld(), bottomBlock.getX(), bottomBlock.getY() + 4, bottomBlock.getZ())).setType(Material.AIR);
        } catch (Exception ignored) {}
    }
}
