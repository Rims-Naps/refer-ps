package com.ruse.world.content.tradingpost;


import com.google.common.base.Preconditions;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.input.Input;
import com.ruse.util.Misc;
import com.ruse.util.StringUtils;
import com.ruse.world.content.AttributeKey;
import com.ruse.world.content.Option;
import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

import java.util.List;
import java.util.function.Consumer;

public class PlayerShops {

    public static final int MAIN_INTERFACE_ID = 109_000;
    public static final int SEARCH_RESULTS_INTERFACE_ID = 110_400;
    public static final int SELECT_SELL_ITEM_INTERFACE_ID = 109_690;
    public static final int SEARCH_FOR_ITEM_INTERFACE_ID = 53_800;
    public static final int MAIN_INTERFACE_STATE_CONFIG = 3102;
    public static final int HISTORY_SIZE = 60;
    public static final int TAX_RATE = 4; // Tax 1 Token every 30 Tokens
    public static final ItemDefinition TRADING_POST_CURRENCY = ItemDefinition.forId(995);

    private static final AttributeKey<Boolean> TRADING_CURRENCY_INFORMATION = new AttributeKey<>("trading-post-currency-info");

    public enum InterfaceState { DEFAULT, SELECTING_SELL_ITEM, CONFIGURING_SELL_ITEM }
    public enum SortType { NONE, ASCENDING, DESCENDING}
    
    private final Player player;
    private InterfaceState state = InterfaceState.DEFAULT;
    private List<ExchangeSearchResult> searchResults;
    private Consumer<Player> currentSearch;
    private SortType priceSortType = SortType.NONE;
    private SortType nameSortType = SortType.NONE;
    private int sellingSlot = -1;
    private int sellingId = -1;
    private int sellingAmount = -1;
    private int sellingPrice = -1;
    private TradingPostCurrency currency = TradingPostCurrency.COIN;

    public PlayerShops(Player player) {
        this.player = player;
    }

    public void onPlayerLogin() {
        PlayerShop shop = PlayerShopRepo.get(player.getLongUsername());
        if (shop != null) {
            shop.checkForOfflineUpdates(player);
        }
    }

    public boolean isSearchDelayed() {
        if (System.currentTimeMillis() - player.getAttributes().getLong("last_item_search") < 2000) {
            player.message("Your doing that too often, try again in a few seconds.");
            return true;
        }

        player.getAttributes().setLong("last_item_search", System.currentTimeMillis());
        return false;
    }

    public void openTradingPost() {
        if (player.getGameMode().isIronman()) {
            player.message("You cannot use player owned shops.");
            return;
        }

        updateTradingPostInterface();
        player.message("scrollreset=" + 109_400);
        player.message("scrollreset=" + 109_200);
        player.getPacketSender().sendInterface(MAIN_INTERFACE_ID);
        state = InterfaceState.DEFAULT;
    }

    private void updateTradingPostInterface() {
        PlayerShop shop = getShop();

        // optimized consecutive string updates
        updateHistory();
        player.getPacketSender().sendString(109_006, Misc.formatCoins2(shop.getCofferAmount()) + " " + TradingPostCurrency.COIN.toString());
        player.getPacketSender().sendString(109_015, "(" + shop.getOfferCount() + " / 10)");
        for (int i = 0; i < 10; i++) {
            int itemListingChild = 109_208 + (i * 7);
            if (shop.getContainer().get(i) != null) {
                Item item = shop.getContainer().get(i);
                int price = shop.getPrices()[i];
                int initialCount = shop.getInitialCount()[i];

                player.getPacketSender().sendString(itemListingChild, item.getDefinition().getName());
                player.getPacketSender().sendString(itemListingChild + 1, "@yel@" + Misc.formatCoins3(price));
                player.getPacketSender().sendString(itemListingChild + 5, StringUtils.putCommasInNumber(initialCount - item.getAmount()) + " / " + StringUtils.putCommasInNumber(initialCount));
            } else {
                player.getPacketSender().sendString(itemListingChild, "");
                player.getPacketSender().sendString(itemListingChild + 1, "");
                player.getPacketSender().sendString(itemListingChild + 5, "");
            }
        }

        player.getPacketSender().sendString(109_014, "List item for sale");

        for (int i = 0; i < 10; i++) {
            int percentage = 0;
            if (shop.getContainer().get(i) != null) {
                percentage = 100 - (int) ((((double) shop.getContainer().get(i).getAmount()) / ((double) shop.getInitialCount()[i])) * 100);
                if (percentage >= 100 && shop.getContainer().get(i).getAmount() != 0) {
                    percentage = 99;
                }
            }
            player.getPacketSender().sendInterfaceActive(109210 + (i * 7), shop.getContainer().get(i) != null); // bar backing sprite
            player.getPacketSender().sendInterfaceActive(109210 + 2 + (i * 7), shop.getContainer().get(i) != null); // bar backing sprite 2
            player.getPacketSender().sendConfig(3103 + i, percentage);
        }

        priceSortType = SortType.NONE;
        nameSortType = SortType.NONE;
        player.getPacketSender().sendConfig(3100, 0); // name sort
        player.getPacketSender().sendConfig(3101, 0); // price sort

        int items = shop.getContainer().capacity() - shop.getContainer().freeSlots();
        player.getPacketSender().sendItemContainer2(shop.getVisualItemList(), 109_201);
        player.getPacketSender().sendInterfaceScrollMax(109_200, 200 + (items > 4 ? (items - 4) * 50 : 0));
        player.getPacketSender().sendConfig(MAIN_INTERFACE_STATE_CONFIG, InterfaceState.DEFAULT.ordinal());
        player.getPacketSender().sendInterfaceActive(109_709, false); // Guide button when right-clicking set price
        player.getPacketSender().sendInterfaceActive(109_016, false); // Search item interface disabled
    }

