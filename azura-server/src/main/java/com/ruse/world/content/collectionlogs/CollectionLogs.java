package com.ruse.world.content.collectionlogs;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.definitions.NPCDrops;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.world.content.KillsTracker;
import com.ruse.world.content.RiftChest;
import com.ruse.world.content.SlayerChests.BeastChest;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.content.casketopening.impl.*;
import com.ruse.world.content.minigames.impl.CircleOfElements;
import com.ruse.world.content.tower.TowerController;
import com.ruse.world.content.zonechests.*;
import com.ruse.world.content.zonechests.CorruptKeyChest;
import com.ruse.world.content.zonechests.SpectralChest;
import com.ruse.world.content.zonechests.Tier1Totem;
import com.ruse.world.content.zonechests.Tier2Totem;
import com.ruse.world.content.zonechests.Tier3Totem;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum CollectionLogs {

    NYTHOR(CollectionLogType.ZONES, 13747,new Item[]{new Item(20104, 25), new Item(15667, 10), new Item(534, 200)}),
    Terran(CollectionLogType.ZONES, 1801,new Item[]{new Item(2706, 1), new Item(17582, 2), new Item(17584, 2), new Item(17586, 2)}),
    Aqualorn(CollectionLogType.ZONES, 9027,new Item[]{new Item(10946, 1), new Item(17582, 3), new Item(17584, 3), new Item(17586, 3)}),
    Ferna(CollectionLogType.ZONES, 1802,new Item[]{new Item(17490, 10), new Item(20071, 10), new Item(20104, 50)}),
    Ignox(CollectionLogType.ZONES, 13458,new Item[]{new Item(2706, 1), new Item(15667, 20), new Item(534, 200)}),
    Crystalis(CollectionLogType.ZONES, 8006,new Item[]{new Item(15668, 1), new Item(17582, 3), new Item(20105, 40)}),
    Ember(CollectionLogType.ZONES, 688,new Item[]{new Item(10262, 1), new Item(17584, 3),new Item(20105, 40)}),
    Xerces(CollectionLogType.ZONES, 350,new Item[]{new Item(10256, 1), new Item(17586, 5),new Item(20105, 40)}),
    Marina(CollectionLogType.ZONES, 182,new Item[]{new Item(10260, 1), new Item(17582, 5), new Item(17584, 5), new Item(17586, 5)}),
    Lava_Guardian(CollectionLogType.MONSTERS, 6330,new Item[]{new Item(23058, 1),new Item(17130, 2)}),


    Kezel(CollectionLogType.ZONES, 9815,new Item[]{new Item(10946, 1),new Item(3580, 2),new Item(2706, 1)}),
    Hydrora(CollectionLogType.ZONES, 1741,new Item[]{new Item(10260, 1),new Item(20106, 100),new Item(17584, 5)}),
    Infernus(CollectionLogType.ZONES, 12228,new Item[]{new Item(10262, 1),new Item(20106, 150),new Item(17582, 5)}),
    Tellurion(CollectionLogType.ZONES, 9026,new Item[]{new Item(10256, 1),new Item(20106, 200),new Item(17586, 5)}),
    Marinus(CollectionLogType.ZONES, 1150,new Item[]{new Item(10946, 1),new Item(15670, 1)}),
    Pyrox(CollectionLogType.ZONES, 9837,new Item[]{new Item(3582, 1),new Item(20107, 200)}),
    Astaran(CollectionLogType.ZONES, 9002,new Item[]{new Item(23171, 1),new Item(15668, 2)}),
    Nereus(CollectionLogType.ZONES, 7000,new Item[]{new Item(23171, 1),new Item(17130, 1)}),
    Volcar(CollectionLogType.ZONES, 1821,new Item[]{new Item(23172, 1),new Item(3582, 1),new Item(20108, 150)}),
    Aqua_Guardian(CollectionLogType.MONSTERS, 9800,new Item[]{new Item(23058, 2),new Item(23173, 1)}),

    Lagoon(CollectionLogType.ZONES, 1727,new Item[]{new Item(15670, 1),new Item(20109, 250),new Item(15669, 2)}),
    Incendia(CollectionLogType.ZONES, 1729,new Item[]{new Item(15670, 1),new Item(23173, 1)}),
    Terra(CollectionLogType.ZONES, 1730,new Item[]{new Item(15670, 1),new Item(3580, 2),new Item(3582, 2)}),
    Abyss(CollectionLogType.ZONES, 1731,new Item[]{new Item(15670, 1),new Item(20109, 300)}),
    Pyra(CollectionLogType.ZONES, 1735,new Item[]{new Item(15670, 2),new Item(15669, 2)}),
    Geode(CollectionLogType.ZONES, 5539,new Item[]{new Item(15670, 2),new Item(15668, 5)}),
    Cerulean(CollectionLogType.ZONES, 5547,new Item[]{new Item(15670, 2),new Item(20109, 350),new Item(10946, 1)}),
    Scorch(CollectionLogType.ZONES, 5533,new Item[]{new Item(15670, 2),new Item(17130, 1),new Item(23173, 1)}),
    Geowind(CollectionLogType.ZONES, 5553,new Item[]{new Item(15671, 2),new Item(23172, 2),new Item(17129, 1)}),

    Goliath(CollectionLogType.ZONES, 1072,new Item[]{new Item(1448, 10),new Item(20111, 50),new Item(15670, 1)}),
    Volcanus(CollectionLogType.ZONES, 1073,new Item[]{new Item(15667, 15),new Item(20111, 125),new Item(10258, 50)}),
    Nautilus(CollectionLogType.ZONES, 1074,new Item[]{new Item(10258, 50),new Item(3582, 5),new Item(15670, 1)}),
    Quake(CollectionLogType.ZONES, 1075,new Item[]{new Item(15670, 2),new Item(20111, 150),new Item(10258, 50)}),
    Scaldor(CollectionLogType.ZONES, 1076,new Item[]{new Item(15667, 15),new Item(3582, 5),new Item(20111, 200)}),
    Seabane(CollectionLogType.ZONES, 1077,new Item[]{new Item(15670, 2),new Item(20112, 50),new Item(10258, 50)}),
    Rumble(CollectionLogType.ZONES, 1078,new Item[]{new Item(17129, 2),new Item(20112, 75),new Item(10258, 50)}),
    Moltron(CollectionLogType.ZONES, 1079,new Item[]{new Item(15670, 2),new Item(17129, 1),new Item(20112, 100)}),
    Hydrox(CollectionLogType.ZONES, 1080,new Item[]{new Item(19118, 1),new Item(3512, 10),new Item(10258, 200)}),

    Gemstone(CollectionLogType.ZONES, 2114,new Item[]{new Item(23058, 2),new Item(460, 1),new Item(19062, 1000)}),

    Gaia_Guardian(CollectionLogType.MONSTERS, 3117,new Item[]{new Item(23059, 1),new Item(23173, 2)}),
    Void_Guardian(CollectionLogType.MONSTERS, 1081,new Item[]{new Item(15670, 5),new Item(20112, 250),new Item(10258, 200)}),
    Easy_Superior(CollectionLogType.MONSTERS, 6830,new Item[]{new Item(15668, 3),new Item(15670, 2),new Item(3576, 5000)}),
    Medium_Superior(CollectionLogType.MONSTERS, 6841,new Item[]{new Item(3578, 1),new Item(15671, 3),new Item(5585, 20)}),
    Elite_Superior(CollectionLogType.MONSTERS, 6831,new Item[]{new Item(3578, 2),new Item(995, 25000),new Item(6466, 20000)}),



    Corrupt_Superior(CollectionLogType.MONSTERS, 4410,new Item[]{new Item(3502, 2000),new Item(2009, 2),new Item(3512, 10)}),

    Spectral_Superior(CollectionLogType.MONSTERS, 4411,new Item[]{new Item(2064, 3500),new Item(19118, 1),new Item(2062, 25)}),

    Brimstone(CollectionLogType.BOSSES, 2017,new Item[]{new Item(6466, 25000),new Item(3578, 1),new Item(1305, 25)}),

    Everthorn(CollectionLogType.BOSSES, 6323,new Item[]{new Item(6466, 25000),new Item(17129, 1),new Item(1305, 25)}),

    Basilisk(CollectionLogType.BOSSES, 2018,new Item[]{new Item(6466, 25000),new Item(19118, 1),new Item(1305, 25)}),

    Oracle(CollectionLogType.BOSSES, 2465,new Item[]{new Item(3502, 5000),new Item(3512, 20),new Item(2009, 5)}),

    Grimlash(CollectionLogType.BOSSES, 2467,new Item[]{new Item(3502, 5000),new Item(3512, 20),new Item(2009, 5)}),

    Rockmaw(CollectionLogType.BOSSES, 2466,new Item[]{new Item(3502, 5000),new Item(3512, 20),new Item(2009, 5)}),

    Vote_boss(CollectionLogType.BOSSES, 2342,new Item[]{new Item(23020, 20),new Item(3578, 2) ,new Item(17129, 1)}),
    Dono_Boss(CollectionLogType.BOSSES, 2341,new Item[]{new Item(4022, 1),new Item(2624, 1) ,new Item(23059, 1)}),
    Emerald_Champion(CollectionLogType.BOSSES, 3308,new Item[]{new Item(1447, 10),new Item(2703, 1)}),
    Skeletal_Demon(CollectionLogType.BOSSES, 3307,new Item[]{new Item(3578, 2),new Item(17129, 1) ,new Item(20112, 100)}),
    Slayer_Beast(CollectionLogType.BOSSES, 8000,new Item[]{new Item(3576, 10000),new Item(1304, 25),new Item(1305, 15)}),

    Hellfire(CollectionLogType.BOSSES, 2111,new Item[]{new Item(3578, 1), new Item(23058, 2),new Item(461, 1)}),
    Cryos(CollectionLogType.BOSSES, 2112,new Item[]{new Item(17129, 5), new Item(23058, 2),new Item(461, 1)}),
    Toxus(CollectionLogType.BOSSES, 2113,new Item[]{new Item(754, 1), new Item(23058, 2),new Item(461, 1)}),


    //MINIGAMES

    CIRCLE_OF_ELEMENTS(CollectionLogType.MINIGAMES, 725,new Item[]{new Item(3512, 50),new Item(2624, 2)}, CircleOfElements.loot, true),

    SOULBANE(CollectionLogType.MINIGAMES, "SoulBane", true, new Item[]{new Item(621, 50000),new Item(23173, 2)}, Tier1Totem.SUPER_RARE, Tier1Totem.RARE , Tier1Totem.CAPE_RARE, Tier1Totem.UNCOMMON, Tier2Totem.SUPER_RARE, Tier2Totem.CAPE_RARE, Tier2Totem.RARE, Tier2Totem.UNCOMMON, Tier3Totem.SUPER_RARE, Tier3Totem.CAPE_RARE, Tier3Totem.RARE, Tier3Totem.UNCOMMON),
    TOWER(CollectionLogType.MINIGAMES, "Tower of Ascension", true, new Item[]{new Item(12427, 10),new Item(19118, 2)}, TowerController.rares),
    //BOXES
    WATER_CRATE(CollectionLogType.BOXES,10260,new Item[]{new Item(23171, 5),new Item(23058, 1)}, WaterCrate.loot, true),
    LAVA_CRATE(CollectionLogType.BOXES,10262,new Item[]{new Item(23171, 5),new Item(23058, 1)}, FireCrate.loot, true),
    EARTH_CRATE(CollectionLogType.BOXES, 10256,new Item[]{new Item(23171, 5),new Item(23058, 1)}, EarthCrate.loot, true),

    NOVICE_WEAPON_CRATE(CollectionLogType.BOXES, 15666,new Item[]{new Item(10946, 5),new Item(23172, 1)}, NoviceWeaponCrate.loot, true),
    LOW_WEAPON_CRATE(CollectionLogType.BOXES, 23171,new Item[]{new Item(23172, 3),new Item(23058, 1)}, LowTierWeaponCrate.loot, true),
    HIGH_WEAPON_CRATE(CollectionLogType.BOXES, 23172,new Item[]{new Item(3578, 2),new Item(23059, 1)}, HighTierWeaponCrate.loot, true),
    VOID_CRATE(CollectionLogType.BOXES, 19118,new Item[]{new Item(17130, 2),new Item(23058, 1)}, VoidCrate.loot, true),
    ELEMENTAL_CACHE(CollectionLogType.BOXES, 17129,new Item[]{new Item(23173, 3),new Item(23058, 1)}, ElementalCache.loot, true),
    SPECTRAL_CACHE(CollectionLogType.BOXES, 2090,new Item[]{new Item(2062, 25),new Item(23058, 2)}, SpectralCache.loot, true),

    NECRO_CRATE(CollectionLogType.BOXES, 23173,new Item[]{new Item(15671, 5),new Item(23059, 1)}, NecroCrate.loot, true),
    PANDORAS_BOX(CollectionLogType.BOXES, 17130,new Item[]{new Item(17129, 2),new Item(23057, 1)}, PandoraBox.loot, true),
    BOX_OF_TREASURES(CollectionLogType.BOXES, 15668,new Item[]{new Item(23172, 2),new Item(23057, 1)}, BoxOfTreasures.loot, true),
    BOX_OF_BLESSINGS(CollectionLogType.BOXES, 15669,new Item[]{new Item(3578, 1),new Item(23058, 1)}, BoxOfBlessings.loot, true),
    BOND_CASKET(CollectionLogType.BOXES, 15670,new Item[]{new Item(17130, 2),new Item(23058, 1)}, BondCasket.loot, true),

    BOND_CASKET_ENCHANTED(CollectionLogType.BOXES, 15671,new Item[]{new Item(23173, 1),new Item(23059, 1)}, BondCasketEnchanted.loot, true),
    BOX_OF_PLUNDERS(CollectionLogType.BOXES, 15667,new Item[]{new Item(15670, 2),new Item(10946, 1)}, BoxOfPlunders.loot, true),
   // FROST_CRATE(CollectionLogType.BOXES,1453,new Item[]{new Item(1451, 30),new Item(1452, 30)}, FrostCrate.loot, true),
   // EGG_CRATE(CollectionLogType.BOXES,720,new Item[]{new Item(720, 4),new Item(715, 500)}, EggCrate.loot, true),
    OWNER_WEP(CollectionLogType.BOXES, 754,new Item[]{new Item(3578, 5),new Item(5585, 50)}, OwnerWepCrate.loot, true),

    CORRUPT_CRATE(CollectionLogType.BOXES,2009,new Item[]{new Item(3512, 15),new Item(2009, 5)}, CorruptCrate.loot, true),
    ENCHANTED_CRATE(CollectionLogType.BOXES,17819,new Item[]{new Item(17819, 5),new Item(2699, 200)}, EnchantedCrate.loot, true),




    ELEMENTAL_KEY_1(CollectionLogType.BOXES, "Elemental Key(1)", Tier1Chest.itemRewards, true,new Item[]{new Item(10946, 1)}),
    ELEMENTAL_KEY_2(CollectionLogType.BOXES, "Elemental Key(2)", Tier2Chest.itemRewards, true,new Item[]{new Item(10946, 1)}),
    ELEMENTAL_KEY_3(CollectionLogType.BOXES, "Elemental Key(3)", Tier3Chest.itemRewards, true,new Item[]{new Item(10946, 2)}),
    ELEMENTAL_KEY_4(CollectionLogType.BOXES, "Elemental Key(4)", Tier4Chest.itemRewards, true,new Item[]{new Item(10946, 2)}),
    ELEMENTAL_KEY_5(CollectionLogType.BOXES, "Elemental Key(5)", Tier5Chest.itemRewards, true,new Item[]{new Item(23058, 1)}),
    ELEMENTAL_KEY_6(CollectionLogType.BOXES, "Elemental Key(6)", Tier6Chest.itemRewards, true,new Item[]{new Item(23058, 1)}),
    ELEMENTAL_KEY_7(CollectionLogType.BOXES, "Elemental Key(7)", Tier7Chest.itemRewards, true,new Item[]{new Item(23058, 1)}),
    ELEMENTAL_KEY_8(CollectionLogType.BOXES, "Elemental Key(8)", Tier8Chest.itemRewards, true,new Item[]{new Item(23058, 1)}),

    CORRUPT_RAID(CollectionLogType.MINIGAMES, "Corrupt Raid", CorruptRaidChest.itemRewards, true,new Item[]{new Item(23057, 1),new Item(23171, 1),new Item(995, 10000),}),
    CORRUPT_RAID_MEDIUM(CollectionLogType.MINIGAMES, "Corrupt Raid Med", CorruptMediumRaidChest.itemRewards, true,new Item[]{new Item(770, 30),new Item(23058, 1),new Item(15668, 5),}),
    CORRUPT_RAID_HARD(CollectionLogType.MINIGAMES, "Corrupt Raid Hard", CorruptHardRaidChest.itemRewards, true,new Item[]{new Item(3578, 1),new Item(23059, 1), new Item(2702, 1),}),



    SPECTRAL_RAID(CollectionLogType.MINIGAMES, "Spectral Raid", SpectralRaidChest.itemRewards, true,new Item[]{new Item(2053, 100),new Item(23057, 1),new Item(430, 1),}),
    SPECTRAL_RAID_MEDIUM(CollectionLogType.MINIGAMES, "Spectral Raid Med", SpectralRaidMediumChest.itemRewards, true,new Item[]{new Item(2054, 75),new Item(23058, 2),new Item(431, 1),}),
    SPECTRAL_RAID_HARD(CollectionLogType.MINIGAMES, "Spectral Raid Hard", SpectralRaidHardChest.itemRewards, true,new Item[]{new Item(2055, 50),new Item(23059, 1), new Item(432, 1),}),


    BEAST_KEY(CollectionLogType.BOXES, "Beast Key", BeastChest.itemRewards, true,new Item[]{new Item(1526, 1),new Item(6466, 25000)}),
    CORRUPT_KEY(CollectionLogType.BOXES, "Corrupt Key", CorruptKeyChest.itemRewards, true,new Item[]{new Item(3502, 10000),new Item(2624, 1)}),
    SPECTRAL_KEY(CollectionLogType.BOXES, "Spectral Key", SpectralChest.itemRewards, true,new Item[]{new Item(2064, 10000),new Item(4022, 2)}),


    //CHRISTMAS_KEY_1(CollectionLogType.BOXES, "Christmas Key(1)", XmasChest1.itemRewards, true,new Item[]{new Item(1453, 2)}),

    //CRHSITMAS_KEY_2(CollectionLogType.BOXES, "Christmas Key(2)", XmasChest2.itemRewards, true,new Item[]{new Item(1453, 4)}),

    RIFT_KEY(CollectionLogType.BOXES, "Rift Key", RiftChest.loot, true,new Item[]{new Item(3578, 1),new Item(15671, 3),new Item(5585, 20)}),
    ;


    CollectionLogs(CollectionLogType type, int npcID, Item[] reward) {
        this(type, npcID,  false, reward);
    }

    CollectionLogs(CollectionLogType type, int npcID, boolean announce, Item reward[]) {
        this.reward = reward;
        this.type = type;
        this.name = NpcDefinition.forId(npcID).getName();
        this.npcId = npcID;
        this.uniqueItems = new ArrayList<>();
        this.announce = announce;
        for (NPCDrops.NpcDropItem npcDropItem : NPCDrops.forId(npcID).getDropList()) {
            if (npcDropItem.getChance() >= 50 || npcDropItem.getId() == 995 || npcDropItem.getId() == 3576 || npcID == 6830 || npcID == 6841 || npcID == 6831

                    || npcID == 2341 || npcID == 2342 || npcID == 8000 || npcID == 3307 || npcID == 3308) {

                if (!uniqueItems.contains(npcDropItem.getId())) {
                    uniqueItems.add(npcDropItem.getId());
                }
            }
        }
    }

    CollectionLogs(CollectionLogType type, String name, Box[] loot, Item[] reward) {
        this(type, name, loot, false, reward);
    }

    CollectionLogs(CollectionLogType type, String name, Item[] loot, boolean announce, Item[] reward) {
        this.reward = reward;
        this.type = type;
        this.name = name;
        this.npcId = -1;
        this.uniqueItems = new ArrayList<>();
        this.announce = announce;
        for (Item item : loot) {
            if (item.getId() < 0)
                continue;
            if (!uniqueItems.contains(item.getId())) {
                uniqueItems.add(item.getId());
            }
        }
    }

    CollectionLogs(CollectionLogType type, String name, Box[] loot, boolean announce, Item... reward) {
        this.reward = reward;
        this.type = type;
        this.name = name;
        this.npcId = -1;
        this.uniqueItems = new ArrayList<>();
        this.announce = announce;
        for (Box item : loot) {
            if (!uniqueItems.contains(item.getId())) {
                uniqueItems.add(item.getId());
            }
        }
    }

    CollectionLogs(CollectionLogType type, int itemID, Item reward[] ,Box[] loot,  boolean announce) {
        this.reward = reward;
        this.type = type;
        this.name = ItemDefinition.forId(itemID).getName();
        this.npcId = -1;
        this.itemId = itemID;
        this.uniqueItems = new ArrayList<>();
        this.announce = announce;
        for (Box item : loot) {
            if (!uniqueItems.contains(item.getId())) {
                uniqueItems.add(item.getId());
            }
        }
    }


    CollectionLogs(CollectionLogType type, String name, boolean announce, Item[] reward, int[]... items) {
        this.reward = reward;
        this.type = type;
        this.name = name;
        this.itemId = -1;
        this.npcId = -1;
        this.uniqueItems = new ArrayList<>();
        this.announce = announce;
        for (int i = 0; i < items.length; i++) {
            for (int z = 0; z < items[i].length; z++) {
                if (!uniqueItems.contains(items[i][z])) {
                    uniqueItems.add(items[i][z]);
                }
            }
        }
    }

    CollectionLogs(CollectionLogType type, int itemID, Item reward[], int[]... items) {
        this(type, itemID, false, reward, items);
    }


    CollectionLogs(CollectionLogType type, int itemID, boolean announce, Item[] reward, int[]... items) {
        this.reward = reward;
        this.type = type;
        this.name = ItemDefinition.forId(itemID).getName();
        this.npcId = -1;
        this.itemId = itemID;
        this.uniqueItems = new ArrayList<>();
        this.announce = announce;
        for (int i = 0; i < items.length; i++) {
            for (int z = 0; z < items[i].length; z++) {
                if (!uniqueItems.contains(items[i][z])) {
                    uniqueItems.add(items[i][z]);
                }
            }
        }
    }

    @Getter
    private CollectionLogType type;
    @Getter
    private int npcId;
    @Getter
    private int itemId;
    @Getter
    private boolean announce;
    @Getter
    private String name;
    @Getter
    private List<Integer> uniqueItems;
    @Getter
    private Item[] reward;

    public int getKillCount(Player player) {
        if (type.ordinal() <= CollectionLogType.BOSSES.ordinal()) {
            return KillsTracker.getTotalKillsForNpc(npcId, player);
        }
        return 0;
    }

    public static CollectionLogs forNpcId(int npcId) {
        for (CollectionLogs logs : CollectionLogs.values()) {
            if (logs.npcId == npcId)
                return logs;
        }
        return null;
    }
    public static CollectionLogs forItemId(int itemId) {
        for (CollectionLogs logs : CollectionLogs.values()) {
            if (logs.itemId == itemId)
                return logs;
        }
        return null;
    }


    public CollectionLogs forName(String name) {
        for (CollectionLogs logs : CollectionLogs.values()) {
            if (logs.name().equalsIgnoreCase(name))
                return logs;
        }
        return null;
    }
}
