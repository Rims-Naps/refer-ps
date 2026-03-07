package com.ruse.world.content.forgottenRaids.data;

import com.google.gson.*;
import com.ruse.world.content.forgottenRaids.ForgottenRaidDifficulty;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaidDataManager {

    private static Map<Long, RaidData> raidDataMap = new HashMap<>();

    public static void addData(RaidData data) {
        raidDataMap.put(data.getStartTime(), data);
    }

    public static RaidData getData(long timestamp) {
        return raidDataMap.getOrDefault(timestamp, null);
    }

    public static List<RaidData> getData(String username) {
        List<RaidData> toReturn = new ArrayList<>();
        for (Map.Entry<Long, RaidData> data : raidDataMap.entrySet())
            if (data.getValue().getPlayers().contains(username)) toReturn.add(data.getValue());
        return toReturn;
    }

    public static List<RaidData> getData(String username, ForgottenRaidDifficulty difficulty) {
        List<RaidData> toReturn = new ArrayList<>();
        for (Map.Entry<Long, RaidData> data : raidDataMap.entrySet())
            if (data.getValue().getPlayers().contains(username) && data.getValue().getDifficulty() == difficulty) toReturn.add(data.getValue());
        return toReturn;
    }

    public static void save() {
        Path path = Paths.get("./data/raid-data/all-data.json");
        File file = path.toFile();
        try (FileWriter writer = new FileWriter(file)) {
            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JSONObject obj = new JSONObject();
            obj.put("data", builder.toJsonTree(raidDataMap, new com.google.common.reflect.TypeToken<HashMap<Long, RaidData>>() {
            }.getType()));
            writer.write(builder.toJson(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        Path path = Paths.get("./data/raid-data/all-data.json");
        File file = path.toFile();
        try (FileReader fileReader = new FileReader(file)) {
            JsonParser fileParser = new JsonParser();
            Gson builder = new GsonBuilder().create();
            Object object = fileParser.parse(fileReader);

            if (object instanceof JsonNull) {
                return;
            }

            JsonObject reader = (JsonObject) object;
            if (reader.has("data")) {
                raidDataMap = builder.fromJson(reader.get("data"), new com.google.common.reflect.TypeToken<HashMap<Long, RaidData>>() {
                }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
