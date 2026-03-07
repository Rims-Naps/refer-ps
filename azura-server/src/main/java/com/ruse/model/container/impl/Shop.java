package com.ruse.model.container.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruse.GameSettings;
import com.ruse.ReducedSellPrice;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.ShopRestockTask;
import com.ruse.model.GameMode;
import com.ruse.model.Item;
import com.ruse.model.Locations.Location;
import com.ruse.model.Skill;
import com.ruse.model.container.ItemContainer;
import com.ruse.model.container.StackType;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.input.impl.EnterAmountToBuyFromShop;
import com.ruse.model.input.impl.EnterAmountToSellToShop;
import com.ruse.util.JsonLoader;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.DonatorShop;
import com.ruse.world.content.CosmeticShop;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPanel;
import com.ruse.world.content.groupironman.IronmanGroup;
import com.ruse.world.content.minigames.impl.RecipeForDisaster;
import com.ruse.world.content.skill.impl.old_dungeoneering.UltimateIronmanHandler;
import com.ruse.world.entity.impl.player.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Messy but perfect Shop System
 *
 * @author Gabriel Hannason
 */

public class Shop extends ItemContainer {

    /**
     * The shop interface id.
     */
    public static final int INTERFACE_ID = 3824;
    /**
     * The starting interface child id of items.
     */
    public static final int ITEM_CHILD_ID = 3900;
    /**
     * The interface child id of the shop's name.
     */
    public static final int NAME_INTERFACE_CHILD_ID = 3901;
    /**
     * Total amount that a player can earn from the trash store
     */
    public static int MAX_EARN_TRASH = 100000000;
    public static final int INVENTORY_INTERFACE_ID = 3823;

    public static final int VOTE_STORE = 0;

    public static final int REFUND_SHOP = 21;


    public static final int BEGINNER_SLAYER = 1;
    public static final int MEDIUM_SLAYER = 2;
    public static final int ELITE_SLAYER = 3;


    public static final int SPECTRAL_SLAYER = 22;


    public static final int WATEVER = 4;


    public static final int BEAST_HUNTER = 5;
    public static final int NECRO_SHOP = 6;
    public static final int MONSTER_ESSENCE_SHOP = 7;
    public static final int SHOP_2 = 8;
    public static final int COSMETIC_SHOP = 9;
    public static final int MINING_SHOP = 10;
    public static final int IRONMAN_SHOP = 11;
    public static final int WOOCUTTING_SHOP = 12;
    public static final int MINE_SHOP = 13;
    public static final int CHRISTMAS_SHOP = 14;
    public static final int GLOBAL_SHOP = 30;
    public static final int STREAM_SHOP = 31;
    public static final int TOWER_SHOP = 17;
    public static final int CORRUPT_SHOP = 18;

    public static final int ENCHANTED_SHOP = 19;

    public static final int AFK_SHOP = 20;


    public static final int SKILLCAPE_STORE_1 = 250;
    public static final int SKILLCAPE_STORE_2 = 251;
    public static final int SKILLCAPE_STORE_3 = 252;
    public static final int GENERAL_STORE = 253;
    public static final int RECIPE_FOR_DISASTER_STORE = 254;
    public static final int TRASH_STORE = 255;
    private static final int DONATOR_STORE_1 = DonatorShop.DonatorShopType.WEAPON.getShopId();
    private static final int DONATOR_STORE_2 = DonatorShop.DonatorShopType.ARMOUR.getShopId();
    private static final int DONATOR_STORE_3 = DonatorShop.DonatorShopType.ACCESSORY.getShopId();
    private static final int DONATOR_STORE_4 = DonatorShop.DonatorShopType.MISC.getShopId();


    private static final int COSMETIC_ORDINARY = CosmeticShop.CosmeticShopTier.ORDINARY.getShopId();
    private static final int COSMETIC_FABLED = CosmeticShop.CosmeticShopTier.FABLED.getShopId();
    private static final int COSMETIC_EXOTIC = CosmeticShop.CosmeticShopTier.EXOTIC.getShopId();
    private static final int COSMETIC_MYTHIC = CosmeticShop.CosmeticShopTier.MYTHIC.getShopId();
    private final int id;
    private String name;
    private Item currency;
    private final Item[] originalStock;
    private boolean restockingItems;
    /**
     * Opens a shop for a player
     *
     * @param player The player to open the shop for
     * @return The shop instance
     */


    private int currencyicon = 0;

    /*
     * The shop constructor
     */
    public Shop(Player player, int id, String name, Item currency, Item[] stockItems) {
        super(player);
        if (stockItems.length > 42)
            throw new ArrayIndexOutOfBoundsException(
                    "Stock cannot have more than 40 items; check shop[" + id + "]: stockLength: " + stockItems.length);
        this.id = id;
        this.name = !name.isEmpty() ? name : "General Store";
        this.currency = currency;
        this.originalStock = new Item[stockItems.length];
        for (int i = 0; i < stockItems.length; i++) {
            Item item = new Item(stockItems[i].getId(), stockItems[i].getAmount());
            add(item, false);
            this.originalStock[i] = item;
        }
    }

    /**
     * Checks if a player has enough inventory space to buy an item
     *
     * @param item The item which the player is buying
     * @return true or false if the player has enough space to buy the item
     */
    public static boolean hasInventorySpace(Player player, Item item, int currency, int pricePerItem) {
        if (player.getInventory().getFreeSlots() >= 1) {
            return true;
        }
        if (item.getDefinition().isStackable()) {
            if (player.getInventory().contains(item.getId())) {
                return true;
            }
        }
        return currency != -1 && player.getInventory().getFreeSlots() == 0
                && player.getInventory().getAmount(currency) == pricePerItem;
    }

    public static boolean shopBuysItem(int shopId, Item item) {
        if (shopId == TRASH_STORE)
            return true;
        if (shopId != TRASH_STORE)
            return false;
        Shop shop = ShopManager.getShops().get(shopId);
        if (shop != null && shop.getOriginalStock() != null) {
            for (Item it : shop.getOriginalStock()) {
                if (it != null && it.getId() == item.getId())
                    return true;
            }
        }
        return false;
    }

