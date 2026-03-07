package com.ruse.world.content.skill.skillable.impl;

import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.content.skill.skillable.ItemCreationSkillable;
import com.ruse.world.entity.impl.player.Player;

import java.util.*;

public class Journeymen {


    //FEATHER LOOT TABLE - TYLER
    public static final Item[] skillingLootTable = {
            new Item(20049, 150),
            new Item(1446, 2),
            new Item(1447, 1),
            new Item(10945, 3),
            new Item(10946, 1),
            new Item(1400, 150),
            new Item(1404, 250),
            new Item(1412, 250),
            new Item(1408, 200),
            new Item(1412, 250),
            new Item(1402, 100),
            new Item(1406, 150),
            new Item(1410, 200),
            new Item(1414, 100),
            new Item(1418, 110),
            new Item(1401, 80),
            new Item(1405, 100),
            new Item(1409, 70),
            new Item(1413, 45),
            new Item(1417, 70),
            new Item(995, 3500),
            new Item(15667, 5),
            new Item(1448, 2),
            new Item(17490, 15),




    };

    public static void  rollSkillingLoot(Player player) {

        Item item = skillingLootTable[Misc.inclusiveRandom(skillingLootTable.length - 1)];
        player.performAnimation(new Animation(1818));
        player.performGraphic(new Graphic(2051));
        player.getInventory().add(item);
        player.msgRed("You find a " + item.getDefinition().getName() + " thanks to your lucky Golden Feather!");
    }
    public static boolean smeltBar(Player player) {
        Smithable list = Smithable.bars.get(99999);
        if (list != null) {
            List<Integer> products = new ArrayList<>();
            for (int i = 0; i < list.getSmithable().length; i++) {
                products.add(list.getSmithable()[i].getProduct().getId());
            }
            CreationMenu menu = new CreationMenu("What would you like to make?", products, (itemId, amount) -> {
                for (SmithableItem smithableItem : list.getSmithable()) {
                    if (smithableItem.getProduct().getId() == itemId) {
                        player.getSkillManager().startSkillable(new ItemCreationSkillable(Arrays.asList(smithableItem.getItems()), smithableItem.getProduct(), amount, Optional.of(new AnimationLoop(smithableItem.getAnimation(), 3)), smithableItem.getLevelRequired(), (int) smithableItem.getExperience(), Skill.JOURNEYMAN));

                        //FEATHER HANDLE - TYLER
                        int chanceForRandomItem = Misc.random(0,500);
                        int feather = 2950;

                        if (player.getEquipment().contains(feather)){
                            if (chanceForRandomItem == 0){
                                rollSkillingLoot(player);
                            }
                        }
                    }
                }
            });
            player.getPacketSender().sendCreationMenu(menu);
            return true;
        }
        return false;
    }


    public static boolean smithBar(Player player, int id1, int id2) {
        if (id1 == 6150 || id2 == 6150) {
            int barId = id1 == 6150 ? id2 : id1;
            Smithable list = Smithable.bars.get(barId);
            if (list != null) {
                List<Integer> products = new ArrayList<>();
                for (int i = 0; i < list.getSmithable().length; i++) {
                    products.add(list.getSmithable()[i].getProduct().getId());
                }
                CreationMenu menu = new CreationMenu("What would you like to make?", products, (itemId, amount) -> {
                    for (SmithableItem smithableItem : list.getSmithable()) {
                        if (smithableItem.getProduct().getId() == itemId) {
                            player.getSkillManager().startSkillable(new ItemCreationSkillable(Arrays.asList(smithableItem.getItems()), smithableItem.getProduct(), amount, Optional.of(new AnimationLoop(smithableItem.getAnimation(), 3)), smithableItem.getLevelRequired(), (int) smithableItem.getExperience(), Skill.JOURNEYMAN));

                           //FEATHER HANDLE - TYLER
                            int chanceForRandomItem = Misc.random(0,500);
                            int feather = 2950;

                            if (player.getEquipment().contains(feather)){
                                if (chanceForRandomItem == 0){
                                    rollSkillingLoot(player);
                                }
                            }
                        }
                    }
                });
                player.getPacketSender().sendCreationMenu(menu);
                return true;
            }
        }
        return false;
    }

