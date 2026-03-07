package com.ruse.world.content.tradingpost;


import com.ruse.model.Item;
import lombok.Getter;

import java.util.List;

@Getter
public class PlayerShopSave {

    private final String owner;
    private final long ownerLong;
    private final List<HistoryListing> history;
    private final int offlineUpdates;
    private final int[] prices;
    private final TradingPostCurrency[] currencies;
    private final int[] initialCount;
    private final long[] listingTime;
    private final long[] coffer;
    private final Item[] items;

    public PlayerShopSave(String owner, long ownerLong, List<HistoryListing> history, int offlineUpdates,
                          int[] prices, TradingPostCurrency[] currencies, int[] initialCount, long[] listingTime,
                          long[] coffer, Item[] items) {
        this.owner = owner;
        this.ownerLong = ownerLong;
        this.history = history;
        this.offlineUpdates = offlineUpdates;
        this.prices = prices;
        this.currencies = currencies;
        this.initialCount = initialCount;
        this.listingTime = listingTime;
        this.coffer = coffer;
        this.items = items;
    }

}