    public Item[] getOriginalStock() {
        return this.originalStock;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Shop setName(String name) {
        this.name = name;
        return this;
    }

    public Item getCurrency() {
        return currency;
    }

    public Shop setCurrency(Item currency) {
        this.currency = currency;
        return this;
    }

    public boolean isRestockingItems() {
        return restockingItems;
    }

    public void setRestockingItems(boolean restockingItems) {
        this.restockingItems = restockingItems;
    }

    public Shop open(Player player) {
        if (id == IRONMAN_SHOP) {
            if (!player.getGameMode().isIronman()) {
                player.getPacketSender().sendMessage("You're unable to access this shop as a " + player.getGameMode().toString().toLowerCase().replace("_", " ") + " player.");
                return this;
            }
        }

        if (player.getGameMode() == GameMode.IRONMAN || player.getGameMode() == GameMode.GROUP_IRON) {
            //SETTING SHOPS IRON CAN ACCESS
            if (id != VOTE_STORE && id != REFUND_SHOP && id != BEGINNER_SLAYER && id != MEDIUM_SLAYER && id != ELITE_SLAYER && id != SPECTRAL_SLAYER && id != BEAST_HUNTER && id != NECRO_SHOP && id != MONSTER_ESSENCE_SHOP && id != MINING_SHOP && id != COSMETIC_ORDINARY && id != COSMETIC_FABLED && id != COSMETIC_EXOTIC && id != COSMETIC_MYTHIC && id != IRONMAN_SHOP && id != DONATOR_STORE_1 && id != DONATOR_STORE_2 && id != DONATOR_STORE_3 && id !=DONATOR_STORE_4
                    && id != TRASH_STORE && id != WOOCUTTING_SHOP && id != MINE_SHOP && id != CHRISTMAS_SHOP && id != 30  && id != 31 && id != TOWER_SHOP && id != CORRUPT_SHOP && id != ENCHANTED_SHOP && id != AFK_SHOP) {
                player.getPacketSender().sendMessage("You're unable to access this shop as a " + player.getGameMode().toString().toLowerCase().replace("_", " ") + " player.");
                return this;
            }
        }

        setPlayer(player);
        getPlayer().getPacketSender().sendInterfaceRemoval().sendClientRightClickRemoval();
        getPlayer().setShop(ShopManager.getShops().get(id)).setInterfaceId(getInterfaceId()).setShopping(true);
        refreshItems();

        Shop shop = ShopManager.getShops().get(id);

        for (int i = 0; i < shop.getItems().length; i++) {
            Item item = getItems()[i];
            int finalValue = 0;
            if (getCurrency().getId() != -1) {
                finalValue = ItemDefinition.forId(item.getId()).getValue();
                Object[] obj = ShopManager.getCustomShopData(id, item.getId());
                if (obj != null) {
                    finalValue = (int) obj[0];
                }
            } else {
                Object[] obj = ShopManager.getCustomShopData(id, item.getId());
                if (obj != null) {
                    finalValue = (int) obj[0];
                }
            }

            if (shop.getId() == DONATOR_STORE_1) {
                currencyicon = 2;
                player.getPacketSender().sendString(35613 + i, finalValue + "," + currencyicon);
            } else {
                player.getPacketSender().sendString(35613 + i, "," + -1);
            }
        }

        return this;
    }

    /**
     * Refreshes a shop for every player who's viewing it
     */
    public void publicRefresh() {
        Shop publicShop = ShopManager.getShops().get(id);
        if (publicShop == null)
            return;
        publicShop.setItems(getItems());
        for (Player player : World.getPlayers()) {
            if (player == null)
                continue;
            if (player.getShop() != null && player.getShop().id == id && player.isShopping())
                player.getShop().setItems(publicShop.getItems());
        }
    }

    /**
     * Checks a value of an item in a shop
     *
     * @param player      The player who's checking the item's value
     * @param slot        The shop item's slot (in the shop!)
     * @param sellingItem Is the player selling the item?
     */
    public void checkValue(Player player, int slot, boolean sellingItem) {
        this.setPlayer(player);
        if (UltimateIronmanHandler.hasItemsStored(player) && player.getLocation() != Location.DUNGEONEERING) {
            player.getPacketSender().sendMessage(
                    "<shad=0>@red@You cannot use the shop until you claim your stored Dungeoneering items.");
            return;
        }
        Item shopItem = new Item(getItems()[slot].getId());
        if (!player.isShopping()) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        Item item = sellingItem ? player.getInventory().getItems()[slot] : getItems()[slot];
        if (item.getId() == 995 || item.getId() == ItemDefinition.COIN_ID)
            return;
        if (sellingItem) {
            if (!shopBuysItem(id, item)) {
                player.getPacketSender().sendMessage("You cannot sell items to this store.");
                return;
            }
        }

        int finalValue = 0;
        String finalString = sellingItem ? "" + ItemDefinition.forId(item.getId()).getName() + ": shop will buy for "
                : "<shad=0>@red@" + ItemDefinition.forId(shopItem.getId()).getName() + " <shad=0><col=AF70C3>currently costs @red@";
        String s = currency.getDefinition().getName().toLowerCase().endsWith("s")
                ? currency.getDefinition().getName().toLowerCase()
                : currency.getDefinition().getName().toLowerCase() + "s";
        if (getCurrency().getId() != -1) {
            finalValue = ItemDefinition.forId(item.getId()).getValue();


            /** CUSTOM CURRENCY, CUSTOM SHOP VALUES **/
            if (id == VOTE_STORE || id == REFUND_SHOP || id == BEGINNER_SLAYER || id == MEDIUM_SLAYER || id == ELITE_SLAYER || id == SPECTRAL_SLAYER || id == BEAST_HUNTER || id == NECRO_SHOP || id == MONSTER_ESSENCE_SHOP || id == MINING_SHOP || id == COSMETIC_ORDINARY || id == COSMETIC_FABLED || id == COSMETIC_EXOTIC || id == COSMETIC_MYTHIC|| id == IRONMAN_SHOP
                    || id == TRASH_STORE  || id == WOOCUTTING_SHOP  || id == MINE_SHOP || id == CHRISTMAS_SHOP|| id == 30 || id == 31 || id == TOWER_SHOP || id == CORRUPT_SHOP || id == ENCHANTED_SHOP || id == AFK_SHOP) {
                Object[] obj = ShopManager.getCustomShopData(id, item.getId());
                if (obj == null)
                    return;
                finalValue = (int) obj[0];
                s = (String) obj[1];
            }
            if (sellingItem) {
                if (finalValue != 1) {
                    finalValue = id == TRASH_STORE ? (int) finalValue : (int) (finalValue * 0.5D);
                }
            } else {
                if (player.getLocation() == Location.DUEL_ARENA) {
                    if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null)
                    {
                        finalValue -= finalValue / 4;
                    }
                }
            }


            finalString += "" + finalValue + " " + s + "" + shopPriceEx(finalValue) + ".";
        } else {
            Object[] obj = ShopManager.getCustomShopData(id, item.getId());
            if (obj == null)
                return;
            finalValue = (int) obj[0];
            if (sellingItem) {
                if (finalValue != 1) {
                    finalValue = id == TRASH_STORE ? (int) finalValue : (int) (finalValue * 0.5D);
                }
            }

            finalString += "" + finalValue + " " + obj[1] + ".";
        }

        if (sellingItem) {
            for (ReducedSellPrice r : ReducedSellPrice.values()) {
                if (r.getUnNotedId() == item.getId() || r.getNotedId() == item.getId()) {
                    finalString = ItemDefinition.forId(item.getId()).getName() + ": shop will buy for " + r.getSellValue() + " " + s + "" + shopPriceEx(r.getSellValue()) + ".";
                }
            }

        }

        if (player != null && finalValue > 0) {
            player.getPacketSender().sendMessage(finalString);
            if (id == COSMETIC_ORDINARY || id == COSMETIC_FABLED || id == COSMETIC_EXOTIC || id == COSMETIC_MYTHIC){
                if (!getPlayer().getCosmeticShop().hasRequirements(item.getId())){
                    return;
                }
            }

            return;
        }

    }

    public void sellItem(Player player, int slot, int amountToSell) {
        this.setPlayer(player);
        if (!player.isShopping() || player.isBanking()) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }

