
package com.ruse.world.content.ascension;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@Getter
@Setter
public class TierUpgrading {

    private boolean viewing;
    private final static int PROTECTION_GEM_ID = 18979;
    private final Player p;
    private int selecteditem;
    private int protectionGemAmount;

    public void display() {
        reset();
        updateInterface();
        viewing = true;
        confirmedLowProtection = false;
        p.getPacketSender().sendInterfaceSet(26291, 3321);
        p.getPacketSender().sendItemContainer(p.getInventory(), 3322);
    }

    public boolean handleButton(int id) {
        switch (id) {
            case 26301:
                upgrade();
                return true;
        }
        return false;
    }

    private boolean upgrading = false;

    private boolean confirmedLowProtection = false;


    public void upgrade() {
        if (upgrading) {
            p.sendMessage("<shad=0>@blu@Please wait a few seconds between upgrades.");
            return;
        }
        if (selecteditem == -1) {
            p.sendMessage("<shad=0>@blu@Please select an item from your inventory to upgrade!");
            return;
        }


        TierUpgradingData data = TierUpgradingData.forItemId(selecteditem);
        if (data == null) {
            return;
        }

        if (data.getRequiredItems() != null && data.getRequiredItems().length > 0) {
            for (Item item : data.getRequiredItems()) {

                if (item.getId() == 995) {
                    if (!p.getInventory().contains(995, item.getAmount())) {
                        p.sendMessage("<shad=0>@red@You don't have enough coins to attempt this upgrade!");
                        return;
                    }
                }

                if (item.getId() != 19062 && item.getId() != 11054 && item.getId() != 11056 && item.getId() != 11052 && item.getId() != 3576 && item.getId() != 6466 && item.getId() != 3502 && item.getId() != 2064) {
                    if (!p.getInventory().contains(item.getId())) {
                        p.sendMessage("<shad=0>@red@Your missing a required item to attempt this upgrade!");
                        return;
                    }
                }

                if (item.getId() == 19062) {
                    if (p.getMonsteressence() < item.getAmount()) {
                        p.sendMessage("<shad=0>@red@You don't have enough monster essence in your pouch!");
                        return;
                    }
                }
                if (item.getId() == 11054) {
                    if (p.getFireessence() < item.getAmount()) {
                        p.sendMessage("<shad=0>@red@You don't have enough Fire essence in your pouch!");
                        return;
                    }
                }
                if (item.getId() == 11056) {
                    if (p.getWateressence() < item.getAmount()) {
                        p.sendMessage("<shad=0>@red@You don't have enough Water essence in your pouch!");
                        return;
                    }
                }
                if (item.getId() == 11052) {
                    if (p.getEarthessence() < item.getAmount()) {
                        p.sendMessage("<shad=0>@red@You don't have enough Earth essence in your pouch!");
                        return;
                    }
                }
                if (item.getId() == 3576) {
                    if (p.getSlayeressence() < item.getAmount()) {
                        p.sendMessage("<shad=0>@red@You don't have enough Slayer essence in your pouch!");
                        return;
                    }
                }
                if (item.getId() == 6466) {
                    if (p.getBeastEssence() < item.getAmount()) {
                        p.sendMessage("<shad=0>@red@You don't have enough Beast essence in your pouch!");
                        return;
                    }
                }
                if (item.getId() == 3502) {
                    if (p.getCorruptEssence() < item.getAmount()) {
                        p.sendMessage("<shad=0>@red@You don't have enough Corrupt essence in your pouch!");
                        return;
                    }
                }
                if (item.getId() == 2064) {
                    if (p.getSpectralEssence() < item.getAmount()) {
                        p.sendMessage("<shad=0>@red@You don't have enough Spectral essence in your pouch!");
                        return;
                    }
                }
            }
        }


        if (p.getInventory().getAmount(PROTECTION_GEM_ID) < protectionGemAmount) {
            p.sendMessage("<shad=0>@red@You don't have this amount of protection gems in your inventory!");
            protectionGemAmount = p.getInventory().getAmount(protectionGemAmount);
            updateInterface();
            return;
        }

        if (!p.getInventory().contains(selecteditem)) {
            p.sendMessage("<shad=0>@red@Your inventory does not contain the selected item!");
            reset();
            updateInterface();
            return;
        }

        if (protectionGemAmount < 10 && !confirmedLowProtection){
            confirmedLowProtection = true;
            p.sendMessage("<shad=0><col=AF70C3>Are you sure you want to upgrade with @red@" + protectionGemAmount*10 + "@red@% <shad=0><col=AF70C3>protection chance?");
            p.msgRed("<shad=0><col=AF70C3>Press upgrade again to continue!");
            return;
        }

        upgrading = true;
        p.sendMessage("<shad=0><col=AF70C3>Attemping Upgrade");

        if (data.getRequiredItems() != null && data.getRequiredItems().length > 0) {
            for (Item item : data.getRequiredItems()) {
                if (item.getId() == 19062) { // MONSTER ESSENCE REMOVAL
                    p.setMonsteressence(p.getMonsteressence() - item.getAmount());
                }
                if (item.getId() == 11054) { // FIRE ESSENCE REMOVAL
                    p.setFireessence(p.getFireessence() - item.getAmount());
                }
                if (item.getId() == 11056) { // WATER ESSENCE REMOVAL
                    p.setWateressence(p.getWateressence() - item.getAmount());
                }
                if (item.getId() == 11052) { // EARTH ESSENCE REMOVAL
                    p.setEarthessence(p.getEarthessence() - item.getAmount());
                }
                if (item.getId() == 3576) { // SLAYER ESSENCE REMOVAL
                    p.setSlayeressence(p.getSlayeressence() - item.getAmount());
                }
                if (item.getId() == 6466) { // BEAST ESSENCE REMOVAL
                    p.setBeastEssence(p.getBeastEssence() - item.getAmount());
                }
                if (item.getId() == 3502) { // CORRUPT ESSENCE REMOVAL
                    p.setCorruptEssence(p.getCorruptEssence() - item.getAmount());
                }
                if (item.getId() == 2064) { // SPECTRAL ESSENCE REMOVAL
                    p.setSpectralEssence(p.getSpectralEssence() - item.getAmount());
                }
                if (item.getId() == 995) { // COINS REMOVAL
                        p.getInventory().delete(995, item.getAmount());
                } else {
                    p.getInventory().delete(item);
                }
            }
        }
        confirmedLowProtection = false;
        p.getPacketSender().sendItemContainer(p.getInventory(), 3322);
        success = Misc.random(100) < data.getSuccessChance();
        p.getPA().sendBarFill(26305, success);
        p.getInventory().delete(PROTECTION_GEM_ID, protectionGemAmount);
        p.getInventory().delete(selecteditem, 1);
    }
    private boolean success = false;

