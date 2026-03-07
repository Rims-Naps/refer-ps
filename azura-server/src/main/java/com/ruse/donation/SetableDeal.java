package com.ruse.donation;

public class SetableDeal {
    public DEALTYPE dealtype = DEALTYPE.NONE;

    enum DEALTYPE {
        NONE, BOGO, BTGO, SPEND_50, SPEND_75
    }

    public DEALTYPE getDealtype() {
        return dealtype;
    }

    public void setDealtype(DEALTYPE type) {
        dealtype = type;
    }
}
