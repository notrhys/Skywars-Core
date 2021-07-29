package cc.flycode.skywars.kits.gui;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlyCode on 20/08/2019 Package cc.flycode.skywars.kits.gui
 */
public class KitGUI implements Listener {
    private ItemStack createSpacer() {
        ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(" ");
        i.setItemMeta(im);
        return i;
    }


    public void openGUI(User user) {
        if (user != null) {
            Inventory inventory = Bukkit.getServer().createInventory(null, 27, ChatColor.GREEN + "Kit Selector");
            if (inventory != null) {
                List<String> kits = new ArrayList<>();
                String kitsData;
                if (Skywars.instance.kitsJedis.exists(user.player.getUniqueId().toString())) {
                    kitsData = Skywars.instance.kitsJedis.get(user.player.getUniqueId().toString());
                    String[] splittedData = kitsData.split(":");
                    for (String data : splittedData) {
                        if (!kits.contains(data)) kits.add(data);
                    }
                }

                ItemStack potion = new ItemStack(Material.POTION);
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 50, 1), true);
                potion.setItemMeta(meta);
                ItemMeta speedPotionMeta = potion.getItemMeta();
                List<String> speedPotionMetaList = new ArrayList<>();
                speedPotionMetaList.add(ChatColor.GREEN + "Gives you 3 Speed 2 splash potions when the game starts");
                speedPotionMetaList.add((kits.contains("Speedy") ? ChatColor.GREEN + "You own this kit!" : ChatColor.RED + "This kit costs 20 coins"));
                if (!kits.contains("Speedy")) {
                    speedPotionMetaList.add(" ");
                    speedPotionMetaList.add(ChatColor.GOLD + "Click to buy this kit!");
                }
                speedPotionMeta.setLore(speedPotionMetaList);
                speedPotionMeta.setDisplayName(ChatColor.GREEN + "Speedy");
                potion.setItemMeta(speedPotionMeta);


                inventory.setItem(0, potion);
                user.player.openInventory(inventory);
            }
        }
    }


    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        try {
            if (e.getInventory() != null && ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Kit Selector")) {
                if (!Skywars.instance.dataManager.hasStarted) {
                    User user = Skywars.instance.userManager.getUser(e.getWhoClicked().getUniqueId());
                    if (user != null) {
                        Skywars.instance.kitManager.applyKit(user, Skywars.instance.kitManager.nameToKit(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())));
                    }
                }
                e.setCancelled(true);
            }
        } catch (Exception ignored) {}
    }
    @EventHandler
    public void onItemInteractEvent(PlayerInteractEvent e) {
        if (!Skywars.instance.dataManager.hasStarted && e.getItem() != null && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("Kits")) {
            User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
            if (user != null) {
                openGUI(user);
                e.setCancelled(true);
            }
        }
    }
}