    private boolean receivedRewards = false;

    public void handleEnd() {
        if (!upgrading) {
            return;
        }

        if (receivedRewards) {
            return;
        }
        confirmedLowProtection = false;

        TierUpgradingData data = TierUpgradingData.forItemId(selecteditem);
        if (data == null) {
            p.sendMessage("An error has occurred, please report this to Loyal (#NFJDIS).");
            PlayerLogs.log(p.getUsername(), "Produced an error when upgrading. selecteditem=" + selecteditem + ", success=" + success);
            upgrading = false;
            success = false;
            confirmedLowProtection = false;
            reset();
            updateInterface();
            return;
        }
        if (success) {
            p.sendMessage("<shad=0>@gre@You have successfully upgraded your " + ItemDefinition.forId(selecteditem).getName().toLowerCase() + " to tier " + (data.getTier() + 1) + "!");
            World.sendMessage( ("<shad=0><col=AF70C3>@red@ " + p.getUsername()) + " <shad=0><col=AF70C3>Just upgraded their <shad=0><col=AF70C3>@red@" + ItemDefinition.forId(selecteditem).getName().toLowerCase() + " <col=AF70C3>to tier <shad=0>@red@" + (data.getTier() + 1) + "<shad=0><col=AF70C3>!");
            Achievements.doProgress(p, Achievements.Achievement.UPGRADE_2_ITEMS, 1);
            Achievements.doProgress(p, Achievements.Achievement.UPGRADE_5_ITEMS, 1);
            Achievements.doProgress(p, Achievements.Achievement.UPGRADE_10_ITEMS, 1);
            Achievements.doProgress(p, Achievements.Achievement.UPGRADE_25_ITEMS, 1);
            Achievements.doProgress(p, Achievements.Achievement.UPGRADE_50_ITEMS, 1);
            Achievements.doProgress(p, Achievements.Achievement.UPGRADE_100_ITEMS, 1);
            Achievements.doProgress(p, Achievements.Achievement.UPGRADE_250_ITEMS, 1);
            Achievements.doProgress(p, Achievements.Achievement.UPGRADE_500_ITEMS, 1);
            p.giveItem(data.getNextItem(), 1);
            p.getPacketSender().sendItemContainer(p.getInventory(), 3322);

        } else {
            int protectionChance = protectionGemAmount*10;
            boolean actuallyProtect = false;
            int newTier;

            if (Misc.random(99) < protectionChance) {
                newTier = data.getTier();
                actuallyProtect = true;
            } else {
                newTier = data.getTier() - 1;
                actuallyProtect = false;
            }

            TierUpgradingData previousData = TierUpgradingData.forTier(data.getType(), newTier);
            p.giveItem(previousData.getItemId(), 1);
            p.getPacketSender().sendItemContainer(p.getInventory(), 3322);
            p.sendMessage("<shad=0>@red@Oh no! You seem to have failed your upgrade..");
            World.sendMessage(("<col=AF70C3><shad=0>@red@ " + p.getUsername()) + " <shad=0><col=AF70C3>Failed to upgrade their <shad=0><col=AF70C3>@red@" + ItemDefinition.forId(selecteditem).getName().toLowerCase() + "<shad=0><col=AF70C3>!");

            if (actuallyProtect) {
                p.sendMessage("<shad=0>@gre@Your protection gems kept your item at the same tier.");
            } else {
                p.sendMessage("<shad=0>@red@Your Protections Gems were not powerful enough, your item has been down graded.");
            }
        }

        receivedRewards = true;
        TaskManager.submit(new Task(5, false) {
            @Override
            protected void execute() {
                upgrading = false;
                success = false;
                receivedRewards = false;
                confirmedLowProtection = false;
                p.getPA().sendBarReset(26305);
                reset();
                updateInterface();
                stop();
            }
        });
    }

