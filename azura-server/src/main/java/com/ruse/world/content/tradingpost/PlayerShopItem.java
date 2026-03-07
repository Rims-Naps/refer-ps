package com.ruse.world.content.tradingpost;


import com.ruse.model.Item;
import lombok.Getter;

@Getter
public class PlayerShopItem extends Item {

    private final int price;
    private final int initialAmount;
    private final long listedTime;
    private final long coffer;
    private final TradingPostCurrency currency;

    public PlayerShopItem(int id, int amount, int price, long listedTime, long coffer, TradingPostCurrency currency) {
        super(id, amount);
        this.price = price;
        this.initialAmount = amount;
        this.listedTime = listedTime;
        this.coffer = coffer;
        this.currency = currency;
    }

}
