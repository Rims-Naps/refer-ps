package com.ruse.world.content.watchlist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruse.world.entity.impl.player.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class WatchListManager {

    private final static List<String> watchListedPlayers = new ArrayList<>();

    public static void watchListPlayer(Player staff, String name) {
        if (watchListedPlayers.contains(name.toLowerCase())) {
            staff.sendMessage("This player is already on the watch list! (" + name + ")");
            return;
        }
        watchListedPlayers.add(name.toLowerCase());
        staff.sendMessage("You have successfully added " + name + " to the watch list.");
    }

    public static boolean beingWatched(String name) {
        return watchListedPlayers.contains(name.toLowerCase());
    }

    public static void removePlayer(Player staff, String name) {
        if (!watchListedPlayers.contains(name.toLowerCase())) {
            staff.sendMessage("This player is not currently on the watch list.");
            return;
        }
        watchListedPlayers.remove(name.toLowerCase());
        staff.sendMessage("You have successfully removed " + name + " from the watch list.");
    }

    public static void save() {
        File file = new File("./data/saves/watchlist.json");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject object = new JsonObject();
            object.add("watchlist", builder.toJsonTree(watchListedPlayers));
            writer.write(builder.toJson(object));
            writer.close();
            System.out.println("Saved watchlist!");
        } catch (Exception e) {
            System.out.println("Error saving watchlist!");
        }
    }

    public static void load() {
        File file = new File("./data/saves/watchlist.json");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (!file.exists())
            return;
        try (FileReader fileReader = new FileReader(file)) {
            JsonParser fileParser = new JsonParser();
            Gson builder = new GsonBuilder().create();
            JsonObject reader = (JsonObject) fileParser.parse(fileReader);
            if (reader.has("watchlist")) {
                String[] watchlist = builder.fromJson(reader.get("watchlist"), String[].class);
                for (String player : watchlist)
                    watchListedPlayers.add(player);
            }
            System.out.println("Loaded watchlist!");
        } catch (Exception e) {
            System.out.println("Error loading watchlist!");
        }
    }
}