    public void updateHistory() {
        PlayerShop shop = getShop();
        for (int i = 0; i < HISTORY_SIZE; i++) {
            int child = 109_406 + i;
            if (shop.getHistory().size() > i) {
                player.getPacketSender().sendString(child, shop.getHistory().get(i).getFormattedLine());
            } else {
                player.getPacketSender().sendString(child, i == 0 ? "No history." : "");
            }
        }
    }

    public void updateCoffer() {
        PlayerShop shop = getShop();
        player.getPacketSender().sendString(109_006, Misc.formatCoins2(shop.getCofferAmount()) + " " + TradingPostCurrency.COIN.toString());
    }

    public void updateSellingItem(int slot) {
        PlayerShop shop = getShop();
        int itemListingChild = 109_208 + (slot * 7);
        int percentage = 0;
        if (shop.getContainer().get(slot) != null) {
            percentage = 100 - (int) ((((double) shop.getContainer().get(slot).getAmount()) / ((double) shop.getInitialCount()[slot])) * 100);
            if (percentage >= 100 && shop.getContainer().get(slot).getAmount() != 0) {
                percentage = 99;
            }
        }
        if (shop.getContainer().get(slot) != null) {
            Item item = shop.getContainer().get(slot);
            int price = shop.getPrices()[slot];
            int initialCount = shop.getInitialCount()[slot];

            player.getPacketSender().sendString(itemListingChild, item.getDefinition().getName());
            player.getPacketSender().sendString(itemListingChild + 1, "@yel@" + Misc.formatCoins3(price));
            player.getPacketSender().sendString(itemListingChild + 5, StringUtils.putCommasInNumber(initialCount - item.getAmount()) + " / " + StringUtils.putCommasInNumber(initialCount));
        } else {
            player.getPacketSender().sendString(itemListingChild, "");
            player.getPacketSender().sendString(itemListingChild + 1, "");
            player.getPacketSender().sendString(itemListingChild + 5, "");
        }
        player.getPacketSender().sendInterfaceActive(109210 + (slot * 7), shop.getContainer().get(slot) != null); // bar backing sprite
        player.getPacketSender().sendInterfaceActive(109210 + 2 + (slot * 7), shop.getContainer().get(slot) != null); // bar backing sprite 2
        player.getPacketSender().sendConfig(3103 + slot, percentage);
        player.getPacketSender().sendItemContainer2(shop.getVisualItemList(), 109_201);
    }

    private void returnToSearchResult() {
        player.getPacketSender().sendInterface(SEARCH_RESULTS_INTERFACE_ID);
    }

    private void sortByPrice() {
        priceSortType = priceSortType == SortType.NONE || priceSortType == SortType.DESCENDING ? SortType.ASCENDING : SortType.DESCENDING;
        player.getPacketSender().sendConfig(3101, priceSortType == SortType.DESCENDING ? 0 : 1);
        boolean asc = priceSortType == SortType.ASCENDING;
        if (searchResults != null && searchResults.size() > 0) {
            searchResults.sort((a, b) -> Integer.compare((asc ? b : a).getPrice(), (asc ? a : b).getPrice()));
            openSearchResult(null, searchResults);
        }
    }

