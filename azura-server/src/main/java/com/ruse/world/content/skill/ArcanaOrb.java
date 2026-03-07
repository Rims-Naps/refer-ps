package com.ruse.world.content.skill;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Animation;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.model.Skill;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.*;

//-12000 for tick, 12000 for interval ticks.
public class ArcanaOrb {
    private static int tick = -12000; // Start with 2-hour delay (12,000 ticks at 600ms)
    private static final int ORB_NPC_ID = 6313; // Unique NPC ID
    private static final int TOTAL_SIPHONS = 10000;
    private static final int SIPHON_COOLDOWN_TICKS = 5; // 3 seconds (5 ticks at 600ms)
    private static final int SPAWN_INTERVAL_TICKS = 12000; // 2 hours (12,000 ticks)
    private static Position orbPos = null;
    private static NPC activeOrb = null;
    private static int remainingSiphons = 0;
    private static final Map<String, Integer> playerSiphons = new HashMap<>(); // UUID -> siphons
    private static final Map<String, Integer> lastSiphonTicks = new HashMap<>(); // UUID -> tick
    private static List<OrbReward> rewards = new ArrayList<>();
    private static final Set<String> siphoningPlayers = new HashSet<>();
    private static int lastAnnouncedPercent = 100;
    // Configurable spawn points
    public static final Position[] SPAWN_POINTS = {
            new Position(3167, 3543, 0), // Varrock Square
            new Position(3034, 3996, 0), // Lumbridge Castle
            new Position(3240, 2975, 0), // Falador Park
            new Position(2594, 4131, 0)  // Edgeville Monastery
    };

    // Location hints for announcements
    public static String locationHints(Position pos) {
        if (pos.equals(new Position(3167, 3543, 0))) {
            return "...at home";
        }
        if (pos.equals(new Position(3034, 3996, 0))) {
            return "...at journeyman mining";
        }
        if (pos.equals(new Position(3240, 2975, 0))) {
            return "...at journeyman woodcutting";
        }
        if (pos.equals(new Position(2594, 4131, 0))) {
            return "...at the salt mine";
        }
        return "";
    }

