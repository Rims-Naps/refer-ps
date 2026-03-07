package com.ruse.world.content.tradingpost;


import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.StringUtils;
import lombok.Getter;

@Getter
public enum TradingPostCurrency {
    COIN(995, "coin", 1.0),
    ;

    private final int itemId;
    private final String shortHand;
    private final double coinsPer;

    TradingPostCurrency(int itemId, String shortHand, double coinsPer) {
        this.itemId = itemId;
        this.shortHand = shortHand;
        this.coinsPer = coinsPer;
    }

    public ItemDefinition getDefinition() {
        return ItemDefinition.forId(itemId);
    }

    /**
     * Returns a pluralized version of the currency item's name.
     *
     * @return the pluralized version of the currency item's name.
     * @see StringUtils#pluralize(String, boolean)
     */
    @Override
    public String toString() {
        return StringUtils.pluralize(getDefinition().getName(), false);
    }
}