    private void sortByName() {
        nameSortType = nameSortType == SortType.NONE || nameSortType == SortType.DESCENDING ? SortType.ASCENDING : SortType.DESCENDING;
        player.getPacketSender().sendConfig(3100, nameSortType == SortType.DESCENDING ? 0 : 1);
        boolean asc = nameSortType == SortType.ASCENDING;
        if (searchResults != null && searchResults.size() > 0) {
            searchResults.sort((a, b) -> ItemDefinition.forId(asc ? b.getId() : a.getId()).getName().compareTo((ItemDefinition.forId(asc ? a.getId() : b.getId()).getName())));
            openSearchResult(null, searchResults);
        }
    }

    public void openSearchResult(String header, List<ExchangeSearchResult> searchResults) {
        this.searchResults = searchResults;

        if (header != null) {
            player.getPacketSender().sendString(110_418, "Trading Post: " + "@red@" + header);
        }

        Item[] items = new Item[20];
        for (int i = 0; i < 20; i++) {
            if (i < searchResults.size()) {
                ExchangeSearchResult result = searchResults.get(i);
                TradingPostCurrency currency = result.getCurrency();

                player.getPacketSender().sendString(110_421 + (i * 7), ItemDefinition.forId(result.getId()).getName());



                int price = result.getPrice();
                String priceFormatted = StringUtils.putCommasInNumber(price);
                String currencyFormatted = (currency == TradingPostCurrency.COIN ? "Coins" : "Coin") + (result.getPrice() == 1 ? "" : "s");
                String formatted = priceFormatted + " " + currencyFormatted;

                player.getPacketSender().sendString(110_422 + (i * 7), formatted);
                player.getPacketSender().sendString(110_423+ (i * 7), result.getSellerName());
                items[i] = new Item(result.getId(), result.getAmount());
            } else {
                player.getPacketSender().sendString(110_421 + (i * 7), "");
                player.getPacketSender().sendString(110_422 + (i * 7), "");
                player.getPacketSender().sendString(110_423 + (i * 7), "");
            }
        }

        for (int i = 0; i < 20; i++) {
            player.getPacketSender().sendInterfaceActive(110_426 + (i * 7), items[i] != null);
        }

        player.getPacketSender().sendItemContainer2(items, 110_560);
        player.getPacketSender().sendInterfaceScrollMax(110_419, 212 + (searchResults.size() > 7 ? (searchResults.size() - 7) * 30 : 0));
        if (player.getInterfaceId() != SEARCH_RESULTS_INTERFACE_ID) {
            player.message("scrollreset=" + 110_419);
            player.getPacketSender().sendInterface(SEARCH_RESULTS_INTERFACE_ID);
        }
    }

    public void refreshSearchResults(boolean message) {
        if (currentSearch != null) {
            if (message) {
                player.message("<col=c90000>" + "Refreshing search..");
            }
            currentSearch.accept(player);
        }
    }

    private void returnToMainInterface() {
        player.getPacketSender().sendString(109_014, "List item for sale");
        player.getPacketSender().sendConfig(MAIN_INTERFACE_STATE_CONFIG, InterfaceState.DEFAULT.ordinal());
        player.getPacketSender().sendInterface(MAIN_INTERFACE_ID);
        state = InterfaceState.DEFAULT;
    }

    private void openListItemInterface() {
        player.getPacketSender().sendString(109_014, "@red@Cancel listing");
        player.getPacketSender().sendConfig(MAIN_INTERFACE_STATE_CONFIG, InterfaceState.SELECTING_SELL_ITEM.ordinal());
        player.getPacketSender().sendItemContainer2(player.getInventory().getItems(), SELECT_SELL_ITEM_INTERFACE_ID + 1);
        player.getPacketSender().sendInterfaceSet2(MAIN_INTERFACE_ID, SELECT_SELL_ITEM_INTERFACE_ID);
        state = InterfaceState.SELECTING_SELL_ITEM;
    }

    private void returnToConfigureSellItemInterface() {
        player.getPacketSender().sendConfig(MAIN_INTERFACE_STATE_CONFIG, InterfaceState.CONFIGURING_SELL_ITEM.ordinal());
        player.getPacketSender().sendInterface(MAIN_INTERFACE_ID);
    }

