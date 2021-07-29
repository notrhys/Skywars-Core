package cc.flycode.skywars.scoreboard;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.config.ConfigValues;
import cc.flycode.skywars.manager.user.User;
import cc.flycode.skywars.scoreboard.scoreboards.InGameScoreboard;
import cc.flycode.skywars.scoreboard.scoreboards.PreGameScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by FlyCode on 14/06/2019 Package cc.flycode.skywars.scoreboard
 */
public class ScoreboardManager {
    public PreGameScoreboard preGameScoreboard = new PreGameScoreboard();

    public void initBoard(User user) {
        user.player.setScoreboard(preGameScoreboard.createScoreboard(user));

    }

    public void update(User user) {
        if (user.hasPreScoreboard) {
            String rankString = Skywars.instance.cloudHelper.getRankScoreboard(user.player);
            if (rankString.length() > 32) rankString = rankString.substring(32);
            user.player.getScoreboard().getTeam("rankName").setSuffix(rankString);

            String onlineString = ChatColor.RED + String.valueOf(Math.abs(Bukkit.getServer().getOnlinePlayers().size() - Skywars.instance.staffUtil.getStaffList().size()));
            if (onlineString.length() > 32) onlineString = onlineString.substring(32);
            user.player.getScoreboard().getTeam("online").setSuffix(onlineString);

            if (Math.abs(Bukkit.getServer().getOnlinePlayers().size()) >= ConfigValues.minPlayers && !Skywars.instance.dataManager.hasStarted) {
                user.player.getScoreboard().getTeam("countdownTitle").setSuffix(ChatColor.GREEN + "Starting in:");
                user.player.getScoreboard().getTeam("countdown").setSuffix(ChatColor.RED + String.valueOf(Skywars.instance.dataManager.secondsToStart));
            } else {
                user.player.getScoreboard().getTeam("countdownTitle").setSuffix(ChatColor.GREEN + "Starting soon!");
                user.player.getScoreboard().getTeam("countdown").setSuffix("");
            }
            if (Skywars.instance.dataManager.secondsToStart < 2) {
                user.hasPreScoreboard = false;
                if (user.player.getScoreboard() != null) {

                    user.player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            user.player.setScoreboard(new InGameScoreboard().createScoreboard(user));
                            user.hasNormalScoreboard = true;
                        }
                    }.runTask(Skywars.instance);
                }
            }
        } else if (user.hasNormalScoreboard) {
            String rankString = Skywars.instance.cloudHelper.getRankScoreboard(user.player);
            if (rankString.length() > 32) rankString = rankString.substring(32);
            user.player.getScoreboard().getTeam("rankName").setSuffix(rankString);

            String killsString = ChatColor.RED + String.valueOf(user.kills);
            if (killsString.length() > 32) killsString = killsString.substring(32);
            user.player.getScoreboard().getTeam("kills").setSuffix(killsString);

            String deathsString = ChatColor.RED + String.valueOf(Skywars.instance.dataManager.deaths);
            if (deathsString.length() > 32) deathsString = deathsString.substring(32);
            user.player.getScoreboard().getTeam("deaths").setSuffix(deathsString);
        }
    }

}
