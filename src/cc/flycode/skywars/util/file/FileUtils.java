package cc.flycode.skywars.util.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by FlyCode on 12/06/2019 Package cc.flycode.skywars.util.file
 */
public class FileUtils {
    private static int total;
    public static void copyFolder(File sourceFolder, File destinationFolder, String mapName) throws IOException {
        int i = 0;
        if (sourceFolder.isDirectory()) {
            if (!destinationFolder.exists()) {
                destinationFolder.mkdir();
            }
            String files[] = sourceFolder.list();
            for (String file : files)
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                copyFolder(srcFile, destFile, mapName);
            }
        } else {
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copying map file: " + destinationFolder + " for map: " + mapName + "("+i+"/"+total+")");
            i++;
            total++;
        }
    }
}