    private void openConfigureSellItemInterface() {
        Preconditions.checkArgument(sellingSlot != -1, "No sell item");

        int price = sellingPrice;
        int buyerPays = sellingPrice;
        ItemDefinition itemDefinition = ItemDefinition.forId(sellingId);
        int itemId = Item.getUnNoted(sellingId);

        player.getPacketSender().sendString(109_706, "Your price:\\n" + "@yel@" + Misc.formatCoins3(price));
        player.getPacketSender().sendString(109_703, ItemDefinition.forId(sellingId).getName());
        player.getPacketSender().sendString(109_704, "Buyer pays: " + "@yel@" + Misc.formatCoins3(buyerPays) + " " + currency.toString());
        player.getPacketSender().sendString(109_705, "Quantity: " + "@yel@" + Misc.formatCoins3(sellingAmount));
        player.getPacketSender().sendString(109_707, "Listings: " + "@yel@" + PlayerShopRepo.getListings(itemId) + "\\n"
                + "Items: " + "@yel@" + Misc.formatCoins3(PlayerShopRepo.getAmount(itemId)) + "\\n"
                + "Median: " + "@yel@" + Misc.formatCoins3(PlayerShopRepo.getMedianPrice(itemId)) + "\\n"
                + "Average: " + "@yel@" + Misc.formatCoins3(PlayerShopRepo.getAveragePrice(itemId)));

        player.getPacketSender().sendItemContainer2(new Item[] {new Item(itemId, sellingAmount)}, 109_702);
        player.getPacketSender().sendConfig(MAIN_INTERFACE_STATE_CONFIG, InterfaceState.CONFIGURING_SELL_ITEM.ordinal());
        player.getPacketSender().sendInterface(MAIN_INTERFACE_ID);
        state = InterfaceState.CONFIGURING_SELL_ITEM;
    }

    private void openSelectSearchItemId() {
        if (player.getInterfaceId() == MAIN_INTERFACE_ID) {
            player.getPacketSender().sendConfig(MAIN_INTERFACE_STATE_CONFIG, InterfaceState.DEFAULT.ordinal());
            state = InterfaceState.DEFAULT;
            player.getPacketSender().sendInterfaceSet2(MAIN_INTERFACE_ID, SEARCH_FOR_ITEM_INTERFACE_ID);
        } else if (player.getInterfaceId() == SEARCH_RESULTS_INTERFACE_ID) {
            player.getPacketSender().sendInterfaceSet2(SEARCH_RESULTS_INTERFACE_ID, SEARCH_FOR_ITEM_INTERFACE_ID);
        }
    }

    private boolean canSell(int invSlot) {
        if (invSlot < 0 || invSlot > 27) {
            return false;
        }

        Item inv = player.getInventory().get(invSlot);

        if (inv == null || inv.getId() <= 0 || inv.getAmount() <= 0) {
            return false;
        }

        int id = inv.getId();

        if (inv.getId() == 995 || inv.getId() == currency.getItemId() || !Item.tradeable(inv.getId())) {
            player.message("<col=C90000>" + "You cannot sell this item: " + inv.getDefinition().getName());
            return false;
        }
        return true;
    }

    private void listItem(int slot, int amount, int price) {
        if (player.getInterfaceId() != MAIN_INTERFACE_ID || state != InterfaceState.CONFIGURING_SELL_ITEM) {
            return;
        }

        if (!canSell(slot)) {
            return;
        }

        if (amount <= 0) {
            player.message("Invalid quantity.");
            return;
        }

        if (price <= 0) {
            player.message("Invalid price.");
            return;
        }

        Item inv = player.getInventory().get(slot);

        if (inv == null || inv.getId() <= 0 || inv.getAmount() <= 0) {
            return;
        }

        int invCount = player.getInventory().getAmount(inv.getId());
        if (invCount < amount) {
            amount = invCount;
        }

        inv = new Item(inv.getId(), amount);
        int itemId = inv.getId();

        if (inv.getDefinition().isNoted()) {
            itemId = Item.getUnNoted(inv.getId());
        }

        Item selling = new Item(itemId, amount);

        PlayerShop shop = getShop();
        if (shop.getContainer().hasRoomFor(selling)) {
            if (shop.getContainer().contains(selling.getId())) {
                player.message("@red@You cannot have two listing with the same item.");
                return;
            }
            player.getInventory().delete(inv);
            shop.add(new PlayerShopItem(itemId, selling.getAmount(), price, System.currentTimeMillis(), 0, currency));
            ItemDefinition itemDefinition = ItemDefinition.forId(itemId);
            String itemName = "null";
            if (itemDefinition != null) {
                itemName = itemDefinition.getName();
            }
           DiscordManager.sendMessage("[LISTING] "+player.getName() + " has just listed x"+selling.getAmount()+" "+itemName+" on their POS for "+Misc.ESFormat(price)+" each!", Channels.TRADINGPOST);
            openTradingPost();
        } else {
            DialogueManager.sendStatement(player, "You don't have any free trading post slots.");
        }
    }

