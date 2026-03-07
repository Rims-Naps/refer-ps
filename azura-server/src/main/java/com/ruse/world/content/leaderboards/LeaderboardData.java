package com.ruse.world.content.leaderboards;

import lombok.Getter;

import java.util.ArrayList;

import static com.ruse.world.content.leaderboards.LeaderboardData.LeaderboardType.MAIN;
import static com.ruse.world.content.leaderboards.LeaderboardData.LeaderboardType.MONSTER;

@Getter
public enum LeaderboardData {
    TOTAL_NPC_KILLS(MAIN,  "Total Kills"),
    EASY_SLAYER_TASKS(MAIN, "Easy Slayer Tasks"),
    MED_SLAYER_TASKS(MAIN, "Medium Slayer Tasks"),
    HARD_SLAYER_TASKS(MAIN, "Elite Slayer Tasks"),
    BEAST_HUNTER_TASKS(MAIN, "Beast Hunter Tasks"),
    PLAYTIME(MAIN, "Playtime"),
    AMOUNT_DONATED_CLAIMED(MAIN, "Donator Rank"),
    COLLECTION_LOGS_COMPLETED(MAIN, "Collection Logs Completed"),
    KEYS_OPENED(MAIN, "Keys Opened"),
    BOXES_OPENED(MAIN, "Boxes Opened"),
    SOULBANE(MAIN, "Soul Bane Runs"),
    TOWER(MAIN, "Tower Completions"),
    CORRUPT_RAIDS(MAIN, "Corrupt Raids"),
    CORRUPT_RAIDS_MED(MAIN, "Corrupt Raids Med"),
    CORRUPT_RAIDS_HARD(MAIN, "Corrupt Raids Hard"),

    SPECTRAL_RAIDS(MAIN, "Spectral Raids"),
    SPECTRAL_RAIDS_MED(MAIN, "Spectral Raids Med"),
    SPECTRAL_RAIDS_HARD(MAIN, "Spectral Raids Hard"),

    CIRCLE_OF_ELEMENTS(MAIN, "COE Runs"),


    Nythor(MONSTER, 13747, "Nythor"),
    Terran(MONSTER, 1801, "Terran"),
    Aqualorn(MONSTER, 9027, "Aqualorn"),
    Ferna(MONSTER, 1802, "Ferna"),
    Ignox(MONSTER, 13458, "Ignox"),
    Crystalis(MONSTER, 8006, "Crystalis"),
    Ember(MONSTER, 688, "Ember"),
    Xerces(MONSTER, 350, "Xerces"),
    Marina(MONSTER, 182, "Marina"),
    Lava_Guardian(MONSTER, 6330, "Lava Guardian"),

    Kezel(MONSTER, 9815, "Kezel"),
    Hydrora(MONSTER, 1741, "Hydrora"),
    Infernus(MONSTER, 12228, "Infernus"),
    Tellurion(MONSTER, 9026, "Tellurion"),
    Marinus(MONSTER, 1150, "Marinus"),
    Pyrox(MONSTER, 9837, "Pyrox"),
    Astaran(MONSTER, 9002, "Astaran"),
    Nereus(MONSTER, 7000, "Nereus"),
    Volcar(MONSTER, 1821, "Volcar"),
    Aqua_Guardian(MONSTER, 9800, "Aqua Guardian"),

    Lagoon(MONSTER, 1727, "Lagoon"),
    Incendia(MONSTER, 1729, "Incendia"),
    Terra(MONSTER, 1730, "Terra"),
    Abyss(MONSTER, 1731, "Abyss"),
    Pyra(MONSTER, 1735, "Pyra"),
    Geode(MONSTER, 5539, "Geode"),
    Cerulean(MONSTER, 5547, "Cerulean"),
    Scorch(MONSTER, 5533, "Scorch"),
    Geowind(MONSTER, 5553, "Geowind"),
    Gaia_Guardian(MONSTER, 3117, "Gaia Guardian"),

    Goliath(MONSTER, 1072, "Goliath"),
    Volcanus(MONSTER, 1073, "Volcanus"),
    Nautilus(MONSTER, 1074, "Nautilus"),
    Quake(MONSTER, 1075, "Quake"),
    Scaldor(MONSTER, 1076, "Scaldor"),
    Seabane(MONSTER, 1077, "Seabane"),
    Rumble(MONSTER, 1078, "Rumble"),
    Moltron(MONSTER, 1079, "Moltron"),
    Hydrox(MONSTER, 1080, "Hydrox"),
    Void_Guardian(MONSTER, 1081, "Void_Guardian"),

    HellFire(MONSTER, 2111, "Hellfire"),
    Cryos(MONSTER, 2112, "Cryos"),
    Toxus(MONSTER, 2114, "Toxus"),
    Gemstone(MONSTER, 2114, "Gemstone"),

    Brimstone(MONSTER, 2017, "Brimstone"),
    Everthorn(MONSTER, 6323, "Everthorn"),
    Basilisk(MONSTER, 2018, "Basilisk"),
    POSEIDON(MONSTER, 8520, "Poseidon"),


    ;

    private LeaderboardType type;
    private String name;
    private int npcId;

    LeaderboardData(LeaderboardType type, String name) {
        this.type = type;
        this.name = name;
        this.npcId = -1;
    }

    LeaderboardData(LeaderboardType type, int npcId, String name) {
        this.type = type;
        this.name = name;
        this.npcId = npcId;
    }



    public static ArrayList<LeaderboardData> getItems(LeaderboardType type) {
        ArrayList<LeaderboardData> items = new ArrayList<>();
        for (LeaderboardData s : LeaderboardData.values()) {
            if (s.type == type) {
                items.add(s);
            }
        }
        return items;
    }
    public static LeaderboardData forNpcId(int npcId) {
        for (LeaderboardData s : LeaderboardData.values()) {
            if (s.npcId == npcId) {
               return s;
            }
        }
        return null;
    }

    public enum LeaderboardType{
        MAIN, MONSTER;
    }



}
