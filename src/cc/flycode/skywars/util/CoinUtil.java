package cc.flycode.skywars.util;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FlyCode on 15/08/2019 Package cc.flycode.skywars.util
 */
public class CoinUtil {
    public void updateCoins(Player player, Types type, double amount) {
        Skywars.instance.executorData.miscExecutor.execute(() -> {
            String db = "CoinsData";
            Map<String, Double> data = new HashMap<>();
            Map<String, String> coinsTmp;
            coinsTmp = Skywars.instance.coinJedis.hgetAll(db);
            switch (type) {
                case ADD:
                    if (!coinsTmp.isEmpty())
                        coinsTmp.forEach((key, value) -> data.put(String.valueOf(key), Double.valueOf(String.valueOf(value))));
                    if (data.containsKey(player.getUniqueId().toString())) {
                        data.put(player.getUniqueId().toString(), data.get(player.getUniqueId().toString()) + amount);
                    } else {
                        data.put(player.getUniqueId().toString(), amount);
                    }
                    break;

                case REMOVE:
                    if (!coinsTmp.isEmpty())
                        coinsTmp.forEach((key, value) -> data.put(String.valueOf(key), Double.valueOf(String.valueOf(value))));
                    if (data.containsKey(player.getUniqueId().toString())) {
                        data.put(player.getUniqueId().toString(), data.get(player.getUniqueId().toString()) - amount);
                    } else {
                        data.put(player.getUniqueId().toString(), amount);
                    }
                    break;
            }
            if (!data.isEmpty()) {
                Map<String, String> tempList = new HashMap<>();
                data.forEach((key, value) -> tempList.put(String.valueOf(key), String.valueOf(value)));
                if (!tempList.isEmpty()) Skywars.instance.coinJedis.hmset(db, tempList);
            }
        });
    }

    public double getCoins(User user) {
        double i;
        Map<String, String> coinsTmp;
        coinsTmp = Skywars.instance.coinJedis.hgetAll("CoinsData");
        i = Double.parseDouble(coinsTmp.get(user.player.getUniqueId().toString()));
        return i;
    }

    public boolean isInDB(User user) {
        Map<String, String> coinsTmp;
        coinsTmp = Skywars.instance.coinJedis.hgetAll("CoinsData");
        return coinsTmp.containsKey(user.player.getUniqueId().toString());
    }

    public enum Types {
        ADD,
        REMOVE
    }
}
