package cc.flycode.skywars.manager.listeners;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.user.User;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.manager.listeners
 */
public class BlockEvent implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null && !user.build) {
            if (Skywars.instance.dataManager.hasStarted && (e.getBlock().getType() == Material.CHEST || e.getBlock().getType() == Material.TRAPPED_CHEST)) {
                e.setCancelled(true);
            }
            if (!Skywars.instance.dataManager.hasStarted) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        User user = Skywars.instance.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null && !user.build) {
            if (Skywars.instance.dataManager.hasStarted && (e.getBlockPlaced().getType() == Material.CHEST || e.getBlockPlaced().getType() == Material.TRAPPED_CHEST)) {
                e.setCancelled(true);
            }
            if (!Skywars.instance.dataManager.hasStarted) e.setCancelled(true);
        }
    }

 /*   @EventHandler
    public void onInventory(InventoryOpenEvent e) {
        if ((e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest) && Skywars.instance.dataManager.hasStarted && !Skywars.instance.dataManager.wonGame && Skywars.instance.dataManager.inGame.contains(e.getPlayer())) {
            Skywars.instance.executorData.inventoryFixExecutor.execute(() -> {
                for (ItemStack itemStack : e.getInventory().getContents()) {
                    if (itemStack == null) {
                        if (!Skywars.instance.dataManager.chestsFilled.contains(e.getInventory())) {
                            Skywars.instance.dataManager.chestsFilled.add(e.getInventory());
                            InventoryUtils.update(e.getInventory());
                        }
                    }
                }
            });
        }
    }*/
}
