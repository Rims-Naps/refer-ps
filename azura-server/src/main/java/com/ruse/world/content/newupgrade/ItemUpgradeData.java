package com.ruse.world.content.newupgrade;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ItemUpgradeData {
    TEST1(1044, 1050, 25, 35),
    TEST2(1046, 1055, 15, 60),
    TEST3(1048, 1057, 15, 30);


    private final int requiredId;
    private final int reward;
    private final int bagsRequired;
    private final int chance;

    private static final ItemUpgradeData[] VALUES = values();

    public static int[] getItems() {
        return Arrays.stream(VALUES).mapToInt(ItemUpgradeData::getRequiredId).toArray();
    }

    public static ItemUpgradeData getByIndex(int index) {
        return index >=  VALUES.length ? null : VALUES[index];
    }

    public static ItemUpgradeData getById(int id) {
        return Arrays.stream(VALUES).filter(data -> data.getRequiredId() == id).findFirst().orElse(null);
    }
}
