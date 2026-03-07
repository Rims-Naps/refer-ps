package org.necrotic.client.util;

public class DummyItem {
    public DummyItem(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public DummyItem(int id) {
        this.id = id;
        this.amount = 1;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int amount;
    public int id;
}
