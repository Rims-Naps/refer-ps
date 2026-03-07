package com.ruse.world.content.casketopening;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BoxesTracker;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.casketopening.impl.*;
//import com.ruse.world.content.christmas.HolidayManager;
import com.ruse.world.content.holidays.HolidayManager;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CasketOpening {

    private final Player player;
    private final int INTERFACE_ID = 108000;
    private boolean canCasketOpening = true;
    private Box SlotPrize;
    private Caskets currentCasket;

    public CasketOpening(Player player) {
        this.player = player;
    }


    public static Box getLoot(Box[] loot) {
        HashMap<Double, ArrayList<Box>> dropRates = new HashMap<>();
        ArrayList<Box> potentialDrops = new ArrayList<>();

        for (Box drop : loot) {
            if (drop == null)
                continue;
            double divisor = drop.getRate();
            if (!dropRates.containsKey(divisor)) {
                ArrayList<Box> items = new ArrayList<>();
                items.add(drop);
                dropRates.put(divisor, items);
            } else {
                dropRates.get(divisor).add(drop);
            }
        }
        for (double dropRate : dropRates.keySet()) {
            double rate = dropRate * 1000;
            if (Misc.getRandom(100000) <= rate) {
                potentialDrops.add(dropRates.get(dropRate).get(Misc.getRandom(dropRates.get(dropRate).size() - 1)));
            }
        }

        if (!potentialDrops.isEmpty()) {
            return potentialDrops.get(Misc.getRandom((potentialDrops.size() - 1)));
        } else {
            return loot[Misc.getRandom(1)];
        }
    }

    public static Box getLoot1(Box[] loot) {
        HashMap<Double, ArrayList<Box>> dropRates = new HashMap<>();
        ArrayList<Box> potentialDrops = new ArrayList<>();

        for (Box drop : loot) {
            if (drop == null)
                continue;
            double divisor = drop.getRate();
            if (!dropRates.containsKey(divisor)) {
                ArrayList<Box> items = new ArrayList<>();
                items.add(drop);
                dropRates.put(divisor, items);
            } else {
                dropRates.get(divisor).add(drop);
            }
        }
        for (double dropRate : dropRates.keySet()) {
            double rate = dropRate * 1000;
            if (Misc.getRandom(100000) <= rate) {
                potentialDrops.add(dropRates.get(dropRate).get(Misc.getRandom(dropRates.get(dropRate).size() - 1)));
            }
        }

        if (!potentialDrops.isEmpty()) {
            return potentialDrops.get(Misc.getRandom((potentialDrops.size() - 1)));
        } else {
            return loot[Misc.getRandom(loot.length - 1)];
        }
    }

    public boolean hasItems() {
        if (!player.getInventory().contains(getCurrentCasket().getItemID())) {
            player.sendMessage("You need a " + ItemDefinition.forId(getCurrentCasket().getItemID()).getName() + " to do this.");
            return false;
        }
        return true;
    }

    public boolean removeItems() {
        if (getCurrentCasket() == Caskets.CHARONS_LANTERN) {
            return false;
        }
        if (player.getInventory().getAmount(getCurrentCasket().getItemID()) >= 1) {
            player.getInventory().delete(getCurrentCasket().getItemID(), 1);
        }
        return false;
    }

    public void spin() {
        if (getCurrentCasket() == null) {
            return;
        }
        if (getCurrentCasket() == Caskets.CHARONS_LANTERN && player.getSouls() < 5000) {
            player.sendMessage("You need 5000 souls to harvest!");
            return;
        }
        if (hasItems()) {
            if (player.getInventory().getFreeSlots() == 0) {
                player.getPacketSender().sendMessage("You don't have enough free inventory space.");
                return;
            }
            if (getCurrentCasket() == Caskets.CHARONS_LANTERN && player.getSouls() >= 5000) {
                player.setSouls(player.getSouls() - 5000);
                player.sendMessage("You harvest your lantern, you now have " + player.getSouls() + " souls remaining!");
            }
            if (!canCasketOpening) {
                player.sendMessage("Please finish your current spin.");
                return;
            }
            removeItems();
            player.setSpinning(true);
            player.getMovementQueue().setLockMovement(true);
            player.sendMessage(":resetnewmapss");
            player.sendMessage(":loadnewmapss");
            process();
        }
    }

    public void quickSpin() {
        if (getCurrentCasket() == null) {
            return;
        }
        if (!canCasketOpening) {
            player.sendMessage("Please finish your current spin.");
            return;
        }
        if (getCurrentCasket() == Caskets.CHARONS_LANTERN && player.getSouls() < 5000) {
            player.sendMessage("You need 5000 souls to harvest!");
            return;
        }
        if (hasItems()) {
            if (player.getInventory().getFreeSlots() == 0) {
                player.getPacketSender().sendMessage("You don't have enough free inventory space.");
                return;
            }
            if (getCurrentCasket() == Caskets.CHARONS_LANTERN && player.getSouls() >= 5000) {
                player.setSouls(player.getSouls() - 5000);
                player.sendMessage("You harvest your lantern, you now have " + player.getSouls() + " souls remaining!");
            }
            if (!canCasketOpening) {
                player.sendMessage("Please finish your current spin.");
                return;
            }
            removeItems();
            player.sendMessage(":resetnewmapss");
            processQuick();
        }
    }
    public void openAll(int amount) {
        if (getCurrentCasket() == null) {
            return;
        }
        if (!canCasketOpening) {
            player.sendMessage("Please finish your current spin.");
            return;
        }
        // Cap the loop at 100 iterations
        int cappedAmount = Math.min(amount, 100);
        for (int i = 0; i < cappedAmount; i++) {
            if (hasItems()) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.getPacketSender().sendMessage("You don't have enough free inventory space.");
                    player.getInventory().refreshItems();
                    return;
                }
                if (getCurrentCasket() == Caskets.CHARONS_LANTERN && player.getSouls() >= 5000) {
                    player.setSouls(player.getSouls() - 5000);
                    player.sendMessage("You harvest your lantern, you now have " + player.getSouls() + " souls remaining!");
                }
                if (!canCasketOpening) {
                    player.sendMessage("Please finish your current spin.");
                    return;
                }
                removeItems();
                Box[] loot = getCurrentCasket().getLoot();
                SlotPrize = getLoot1(loot);
                reward(SlotPrize.isAnnounce());
            } else {
                player.getInventory().refreshItems();
                return;
            }
        }
        player.getInventory().refreshItems();
    }






    public void process() {
        SlotPrize = null;
        canCasketOpening = false;
        Box[] loot = getCurrentCasket().getLoot();
        SlotPrize = getLoot1(loot);
        if (SlotPrize.getRate() < 10D && Misc.getRandom(1) == 0) {
            SlotPrize = getLoot1(loot);
        }
        if (SlotPrize.getRate() < 10D && Misc.getRandom(2) == 0) {
            SlotPrize = getLoot1(loot);
        }

        player.getDailyTaskInterface().MiscTasksCompleted(5, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_10_BOXES, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_50_BOXES, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_100_BOXES, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_250_BOXES, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_500_BOXES, 1);
        player.getBoxTracker().add(new BoxesTracker.BoxEntry(player.getCasketOpening().getCurrentCasket().getItemID(), 1));
        player.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(player.getCasketOpening().getCurrentCasket().getItemID(), player) + " " + ItemDefinition.forId(player.getCasketOpening().getCurrentCasket().getItemID()).getName());
        player.setTotalBoxesOpened(player.getTotalBoxesOpened() + 1);

      //  if (Objects.equals(ItemDefinition.forId(getCurrentCasket().getItemID()).getName(), "Egg Crate")) {
      //      HolidayManager.increaseMeter(player,.05);
       // }


        boolean announce = SlotPrize.isAnnounce();

        for (int i = 0; i < 28; i++) {
            Box NOT_PRIZE = getLoot1(loot);
            if (NOT_PRIZE.getRate() > 10 && Misc.getRandom(2) == 0) {
                NOT_PRIZE = getLoot1(loot);
            }
            sendItem(i, 23, SlotPrize.getId(), SlotPrize.getMax(), NOT_PRIZE.getId(), NOT_PRIZE.getMax(), 108501);
        }

        boolean announceLoot = announce;
        TaskManager.submit(new Task(7, player, false) {

            @Override
            public void execute() {
                reward(announceLoot);
                player.setSpinning(false);
                player.getMovementQueue().setLockMovement(false);
                stop();
            }
        });
    }

    public void processQuick() {
        SlotPrize = null;
        canCasketOpening = false;
        Box[] loot = getCurrentCasket().getLoot();
        SlotPrize = getLoot1(loot);
        if (SlotPrize.getRate() < 10D && Misc.getRandom(1) == 0) {
            SlotPrize = getLoot1(loot);
        }
        if (SlotPrize.getRate() < 10D && Misc.getRandom(2) == 0) {
            SlotPrize = getLoot1(loot);
        }

        if (Objects.equals(ItemDefinition.forId(getCurrentCasket().getItemID()).getName(), "Frost Crate")) {
           // HolidayManager.increaseMeter(player,.05);
        }

        boolean announce = SlotPrize.isAnnounce();

        player.getDailyTaskInterface().MiscTasksCompleted(5, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_10_BOXES, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_50_BOXES, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_100_BOXES, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_250_BOXES, 1);
        Achievements.doProgress(player, Achievements.Achievement.OPEN_500_BOXES, 1);
        player.getBoxTracker().add(new BoxesTracker.BoxEntry(player.getCasketOpening().getCurrentCasket().getItemID(), 1));
        player.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(player.getCasketOpening().getCurrentCasket().getItemID(), player) + " " + ItemDefinition.forId(player.getCasketOpening().getCurrentCasket().getItemID()).getName());
        player.setTotalBoxesOpened(player.getTotalBoxesOpened() + 1);



        for (int i = 0; i < 7; i++) {
            Box NOT_PRIZE = getLoot1(loot);
            if (NOT_PRIZE.getRate() > 10 && Misc.getRandom(2) == 0) {
                NOT_PRIZE = getLoot1(loot);
            }
            sendItem(i, 3, SlotPrize.getId(), SlotPrize.getMax(), NOT_PRIZE.getId(), NOT_PRIZE.getMax(), 108501);
        }
        // player.getBank(0).add(new Item(SlotPrize.getId(), SlotPrize.getMax()), false);
        //   canCasketOpening = true;

        reward(announce);
        player.setSpinning(false);
    }

    public void sendItem(int i, int prizeSlot, int PRIZE_ID, int prizeamount, int NOT_PRIZE, int amount, int ITEM_FRAME) {
        if (i == prizeSlot) {
            player.sendMessage("newmapsopening##" + ITEM_FRAME + "##" + PRIZE_ID + "##" + prizeamount + "##" + i + "##");
        } else {
            player.sendMessage("newmapsopening##" + ITEM_FRAME + "##" + NOT_PRIZE + "##" + amount + "##" + i + "##");
        }
    }

    public void resetInterface() {
        for (int i = 0; i < 7; i++) {
            sendItem(i, 3, -1, 0, -1, 0, 108501);
        }
    }

    public void reward(boolean announce) {
        if (SlotPrize == null) {
            return;
        }

        HashMap<String, Integer> tracker = player.getTrackers();
        String name = ItemDefinition.forId(getCurrentCasket().itemID).getName();
        if (tracker.containsKey(name)) {
            tracker.put(name, tracker.get(name) + 1);
        } else {
            tracker.put(name, 1);
        }


        player.getInventory().add(SlotPrize.getId(), SlotPrize.getMax());
        player.sendMessage("<col=AF70C3><shad=0>You won x" + SlotPrize.getMax() + " " + ItemDefinition.forId(SlotPrize.getId()).getName());
        //System.out.println("Name: " + name);
        player.getCollectionLogManager().addItem(getCurrentCasket().getItemID(), new Item(SlotPrize.getId(), SlotPrize.getMax()));
        if (announce) {

            if (!Objects.equals(ItemDefinition.forId(getCurrentCasket().getItemID()).getName(), "Bag Of Coins")) {

                World.sendMessage("<col=AF70C3><shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>just received <col=AF70C3><shad=0>"
                        + (SlotPrize.getMax() > 1 ? "x" + SlotPrize.getMax() : "") + " "
                        + ItemDefinition.forId(SlotPrize.getId()).getName() + "<col=AF70C3><shad=0> from a @red@" +
                        ItemDefinition.forId(getCurrentCasket().getItemID()).getName() + "<col=AF70C3><shad=0>!");
            }
        }

        canCasketOpening = true;
    }

    public void openInterface() {
        player.sendMessage(":resetnewmapss");
        player.getPacketSender().sendItemOnInterface(108009, 13759, 1);
        player.getPacketSender().sendItemOnInterface(108010, 13758, 1);
        resetInterface();
        Box[] loot = getCurrentCasket().getLoot();

        int length = loot.length;
        if (length >= 160)
            length = 160;
        if (length <= 16)
            length = 16;

        length += 8 - (length % 8);

        for (int i = 0; i < length; i++) {
            if (loot.length > i)
                player.getPacketSender().sendItemOnInterface(108101 + i, loot[i].getId(), loot[i].getMax());
            else
                player.getPacketSender().sendItemOnInterface(108101 + i, -1, 0);
        }

        for (int i = 0; i < length; i++) {
            if (loot.length > i)
                player.getPacketSender().sendString(108261 + i, "1/" + getRate(loot[i].getRate()));
            else
                player.getPacketSender().sendString(108261 + i, "");
        }
        int scroll = 9 + ((loot.length / 8) + 1) * 55;
        if (scroll <= 165)
            scroll = 165;
        player.getPacketSender().setScrollBar(108100, scroll);


        player.getPA().sendInterface(INTERFACE_ID);
    }

    public int getRate(double rate) {
        int result = (int) (100 / rate);
        return result;
    }


    public Caskets getCurrentCasket() {
        return currentCasket;
    }

    public void setCurrentCasket(Caskets currentCasket) {
        this.currentCasket = currentCasket;
    }

    public enum Caskets {
        EARTH_CRATE(10256, EarthCrate.loot),
        WATER_CRATE(10260, WaterCrate.loot),
        FIRE_CRATE(10262, FireCrate.loot),
        LOVERS_CRATE(2622, LoversCrate.loot),


        ELEMENTAL_CACHE(17129, ElementalCache.loot),
        ELEMENTAL_CACHE_U(2624, ElementalCacheU.loot),
        CHARONS_LANTERN(7053, CharonsLantern.loot),
        PANDORAS_BOX(17130, PandoraBox.loot),

        NECROMANCY_CRATE(23173, NecroCrate.loot),

        FROST_CRATE(1453, FrostCrate.loot),

        LOW_TIER_WEAPON_CRATE(23171, LowTierWeaponCrate.loot),
        HIGH_TIER_WEAPON_CRATE(23172, HighTierWeaponCrate.loot),

        BOND_CASKET(15670, BondCasket.loot),
        VOID_CRATE(19118, VoidCrate.loot),
        SPECTRAL_CACHE(2090, SpectralCache.loot),

        BOND_CASKET_ENCHANTED(15671, BondCasketEnchanted.loot),
        BOX_OF_TREASURES(15668, BoxOfTreasures.loot),
        BOX_OF_BLESSINGS(15669, BoxOfBlessings.loot),
        BOX_OF_PLUNDERS(15667, BoxOfPlunders.loot),
        NOVICE_WEAPON_CRATE(15666, NoviceWeaponCrate.loot),

        SLAYER_CASKET(1306, SlayerCasket.loot),
        SLAYER_CASKET_U(1307, SlayerCasketU.loot),

        BEAST_CASKET(300, BeastCasket.loot),
        BEAST_CASKET_U(301, BeastCasketU.loot),

        AFK_BOX(23171, WaterCrate.loot),

        CORRUPT_CRATE(2009, CorruptCrate.loot),
        ENCHANTED_CRATE(17819, EnchantedCrate.loot),

        EGG_CRATE(720, EggCrate.loot),
        VOTE_CRATE(731, VoteBox.loot),

        OWNER_WEP_CRATE(754, OwnerWepCrate.loot),

        ;
        private int itemID;
        private Box[] loot;

        Caskets(int itemID, Box[] loot) {
            this.itemID = itemID;
            this.loot = loot;
        }

        public int getItemID() {
            return itemID;
        }

        public Box[] getLoot() {
            return loot;
        }

    }

}