    private void openConfigureItemSellAmount() {
        player.getPacketSender().sendEnterAmountPrompt("How many would you like to sell?");
        player.setInputHandling(new Input() {
            @Override
            public void handleAmount(Player player, int amount) {
                if (state != InterfaceState.DEFAULT) {
                    int count = player.getInventory().getAmount(sellingId);
                    if (count == 0 || amount == 0)
                        return;
                    if (amount > count)
                        amount = count;
                    sellingAmount = amount;
                    openConfigureSellItemInterface();
                }
            }
        });
    }

    private void addToSellAmount(int amount) {
        sellingAmount += amount;
        int invCount = player.getInventory().getAmount(sellingId);
        if (sellingAmount > invCount) {
            sellingAmount = invCount;
        }
        openConfigureSellItemInterface();
    }

    public PlayerShop getShop() {
        return PlayerShopRepo.getOrRegister(player.getUsername());
    }

    public void setCurrentSearch(Consumer<Player> currentSearch) {
        this.currentSearch = currentSearch;
    }

    public boolean itemActionPacket(final Option option, int interfaceId, final int slot) {
        interfaceId = interfaceId & 0xFFFF;
        if (interfaceId == 44155 && state == InterfaceState.SELECTING_SELL_ITEM) { // Select item to sell container
            int amount = option == Option.ONE ? 1 : option == Option.TWO ? 5 : option == Option.THREE ? 10 : option == Option.FOUR ? 0 : -1;
            if (amount == -1)
                return true;
            if (slot < 0 || slot >= 28)
                return true;
            Item item = player.getInventory().get(slot);
            if (item == null || item.getId() <= -1 || item.getAmount() <= 0)
                return true;

            PlayerShop shop = getShop();
            if (shop.getContainer().contains(item.getId())) {
                player.message("@red@You cannot have two listing with the same item.");
                return true;
            } else if (!canSell(slot)) {
                return true;
            }

            sellingId = item.getId();
            sellingSlot = slot;
            sellingPrice = Math.max(1, item.getDefinition() != null ? (item.getDefinition().getValue() / currency.getDefinition().getValue()) : 0);

            if (amount == 0) {
                sellingAmount = -1;
                openConfigureItemSellAmount();
            } else {
                sellingAmount = amount;
                openConfigureSellItemInterface();
            }
        }
        return false;
    }

    private void attemptPurchase(ExchangeSearchResult result, int amount) {
        PlayerShop shop = PlayerShopRepo.get(result.getSellerNameToLong());
        if (shop != null) {

            int bought = shop.purchase(player, result.getId(), result.getSlot(), amount, result.getPrice(), result.getCurrency());
            if (bought != 0) {
                int price = bought * (result.getPrice());
                refreshSearchResults(false);
               DiscordManager.sendMessage("[PURCHASE] "+player.getName() + " has just purchased x"+bought+" "+ItemDefinition.forId(result.getId()).getName()+" from "+shop.getOwner()+" for"+Misc.ESFormat(price)+" each!", Channels.TRADINGPOST);

                DialogueManager.sendStatement(player, String.format("Purchased %d x %s for %d %s.", bought, ItemDefinition.forId(result.getId()).getName(), price, result.getCurrency().toString()));
               // DialogueManager.sendItemStatementDialogue(player, result.getId(), 10, );
            }
        }
    }

