package com.ruse.world.content.tradingpost;

import com.ruse.model.Item;
import com.ruse.model.container.new_container.NewItemContainer;
import com.ruse.util.StringUtils;
import com.ruse.world.World;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlayerShop {

    private final NewItemContainer container = new NewItemContainer(NewItemContainer.Type.ALWAYS_STACK, 10) {
        @Override
        public void shift() {
            Item[] old = items;
            int[] oldPrices = prices;
            int[] oldInitial = initialCount;
            long[] oldTime = listingTime;
            long[] oldCoffer = coffer;
            TradingPostCurrency[] oldCurrencies = currencies;

            items = new Item[capacity()];
            prices = new int[capacity()];
            initialCount = new int[capacity()];
            coffer = new long[capacity()];
            currencies = new TradingPostCurrency[capacity()];

            int newIndex = 0;
            for(int i = 0; i < items.length; i++) {
                if(old[i] != null) {
                    items[newIndex] = old[i];
                    prices[newIndex] = oldPrices[i];
                    currencies[newIndex] = oldCurrencies[i];
                    initialCount[newIndex] = oldInitial[i];
                    listingTime[newIndex] = oldTime[i];
                    coffer[newIndex] = oldCoffer[i];
                    newIndex++;
                }
            }
            if(isFiringEvents()) {
                fireItemsChanged();
            }
        }
    };

    private final String owner;
    private final long ownerLong;
    private List<HistoryListing> history = new ArrayList<>();
    private int offlineUpdates = 0;
    private int[] prices = new int[10];
    private int[] initialCount = new int[10];
    private long[] listingTime = new long[10];
    private long[] coffer = new long[10];
    private TradingPostCurrency[] currencies = createCurrencies();
    private boolean forceSave = false;

    public PlayerShop(String owner) {
        this.owner = owner;
        this.ownerLong = StringUtils.stringToLong(owner);
    }

    public PlayerShop(PlayerShopSave save) {
        this.owner = save.getOwner();
        this.ownerLong = save.getOwnerLong();
        this.history = save.getHistory();
        this.offlineUpdates = save.getOfflineUpdates();
        this.prices = save.getPrices();
        this.currencies = save.getCurrencies();
        if (currencies == null) {
            currencies = createCurrencies();
        }
        this.initialCount = save.getInitialCount();
        this.listingTime = save.getListingTime();
        this.coffer = save.getCoffer();
        for (int i = 0; i < save.getItems().length; i++) {
            container.set(i, save.getItems()[i]);
        }
    }

    private TradingPostCurrency[] createCurrencies() {
        return new TradingPostCurrency[10];
    }

    public Optional<PlayerShopSave> createSave() {
        if (history.size() != 0 || offlineUpdates != 0 || container.freeSlots() != 10 || forceSave) {
            forceSave = false;
            return Optional.of(new PlayerShopSave(owner, ownerLong, history, offlineUpdates, prices, currencies, initialCount, listingTime, coffer, container.array()));
        } else {
            return Optional.empty();
        }
    }

    public void checkForOfflineUpdates(Player owner) {
        if (offlineUpdates != 0) {
            owner.message("<col=0E6117>" + "Trading Post: Items have sold while you were offline.");
            offlineUpdates = 0;
        }
    }

    public int purchase(Player buying, int slottedItemId, int slot, int amount, long price, TradingPostCurrency currency) {
        if (buying.getInterfaceId() != PlayerShops.SEARCH_RESULTS_INTERFACE_ID) {
            return 0;
        }

        if (slot < 0 || slot >= container.capacity()) {
            return 0;
        }

        Item item = container.get(slot);
        if (item == null || slottedItemId != item.getId() || item.getId() <= 0 || item.getAmount() <= 0) {
            return 0;
        }

        if (price != prices[slot]) {
            buying.message("The price for this item has changed.");
            return 0;
        }

        if (amount <= 0) {
            return 0;
        }

        if (amount > item.getAmount()) {
            amount = item.getAmount();
        }

        final long totalCost = (price) * (amount);
        final long profit = price * amount;

        if (totalCost > Integer.MAX_VALUE) {
            buying.message("Cost is too high.");
            return 0;
        }

        if (buying.getInventory().getAmount(currency.getItemId()) < totalCost) {
            DialogueManager.sendStatement(buying, "You don't have enough " + currency.toString() + " to buy this.");
            return 0;
        }

/*        if (profit + coffer[slot] > (long) Integer.MAX_VALUE * 10) {
            DialogueManager.sendStatement(buying, "The other player's coffer is too full to accept this purchase.");
            return 0;
        }*/

        int itemId = item.getId();
        if (amount > 1) {
            itemId = Item.getNoted(itemId);
        }

        Item invItem = new Item(itemId, amount);
        if (!buying.getInventory().hasRoomFor(invItem)) {
            DialogueManager.sendStatement(buying, "You don't have enough inventory space to buy this.");
            return 0;
        }
        buying.getInventory().delete(currency.getItemId(), (int) totalCost);
        coffer[slot] += profit * currencies[slot].getCoinsPer();
        buying.getInventory().add(invItem);
        if (container.get(slot).getAmount() == amount) {
            container.set(slot, new Item(container.get(slot).getId(), 0));
        } else {
            container.remove(new Item(item.getId(), amount));
            if (container.size() == 0) {
                forceSave = true;
            }
        }
        Optional<Player> owner = getOnlineOwner();
        buying.getPlayerShops().getShop().insertHistory(new HistoryListing(HistoryListing.Type.BOUGHT, getOwner(),
                item.getId(), amount, (int) price, currency,
                System.currentTimeMillis()));
        buying.getPlayerShops().updateHistory();
        insertHistory(new HistoryListing(HistoryListing.Type.SOLD, buying.getUsername(), item.getId(), amount,
                (int) price, currency, System.currentTimeMillis()));
        container.shift();
        if (owner.isPresent()) {
            Player ownerPlayer = owner.get();
            ownerPlayer.getPlayerShops().updateSellingItem(slot);
            ownerPlayer.getPlayerShops().updateCoffer();
            ownerPlayer.getPlayerShops().updateHistory();
        }
        return amount;
    }

    public void insertHistory(HistoryListing entry) {
        if (history.size() < PlayerShops.HISTORY_SIZE) {
            history.add(entry);
        } else {
            long time = -1;
            int index = -1;
            for (int i = 0; i < history.size(); i++) {
                HistoryListing l = history.get(i);
                if (l.getTime() < time) {
                    time = l.getTime();
                    index = i;
                }
            }

            if (index != -1) {
                history.remove(index);
                history.add(entry);
            }
        }

        Collections.sort(history, (o1, o2) -> {
            if (o1 == null && o2 != null)
                return 1;
            if (o2 == null && o1 != null)
                return -1;
            if (o1 == null && o2 == null)
                return 0;
            return o1.getTime() < o2.getTime() ? 1 : -1;
        });

        if (entry.getType() == HistoryListing.Type.SOLD) {
            Optional<Player> owner = getOnlineOwner();
            if (owner.isPresent()) {
                int slot = container.getSlotById(entry.getItemId());
                if (slot != -1 && container.get(slot) != null) {
                    if (container.get(slot).getAmount() == 0) {
                        owner.get().message("<col=0E6117>" + "Trading Post: Finished selling " + initialCount[slot] + " x " + container.get(slot).getDefinition().getName() + ".");
                    }
                }
            } else {
                offlineUpdates++;
            }
        }
    }

    public boolean add(PlayerShopItem item) {
        int slot = container.addReturnSlot(item);
        if (slot != -1) {
            prices[slot] = item.getPrice();
            currencies[slot] = item.getCurrency();
            initialCount[slot] = item.getInitialAmount();
            listingTime[slot] = item.getListedTime();
            coffer[slot] = item.getCoffer();
        }
        return slot != -1;
    }

    public void remove(int slot) {
        prices[slot] = 0;
        currencies[slot] = null;
        initialCount[slot] = 0;
        listingTime[slot] = 0;
        container.set(slot, null);
        container.shift();
        if (container.size() == 0) {
            forceSave = true;
        }
    }

    private Optional<Player> getOnlineOwner() {
        return Optional.ofNullable(World.getPlayerByLong(ownerLong));
    }
    
    public long claim(long maxCoins) {
        long coinsClaimed = 0;
        for (int i = 0; i < coffer.length; i++) {
            if (coffer[i] > 0) {
                long toRemove = Math.min(coffer[i], maxCoins);
                coffer[i] -= toRemove;
                maxCoins -= toRemove;
                coinsClaimed += toRemove;

                if (container.get(i) != null && container.get(i).getAmount() == 0 && coffer[i] == 0) {
                    remove(i);
                    i = -1;
                }

                if (maxCoins <= 0) {
                    break;
                }
            }
        }
        return coinsClaimed;
    }

    public Item[] getVisualItemList() {
        Item[] items = new Item[container.capacity()];
        for (int i = 0; i < container.capacity(); i++) {
            if (container.get(i) != null) {
                items[i] = new Item(container.get(i).getId());
            }
        }
        return items;
    }

    public long getCofferAmount() {
        long total = 0;
        for (long l : coffer) {
            total += l;
        }
        return total;
    }

    public String getOwner() {
        return owner;
    }

    public long getOwnerLong() {
        return ownerLong;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getInitialCount() {
        return initialCount;
    }

    public long[] getListingTime() {
        return listingTime;
    }

    public long[] getCoffer() {
        return coffer;
    }

    public int getOfferCount() {
        return container.capacity() - container.freeSlots();
    }

    public List<HistoryListing> getHistory() {
        return history;
    }

    public NewItemContainer getContainer() {
        return container;
    }

    public TradingPostCurrency[] getCurrencies() {
        return currencies;
    }
}
