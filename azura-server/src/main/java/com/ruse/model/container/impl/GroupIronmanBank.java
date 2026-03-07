package com.ruse.model.container.impl;

import com.ruse.model.Flag;
import com.ruse.model.GameMode;
import com.ruse.model.Item;
import com.ruse.model.container.ItemContainer;
import com.ruse.model.container.StackType;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.definitions.WeaponInterfaces;
import com.ruse.model.input.impl.ItemSearch;
import com.ruse.world.World;
import com.ruse.world.content.BankPin;
import com.ruse.world.content.BonusManager;
import com.ruse.world.content.combat.magic.Autocasting;
import com.ruse.world.content.skill.impl.old_dungeoneering.Dungeoneering;
import com.ruse.world.entity.impl.player.Player;

/**
 * 100% safe Bank System
 *
 * @author Gabriel Hannason
 */

public class GroupIronmanBank extends ItemContainer {

    private GroupIronmanBank[] bankTabs; // open the shift click packet 4 me

    public GroupIronmanBank() {
        super(null);
    }
    public void add(Item[] items) {
        for (Item item : items) {
            if (item == null) {
                continue;
            }
            add(item);
        }

    }
    public void add(Item[] items, boolean refresh) {
        for (Item item : items) {
            if (item == null) {
                continue;
            }
            add(item, refresh);
        }

    }
    public GroupIronmanBank open(Player player) {
        return open(player, true);
    }


    public GroupIronmanBank open(Player player, boolean reset) {
        return open(player, reset, true);
    }

    public GroupIronmanBank open(Player player, boolean reset, boolean sort) {
        player.getPacketSender().sendClientRightClickRemoval();
        if (Dungeoneering.doingOldDungeoneering(player)) {
            return this;
        }
        player.setViewing(Player.INTERFACES.GROUP_BANK);
        if (sort)
            sortItems();
        GroupIronmanBank bank = player.getGroupBankSearchingAttribtues().isSearchingBank()
            && player.getGroupBankSearchingAttribtues().getSearchedBank() != null
            ? player.getGroupBankSearchingAttribtues().getSearchedBank() : this;
        int amountToSend = bank.getValidItems().size() >= 51 ? 38 * (int) Math.ceil(((double) bank.getValidItems().size() / 10)) : 0;
        player.getPacketSender().setScrollBar(5385, amountToSend);
        sortItems();
        refreshItems(player);
        player.setBanking(true).setInputHandling(null);
        player.getPacketSender().sendConfig(115, player.withdrawAsNote() ? 0 : 1)
            .sendConfig(304, player.swapMode() ? 0 : 1)
            .sendConfig(117,(player.getGroupBankSearchingAttribtues().isSearchingBank() && player.getGroupBankSearchingAttribtues().getSearchedBank() != null) ? 0 : 1)
            .sendConfig(111, player.isPlaceholders() ? 0 : 1)
            .sendInterfaceSet(5292, 5063)
            .sendString(5383, "The Bank of Team: " + player.getIronmanGroup().getName());
        return this;
    }
    public GroupIronmanBank openOther(Player checker, Player player, boolean reset, boolean sort) {
        checker.getPacketSender().sendClientRightClickRemoval();

        GroupIronmanBank bank = checker.getGroupBankSearchingAttribtues().isSearchingBank()
            && checker.getGroupBankSearchingAttribtues().getSearchedBank() != null
            ? checker.getGroupBankSearchingAttribtues().getSearchedBank() : this;
        checker.getPacketSender().sendString(22033, "" + bank.getValidItems().size());
        checker.getPacketSender().sendString(22034, "" + bank.capacity());
        checker.getPacketSender().sendItemContainer(bank, INTERFACE_ID);
        checker.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_INTERFACE_ID);


