package cc.flycode.skywars.util;

import cc.flycode.skywars.Skywars;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Random;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.util
 */
public class InventoryUtils {
    public static void fillChests() {
        for (Chunk chunk : Skywars.instance.getServer().getWorld(Skywars.instance.dataManager.mapName).getLoadedChunks()) {
            if (chunk != null) {
                for (BlockState ent : chunk.getTileEntities()) {
                    if (ent instanceof Chest) {
                        Inventory inventory = ((Chest) ent).getInventory();
                        //      update(inventory);
                    }
                }
            }
        }
    }

    public static void updateOP(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
        inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.STONE, MathUtil.getRandomInteger(20, 64)));
        if (MathUtil.getRandomInteger(1, 5) > 3) {
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
            item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
            inventory.setItem(new Random().nextInt(inventory.getSize()), item);
        }
        if (MathUtil.getRandomInteger(1, 5) > 4) {
            ItemStack item = new ItemStack(Material.DIAMOND_HELMET, 1);
            item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            inventory.setItem(new Random().nextInt(inventory.getSize()), item);
            ItemStack item1 = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
            item1.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            inventory.setItem(new Random().nextInt(inventory.getSize()), item1);
        }
        if (MathUtil.getRandomInteger(1, 5) < 2) {
            ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            inventory.setItem(new Random().nextInt(inventory.getSize()), item);
        }
        if (MathUtil.getRandomInteger(1, 5) < 3) {
            ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
            item.addEnchantment(Enchantment.PROTECTION_FALL, 1);
            inventory.setItem(new Random().nextInt(inventory.getSize()), item);
        }
        int random1 = MathUtil.getRandomInteger(1,10), random2 = MathUtil.getRandomInteger(1,5);
        if (random1 > 5 && random1 < 8) {
            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 5, (short) 1);
            inventory.setItem(new Random().nextInt(inventory.getSize()), item);
        }
        if (random2 > 2 && random2 < 5) {
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
            item.addEnchantment(Enchantment.FIRE_ASPECT, 1);
            inventory.setItem(new Random().nextInt(inventory.getSize()), item);
        }
    }
    public static void update(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
        inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.STONE, MathUtil.getRandomInteger(20, 64)));

        if (Math.random() < 0.5) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.GOLD_SWORD, 1));
        }
        if (MathUtil.getRandomInteger(1, 5) < 3) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        }
        if (MathUtil.getRandomInteger(1, 5) > 3) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.LEATHER_BOOTS, 1));
        }
        if (MathUtil.getRandomInteger(1, 5) > 4) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        }
        if (MathUtil.getRandomInteger(5, 10) > 7) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        }
        if (MathUtil.getRandomInteger(5, 15) > 12) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.DIAMOND_BOOTS, 1));
        }
        if (MathUtil.getRandomInteger(5, 15) > 10) {
            Potion potion = new Potion(PotionType.SPEED, MathUtil.getRandomInteger(1, 2));
            potion.setSplash(true);
            inventory.setItem(new Random().nextInt(inventory.getSize()), potion.toItemStack(1));

        }

        if (MathUtil.getRandomInteger(5, 15) > 14) {
            Potion potion = new Potion(PotionType.INSTANT_HEAL, 1);
            potion.setSplash(true);
            inventory.setItem(new Random().nextInt(inventory.getSize()), potion.toItemStack(1));
        }

        if (MathUtil.getRandomInteger(30, 55) > 45) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.IRON_SWORD, 1));
        }

        if (MathUtil.getRandomInteger(100, 200) > 150) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.IRON_HELMET, 1));
        }
        if (MathUtil.getRandomInteger(100, 200) > 150 && MathUtil.getRandomInteger(100, 200) < 170) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.IRON_LEGGINGS, 1));
        }

        if (MathUtil.getRandomInteger(20, 30) > 25) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.DIAMOND_SWORD, 1));
        }

        if (MathUtil.getRandomInteger(5, 10) > 5) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.CHAINMAIL_HELMET, 1));
        }
        if (MathUtil.getRandomInteger(1, 3) > 1) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.GRILLED_PORK, MathUtil.getRandomInteger(4, 10)));
        }
        int randEndPearl = MathUtil.getRandomInteger(1, 20);
        if (randEndPearl > 5 && randEndPearl < 10) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.ENDER_PEARL, MathUtil.getRandomInteger(1, 4)));
        }
        if (MathUtil.getRandomInteger(1, 10) > 6) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), new ItemStack(Material.GOLDEN_APPLE, MathUtil.getRandomInteger(1, 6)));
        }
    }
}
