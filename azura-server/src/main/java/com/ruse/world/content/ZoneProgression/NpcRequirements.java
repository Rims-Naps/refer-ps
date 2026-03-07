package com.ruse.world.content.ZoneProgression;

import com.ruse.world.content.KillsTracker;
import com.ruse.world.entity.impl.player.Player;

public enum NpcRequirements {
        ZONE1_1(13747, 1614, 0, "Nythor"),
        ZONE1_2(1801, 13747, 125, "Terran"),
        ZONE1_3(9027, 1801, 200, "Aqualorn"),
        ZONE1_4(1802, 9027, 300, "Ferna"),
        ZONE1_5(13458, 1802, 400, "Ignox"),
        ZONE1_6(8006, 13458, 500, "Crystalis"),
        ZONE1_7(688, 8006, 600, "Ember"),
        ZONE1_8(350, 688, 700, "Xerces"),
        ZONE1_9(182, 350, 850, "Marina"),
        ZONE1_10(6330, 182, 1000, "MultiBoss 2"),

        ZONE2_1(9815, 6330, 25, "Kezel"),
        ZONE2_2(1741, 9815, 1250, "Hydrora"),
        ZONE2_3(12228, 1741, 1500, "Infernus"),
        ZONE2_4(9026, 12228, 1750, "Tellurion"),
        ZONE2_5(1150, 9026, 2000, "Marinus"),
        ZONE2_6(9837, 1150, 2250, "Pyrox"),
        ZONE2_7(9002, 9837, 2500, "Astaran"),
        ZONE2_8(7000, 9002, 2750, "Nereus"),
        ZONE2_9(1821, 7000, 3000, "Volcar"),
        ZONE2_10(9800, 1821, 3250, "MultiBoss 2"),

        ZONE3_1(1727, 9800, 25, "Lagoon"),
        ZONE3_2(1729, 1727, 2500, "Incendia"),
        ZONE3_3(1730, 1729, 3000, "Terra"),
        ZONE3_4(1731, 1730, 3500, "Abyss"),
        ZONE3_5(1735, 1731, 4000, "Pyra"),
        ZONE3_6(5539, 1735, 4500, "Geode"),
        ZONE3_7(5547, 5539, 5000, "Cerulean"),
        ZONE3_8(5533, 5547, 5500, "Scorch"),
        ZONE3_9(5553, 5533, 6000, "Geowind"),
        ZONE3_10(3117, 5553, 6500, "Multi Boss 3"),

        MASTER_1(1072, 3117, 25, "Goliath"),
        MASTER_2(1073, 1072, 5000, "Volcanus"),
        MASTER_3(1074, 1073, 6000, "Nautilus"),
        MASTER_4(1075, 1074, 7000, "Quake"),
        MASTER_5(1076, 1075, 8000, "Scaldor"),
        MASTER_6(1077, 1076, 9000, "Seabane"),
        MASTER_7(1078, 1077, 10000, "Rumble"),
        MASTER_8(1079, 1078, 11000, "Moltron"),
        MASTER_9(1080, 1079, 12000, "Hydrox"),
        MASTER_10(1081, 1080, 15000, "Void Guardian"),
        CHALLENGER_1(2111, 1081, 250, "Hellfire"),
        CHALLENGER_2(2112, 2111, 500, "Cryos"),
        CHALLENGER_3(2113, 2112, 750, "Toxus"),
        CHALLENGER_4(2114, 2113, 1000, "Gemstone");

    private int npcId;
    private String name;
    private int requireNpcId;
    private int amountRequired;
    private int killCount;


    NpcRequirements(int npcId, int requireNpcId, int amountRequired, String name) {
        this.npcId = npcId;
        this.requireNpcId = requireNpcId;
        this.amountRequired = amountRequired;
        this.killCount = 0;
        this.name = name;

    }



    public int getNpcId() {
        return npcId;
    }

    public int getKillCount() {
        return killCount;
    }

    public int getRequireNpcId() {
        return requireNpcId;
    }

    public String getName() {
        return name;
    }

    public int getAmountRequired() {
        return amountRequired;
    }

    public static void printUnlockedNpcs(Player p) {
        for (NpcRequirements req : NpcRequirements.values()) {
            int playerKillCount = p.getPointsHandler().getNPCKILLCount();

            // Check if the player has unlocked the NPC by meeting either the kill count or the specific NPC requirement
            if ((req.getKillCount() > 0 && playerKillCount >= req.getKillCount()) ||
                    (req.getAmountRequired() > 0 && KillsTracker.getTotalKillsForNpc(req.getRequireNpcId(), p) >= req.getAmountRequired())) {
                p.sendMessage("Unlocked: " + req.getName());
            }
        }
    }


    public int getNextNpcId() {
            // To find the next NPC ID, you can look for the next requirement in the enum.
            for (NpcRequirements req : NpcRequirements.values()) {
                if (req.requireNpcId == this.npcId) {
                    return req.npcId;
                }
            }
            // If there's no next requirement, return -1 or any other appropriate value.
            return -1;
        }
    }