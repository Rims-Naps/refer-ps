package com.ruse.world.content.Easter;

import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.entity.impl.player.Player;



public class EasterQuest {
    public static int VORPAL_SCRAPS = 1408;
    public static int KINGS_ITEM = 717;
    public static int VORPAL_LOGS = 1400;
    public static int EASTER_EGG = 12637;

    public static boolean stage0(Player player) {
        DialogueManager.start(player, 8800);
        player.setDialogueActionId(8807);
        return true;
}

    public static boolean stage1Requirements(Player player) {
        if (player.getInventory().getAmount(VORPAL_SCRAPS) < 20){
            DialogueManager.start(player, 8812);
            return false;
        }
        player.getInventory().delete(VORPAL_SCRAPS,20);
        player.setEasterQuestStage(2);
        DialogueManager.start(player, 8815);
        return true;
    }

    public static boolean stage2Requirements(Player player) {
        if (!player.getInventory().contains(KINGS_ITEM)){
            DialogueManager.start(player, 8817);
            return false;
        }

        player.getInventory().delete(KINGS_ITEM,1);
        player.setEasterQuestStage(3);
        DialogueManager.start(player, 8820);
        return true;
    }
    public static boolean stage3Requirements(Player player) {
        if (player.getRabidBunniesKilled() < 25){
            DialogueManager.start(player, 8823);
            return false;
        }

        player.setEasterQuestStage(4);
        DialogueManager.start(player, 8824);
        return true;
    }

    public static boolean stage4Requirements(Player player) {
        if (player.getInventory().getAmount(VORPAL_LOGS) < 25){
            DialogueManager.start(player, 8828);
            return false;
        }

        player.getInventory().delete(VORPAL_LOGS,25);
        player.setEasterQuestStage(5);
        DialogueManager.start(player, 8829);
        return true;
    }
    public static boolean stage5Requirements(Player player) {
        if (!player.getInventory().contains(EASTER_EGG)){
            DialogueManager.start(player, 8833);
            return false;
        }

        player.getInventory().delete(EASTER_EGG,1);
        player.setEasterQuestStage(6);
        DialogueManager.start(player, 8835);
        player.setDialogueActionId(8834);

        return true;
    }
    public static boolean completion(Player player) {
        if (!player.acceptedEasterReward){
            DialogueManager.start(player, 8835);
            return false;
        }
        return true;
    }
}
