package com.ruse.world.content.tradingpost;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.util.StringUtils;
import lombok.Getter;

@Getter
public class HistoryListing {

    public enum Type {
        SOLD, BOUGHT
    }

    private final String username;
    private final int itemId;
    private final int units;
    private final int unitPrice;
    private final TradingPostCurrency currency;
    private final long time;
    private final Type type;

    public HistoryListing(Type type, String username, int itemId, int units, int unitPrice, TradingPostCurrency currency, long time) {
        this.username = username;
        this.itemId = itemId;
        this.units = units;
        this.unitPrice = unitPrice;
        this.currency = currency;
        this.time = time;
        this.type = type;
    }

    public String getFormattedLine() {
        return StringUtils.putCommasInNumber(units) + " x " + ItemDefinition.forId(itemId).getName() + "\\n "
                + (type == Type.SOLD ? "sold to" : "bought from") + " @yel@" + username + "@or1@ for "
                + "@yel@" + Misc.formatCoins3(unitPrice) + " " + currency.getShortHand() + (units > 1 ? " each." : ".");
    }

}
