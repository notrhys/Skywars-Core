package cc.flycode.skywars;

import cc.flycode.helper.util.CloudHelper;
import cc.flycode.skywars.commands.BuildCommand;
import cc.flycode.skywars.commands.addChestCommand;
import cc.flycode.skywars.commands.addSpawnCommand;
import cc.flycode.skywars.kits.KitManager;
import cc.flycode.skywars.kits.gui.KitGUI;
import cc.flycode.skywars.manager.config.ConfigManager;
import cc.flycode.skywars.manager.config.ConfigValues;
import cc.flycode.skywars.manager.game.GameHandler;
import cc.flycode.skywars.manager.listeners.*;
import cc.flycode.skywars.manager.packet.PacketInjector;
import cc.flycode.skywars.manager.thread.ThreadManager;
import cc.flycode.skywars.manager.user.User;
import cc.flycode.skywars.manager.user.UserManager;
import cc.flycode.skywars.scoreboard.ScoreboardManager;
import cc.flycode.skywars.util.*;
import cc.flycode.skywars.util.file.ConfigFile;
import cc.flycode.skywars.util.file.FileUtils;
import cc.flycode.skywars.util.file.MapFile;
import cc.flycode.skywars.util.file.MapRotationFile;
import cc.flycode.staff.util.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by FlyCode on 11/06/2019 Package cc.flycode.skywars
 */
public class Skywars extends JavaPlugin {
    public static Skywars instance;

    public ExecutorData executorData;
    public UserManager userManager;
    public DataManager dataManager;
    public boolean forceStart = false;
    public ConfigManager configManager;
    public ScoreboardManager scoreboardManager;
    public Jedis jedis, coinJedis, kitsJedis;
    public long lastFailedCageGeneration;
    public List<Integer> avdCages = new CopyOnWriteArrayList<>();
    public CoinUtil coinUtil;
    public CloudHelper cloudHelper;
    public KitManager kitManager;
    public StaffUtil staffUtil;
    public Scoreboard emptScoreboard;