    // Load rewards from JSON
    static {
        try (FileReader reader = new FileReader("data/arcana_orbs.json")) {
            rewards = new Gson().fromJson(reader, new TypeToken<List<OrbReward>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading arcana_orbs.json. Arcana Orb rewards disabled.");
        }
    }

    // Tick-based sequence
    public static void sequence() {
        tick++;

        // Spawn orb every 2 hours
        if (tick % SPAWN_INTERVAL_TICKS == 0) {
            System.out.println("Arcana Orb tick: " + tick);

            // Despawn existing orb if active
            if (activeOrb != null) {
                World.deregister(activeOrb);
                if (remainingSiphons <= 0) {
                    distributeRewards();
                }
                activeOrb = null;
                orbPos = null;
                remainingSiphons = 0;
                playerSiphons.clear();
                lastSiphonTicks.clear();
                lastAnnouncedPercent = 100;
            }

            // Spawn new orb
            Position location = SPAWN_POINTS[Misc.random(SPAWN_POINTS.length - 1)];
            orbPos = location;
            activeOrb = new NPC(ORB_NPC_ID, orbPos);
            World.register(activeOrb);
            remainingSiphons = TOTAL_SIPHONS;
            String message = "<shad=0><col=AF70C3>[ARCANA] An Arcana Portal has appeared" + locationHints(location);
            World.sendMessage(message);
            GameSettings.broadcastTime = 100;
            lastAnnouncedPercent = 100;
            DiscordManager.sendMessage("[ARCANA] An Arcana Portal has appeared <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
        }
    }
    private static void checkOrbPercentage() {
        int percentRemaining = (int) (((double) remainingSiphons / TOTAL_SIPHONS) * 100);

        // Announce every 10% drop (e.g., 90%, 80%, 70%...)
        if (percentRemaining <= lastAnnouncedPercent - 10) {
            World.sendMessage("<col=AF70C3>[ARCANA] The Arcana Portal has " + percentRemaining + "% remaining.");
            lastAnnouncedPercent = percentRemaining;
        }
    }

    // Handle orb siphoning
    public static void handleOrbClick(int npcId, Player player) {
        if (npcId != ORB_NPC_ID) return;
        if (activeOrb == null || remainingSiphons <= 0) {
            player.sendMessage("There is no active Arcana Portal!");
            return;
        }

        String uuid = player.getUsername();

        // Prevent multiple siphon loops for same player
        if (siphoningPlayers.contains(uuid)) {
            player.sendMessage("You are already siphoning the Portal!");
            return;
        }

        siphoningPlayers.add(uuid);

        // Start siphoning loop
        TaskManager.submit(new Task(SIPHON_COOLDOWN_TICKS, player, true) {
            @Override
            protected void execute() {
                // Stop if orb is gone or player moved too far
                if (activeOrb == null || remainingSiphons <= 0 || !player.isRegistered()
                        || !player.getPosition().isWithinDistance(orbPos, 2)) {
                    player.sendMessage("You stop siphoning the Portal.");
                    siphoningPlayers.remove(uuid);
                    stop();
                    return;
                }

                int playerLevel = player.getSkillManager().getCurrentLevel(Skill.ARCANA);
                int siphons = 10 + (playerLevel / 10); // Base 10 + level/10

                remainingSiphons -= siphons;
                playerSiphons.merge(uuid, siphons, Integer::sum);
                checkOrbPercentage();
                player.getSkillManager().addExperience(Skill.ARCANA, siphons * 50); // XP
                player.performAnimation(new Animation(2284)); // Animation
                player.sendMessage("You siphon " + siphons + " Aether from the Arcana Portal!");

                if (remainingSiphons <= 0) {
                    World.deregister(activeOrb);
                    World.sendMessage("<shad=0><col=AF70C3>[ARCANA] The Arcana Portal has been fully siphoned!");
                    distributeRewards();
                    activeOrb = null;
                    orbPos = null;
                    remainingSiphons = 0;
                    playerSiphons.clear();
                    lastSiphonTicks.clear();
                    siphoningPlayers.clear();
                    lastAnnouncedPercent = 100;// clear all in case
                    stop();
                }
            }
        });
    }

    // Distribute rewards based on siphon contribution
    private static void distributeRewards() {
        for (Map.Entry<String, Integer> entry : playerSiphons.entrySet()) {
            Player player = World.getPlayerByName(entry.getKey());
            if (player == null) continue; // Player offline
            int siphons = entry.getValue();
            double share = (double) siphons / TOTAL_SIPHONS;
            int xpReward = siphons * 100; // 100 XP per siphon
            player.getSkillManager().addExperience(Skill.ARCANA, xpReward);
            List<Item> playerRewards = new ArrayList<>();
            for (OrbReward reward : rewards) {
                if (Misc.random(100) < reward.chance * share * 100) {
                    int amount = (int) (reward.baseAmount * share);
                    if (amount > 0) {
                        playerRewards.add(new Item(reward.itemId, amount));
                    }
                }
            }
            for (Item reward : playerRewards) {
                if (player.getInventory().isFull()) {
                    player.depositItemBank(new Item(reward.getId(),reward.getAmount()), false);
                    player.sendMessage("Reward: " + reward.getDefinition().getName() + " x" + reward.getAmount() + " sent to your bank");
                } else {
                    player.getInventory().add(reward);
                    player.sendMessage("Reward: " + reward.getDefinition().getName() + " x" + reward.getAmount());
                }
                if (reward.getId() == 438 || reward.getId() == 14639) {
                    World.sendMessage("@red@<shad=0>[Arcana] " + player.getUsername() + "has received a " + reward.getDefinition().getName() + " from the arcana Portal!");
                }

            }
            player.getInventory().add(995, 100);
            player.sendMessage("You gained " + xpReward + " ARCANA XP for siphoning " + siphons + " Aether!");
        }
    }

    // Orb reward class
    public static class OrbReward {
        public int itemId;
        public int baseAmount;
        public double chance; // 0.0 to 1.0
    }
}