    public boolean button(int button) {
        if (player.getInterfaceId() == SEARCH_RESULTS_INTERFACE_ID) {
            int buyOneButton = 110_426;
            int buyXButton = 110_425;
            int buyAllButton = 110_424;

            for (int i = 0; i < 20; i++) {
                if (button == buyOneButton + (i * 7)) { // Buy one
                    if (searchResults.size() > i) {
                        ExchangeSearchResult result = searchResults.get(i);
                        PlayerShop shop = PlayerShopRepo.get(result.getSellerNameToLong());
                        if (shop != null) {
                            Item item = shop.getContainer().get(result.getSlot());
                            if (item != null && item.getAmount() > 0) {

                                }
                                String title = "@red@" + item.getDefinition().getName() + ": "
                                        + "@bla@" + Misc.formatCoins3(result.getPrice()) + " + ";
                                int totalCost = result.getPrice();
                                long priceForAll = (long) totalCost * (long) result.getAmount();

                                String buy1 = "Buy-1 for " + StringUtils.putCommasInNumber(totalCost) + " " + result.getCurrency().toString() + ".";
                                String buyAll = "Buy-all (" + "@red@" + result.getAmount() + "@bla@) for " + "@red@" + StringUtils.putCommasInNumber(priceForAll) + "@bla@ " + result.getCurrency().toString() + ".";
                                String buyX = "Buy-X@bla@ (" + "@red@" + result.getAmount() + "@bla@ available)";
                                new SelectionDialogue(player, title,
                                        new SelectionDialogue.Selection(buy1, 0, (p) -> p.getPlayerShops().attemptPurchase(result, 1)),
                                        new SelectionDialogue.Selection(buyAll, 1, (p) -> p.getPlayerShops().attemptPurchase(result, result.getAmount())),
                                        new SelectionDialogue.Selection(buyX, 2, (p) -> {
                                            player.getPacketSender().sendEnterAmountPrompt("How many would you like to buy?");
                                            player.setInputHandling(new Input() {
                                                public void handleAmount(Player p, int amount) {
                                                    if (amount <= 0)
                                                        return;
                                                    p.getPlayerShops().attemptPurchase(result, amount);
                                                }
                                            });
                                        }),
                                        new SelectionDialogue.Selection("Cancel", 3, (p) -> p.getPacketSender().sendChatboxInterfaceRemoval())
                                ).start();
                            } else {
                                player.message("<col=c90000>" + "Item no longer available.");
                            }
                        
                    }
                    return true;
                } else if (button == buyXButton + (i * 7)) { // Buy X
                    if (searchResults.size() > i) {
                        ExchangeSearchResult result = searchResults.get(i);
                        player.getPacketSender().sendEnterAmountPrompt("How many would you like to buy?");
                        player.setInputHandling(new Input() {
                            public void handleAmount(Player p, int amount) {
                                if (amount <= 0)
                                    return;
                                p.getPlayerShops().attemptPurchase(result, amount);
                            }
                        });
                    }
                    return true;
                } else if (button == buyAllButton + (i * 7)) { // Buy all
                    if (searchResults.size() > i) {
                        final ExchangeSearchResult result = searchResults.get(i);
                        int items = result.getAmount();
                        long totalPrice = items * (result.getPrice());

                        new SelectionDialogue(player,
                                "Buy-all (" + "@red@" + Misc.formatCoins3(items)
                                        + "@bla@" + ") for " + "@red@" + Misc.formatCoins3(totalPrice) + "@bla@" + " " + result.getCurrency().toString(),
                                new SelectionDialogue.Selection("Yes", 0,p -> p.getPlayerShops().attemptPurchase(result, result.getAmount())),
                                new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                        ).start();
                    }
                    return true;
                }
            }

            if (button == 110_402) { // Go back
                returnToMainInterface();
                return true;
            } else if (button == 110_417) { // Sort by price
                sortByPrice();
            } else if (button == 110_416) { // Sort by name
                sortByName();
            } else if (button == 110_403) { // Modify
                new SelectionDialogue(player,"Search",
                        new SelectionDialogue.Selection("View recent", 0, p -> {
                            p.getPacketSender().sendChatboxInterfaceRemoval();
                            setCurrentSearch(p1 ->  PlayerShopRepo.searchForRecent(p1));
                            currentSearch.accept(player);
                        }),
                        //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                        new SelectionDialogue.Selection("Search for an item", 1, p -> openSearchForItemName()),
                        new SelectionDialogue.Selection("Search by player", 2, p ->  openSearchForPlayer()),
                        new SelectionDialogue.Selection("Cancel", 3, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                ).start();
                return true;
            } else if (button == 110_404) { // Refresh
                refreshSearchResults(true);
                return true;
            }
        }

        // Close search item interface
        if (player.getInterfaceId() == MAIN_INTERFACE_ID || player.getInterfaceId() == SEARCH_RESULTS_INTERFACE_ID) {
            if (button == 53_804) {
                if (player.getInterfaceId() == MAIN_INTERFACE_ID) {
                    returnToMainInterface();
                } else if (player.getInterfaceId() == SEARCH_RESULTS_INTERFACE_ID) {
                    returnToSearchResult();
                }
                return true;
            }
        }


        if (player.getInterfaceId() == MAIN_INTERFACE_ID) {
            if (state == InterfaceState.CONFIGURING_SELL_ITEM) {
                if (button == 109_710) { // Set price
                    /*new SelectionDialogue(player,"Select currency type",
                            new SelectionDialogue.Selection("Bitcoins", 0, p -> showItemPriceDialogue(TradingPostCurrency.COIN))
                    ).start();*/
                    showItemPriceDialogue(TradingPostCurrency.COIN);
                    return true;
                } else if (button == 109_709) { // Set guide price
                    return true;
                } else if (button == 109_716) { // Set quantity
                    openConfigureItemSellAmount();
                    return true;
                } else if (button == 109_715) { // Add 10
                    addToSellAmount(10);
                    return true;
                } else if (button == 109_714) { // Add 100
                    addToSellAmount(100);
                    return true;
                } else if (button == 109_713) { // Add 1000
                    addToSellAmount(1000);
                    openConfigureSellItemInterface();
                    return true;
                } else if (button == 109_712) { // Add all
                    addToSellAmount(player.getInventory().getAmount(sellingId));
                    openConfigureSellItemInterface();
                    return true;
                } else  if (button == 109_718) { // Confirm
                    new SelectionDialogue(player,"Confirm listing",
                            new SelectionDialogue.Selection(
                                    "List " + StringUtils.putCommasInNumber(sellingAmount) + " x " + ItemDefinition.forId(sellingId).getName()
                                            + " for " + Misc.formatCoins3(sellingPrice) + " " + currency.toString() + " each"
                                    , 0, (p) -> listItem(sellingSlot, sellingAmount, sellingPrice)),
                            new SelectionDialogue.Selection("Cancel", 1, p -> returnToConfigureSellItemInterface())
                    ).start();
                    return true;
                }
            }

            if (state == InterfaceState.DEFAULT) {
                int cancelButton = 109_207;
                int viewDetailsButton = 109_208;
                for (int i = 0; i < 10; i++) {
                    if (button == cancelButton + (i * 7)) {
                        PlayerShop shop = getShop();
                        if (shop.getContainer().get(i) != null) {
                            if (shop.getCoffer()[i] != 0) {
                                player.message("<col=c90000>" + "You must collect your coffer for this item first.");
                            } else {
                                Item item = shop.getContainer().get(i);
                                if (item.getAmount() > 0) {
                                    Item addingItem = new Item(Item.getNoted(item.getId()), item.getAmount());
                                    if (!player.getInventory().hasRoomFor(addingItem)) {
                                        player.message("You don't have enough inventory space.");
                                        return true;
                                    }
                                    player.getInventory().add(addingItem);
                                    player.message("<col=0E6117>" + "You retrieve: " +
                                            item.getAmount() + " x " + item.getDefinition().getName() + ".");
                                }
                                shop.remove(i);
                             DiscordManager.sendMessage("[REMOVED LISTING] "+player.getName() + " has just removed x"+item.getAmount()+" "+item.getDefinition().getName(), Channels.TRADINGPOST);
                                player.message("<col=c90000>" + "Listing removed: " +
                                        item.getAmount() + " x " + item.getDefinition().getName() + ".");
                                updateTradingPostInterface();
                            }
                        }
                        return true;
                    } else if (button == viewDetailsButton + (i * 7)) {
                        PlayerShop shop = getShop();
                        if (shop.getContainer().get(i) != null) {
                            Item item = shop.getContainer().get(i);
                            player.message("<col=003300>" + StringUtils.putCommasInNumber(shop.getInitialCount()[i]) + " x " + item.getDefinition().getName()
                                    + "</col>.. Sold: " + "<col=003300>" + (shop.getInitialCount()[i] - item.getAmount())
                                    + "</col> @ " + "<col=003300>" + Misc.formatCoins3(shop.getPrices()[i]) + " " + shop.getCurrencies()[i].toString()  + " each.</col>"
                                    + " Unclaimed: " + "<col=003300>" + Misc.formatCoins3(shop.getCoffer()[i]) + " " + shop.getCurrencies()[i] + "."
                            );
                        }
                        return true;
                    }
                }
            }

            switch (button) {
                case 109_005: // Claim
                    PlayerShop shop = getShop();
                    long cofferAmount = shop.getCofferAmount();
                    if (cofferAmount > 0) {
                        int maxTokens = Math.min((int) (cofferAmount / TradingPostCurrency.COIN.getCoinsPer()),
                                Integer.MAX_VALUE - player.getInventory().getAmount(TradingPostCurrency.COIN.getItemId()));
                        Item cofferToken = new Item(TradingPostCurrency.COIN.getItemId(), maxTokens);
                        if (!player.getInventory().hasRoomFor(cofferToken)) {
                            player.message("<col=c90000>" + "You don't have enough free space to collect your coffer.");
                        } else if (maxTokens == 0) {
                            player.message("<col=c90000>" + "You don't have enough currency space to collect your coffer.");
                        } else {
                            long retrieveAmount = shop.claim((long) (maxTokens * TradingPostCurrency.COIN.getCoinsPer()));
                            int retrieveTokens = (int) (retrieveAmount / TradingPostCurrency.COIN.getCoinsPer());
                            player.getInventory().add(new Item(TradingPostCurrency.COIN.getItemId(), retrieveTokens));
                            String message = "<col=003300>" + "You retrieve ";
                            if (retrieveTokens > 0) {
                                message += Misc.formatCoins3(retrieveTokens) + " " + TradingPostCurrency.COIN.toString();
                            }
                            message += " from your coffer";
                            player.message(message);
                            openTradingPost();
                        }
                    } else {
                        player.message("<col=c90000>" + "You do not have any " + TradingPostCurrency.COIN.toString() + " to claim.");
                    }
                    return true;
                case 109_016: // Search for item
                    // openSelectSearchItemId();
                    return true;
                case 109_007: // Search for item name
                    openSearchForItemName();
                    return true;
                case 109_009: // Search for player
                    openSearchForPlayer();
                    return true;
                case 109_011: // Search for recent
                    setCurrentSearch(p ->  PlayerShopRepo.searchForRecent(player));
                    currentSearch.accept(player);
                    return true;
                case 109_013: // List item for sale
                    if (state == InterfaceState.DEFAULT) {
                        openListItemInterface();
                    } else {
                        returnToMainInterface();
                    }
                    return true;
            }
        }
        return false;
    }

    private void showItemPriceDialogue(final TradingPostCurrency currency) {
        this.currency = currency;
        player.getPacketSender().sendEnterAmountPrompt("How many " + currency.toString() + " would you like to sell each item for?");
        player.setInputHandling(new Input() {
            @Override
            public void handleAmount(Player player, int amount) {
                if (state == InterfaceState.CONFIGURING_SELL_ITEM) {
                    if (amount < 0)
                        return;
                    if (amount > Integer.MAX_VALUE) {
                        amount = Integer.MAX_VALUE;
                    }
                    sellingPrice = amount;
                    openConfigureSellItemInterface();
                }
            }
        });
    }

    private void openSearchForPlayer() {
        if (player.getInterfaceId() == MAIN_INTERFACE_ID) {
            returnToMainInterface();
        } else if (player.getInterfaceId() == SEARCH_RESULTS_INTERFACE_ID) {
            returnToSearchResult();
        }
        player.getPacketSender().sendEnterInputPrompt("Enter the name of the player:");
        player.setInputHandling(new Input() {
            public void handleSyntax(Player player, String syntax) {
                if (player.getInterfaceId() == MAIN_INTERFACE_ID || player.getInterfaceId() == SEARCH_RESULTS_INTERFACE_ID) {
                    setCurrentSearch(p ->  PlayerShopRepo.searchForPlayerName(player, syntax));
                    currentSearch.accept(player);
                }
            }
        });
    }

    private void openSearchForItemName() {
        if (player.getInterfaceId() == MAIN_INTERFACE_ID) {
            returnToMainInterface();
        } else if (player.getInterfaceId() == SEARCH_RESULTS_INTERFACE_ID) {
            returnToSearchResult();
        }
        player.getPacketSender().sendEnterInputPrompt("Enter the name of the item:");
        player.setInputHandling(new Input() {
            public void handleSyntax(Player player, String syntax) {
                if (player.getInterfaceId() == MAIN_INTERFACE_ID || player.getInterfaceId() == SEARCH_RESULTS_INTERFACE_ID) {
                    setCurrentSearch(p ->  PlayerShopRepo.searchForItemName(p, syntax));
                    currentSearch.accept(player);
                }
            }
        });
    }
}
