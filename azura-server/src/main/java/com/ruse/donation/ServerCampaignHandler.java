package com.ruse.donation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruse.GameServer;
import com.ruse.model.Item;
import com.ruse.model.container.impl.Bank;
import com.ruse.world.World;
import com.ruse.world.content.DonobossSpawner;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.logging.Level;

public class ServerCampaignHandler {
    @Setter
    @Getter
    public static double currentDonoBossVal = 0;
    @Setter
    @Getter
    public static double currentSaltsVal = 0;
    @Setter
    @Getter
    public static double currentCrateVal = 0;
    @Setter
    @Getter
    public static boolean isDonoBossSpawning = false;

    public static LocalDateTime lastDeath = LocalDateTime.now();

    public static LocalDateTime getLastDeath() {
        return lastDeath;
    }

    public static void setLastDeath(LocalDateTime lastSpawn1) {
        lastDeath = lastSpawn1;
    }

    public enum DataSet {
        DONATION_BOSS(500),
        PANDORAS_PLEDGE(1000, new Item[]{new Item(17130, 2), new Item(15667, 5)}),
        LOOT_FRENZY(2000, new Item[]{new Item(3578, 1)});

        @Getter
        int maxThreshold;
        @Getter
        Item[] items;

        DataSet(int max) {
            this.maxThreshold = max;
            this.items = null;
        }

        DataSet(int max, Item[] items) {
            this.maxThreshold = max;
            this.items = items;
        }
    }

    public static void save() {
        // Create the path and file objects.
        Path path = Paths.get("./data/","ServerCampaign.json");
        File file = path.toFile();
        file.getParentFile().setWritable(true);
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (SecurityException e) {
             System.out.println("Unable to create directory for player data!");
            }
        }
        try (FileWriter writer = new FileWriter(file)) {

            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject object = new JsonObject();
            object.addProperty("dono-boss-value", getCurrentDonoBossVal());
            object.addProperty("salts-storm-value", getCurrentSaltsVal());
            object.addProperty("crate-storm-value", getCurrentCrateVal());

            writer.write(builder.toJson(object));

        } catch (Exception e) {
            // An error happened while saving.
            GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a character file!", e);
        }
    }

    public static void load() {
        // Create the path and file objects.
        Path path = Paths.get("./data/","ServerCampaign.json");
        File file = path.toFile();

        if (!file.exists()) {
            try {
                System.out.println("I probably fucked up - Brad");
            } catch (SecurityException e) {
                System.out.println("Unable to create directory for player data!");
            }
        }
        try (FileReader fileReader = new FileReader(file)) {
            JsonParser fileParser = new JsonParser();
            Gson builder = new GsonBuilder().create();
            JsonObject reader = (JsonObject) fileParser.parse(fileReader);
            if (reader.has("dono-boss-value")) {
                setCurrentDonoBossVal(reader.get("dono-boss-value").getAsInt());
            }
            if (reader.has("salts-storm-value")) {
                setCurrentSaltsVal(reader.get("salts-storm-value").getAsInt());
            }
            if (reader.has("crate-storm-value")) {
                setCurrentCrateVal(reader.get("crate-storm-value").getAsInt());
            }
        } catch (Exception e) {
            // An error happened while saving.
            GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a character file!", e);
        }
    }

    public static void trySpawns(Player plr) {
        handleDonoBoss(0, plr);
        handleSaltStorm(0);
        handleCrateStorm(0);
    }

    public static void updateInterface(Player plr) {
        Milestone[] milestones = new Milestone[]{
            new Milestone(DataSet.DONATION_BOSS.getMaxThreshold(), "Spawns a donation boss", Milestone.MilestoneType.DESCRIPTION),
            new Milestone(DataSet.PANDORAS_PLEDGE.getMaxThreshold(), DataSet.PANDORAS_PLEDGE.getItems(), Milestone.MilestoneType.ITEMS),
            new Milestone(DataSet.LOOT_FRENZY.getMaxThreshold(), DataSet.LOOT_FRENZY.getItems(), Milestone.MilestoneType.ITEMS)
        };
        plr.getPacketSender().sendCustomProgressBarWMilestones(28602, (int) currentCrateVal, milestones, DataSet.LOOT_FRENZY.maxThreshold);

    }

    private static int tick = 0;

    public static void process() {
        tick++;
        if (tick % 8 == 0) {
            tick = 0;
            for (Player plr : World.getPlayers()) {
                if (plr != null) {
                    LifetimeStreakHandler.updateInterface(plr);
                    updateInterface(plr);
                    trySpawns(plr);
                }
            }
        }
    }

    public static void handleDonoBoss(int amount, Player plr) {
        setCurrentDonoBossVal(getCurrentDonoBossVal() + amount);
        //System.out.println("Dono Boss: " + getCurrentDonoBossVal());
        if (getCurrentDonoBossVal() >= DataSet.DONATION_BOSS.getMaxThreshold()) {
            if (isDonoBossSpawning)
                return;
            isDonoBossSpawning = true;
            DonobossSpawner.startDonoboss(plr);
            setCurrentDonoBossVal(getCurrentDonoBossVal() - DataSet.DONATION_BOSS.getMaxThreshold());
        }
    }

    public static void handleCrateStorm(int amount) {
        setCurrentCrateVal(getCurrentCrateVal() + amount);
        if (getCurrentCrateVal() < DataSet.LOOT_FRENZY.getMaxThreshold())
            return;
        setCurrentCrateVal(getCurrentCrateVal() - DataSet.LOOT_FRENZY.getMaxThreshold());
        for (Player plr : World.getPlayers()) {
            if (plr != null) {
                for (int i = 0; i < DataSet.LOOT_FRENZY.getItems().length; i++) {
                    int tab = Bank.getTabForItem(plr, DataSet.LOOT_FRENZY.items[i]);
                    plr.getBank(tab).add(DataSet.LOOT_FRENZY.items[i]);
                }
            }
        }
    }

    public static void handleSaltStorm(int amount) {
        setCurrentSaltsVal(getCurrentSaltsVal() + amount);
        if (getCurrentSaltsVal() < DataSet.PANDORAS_PLEDGE.getMaxThreshold())
            return;
        setCurrentSaltsVal(getCurrentSaltsVal() - DataSet.PANDORAS_PLEDGE.getMaxThreshold());
        for (Player plr : World.getPlayers()) {
            if (plr != null) {
                for (int i = 0; i < DataSet.PANDORAS_PLEDGE.getItems().length; i++) {
                    int tab = Bank.getTabForItem(plr, DataSet.PANDORAS_PLEDGE.items[i]);
                    plr.getBank(tab).add(DataSet.PANDORAS_PLEDGE.items[i]);
                }
            }
        }
    }
}