    private void reset() {
        if (upgrading)
            return;
        selecteditem = -1;
        protectionGemAmount = 0;
    }

    public boolean handleItemClick(int id) {
        if (upgrading)
            return false;
        TierUpgradingData data = TierUpgradingData.forItemId(id);
        if (data != null) {
            selecteditem = id;
            protectionGemAmount = 0;
            updateInterface();
            return true;
        }
        switch (id) {
            case PROTECTION_GEM_ID:
                if (selecteditem == -1) {
                    p.sendMessage("<shad=0>@red@Select an item first!");
                } else {
                    if (protectionGemAmount == 10)
                        p.sendMessage("<shad=0>@blu@You already have 100% protection.");
                    else {
                        if (p.getInventory().getAmount(PROTECTION_GEM_ID) == protectionGemAmount) {
                            p.sendMessage("<shad=0>@blu@You need to have protection gems in your inventory!");
                        } else {
                            protectionGemAmount++;
                            p.sendMessage("<shad=0>@blu@Your chance to save your items TIER on failure is " + (protectionGemAmount * 10) + "%.");
                        }
                        updateInterface();
                    }
                }
                return true;
        }
        return false;
    }

    public void updateInterface() {
        TierUpgradingData data = TierUpgradingData.forItemId(selecteditem);

        if (data != null) {
            p.getPA().sendItemOnInterface(26295, data.getNextItem(), 1);
            p.getPA().sendItemOnInterface(26296, data.getItemId(), 1);

            if (data.itemId == 22003) {
                p.msgRed("100k Coins will be used on your Helmet upgrade attempt!");
            }

            if (data.itemId == 12466) {
                p.msgRed("250k Coins will be used on your Helmet upgrade attempt!");
            }

            if (protectionGemAmount > 0)
                p.getPA().sendItemOnInterface(26298, PROTECTION_GEM_ID, protectionGemAmount);
            else
                p.getPA().resetItemsOnInterface(26298, 1);

            if (data.getRequiredItems() != null && data.getRequiredItems().length > 0) {
                CopyOnWriteArrayList<Item> arrayList = new CopyOnWriteArrayList<>(Arrays.asList(data.getRequiredItems()));

                if (!arrayList.isEmpty()) {
                    Item firstRequiredItem = arrayList.remove(0);
                    p.getPA().sendItemOnInterface(26297, firstRequiredItem.getId(), firstRequiredItem.getAmount());
                }
                p.getPA().sendInterfaceItems(26300, arrayList);
            } else {
                p.getPA().resetItemsOnInterface(26300, 10);
            }
            p.getPA().sendString(26294, "Success: " + data.getSuccessChance() + "%");
            p.getPA().sendString(26307, "Protection: @yel@" +protectionGemAmount*10 + "%");

        } else {
            p.getPA().resetItemsOnInterface(26295, 1);
            p.getPA().resetItemsOnInterface(26296, 1);
            p.getPA().resetItemsOnInterface(26297, 1);
            p.getPA().resetItemsOnInterface(26298, 1);
            p.getPA().resetItemsOnInterface(26300, 10);
            p.getPA().sendString(26294, "Select Item!");
        }
        p.getPA().sendItemOnInterface(26296, selecteditem, 1);
    }

