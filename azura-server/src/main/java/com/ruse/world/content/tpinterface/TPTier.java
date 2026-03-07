package com.ruse.world.content.tpinterface;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TPTier {

    //ZONES
    TIER_1("Beginner"),
    TIER_2("Intermediate"),
    TIER_3("Elite"),
    TIER_4("Master"),
    TIER_5("Challenger"),
    TIER_6("Demi"),
    TIER_7("Godly"),
    TIER_8("Titan"),


    //BOSSES
    BOSSES("Bosses"),
    MINIGAMES("Minigames"),
    SKILLING("Skilling"),
    DONATOR("Donator"),
    OTHER1(""),
    OTHER2("");
    private final String name;
}
