package com.ruse.world.content;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.entity.impl.player.Player;

public class MarinasTasks {
    public static final Item[] MarinaTable = { // - TYLER
            new Item(10945, 1),
            new Item(10946, 1),
            new Item(1446, 2),
            new Item(1448, 2),
            new Item(15668, 1),
            new Item(3576, 150),
            new Item(995, 2500),
            new Item(15667, 5),
            new Item(17129, 1),
            new Item(15669, 2),
            new Item(23171, 1),
            new Item(10262, 1),
            new Item(10260, 1),
            new Item(10256, 1),
            new Item(3580, 3),
            new Item(3582, 1),
            new Item(15670, 1),
            new Item(5585, 5)
    };

    public static void  rollMarinaTable(Player player) {
        Item item = MarinaTable[Misc.inclusiveRandom(MarinaTable.length - 1)];
        player.getInventory().add(item);
        player.msgRed("Queen Marina has reward you with a " + item.getDefinition().getName() + "!");
    }

    public static void assignTask(Player player) {
        int randomTask = Misc.random(1, 10);

        if (player.getDailyMarinaTasks() >= 5){
            player.msgRed("You have already completed your 5 daily tasks for Queen Marina.");
            return;
        }

        if (player.getMarinaTaskID() != 0) {
            player.msgRed("You already have a task from Marina, Operate the Crown to view it.");
            return;
        }

            player.setMarinaTaskID(randomTask);

            switch (player.getMarinaTaskID()) {
                case 1:
                    DialogueManager.sendStatement(player, "Dig near a lamp");
                    break;
                case 2:
                    DialogueManager.sendStatement(player, "Dig near a Bank Chest");
                    break;
                case 3:
                    DialogueManager.sendStatement(player, "Dig on Charred Bones");
                    break;
                case 4:
                    DialogueManager.sendStatement(player, "Dig in front of an altar");
                    break;
                case 5:
                    DialogueManager.sendStatement(player, "Dig on a branch near a Vorpal Tree");
                    break;
                case 6:
                    DialogueManager.sendStatement(player, "Use Coins the fire wizard at COE.");
                    break;
                case 7:
                    DialogueManager.sendStatement(player, "Talk to the King, upstairs in the Castle.");
                    break;
                case 8:
                    DialogueManager.sendStatement(player, "Defeat the Donator Boss");
                    break;
                case 9:
                    DialogueManager.sendStatement(player, "Complete a Slayer Task");
                    break;
                case 10:
                    DialogueManager.sendStatement(player, "Defeat Netherveil");
                    break;
            }
        }


    public static void dig(Player player) {
        player.getMovementQueue().reset();
        player.getPacketSender().sendMessage("You start digging..");
        player.performAnimation(new Animation(830));
        handleTasks(player);
    }

    public static void resetAndReward(Player player) {
        rollMarinaTable(player);
        player.setMarinaTaskID(0);
        player.setDailyMarinaTasks(player.getDailyMarinaTasks() + 1);
    }


    public static void handleTasks(Player player) {


        final Position playerPosition = new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());


        switch (player.getMarinaTaskID()){
            case 1:
                if (!playerPosition.equals(CLUE_SPOT_1)){
                    player.msgRed("wrong spot");
                    return;
                }
                resetAndReward(player);
                break;
            case 2:
                if (!playerPosition.equals(CLUE_SPOT_2)){
                    player.msgRed("wrong spot");
                    return;
                }
                resetAndReward(player);
                break;
            case 3:
                if (!playerPosition.equals(CLUE_SPOT_3)){
                    player.msgRed("wrong spot");
                    return;
                }
                resetAndReward(player);
                break;
            case 4:
                if (!playerPosition.equals(CLUE_SPOT_4)){
                    player.msgRed("wrong spot");
                    return;
                }
                resetAndReward(player);
                break;
            case 5:
                if (!playerPosition.equals(CLUE_SPOT_5)){
                    player.msgRed("wrong spot");
                    return;
                }
                resetAndReward(player);
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                resetAndReward(player);
                break;
        }
    }

    private static final Position CLUE_SPOT_1 = new Position(3178, 3546, 0);
    private static final Position CLUE_SPOT_2 = new Position(3176, 3542, 0);
    private static final Position CLUE_SPOT_3 = new Position(2723, 3110, 0);
    private static final Position CLUE_SPOT_4 = new Position(3042, 3860, 0);
    private static final Position CLUE_SPOT_5 = new Position(3247, 2990, 0);

}
