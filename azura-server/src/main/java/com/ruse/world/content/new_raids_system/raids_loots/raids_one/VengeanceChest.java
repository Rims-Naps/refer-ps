package com.ruse.world.content.new_raids_system.raids_loots.raids_one;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.entity.impl.player.Player;

import static com.ruse.model.container.impl.Equipment.AMMUNITION_SLOT;

public class VengeanceChest {

    public static int raidsKey = 5585;
    public static int[] rareLoots = { 7251,23060,21613, 7308, 7309, 7311, 22111};
    public static int[] ultraLoots = {10228, 22091, 10230};
    public static int[] amazingLoots = {10834, 23173, 23172, 10246, 10946};
    public static int[] commonLoots = {10946, 15359, 10946, 15359, 6183};

    public static int getRandomItem(int[] array) {
        return array[Misc.getRandom(array.length - 1)];
    }

    public static void openChest(Player player) {
        if (!player.getClickDelay().elapsed(1000))
            return;
        if (player.getInventory().contains(raidsKey)) {
            player.getInventory().delete(raidsKey, 1);

            TaskManager.submit(new Task(2, player, false) {

                @Override
                public void execute() {
                    player.performAnimation(new Animation(827));
                    player.performGraphic(new Graphic(1322));
                    player.getPacketSender().sendMessage("@blu@<shad=0>You open the chest...");
                    giveReward(player);
                    this.stop();
                }
            });
        } else {
            player.getPacketSender().sendMessage("@blu@<shad=0>You need a CorruptRaid Key to open this chest!");
        }
    }

    public static void giveReward(Player player) {
        int soulMultiplier = 1;

        // Check if player has a Raids Soul or Raids Soul(2)
        if (player.getEquipment().get(AMMUNITION_SLOT).getId() == 2728) {
            soulMultiplier = 2; //Raids Soul doubles the reward
        }
        else if (player.getEquipment().get(AMMUNITION_SLOT).getId() == 2730) {
            soulMultiplier = 3; //Raids Soul(2) triples the reward
        }

        if (Misc.getRandom(2500) == 1) { //Rare Item
            int rareDrops = getRandomItem(rareLoots);
            player.getInventory().add(rareDrops, 1 * soulMultiplier);
            World.sendMessage("@or3@[CorruptRaid]@bla@ " + player.getUsername() + " has received a Legendary from the chest!");

            if (soulMultiplier > 1) {
                player.sendMessage("@red@Your Raids Soul Granted you extra rewards!");
            }
        }
        else if (Misc.getRandom(500) == 1) { //Ultra Rare items
            int ultraDrops = getRandomItem(ultraLoots);
            player.getInventory().add(ultraDrops, 1 * soulMultiplier);
            World.sendMessage("@or3@[CorruptRaid]@bla@ " + player.getUsername() + " has received an " + ItemDefinition.forId(ultraDrops).getName() + "from the chest!");

            if (soulMultiplier > 1) {
                player.sendMessage("@red@Your Raids Soul Granted you extra rewards!");
            }
        }
        else if (Misc.getRandom(250) == 1) { //Amazing items
            int amazingDrops = getRandomItem(amazingLoots);
            player.getInventory().add(amazingDrops, 2 * soulMultiplier);
            player.sendMessage("@or3@[CorruptRaid]@bla@ " + player.getUsername() + " has received a Rare Reward from the chest!!");

            if (soulMultiplier > 1) {
                player.sendMessage("@red@Your Raids Soul Granted you extra rewards!");
            }
        }
        else if (Misc.getRandom(150) == 1) { //Common items
            int commonDrops = getRandomItem(commonLoots);
            player.getInventory().add(commonDrops, 2 * soulMultiplier);

            if (soulMultiplier > 1) {
                player.sendMessage("@red@Your Raids Soul Granted you extra rewards!");
            }
        }
        else {
            player.getInventory().add(995, (1000 + Misc.getRandom(10000)) * soulMultiplier);

            if (soulMultiplier > 1) {
                player.sendMessage("@red@Your Raids Soul Granted you extra rewards!");
            }
        }
    }
    public static Box[] loot = { //
            new Box(10946, 1, 0, false),
            new Box(15359, 1, 5, false),
            new Box(23173, 1, 0, false),
            new Box(23172, 1, .5, false),
            new Box(10246, 1, .3, false),
            new Box(10228, 1, .3, false),
            new Box(22091, 1, .3, false),
            new Box(10230, 1, .13, false),
            new Box(23060, 1, .13, false),
            new Box(21613, 1, .13, false),
            new Box(7308, 1, .07, false),
            new Box(7309, 1, .13, false),
            new Box(7311, 1, .13, false),
            new Box(22111, 1, .07, false),
            new Box(7251, 1, .07, false),


    };
}