    public enum Smithable {
        VORPAL(1412,
            new SmithableItem( new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1412), true)}, new Item(1416, 25), 1, 25, new Animation(20898)),
            new SmithableItem( new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1412, 250), true)}, new Item(1420), 35, 6250, new Animation(20898)),
            new SmithableItem( new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1412, 250), true)}, new Item(1424), 35, 6250, new Animation(20898))),
        BLOODSTAINED(1413,
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1413), true)},new Item(1417, 25), 35, 62.5, new Animation(20898)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1413, 250), true)},new Item(1421), 75, 15625, new Animation(20898)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1413, 250), true)},new Item(1425), 75, 15625, new Animation(20898))),
        SYMBIOTIC(1414,
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1414), true)},new Item(1418, 25), 75, 125, new Animation(20898)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1414, 250), true)},new Item(1422), 90, 31250, new Animation(20898)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1414, 250), true)},new Item(1426), 90, 31250, new Animation(20898))),
        NETHER(1415,
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1415), true)},new Item(1419, 25), 95, 250, new Animation(20898)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1415, 250), true)},new Item(1423), 110, 62500, new Animation(20898)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(2347), false), new RequiredItem(new Item(1415, 250), true)},new Item(1427), 110, 62500, new Animation(20898))),
        BARS(99999,
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(1408), true)},new Item(1412,10), 1, 25, new Animation(20896)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(1409), true)},new Item(1413,10), 35, 62.5, new Animation(20896)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(1410), true)},new Item(1414,10), 75, 125, new Animation(20896)),
            new SmithableItem(new RequiredItem[]{new RequiredItem(new Item(1411), true)},new Item(1415,10), 95, 250, new Animation(20896))
        );

        static Map<Integer, Smithable> bars = new HashMap<Integer, Smithable>();

        static {
            for (Smithable l : Smithable.values()) {
                bars.put(l.getBarId(), l);
            }
        }

        private final int barId;
        private final SmithableItem[] smithable;

        Smithable(int barId, SmithableItem... smithable) {
            this.barId = barId;
            this.smithable = smithable;
        }

        public int getBarId() {
            return barId;
        }

        public SmithableItem[] getSmithable() {
            return smithable;
        }

    }

    public static final class SmithableItem {

        private final RequiredItem[] item;
        private final Item product;
        private final int levelRequired;
        private final double experience;
        private final Animation animation;

        public SmithableItem(RequiredItem[] item, Item product, int levelRequired, double experience, Animation animation) {
            this.item = item;
            this.product = product;
            this.levelRequired = levelRequired;
            this.experience = experience;
            this.animation = animation;
        }

        public Item getProduct() {
            return product;
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        public double getExperience() {
            return experience;
        }

        public Animation getAnimation() {
            return animation;
        }

        public RequiredItem[] getItems() {
            return item;
        }
    }

    public static boolean fletchLog(Player player, int itemUsed, int itemUsedWith) {
        if (itemUsed == 946 || itemUsedWith == 946) {
            int logId = itemUsed == 946 ? itemUsedWith : itemUsed;
            FletchableLog list = FletchableLog.logs.get(logId);
            if (list != null) {
                List<Integer> products = new ArrayList<>();
                for (int i = 0; i < list.getFletchable().length; i++) {
                    products.add(list.getFletchable()[i].getProduct().getId());
                }
                CreationMenu menu = new CreationMenu("What would you like to make?", products, (itemId, amount) -> {
                    for (FletchableItem fl : list.getFletchable()) {
                        if (fl.getProduct().getId() == itemId) {
                            player.getSkillManager().startSkillable(new ItemCreationSkillable(Arrays.asList(new RequiredItem(new Item(946), false), new RequiredItem(new Item(list.getLogId()), true)), fl.getProduct(), amount, Optional.of(new AnimationLoop(fl.getAnimation(), 3)), fl.getLevelRequired(), (int) fl.getExperience(), Skill.JOURNEYMAN));

                            //FEATHER HANDLE - TYLER
                            int chanceForRandomItem = Misc.random(0,1000);
                            int feather = 2950;

                            if (player.getEquipment().contains(feather)){
                                if (chanceForRandomItem == 0){
                                    rollSkillingLoot(player);
                                }
                            }
                        }
                    }
                });
                player.getPacketSender().sendCreationMenu(menu);
                return true;
            }
        }
        return false;
    }


    public enum FletchableLog {
        VORPAL(1400,
            new FletchableItem(new Item(1404, 50), 1, 25, new Animation(29010))),
        BLOODSTAINED(1401,
            new FletchableItem(new Item(1405, 50), 35, 62.5, new Animation(29010))),
        SYMBIOTIC(1402,
            new FletchableItem(new Item(1406, 50), 50, 125, new Animation(29010))),
        NETHER(1403,
            new FletchableItem(new Item(1407, 50), 65, 250, new Animation(29010)));

        static Map<Integer, FletchableLog> logs = new HashMap<Integer, FletchableLog>();

        static {
            for (FletchableLog l : FletchableLog.values()) {
                logs.put(l.getLogId(), l);
            }
        }

        private final int logId;
        private final FletchableItem[] fletchable;

        FletchableLog(int logId, FletchableItem... fletchable) {
            this.logId = logId;
            this.fletchable = fletchable;
        }

        public int getLogId() {
            return logId;
        }

        public FletchableItem[] getFletchable() {
            return fletchable;
        }
    }

    public static final class FletchableItem {
        private final Item product;
        private final int levelRequired;
        private final double experience;
        private final Animation animation;

        public FletchableItem(Item product, int levelRequired, double experience, Animation animation) {
            this.product = product;
            this.levelRequired = levelRequired;
            this.experience = experience;
            this.animation = animation;
        }

        public Item getProduct() {
            return product;
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        public double getExperience() {
            return experience;
        }

        public Animation getAnimation() {
            return animation;
        }
    }

    public static boolean makeArrow(Player player, int itemUsed, int itemUsedWith) {
        int id = 0;
        for (ArrowMaking ar : ArrowMaking.values()) {
            if (ar.getShaftId() == itemUsed && itemUsedWith == ar.getShaftId() + 12)
                id = itemUsed;
            if (ar.getShaftId() == itemUsedWith && itemUsed == ar.getShaftId() + 12)
                id = itemUsedWith;
        }
        if (id == 0)
            return false;
        ArrowMaking list = ArrowMaking.arrows.get(id);
        if (list != null) {
            List<Integer> products = new ArrayList<>();
            for (int i = 0; i < list.getArrowable().length; i++) {
                products.add(list.getArrowable()[i].getProduct().getId());
            }
            CreationMenu menu = new CreationMenu("What would you like to make?", products, (itemId, amount) -> {
                for (ArrowableItem fl : list.getArrowable()) {
                    if (fl.getProduct().getId() == itemId) {
                        player.getSkillManager().startSkillable(new ItemCreationSkillable(Arrays.asList(fl.getItems()), fl.getProduct(), amount, Optional.of(new AnimationLoop(fl.getAnimation(), 3)), fl.getLevelRequired(), (int) fl.getExperience(), Skill.JOURNEYMAN));

                        //FEATHER HANDLE - TYLER
                        int chanceForRandomItem = Misc.random(0,1000);
                        int feather = 2950;

                        if (player.getEquipment().contains(feather)){
                            if (chanceForRandomItem == 0){
                                rollSkillingLoot(player);
                            }
                        }
                    }
                }
            });
            player.getPacketSender().sendCreationMenu(menu);
            return true;
        }
        return false;
    }

    public enum ArrowMaking {
        VORPAL(1404,
            new ArrowableItem(new RequiredItem[]{new RequiredItem(new Item(1404, 10), true), new RequiredItem(new Item(1416, 10), true)}, new Item(1428, 25), 1, 25, new Animation(6702))),
        BLOODSTAINED(1405,
            new ArrowableItem(new RequiredItem[]{new RequiredItem(new Item(1405, 10), true), new RequiredItem(new Item(1417, 10), true)},new Item(1430, 25), 15, 62.5, new Animation(6702))),
        SYMBIOTIC(1406,
            new ArrowableItem(new RequiredItem[]{new RequiredItem(new Item(1406, 10), true), new RequiredItem(new Item(1418, 10), true)},new Item(1429, 25), 30, 125, new Animation(6702))),
        NETHER(1407,
            new ArrowableItem(new RequiredItem[]{new RequiredItem(new Item(1407, 10), true), new RequiredItem(new Item(1419, 10), true)},new Item(1431, 25), 45, 250, new Animation(6702)));

        static Map<Integer, ArrowMaking> arrows = new HashMap<Integer, ArrowMaking>();

        static {
            for (ArrowMaking l : ArrowMaking.values()) {
                arrows.put(l.getShaftId(), l);
            }
        }

        private final int shaftId;
        private final ArrowableItem[] arrowable;

        ArrowMaking(int logId, ArrowableItem... fletchable) {
            this.shaftId = logId;
            this.arrowable = fletchable;
        }

        public int getShaftId() {
            return shaftId;
        }

        public ArrowableItem[] getArrowable() {
            return arrowable;
        }
        
    }
    

    public static final class ArrowableItem {
        private final RequiredItem[] item;
        private final Item product;
        private final int levelRequired;
        private final double experience;
        private final Animation animation;

        public ArrowableItem(RequiredItem[] item, Item product, int levelRequired, double experience, Animation animation) {
            this.item = item;
            this.product = product;
            this.levelRequired = levelRequired;
            this.experience = experience;
            this.animation = animation;
        }

        public Item getProduct() {
            return product;
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        public double getExperience() {
            return experience;
        }

        public Animation getAnimation() {
            return animation;
        }

        public RequiredItem[] getItems() {
            return item;
        }
    }
}
