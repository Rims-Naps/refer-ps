package com.ruse.model.definitions;

import com.ruse.engine.GameEngine;
import com.ruse.model.container.impl.Equipment;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.DoubleStream;

/**
 * This file manages every item definition, which includes their name,
 * description, value, skill requirements, etc.
 *
 * @author relex lawl
 */

public class ItemDefinition {

    public boolean allZero() {
        return DoubleStream.of(bonus).allMatch(x -> x == 0);
    }

    public Map<Integer, Integer> getAttackBonuses() {
        Map<Integer, Integer> attackBonuses = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            attackBonuses.put(i, (int) bonus[i]);
        }
        return attackBonuses;
    }

    public Map<Integer, Integer> getDefenceBonuses() {
        Map<Integer, Integer> defenceBonuses = new HashMap<>();
        for (int i = 5; i < 10; i++) {
            defenceBonuses.put(i, (int) bonus[i]);
        }
        return defenceBonuses;
    }

    public Map<Integer, Integer> getOtherBonuses() {
        Map<Integer, Integer> otherBonuses = new HashMap<>();
        otherBonuses.put(14, (int) bonus[14]);
        otherBonuses.put(15, (int) bonus[15]);
        return otherBonuses;
    }

    public int getPrimaryBonusSum(int classIndex, int categoryIndex) {
        switch (classIndex) {
            case 0:
                switch (categoryIndex) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        return getOtherBonuses().getOrDefault(14, 0);
                }
                break;

            case 1:
                switch (categoryIndex) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        return getAttackBonuses().getOrDefault(4, 0);
                }
                break;

            case 2:
                switch (categoryIndex) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        return getAttackBonuses().getOrDefault(3, 0);
                }
                break;
        }
        return 0;

    }

    public static final int BOXBOXES = 6833;

    public static final int MONSTER_ESSENCE = 19062;
    public static final int FIRE_ESSENCE = 11054;
    public static final int WATER_ESSENCE = 11056;
    public static final int EARTH_ESSENCE = 11052;
    public static final int SLAYER_ESSENCE = 3576;


    public static final int SPINNER = 3687;
    public static final int BOND25 = 23058;
    public static final int BOND10 = 23057;
    public static final int OWNERBOX = 23173;
    public static final int COIN_ID = 995;
    public static final int TOKEN_ID = 10835;
  //  public static final int MILL_ID = 23151;

    /**
     * The directory in which item definitions are found.
     */
    private static final String FILE_DIRECTORY = "./data/def/txt/items.txt";
    private static final String NAME_DIRECTORY = "./data/def/txt/itemnames.txt";



    /**
     * The max amount of items that will be loaded.-+
     */
    private static final int MAX_AMOUNT_OF_ITEMS = 24000;

    /**
     * ItemDefinition array containing all items' definition values.
     */
    private static ItemDefinition[] definitions = new ItemDefinition[MAX_AMOUNT_OF_ITEMS];
    /**
     * The id of the item.
     */
    private int id = 0;
    /**
     * The name of the item.
     */
    private String name = "None";
    /**
     * The item's description.
     */
    private String description = "Null";
    /**
     * Flag to check if item is stackable.
     */
    private boolean stackable;
    /**
     * The item's shop value.
     */
    private int value;
    /**
     * Flag that checks if item is noted.
     */
    private boolean noted;
    private boolean isTwoHanded;
    private boolean weapon;

    private boolean earth;
    private boolean fire;
    private boolean water;
    private boolean vod;

    private EquipmentType equipmentType = EquipmentType.WEAPON;
    private double[] bonus = new double[18];
    private int[] requirement = new int[26];
    private int[] criticalchance = new int[1];

    /**
     * Loading all item definitions
     */
    public static void init() {
        ItemDefinition definition = definitions[0];
        try {
            File file = new File(FILE_DIRECTORY);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("inish")) {
                    definitions[definition.id] = definition;
                    continue;
                }
                String[] args = line.split(": ");
                if (args.length <= 1)
                    continue;
                String token = args[0], value = args[1];
                if (line.contains("Bonus[")) {
                    String[] other = line.split("]");
                    int index = Integer.parseInt(line.substring(6, other[0].length()));
                    double bonus = Double.parseDouble(value);
                    definition.bonus[index] = bonus;
                    continue;
                }  /// lol thanks papi <3
                if (line.contains("Requirement[")) {
                    String[] other = line.split("]");
                    int index = Integer.parseInt(line.substring(12, other[0].length()));
                    int requirement = Integer.parseInt(value);
                    definition.requirement[index] = requirement;
                    continue;
                }
                switch (token.toLowerCase()) {
                    case "item id":
                        int id = Integer.parseInt(value);
                        definition = new ItemDefinition();
                        definition.id = id;
                        break;
                    case "name":
                        if (value == null)
                            continue;
                        definition.name = value;
                        break;
                    case "examine":
                        definition.description = value;
                        break;
                    case "value":
                        definition.value = Integer.parseInt(value);
                        break;
                    case "stackable":
                        definition.stackable = Boolean.parseBoolean(value);
                        break;
                    case "noted":
                        definition.noted = Boolean.parseBoolean(value);
                        break;
                    case "double-handed":
                        definition.isTwoHanded = Boolean.parseBoolean(value);
                        break;
                    case "equipment type":
                        definition.equipmentType = EquipmentType.valueOf(value);
                        break;
                    case "is weapon":
                        definition.weapon = Boolean.parseBoolean(value);
                        break;
                    case "fire":
                        definition.fire = Boolean.parseBoolean(value);
                        break;
                    case "water":
                        definition.water = Boolean.parseBoolean(value);
                        break;
                    case "earth":
                        definition.earth = Boolean.parseBoolean(value);
                        break;
                    case "void":
                        definition.vod = Boolean.parseBoolean(value);
                        break;



                }

                ItemDefinition finalDefinition = definition;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            File file = new File(NAME_DIRECTORY);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("inish")) {
                    definitions[definition.id] = definition;
                    continue;
                }

                String[] args = line.split(": ");
                if (args.length <= 1)
                    continue;
                String token = args[0], value = args[1];

                switch (token.toLowerCase()) {
                    case "item id":
                        int id = Integer.parseInt(value);
                        if (id < definitions.length)
                            definition = ItemDefinition.forId(id);
                        break;
                    case "name":
                        if (definition != null)
                            definition.name = value;
                        break;
                }
                definition.isEquitable = definition.equipmentType != EquipmentType.WEAPON || definition.isWeapon();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

     dumpItems();
    }
    
    public boolean isEquitable;
    
    public static void dumpItems() {

        File file = new File("./data/def/itemstats.dat");
        if (file.exists())
           file.delete();

        GameEngine.submit(() -> {
            try {
                FileWriter fw = new FileWriter("./data/def/itemstats.dat", true);
                if (fw != null) {
                    fw.write("[STATS]");
                    fw.write(System.lineSeparator());
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        for (ItemDefinition def : definitions) {
            if (def == null || def.getBonus() == null)
                continue;

            boolean dump = false;
            for (double b : def.getBonus()) {
                if (b > 0) {
                    dump = true;
                    break;
                }
            }

            if (dump) {
                GameEngine.submit(() -> {
                    try {
                        FileWriter fw = new FileWriter("./data/def/itemstats.dat", true);
                        StringBuilder write = new StringBuilder(def.id + " ");
                        for (int i = 0; i < 10; i++) {
                            write.append((int) def.getBonus()[i]).append(" ");
                        }
                        write.append((int) def.getBonus()[14]).append(" ");
                        write.append((int) def.getBonus()[15]).append(" ");
                        write.append((int) def.getBonus()[16]).append(" ");
                        write.append((int) def.getBonus()[17]);

                        if (fw != null) {
                            fw.write(write.toString());
                            fw.write(System.lineSeparator());
                            fw.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }


    public static ItemDefinition[] getDefinitions() {
        return definitions;
    }

    /**
     * Gets the item definition correspondent to the id.
     *
     * @param id The id of the item to fetch definition for.
     * @return definitions[id].
     */
    public static ItemDefinition forId(int id) {
        return (id < 0 || id > definitions.length || definitions[id] == null) ? new ItemDefinition() : definitions[id];
    }

    /**
     * Gets the max amount of items that will be loaded in Niobe.
     *
     * @return The maximum amount of item definitions loaded.
     */
    public static int getMaxAmountOfItems() {
        return MAX_AMOUNT_OF_ITEMS;
    }

    public static int getItemId(String itemName) {
        for (int i = 0; i < MAX_AMOUNT_OF_ITEMS; i++) {
            if (definitions[i] != null) {
                if (definitions[i].getName().equalsIgnoreCase(itemName)) {
                    return definitions[i].getId();
                }
            }
        }
        return -1;
    }

    /**
     * Gets the item's id.
     *
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the item's name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the item's description.
     *
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the item is stackable.
     *
     * @return stackable.
     */
    public boolean isStackable() {
        return noted || stackable;
    }

    /**
     * Gets the item's shop value.
     *
     * @return value.
     */
    public int getValue() {
        return isNoted() ? ItemDefinition.forId(getId() - 1).value : value;
    }

    /**
     * Gets the item's equipment slot index.
     *
     * @return equipmentSlot.
     */
    public int getEquipmentSlot() {
        return equipmentType.slot;
    }

    /**
     * Checks if item is noted.
     *
     * @return noted.
     */
    public boolean isNoted() {
        return noted;
    }

    /**
     * Checks if item is two-handed
     */
    public boolean isTwoHanded() {
        return isTwoHanded;
    }

    public boolean isWeapon() {
        return weapon;
    }
    public boolean isEarth() {return earth;}
    public boolean isWater() {return water;}
    public boolean isFire() {return fire;}

    public boolean isVoid() {return vod;}


    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    /**
     * Checks if item is full body.
     */
    public boolean isFullBody() {
        return equipmentType == EquipmentType.PLATEBODY;
    }

    /**
     * Checks if item is full helm.
     */
    public boolean isFullHelm() {
        return equipmentType == EquipmentType.FULL_HELMET;
    }
    public boolean isFullHelm1() {
        return equipmentType == EquipmentType.FULL_HELMET1;
    }

    public double[] getBonus() {
        return bonus;
    }

    public int[] getRequirement() {
        return requirement;
    }

    public int[] getCriticalchance() {
        return criticalchance;
    }


    @Override
    public String toString() {
        return "[ItemDefinition(" + id + ")] - Name: " + name + "; equipment slot: " + getEquipmentSlot() + "; value: "
                + value + "; stackable ? " + Boolean.toString(stackable) + "; noted ? " + Boolean.toString(noted)
                + "; 2h ? " + isTwoHanded;
    }

    public enum EquipmentType {
        HAT(Equipment.HEAD_SLOT), CAPE(Equipment.CAPE_SLOT), SHIELD(Equipment.SHIELD_SLOT),
        GLOVES(Equipment.HANDS_SLOT), BOOTS(Equipment.FEET_SLOT), AMULET(Equipment.AMULET_SLOT),
        RING(Equipment.RING_SLOT), ARROWS(Equipment.AMMUNITION_SLOT), FULL_MASK(Equipment.HEAD_SLOT),
        FULL_HELMET(Equipment.HEAD_SLOT), BODY(Equipment.BODY_SLOT), PLATEBODY(Equipment.BODY_SLOT)
        , FULL_HELMET1(Equipment.HEAD_SLOT),
        LEGS(Equipment.LEG_SLOT), WEAPON(Equipment.WEAPON_SLOT), AURA(Equipment.AURA_SLOT) , WINGS(Equipment.WINGS), ASCEND(Equipment.ASCEND);

        private int slot;

        public int getSlot() {
            return slot;
        }

        private EquipmentType(int slot) {
            this.slot = slot;
        }
    }
}
