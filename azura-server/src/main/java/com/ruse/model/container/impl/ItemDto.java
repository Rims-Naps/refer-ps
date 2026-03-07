package com.ruse.model.container.impl;

import com.ruse.model.Item;
import lombok.Getter;

public class ItemDto {
    @Getter
    private final int id;
    @Getter
    private final int amount;
    @Getter
    private final int customValue;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.amount = item.getAmount();
        this.customValue = 0;
    }

    public ItemDto(int id, int amount, int value) {
        this.id = id;
        this.amount = amount;
        this.customValue = value;
    }

}
