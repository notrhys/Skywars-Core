package cc.flycode.skywars.commands;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.user.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (s.equalsIgnoreCase("build")) {
            if (commandSender.isOp()) {
                Player player = (Player) commandSender;
                User user = Skywars.instance.userManager.getUser(player.getUniqueId());
                if (user != null) {
                    if (user.build) {
                        user.build = false;
                        commandSender.sendMessage(ChatColor.RED + "Building disabled!");
                    } else {
                        user.build = true;
                        commandSender.sendMessage(ChatColor.GREEN + "Building enabled!");
                    }
                }
            } else {
                commandSender.sendMessage(ChatColor.RED + "No Permission");
            }
            return true;
        }
        return false;
    }
}
