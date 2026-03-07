package com.ruse.world.content.megaChests;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.util.Stopwatch;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ruse.world.content.megaChests.MegaChest.MegaChestRarity.*;

public class MegaChest {

    private Player player;
    private int itemId;
    private MegaChestReward[] rewards;
    private boolean opening;
    private boolean sentfade;


    private Stopwatch stopwatch = new Stopwatch();

    public MegaChest(Player player) {
        this.player = player;
    }

    public boolean handleButton(int buttonId) {
        if (buttonId == 141507)
            openAgain();
        if (buttonId == 141006) {
            openAgain();
        }
        if (buttonId == 141506) {
            openChest();
        }
        return false;
    }

    public void openAgain() {
        if (!stopwatch.elapsed(200)) {
            return;
        }
        if (itemId > 0) {
            if (!player.getInventory().contains(itemId)) {
                player.sendMessage("You do not have a " + ItemDefinition.forId(itemId).getName());
                return;
            }

            player.getMegaChest().handleChestClick(itemId);

        }
        }


    public void handleChestClick(int item) {
        if (opening) {
            return;
        }
        this.itemId = item;
        if (item > 0) {
            if (itemId == 460) {
                player.getPacketSender().sendInterface(141500);
                player.getPacketSender().sendInterfaceItemModel(25358, itemId);
                player.getPacketSender().sendSpriteChange(141501, 2004);
                return;
            }
            if (itemId == 461) {
                player.getPacketSender().sendInterface(141500);
                player.getPacketSender().sendInterfaceItemModel(25358, itemId);
                player.getPacketSender().sendSpriteChange(141501, 2005);
                return;
            }
            if (itemId == 462) {
                player.getPacketSender().sendInterface(141500);
                player.getPacketSender().sendInterfaceItemModel(25358, itemId);
                player.getPacketSender().sendSpriteChange(141501, 2006);
                return;
            }
        }
    }

    public void openChest() {
       // System.out.println("openPack() called"); // Debug check
        if (opening) {
            return;
        }
        if (player.getInventory().contains(itemId)) {
            player.getPacketSender().sendInterfaceRemoval();
            player.getInventory().delete(itemId, 1);
            player.getPacketSender().sendFadeTransition(60, 25, 40);
            opening = true;

            TaskManager.submit(new Task(1, true) {
                int tick = 0;

                @Override
                protected void execute() {
                    tick++;


                        if (tick == 5) {

                            player.getPacketSender().sendInterface(141000);

                            if (itemId == 460) {
                                player.getPacketSender().sendSpriteChange(141001, 2004);
                            }
                            if (itemId == 461) {
                                player.getPacketSender().sendSpriteChange(141001, 2005);
                            }
                            if (itemId == 462) {
                                player.getPacketSender().sendSpriteChange(141001, 2006);
                            }

                        int interfaceId = 141007;
                        boolean noSpace = false;
                        for (int i = 0; i < 6; i++) {
                            MegaChestReward PRIZE = getLoot(loot);
                            player.getPacketSender().sendSpriteChange(interfaceId++, PRIZE.getRarity().getSpriteID());
                            player.getPacketSender().sendItemOnInterface(interfaceId++, PRIZE.getId(), PRIZE.getAmount());

                                String message = "@mag@<shad=0>" + player.getUsername() + " @mag@<shad=0>has received@red@<shad=0>"
                                        + (PRIZE.getAmount() > 1 ? " x@red@<shad=0>" + PRIZE.getAmount() : "") + " "
                                        + ItemDefinition.forId(PRIZE.getId()).getName() + "@mag@<shad=0> from an <col=0084ff>@red@<shad=0>" +
                                        ItemDefinition.forId(itemId).getName() + "!";

                                if (PRIZE.rarity == GOLD || PRIZE.rarity == DIAMOND || PRIZE.rarity == EXOTIC)
                                World.sendNewsMessage(message);

                            if ((!ItemDefinition.forId(PRIZE.getId()).isStackable()
                                    && player.getInventory().getFreeSlots() == 0)
                                    || (ItemDefinition.forId(PRIZE.getId()).isStackable() && !player.getInventory().contains(PRIZE.getId())
                                    && player.getInventory().getFreeSlots() == 0)) {
                                noSpace = true;
                                player.depositItemBank(new Item(PRIZE.getId(), PRIZE.getAmount()), false);
                            } else {
                                player.getInventory().add(new Item(PRIZE.getId(), PRIZE.getAmount()), false);
                            }
                        }

                            opening = false;
                        player.getInventory().refreshItems();
                        if (noSpace)
                            player.sendMessage("Some items were deposited into your bank.");

                        stop();
                    }
                }
            });
        }
    }