        checker.setBanking(true).setInputHandling(null);
        checker.getPacketSender().sendConfig(115, checker.withdrawAsNote() ? 0 : 1)
            .sendConfig(304, checker.swapMode() ? 0 : 1)
            .sendConfig(117,(checker.getGroupBankSearchingAttribtues().isSearchingBank() && checker.getGroupBankSearchingAttribtues().getSearchedBank() != null) ? 0 : 1)
            .sendConfig(111, checker.isPlaceholders() ? 0 : 1)
            .sendInterfaceSet(5292, 5063);
        return this;
    }
    
    public GroupIronmanBank switchItem(Player player, ItemContainer to, Item item, int slot, boolean sort, boolean refresh) {
        if (!player.isBanking() || player.getInterfaceId() != 5292 || to instanceof Inventory
            && !(player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).contains(item.getId())
            || player.getGroupBankSearchingAttribtues().getSearchedBank() != null
            && player.getGroupBankSearchingAttribtues().getSearchedBank().contains(item.getId()))) {
            player.getPacketSender().sendClientRightClickRemoval();
            return this;
        }
        ItemDefinition def = ItemDefinition.forId(item.getId() + 1);
        if (to.getFreeSlots() <= 0 && (!(to.contains(item.getId()) && item.getDefinition().isStackable()))
            && !(player.withdrawAsNote() && def != null && def.isNoted() && to.contains(def.getId()))) {
            int tab = GroupIronmanBank.getTabForItem(player, item.getId());
            if (player.getIronmanGroup().getBank(tab).contains(item.getId())) {
                if (player.getIronmanGroup().getBank(tab).getAmount(item.getId()) > 0 && to instanceof Inventory) {
                    to.full();
                    return this;
                }
            }

        }
        if (item.getAmount() > to.getFreeSlots() && !item.getDefinition().isStackable()) {
            if (to instanceof Inventory) {
                if (player.withdrawAsNote()) {
                    if (def == null || !def.isNoted())
                        item.setAmount(to.getFreeSlots());
                } else
                    item.setAmount(to.getFreeSlots());
            }
        }

        if (player.getGroupBankSearchingAttribtues().isSearchingBank()
            && player.getGroupBankSearchingAttribtues().getSearchedBank() != null) {
            int tab = GroupIronmanBank.getTabForItem(player, item.getId());
            if (!player.getIronmanGroup().getBank(tab).contains(item.getId())
                || !player.getGroupBankSearchingAttribtues().getSearchedBank().contains(item.getId()))
                return this;
            if (item.getAmount() > player.getIronmanGroup().getBank(tab).getAmount(item.getId())) {
                item.setAmount(player.getIronmanGroup().getBank(tab).getAmount(item.getId()));
            }
            if (item.getAmount() <= 0) {
                int from = player.getIronmanGroup().getBank(tab).getSlot(item.getId());
                if (from != -1) {
                    if (player.getIronmanGroup().getBank(tab).getItems()[from].getId() == item.getId()) {
                        player.getIronmanGroup().getBank(tab).getItems()[from].setId(-1);
                        player.getGroupBankSearchingAttribtues().getSearchedBank().delete(item);
                        player.getGroupBankSearchingAttribtues().getSearchedBank().open(player, false);
                    }
                }
                return this;
            }
            player.getIronmanGroup().getBank(tab).delete(item);
            player.getGroupBankSearchingAttribtues().getSearchedBank().delete(item);
            player.getGroupBankSearchingAttribtues().getSearchedBank().open(player, false);
        } else {
            if (getItems()[slot].getId() != item.getId() || !contains(item.getId()))
                return this;
            if (item.getAmount() > getAmount(item.getId())) {
                item.setAmount(getAmount(item.getId()));
            }

            if (item.getAmount() <= 0) {
                // Placeholder
                getItems()[slot].setId(-1);
                refreshItems(player);
                return this;
            }

            if (to instanceof Inventory) {
                boolean withdrawAsNote = player.withdrawAsNote() && def != null && def.isNoted()
                    && item.getDefinition() != null
                    && def.getName().equalsIgnoreCase(item.getDefinition().getName())
                    && !def.getName().contains("Torva") && !def.getName().contains("Virtus")
                    && !def.getName().contains("Pernix") && !def.getName().contains("Torva");
                int checkId = withdrawAsNote ? item.getId() + 1 : item.getId();
                if (to.getAmount(checkId) + item.getAmount() > Integer.MAX_VALUE
                    || to.getAmount(checkId) + item.getAmount() <= 0) {
                    player.getPacketSender()
                        .sendMessage("You cannot withdraw that entire amount into your inventory.");
                    return this;
                }
            }

            delete(item, slot, refresh, to);
        }
        if (player.withdrawAsNote()) {
            if (def != null && def.isNoted() && item.getDefinition() != null
                && def.getName().equalsIgnoreCase(item.getDefinition().getName())
                && !def.getName().contains("Torva") && !def.getName().contains("Virtus")
                && !def.getName().contains("Pernix") && !def.getName().contains("Torva"))
                item.setId(item.getId() + 1);
            else
                player.getPacketSender().sendMessage("This item cannot be withdrawn as a note.");
        }
        to.add(item, refresh);

        if (sort)
            sortItems();
        if (refresh) {
            refreshItems(player);
            for (int i = 0; i < 5; i++) {
                if (player.getIronmanGroup().getMembers().size() > i) {
                    String name = player.getIronmanGroup().getMembers().get(i);
                    Player target = World.getPlayerByName(name);
                    if (target != null) {
                        if (target.getCurrentGroupBankTab() != player.getCurrentGroupBankTab())
                            continue;
                        target.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).refreshItems(target);
                    }
                }
            }
            to.refreshItems();
        }
        return this;
    }

    @Override
    public int capacity() {

        return 1000;

    }

    @Override
    public StackType stackType() {
        return StackType.STACKS;
    }

    @Override
    public ItemContainer refreshItems() {
        return null;
    }

    @Override
    public ItemContainer full() {
        return null;
    }


    public GroupIronmanBank refreshItems(Player player) {
        GroupIronmanBank bank = player.getGroupBankSearchingAttribtues().isSearchingBank()
            && player.getGroupBankSearchingAttribtues().getSearchedBank() != null
            ? player.getGroupBankSearchingAttribtues().getSearchedBank() : this;
        player.getPacketSender().sendString(22033, "" + bank.getValidItems().size());
        player.getPacketSender().sendString(22034, "" + bank.capacity());
        int amountToSend = bank.getValidItems().size() >= 51 ? 38 * (int) Math.ceil(((double) bank.getValidItems().size() / 10)) : 0;
        player.getPacketSender().setScrollBar(5385, amountToSend);
        player.getPacketSender().sendItemContainer(bank, INTERFACE_ID);
        player.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_INTERFACE_ID);
        sendTabs(player, getBankTabs());
        if (!player.isBanking() || player.getInterfaceId() != 5292)
            player.getPacketSender().sendClientRightClickRemoval();
        return this;
    }
    
    public GroupIronmanBank full(Player player) {
        player.getPacketSender().sendMessage("Not enough space in group bank.");
        return this;
    }

    public static void sendTabs(Player player, GroupIronmanBank[] bankTabs) {
        if (bankTabs == null) {
            bankTabs = player.getIronmanGroup().getBanks();
        }
        boolean moveRest = false;
        if (isEmpty(bankTabs[1])) { // tab 1 empty
            player.getIronmanGroup().setBank(1, bankTabs[2]);
            player.getIronmanGroup().setBank(2, new GroupIronmanBank());
            moveRest = true;
        }
        if (isEmpty(bankTabs[2]) || moveRest) {
            player.getIronmanGroup().setBank(2, bankTabs[3]);
            player.getIronmanGroup().setBank(3, new GroupIronmanBank());
            moveRest = true;
        }
        if (isEmpty(bankTabs[3]) || moveRest) {
            player.getIronmanGroup().setBank(3, bankTabs[4]);
            player.getIronmanGroup().setBank(4, new GroupIronmanBank());
            moveRest = true;
        }
        if (isEmpty(bankTabs[4]) || moveRest) {
            player.getIronmanGroup().setBank(4, bankTabs[5]);
            player.getIronmanGroup().setBank(5, new GroupIronmanBank());
            moveRest = true;
        }
        if (isEmpty(bankTabs[5]) || moveRest) {
            player.getIronmanGroup().setBank(5, bankTabs[6]);
            player.getIronmanGroup().setBank(6, new GroupIronmanBank());
            moveRest = true;
        }
        if (isEmpty(bankTabs[6]) || moveRest) {
            player.getIronmanGroup().setBank(6, bankTabs[7]);
            player.getIronmanGroup().setBank(7, new GroupIronmanBank());
            moveRest = true;
        }
        if (isEmpty(bankTabs[7]) || moveRest) {
            player.getIronmanGroup().setBank(7, bankTabs[8]);
            player.getIronmanGroup().setBank(8, new GroupIronmanBank());
        }
        /*
         * boolean moveRest = false; for(int i = 1; i <= 7; i++) {
         * if(isEmpty(bankTabs[i)) || moveRest) { int j = i+2 > 8 ? 8 : i+2;
         * player.getIronmanGroup().setBank(i, bankTabs[j)); player.getIronmanGroup().setBank(j, new Bank(player));
         * moveRest = true; } }
         */
        int tabs = getTabCount(player, bankTabs);
        if (player.getCurrentGroupBankTab() > tabs)
            player.setCurrentGroupBankTab(tabs);
        player.getPacketSender().sendString(27001, Integer.toString(tabs)).sendString(27002,
            Integer.toString(player.getCurrentGroupBankTab()));
        int l = 1;
        for (int i = 27015; i < 27023; i++) {
            player.getPacketSender().sendItemOnInterface(i, getInterfaceModel(bankTabs[l]), 1);
            l++;
        }
        player.getPacketSender().sendString(27000, "1");
    }


    public static void depositItems(Player p, ItemContainer from, boolean ignoreReqs) {
        if (!ignoreReqs)
            if (!p.isBanking() || p.getInterfaceId() != 5292)
                return;
		/*if (p.getInventory().getAmount(ItemDefinition.COIN_ID) > 10 && p.getInventory().contains(ItemDefinition.COIN_ID)) {
			int coinsamount = p.getInventory().getAmount(ItemDefinition.COIN_ID);
			p.getInventory().delete(ItemDefinition.COIN_ID,coinsamount);
			p.setMoneyInPouch(p.getMoneyInPouch() + coinsamount);
			p.getPacketSender().sendString(8135, "" + p.getMoneyInPouch());
			p.sendMessage("Your coins has been added to the pouch");
		}*/
        if ((p.getCurrentGroupBankTab()) > 8) {
            p.setCurrentGroupBankTab(8);
            if (p.getIronmanGroup().getBank(p.getCurrentGroupBankTab()).isFull()) {
                p.sendMessage("Your whole bank is full.");
                return;
            }
        }
        for (Item it : from.getValidItems()) {
            if (p.getIronmanGroup().getBank(p.getCurrentGroupBankTab()).getFreeSlots() <= 0
                && !(p.getIronmanGroup().getBank(p.getCurrentGroupBankTab()).contains(it.getId()) && it.getDefinition().isStackable())) {
                p.getPacketSender().sendMessage("Bank full.");
                return;
            }

            Item toBank = new Item(ItemDefinition.forId(it.getId()).isNoted() ? (it.getId() - 1) : it.getId(),
                it.getAmount());
            int tab = getTabForItem(p, toBank.getId());
            p.setCurrentGroupBankTab(tab);
            int bankAmt = p.getIronmanGroup().getBank(tab).getAmount(toBank.getId());
            if (bankAmt + toBank.getAmount() >= Integer.MAX_VALUE || bankAmt + toBank.getAmount() <= 0) {
                p.getPacketSender().sendMessage("Your bank cannot hold that amount of that item.");
                continue;
            }
            p.getIronmanGroup().getBank(tab).add(toBank.copy(), false);
            if (p.getGroupBankSearchingAttribtues().isSearchingBank()
                && p.getGroupBankSearchingAttribtues().getSearchedBank() != null)
                GroupBankSearchAttributes.addItemToBankSearch(p, toBank);
            from.delete(it.getId(), it.getAmount(), false);
        }
        from.refreshItems();
        p.getIronmanGroup().getBank(p.getCurrentGroupBankTab());
        p.getIronmanGroup().getBank(p.getCurrentGroupBankTab()).refreshItems(p);
        for (int i = 0; i < 5; i++) {
            if (p.getIronmanGroup().getMembers().size() > i) {
                String name = p.getIronmanGroup().getMembers().get(i);
                Player target = World.getPlayerByName(name);
                if (target != null)
                    target.getIronmanGroup().getBank(p.getCurrentGroupBankTab()).refreshItems(target);
            }
        }
        if (from instanceof Equipment) {
            Item weapon = p.getEquipment().get(Equipment.WEAPON_SLOT);
            Autocasting.resetAutocast(p, true);
            p.getPacketSender().sendMessage("Autocast spell cleared.");
            WeaponInterfaces.assign(p, p.getEquipment().get(Equipment.WEAPON_SLOT));
            BonusManager.update(p);
            p.setStaffOfLightEffect(-1);
            p.getUpdateFlag().flag(Flag.APPEARANCE);
        }
    }

    public static boolean isEmpty(GroupIronmanBank bank) {
        return bank.sortItems().getValidItems().size() <= 0;
    }

    public static int getTabCount(Player player, GroupIronmanBank[] bankTabs) {
        int tabs = 0;
        for (int i = 1; i < 9; i++) {
            if (!isEmpty(bankTabs[i])) {
                tabs++;
            } else
                break;
        }
        return tabs;
    }

    public static int getTabForItem(Player player, int itemID) {
        if (ItemDefinition.forId(itemID).isNoted()) {
            itemID = Item.getUnNoted(itemID);
        }
        for (int k = 0; k < 9; k++) {
            GroupIronmanBank bank = player.getIronmanGroup().getBank(k);
            if (bank == null) {
                System.out.println("Nulled bank for: " + player.getIronmanGroup().getName() + " at Index: " + k);
                continue;
            }
            for (int i = 0; i < bank.getValidItems().size(); i++) {
                if (bank.getItems()[i].getId() == itemID) {
                    return k;
                }
            }
        }
        return player.getCurrentGroupBankTab();
    }

    public static int getTabForItem(Player player, Item item) {
        if (ItemDefinition.forId(item.getId()).isNoted()) {
            item.setId(Item.getUnNoted(item.getId()));
        }
        for (int k = 0; k < 9; k++) {
            GroupIronmanBank bank = player.getIronmanGroup().getBank(k);
            for (int i = 0; i < bank.getValidItems().size(); i++) {
                if (bank.getItems()[i].getId() == item.getId()) {
                    return k;
                }
            }
        }
        return player.getCurrentGroupBankTab();
    }

    public static int getInterfaceModel(GroupIronmanBank bank) {
        if (bank.getItems()[0] == null)
            return -1;
        int model = bank.getItems()[0].getId();
        int amount = bank.getItems()[0].getAmount();
        return model;
    }

    /**
     * The bank interface id.
     */
    public static final int INTERFACE_ID = 5382;

    /**
     * The bank inventory interface id.
     */
    public static final int INVENTORY_INTERFACE_ID = 5064;

    /**
     * The bank tab interfaces
     */
    public static final int[][] BANK_TAB_INTERFACES = { { 5, 0 }, { 13, 1 }, { 26, 2 }, { 39, 3 }, { 52, 4 }, { 65, 5 },
        { 78, 6 }, { 91, 7 }, { 104, 8 } };

    /**
     * The item id of the selected item in the 'bank X' option
     */

    public static class GroupBankSearchAttributes {

        private boolean searchingBank;
        private String searchSyntax;
        private GroupIronmanBank searchedBank;

        public boolean isSearchingBank() {
            return searchingBank;
        }

        public GroupBankSearchAttributes setSearchingBank(boolean searchingBank) {
            this.searchingBank = searchingBank;
            return this;
        }

        public String getSearchSyntax() {
            return searchSyntax;
        }

        public GroupBankSearchAttributes setSearchSyntax(String searchSyntax) {
            this.searchSyntax = searchSyntax;
            return this;
        }

        public GroupIronmanBank getSearchedBank() {
            return searchedBank;
        }

        public GroupBankSearchAttributes setSearchedBank(GroupIronmanBank searchedBank) {
            this.searchedBank = searchedBank;
            return this;
        }

        public static void beginSearch(Player player, String searchSyntax) {
            player.getPacketSender().sendClientRightClickRemoval();
            searchSyntax = (String) ItemSearch.getFixedSyntax(searchSyntax)[0];
            player.getPacketSender().sendString(5383, "Searching for: " + searchSyntax + "..");
            player.getGroupBankSearchingAttribtues().setSearchingBank(true).setSearchSyntax(searchSyntax);
            player.setCurrentGroupBankTab(0).setNoteWithdrawal(false);
            player.getPacketSender().sendString(27002, Integer.toString(player.getCurrentGroupBankTab())).sendString(27000,
                "1");
            player.getGroupBankSearchingAttribtues().setSearchedBank(new GroupIronmanBank());
            for (GroupIronmanBank bank : player.getIronmanGroup().getBanks()) {
                bank.sortItems();
                for (Item bankedItem : bank.getValidItems())
                    addItemToBankSearch(player, bankedItem);
            }
            player.getGroupBankSearchingAttribtues().getSearchedBank().refreshItems(player);
            player.getGroupBankSearchingAttribtues().getSearchedBank().open(player);
            player.getPacketSender().sendString(5383, "Showing results found for: " + searchSyntax + "");
        }

        public static void addItemToBankSearch(Player player, Item item) {
            if (player.getGroupBankSearchingAttribtues().getSearchSyntax() != null) {
                if (item.getDefinition().getName().toLowerCase()
                    .contains(player.getGroupBankSearchingAttribtues().getSearchSyntax())) {
                    if (player.getGroupBankSearchingAttribtues().getSearchedBank().getFreeSlots() == 0)
                        return;
                    player.getGroupBankSearchingAttribtues().getSearchedBank().add(item, false);
                }
            }
        }

        public static void stopSearch(Player player, boolean openBank) {
            player.getPacketSender().sendClientRightClickRemoval();
            player.getGroupBankSearchingAttribtues().setSearchedBank(null).setSearchingBank(false).setSearchSyntax(null);
            player.setCurrentGroupBankTab(0).setNoteWithdrawal(false);
            player.getPacketSender().sendString(27002, Integer.toString(0)).sendString(27000, "1").sendConfig(117, 0)
                .sendString(5383, "  The Bank of Athens");
            if (openBank)
                player.getIronmanGroup().getBank(0).open(player, false);
            player.setInputHandling(null);
        }

        public static void withdrawFromSearch(Player player, Item item) {
            if (player.isBanking() && player.getGroupBankSearchingAttribtues().isSearchingBank()) {
                int tab = Bank.getTabForItem(player, item.getId());
                if (tab == player.getCurrentGroupBankTab() && !player.getIronmanGroup().getBank(tab).contains(item.getId()))
                    return;
            }
        }
    }

    /**
     * The items in this container.
     */
    private Item[] items;

    public Item[] array() {
        return items.clone();
    }

    public GroupIronmanBank[] getBankTabs() {
        return bankTabs;
    }

    public void setBankTabs(GroupIronmanBank[] bankTabs) {
        this.bankTabs = bankTabs;
    }
}
