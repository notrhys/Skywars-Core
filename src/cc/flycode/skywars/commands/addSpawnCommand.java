package cc.flycode.skywars.commands;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.util.file.MapFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.commands
 */
public class addSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (s.equalsIgnoreCase("force") && commandSender.isOp()) {

            return true;
        }
        if (s.equalsIgnoreCase("addSpawn") && commandSender.isOp()) {
            Player player = (Player) commandSender;
            MapFile.getInstance().setup(Skywars.instance, player.getWorld().getName());
            MapFile.getInstance().getData().set("data.amount", (MapFile.getInstance().getData().contains("data.amount") ? MapFile.getInstance().getData().getInt("data.amount") + 1 : 1));
            int amount = (MapFile.getInstance().getData().contains("data.amount") ? MapFile.getInstance().getData().getInt("data.amount") : 0);
            MapFile.getInstance().getData().set("cage."+amount+".X", player.getLocation().getX());
            MapFile.getInstance().getData().set("cage."+amount+".Y", player.getLocation().getY());
            MapFile.getInstance().getData().set("cage."+amount+".Z", player.getLocation().getZ());
            MapFile.getInstance().getData().set("cage."+amount+".World", player.getWorld().getName());
            MapFile.getInstance().saveData();
            commandSender.sendMessage(ChatColor.GREEN + "Added spawn " + ChatColor.RED + amount + " " + ChatColor.GREEN + "for map " + ChatColor.RED + player.getWorld().getName());
            Skywars.instance.dataManager.cagesOpen = amount;
            return true;
        }
        return false;
    }
}