    @Getter
    private enum TierUpgradingData {

        EARTH_SLAYER_HELM_1(22002, 1, TierUpgradeType.EARTHSLAYER,  12464, 100 , new Item(3576, 3000), new Item(11052, 75) , new Item(19062, 25), new Item(995, 10000)),
        EARTH_SLAYER_HELM_2(12464, 2, TierUpgradeType.EARTHSLAYER, 12465, 95, new Item(3576, 7500), new Item(11052, 125) , new Item(19062, 50), new Item(995, 25000)),

        FIRE_SLAYER_HELM_1(22000, 1, TierUpgradeType.FIRESLAYER,12460, 100, new Item(3576, 3000),  new Item(11054, 75) , new Item(19062, 25), new Item(995, 10000)),
        FIRE_SLAYER_HELM_2(12460, 2, TierUpgradeType.FIRESLAYER,12461, 95, new Item(3576, 7500),  new Item(11054, 125) , new Item(19062, 50), new Item(995, 25000)),

        WATER_SLAYER_HELM1(22001, 1, TierUpgradeType.WATERSLAYER ,12462, 100, new Item(3576, 3000),  new Item(11056, 75) , new Item(19062, 25), new Item(995, 10000)),
        WATER_SLAYER_HELM2(12462, 2, TierUpgradeType.WATERSLAYER ,12463, 95, new Item(3576, 7500),  new Item(11056, 125) , new Item(19062, 50), new Item(995, 25000)),

        VOID_SLAYER_HELM_1(22003, 1, TierUpgradeType.VOIDSLAYER ,12466, 90, new Item(3576, 7500),new Item(19062, 250), new Item(11054, 75) , new Item(11052, 75) , new Item(11056, 75), new Item(995, 100000)),
        VOID_SLAYER_HELM_2(12466, 2, TierUpgradeType.VOIDSLAYER ,12467, 85, new Item(3576, 15000),new Item(19062, 350) , new Item(11054, 125) , new Item(11052, 125) , new Item(11056, 125), new Item(995, 250000)),

