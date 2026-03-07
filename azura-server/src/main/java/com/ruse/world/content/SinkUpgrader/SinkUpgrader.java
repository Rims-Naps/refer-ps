package com.ruse.world.content.SinkUpgrader;

import com.google.gson.reflect.TypeToken;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.entity.impl.player.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SinkUpgrader {

    public enum UpgradeTier {
        TIER_1(1, 2, 1),
        TIER_2(2, 4, 2),
        TIER_3(3, 6, 3),
        TIER_4(4, 8, 4),
        TIER_5(5, 10, 5);

        private int tier;
        private double boostPercent;
        private int requiredSinks;

        UpgradeTier(int tier, double boostPercent, int requiredSinks) {
            this.tier = tier;
            this.boostPercent = boostPercent;
            this.requiredSinks = requiredSinks;
        }

        public int getTier() {
            return tier;
        }

        public double getBoostPercent() {
            return boostPercent;
        }

        public int getRequiredSinks() {
            return requiredSinks;
        }
    }

    public static void sendDialogue(Player p) {
        p.setDialogueActionId(0);
        DialogueManager.start(p, 0);
    }

    public static void handleConfirmation(Player p, int usedItem) {
        if (!p.getInventory().contains(usedItem)) {
            p.getPA().sendInterfaceRemoval();
            p.getPA().sendMessage("You don't have that item...");
            return;
        }
    }

    public static void handleUsedItem(Player p, int usedItem) {

    }

    public static void handleUpgrade(Player p, int usedItem) {
        p.getInventory().delete(1,1);
        addItemtoSinkFile(p, usedItem);
    }

    private static Map<Integer, JsonObject> sinkData = new HashMap<>();
    private static final String SAVE_DIRECTORY = "data/saves/Upgrades/";

    public static void addItemtoSinkFile(Player player, int usedItem) {
        String fileName = SAVE_DIRECTORY + player.getUsername() + ".json";

        loadSinkDataFromStorage(fileName);

        JsonObject itemData = sinkData.get(usedItem);

        if (itemData == null) {
            itemData = new JsonObject();
        }

        int currentSinks = itemData.has("sinks") ? itemData.get("sinks").getAsInt() : 0;
        int currentTier = itemData.has("tier") ? itemData.get("tier").getAsInt() : 0;

        currentSinks++;

        itemData.addProperty("sinks", currentSinks);

        if (currentSinks >= UpgradeTier.values()[currentTier].getRequiredSinks()) {
            currentTier++;
            itemData.addProperty("tier", currentTier);
        }

        sinkData.put(usedItem, itemData);

        saveSinkDataToStorage(fileName);
    }

    private static void loadSinkDataFromStorage(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<Integer, JsonObject>>() {}.getType();
            sinkData = gson.fromJson(reader, type);
            if (sinkData == null) {
                sinkData = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            sinkData = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveSinkDataToStorage(String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(sinkData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}