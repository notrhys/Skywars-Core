package cc.flycode.skywars.kits;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.user.User;
import cc.flycode.skywars.util.CoinUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlyCode on 20/08/2019 Package cc.flycode.skywars.kits
 */
public class KitManager {

    public void applyKit(User user, Kits kit) {
        if (user != null) {
            List<String> kits = new ArrayList<>();
            String kitsData;
            if (Skywars.instance.kitsJedis.exists(user.player.getUniqueId().toString())) {
                kitsData = Skywars.instance.kitsJedis.get(user.player.getUniqueId().toString());
                String[] splittedData = kitsData.split(":");
                for (String data : splittedData) {
                    if (!kits.contains(data)) kits.add(data);
                }
            }

            switch (kit) {
                case SPEEDY:
                    if (kits.contains("Speedy")) {
                        user.selectedKit = kit;
                    } else {
                        user.player.closeInventory();
                        if (Skywars.instance.coinUtil.isInDB(user)) {
                            if (Skywars.instance.coinUtil.getCoins(user) >= 20.00) {
                                Skywars.instance.coinUtil.updateCoins(user.player, CoinUtil.Types.REMOVE, 20.00);
                                user.player.sendMessage(ChatColor.GREEN + "You have bought the kit " + ChatColor.AQUA + "Speedy");
                                user.selectedKit = kit;
                                if (Skywars.instance.kitsJedis.exists(user.player.getUniqueId().toString())) {
                                    Skywars.instance.kitsJedis.set(user.player.getUniqueId().toString(), "Speedy:" + Skywars.instance.kitsJedis.get(user.player.getUniqueId().toString()));
                                } else {
                                    Skywars.instance.kitsJedis.set(user.player.getUniqueId().toString(), "Speedy");
                                }
                                user.player.playSound(user.player.getLocation(), Sound.LEVEL_UP, 1, 100);

                            } else {
                                user.player.closeInventory();
                                user.player.sendMessage(ChatColor.RED + "You do not have the funds for this kit!");
                                user.player.playSound(user.player.getLocation(), Sound.ANVIL_BREAK, 100, 100);
                            }
                        } else {
                            user.player.closeInventory();
                            user.player.sendMessage(ChatColor.RED + "You do not have the funds for this kit!");
                            user.player.playSound(user.player.getLocation(), Sound.ANVIL_BREAK, 100, 100);
                        }
                        return;
                    }
                    break;
            }
            user.player.closeInventory();
            user.player.sendMessage(ChatColor.GREEN + "Applied kit: " + ChatColor.RED + kit.name());
            user.player.playSound(user.player.getLocation(), Sound.LEVEL_UP, 1, 100);

        }
    }

    public void action(User user) {
        if (user != null && user.selectedKit != null) {
            switch (user.selectedKit) {
                case SPEEDY:
                    ItemStack item = new ItemStack(Material.POTION, 3);
                    Potion pot = new Potion(1);
                    pot.setType(PotionType.SPEED);
                    pot.setLevel(2);
                    pot.setHasExtendedDuration(true);
                    pot.setSplash(true);
                    pot.apply(item);
                    user.player.getInventory().setItem(0, item);
                break;
            }
        }
    }

    public Kits nameToKit(String kitName) {
        if (kitName.equalsIgnoreCase("Speedy")) {
            return Kits.SPEEDY;
        }
        return null;
    }

    public enum Kits {
        SPEEDY
    }
}
