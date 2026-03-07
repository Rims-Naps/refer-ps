package com.ruse.world.content.collectionlogs;

import com.ruse.model.Item;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BoxesTracker;
import com.ruse.world.content.RewardHandler;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CollectionLogManager {

    private final Player player;
    private CollectionLogType type;
    private ArrayList<CollectionLogs> currentCollectionLogs;
    private ArrayList<CollectionLogs> logsViewing;
    private CollectionLogs logCurrentlyViewing;

    @Getter
    private ArrayList<LogProgress> logProgress;


    public CollectionLogManager(Player player) {
        this.player = player;
        this.type = CollectionLogType.MONSTERS;
        this.currentCollectionLogs = new ArrayList<>();
        this.logsViewing = new ArrayList<>();
        this.logProgress = new ArrayList<>();
        addLogs();
    }

    public void openInterface() {
        for (int i = 131052; i <= 131076; i += 2) {
            player.getPacketSender().sendString(i, "");
        }
        openBossLog();
        player.getPacketSender().sendInterface(131000);
    }

    public void createNewCurrentLogs() {
        currentCollectionLogs.clear();
        for (CollectionLogs b : CollectionLogs.values()) {
            currentCollectionLogs.add(b);
        }

    }


    public void addLogProgress(LogProgress log) {
        for (LogProgress progress : logProgress) {
            if (progress.name.equalsIgnoreCase(log.getName())) {
                    for (CollectionLogs b : CollectionLogs.values()) {
                        if (b.name().equalsIgnoreCase(log.getName())) {
                            for (Item item : log.getObtainedItems()) {
                                if (b.getUniqueItems().contains(item.getId()))
                                    progress.getObtainedItems().add(item);
                            }
                        }
                    }

                progress.setCompleted(log.getCompleted());
            }
        }
    }

    public void addLogs() {
        for (CollectionLogs b : CollectionLogs.values()) {
            logProgress.add(new LogProgress(b.name()));
        }
    }

    public ArrayList<Item> getObtainedItems(CollectionLogs log) {
        for (LogProgress progress : logProgress) {
            if (progress.name.equalsIgnoreCase(log.name())) {
                return progress.getObtainedItems();
            }
        }
        return new ArrayList<>();
    }

    public boolean isCompleted(CollectionLogs log) {
        for (LogProgress progress : logProgress) {
            if (progress.name.equalsIgnoreCase(log.name())) {
                if (progress.getObtainedItems().size() == log.getUniqueItems().size()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int totalCompleted() {
        int count = 0;
        for (LogProgress progress : logProgress) {
            if (progress.getObtainedItems().size() == CollectionLogs.valueOf(progress.getName()).getUniqueItems().size()) {
                count++;
            }
        }
        return count;
    }

    public int getOpenedForBoxes_Keys(String name) {
        switch (name) {
            case "WATER_CRATE":
                return BoxesTracker.getTotalBoxesOpenedForItem(10260, player);
            case "LAVA_CRATE":
                return BoxesTracker.getTotalBoxesOpenedForItem(10262, player);
            case "EARTH_CRATE":
                return BoxesTracker.getTotalBoxesOpenedForItem(10256, player);
            case "LOW_WEAPON_CRATE":
                return BoxesTracker.getTotalBoxesOpenedForItem(23171, player);
            case "HIGH_WEAPON_CRATE":
                return BoxesTracker.getTotalBoxesOpenedForItem(23172, player);
            case "ELEMENTAL_CACHE":
                return BoxesTracker.getTotalBoxesOpenedForItem(17129, player);
            case "NECRO_CRATE":
                return BoxesTracker.getTotalBoxesOpenedForItem(23173, player);
            case "PANDORAS_BOX":
                return BoxesTracker.getTotalBoxesOpenedForItem(17130, player);
            case "BOX_OF_TREASURES":
                return BoxesTracker.getTotalBoxesOpenedForItem(15668, player);
            case "BOX_OF_BLESSINGS":
                return BoxesTracker.getTotalBoxesOpenedForItem(15669, player);
            case "BOND_CASKET":
                return BoxesTracker.getTotalBoxesOpenedForItem(15670, player);
            case "BOND_CASKET_ENCHANTED":
                return BoxesTracker.getTotalBoxesOpenedForItem(15671, player);
            case "VOID_CRATE":
                return BoxesTracker.getTotalBoxesOpenedForItem(19118, player);
            case "BOX_OF_PLUNDERS":
                return BoxesTracker.getTotalBoxesOpenedForItem(15667, player);
            case "FROST_CRATE":
                return BoxesTracker.getTotalBoxesOpenedForItem(1453, player);
            case "ELEMENTAL_KEY_1":
                return BoxesTracker.getTotalBoxesOpenedForItem(20104, player);
            case "ELEMENTAL_KEY_2":
                return BoxesTracker.getTotalBoxesOpenedForItem(20105, player);
            case "ELEMENTAL_KEY_3":
                return BoxesTracker.getTotalBoxesOpenedForItem(20106, player);
            case "ELEMENTAL_KEY_4":
                return BoxesTracker.getTotalBoxesOpenedForItem(20107, player);
            case "ELEMENTAL_KEY_5":
                return BoxesTracker.getTotalBoxesOpenedForItem(20108, player);
            case "ELEMENTAL_KEY_6":
                return BoxesTracker.getTotalBoxesOpenedForItem(20109, player);
            case "ELEMENTAL_KEY_7":
                return BoxesTracker.getTotalBoxesOpenedForItem(20111, player);
            case "ELEMENTAL_KEY_8":
                return BoxesTracker.getTotalBoxesOpenedForItem(20112, player);
            case "CHRISTMAS_KEY_1":
                return BoxesTracker.getTotalBoxesOpenedForItem(1451, player);
            case "CRHSITMAS_KEY_2":
                return BoxesTracker.getTotalBoxesOpenedForItem(1452, player);
            default:
                return 0;
        }
    }

    public int get_minigame_counters(String string) {
        switch (string) {
            case "TOWER":
                return player.getTotal_tower_completions();
            case "SOULBANE":
                return player.getTotalSoulbaneRuns();
            case "CORRUPT_RAID":
                return player.getCorruptCompletions();
            case "CORRUPT_RAID_MEDIUM":
                return player.getCorruptMediumCompletions();
            case "CORRUPT_RAID_HARD":
                return player.getCorruptHardCompletions();
            case "CIRCLE_OF_ELEMENTS":
                return player.getCOECompletions();

            default:
                return 0;
        }
    }

    public void openBossLog() {
        if (currentCollectionLogs.size() <= 0) {
            createNewCurrentLogs();
        }

        player.getPacketSender().sendConfig(2388, type.ordinal());

        if (logCurrentlyViewing == null) {
            logCurrentlyViewing = currentCollectionLogs.get(0);
        }
        logsViewing.clear();
        int index = 131052;
        for (CollectionLogs b : CollectionLogs.values()) {
            if (b.getType() == type) {
                player.getPacketSender().sendString(index, (isCompleted(b) ? "@gre@" : "") + b.getName());
                logsViewing.add(b);
                index += 2;
            }
        }
        int length = logsViewing.size() * 17;
        if (length <= 214)
            length = 214;
        player.getPacketSender().setScrollBar(131050, length);

        player.getPacketSender().sendString(131006, "Total: @whi@(" + totalCompleted() + "/" + logProgress.size() + ")");
        player.getPacketSender().sendString(131007, logCurrentlyViewing.getName() != null ? logCurrentlyViewing.getName()
                : "None");

        for (CollectionLogs log : currentCollectionLogs) {
            if (log.getName().equals(logCurrentlyViewing.getName())) {
                double percent = 100 * getObtainedItems(log).size() / (log.getUniqueItems().size());
                player.getPacketSender().sendProgressBar(131008, 0, (int) percent, 0);
                player.getPacketSender().sendString(131009,
                        (type == CollectionLogType.BOXES ? "Opened: @whi@" + getOpenedForBoxes_Keys(log.name()) : type == CollectionLogType.MINIGAMES ? "Runs: @whi@" + get_minigame_counters(log.name()) : "Killed: @whi@" + Misc.insertCommasToNumber(log.getKillCount(player))));
                player.getPacketSender().sendString(131020, "@whi@" + getObtainedItems(log).size() + "/" + log.getUniqueItems().size());

                player.getPacketSender().sendItemsOnInterface(131022, log.getReward());

            }
        }

        int itemsLength = 180;

        int itemsScroll = 36 + 3 + (logCurrentlyViewing.getUniqueItems().size() / 6 * 36);
        if (itemsScroll <= 175)
            itemsScroll = 175;
        player.getPacketSender().setScrollBar(131250, itemsScroll);

        for (int i = 0; i < itemsLength; i++) {
            if (logCurrentlyViewing.getUniqueItems().size() > i) {
                int amount = 0;
                int itemId = logCurrentlyViewing.getUniqueItems().get(i);
                int count = 0;

                for (Item item : getObtainedItems(logCurrentlyViewing)) {
                    if (item.getId() == itemId) {
                        count += item.getAmount();
                        break;
                    }
                }

                if (count > 0) {
                    amount = count;
                }
                player.getPacketSender().sendItemOnInterface(131251 + i, new Item(itemId, amount));
            } else {
                player.getPacketSender().sendItemOnInterface(131251 + i, -1, 1);
            }
        }

    }


    public void finishCollectionLog(CollectionLogs collectionLogs) {
        claimReward(collectionLogs);
    }

    @Getter
    private List<String> claimedCollectionRewards = new ArrayList<>();

    public boolean claimReward(CollectionLogs collectionLogs) {
        if (!claimedCollectionRewards.contains(collectionLogs.name())) {
            if (isCompleted(collectionLogs)) {
                if (collectionLogs.isAnnounce()) {
                    World.sendMessage(player.getUsername() + " has just completed the @red@" + collectionLogs.getName() + " <col=f97100>Collection log! @red@(KC:"
                            + collectionLogs.getKillCount(player) + ")");
                } else {
                    World.sendMessage("@red@<shad=0> " + player.getUsername() + " <col=AF70C3>has just completed the @red@" + collectionLogs.getName() + " <col=AF70C3>Collection log!");
                }

                if (collectionLogs.getReward() != null) {
                    for (Item item : collectionLogs.getReward()) {
                        RewardHandler.handleItemReward(player, item);
                    }

                    claimedCollectionRewards.add(collectionLogs.name());
                }
            }
        }
        return false;
    }



    public void checkClaimedLogs() {
        for (CollectionLogs logs : CollectionLogs.values()) {
            claimReward(logs);
        }
    }

    public void incrementLog(CollectionLogs collectionLogs, LogProgress log, Item item, int amount) {
        boolean add = true;
        boolean complete = isCompleted(collectionLogs);
        log.setCompleted(log.getCompleted() + amount);

        if (!collectionLogs.getUniqueItems().contains(item.getId())) {
            return;
        }
        for (Item obtainedItems : log.getObtainedItems()) {
            if (obtainedItems.getId() == item.getId()) {
                obtainedItems.setAmount(obtainedItems.getAmount() + item.getAmount());
                add = false;
                break;
            }
        }
        if (add) {
            player.msgFancyPurp( item.getDefinition().getName() + " @red@A unique item has been added to your collection log!");
            log.getObtainedItems().add(item);
        }
        if (!complete && isCompleted(collectionLogs)) {
            finishCollectionLog(collectionLogs);
        }
    }

    public void addDrop(NPC npc, Item item) {
        CollectionLogs collectionLogs = CollectionLogs.forNpcId(npc.getId());
        if (collectionLogs != null) {
            for (LogProgress log : logProgress) {
                if (log.getName().equalsIgnoreCase(collectionLogs.name())) {
                    incrementLog(collectionLogs, log, item, 1);
                }
            }
        }
    }

    public void addItem(int itemID, Item item) {
        addItem(itemID, item, 1);
    }

    public void addItem(int itemID, Item item, int amount) {
        CollectionLogs collectionLogs = CollectionLogs.forItemId(itemID);
        if (collectionLogs != null) {
            for (LogProgress log : logProgress) {
                if (log.getName().equalsIgnoreCase(collectionLogs.name())) {
                    incrementLog(collectionLogs, log, item, amount);
                }
            }
        }
    }

    public void addItem(CollectionLogs collectionLogs, Item item) {
        addItem(collectionLogs, item, 1);
    }

    public void addItem(CollectionLogs collectionLogs, Item item, int amount) {
        if (collectionLogs != null) {
            for (LogProgress log : logProgress) {
                if (log.getName().equalsIgnoreCase(collectionLogs.name())) {
                    incrementLog(collectionLogs, log, item, amount);
                }
            }
        }
    }


    public boolean handleButton(final int buttonId) {
        if (buttonId >= 131051 && buttonId <= 131200) {
            int index = (buttonId - 131051) / 2;
            if (index < logsViewing.size()) {
                logCurrentlyViewing = logsViewing.get(index);
                openInterface();
            }
            return true;
        } else if (buttonId >= 131010 && buttonId <= 131018) {
            int index = (buttonId - 131010) / 2;
            if (index < CollectionLogType.values().length) {
                for (int i = 131052; i <= 131076; i += 2) {
                    player.getPacketSender().sendString(i, "");
                }
                type = CollectionLogType.values()[index];
                openInterface();
                logCurrentlyViewing = logsViewing.get(0);
                player.getPacketSender().sendConfig(2451, 0);

                openInterface();
            }
            return true;
        }
        return false;
    }

    @Getter
    @Setter
    public class LogProgress {
        private String name;
        private int completed;
        private ArrayList<Item> obtainedItems;

        public LogProgress(String name) {
            this.name = name;
            this.obtainedItems = new ArrayList<>();
        }
    }

}

