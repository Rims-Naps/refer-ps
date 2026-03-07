
package com.ruse.world.content.serverperks;

import com.ruse.engine.GameEngine;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.util.StringUtils;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerPerks {

    private static ServerPerks instance = null;
    private final Map<Perk, Integer> contributions = new HashMap<>();
    private final int TIME = 6000; // 1 hour
    public static final int INTERFACE_ID = 42050;
    public static final int OVERLAY_ID = 42112;
    private final Perk[] PERKS = Perk.values();
    private final Path FILE_PATH = Paths.get("./data/serverperks.txt");
    private static final int MAX_ACTIVE_PERKS = 2;

    private Perk activePerk;
    private int currentTime = 0;
    private boolean active = false;

    private ServerPerks() {

    }

    public static ServerPerks getInstance() {
        if (instance == null) {
            instance = new ServerPerks();
        }
        return instance;
    }

    public List<Perk> activePerks = new ArrayList<>();

    public Perk getActivePerk() {
        return activePerk;
    }
    public List<Perk> getActivePerks() {
        return activePerks;
    }

    public void open(Player player) {
        player.getPacketSender().sendInterface(INTERFACE_ID);
        player.setPerkIndex(0);
        updateInterface(player);
    }

    public void contribute(Player player, int amount) {
        if (activePerks.size() >= 2) {
            player.sendMessage("Only two perks can be active at once!");
            return;
        }

        if (!player.getInventory().contains(ItemDefinition.COIN_ID, amount)) {
            amount = player.getInventory().getAmount(ItemDefinition.COIN_ID);
        }

        int index = player.getPerkIndex();
        Perk perk = PERKS[index];
        int current = contributions.getOrDefault(perk, 0);
        int necessary = perk.getAmount();
        amount = Math.min(amount, necessary - current);

        // Combo boost restrictions
        if (perk == Perk.DROP_RATE && activePerks.contains(Perk.COMBO)){
            player.sendMessage("COMBO BOOST CAN ONLY BE ENABLED WITH DOUBLE DROPS/DOUBLE KILLS/SLAYER");
            return;
        }
        if (perk == Perk.DAMAGE && activePerks.contains(Perk.COMBO)){
            player.sendMessage("COMBO BOOST CAN ONLY BE ENABLED WITH DOUBLE DROPS/DOUBLE KILLS/SLAYER");
            return;
        }

        player.getInventory().delete(ItemDefinition.COIN_ID, amount);
        int total = contributions.merge(perk, amount, Integer::sum);
        updateInterface(player);

        if (amount >= 50) {
            World.sendMessage("<shad=1>@red@" + player.getUsername() + " @blu@has just donated @gre@" + amount + " @blu@Coins to " + perk.name);
        }
        if (total >= necessary) {
            start(perk);
        }
    }

    public void tick() {
        List<Perk> activePerksCopy = new ArrayList<>(activePerks);
        for (Perk perk : activePerksCopy) {
            int timeLeft = activeTimes.merge(perk, -1, Integer::sum);
            if (timeLeft == 0) {
                end(perk);
            }
        }

        updateOverlay();
    }

    private Map<Perk, Integer> activeTimes = new HashMap<>();

    private void start(Perk perk) {
        if (activePerks.contains(perk) || activePerks.size() >= MAX_ACTIVE_PERKS) {
            return; // already active or max active perks
        }

        // Combo boost restrictions
        if (perk == Perk.DROP_RATE && activePerks.contains(Perk.COMBO)){
            return;
        }
        if (perk == Perk.DAMAGE && activePerks.contains(Perk.COMBO)){
            return;
        }
        if (perk == Perk.SLAYER && activePerks.contains(Perk.COMBO)){
            return;
        }

        activePerks.add(perk);
        activeTimes.put(perk, TIME);
        contributions.put(perk, 0);

        deleteTypeFromLog(perk);
        World.sendMessage("<img=4> <col=0><shad=6C1894>[WORLD]<img=4> " + perk.getName() + " has just been activated!");

        // Check if the combination is allowed
    }

    private void end(Perk perk) {
        activePerks.remove(perk);
        contributions.put(perk, 0);
        World.sendMessage("<img=4> <col=0><shad=6C1894>[WORLD]<img=4>" + perk.getName() + " has ended");

        resetInterface();
        updateOverlay();
    }

    private void updateOverlay() {
        if (activePerks.isEmpty()) {
            return;
        }

        World.getPlayers().forEach(player -> {
            int i = 0;
            for (Perk perk : activePerks) {
                int overlayStartingPoint = i == 0 ? 42112 : 42120;
                int minutes = (int) QuickUtils.tickToMin(activeTimes.get(perk));

                if (player.isEffectson()){
                    player.getPacketSender().sendWalkableInterface(42112, false);
                    player.getPacketSender().sendWalkableInterface(42120, false);
                    return;
                }

                player.getPacketSender().sendSpriteChange(overlayStartingPoint + 4, perk.getSpriteId());
                player.getPacketSender().sendWalkableInterface(overlayStartingPoint, true);
                player.getPacketSender().sendString(overlayStartingPoint + 3, "");
                player.getPacketSender().sendString(overlayStartingPoint + 2, minutes + " min");

                i++;
            }
        });
    }

    private void resetInterface() {
        World.getPlayers().forEach(player -> {
            player.getPacketSender().sendWalkableInterface(OVERLAY_ID, false);
            player.getPacketSender().updateProgressBar(INTERFACE_ID + 10, 0);
        });
    }

    private void updateInterface(Player player) {
        int index = player.getPerkIndex();
        Perk perk = PERKS[index];
        int current = contributions.getOrDefault(perk, 0);
        int required = perk.getAmount();
        int percentage = getPercentage(current, required);
        player.getPacketSender().updateProgressBar(INTERFACE_ID + 10, percentage);
        player.getPacketSender().sendString(INTERFACE_ID + 11, Misc.formatNumber(current) + " / " + Misc.formatNumber(required) + "");
    }

    private int getPercentage(int n, int total) {
        float proportion = ((float) n) / ((float) total);
        return (int) (proportion * 100f);
    }

    public boolean handleButton(Player player, int id) {
        if (id > -23465 || id < -23470) {
            return false;
        }
        if (id == -23467 && activePerks.size() >= 1) {
            player.sendMessage("@red@<shad=0>Combo Boost can only be activated by itself!");
            return true;
        }

        int index = 23470 + id;
        player.setPerkIndex(index);
        updateInterface(player);
        return true;
    }

    public void reset() {
        contributions.clear();
        World.getPlayers().forEach(this::updateInterface);
    }

    public void deleteTypeFromLog(Perk name) {
        GameEngine.submit(() -> {
            try {
                BufferedReader r = new BufferedReader(new FileReader(FILE_PATH.toString()));
                ArrayList<String> contents = new ArrayList<>();
                while (true) {
                    String line = r.readLine();
                    String lineUser = line;
                    if (line == null) {
                        break;
                    } else {
                        line = line.trim();
                        lineUser = line.substring(0, line.indexOf(","));
                    }
                    if (!lineUser.equalsIgnoreCase(name.name())) {
                        contents.add(line);
                    }
                }
                r.close();
                BufferedWriter w = new BufferedWriter(new FileWriter(FILE_PATH.toString()));
                for (String line : contents) {
                    w.write(line, 0, line.length());
                    w.write(System.lineSeparator());
                }
                w.flush();
                w.close();

                activeTimes.remove(name); // remove active time for the perk being removed

            } catch (Exception e) {
            }
        });
    }

    public enum Perk {

        DAMAGE("15% Damage", 0, 200000, 1522),// DONE
        DROP_RATE("1.5X Drop Rate", 1, 350000, 1523),// DONE
        SLAYER("Slayer Dmg + Tickets", 200000, 10000, 1521),// DONE
        COMBO("2X DR + 2X Dmg + 2X Exp", 3, 1000000, 1524),// DONE
        DBL_KC("2X Kills", 4, 5000000, 3398),
        DBL_DROPS("2X Drops", 5, 50000000, 3397); //DONE

        private final int index;
        private final int amount;
        private final int spriteId;
        @Getter
        private String name;

        Perk(String name, int index, int amount, int spriteId) {
            this.name = name;
            this.index = index;
            this.amount = amount;
            this.spriteId = spriteId;
        }

        public int getIndex() {
            return index;
        }

        public int getAmount() {
            return amount;
        }

        public int getSpriteId() {
            return spriteId;
        }
    }
}
