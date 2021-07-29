package cc.flycode.skywars.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.util
 */
public class MathUtil {
    private static ImmutableSet<Material> ground = Sets.immutableEnumSet(Material.SUGAR_CANE, Material.SUGAR_CANE_BLOCK,
            Material.TORCH, Material.ACTIVATOR_RAIL, Material.AIR, Material.CARROT, Material.CROPS, Material.DEAD_BUSH,
            Material.DETECTOR_RAIL, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.DOUBLE_PLANT,
            Material.FIRE, Material.GOLD_PLATE, Material.IRON_PLATE, Material.LAVA, Material.LEVER, Material.LONG_GRASS,
            Material.MELON_STEM, Material.NETHER_WARTS, Material.PORTAL, Material.POTATO, Material.POWERED_RAIL,
            Material.PUMPKIN_STEM, Material.RAILS, Material.RED_ROSE, Material.REDSTONE_COMPARATOR_OFF,
            Material.REDSTONE_COMPARATOR_ON, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON,
            Material.REDSTONE_WIRE, Material.SAPLING, Material.SEEDS, Material.SIGN, Material.SIGN_POST,
            Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.STONE_BUTTON, Material.STONE_PLATE,
            Material.SUGAR_CANE_BLOCK, Material.TORCH, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.WALL_SIGN,
            Material.WATER, Material.WEB, Material.WOOD_BUTTON, Material.WOOD_PLATE, Material.YELLOW_FLOWER);

    public static Map<EntityType, Vector> entityDimensions;

    public MathUtil() {
        entityDimensions = new HashMap<>();
        entityDimensions.put(EntityType.WOLF, new Vector(0.31, 0.8, 0.31));
        entityDimensions.put(EntityType.SHEEP, new Vector(0.45, 1.3, 0.45));
        entityDimensions.put(EntityType.COW, new Vector(0.45, 1.3, 0.45));
        entityDimensions.put(EntityType.PIG, new Vector(0.45, 0.9, 0.45));
        entityDimensions.put(EntityType.MUSHROOM_COW, new Vector(0.45, 1.3, 0.45));
        entityDimensions.put(EntityType.WITCH, new Vector(0.31, 1.95, 0.31));
        entityDimensions.put(EntityType.BLAZE, new Vector(0.31, 1.8, 0.31));
        entityDimensions.put(EntityType.PLAYER, new Vector(0.3, 1.8, 0.3));
        entityDimensions.put(EntityType.VILLAGER, new Vector(0.31, 1.8, 0.31));
        entityDimensions.put(EntityType.CREEPER, new Vector(0.31, 1.8, 0.31));
        entityDimensions.put(EntityType.GIANT, new Vector(1.8, 10.8, 1.8));
        entityDimensions.put(EntityType.SKELETON, new Vector(0.31, 1.8, 0.31));
        entityDimensions.put(EntityType.ZOMBIE, new Vector(0.31, 1.8, 0.31));
        entityDimensions.put(EntityType.SNOWMAN, new Vector(0.35, 1.9, 0.35));
        entityDimensions.put(EntityType.HORSE, new Vector(0.7, 1.6, 0.7));
        entityDimensions.put(EntityType.ENDER_DRAGON, new Vector(1.5, 1.5, 1.5));

        entityDimensions.put(EntityType.ENDERMAN, new Vector(0.31, 2.9, 0.31));
        entityDimensions.put(EntityType.CHICKEN, new Vector(0.2, 0.7, 0.2));
        entityDimensions.put(EntityType.OCELOT, new Vector(0.31, 0.7, 0.31));
        entityDimensions.put(EntityType.SPIDER, new Vector(0.7, 0.9, 0.7));
        entityDimensions.put(EntityType.WITHER, new Vector(0.45, 3.5, 0.45));
        entityDimensions.put(EntityType.IRON_GOLEM, new Vector(0.7, 2.9, 0.7));
        entityDimensions.put(EntityType.GHAST, new Vector(2, 4, 2));
        String bukkit = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        if (bukkit.contains("1_8") || bukkit.contains("1_9") | bukkit.contains("1_1")) {
            ground = Sets.immutableEnumSet(Material.SUGAR_CANE, Material.SUGAR_CANE_BLOCK,
                    Material.TORCH, Material.ACTIVATOR_RAIL, Material.AIR, Material.CARROT, Material.CROPS, Material.DEAD_BUSH,
                    Material.DETECTOR_RAIL, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.DOUBLE_PLANT,
                    Material.FIRE, Material.GOLD_PLATE, Material.IRON_PLATE, Material.LAVA, Material.LEVER, Material.LONG_GRASS,
                    Material.MELON_STEM, Material.NETHER_WARTS, Material.PORTAL, Material.POTATO, Material.POWERED_RAIL,
                    Material.PUMPKIN_STEM, Material.RAILS, Material.RED_ROSE, Material.REDSTONE_COMPARATOR_OFF,
                    Material.REDSTONE_COMPARATOR_ON, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON,
                    Material.REDSTONE_WIRE, Material.SAPLING, Material.SEEDS, Material.SIGN, Material.SIGN_POST,
                    Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.STONE_BUTTON, Material.STONE_PLATE,
                    Material.SUGAR_CANE_BLOCK, Material.TORCH, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.WALL_SIGN,
                    Material.WATER, Material.WEB, Material.WOOD_BUTTON, Material.WOOD_PLATE, Material.YELLOW_FLOWER,
                    Material.getMaterial("ARMOR_STAND"), Material.getMaterial("BANNER"), Material.getMaterial("STANDING_BANNER")
                    , Material.getMaterial("WALL_BANNER"));
        }

    }

