package cc.flycode.skywars.util;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.util
 */
public class DataManager {
    public int takenCages = 1, cagesOpen, secondsToStart, deaths = 0, currentCages = 0, gayCage = 8, maxPlayers = 1;
    public String mapName = "skywars1";
    public boolean hasStarted, wonGame, allowDamage, allowTabUpdate, denyLogin = true;
    public List<Integer> randomNumbers = new CopyOnWriteArrayList<>();
    public List<Player> inGame = new CopyOnWriteArrayList<>();
    public List<String> normalLocationsForCurrentMap = new CopyOnWriteArrayList<>(), opLocationsForCurrentMap = new CopyOnWriteArrayList<>();
    public List<Player> hasScoreboard = new CopyOnWriteArrayList<>();
   // public String[] maps = {"skywars1","skywars2", "skywars3"};
    public List<String> maps = new ArrayList<>();
}
