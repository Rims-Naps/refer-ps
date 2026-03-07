package com.ruse.world.content.tpinterface;

import com.ruse.GameSettings;
import com.ruse.model.Position;
import com.ruse.net.packet.impl.CommandPacketListener;
import com.ruse.util.Misc;
import com.ruse.world.content.KillsTracker;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public enum TPData {


    //TIER 1
    TIER1_0("Nythor", TPTier.TIER_1, 13747, 825, new Position(2138, 4653, 1), "Nythor", "Beginner", ""),
    TIER1_1("Terran", TPTier.TIER_1, 1801, 1100, new Position(3105, 4112,0), "Terran", "Beginner", ""),
    TIER1_2("Aqualorn", TPTier.TIER_1, 9027, 950, new Position(2148, 4119,0), "Aqualorn", "Beginner", ""),
    TIER1_3("Ferna", TPTier.TIER_1, 1802, 960, new Position(2972, 4110,0), "Ferna", "Beginner", ""),
    TIER1_4("Ignox", TPTier.TIER_1, 13458, 990, new Position(2142, 4509,0), "Ignox", "Beginner", ""),
    TIER1_5("Crystalis", TPTier.TIER_1, 8006, 1000, new Position(2148, 3985,0), "Crystalis", "Beginner", ""),
    TIER1_6("Ember", TPTier.TIER_1, 688, 1100, new Position(1950, 4444, 0), "Ember", "Beginner", ""),
    TIER1_7("Xerces", TPTier.TIER_1, 350, 900, new Position(3159, 4254,0), "Xerces", "Beginner", ""),
    TIER1_8("Marina ", TPTier.TIER_1, 182, 935, new Position(2091, 3864,0), "Marina ", "Beginner", ""),
    TIER1_9("Lava Guardian", TPTier.TIER_1, 6330, 1850, new Position(1632, 5146,0), "Lava Guardian", "Beginner", "Multi Boss"),

    //TIER 2
    TIER2_0("Kezel", TPTier.TIER_2, 9815, 985, new Position(2986, 4244,0), "Kezel", "Intermediate", ""),
    TIER2_1("Hydrora", TPTier.TIER_2, 1741, 1000, new Position(2134, 3739,0), "Hydrora", "Intermediate", ""),
    TIER2_2("Infernus", TPTier.TIER_2, 12228, 1000, new Position(2730, 3360,0), "Infernus", "Intermediate", ""),
    TIER2_3("Tellurion", TPTier.TIER_2, 9026, 900, new Position(2525, 2520, 4), "Tellurion", "Intermediate", ""),
    TIER2_4("Marinus", TPTier.TIER_2, 1150, 900, new Position(3753, 3040, 4), "Marinus", "Intermediate", ""),
    TIER2_5("Pyrox", TPTier.TIER_2, 9837, 860, new Position(2522, 3224,0), "Pyrox", "Intermediate", ""),
    TIER2_6("Astaran", TPTier.TIER_2, 9002, 1015, new Position(2593, 2649,0), "Astaran", "Intermediate", ""),
    TIER2_7("Nereus", TPTier.TIER_2, 7000, 900, new Position(3875, 2914,0), "Nereus", "Intermediate", ""),
    TIER2_8("Volcar", TPTier.TIER_2, 1821, 910, new Position(2644, 3244, 1), "Volcar", "Intermediate", ""),
    TIER2_9("Aqua Guardian", TPTier.TIER_2, 9800, 1850, new Position(2466, 3035,0), "Aqua Guardian", "Intermediate", "Multi Boss"),


    //TIER 3
    TIER3_0("Lagoon", TPTier.TIER_3, 1727, 1000, new Position(2528, 2905,0), "Lagoon", "Elite", ""),
    TIER3_1("Incendia", TPTier.TIER_3, 1729, 1000, new Position(2333, 4441,0), "Incendia", "Elite", ""),
    TIER3_2("Terra", TPTier.TIER_3, 1730, 1000, new Position(1952, 4891,0), "Terra", "Elite", ""),
    TIER3_3("Abyss", TPTier.TIER_3, 1731, 1000, new Position(2782, 2773,0), "Abyss", "Elite", ""),
    TIER3_4("Pyra", TPTier.TIER_3, 1735, 1000, new Position(1761, 4952,0), "Pyra", "Elite", ""),
    TIER3_5("Geode", TPTier.TIER_3, 5539, 1000, new Position(1819, 4821,0), "Geode", "Elite", ""),
    TIER3_6("Cerulean", TPTier.TIER_3, 5547, 1000, new Position(1701, 5460,0), "Cerulean", "Elite", ""),
    TIER3_7("Scorch", TPTier.TIER_3, 5533, 1000, new Position(2335, 4693,0), "Scorch", "Elite", ""),
    TIER3_8("Geowind", TPTier.TIER_3, 5553, 1000, new Position(2666, 2783,0), "Geowind", "Elite", ""),
    TIER3_9("Gaia Guardian", TPTier.TIER_3, 3117, 1850, new Position(1762, 5335,0), "Gaia Guardian", "Elite", "Multi Boss"),

    //TIER 4
    TIER4_0("Goliath", TPTier.TIER_4, 1072, 1075, new Position(2600, 5218,0), "Goliath", "Master", ""),
    TIER4_1("Volcanus", TPTier.TIER_4, 1073, 900, new Position(2336, 5397,0), "Volcanus", "Master", ""),
    TIER4_2("Nautilus", TPTier.TIER_4, 1074, 900, new Position(2982, 5015,0), "Nautilus", "Master", ""),
    TIER4_3("Quake", TPTier.TIER_4, 1075, 914, new Position(2406, 5215,0), "Quake", "Master", ""),
    TIER4_4("Scaldor", TPTier.TIER_4, 1076, 900, new Position(2592, 5018,0), "Scaldor", "Master", ""),
    TIER4_5("Seabane", TPTier.TIER_4, 1077, 900, new Position(2850, 4953,0), "Seabane", "Master", ""),
    TIER4_6("Rumble", TPTier.TIER_4, 1078, 915, new Position(2473, 5347,0), "Rumble", "Master", ""),
    TIER4_7("Moltron", TPTier.TIER_4, 1079, 900, new Position(2719, 5016,0), "Moltron", "Master", ""),
    TIER4_8("Hydrox", TPTier.TIER_4, 1080, 900, new Position(2464, 5021,0), "Hydrox", "Master", ""),
    TIER4_9("Void Guardian", TPTier.TIER_4, 1081, 1850, new Position(3423, 4569, 3), "Void Guardian", "Master", "Multi Boss"),


    TIER5_1("Hellfire", TPTier.TIER_5, 2111, 1600, new Position(2014, 5530), "Hellfire", "Challenger", ""),
    TIER5_2("Cryos", TPTier.TIER_5, 2112, 1550, new Position(2976, 9246), "Cryos", "Challenger", ""),
    TIER5_3("Toxus", TPTier.TIER_5, 2113, 1600, new Position(2335, 9377,1), "Toxus", "Challenger", ""),
    TIER5_4("Gemstone", TPTier.TIER_5, 2114, 1600, new Position(3048, 9570), "Gemstone", "Challenger", ""),


    //BOSSES 2
    GLOBAL_BOSS_1("Rift Event", TPTier.BOSSES, 4444, 1900, new Position(2783, 3285, 1), "Rift Event", "Close the Void Rift", "Save Athens!"),
    GLOBAL_BOSS_2("Vote Boss", TPTier.BOSSES, 2342, 2100, new Position(2849, 5081,0), "Vote Boss", "Every " + "20", "Votes"),//TODO
    GLOBAL_BOSS_3("Dono Boss", TPTier.BOSSES, 2341, 1870, new Position(3616, 3351,0), "Dono Boss", "Every $500", "Donated"),//TODO
    GLOBAL_BOSS_4("Skeletal Demon", TPTier.BOSSES, 3307, 2550, new Position(1823, 4505,0), "Skeletal Demon", "Every 5", "Offerings"),//TODO
    GLOBAL_BOSS_5("Emerald Champion", TPTier.BOSSES, 3308, 1830, new Position(2719, 5722,0), "Emerald Champion", "Every 5000", "Resources"),//TODO
    GLOBAL_BOSS_6("Poseidon", TPTier.BOSSES, 8520, 1830, new Position(2976, 9246,4), "Poseidon", "God Of The Sea", "Seasonal Multi"),//TODO

    //SOLO MINIGAMES
    CORRUPT_RAID("Corrupt Raids", TPTier.MINIGAMES, 1037, 1300, new Position(2974, 3879,0), "Beginner Raids", "Unlock Elemental", "Weapon Upgrades"),
    SPECTRAL_RAID("Spectral Raids", TPTier.MINIGAMES, 2115, 1300, new Position(2521, 4833,0), "Advanced Raids", "Unlock Elemental", "Weapon Upgrades"),

    COE("Circle of Elements", TPTier.MINIGAMES, 995, 1200, new Position(2916, 2593,0), "Circle of Elements", "Unlock powerful", "Elemental Prayers"),//TODO
    ELEMENTAL_ENERGY("Fractured Crystal", TPTier.MINIGAMES, 1781, 1200, new Position(2912, 3550,0), "Fractured Crystal", "Gather Crystals", "Grow Stronger"),//TODO
    MYSTIC_MAYHEM("Mystic Mayhem", TPTier.MINIGAMES, 3170, 1000, new Position(2650, 3990, Misc.random(0, 1) * 4), "Mystic Mayhem", "Unlock Powerful", "Damage Boosters"),

    //GROUP MINIGAMES
    NECRO_MINIGAME("SoulBane Gauntlet", TPTier.MINIGAMES, 316, 2200, new Position(3047, 3865,0), "Soulbane Gauntlet", "Work Together", "Summon Soulbane"),
    TOWER_OF_ASCENSION("Tower of Ascension", TPTier.MINIGAMES, 6522, 1750, new Position(3486, 9246,0), "Tower of Ascension", "Battle Waves", "Survive!"),
    //SKILLING
    SALT_MINING("Salt Mine", TPTier.SKILLING, 2209, 700, new Position(2588, 4131,0), "Salt Mines", "Mine Powerful Salts", "Earn Rare Gems"),
    WOODCUTING("Woodcutting", TPTier.SKILLING, 231, 800, new Position(3244, 2978,0), "Woodcutting", "Train Woodcutting", "Gather Logs"),//TODO
    MINING("Mining", TPTier.SKILLING, 230, 800, new Position(3038, 3991,0), "Mining", "Train Mining", "Gather Ore"),//TODO


    ARCHON("Archon Zone", TPTier.DONATOR, 555, 2000, new Position(2399, 3554, 0), "Archon Zone", "", "$500 Rank"),//TODO
    CELESTIAL("Celestial Zone", TPTier.DONATOR, 556, 2000, new Position(2527, 3554, 0), "Celestial Zone", "", "$1000 Rank"),//TODO
    ASCENDENT("Ascendent Zone", TPTier.DONATOR, 557, 1835, new Position(2655, 3554, 0), "Ascendent Zone", "", "$1500 Rank"),//TODO
    GLADIATOR("Gladiator Zone", TPTier.DONATOR, 559, 2000, new Position(2783, 3544, 0), "Gladiator Zone", "", "$2000 Rank"),//TODO

    ;

    public static TPData forId(int id) {
        for (TPData data : values()) {
            if (data.npcId == id)
                return data;
        }
        return null;
    }


    public static List<TPData> forTier(TPTier tier) {
        List<TPData> list = new ArrayList<>();
        for (TPData data : values())
            if (data.tier == tier)
                list.add(data);
        return list;
    }

    TPData(String name, TPTier tier, int npcId, int modelZoom, Position position, String... info) {
        this.name = name;
        this.tier = tier;
        this.npcId = npcId;
        this.modelZoom = modelZoom;
        this.position = position;
        this.info = info;
    }

    private final String name;
    private final TPTier tier;
    private final int npcId;
    private final Position position;
    private final String[] info;
    private final int modelZoom;
    int randomValue = Misc.random(0, 3) * 4;
}
