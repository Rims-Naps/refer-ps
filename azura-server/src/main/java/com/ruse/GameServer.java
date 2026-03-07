package com.ruse;

import com.ruse.util.ShutdownHook;
import com.ruse.world.content.discord.handler.DiscordManager;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The starting point of Ruse.
 *
 * @author Gabriel
 * @author Samy
 */
public class GameServer {

    private static final GameLoader loader = new GameLoader(GameSettings.GAME_PORT);
    private static final Logger logger = Logger.getLogger("Athens");
    private static boolean updating;

    public static void main(String[] args) {
        StringBuilder combinedArguments = new StringBuilder();
        for (String arg : args) {
            combinedArguments.append(arg);
        }

        if (args.length > 0) {
            List<String> argumentsAsList = Arrays.asList(args);

            if(argumentsAsList.get(0).matches("\\d+"))
                GameSettings.GAME_PORT = Integer.parseInt(args[0]);

            if (combinedArguments.toString().contains("DEV_MODE")) {
                GameSettings.DEV_MODE = true;
            }
        }

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        try {
            logger.info("Initializing the loader...");
            loader.init();
            loader.finish();
            logger.info("The loader has finished loading utility tasks.");
            logger.info(GameSettings.RSPS_NAME + " is now online on port " + GameSettings.GAME_PORT + "!");
            DiscordManager.initialize();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not start " + GameSettings.RSPS_NAME + "! Program terminated.", ex);
            System.exit(1);
        }
    }

    public static GameLoader getLoader() {
        return loader;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setUpdating(boolean updating) {
        GameServer.updating = updating;
    }

    public static boolean isUpdating() {
        return GameServer.updating;
    }
}