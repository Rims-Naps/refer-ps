package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.GoodieBags.OwnerGoodiebagHandler;
import com.ruse.world.content.GoodieBags.UpgOwnerHandler;
import com.ruse.world.content.SlayerChests.BeastChest;
import com.ruse.world.content.SlayerChests.BeginnerSlayerChest;
import com.ruse.world.content.SlayerChests.EliteSlayerChest;
import com.ruse.world.content.SlayerChests.MediumSlayerChest;
import com.ruse.world.content.megaChests.MegaChest;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.content.casketopening.impl.*;
import com.ruse.world.content.casketopening.impl.TreasureTrinket;
import com.ruse.world.content.minigames.impl.CircleOfElements;
import com.ruse.world.content.zonechests.*;
import com.ruse.world.content.zonechests.CorruptKeyChest;
import com.ruse.world.content.zonechests.SpectralChest;
import com.ruse.world.content.zonechests.Tier1Totem;
import com.ruse.world.content.zonechests.Tier2Totem;
import com.ruse.world.content.zonechests.Tier3Totem;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;

public class
PossibleLootInterface {

    public static void openInterface(Player player, LootData data) {
        player.getPacketSender().sendInterface(101000);

        int stringStart = 101271;
        for (LootData loot : LootData.values()) {
            player.getPacketSender().sendItemOnInterface(stringStart++, loot.getItemId(), 1);
            String name = loot.name != null ? loot.name : "          " + ItemDefinition.forId(loot.getItemId()).getName();
            player.getPacketSender().sendString(stringStart++, (loot == data ? "@whi@" : "") + name);
        }

        player.getPacketSender().setScrollBar(101250, LootData.values().length * 39);

        int index = 101101;
        int i = 0;
        for (Item item : data.getLoot()) {
            player.getPacketSender().sendItemOnInterface(index++, item.getId(), item.getAmount());
            i++;
        }
        int rows = (i / 7) + 1;
        if (rows <= 6)
            rows = 6;
        player.getPacketSender().setScrollBar(101100, 2 + (rows * 35));

        for (int z = i; z < (rows * 7 >= 48 ? rows * 7 : 50); z++) {
            player.getPacketSender().sendItemOnInterface(index++, -1, 1);
        }
    }

    public static boolean handleButton(Player player, int buttonID) {
        if (buttonID >= 101272 && buttonID <= 101370) {
            int index = (buttonID - 101272) / 2;

            if (index <= LootData.values().length)
                openInterface(player, LootData.values()[index]);

            return true;
        }
        return false;
    }

    public enum LootData {
        VOTE_CRATE(731, VoteBox.loot),
        RIFT_KEY(5585, RiftChest.loot),
        CORRUPT_RAID(770, CorruptRaidChest.itemRewards),
        CORRUPT_RAID_MED(791, CorruptMediumRaidChest.itemRewards),
        CORRUPT_RAID_HARD(792, CorruptHardRaidChest.itemRewards),

        ELEMENTAL_CACHE(17129, ElementalCache.loot),
        ELEMENTAL_CACHE_U(2624, ElementalCacheU.loot),
        SPECTRAL_CACHE(2090, SpectralCache.loot),

        COE_CHEST(725, CircleOfElements.loot),

        OWNER_WEP(754, OwnerWepCrate.loot),

       // EGG_CRATE(720, EggCrate.loot),
        ENCHANTED_CRATE(17819, EnchantedCrate.loot),
        CORRUPT_CRATE(2009, CorruptCrate.loot),
        //LOVERS_CRATE(2622, LoversCrate.loot),
        PANDORAS_BOX(17130, PandoraBox.loot),
        NECRO_CRATE(23173, NecroCrate.loot),
        EARTH_CRATE(10256, EarthCrate.loot),
        WATER_CRATE(10260, WaterCrate.loot),
        FIRE_CRATE(10262, FireCrate.loot),
        OWNER_GOODIE(3578, OwnerGoodiebagHandler.loot),
        OWNER_GOODIEU(4022, UpgOwnerHandler.loot),

        TREASURE_TRINKET(438, TreasureTrinket.loot),

        NOVICE_CRATE(15666, NoviceWeaponCrate.loot),
        LOW_WEAPON(23171, LowTierWeaponCrate.loot),
        HIGH_WEAPON(23172, HighTierWeaponCrate.loot),
        VOID_CRATE(19118, VoidCrate.loot),
        BOX_OF_BLESSINGS(15669, BoxOfBlessings.loot),
        BOX_OF_TREASURE(15668, BoxOfTreasures.loot),
        BOND_CASKET(15670, BondCasket.loot),
        BRONZE_CASE(460, BronzeCaseLoot.loot),
        SILVER_CASE(461, SilverCaseLoot.loot),
        GOLD_CASE(462, GoldCaseLoot.loot),

        ELEMENTAL_KEY_1(20104, Tier1Chest.itemRewards),
        ELEMENTAL_KEY_2(20105, Tier2Chest.itemRewards),
        ELEMENTAL_KEY_3(20106, Tier3Chest.itemRewards),
        ELEMENTAL_KEY_4(20107, Tier4Chest.itemRewards),
        ELEMENTAL_KEY_5(20108, Tier5Chest.itemRewards),
        ELEMENTAL_KEY_6(20109, Tier6Chest.itemRewards),
        ELEMENTAL_KEY_7(20111, Tier7Chest.itemRewards),
        ELEMENTAL_KEY_8(20112, Tier8Chest.itemRewards),
        SLAYER_KEY_1(1302, BeginnerSlayerChest.itemRewards),
        SLAYER_KEY_2(1303, MediumSlayerChest.itemRewards),
        SLAYER_KEY_3(1304, EliteSlayerChest.itemRewards),

        BEAST_KEY(1305, BeastChest.itemRewards),
        CORRUPT_KEY(3512, CorruptKeyChest.itemRewards),
        SPECTRAL_KEY(2062, SpectralChest.itemRewards),

        TIER_1_TOTEM(20080, Tier1Totem.itemRewards),
        TIER_2_TOTEM(20081, Tier2Totem.itemRewards),
        TIER_3_TOTEM(20082, Tier3Totem.itemRewards),

        SPECTRAL_RAID(2053, SpectralRaidChest.itemRewards),
        SPECTRAL_RAID_MED(2054, SpectralRaidMediumChest.itemRewards),
        SPECTRAL_RAID_HARD(2055, SpectralRaidHardChest.itemRewards),


        ;

        private int itemId;
        private String name;
        private Item[] loot;


        LootData(int itemId, Item[] C, String name) {
            this.itemId = itemId;
            this.loot = new Item[C.length];
            this.name = name;
            int i = 0;
            for (Item d : C) {
                this.loot[i++] = new Item(d.getId(), d.getAmount());
            }
        }

        LootData(String name, int itemId, Box[] loot) {
            this.itemId = itemId;
            this.loot = new Item[loot.length];
            this.name = name;
            int i = 0;
            for (Box d : loot) {
                this.loot[i++] = new Item(d.getId(), d.getMax());
            }
        }


        LootData(int itemId, Box[] loot) {
            this.itemId = itemId;
            this.loot = new Item[loot.length];
            int i = 0;
            for (Box d : loot) {
                this.loot[i++] = new Item(d.getId(), d.getMax());
            }
        }



        LootData(int itemId, int[]... items) {
            this.itemId = itemId;

            ArrayList<Item> loot = new ArrayList<>();
            for (int i = 0; i < items.length; i++) {
                for (int z = 0; z < items[i].length; z++) {
                    loot.add(new Item(items[i][z]));
                }
            }
            this.loot = Misc.convertItems(loot);
        }
        LootData(int itemId, ArrayList<MegaChest.MegaChestReward> items) {
            this.itemId = itemId;

            ArrayList<Item> loot = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                loot.add(new Item(items.get(i).getId(), items.get(i).getAmount()));
            }
            this.loot = Misc.convertItems(loot);
        }

        LootData(int itemId, Item[]... items) {
            this.itemId = itemId;

            ArrayList<Item> loot = new ArrayList<>();
            for (int i = 0; i < items.length; i++) {
                for (int z = 0; z < items[i].length; z++) {
                    loot.add(items[i][z]);
                }
            }
            this.loot = Misc.convertItems(loot);
        }

        public int getItemId() {
            return itemId;
        }

        public Item[] getLoot() {
            return loot;
        }

    }

}