        CORRUPT_HELMET_1(2677, 1, TierUpgradeType.CORRUPT_HELMET , 2678, 85, new Item(3502, 10000),  new Item(6466, 15000), new Item(3576, 25000)),
        CORRUPT_HELMET_2(2678, 2, TierUpgradeType.CORRUPT_HELMET , 2679, 80, new Item(3502, 15000),  new Item(6466, 15000), new Item(3576, 25000)),


       SPECTRAL_HELMET_1(3013, 1, TierUpgradeType.CORRUPT_HELMET , 3014, 85, new Item(2064, 12000),  new Item(6466, 15000), new Item(3576, 25000)),
       SPECTRAL_HELMET_2(3014, 2, TierUpgradeType.CORRUPT_HELMET , 3015, 80, new Item(2064, 17000),  new Item(6466, 15000), new Item(3576, 25000)),


        MELEE_TOTEM_1(16517, 1, TierUpgradeType.MELEE_TOTEM , 16518, 80,  new Item(19062, 125), new Item(995, 15000)),
        MELEE_TOTEM_2(16518, 2, TierUpgradeType.MELEE_TOTEM , 16519, 70,  new Item(19062, 250), new Item(995, 25000)),

        RANGE_TOTEM_1(16520, 1, TierUpgradeType.RANGE_TOTEM , 16508, 80,  new Item(19062, 125), new Item(995, 15000)),
        RANGE_TOTEM_2(16508, 2, TierUpgradeType.RANGE_TOTEM , 16509, 70,  new Item(19062, 250), new Item(995, 25000)),

        MAGIC_TOTEM_1(16510, 1, TierUpgradeType.MAGIC_TOTEM , 16511, 80,  new Item(19062, 125), new Item(995, 15000)),
        MAGIC_TOTEM_2(16511, 2, TierUpgradeType.MAGIC_TOTEM , 16512, 70,  new Item(19062, 250), new Item(995, 25000)),

        MASTER_TOTEM_1(16513, 1, TierUpgradeType.MASTER_TOTEM , 16514, 75,  new Item(19062, 500), new Item(995, 50000)),
        MASTER_TOTEM_2(16514, 2, TierUpgradeType.MASTER_TOTEM , 16515, 70,  new Item(19062, 750), new Item(995, 75000)),


        GAIA_CRYSTAL_1(12839, 1, TierUpgradeType.GAIA_CRYSTAL , 17003, 70,  new Item(19062, 150), new Item(995, 15000)),
        GAIA_CRYSTAL_2(17003, 2, TierUpgradeType.GAIA_CRYSTAL , 17004, 65,  new Item(19062, 300), new Item(995, 25000)),

        LAVA_CRYSTAL_1(12840, 1, TierUpgradeType.LAVA_CRYSTAL , 17005, 70,  new Item(19062, 150), new Item(995, 15000)),
        LAVA_CRYSTAL_2(17005, 2, TierUpgradeType.LAVA_CRYSTAL , 17006, 65,  new Item(19062, 300), new Item(995, 25000)),

        AQUA_CRYSTAL_1(12841, 1, TierUpgradeType.AQUA_CRYSTAL , 17007, 70,  new Item(19062, 150), new Item(995, 15000)),
        AQUA_CRYSTAL_2(17007, 2, TierUpgradeType.AQUA_CRYSTAL , 17008, 65,  new Item(19062, 300), new Item(995, 25000)),

        VOID_CRYSTAL_1(12842, 1, TierUpgradeType.VOID_CRYSTAL , 17009, 70,  new Item(19062, 500), new Item(11054, 250) , new Item(11052, 250) , new Item(11056, 250), new Item(995, 25000)),
        VOID_CRYSTAL_2(17009, 2, TierUpgradeType.VOID_CRYSTAL , 17010, 65,  new Item(19062, 750), new Item(11054, 350) , new Item(11052, 350) , new Item(11056, 350), new Item(995, 50000)),

