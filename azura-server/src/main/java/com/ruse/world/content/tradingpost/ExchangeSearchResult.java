package com.ruse.world.content.tradingpost;


import com.ruse.util.StringUtils;
import lombok.Getter;

@Getter
public class ExchangeSearchResult {

    private final int id;
    private final int slot;
    private final int price;
    private final int amount;
    private final long time;
    private final String sellerName;
    private final long sellerNameToLong;
    private final TradingPostCurrency currency;

    public ExchangeSearchResult(int id, int slot, int amount, int price, long time, String sellerName, TradingPostCurrency currency) {
        this.id = id;
        this.slot = slot;
        this.amount = amount;
        this.price = price;
        this.sellerName = sellerName;
        this.time = time;
        this.sellerNameToLong = StringUtils.stringToLong(sellerName);
        this.currency = currency;
    }

}
