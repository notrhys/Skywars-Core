package cc.flycode.skywars.util;

import org.bukkit.Location;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.util
 */
public class LocationUtil {

    public static Location getCenter(Location loc) {
        return new Location(loc.getWorld(),
                getRelativeCoord(loc.getBlockX()),
                getRelativeCoord(loc.getBlockY()),
                getRelativeCoord(loc.getBlockZ()));
    }
    private static double getRelativeCoord(int i) {
        double d = i;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }
}