        if (!player.isShopping() || player.isBanking()) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }

        Item itemToSell = player.getInventory().getItems()[slot];
        if (!shopBuysItem(id, itemToSell)) {
            player.getPacketSender().sendMessage("You cannot sell items to this store.");
            return;
        }


        if (!player.getInventory().contains(itemToSell.getId()) || itemToSell.getId() == 995 || itemToSell.getId() == ItemDefinition.COIN_ID)
            return;
        if (this.full(itemToSell.getId()))
            return;
        if (player.getInventory().getAmount(itemToSell.getId()) < amountToSell)
            amountToSell = player.getInventory().getAmount(itemToSell.getId());
        if (amountToSell == 0)
            return;

        int itemId = itemToSell.getId();
        boolean customShop = this.getCurrency().getId() == -1;
        boolean inventorySpace = customShop;
        if (!customShop) {
            if (!itemToSell.getDefinition().isStackable()) {
                if (!player.getInventory().contains(this.getCurrency().getId()))
                    inventorySpace = true;
            }
            if (player.getInventory().getFreeSlots() <= 0
                    && player.getInventory().getAmount(this.getCurrency().getId()) > 0)
                inventorySpace = true;
            if (player.getInventory().getFreeSlots() > 0
                    || player.getInventory().getAmount(this.getCurrency().getId()) > 0)
                inventorySpace = true;
        }
        int itemValue = 0;
        if (getCurrency().getId() > 0 && id != 119) {
            if (id != TRASH_STORE) {
                itemValue = ItemDefinition.forId(itemToSell.getId()).getValue();
            }
            if (itemValue == 0) {
                Object[] obj = ShopManager.getCustomShopData(id, itemToSell.getId());
                if (obj == null)
                    return;
                itemValue = (int) obj[0];
            }
        } else {
            Object[] obj = ShopManager.getCustomShopData(id, itemToSell.getId());
            if (obj == null)
                return;
            itemValue = (int) obj[0];
        }
        if (itemValue <= 0)
            return;
        itemValue = id == TRASH_STORE ? (int) itemValue : (int) (itemValue * 0.5D);


        if (itemValue <= 0) {
            itemValue = 1;
        }
        for (int i = amountToSell; i > 0; i--) {
            itemToSell = new Item(itemId);
            if (this.full(itemToSell.getId()) || !player.getInventory().contains(itemToSell.getId())
                    || !player.isShopping())
                break;
            if (!itemToSell.getDefinition().isStackable()) {
                if (inventorySpace) {
                    super.switchItem(player.getInventory(), id == TRASH_STORE ? null : this, itemToSell.getId(), -1);
                    if (!customShop) {
                        if (ReducedSellPrice.forId(itemToSell.getId()) != null) {
                            player.getInventory().add(new Item(getCurrency().getId(),
                                    ReducedSellPrice.forId(itemToSell.getId()).getSellValue()), false);
                            PlayerLogs.logStores(player.getUsername(), "Player sold to store: " + ShopManager.getShops().get(id).getName()
                                    + ". Item: " + itemToSell.getDefinition().getName() + ", id: " + itemToSell.getId() + ", amount: " + 1 + ", profit: " + itemValue);
                        } else {
                            player.getInventory().add(new Item(getCurrency().getId(), itemValue), false);
                        }
                    } else {
                        // Return points here
                    }
                } else {
                    player.getPacketSender().sendMessage("Please free some inventory space before doing that.");
                    break;
                }
            } else {
                if (inventorySpace) {

                    super.switchItem(player.getInventory(), id == TRASH_STORE ? null : this, itemToSell.getId(), amountToSell);

                    if (!customShop) {
                        if (itemToSell.reducedPrice()) {
                            player.getInventory()
                                    .add(new Item(getCurrency().getId(),
                                                    ReducedSellPrice.forId(itemToSell.getId()).getSellValue() * amountToSell),
                                            false);
                        } else {
                            player.getInventory().add(new Item(getCurrency().getId(), itemValue * amountToSell), false);
                        }
                    } else {
                        // Return points here
                    }
                    break;
                } else {
                    player.getPacketSender().sendMessage("Please free some inventory space before doing that.");
                    break;
                }
            }
            amountToSell--;
        }
        if (customShop) {
            PlayerPanel.refreshPanel(player);
        }
        player.getInventory().refreshItems();
        fireRestockTask();
        refreshItems();
        publicRefresh();
    }

    public static int[] pouch_currency_id = {
            19062,
            11054,
            11056,
            11052,
            3576,
            6466,
            3502
    };

    public static boolean isPouchCurrency(int id) {
        for (Integer i : pouch_currency_id)
            if (i == id)
                return true;
        return false;
    }

    /**
     * Buying an item from a shop
     */
    @Override
    public Shop switchItem(ItemContainer to, Item item, int slot, boolean sort, boolean refresh) {
        Player player = getPlayer();
        if (player == null)
            return this;
        if (!player.isShopping() || player.isBanking()) {
            player.getPacketSender().sendInterfaceRemoval();
            return this;
        }

        if (!shopSellsItem(item))
            return this;

        if (item.getAmount() > getItems()[slot].getAmount())
            item.setAmount(getItems()[slot].getAmount());
        int amountBuying = item.getAmount();

        if (amountBuying <= 0)
            return this;
        if (amountBuying > GameSettings.Shop_Buy_Limit) {
            player.getPacketSender().sendMessage("You can only buy " + GameSettings.Shop_Buy_Limit + " "
                    + ItemDefinition.forId(item.getId()).getName() + "s at a time.");
            return this;
        }

        if (id == COSMETIC_ORDINARY || id == COSMETIC_FABLED || id == COSMETIC_EXOTIC || id == COSMETIC_MYTHIC) {
            if (!getPlayer().getCosmeticShop().hasRequirements(id)) {
                return this;
            }
        }

        boolean customShop = getCurrency().getId() == -1;
        boolean usePouch = false;
        int playerCurrencyAmount = 0;
        int value = ItemDefinition.forId(item.getId()).getValue();
        String currencyName = "";


        if (getCurrency().getId() != -1) {
            playerCurrencyAmount = player.getInventory().getAmount(currency.getId());
            currencyName = ItemDefinition.forId(currency.getId()).getName().toLowerCase();
            if (isPouchCurrency(currency.getId()))
                usePouch = true;
            //SET SHOP ITEM CURRENCY
            if (id == BEGINNER_SLAYER || id == MEDIUM_SLAYER || id == ELITE_SLAYER  || id == SPECTRAL_SLAYER|| id == BEAST_HUNTER || id == NECRO_SHOP || id == MONSTER_ESSENCE_SHOP || id == MINING_SHOP || id == COSMETIC_ORDINARY || id == COSMETIC_FABLED || id == COSMETIC_EXOTIC || id == COSMETIC_MYTHIC
                    || id == IRONMAN_SHOP || id == WOOCUTTING_SHOP || id == MINE_SHOP || id == CHRISTMAS_SHOP|| id == 30 || id == 31 || id == TOWER_SHOP || id == CORRUPT_SHOP|| id == ENCHANTED_SHOP || id == AFK_SHOP) {
                value = (int) ShopManager.getCustomShopData(id, item.getId())[0];
            }
        } else {
            Object[] obj = ShopManager.getCustomShopData(id, item.getId());
            if (obj == null)
                return this;
            value = (int) obj[0];
            currencyName = (String) obj[1];

            //SET SHOP POINTS CURRENCY
            if (id == VOTE_STORE) {
                playerCurrencyAmount = player.getPointsHandler().getVotingPoints();
            } else if (id == DONATOR_STORE_1 || id == DONATOR_STORE_2 || id == DONATOR_STORE_3 || id == DONATOR_STORE_4) {
                playerCurrencyAmount = player.getPointsHandler().getDonatorPoints();
            } else if (id == REFUND_SHOP) {
                playerCurrencyAmount = player.getPointsHandler().getRefundPoints();
            }
        }

        if (player.getLocation() == Location.DUEL_ARENA) {
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null) {
                value -= value / 4;
            }
        }

        if (usePouch) {
            switch (id) {
                case BEGINNER_SLAYER:
                case MEDIUM_SLAYER:
                case ELITE_SLAYER:
                    playerCurrencyAmount = player.getSlayeressence();
                    break;
                case MONSTER_ESSENCE_SHOP:
                case IRONMAN_SHOP:
                    playerCurrencyAmount = player.getMonsteressence();
                    break;
                case BEAST_HUNTER:
                    playerCurrencyAmount = player.getBeastEssence();
                    break;
                case CORRUPT_SHOP:
                    playerCurrencyAmount = player.getCorruptEssence();
                    break;
                case SPECTRAL_SLAYER:
                    playerCurrencyAmount = player.getSpectralEssence();
                    break;
                case ENCHANTED_SHOP:
                    playerCurrencyAmount = player.getEnchantedEssence();
                    break;
            }
        }

        if (value <= 0) {
            return this;
        }
        if (!hasInventorySpace(player, item, getCurrency().getId(), value)) {
            player.getPacketSender().sendMessage("You do not have any free inventory slots.");
            return this;
        }
        if (playerCurrencyAmount <= 0 || playerCurrencyAmount < value) {
            player.getPacketSender().sendMessage("You do not have enough " + ((currencyName.endsWith("s") ? (currencyName) : (currencyName + "s"))) + (usePouch ? " in your essence pouch" : "") + " to purchase this.");
            return this;
        }
        if (id == SKILLCAPE_STORE_1 || id == SKILLCAPE_STORE_2 || id == SKILLCAPE_STORE_3) {
            for (int i = 0; i < item.getDefinition().getRequirement().length; i++) {
                int req = item.getDefinition().getRequirement()[i];
                if ((i == 3 || i == 5) && req == 99)
                    req *= 10;
                if (req > player.getSkillManager().getMaxLevel(i)) {
                    player.getPacketSender().sendMessage("You need to have at least level 99 in "
                            + Misc.formatText(Skill.forId(i).toString().toLowerCase()) + " to buy this item.");
                    return this;
                }
            }
        }

        for (int i = amountBuying; i > 0; i--) {
            if (!shopSellsItem(item)) {
                break;
            }
            if (getItems()[slot].getAmount() <= 1 && id != GENERAL_STORE) {
                player.getPacketSender().sendMessage("The shop has run out of stock for this item.");
                break;
            }
            if (!item.getDefinition().isStackable()) {
                if (playerCurrencyAmount >= value && hasInventorySpace(player, item, getCurrency().getId(), value)) {

                    if (!customShop) {
                        if (usePouch) {
                            switch (id) {
                                case BEGINNER_SLAYER:
                                case MEDIUM_SLAYER:
                                case ELITE_SLAYER:
                                    player.setSlayeressence(player.getSlayeressence() - value);
                                    break;
                                case MONSTER_ESSENCE_SHOP:
                                case IRONMAN_SHOP:
                                    player.setMonsteressence(player.getMonsteressence() - value);
                                    break;
                                case BEAST_HUNTER:
                                    player.setBeastEssence(player.getBeastEssence() - value);
                                    break;
                                case CORRUPT_SHOP:
                                    player.setCorruptEssence(player.getCorruptEssence() - value);
                                    break;
                                case SPECTRAL_SLAYER:
                                    player.setSpectralEssence(player.getSpectralEssence() - value);
                                    break;
                                case ENCHANTED_SHOP:
                                    player.setEnchantedEssence(player.getEnchantedEssence() - value);
                                    break;
                            }
                        } else {
                            player.getInventory().delete(currency.getId(), value, false);
                        }
                    } else {
                        if (id == VOTE_STORE) {
                            player.getPointsHandler().setVotingPoints(-value, true);
                        } else if (id == DONATOR_STORE_1 || id == DONATOR_STORE_2 || id == DONATOR_STORE_3 || id == DONATOR_STORE_4) {
                            player.getPointsHandler().setDonatorPoints(-value, true);
                        } else if (id == REFUND_SHOP) {
                            player.getPointsHandler().setRefundPoints(-value, true);
                        }

                    }

                    super.switchItem(to, new Item(item.getId(), 1), slot, false, false);

                    playerCurrencyAmount -= value;
                } else {
                    break;
                }
            } else {
                if (playerCurrencyAmount >= value && hasInventorySpace(player, item, getCurrency().getId(), value)) {

                    int canBeBought = playerCurrencyAmount / (value);
                    if (canBeBought >= amountBuying) {
                        canBeBought = amountBuying;
                    }
                    if (canBeBought == 0)
                        break;

                    if (!customShop) {
                        if (usePouch) {
                            switch (id) {
                                case BEGINNER_SLAYER:
                                case MEDIUM_SLAYER:
                                case ELITE_SLAYER:
                                    player.setSlayeressence(player.getSlayeressence() - value * canBeBought);
                                    break;
                                case MONSTER_ESSENCE_SHOP:
                                case IRONMAN_SHOP:
                                    player.setMonsteressence(player.getMonsteressence() - value * canBeBought);
                                    break;
                                case BEAST_HUNTER:
                                    player.setBeastEssence(player.getBeastEssence() - value * canBeBought);
                                    break;
                                case CORRUPT_SHOP:
                                    player.setCorruptEssence(player.getCorruptEssence() - value * canBeBought);
                                    break;
                                case SPECTRAL_SLAYER:
                                    player.setSpectralEssence(player.getSpectralEssence() - value * canBeBought);
                                    break;
                                case ENCHANTED_SHOP:
                                    player.setEnchantedEssence(player.getEnchantedEssence() - value * canBeBought);
                                    break;
                            }
                        } else {
                            player.getInventory().delete(currency.getId(), value * canBeBought, false);
                        }
                    } else {

                        if (id == VOTE_STORE) { //
                            player.getPointsHandler().setVotingPoints(-value * canBeBought, true);
                        } else if (id == DONATOR_STORE_1 || id == DONATOR_STORE_2 || id == DONATOR_STORE_3 || id == DONATOR_STORE_4) {
                            player.getPointsHandler().setDonatorPoints(-value * canBeBought, true);
                        } else if (id == REFUND_SHOP) {
                            player.getPointsHandler().setRefundPoints(-value * canBeBought, true);
                        }
                    }
                    super.switchItem(to, new Item(item.getId(), canBeBought), slot, false, false);
                    playerCurrencyAmount -= value;
                    break;
                } else {
                    break;
                }
            }
            amountBuying--;
        }
        if (!customShop) {
        } else {
            PlayerPanel.refreshPanel(player);
        }
        player.getInventory().refreshItems();
        fireRestockTask();
        refreshItems();
        publicRefresh();
        return this;
    }

    @Override
    public Shop add(Item item, boolean refresh) {
        super.add(item, false);
        if (id != RECIPE_FOR_DISASTER_STORE)
            publicRefresh();
        return this;
    }

    @Override
    public int capacity() {
        return 42;
    }

    @Override
    public StackType stackType() {
        return StackType.STACKS;
    }

    @Override
    public Shop refreshItems() {
        if (id == RECIPE_FOR_DISASTER_STORE) {
            RecipeForDisaster.openRFDShop(getPlayer());
            return this;
        }
        for (Player player : World.getPlayers()) {
            if (player == null || !player.isShopping() || player.getShop() == null || player.getShop().id != id)
                continue;

            int scrollAmount = 5 + ((getValidItemsArray().length / 8) * 56);
            if (getValidItemsArray().length % 8 != 0)
                scrollAmount += 56;

            if (scrollAmount < 221) {
                scrollAmount = 221;
            }
            player.getPacketSender().setScrollBar(29995, scrollAmount);

            player.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_INTERFACE_ID);

            player.getPacketSender().sendItemContainer(ShopManager.getShops().get(id), getItemChildId());

            player.getPacketSender().sendString(NAME_INTERFACE_CHILD_ID, name);
            if (player.getInputHandling() == null || !(player.getInputHandling() instanceof EnterAmountToSellToShop
                    || player.getInputHandling() instanceof EnterAmountToBuyFromShop))

                player.getPacketSender().sendInterfaceSet(getInterfaceId(), INVENTORY_INTERFACE_ID - 1);

        }
        return this;
    }

    public int getItemChildId() {
        if (DonatorShop.DonatorShopType.isDonatorStore(id)) {
            return DonatorShop.ITEM_CHILD_ID;
        } else if (CosmeticShop.CosmeticShopTier.isCosmeticShop(id)) {
            return CosmeticShop.ITEM_CHILD_ID;
        }
        return ITEM_CHILD_ID;
    }

    public int getInterfaceId() {
        if (DonatorShop.DonatorShopType.isDonatorStore(id)) {
            return DonatorShop.INTERFACE_ID;
        } else if (CosmeticShop.CosmeticShopTier.isCosmeticShop(id)) {
            return CosmeticShop.INTERFACE_ID;
        }
        return INTERFACE_ID;
    }

    @Override
    public Shop full() {
        getPlayer().getPacketSender().sendMessage("The shop is currently full. Please come back later.");
        return this;
    }

    public String shopPriceEx(int shopPrice) {
        String ShopAdd = "";
        if (shopPrice >= 1000 && shopPrice < 1000000) {
            ShopAdd = " (" + (shopPrice / 1000) + "K)";
        } else if (shopPrice >= 1000000) {
            ShopAdd = " (" + (shopPrice / 1000000) + "M)";
        }

        return ShopAdd;
    }

    private boolean shopSellsItem(Item item) {
        return contains(item.getId());
    }

    public void fireRestockTask() {
        if (isRestockingItems() || fullyRestocked())
            return;
        setRestockingItems(true);
        TaskManager.submit(new ShopRestockTask(this));
    }

    public boolean fullyRestocked() {
        if (id == GENERAL_STORE) {
            return getValidItems().isEmpty();
        } else if (id == RECIPE_FOR_DISASTER_STORE) {
            return true;
        }
        if (getOriginalStock() != null) {
            for (int shopItemIndex = 0; shopItemIndex < getOriginalStock().length; shopItemIndex++) {
                if (getItems()[shopItemIndex].getAmount() != getOriginalStock()[shopItemIndex].getAmount())
                    return false;
            }
        }
        return true;
    }

    public static class ShopManager {

        private static final Map<Integer, Shop> shops = new HashMap<>();
        private static int slot = 0;

        public static Map<Integer, Shop> getShops() {
            return shops;
        }

        public static JsonLoader parseShops() {
            return new JsonLoader("./data/def/json/world_shops.json") {
                @Override
                public void load(JsonObject reader, Gson builder) {
                    int id = reader.get("id").getAsInt();
                    String name = reader.get("name").getAsString();
                    Item[] items = builder.fromJson(reader.get("items").getAsJsonArray(), Item[].class);
                    Item currency = new Item(reader.get("currency").getAsInt());
                    shops.put(id, new Shop(null, id, name, currency, items));
                }
            };
        }

        public static void saveTaxShop() {
            Item[] items = getShops().get(119).getItems();
            Path path = Paths.get("./data/shopdata.txt");
            List<String> lines = new ArrayList<>();
            for (Item item : items) {
                lines.add(item.getId() + ":" + item.getAmount());
            }

            try {
                Files.write(path, lines);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static Object[] getCustomShopData(int shop, int item) {
            if (shop == VOTE_STORE) {
                switch (item) {

                    //CRATES
                    case 10256:
                        return new Object[]{5, "Vote points"};
                    case 10260:
                        return new Object[]{5, "Vote points"};
                    case 10262:
                        return new Object[]{5, "Vote points"};

                    //LAMPS
                    case 20066:
                        return new Object[]{1, "Vote points"};
                    case 20067:
                        return new Object[]{1, "Vote points"};
                    case 20068:
                        return new Object[]{1, "Vote points"};
                    case 20069:
                        return new Object[]{1, "Vote points"};
                    case 20071:
                        return new Object[]{1, "Vote points"};
                    case 10946:
                        return new Object[]{20, "Vote points"};
                    case 23057:
                        return new Object[]{35, "Vote points"};
                    case 20031:
                        return new Object[]{25, "Vote points"};
                    case 20036:
                        return new Object[]{25, "Vote points"};
                    case 20041:
                        return new Object[]{25, "Vote points"};
                    case 15666:
                        return new Object[]{4, "Vote points"};
                    case 23171:
                        return new Object[]{16, "Vote points"};
                    case 23172:
                        return new Object[]{50, "Vote points"};
                    case 3578:
                        return new Object[]{100, "Vote points"};
                    case 1453:
                        return new Object[]{40, "Vote points"};
                    case 1448:
                        return new Object[]{5, "Vote points"};
                    case 10945:
                        return new Object[]{5, "Vote points"};
                    case 1446:
                        return new Object[]{7, "Vote points"};
                    case 15669:
                        return new Object[]{15, "Vote points"};


                }

            }
            else if (shop == REFUND_SHOP) {
                switch (item) {
                    case 17129://ELE CACHE
                        return new Object[]{50, "Refund Points"};
                    case 3578://OWNER GOODIE
                        return new Object[]{75, "Refund Points"};
                    case 10945://$1 BOND
                        return new Object[]{4, "Refund Points"};
                    case 460://B CASE
                        return new Object[]{175, "Refund Points"};
                    case 461://SILVER CASE
                        return new Object[]{275, "Refund Points"};
                    case 463://T1 BPASS
                        return new Object[]{200, "Refund Points"};
                    case 464://T2 BPASS
                        return new Object[]{500, "Refund Points"};
                    case 5585://RIFT KEY
                        return new Object[]{10, "Refund Points"};
                    case 16416:
                        return new Object[]{200, "Refund Points"};
                    case 16417:
                        return new Object[]{400, "Refund Points"};
                    case 19944:
                        return new Object[]{1000, "Refund Points"};
                    case 2706:
                        return new Object[]{25, "Refund Points"};

                }
            }
            else if (shop == BEGINNER_SLAYER) {
                switch (item) {
                    case 12834:
                    case 416:
                    case 401:
                    case 402:
                    case 403:
                    case 404:
                    case 405:
                        return new Object[]{7500, "Slayer Essence"};
                    case 12835:
                        return new Object[]{7500, "Slayer Essence"};
                    case 12836:
                        return new Object[]{7500, "Slayer Essence"};
                    case 17127:
                        return new Object[]{20, "Slayer Essence"};
                    case 10260:
                        return new Object[]{1000, "Slayer Essence"};
                    case 10256:
                        return new Object[]{1000, "Slayer Essence"};
                    case 10262:
                        return new Object[]{1000, "Slayer Essence"};
                    case 23171:
                        return new Object[]{1500, "Slayer Essence"};
                    case 3580:
                        return new Object[]{200, "Slayer Essence"};
                }
            }
            else if (shop == MEDIUM_SLAYER) {
                switch (item) {
                    case 7236:
                        return new Object[]{12500, "Slayer Essence"};
                    case 7241:
                        return new Object[]{12500, "Slayer Essence"};
                    case 7238:
                        return new Object[]{12500, "Slayer Essence"};
                    case 7245:
                        return new Object[]{12500, "Slayer Essence"};
                    case 7249:
                        return new Object[]{12500, "Slayer Essence"};
                    case 7247:
                        return new Object[]{12500, "Slayer Essence"};
                    case 17584:
                        return new Object[]{250, "Slayer Essence"};
                    case 17582:
                        return new Object[]{250, "Slayer Essence"};
                    case 17586:
                        return new Object[]{250, "Slayer Essence"};
                    case 17128:
                        return new Object[]{30, "Slayer Essence"};
                    case 9719:
                        return new Object[]{40, "Slayer Essence"};
                    case 13022:
                        return new Object[]{12500, "Slayer Essence"};
                    case 15485:
                        return new Object[]{12500, "Slayer Essence"};
                    case 13035:
                        return new Object[]{12500, "Slayer Essence"};


                }
            }
            else if (shop == ELITE_SLAYER) {
                switch (item) {

                    case 10945:
                        return new Object[]{1500, "Slayer Essence"};
                    case 17127:
                        return new Object[]{10, "Slayer Essence"};
                    case 17128:
                        return new Object[]{20, "Slayer Essence"};
                    case 20068:
                        return new Object[]{40, "Slayer Essence"};
                    case 1306:
                        return new Object[]{500, "Slayer Essence"};
                    case 1307:
                        return new Object[]{850, "Slayer Essence"};
                    case 1303:
                        return new Object[]{500, "Slayer Essence"};
                    case 17584:
                        return new Object[]{175, "Slayer Essence"};
                    case 17582:
                        return new Object[]{175, "Slayer Essence"};
                    case 17586:
                        return new Object[]{175, "Slayer Essence"};
                    case 15670:
                        return new Object[]{4000, "Slayer Essence"};
                    case 3582:
                        return new Object[]{1250, "Slayer Essence"};
                    case 17108:
                        return new Object[]{22500, "Slayer Essence"};
                    case 17109:
                        return new Object[]{22500, "Slayer Essence"};
                    case 17110:
                        return new Object[]{22500, "Slayer Essence"};

                    case 7509:
                        return new Object[]{25000, "Slayer Essence"};
                    case 3590:
                        return new Object[]{50000, "Slayer Essence"};


                }
            }
            else if (shop == SPECTRAL_SLAYER) {
                switch (item) {

                    case 2007:
                        return new Object[]{200000, "Spectral Essence"};
                    case 1323:
                        return new Object[]{250, "Spectral Essence"};
                    case 3578:
                        return new Object[]{4000, "Spectral Essence"};
                    case 15670:
                        return new Object[]{2000, "Spectral Essence"};
                    case 17129:
                        return new Object[]{4250, "Spectral Essence"};
                    case 17130:
                        return new Object[]{3000, "Spectral Essence"};
                    case 19118:
                        return new Object[]{10000, "Spectral Essence"};
                    case 23173:
                        return new Object[]{3000, "Spectral Essence"};
                    case 2689:
                        return new Object[]{500000, "Spectral Essence"};
                    case 2072:
                        return new Object[]{30000, "Spectral Essence"};
                    case 2073:
                        return new Object[]{30000, "Spectral Essence"};
                    case 2074:
                        return new Object[]{30000, "Spectral Essence"};
                }
            }
            else if (shop == BEAST_HUNTER) {
                switch (item) {
                    case 1306:
                        return new Object[]{1000, "Beast Essence"};
                    case 1304:
                        return new Object[]{1000, "Beast Essence"};
                    case 17582:
                        return new Object[]{100, "Beast Essence"};
                    case 17584:
                        return new Object[]{100, "Beast Essence"};
                    case 17586:
                        return new Object[]{100, "Beast Essence"};
                    case 15670:
                        return new Object[]{5000, "Beast Essence"};
                    case 17129:
                        return new Object[]{3500, "Beast Essence"};
                    case 20068:
                        return new Object[]{75, "Beast Essence"};
                    case 15667:
                        return new Object[]{275, "Beast Essence"};
                    case 3580:
                        return new Object[]{350, "Beast Essence"};
                    case 3582:
                        return new Object[]{575, "Beast Essence"};

                }
            }
            else if (shop == MINING_SHOP) {
                switch (item) {
                    case 1446:
                        return new Object[]{200, "Rare Gems"};
                    case 1447:
                        return new Object[]{325, "Rare Gems"};
                    case 1448:
                        return new Object[]{175, "Rare Gems"};
                    case 2702:
                        return new Object[]{55000, "Rare Gems"};
                    case 15667:
                        return new Object[]{125, "Rare Gems"};
                    case 10945:
                        return new Object[]{125, "Rare Gems"};
                }
            }
            else if (shop == CHRISTMAS_SHOP) {
                switch (item) {
                    case 1455:
                    case 1456:
                    case 1457:
                    case 1458:
                    case 1459:
                        return new Object[]{25000, "Christmas Fragments"};
                    case 1453:
                        return new Object[]{2000, "Christmas Fragments"};
                    case 1454:
                        return new Object[]{500, "Christmas Fragments"};
                    case 17490:
                        return new Object[]{25, "Christmas Fragments"};
                }
            }
            else if (shop == MINE_SHOP) {
                switch (item) {
                    case 1441:
                    case 1442:
                    case 1443:
                    case 1444:
                    case 1445:
                        return new Object[]{50, "Crushed Gems"};
                    case 1448:
                        return new Object[]{3, "Crushed Gems"};
                    case 1446:
                        return new Object[]{1, "Crushed Gems"};
                    case 1447:
                        return new Object[]{3, "Crushed Gems"};
                    case 10945:
                        return new Object[]{15, "Crushed Gems"};
                }
            }
            else if (shop == WOOCUTTING_SHOP) {
                switch (item) {
                    case 1436:
                    case 1437:
                    case 1439:
                        return new Object[]{50, "Crushed Nests"};
                    case 1448:
                        return new Object[]{3, "Crushed Nests"};
                    case 1446:
                        return new Object[]{1, "Crushed Nests"};
                    case 1447:
                        return new Object[]{3, "Crushed Nests"};
                    case 10945:
                        return new Object[]{15, "Crushed Nests"};
                }
            }
            else if (shop == NECRO_SHOP) {
                switch (item) {
                    case 6805:
                        return new Object[]{5000, "Necro Points"};
                    case 6806:
                        return new Object[]{5000, "Necro Points"};
                    case 6807:
                        return new Object[]{5000, "Necro Points"};
                    case 20061:
                        return new Object[]{25000, "Necro Points"};
                    case 20062:
                        return new Object[]{75000, "Necro Points"};
                    case 20063:
                        return new Object[]{150000, "Necro Points"};
                    case 20064:
                        return new Object[]{350000, "Necro Points"};
                    case 20065:
                        return new Object[]{50000, "Necro Points"};
                    case 3580:
                        return new Object[]{2500, "Necro Points"};
                    case 3582:
                        return new Object[]{7500, "Necro Points"};

                }
            }
            else if (shop == MONSTER_ESSENCE_SHOP) {
                switch (item) {
                    case 2620:
                        return new Object[]{5000, "Monster Essence"};
                    case 10946:
                        return new Object[]{7500, "Monster Essence"};
                    case 15667:
                        return new Object[]{1500, "Monster Essence"};
                    case 20104:
                        return new Object[]{200, "Monster Essence"};
                    case 20105:
                        return new Object[]{500, "Monster Essence"};
                    case 20106:
                        return new Object[]{800, "Monster Essence"};
                    case 10256:
                    case 10260:
                    case 10262:
                        return new Object[]{1000, "Monster Essence"};
                    case 17490:
                        return new Object[]{1, "Monster Essence"};
                    case 946:
                    case 2347:
                    case 1432:
                    case 1433:
                        return new Object[]{2, "Monster Essence"};
                }
            }

            else if (shop == 30) {
                switch (item) {
                    case 5585:
                        return new Object[]{2, "Global Fragments"};
                    case 10262:
                        return new Object[]{5, "Global Fragments"};
                    case 10260:
                        return new Object[]{5, "Global Fragments"};
                    case 10256:
                        return new Object[]{5, "Global Fragments"};
                    case 1446:
                        return new Object[]{5, "Global Fragments"};
                    case 1448:
                        return new Object[]{5, "Global Fragments"};
                    case 17130:
                        return new Object[]{12, "Global Fragments"};
                    case 10946:
                        return new Object[]{15, "Global Fragments"};
                    case 15670:
                        return new Object[]{20, "Global Fragments"};
                    case 23173:
                        return new Object[]{20, "Global Fragments"};
                    case 17129:
                        return new Object[]{25, "Global Fragments"};
                    case 23172:
                        return new Object[]{55, "Global Fragments"};
                    case 15668:
                        return new Object[]{15, "Global Fragments"};

                    case 3578:
                        return new Object[]{125, "Global Fragments"};
                }
            }

            else if (shop == 31) {
                switch (item) {
                    case 5585:
                        return new Object[]{1, "Stream Points"};
                    case 1448:
                        return new Object[]{2, "Stream Points"};
                    case 10256:
                        return new Object[]{2, "Stream Points"};
                    case 10260:
                        return new Object[]{2, "Stream Points"};
                    case 10262:
                        return new Object[]{2, "Stream Points"};
                    case 1446:
                        return new Object[]{3, "Stream Points"};
                    case 15670:
                        return new Object[]{5, "Stream Points"};
                    case 15668:
                        return new Object[]{5, "Stream Points"};
                    case 10946:
                        return new Object[]{5, "Stream Points"};
                    case 23172:
                        return new Object[]{15, "Stream Points"};

                    case 3578:
                        return new Object[]{75, "Stream Points"};
                }
            }

            else if (shop == TOWER_SHOP) {
                switch (item) {
                    case 5585:
                        return new Object[]{50, "Tower Points"};
                    case 1446:
                        return new Object[]{70, "Tower Points"};
                    case 1447:
                        return new Object[]{100, "Tower Points"};
                    case 1448:
                        return new Object[]{100, "Tower Points"};
                    case 17130:
                        return new Object[]{200, "Tower Points"};
                    case 10946:
                        return new Object[]{200, "Tower Points"};
                    case 15669:
                        return new Object[]{400, "Tower Points"};
                    case 15668:
                        return new Object[]{350, "Tower Points"};
                    case 17129:
                        return new Object[]{300, "Tower Points"};
                    case 23172:
                        return new Object[]{600, "Tower Points"};

                    case 3578:
                        return new Object[]{1000, "Tower Points"};
                    case 19118:
                        return new Object[]{2500, "Tower Points"};

                    case 2000:
                        return new Object[]{1250, "Tower Points"};
                    case 2001:
                        return new Object[]{1500, "Tower Points"};
                    case 2002:
                        return new Object[]{2000, "Tower Points"};
                    case 2003:
                        return new Object[]{1500, "Tower Points"};
                    case 2004:
                        return new Object[]{3000, "Tower Points"};
                    case 2005:
                        return new Object[]{2500, "Tower Points"};
                }
            }
            else if (shop == CORRUPT_SHOP) {
                switch (item) {
                    case 2688:
                        return new Object[]{200000, "Corrupt Essence"};
                    case 5585:
                        return new Object[]{300, "Corrupt Essence"};
                    case 1323:
                        return new Object[]{250, "Corrupt Essence"};
                    case 3590:
                        return new Object[]{15000, "Corrupt Essence"};
                    case 2009:
                        return new Object[]{3000, "Corrupt Essence"};
                    case 19118:
                        return new Object[]{12500, "Corrupt Essence"};
                    case 3578:
                        return new Object[]{3500, "Corrupt Essence"};
                    case 2007:
                        return new Object[]{200000, "Corrupt Essence"};
                    case 2657:
                        return new Object[]{55000, "Corrupt Essence"};
                    case 2658:
                        return new Object[]{55000, "Corrupt Essence"};
                    case 2659:
                        return new Object[]{55000, "Corrupt Essence"};
                }
            }
            else if (shop == ENCHANTED_SHOP) {
                switch (item) {
                    case 1448://POTION PACK
                        return new Object[]{20, "Enchanted Essence"};
                    case 17130 :
                        return new Object[]{50, "Enchanted Essence"};
                    case 23173:
                        return new Object[]{50, "Enchanted Essence"};
                    case 17129:
                        return new Object[]{75, "Enchanted Essence"};
                    case 17819:
                        return new Object[]{85, "Enchanted Essence"};
                }
            }
            else if (shop == AFK_SHOP) {
                switch (item) {
                    case 1448:
                        return new Object[]{2500, "Afk Tickets"};
                    case 15668:
                        return new Object[]{15000, "Afk Tickets"};
                    case 2702:
                        return new Object[]{250000, "Afk Tickets"};
                    case 1446:
                        return new Object[]{2500, "Afk Tickets"};
                    case 2556:
                        return new Object[]{4000, "Afk Tickets"};
                    case 733:
                        return new Object[]{3000, "Afk Tickets"};
                }
            }

            else if (shop == IRONMAN_SHOP) {
                switch (item) {
                    case 17584:
                        return new Object[]{25, "Monster Essence"};
                    case 17582:
                        return new Object[]{25, "Monster Essence"};
                    case 17586:
                        return new Object[]{25, "Monster Essence"};
                    case 2620:
                        return new Object[]{450, "Monster Essence"};
                    case 10946:
                        return new Object[]{350, "Monster Essence"};
                    case 15667:
                        return new Object[]{10, "Monster Essence"};
                    case 20104:
                        return new Object[]{7, "Monster Essence"};
                    case 20105:
                        return new Object[]{15, "Monster Essence"};
                    case 20106:
                        return new Object[]{25, "Monster Essence"};
                    case 10256:
                    case 10260:
                    case 10262:
                        return new Object[]{80, "Monster Essence"};
                    case 17490:
                        return new Object[]{1, "Monster Essence"};
                    case 946:
                    case 2347:
                    case 1432:
                    case 1433:
                        return new Object[]{2, "Monster Essence"};
                }
            }

            else if (shop == TRASH_STORE) {//TRASH TO CASH VALUES
                switch (item) {
                    case 457://MONEY VOUCHER
                        return new Object[]{10_000, "coins"};
                    case 458://BAG OF RICHES
                        return new Object[]{50_000, "coins"};
                    case 700://
                    case 701://
                    case 702://
                        return new Object[]{75000, "coins"};

                    case 20030://GAIAS_BLESSING
                    case 20040://CINDERS_TOUCH
                    case 20035://EBB_FLOW
                        return new Object[]{1000, "coins"};
                    case 20031://SERENITY
                    case 20036://AQUASTRIKE
                    case 20041://EMBERBLAST
                        return new Object[]{8500, "coins"};
                    case 20032://ROCKHEART
                    case 20037://RIPTIDE
                    case 20042://INFERNIFY
                        return new Object[]{12000, "coins"};
                    case 20033://GEOVIGOR
                    case 20038://SEASLICER
                    case 20043://BLAZEUP
                        return new Object[]{15000, "coins"};
                    case 20034://STONEHAVEN
                    case 20039://SWIFTIDE
                    case 20044://INFERNO
                        return new Object[]{25000, "coins"};

                    case 16415://MYSTIC 1 Weapon
                        return new Object[]{2500, "coins"};
                    case 17024://Mystic 1
                    case 17025://Mystic 1
                    case 17026://Mystic 1
                    case 17027://Mystic 1
                    case 17028://Mystic 1
                        return new Object[]{2000, "coins"};
                    case 16416://MYSTIC 2 Weapon
                        return new Object[]{3500, "coins"};
                    case 17029://Mystic 2
                    case 17030://Mystic 2
                    case 17031://Mystic 2
                    case 17032://Mystic 2
                    case 17033://Mystic 2
                        return new Object[]{4500, "coins"};
                    case 16417://MYSTIC 3 Weapon
                        return new Object[]{7500, "coins"};
                    case 17034://Mystic 3
                    case 17035://Mystic 3
                    case 17036://Mystic 3
                    case 17037://Mystic 3
                    case 17038://Mystic 3
                        return new Object[]{8000, "coins"};
                    case 16418://MYSTIC 4 Weapon
                        return new Object[]{15000, "coins"};
                    case 17039://Mystic 4
                    case 17040://Mystic 4
                    case 17041://Mystic 4
                    case 17042://Mystic 4
                    case 17043://Mystic 4
                        return new Object[]{11000, "coins"};
                    case 16419://MYSTIC 5 Weapon
                        return new Object[]{22000, "coins"};
                    case 17044://Mystic 5
                    case 17045://Mystic 5
                    case 17046://Mystic 5
                    case 17047://Mystic 5
                    case 17048://Mystic 5
                        return new Object[]{14000, "coins"};
                    case 16420://MYSTIC 6 Weapon
                        return new Object[]{35000, "coins"};
                    case 17049://Mystic 6
                    case 17050://Mystic 6
                    case 17051://Mystic 6
                    case 17052://Mystic 6
                    case 17053://Mystic 6
                        return new Object[]{20000, "coins"};
                    case 16421://MYSTIC 7 Weapon
                        return new Object[]{50000, "coins"};
                    case 17054://Mystic 7
                    case 17055://Mystic 7
                    case 17056://Mystic 7
                    case 17057://Mystic 7
                    case 17058://Mystic 7
                        return new Object[]{25000, "coins"};
                    case 17101://Elite 1 Weapon
                        return new Object[]{60000, "coins"};
                    case 12300://Elite 1
                    case 12301://Elite 1
                    case 12302://Elite 1
                    case 12303://Elite 1
                    case 12304://Elite 1
                        return new Object[]{35000, "coins"};
                    case 17102://Elite 2 Weapon
                        return new Object[]{70000, "coins"};
                    case 12305://Elite 2
                    case 12306://Elite 2
                    case 12307://Elite 2
                    case 12308://Elite 2
                    case 12309://Elite 2
                        return new Object[]{45000, "coins"};
                    case 17103://Elite 3 Weapon
                        return new Object[]{90000, "coins"};
                    case 12310://Elite 3
                    case 12311://Elite 3
                    case 12312://Elite 3
                    case 12313://Elite 3
                    case 12314://Elite 3
                        return new Object[]{55000, "coins"};
                    case 12839://Gaia crystal(1)
                        return new Object[]{1000, "coins"};
                    case 17003://Gaia crystal(2)
                        return new Object[]{5000, "coins"};
                    case 17004://Gaia crystal(3)
                        return new Object[]{10000, "coins"};
                    case 12841://Aqua crystal(1)
                        return new Object[]{1000, "coins"};
                    case 17007://Aqua crystal(2)
                        return new Object[]{5000, "coins"};
                    case 17008://Aqua crystal(3)
                        return new Object[]{10000, "coins"};
                    case 12840://Lava crystal(1)
                        return new Object[]{1000, "coins"};
                    case 17005://Lava crystal(2)
                        return new Object[]{5000, "coins"};
                    case 17006://Lava crystal(3)
                        return new Object[]{10000, "coins"};
                    case 7245://Ring of Nature
                        return new Object[]{10000, "coins"};
                    case 7249://Ring of Atlantis
                        return new Object[]{10000, "coins"};
                    case 7247://Ring of Hades
                        return new Object[]{10000, "coins"};
                    case 7236://Nature Amulet
                        return new Object[]{10000, "coins"};
                    case 7241://Atlantis Amulet
                        return new Object[]{10000, "coins"};
                    case 7238://Hades Amulet
                        return new Object[]{10000, "coins"};
                    case 22002://Gaia Helmet(1)
                        return new Object[]{5000, "coins"};
                    case 12464://Gaia Helmet(2)
                        return new Object[]{7000, "coins"};
                    case 12465://Gaia Helmet(3)
                        return new Object[]{11000, "coins"};
                    case 22001://Aqua Helmet(1)
                        return new Object[]{5000, "coins"};
                    case 12462://Aqua Helmet(2)
                        return new Object[]{7000, "coins"};
                    case 12463://Aqua Helmet(3)
                        return new Object[]{11000, "coins"};
                    case 22000://Lava Helmet(1)
                        return new Object[]{5000, "coins"};
                    case 12460://Lava Helmet(2)
                        return new Object[]{7000, "coins"};
                    case 12461://Lava Helmet(3)
                        return new Object[]{11000, "coins"};
                    case 20006://Necro Page(1)
                        return new Object[]{1000, "coins"};
                    case 17013://Sharpshooter fragment
                        return new Object[]{5000, "coins"};
                    case 17014://Sorcerer's fragment
                        return new Object[]{5000, "coins"};
                    case 17012://Warrior fragment
                        return new Object[]{5000, "coins"};
                    case 15386://Demonic Horn
                        return new Object[]{5000, "coins"};
                    case 18678://Wolven Fang
                        return new Object[]{5000, "coins"};
                    case 7871://Titanic Bone
                        return new Object[]{5000, "coins"};
                    case 20061://Demonic Unlock
                        return new Object[]{1000, "coins"};
                    case 20062://Ogre Unlock
                        return new Object[]{7000, "coins"};
                    case 20063://Spectral Unlock
                        return new Object[]{12500, "coins"};
                    case 20064://Master Unlock
                        return new Object[]{20000, "coins"};
                    case 17018://Slayer Energy
                        return new Object[]{20000, "coins"};
                    case 17019://Blitz Energy
                        return new Object[]{20000, "coins"};
                    case 17020://Luck Energy
                        return new Object[]{20000, "coins"};
                    case 17021://Soul Energy
                        return new Object[]{20000, "coins"};
                    case 17022://Undead Energy
                        return new Object[]{20000, "coins"};
                    case 441://The Bloodlust
                        return new Object[]{20000, "coins"};
                    case 453://Blightleaf
                        return new Object[]{25000, "coins"};
                    case 452://Faithleaf
                        return new Object[]{25000, "coins"};
                    case 455://Blurite
                        return new Object[]{30000, "coins"};
                    case 454://Dragonite
                        return new Object[]{30000, "coins"};
                    case 2049://Sureshot
                        return new Object[]{30000, "coins"};
                    case 2050://Precision
                        return new Object[]{30000, "coins"};
                    case 2051://Despair
                        return new Object[]{40000, "coins"};
                    case 2052://Doom
                        return new Object[]{40000, "coins"};
                    case 15713://Cape of Atlantis
                        return new Object[]{25000, "coins"};
                    case 15710://Cape of Nature
                        return new Object[]{25000, "coins"};
                    case 15715://Cape of Hades
                        return new Object[]{25000, "coins"};


                }
            }

            else if (shop == DONATOR_STORE_1 || shop == DONATOR_STORE_2 || shop == DONATOR_STORE_3 || shop == DONATOR_STORE_4) {
                return DonatorShop.getPrice(item);
            }
            else if (shop == COSMETIC_ORDINARY || shop == COSMETIC_FABLED || shop == COSMETIC_EXOTIC || shop == COSMETIC_MYTHIC) {
                return CosmeticShop.getPrice(item);
            }
            return null;
        }
    }
}
