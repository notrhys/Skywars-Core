package cc.flycode.skywars.commands;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.util.file.MapFile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by FlyCode on 10/08/2019 Package cc.flycode.skywars.commands
 */
public class addChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (s.equalsIgnoreCase("addChest") && commandSender.isOp()) {
            if (strings.length > 0) {
                Player player = (Player) commandSender;
                if (strings[0].equalsIgnoreCase("normal")) {
                    MapFile.getInstance().setup(Skywars.instance, player.getWorld().getName());
                    List<String> locations = MapFile.getInstance().getData().getStringList("normalChests");
                    locations.add(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation().getX() + ":" + player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation().getY() + ":" + player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation().getZ());
                    MapFile.getInstance().getData().set("normalChests", locations);
                    MapFile.getInstance().saveData();
                    commandSender.sendMessage(ChatColor.GREEN + "Added chest location with the type " + ChatColor.GRAY + strings[0] + " " + ChatColor.AQUA + "(" + ChatColor.GRAY + locations.size() + ChatColor.AQUA + ")");
                }
                if (strings[0].equalsIgnoreCase("op")) {
                    MapFile.getInstance().setup(Skywars.instance, player.getWorld().getName());
                    List<String> locations = MapFile.getInstance().getData().getStringList("opChests");
                    locations.add(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation().getX() + ":" + player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation().getY() + ":" + player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation().getZ());
                    MapFile.getInstance().getData().set("opChests", locations);
                    MapFile.getInstance().saveData();
                    commandSender.sendMessage(ChatColor.GREEN + "Added chest location with the type " + ChatColor.GRAY + strings[0] + " " + ChatColor.AQUA + "(" + ChatColor.GRAY + locations.size() + ChatColor.AQUA + ")");
                }
            }
            return true;
        }
        return false;
    }
}
