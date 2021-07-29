package cc.flycode.skywars.scoreboard.scoreboards;

import cc.flycode.skywars.manager.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

/**
 * Created by FlyCode on 14/06/2019 Package cc.flycode.skywars.scoreboard.scoreboards
 */
public class PreGameScoreboard {

    public Scoreboard createScoreboard(User user) {
        user.hasPreScoreboard = true;
        user.hasNormalScoreboard = false;
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Test", "Dummy");
        objective.setDisplayName("§3§lCloud§bMC");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int i = 7;

        //Top Spacer
        Team topSpacer = scoreboard.registerNewTeam("spacerTop");
        topSpacer.addEntry(ChatColor.BLACK + "" + ChatColor.GRAY);
        topSpacer.setPrefix("  ");
        objective.getScore(ChatColor.BLACK + "" + ChatColor.GRAY).setScore(i);


        //Rank
        i--;
        Team rankDisplay = scoreboard.registerNewTeam("rank");
        rankDisplay.addEntry(ChatColor.BLACK + "" + ChatColor.GOLD);
        rankDisplay.setSuffix(ChatColor.AQUA + "Rank: ");
        rankDisplay.setPrefix("");
        objective.getScore(ChatColor.BLACK + "" + ChatColor.GOLD).setScore(i);

        //Rank Name
        i--;
        Team rankName = scoreboard.registerNewTeam("rankName");
        rankName.addEntry(ChatColor.BLACK + "" + ChatColor.DARK_PURPLE);
        rankName.setSuffix("");
        rankName.setPrefix("");
        objective.getScore(ChatColor.BLACK + "" + ChatColor.DARK_PURPLE).setScore(i);

        //Spacer 2
        Team spacer2 = scoreboard.registerNewTeam("spacer2");
        spacer2.addEntry(ChatColor.AQUA + "" + ChatColor.RED);
        spacer2.setPrefix("  ");
        objective.getScore(ChatColor.AQUA + "" + ChatColor.RED).setScore(i);

        //Online title
        i--;
        Team onlineTitle = scoreboard.registerNewTeam("onlineTitle");
        onlineTitle.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        onlineTitle.setSuffix(ChatColor.GREEN + "Online:");
        onlineTitle.setPrefix("");
        objective.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(i);

        //Online
        i--;
        Team online = scoreboard.registerNewTeam("online");
        online.addEntry(ChatColor.LIGHT_PURPLE + "" + ChatColor.DARK_GRAY);
        online.setSuffix("");
        online.setPrefix("");
        objective.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.DARK_GRAY).setScore(i);


        //Spacer 3
        i--;
        Team spacer3 = scoreboard.registerNewTeam("spacer3");
        spacer3.addEntry(ChatColor.BLACK + "" + ChatColor.DARK_RED);
        spacer3.setPrefix("  ");
        objective.getScore(ChatColor.BLACK + "" + ChatColor.DARK_RED).setScore(i);


        //countdown title
        i--;
        Team countDownTitle = scoreboard.registerNewTeam("countdownTitle");
        countDownTitle.addEntry(ChatColor.BOLD + "" + ChatColor.BLACK);
        countDownTitle.setSuffix("");
        countDownTitle.setPrefix("");
        objective.getScore(ChatColor.BOLD + "" + ChatColor.BLACK).setScore(i);

        //countdown
        i--;
        Team coutdown = scoreboard.registerNewTeam("countdown");
        coutdown.addEntry(ChatColor.YELLOW + "" + ChatColor.DARK_AQUA);
        coutdown.setSuffix("");
        coutdown.setPrefix("");
        objective.getScore(ChatColor.YELLOW + "" + ChatColor.DARK_AQUA).setScore(i);


        return scoreboard;
    }
}