        CORRUPT_CRYSTAL_1(763, 1, TierUpgradeType.CORRUPT_CRYSTAL , 764, 70,  new Item(3502, 5000), new Item(11054, 1500) , new Item(11052, 1500) , new Item(11056, 1500), new Item(995, 75000)),
        CORRUPT_CRYSTAL_2(764, 2, TierUpgradeType.CORRUPT_CRYSTAL , 765, 65,  new Item(3502, 7500), new Item(11054, 2500) , new Item(11052, 2500) , new Item(11056, 2500), new Item(995, 150000)),


        GAIA_BOOK_1(6805, 1, TierUpgradeType.GAIA_BOOK , 15700, 75,  new Item(20007, 1), new Item(621, 10000)),
        GAIA_BOOK_2(15700, 2, TierUpgradeType.GAIA_BOOK , 15701, 70,  new Item(20008, 1), new Item(621, 50000)),

        LAVA_BOOK_1(6807, 1, TierUpgradeType.LAVA_BOOK , 15705, 75,  new Item(20007, 1), new Item(621, 10000)),
        LAVA_BOOK_2(15705, 2, TierUpgradeType.LAVA_BOOK , 15706, 70,  new Item(20008, 1), new Item(621, 50000)),

        AQUA_BOOK_1(6806, 1, TierUpgradeType.AQUA_BOOK , 15703, 75,  new Item(20007, 1), new Item(621, 10000)),
        AQUA_BOOK_2(15703, 2, TierUpgradeType.AQUA_BOOK , 15704, 70,  new Item(20008, 1), new Item(621, 50000)),

        VOID_BOOK_1(6808, 1, TierUpgradeType.VOID_BOOK , 15707, 75,  new Item(20008, 2), new Item(995, 75000)),
        VOID_BOOK_2(15707, 2, TierUpgradeType.VOID_BOOK , 15708, 70,  new Item(20009, 2), new Item(995, 125000)),

        CORRUPT_BOOK_1(3526, 1, TierUpgradeType.CORRUPT_BOOK , 3527, 70,  new Item(3502, 7500), new Item(20008, 5) , new Item(20009, 2)),
        CORRUPT_BOOK_2(3527, 2, TierUpgradeType.CORRUPT_BOOK , 3528, 70,  new Item(3502, 15000), new Item(20008, 10) , new Item(20009, 2)),


        ENCHANTED_HEART_1(2705, 1, TierUpgradeType.ENCHANTED_HEART , 1300, 60,  new Item(19062, 200), new Item(995, 15000)),
        ENCHANTED_HEART_2(1300, 2, TierUpgradeType.ENCHANTED_HEART , 1301, 40,  new Item(19062, 400), new Item(995, 35000)),


        MAGMA_BLADE_1(1560, 1, TierUpgradeType.MAGMA_BLADE , 1561, 60,  new Item(12427, 2),  new Item(19062, 200), new Item(995, 150000)),
        MAGMA_BLADE_2(1561, 2, TierUpgradeType.MAGMA_BLADE , 1562, 55,  new Item(12427, 4),  new Item(19062, 350), new Item(995, 250000)),

        MAGMA_BOW_1(1570, 1, TierUpgradeType.MAGMA_BOW , 1571, 60,  new Item(12427, 2),  new Item(19062, 200), new Item(995, 150000)),
        MAGMA_BOW_2(1571, 2, TierUpgradeType.MAGMA_BOW , 1572, 55,  new Item(12427, 4),  new Item(19062, 350), new Item(995, 250000)),

        MAGMA_STAFF_1(1580, 1, TierUpgradeType.MAGMA_STAFF , 1581, 60,  new Item(12427, 2),  new Item(19062, 200), new Item(995, 150000)),
        MAGMA_STAFF_2(1581, 2, TierUpgradeType.MAGMA_STAFF , 1582, 55,  new Item(12427, 4),  new Item(19062, 350), new Item(995, 250000)),


        OVERGROWN_BLADE_1(1566, 1, TierUpgradeType.OVERGROWN_BLADE , 1567, 60,  new Item(12423, 2),  new Item(19062, 200), new Item(995, 150000)),
        OVERGROWN_BLADE_2(1567, 2, TierUpgradeType.OVERGROWN_BLADE , 1568, 55,  new Item(12423, 4),  new Item(19062, 350), new Item(995, 250000)),

