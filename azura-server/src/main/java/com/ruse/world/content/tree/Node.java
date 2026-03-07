package com.ruse.world.content.tree;

import com.ruse.model.Item;
import lombok.Getter;

public enum Node {
    /**
     * The associated nodes in path 1
     */
    FERAL_FURY(Path.PONTUS, Section.SECTION_1, "5% Increased Damage to Slayer/Beast Tasks", new Item(2617, 5), 90012, 1683, 1684, 1681, 1682),
    BOUNTY_HUNTER(Path.PONTUS, Section.SECTION_1, "Seperate roll for Slayer Keys on Task", new Item(2617, 10), 90014, 1683, 1684, 1681, 1682),
    ESSENCE_REAVER(Path.PONTUS, Section.SECTION_1, "10% Increased Slayer/Beast Essence", new Item(2617, 15), 90016, 1683, 1684, 1681, 1682),
    AQUATIC_SURGE(Path.PONTUS, Section.SECTION_2, "10% Increased Water Damage", new Item(2617, 20), 90018, 1651, 1652, 1650, 1649), //Big Dick Node
    BLOOD_OATH(Path.PONTUS, Section.SECTION_2, "10% Higher Chance for Superiors", new Item(2617, 25), 90020, 1683, 1684, 1681, 1682),
    FERAL_MASTERY(Path.PONTUS, Section.SECTION_2, "Additional 5% Increased Damage to Slayer/Beast Tasks", new Item(2617, 30), 90022, 1683, 1684, 1681, 1682),
    BEAST_CRUSHER(Path.PONTUS, Section.SECTION_2, "Beast Crusher Special attack, takes 100% special. hits 50% of beast mobs HP", new Item(2617, 35), 90024, 1683, 1684, 1681, 1682),
    SORCERERS_VEIL(Path.PONTUS, Section.SECTION_3, "2.5% Increase Magic Damage", new Item(2617, 40), 90026, 1655, 1656, 1653, 1654), //Big Dick node

    /**
     * The associated nodes in path 2
     */
    PERFECTIONIST(Path.HADES, Section.SECTION_1, "2% Maxhit Boost", new Item(2617, 5), 90028, 1683, 1684, 1681, 1682),
    RUTHLESS_WRATH(Path.HADES, Section.SECTION_1, "Unleash Cinders Wrath", new Item(2617, 10), 90030, 1683, 1684, 1681, 1682),
    ALCHEMICAL_MIND(Path.HADES, Section.SECTION_1, "2x Duration of Fury and Rage Potions", new Item(2617, 15), 90032, 1683, 1684, 1681, 1682),
    INFERNAL_BLAZE(Path.HADES, Section.SECTION_2, "10% Increased Fire Damage", new Item(2617, 20), 90034, 1663, 1664, 1661, 1662), //Big Dick Node
    BLADE_DANCER(Path.HADES, Section.SECTION_2, "5% Increase Critical Damage", new Item(2617, 25), 90036, 1683, 1684, 1681, 1682),
    CHAOTIC_RADIANCE(Path.HADES, Section.SECTION_2, "Area of Effect damage is greatly increased", new Item(2617, 30), 90038, 1683, 1684, 1681, 1682),
    MAXIMUM_OVERDRIVE(Path.HADES, Section.SECTION_2, "10% chance to double slayer points", new Item(2617, 35), 90040, 1683, 1684, 1681, 1682),
    BERSERKER(Path.HADES, Section.SECTION_3, "2.5% Increase Melee Damage", new Item(2617, 40), 90042, 1667, 1668, 1665, 1666), //Big Dick node

    /**
     * The associated nodes in path 3
     */
    VERDANT_VITALITY(Path.GAIA, Section.SECTION_1, "2% Droprate Boost", new Item(2617, 5), 90044, 1683, 1684, 1681, 1682),
    DEFT_HANDS(Path.GAIA, Section.SECTION_1, "4% Chance to Save Key on Open", new Item(2617, 10), 90046, 1683, 1684, 1681, 1682),
    HERBALIST(Path.GAIA, Section.SECTION_1, "20% Increase potion duration(Does not affect Fury/Rage)", new Item(2617, 15), 90048, 1683, 1684, 1681, 1682),
    CALL_TO_GAIA(Path.GAIA, Section.SECTION_2, "10% Increased Earth Damage", new Item(2617, 20), 90050, 1671, 1672, 1669, 1670), //Big Dick Node
    GOBLINS_GREED(Path.GAIA, Section.SECTION_2, "Chance for Extra Coins while killing", new Item(2617, 25), 90052, 1683, 1684, 1681, 1682),
    DRUIDIC_ENTANGLEMENT(Path.GAIA, Section.SECTION_2, "Vine Special attack similiar to Emberblast", new Item(2617, 30), 90054, 1683, 1684, 1681, 1682),
    SPIRITUAL_SIPHON(Path.GAIA, Section.SECTION_2, "Heal 1% of Damage Dealt", new Item(2617, 35), 90056, 1683, 1684, 1681, 1682),
    HAWKEYE(Path.GAIA, Section.SECTION_3, "2.5% Increase Range Damage", new Item(2617, 40), 90058, 1675, 1676, 1673, 1674), //Big Dick node
    ;

    @Getter
    private final Path path;

    @Getter
    private final Section section;

    @Getter
    private final Item requiredItemToUnlock;

    @Getter
    private final int componentId; //InterfaceID

    @Getter
    private final int buttonOnIdUnlocked; //Button On Sprite Id - Unlocked;

    @Getter
    private final int buttonOffIdUnlocked; //Button Off Sprite Id - Unlocked;

    @Getter
    private final int buttonOnIdLocked; //Button On Sprite Id - Locked;

    @Getter
    private final int buttonOffIdLocked; //Button Off Sprite Id - Locked;

    @Getter
    private final String description;

    Node(Path path, Section section, String description, Item requiredItemToUnlock, int componentId, int buttonOnIdUnlocked, int buttonOffIdUnlocked, int buttonOnIdLocked, int buttonOffIdLocked) {
        this.path = path;
        this.section = section;
        this.description = description;
        this.requiredItemToUnlock = requiredItemToUnlock;
        this.componentId = componentId;
        this.buttonOnIdUnlocked = buttonOnIdUnlocked;
        this.buttonOffIdUnlocked = buttonOffIdUnlocked;
        this.buttonOnIdLocked = buttonOnIdLocked;
        this.buttonOffIdLocked = buttonOffIdLocked;
    }
}
