package bqz.utility;

import bqz.model.Game;

import java.io.*;
import java.nio.file.Paths;

public class Logs {
    public static boolean save(Game game) {
        try {
            String directoryPath = Paths.get(Helper.jarPath(), "Logs").toAbsolutePath().toString();

            // Create Logs folder
            File directory = new File(directoryPath);
            if (!directory.exists()) directory.mkdir();

            // Create Solo and Multi folders inside Logs
            String soloPath  = Paths.get(directoryPath, "Solo").toAbsolutePath().toString();
            String multiPath = Paths.get(directoryPath, "Multi").toAbsolutePath().toString();
            File soloDir = new File(soloPath);
            File multiDir = new File(multiPath);
            if (!soloDir.exists()) soloDir.mkdir();
            if (!multiDir.exists()) multiDir.mkdir();

            // Log file
            String parentPath;
            String logFileName;

            if (game.getPlayers().length > 1) {
                parentPath = multiPath;
                logFileName = Helper.getRandomString(8) + ".dat";
            }
            else {
                parentPath = soloPath;
                logFileName = "solo.dat";
            }

            String filePath = Paths.get(parentPath, logFileName).toAbsolutePath().toString();

            FileOutputStream logOutput = new FileOutputStream(filePath);
            ObjectOutputStream objectOutput = new ObjectOutputStream(logOutput);
            objectOutput.writeObject(game);
            objectOutput.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object load(String logPath) {
        try {
            FileInputStream logInput = new FileInputStream(logPath);
            ObjectInputStream objectInput = new ObjectInputStream(logInput);

            Object obj = objectInput.readObject();
            objectInput.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
