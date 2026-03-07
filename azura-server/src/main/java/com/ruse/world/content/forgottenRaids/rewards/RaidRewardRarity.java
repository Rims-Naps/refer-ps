package com.ruse.world.content.forgottenRaids.rewards;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RaidRewardRarity {
    COMMON("Common", "0XFFFFFF", 1),
    RARE("Rare", "0XFFFFFF", 1982),//1/150
    EXOTIC("Exotic", "0XFFFFFF", 1996),//1/500
    ;

    private final String name;
    private final String color;
    private final int chance;

}