    public static int getRandomInteger(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

    private static boolean isGround(Material material) {
        return ground.contains(material);
    }

    public static double getBlockHeight(Block block) {
        if (block.getTypeId() == 44) {
            return 0.5;
        }
        if (block.getTypeId() == 53) {
            return 0.5;
        }
        if (block.getTypeId() == 85) {
            return 0.2;
        }
        if (block.getTypeId() == 54 || block.getTypeId() == 130) {
            return 0.125;
        }
        return 0;
    }

    public static boolean isOnGround(Location loc) {
        double diff = .3;

        return !isGround(loc.clone().add(0, -.1, 0).getBlock().getType())
                || !isGround(loc.clone().add(diff, -.1, 0).getBlock().getType())
                || !isGround(loc.clone().add(-diff, -.1, 0).getBlock().getType())
                || !isGround(loc.clone().add(0, -.1, diff).getBlock().getType())
                || !isGround(loc.clone().add(0, -.1, -diff).getBlock().getType())
                || !isGround(loc.clone().add(diff, -.1, diff).getBlock().getType())
                || !isGround(loc.clone().add(diff, -.1, -diff).getBlock().getType())
                || !isGround(loc.clone().add(-diff, -.1, diff).getBlock().getType())
                || !isGround(loc.clone().add(-diff, -.1, -diff).getBlock().getType())
                || (getBlockHeight(loc.clone().subtract(0.0D, 0.5D, 0.0D).getBlock()) != 0.0D &&
                (!isGround(loc.clone().add(diff, getBlockHeight(loc.getBlock()) - 0.1, 0).getBlock().getType())
                        || !isGround(loc.clone().add(-diff, getBlockHeight(loc.getBlock()) - 0.1, 0).getBlock().getType())
                        || !isGround(loc.clone().add(0, getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
                        || !isGround(loc.clone().add(0, getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())
                        || !isGround(loc.clone().add(diff, getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
                        || !isGround(loc.clone().add(diff, getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())
                        || !isGround(loc.clone().add(-diff, getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
                        || !isGround(loc.clone().add(-diff, getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())));
    }

}