        OVERGROWN_BOW_1(1576, 1, TierUpgradeType.OVERGROWN_BOW , 1577, 60,  new Item(12423, 2),  new Item(19062, 200), new Item(995, 150000)),
        OVERGROWN_BOW_2(1577, 2, TierUpgradeType.OVERGROWN_BOW , 1578, 55,  new Item(12423, 4),  new Item(19062, 350), new Item(995, 250000)),

        OVERGROWN_STAFF_1(1586, 1, TierUpgradeType.OVERGROWN_STAFF , 1587, 60,  new Item(12423, 2),  new Item(19062, 200), new Item(995, 150000)),
        OVERGROWN_STAFF_2(1587, 2, TierUpgradeType.OVERGROWN_STAFF , 1588, 55,  new Item(12423, 4),  new Item(19062, 350), new Item(995, 250000)),


        AQUATIC_BLADE_1(1563, 1, TierUpgradeType.AQUATIC_BLADE , 1564, 60,  new Item(12424, 2),  new Item(19062, 200), new Item(995, 150000)),
        AQUATIC_BLADE_2(1564, 2, TierUpgradeType.AQUATIC_BLADE , 1565, 55,  new Item(12424, 4),  new Item(19062, 350), new Item(995, 250000)),

        AQUATIC_BOW_1(1573, 1, TierUpgradeType.AQUATIC_BOW , 1574, 60,  new Item(12424, 2),  new Item(19062, 200), new Item(995, 150000)),
        AQUATIC_BOW_2(1574, 2, TierUpgradeType.AQUATIC_BOW , 1575, 55,  new Item(12424, 4),  new Item(19062, 350), new Item(995, 250000)),

        AQUATIC_STAFF_1(1583, 1, TierUpgradeType.AQUATIC_STAFF , 1584, 60,  new Item(12424, 2),  new Item(19062, 200), new Item(995, 150000)),
        AQUATIC_STAFF_2(1584, 2, TierUpgradeType.AQUATIC_STAFF , 1585, 55,  new Item(12424, 4),  new Item(19062, 350), new Item(995, 250000)),


        SOUL_ENERGY(17021, 1, TierUpgradeType.SOUL_ENERGY , 2613, 70,  new Item(19062, 400),  new Item(11054, 750) ,  new Item(11052, 750) ,  new Item(11056, 750), new Item(995, 500000)),
        BLITZ_ENERGY(17019, 1, TierUpgradeType.BLITZ_ENERGY , 2611, 70,  new Item(19062, 400),  new Item(11054, 750) ,  new Item(11052, 750) ,  new Item(11056, 750), new Item(995, 500000)),
        UNDEAD_ENERGY(17022, 1, TierUpgradeType.UNDEAD_ENERGY , 2614, 70,  new Item(19062, 200),  new Item(11054, 750) ,  new Item(11052, 750) ,  new Item(11056, 750), new Item(995, 200000)),
        SLAYER_ENERGY(17018, 1, TierUpgradeType.SLAYER_ENERGY , 2610, 70,  new Item(19062, 350),  new Item(11054, 750) ,  new Item(11052, 750) ,  new Item(11056, 750), new Item(995, 300000)),
        LUCK_ENERGY(17020, 1, TierUpgradeType.LUCK_ENERGY , 2612, 70,  new Item(19062, 350),  new Item(11054, 750) ,  new Item(11052, 750) ,  new Item(11056, 750), new Item(995, 300000)),
        ;

