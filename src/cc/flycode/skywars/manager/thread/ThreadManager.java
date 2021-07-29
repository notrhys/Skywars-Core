package cc.flycode.skywars.manager.thread;

import cc.flycode.skywars.manager.thread.handles.MiscThread;
import cc.flycode.skywars.manager.thread.handles.PlayerDataThread;
import cc.flycode.skywars.manager.thread.handles.ScoreboardThread;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.manager.thread
 */
public class ThreadManager {
    public ThreadManager() {
        new MiscThread().init();
        new ScoreboardThread().init();
        new PlayerDataThread().init();;
    }
}