    public MegaChestReward getLoot(MegaChestReward[] loot) {
       // System.out.println("getLoot() called"); // Debug check
        HashMap<Double, ArrayList<MegaChestReward>> dropRates = new HashMap<>();
        ArrayList<MegaChestReward> potentialDrops = new ArrayList<>();

        for (MegaChestReward drop : loot) {
            if (drop == null)
                continue;
            double divisor = MegaChestRates.forId(itemId).getRates()[drop.getRarity().ordinal()];
            if (!dropRates.containsKey(divisor)) {
                ArrayList<MegaChestReward> items = new ArrayList<>();
                items.add(drop);
                dropRates.put(divisor, items);
            } else {
                dropRates.get(divisor).add(drop);
            }
        }

        MegaChestRarity rarity = getDropTable(Misc.getRandomDouble() * 100);

        for (double dropRate : dropRates.keySet()) {
            if (dropRate == MegaChestRates.forId(itemId).getRates()[rarity.ordinal()]) {
                potentialDrops.add(dropRates.get(dropRate).get(Misc.getRandom(dropRates.get(dropRate).size() - 1)));
            }
        }

        if (potentialDrops.size() > 0) {
            return potentialDrops.get(Misc.getRandom((potentialDrops.size() - 1)));
        } else {
            return getLoot(loot);
        }
    }


    public MegaChestRarity getDropTable(double rate) {
        //System.out.println("getDropTable() called"); // Debug check
        double[] rates = new double[MegaChestRarity.values().length];
        for (MegaChestRarity r : MegaChestRarity.values()) {
            rates[r.ordinal()] = MegaChestRates.forId(itemId).getRates()[r.ordinal()]
                    + (r.ordinal() > 0 ? rates[r.ordinal() - 1] : 0);
        }
        for (int i = 0; i < rates.length; i++) {
            if (rate <= rates[i]) {
                return MegaChestRarity.values()[i];
            }
        }
        return BRONZE;
    }


    @Getter
    @AllArgsConstructor
    public static class MegaChestReward {
        private int id;
        private int amount;
        private MegaChestRarity rarity;
    }

    @Getter
    @AllArgsConstructor
    public enum MegaChestRarity {
        EXOTIC(2016),
        DIAMOND(2015),
        GOLD(2009),
        SILVER(2008),
        BRONZE(2007),
        ;

        private int spriteID;
    }

    @Getter
    @AllArgsConstructor
    public enum MegaChestRates {

        GOLD(462, ChestRates.GoldRates),
        SILVER(461, ChestRates.SilverRates),
        BRONZE(460, ChestRates.BronzeRates),
        ;
        private int itemId;
        private double[] rates;

        public static MegaChestRates forId(int id) {
            for (MegaChestRates data : MegaChestRates.values()) {
                if (data.getItemId() == id) {
                    return data;
                }
            }
            return null;
        }
    }

    public static ArrayList<MegaChestReward> getRewards(int itemId) {
        ArrayList<MegaChestReward> rewards = new ArrayList<>();
        ArrayList<MegaChestRarity> rarities = new ArrayList<>();
        for (int i = 0; i < MegaChestRates.forId(itemId).getRates().length; i++) {
            if (MegaChestRates.forId(itemId).getRates()[i] > 0)
                rarities.add(MegaChestRarity.values()[i]);
        }

        for (MegaChestReward chestReward : loot) {
            if (rarities.contains(chestReward.getRarity())) {
                rewards.add(chestReward);
            }
        }

        return rewards;

    }