    @Override
    public void onEnable() {
        jedis = new Jedis(RedisData.REDIS_IP, RedisData.REDIS_PORT);
        jedis.auth(RedisData.REDIS_PASSWORD);
        jedis.select(RedisData.REDIS_DB);

        coinJedis = new Jedis(RedisData.REDIS_IP, RedisData.REDIS_PORT);
        coinJedis.auth(RedisData.REDIS_PASSWORD);
        coinJedis.select(3);

        kitsJedis = new Jedis(RedisData.REDIS_IP, RedisData.REDIS_PORT);
        kitsJedis.auth(RedisData.REDIS_PASSWORD);
        kitsJedis.select(4);

        instance = this;
        emptScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        staffUtil = new StaffUtil();
        kitManager = new KitManager();
        cloudHelper = new CloudHelper();
        configManager = new ConfigManager();
        configManager.load();
        dataManager = new DataManager();
        coinUtil = new CoinUtil();
        ConfigFile.getInstance().setup(this);
        dataManager.maps = ConfigFile.getInstance().getData().getStringList("Maps");
        new MathUtil();
        scoreboardManager = new ScoreboardManager();
        randomMap();
        new File("plugins/Skywars/maps").mkdir();
        userManager = new UserManager();
        executorData = new ExecutorData();
        executorData.init();
        new ThreadManager();
        load();
        Bukkit.getServer().getOnlinePlayers().forEach((player -> {
            dataManager.takenCages++;
            userManager.addUser(new User(player));
            User user = userManager.getUser(player.getUniqueId());
            if (user != null) user.onJoin();
        }));
        MapFile.getInstance().setup(this,dataManager.mapName);
        if (MapFile.getInstance().getData().contains("data.amount")) {
            int amount = (MapFile.getInstance().getData().contains("data.amount") ? MapFile.getInstance().getData().getInt("data.amount") : 1);
            dataManager.cagesOpen = amount;
        }
        new GameHandler();

      MapFile.getInstance().setup(this, dataManager.mapName);
      dataManager.normalLocationsForCurrentMap = MapFile.getInstance().getData().getStringList("normalChests");
      dataManager.opLocationsForCurrentMap = MapFile.getInstance().getData().getStringList("opChests");
      new PacketInjector();
      MapFile.getInstance().setup(this, dataManager.mapName);
      dataManager.maxPlayers = MapFile.getInstance().getData().getInt("data.amount");
      new BukkitRunnable() {
          int seconds = 0;
          @Override
          public void run() {
              if (seconds == 3) {
                  ConfigFile.getInstance().setup(Skywars.instance);
                  for (String str : dataManager.maps) {
                      World world = Bukkit.getWorld(str);
                      MapFile.getInstance().setup(Skywars.instance, world.getName());
                      for (int i = 1; i <= MapFile.getInstance().getData().getInt("data.amount"); i++) {
                          CageUtils.clearAllCages(world, i);
                      }
                  }
              }
              if (seconds > 8) {
                  seconds = 0;
                  for (int i = 0; i <= dataManager.maxPlayers; i++) {
                      if (avdCages.contains(i) || i == 0) continue;
                      getLogger().info("Added cage slot: " + i);
                      avdCages.add(i);
                  }

                  dataManager.denyLogin = false;
                  getLogger().info("now accepting players!");
                 if (!ConfigValues.developerMode) jedis.set(ConfigValues.serverName, "true:0:false");

                 getLogger().info("dev mode: " + ConfigValues.developerMode);

                  this.cancel();
              }
              seconds++;
          }
      }.runTaskTimer(this, 20L, 20L);

      new BukkitRunnable() {
          int i = 0;
          @Override
          public void run() {
              if (i > 3) {
                  i = 0;
                  for (Entity entity : Bukkit.getWorld(dataManager.mapName).getEntities()) {
                      if (!(entity instanceof Item) && !(entity instanceof Player) && !(entity instanceof EnderPearl) && !(entity instanceof Snowball) && !(entity instanceof Egg) && !(entity instanceof Potion) && !(entity instanceof Arrow) && !(entity instanceof ExperienceOrb)) entity.remove();
                  }
              }
              i++;
          }
      }.runTaskTimer(this, 20L, 20L);
    }
    @Override
    public void onDisable() {
        if (jedis != null) {
            if (!ConfigValues.developerMode) jedis.set(ConfigValues.serverName, "false:0:true");
            jedis.close();
        }
        if (kitsJedis != null) kitsJedis.close();
        if (coinJedis != null) {
            coinJedis.close();
        }
        boolean copy = true;
        if (copy) {
            for (String mapName : ConfigValues.maps) {
                try {
                    FileUtils.copyFolder(new File("plugins/Skywars/maps/"+mapName), new File(mapName), mapName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userManager.getUsers().forEach(User::deleteCage);
        executorData.shutdown();
    }
    private void load() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new BlockEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new PreLogin(), this);
        getServer().getPluginManager().registerEvents(new ItemEvent(), this);
        getCommand("addSpawn").setExecutor(new addSpawnCommand());
        getCommand("force").setExecutor(new addSpawnCommand());
        getCommand("addChest").setExecutor(new addChestCommand());
        getCommand("build").setExecutor(new BuildCommand());
        getServer().getPluginManager().registerEvents(new PreLoginListener(), this);
        getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new KitGUI(), this);
        getServer().getPluginManager().registerEvents(new LeavesDecayListener(), this);
    }
    private void randomMap() {
        ConfigFile.getInstance().setup(this);
        MapRotationFile.getInstance().setup(this);

        String lastMapName = "not set";
        if (MapRotationFile.getInstance().getData().contains("Maps")) lastMapName = MapRotationFile.getInstance().getData().getString("lastMap");
        //dataManager.maps.get(new Random().nextInt(dataManager.maps.size()));
        SecureRandom random = new SecureRandom();

        dataManager.mapName = dataManager.maps.get(random.nextInt(dataManager.maps.size()));
        System.out.println("selected map " + dataManager.mapName);

        do {
            dataManager.mapName = dataManager.maps.get(random.nextInt(dataManager.maps.size()));
            System.out.println("selected map " + dataManager.mapName);
        } while (dataManager.mapName.equalsIgnoreCase(lastMapName));

        ConfigFile.getInstance().getData().set("lastMap", dataManager.mapName);
        ConfigFile.getInstance().saveData();



    }
}
