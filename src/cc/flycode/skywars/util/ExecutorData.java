package cc.flycode.skywars.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by FlyCode on 11/06/2019 Package cc.flycode.skywars.util
 */
public class ExecutorData {
    public ScheduledExecutorService miscExecutor, inventoryFixExecutor, scoreboardExecutor, playerDataExecutor, cageExecutor;

    public void init() {
        scoreboardExecutor = Executors.newSingleThreadScheduledExecutor();
        inventoryFixExecutor = Executors.newSingleThreadScheduledExecutor();
        miscExecutor = Executors.newSingleThreadScheduledExecutor();
        playerDataExecutor = Executors.newSingleThreadScheduledExecutor();
        cageExecutor = Executors.newSingleThreadScheduledExecutor();
    }
    public void shutdown() {
        scoreboardExecutor.shutdownNow();
        inventoryFixExecutor.shutdownNow();
        miscExecutor.shutdownNow();
        playerDataExecutor.shutdownNow();
        cageExecutor.shutdownNow();
    }
}
