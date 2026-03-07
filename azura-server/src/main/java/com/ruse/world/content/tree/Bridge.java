package com.ruse.world.content.tree;

import com.ruse.model.Item;
import lombok.Getter;

public enum Bridge {
    BRIDGE_ONE(Section.SECTION_1, "Bridge between Paths on the skill tree", new Item(1, 1), 90008, 1679, 1680, 1677, 1678, 90064),
    BRIDGE_TWO(Section.SECTION_1, "Bridge between Paths on the skill tree", new Item(1, 1), 90010, 1679, 1680, 1677, 1678, 90065),
    BRIDGE_THREE(Section.SECTION_2, "Bridge between Paths on the skill tree", new Item(1, 1), 90004, 1679, 1680, 1677, 1678, 90062),
    BRIDGE_FOUR(Section.SECTION_2, "Bridge between Paths on the skill tree", new Item(1, 1), 90006, 1679, 1680, 1677, 1678, 90063);


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

    @Getter
    private final int itemSlotId; //ItemGroupId

    Bridge(Section section, String description, Item requiredItemToUnlock, int componentId, int buttonOnIdUnlocked, int buttonOffIdUnlocked, int buttonOnIdLocked, int buttonOffIdLocked, int itemSlotId) {
        this.section = section;
        this.description = description;
        this.requiredItemToUnlock = requiredItemToUnlock;
        this.componentId = componentId;
        this.buttonOnIdUnlocked = buttonOnIdUnlocked;
        this.buttonOffIdUnlocked = buttonOffIdUnlocked;
        this.buttonOnIdLocked = buttonOnIdLocked;
        this.buttonOffIdLocked = buttonOffIdLocked;
        this.itemSlotId = itemSlotId;
    }
}