        public static TierUpgradingData forItemId(int itemId) {
            for (TierUpgradingData data : values())
                if (data.getItemId() == itemId)
                    return data;
            return null;
        }
        public static TierUpgradingData forTier(TierUpgradeType type, int tier) {
            for (TierUpgradingData data : values()) {
                if (data.getType() == type && data.getTier() == tier) {
                    return data;
                }
            }

            switch (type) {
                case EARTHSLAYER:
                    return EARTH_SLAYER_HELM_1;
                case WATERSLAYER:
                    return WATER_SLAYER_HELM1;
                case FIRESLAYER:
                    return FIRE_SLAYER_HELM_1;
                case VOIDSLAYER:
                    return VOID_SLAYER_HELM_1;
                case GAIA_CRYSTAL:
                    return GAIA_CRYSTAL_1;
                case AQUA_CRYSTAL:
                    return AQUA_CRYSTAL_1;
                case LAVA_CRYSTAL:
                    return LAVA_CRYSTAL_1;
                case VOID_CRYSTAL:
                    return VOID_CRYSTAL_1;
                case CORRUPT_CRYSTAL:
                    return CORRUPT_CRYSTAL_1;


                case MAGIC_TOTEM:
                    return MAGIC_TOTEM_1;
                case MELEE_TOTEM:
                    return MELEE_TOTEM_1;
                case RANGE_TOTEM:
                    return RANGE_TOTEM_1;
                case MASTER_TOTEM:
                    return MASTER_TOTEM_1;



                case GAIA_BOOK:
                    return GAIA_BOOK_1;
                case AQUA_BOOK:
                    return AQUA_BOOK_1;
                case LAVA_BOOK:
                    return LAVA_BOOK_1;
                case VOID_BOOK:
                    return VOID_BOOK_1;


              /*  case GAIA_CAPE:
                    return GAIA_CAPE_1;
                case AQUA_CAPE:
                    return AQUA_CAPE_1;
                case LAVA_CAPE:
                    return LAVA_CAPE_1;
                case VOID_CAPE:
                    return VOID_CAPE_1;*/

                case ENCHANTED_HEART:
                    return ENCHANTED_HEART_1;

                case MAGMA_BLADE:
                    return MAGMA_BLADE_1;
                case MAGMA_BOW:
                    return MAGMA_BOW_1;
                case MAGMA_STAFF:
                    return MAGMA_STAFF_1;

                case AQUATIC_BLADE:
                    return AQUATIC_BLADE_1;
                case AQUATIC_BOW:
                    return AQUATIC_BOW_1;
                case AQUATIC_STAFF:
                    return AQUATIC_STAFF_1;

                case OVERGROWN_BLADE:
                    return OVERGROWN_BLADE_1;
                case OVERGROWN_BOW:
                    return OVERGROWN_BOW_1;
                case OVERGROWN_STAFF:
                    return OVERGROWN_STAFF_1;

                case SOUL_ENERGY:
                    return SOUL_ENERGY;
                case LUCK_ENERGY:
                    return LUCK_ENERGY;
                case BLITZ_ENERGY:
                    return BLITZ_ENERGY;
                case SLAYER_ENERGY:
                    return SLAYER_ENERGY;
                case UNDEAD_ENERGY:
                    return UNDEAD_ENERGY;

         /*       case CORRUPT_AMULET:
                    return CORRUPT_AMULET_1;*/

              /*  case CORRUPT_RING:
                    return CORRUPT_RING_1;*/
                    
                case CORRUPT_HELMET:
                    return CORRUPT_HELMET_1;
                case SPECTRAL_HELMET:
                    return SPECTRAL_HELMET_1;

                case CORRUPT_BOOK:
                    return CORRUPT_BOOK_1;
            /*    case CORRUPT_CAPE:
                    return CORRUPT_CAPE_1;*/

                default:
                    throw new IllegalArgumentException("Unknown TierUpgradeType: " + type);
            }
        }
        TierUpgradingData(int itemId, int tier, TierUpgradeType type, int nextItem, int successChance, Item... requiredItems) {
            this.itemId = itemId;
            this.tier = tier;
            this.type = type;
            this.nextItem = nextItem;
            this.successChance = successChance;
            this.requiredItems = requiredItems;
        }
        private final int itemId;
        private final int tier;
        private final TierUpgradeType type;
        private final int nextItem;
        private final int successChance;
        private final Item[] requiredItems;
    }
}