    public static MegaChestReward[] loot = {
            new MegaChestReward(1446, 3, BRONZE), // Resource Crate T1
            new MegaChestReward(770, 15, BRONZE), // Corrupt Raids Key
            new MegaChestReward(23173, 1, BRONZE), // Necromancy Crate
            new MegaChestReward(10260, 2, BRONZE), // Water Crate
            new MegaChestReward(10262, 2, BRONZE), // Earth Crate
            new MegaChestReward(10945, 10, BRONZE), // $1 Bond
            new MegaChestReward(10945, 7, BRONZE), // $1 Bond
            new MegaChestReward(10945, 5, BRONZE), // $1 Bond
            new MegaChestReward(995, 2000, BRONZE), // Coins
            new MegaChestReward(995, 2250, BRONZE), // Coins
            new MegaChestReward(995, 3000, BRONZE), // Coins
            new MegaChestReward(19062, 60, BRONZE), // Monster Essence
            new MegaChestReward(19062, 80, BRONZE), // Monster Essence
            new MegaChestReward(10946, 1, BRONZE), // $5 Bond
            new MegaChestReward(10946, 2, BRONZE), // $5 Bond
            new MegaChestReward(2706, 2, BRONZE), // Dream Ticket
            new MegaChestReward(2706, 2, BRONZE), // Dream Ticket
            new MegaChestReward(2706, 2, BRONZE), // Dream Ticket
            new MegaChestReward(10256, 2, BRONZE), // Fire Crate
            new MegaChestReward(15670, 2, BRONZE), // Bond Casket
            new MegaChestReward(20106, 35, BRONZE), // Elemental Key (3)
            new MegaChestReward(20106, 50, BRONZE), // Elemental Key (3)
            new MegaChestReward(17130, 3, BRONZE), // Pandora's Box
            new MegaChestReward(17129, 1, BRONZE), // Elemental Cache
            new MegaChestReward(17129, 1, BRONZE), // Elemental Cache
            new MegaChestReward(5585, 15, BRONZE), // Rift Key
            new MegaChestReward(995, 3250, BRONZE), // Coins
            new MegaChestReward(995, 2500, BRONZE), // Coins
            new MegaChestReward(995, 2750, BRONZE), // Coins
            new MegaChestReward(995, 3750, BRONZE), // Coins
            new MegaChestReward(995, 3500, BRONZE), // Coins
            new MegaChestReward(995, 4000, BRONZE), // Coins
            new MegaChestReward(20107, 35, BRONZE), // Elemental Key (4)
            new MegaChestReward(15668, 1, BRONZE), // Box of Treasure
            new MegaChestReward(15668, 2, BRONZE), // Box of Treasure
            new MegaChestReward(8087, 1, BRONZE), // Seafire Blade
            new MegaChestReward(14006, 1, BRONZE), // Seafire Staff
            new MegaChestReward(16871, 1, BRONZE), // Seafire Bow
            new MegaChestReward(18638, 1, BRONZE), // Vire Blade
            new MegaChestReward(13329, 1, BRONZE), // Torrid Bow
            new MegaChestReward(20171, 1, BRONZE), // Tidal Axe
            new MegaChestReward(17127, 15, BRONZE), // Slayer Lamp
            new MegaChestReward(15669, 2, BRONZE), // Box of Blessing
            new MegaChestReward(15669, 4, BRONZE), // Box of Blessing
            new MegaChestReward(2706, 1, BRONZE), // Dream Ticket (u)
            new MegaChestReward(2706, 1, BRONZE), // Dream Ticket (u)
            new MegaChestReward(995, 4250, BRONZE), // Coins
            new MegaChestReward(995, 4150, BRONZE), // Coins
            new MegaChestReward(995, 3650, BRONZE), // Coins
            new MegaChestReward(995, 2850, BRONZE), // Coins
            new MegaChestReward(995, 4350, BRONZE), // Coins
            new MegaChestReward(995, 4650, BRONZE), // Coins
            new MegaChestReward(20066, 25, BRONZE), // Necro xp Lamp
            new MegaChestReward(20066, 15, BRONZE), // Necro xp Lamp
            new MegaChestReward(1448, 2, BRONZE), // Potion Pack
            new MegaChestReward(1448, 4, BRONZE), // Potion Pack


            new MegaChestReward(1446, 5, SILVER), // Resource Crate (T1)
            new MegaChestReward(10945, 30, SILVER), // $1 Bond
            new MegaChestReward(10945, 20, SILVER), // $1 Bond
            new MegaChestReward(995, 7500, SILVER), // Coins
            new MegaChestReward(995, 8000, SILVER), // Coins
            new MegaChestReward(995, 8500, SILVER), // Coins
            new MegaChestReward(19062, 180, SILVER), // Monster Essence
            new MegaChestReward(19062, 220, SILVER), // Monster Essence
            new MegaChestReward(10946, 2, SILVER), // $5 Bond
            new MegaChestReward(10946, 4, SILVER), // $5 Bond
            new MegaChestReward(2706, 4, SILVER), // Dream Ticket
            new MegaChestReward(2706, 6, SILVER), // Dream Ticket
            new MegaChestReward(20108, 35, SILVER), // Elemental Key (5)
            new MegaChestReward(20108, 40, SILVER), // Elemental Key (5)
            new MegaChestReward(20109, 45, SILVER), // Elemental Key (6)
            new MegaChestReward(20109, 55, SILVER), // Elemental Key (6)
            new MegaChestReward(17129, 2, SILVER), // Elemental Cache
            new MegaChestReward(17129, 2, SILVER), // Elemental Cache
            new MegaChestReward(5585, 20, SILVER), // Rift Key
            new MegaChestReward(5585, 25, SILVER), // Rift Key
            new MegaChestReward(995, 9000, SILVER), // Coins
            new MegaChestReward(995, 9250, SILVER), // Coins
            new MegaChestReward(995, 9750, SILVER), // Coins
            new MegaChestReward(995, 10000, SILVER), // Coins
            new MegaChestReward(995, 12500, SILVER), // Coins
            new MegaChestReward(995, 12750, SILVER), // Coins
            new MegaChestReward(995, 7500, SILVER), // Cape Upgrade Fragment
            new MegaChestReward(995, 8500, SILVER), // Cape Upgrade Fragment
            new MegaChestReward(15668, 2, SILVER), // Box of Treasure
            new MegaChestReward(15668, 3, SILVER), // Box of Treasure
            new MegaChestReward(17127, 35, SILVER), // Slayer Lamp
            new MegaChestReward(2706, 3, SILVER), // Dream Ticket (u)
            new MegaChestReward(2706, 3, SILVER), // Dream Ticket (u)
            new MegaChestReward(19062, 125, SILVER), // Monster Essence
            new MegaChestReward(19062, 140, SILVER), // Monster Essence
            new MegaChestReward(19062, 165, SILVER), // Monster Essence
            new MegaChestReward(19062, 200, SILVER), // Monster Essence
            new MegaChestReward(19062, 225, SILVER), // Monster Essence
            new MegaChestReward(19062, 260, SILVER), // Monster Essence
            new MegaChestReward(17127, 25, SILVER), // Slayer Lamp
            new MegaChestReward(17127, 30, SILVER), // Slayer Lamp
            new MegaChestReward(1448, 6, SILVER), // Potion Pack
            new MegaChestReward(1448, 10, SILVER), // Potion Pack
            new MegaChestReward(15668, 3, SILVER), // Box of Treasure
            new MegaChestReward(2009, 1, SILVER), // Corrupt Crate
            new MegaChestReward(19118, 1, SILVER), // Void Crate
            new MegaChestReward(3578, 1, SILVER), // Owner Goodiebag
            new MegaChestReward(23058, 1, SILVER), // $25 Bond
            new MegaChestReward(15671, 1, SILVER), // Bond Casket (u)
            new MegaChestReward(15671, 2, SILVER), // Bond Casket (u)
            new MegaChestReward(3582, 3, SILVER), // Rune Pack (T2)
            new MegaChestReward(3582, 5, SILVER), // Rune Pack (T2)
            new MegaChestReward(23173, 5, SILVER), // Necromancy Crate
            new MegaChestReward(23057, 2, SILVER), // $10 Bond
            new MegaChestReward(1447, 5, SILVER), // Resource Crate (T2)
            new MegaChestReward(23171, 2, SILVER), // Medium Weapon Crate
            new MegaChestReward(23172, 1, SILVER), // Elite Weapon Crate
            new MegaChestReward(17109, 1, SILVER), // Igntion Axe
            new MegaChestReward(17110, 1, SILVER), // Bedrock Sceptre


            new MegaChestReward(406, 1, GOLD), // Helmet Mould (2)
            new MegaChestReward(17053, 1, GOLD), // Mystic Boots (6)
            new MegaChestReward(407, 1, GOLD), // Chest Mould (2)
            new MegaChestReward(17052, 1, GOLD), // Mystic Gloves (6)
            new MegaChestReward(3578, 2, GOLD), // Owner's Cape Goodiebag
            new MegaChestReward(754, 1, GOLD), // Owner Weapon Crate
            new MegaChestReward(408, 1, GOLD), // Legs Mould (2)
            new MegaChestReward(4022, 1, GOLD), // Owner's Goodiebag (U)
            new MegaChestReward(23058, 2, GOLD), // $50 Bond
            new MegaChestReward(2624, 1, GOLD), // Elemental Cache (U)
            new MegaChestReward(17819, 4, GOLD), // Enchanted Casket
            new MegaChestReward(409, 1, GOLD), // Gloves Mould (2)
            new MegaChestReward(410, 1, GOLD), // Boots Mould (2)
            new MegaChestReward(16420, 1, GOLD), // Mystic Staff (6)
            new MegaChestReward(17049, 1, GOLD), // Mystic Helm (6)
            new MegaChestReward(17050, 1, GOLD), // Mystic Body (6)
            new MegaChestReward(17051, 1, GOLD), // Mystic Legs (6)
            new MegaChestReward(10945, 60, GOLD), // $1 Bond
            new MegaChestReward(10945, 40, GOLD), // $1 Bond
            new MegaChestReward(10946, 6, GOLD), // $5 Bond
            new MegaChestReward(10946, 8, GOLD), // $5 Bond
            new MegaChestReward(17129, 3, GOLD), // Elemental Cache
            new MegaChestReward(791, 35, GOLD), // Raids Med Key
            new MegaChestReward(770, 50, GOLD), // Raids Key
            new MegaChestReward(2706, 5, GOLD), // Dream Ticket (u)
            new MegaChestReward(19062, 400, GOLD), // Monster Essence
            new MegaChestReward(19118, 2, GOLD), // Void Crate
            new MegaChestReward(2009, 2, GOLD), // Corrupt Crate
            new MegaChestReward(23058, 2, GOLD), // $25 Bond


            new MegaChestReward(20001, 1, DIAMOND), // Void Bow
            new MegaChestReward(20002, 1, DIAMOND), // Void Staff
            new MegaChestReward(20000, 1, DIAMOND), // Void Blade
            new MegaChestReward(15717, 1, DIAMOND), // Cape of Void
            new MegaChestReward(15671, 5, DIAMOND), // Bond Casket (u)
            new MegaChestReward(3578, 3, DIAMOND), // Owner's Cape Goodiebag
            new MegaChestReward(17125, 1, DIAMOND), // Cosmetic Ticket
            new MegaChestReward(754, 2, DIAMOND), // Owner Weapon Crate
            new MegaChestReward(4022, 2, DIAMOND), // Owner's Goodiebag (u)
            new MegaChestReward(23058, 4, DIAMOND), // $25 Bond
            new MegaChestReward(19118, 3, DIAMOND), // Void Crate
            new MegaChestReward(2624, 2, DIAMOND), // Elemental Cache (u)
            new MegaChestReward(10945, 75, DIAMOND), // $1 Bond
            new MegaChestReward(10946, 12, DIAMOND), // $5 Bond
            new MegaChestReward(23172, 2, DIAMOND), // Elite Weapon Crate
            new MegaChestReward(2703, 1, DIAMOND), // Tier 3 Membership
            new MegaChestReward(2009, 3, DIAMOND), // Corrupt Crate
            new MegaChestReward(23058, 3, DIAMOND), // $25 Bond


            new MegaChestReward(23059, 2, EXOTIC), // $100 Bond
            new MegaChestReward(460, 1, EXOTIC), // Bronze Crate
            new MegaChestReward(3509, 2, EXOTIC), // Poison Dye
            new MegaChestReward(3507, 2, EXOTIC), // Corrupt Dye
            new MegaChestReward(3508, 2, EXOTIC), // Icy Dye
            new MegaChestReward(19944, 1, EXOTIC), // Owner's cape
            new MegaChestReward(438, 1, EXOTIC), // Artifact Trinket
            new MegaChestReward(23058, 8, EXOTIC), // $25 Bond
            new MegaChestReward(3578, 3, EXOTIC), // Owner's Goodiebag
            new MegaChestReward(19118, 6, EXOTIC), // Void Crate
            new MegaChestReward(2009, 8, EXOTIC), // Corrupt Crate
            new MegaChestReward(4022, 4, EXOTIC), // Owner's Goodiebag (U)
};
}
