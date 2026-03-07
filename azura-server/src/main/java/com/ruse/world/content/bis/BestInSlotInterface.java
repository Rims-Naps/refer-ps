package com.ruse.world.content.bis;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.definitions.WeaponInterfaces;
import com.ruse.world.entity.impl.player.Player;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BestInSlotInterface {

    private final Player player;

    public BestInSlotInterface(Player player) {
        this.player = player;
    }

    private final int STARTING_POINT = 27245; // where the client

    private int classIndex = 0;
    private int categoryIndex = 0;
    private int viewedIndex = 0;

    private final int[] categoryItemIds = {19153, 10069, 19142, 6805, 19141, 16419, 23097, 12825, 14914, 7245};

    private static Map<ItemDefinition.EquipmentType, List<ItemDefinition>> items;

    public static void loadAllItems() {
        items = Arrays.stream(ItemDefinition
                        .getDefinitions())
                .filter(Objects::nonNull)
                .collect(groupingBy(ItemDefinition::getEquipmentType));

        //System.out.println("Loaded: " + items.size() + " items");
    }

    private List<ItemDefinition> loadedDefinitions;
    private ItemDefinition selectedDefinition;

    public void open() {

        sendInterfaceData();
        player.getPacketSender().sendInterface(STARTING_POINT);
        loadItems();
    }

    public String getSpeedText() {
        WeaponInterfaces.WeaponInterface weaponInterface = WeaponInterfaces.getById(selectedDefinition.getId());
        int speed = weaponInterface != null ? weaponInterface.getSpeed() : -1;
        switch (speed) {
            case 0:
                return "NONE";
            case 1:
                return "NONE";
            case 2:
                return "NONE";
            case 3:
                return "NONE";
            case 4:
                return "NONE";
            case 5:
                return "NONE";
            case 6:
                return "NONE";
            default:
                return "NONE";
        }
    }


    private void sendInterfaceData() {
        Item[] items = Arrays.stream(categoryItemIds).mapToObj(Item::new).toArray(Item[]::new);
        player.getPacketSender().sendItemContainer2(items, STARTING_POINT + 12);
    }

    private void loadItems() {
        List<ItemDefinition> data;
        if (categoryIndex == 0) {
            // Special case for FULL_HELMET and FULL_MASK
            data = items.entrySet().stream().filter(entry -> entry.getKey() == ItemDefinition.EquipmentType.FULL_HELMET ||
                            entry.getKey() == ItemDefinition.EquipmentType.FULL_MASK ||
                            entry.getKey() == ItemDefinition.EquipmentType.HAT)
                    .flatMap(entry -> entry.getValue().stream())
                    .filter(this::isValid)
                    .filter(def -> !def.isNoted())
                    .sorted(Comparator.<ItemDefinition>comparingInt(def -> def.getPrimaryBonusSum(classIndex, categoryIndex))
                            .reversed()).limit(80).collect(toList());
        } else {
            // Regular case for other equipment types
            ItemDefinition.EquipmentType type = getEquipmentType();
            data = items.get(type)
                    .stream()
                    .filter(this::isValid)
                    .filter(def -> !def.isNoted())
                    .sorted(Comparator.<ItemDefinition>comparingInt(def -> def.getPrimaryBonusSum(classIndex, categoryIndex))
                            .reversed()).limit(80).collect(toList());
        }

        int[] ids = data.stream()
                .mapToInt(ItemDefinition::getId)
                .toArray();
        loadedDefinitions = data;
        List<String> strings = new ArrayList<>();
        IntStream.range(0, data.size()).forEach(i -> {
            strings.add(data.get(i).getName());
        });

        int textIndex = STARTING_POINT + 164;
        for(int i = textIndex; i < textIndex + 150; i++) {
            player.getPacketSender().sendString(i, "");
        }

        Item[] items = Arrays.stream(ids).mapToObj(Item::new).toArray(Item[]::new);
        player.getPacketSender().sendItemContainer2(items, STARTING_POINT + 314);
        for(int i = 0; i < strings.size(); i++) {
            player.getPacketSender().sendString(textIndex + i, strings.get(i));
        }

        player.getPacketSender().setScrollBar(STARTING_POINT  + 13, (strings.size() * 42) + 2);
        viewedIndex = 0;
        player.getPacketSender().sendToggle(3200, 0);
        handleItemSelection();
    }



    private boolean isValid(ItemDefinition definition) {
        boolean isSpectral = definition.getName().toLowerCase().contains("spectral");
        boolean ignoreMystic1 = definition.getName().toLowerCase().contains("(1)")
                && !definition.getName().contains("Mystic")
                && !definition.getName().contains("Void Helmet")
                && !definition.getName().contains("Lava Helmet")
                && !definition.getName().contains("Void")
                && !definition.getName().contains("Aqua Helmet")
                && !definition.getName().contains("Gaia Helmet")
                && !isSpectral; // Allow "spectral hood(1)"

        boolean ignoreMystic2 = definition.getName().toLowerCase().contains("(2)")
                && !definition.getName().contains("Mystic")
                && !definition.getName().contains("Void Helmet")
                && !definition.getName().contains("Lava Helmet")
                && !definition.getName().contains("Void")
                && !definition.getName().contains("Aqua Helmet")
                && !definition.getName().contains("Gaia Helmet")
                && !isSpectral; // Allow "spectral hood(2)"

        if (!ItemDefinition.forId(definition.getId()).isEarth() && (!ItemDefinition.forId(definition.getId()).isWater()) && (!ItemDefinition.forId(definition.getId()).isFire()) && (!ItemDefinition.forId(definition.getId()).isVoid())){

            return false;
        };

        if (ignoreMystic1 || ignoreMystic2)
            return false;
        if(definition.allZero()) {
            return false;
        }
        boolean valid;
        switch (classIndex) {
            case 0: valid = definition.getOtherBonuses().get(14) != 0; break;
            case 1: valid = definition.getAttackBonuses().get(4) != 0; break;
            case 2: valid = definition.getAttackBonuses().get(3) != 0; break;
            default: throw new IllegalStateException("Unexpected value: " + classIndex);
        };
        return valid;
    }

    private void handleItemSelection() {
        if (loadedDefinitions.size() > viewedIndex) {
            selectedDefinition = loadedDefinitions.get(viewedIndex);
            sendStats();
        }
    }

    private final String[] BONUS_STRINGS = {"Attack bonus", "Stab: +0", "Slash: +0", "Crush: +0", "Magic: +0", "Range: +0", "Defence bonus", "Stab: +0", "Slash: +0", "Crush: +0", "Magic: +0", "Range: +0", "Other bonus", "Melee strength: +0", "Ranged strength: +0", "Magic damage: +0.0%", "Prayer: +0"};

    private void sendStats() {
        int start = STARTING_POINT + 315;
        int attackOffset = 27561;
        int defenceOffset = 27567;
        int otherOffset = 27573;
        int index;
        String[] mainNames = {"Stab: ", "Slash: ", "Crush: ", "Magic: ", "Range: "};
        String[] otherNames = {"Melee strength: ", "Ranged strength: ", "Magic damage: ", "Prayer: "};

        Map<Integer, String> stringMap = new HashMap<>();
        for(int i = 0; i < BONUS_STRINGS.length; i++) {
            player.getPacketSender().sendString(start + i, BONUS_STRINGS[i]);
        }
        for (Map.Entry<Integer, Integer> entry : selectedDefinition.getAttackBonuses()
                .entrySet()) {
            index = entry.getKey();
            stringMap.put(attackOffset + index, mainNames[index] + entry.getValue());
            //System.out.println("Sending attack at " + (attackOffset + index));
        }

        for (Map.Entry<Integer, Integer> entry : selectedDefinition.getDefenceBonuses()
                .entrySet()) {
            index = entry.getKey() - 5;
            stringMap.put(defenceOffset + index, mainNames[index] + entry.getValue());
            //System.out.println("Sending def at " + (defenceOffset + index));
        }

        for (Map.Entry<Integer, Integer> entry : selectedDefinition.getOtherBonuses()
                .entrySet()) {
            index = entry.getKey() - 14;
            stringMap.put(otherOffset + index, otherNames[index] + entry.getValue());
            //System.out.println("Sending other at " + (otherOffset + index));
        }

        stringMap.put(otherOffset + 4, "");

        stringMap.forEach((id, str) -> player.getPacketSender().sendString(id, str));
    }

    private ItemDefinition.EquipmentType getEquipmentType() {
        ItemDefinition.EquipmentType type;
        switch (categoryIndex) {
            case 0: type = ItemDefinition.EquipmentType.FULL_HELMET; break;
            case 1: type = ItemDefinition.EquipmentType.PLATEBODY; break;
            case 2: type = ItemDefinition.EquipmentType.LEGS; break;
            case 4: type = ItemDefinition.EquipmentType.BOOTS; break;
            case 3: type = ItemDefinition.EquipmentType.GLOVES; break;
            case 5: type = ItemDefinition.EquipmentType.CAPE; break;
            case 6: type = ItemDefinition.EquipmentType.SHIELD; break;
            case 7: type = ItemDefinition.EquipmentType.WEAPON; break;
            case 8: type = ItemDefinition.EquipmentType.AMULET; break;
            case 9: type = ItemDefinition.EquipmentType.RING; break;
            default: throw new IllegalStateException("Unexpected value: " + categoryIndex);
        }
        return type;
    }

    public boolean handleButtonClick(int buttonId) {
        if (buttonId < 27247 || buttonId > 27584) {
            return false;
        }

        if (buttonId >= 27582) {
            int oldClassIndex = classIndex;
            classIndex = buttonId - 27582;
            if (classIndex != oldClassIndex) {
                loadItems();
            }
            return true;
        }

        if (buttonId <= 27256) {
            int oldCategoryIndex = categoryIndex;
            categoryIndex = buttonId - 27247;
            if (categoryIndex != oldCategoryIndex) {
                loadItems();
            }
            return true;
        }

        if (buttonId >= 27259 && buttonId <= 27408) {
            int oldViewedIndex = viewedIndex;
            viewedIndex = buttonId - 27259;
            if (viewedIndex != oldViewedIndex) {
                handleItemSelection();
            }
        }
        if (buttonId >= 27259 && buttonId <= 27408) {
            int oldViewedIndex = viewedIndex;
            viewedIndex = buttonId - 27259;
            if (viewedIndex != oldViewedIndex) {
                handleItemSelection();
            }
        }

        return true;
    }


}
