package cc.flycode.skywars.manager.thread.handles;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.util.MathUtil;

/**
 * Created by FlyCode on 10/08/2019 Package cc.flycode.skywars.manager.thread.handles
 */
public class PlayerDataThread {
    public void init() {
        Skywars.instance.executorData.playerDataExecutor.execute(() -> Skywars.instance.userManager.getUsers().forEach(user -> {
            user.onGround = MathUtil.isOnGround(user.player.getLocation());
            if (user.onGround) {
                user.lastGroundLocation = user.player.getLocation();
            }
        }));
    }
}
