package com.ruse.world.content.raids.invocation;

import lombok.Getter;

public class Invocations {

    public enum TowerInvocations {
        DOUBLE_DOUBLE("Double Double", "Doubles the amount of golems spawned per wave", 2000, 88005, 0.1),
        DOUBLE_TROUBLE("Double Trouble", "Doubles the amount of Elementals spawned per Elemental Wave", 2001,88006, 0.25),
        ELEMENTAL_OVERDRIVE("Elemental Overdrive", "Elementals spawn every round.", 2002,88007, 0.20),
        DEMONIC_AGGRESSION("Demonic Aggression", "Elementals are now aggresive when spawned", 2003,88008, 0.1),
        WARLOCKS_PACT("Warlock's Pact", "Chance to get a Tower Superior instead of an Elemental.", 2004,88009, 0.1),
        MARK_OF_VALOR("Mark of Valor", "Doubles the damage done by all foes encountered in Tower", 2005,88010, 0.35)
        ;


        @Getter
        private String name;

        @Getter
        private String description;

        @Getter
        private int itemId;

        @Getter
        private int interfaceId;

        @Getter
        private double modifier;

        TowerInvocations(String name, String description, int itemId, int interfaceId, double modifier) {
            this.name = name;
            this.description = description;
            this.itemId = itemId;
            this.interfaceId = interfaceId;
            this.modifier = modifier;
        }

    }

}
