package com.ruse.world.content.ExpLamps;

import com.ruse.model.Skill;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.entity.impl.player.Player;

public class ExpLamps {
    public static int NECRO_XP = 500;
    public static int HERBLORE_XP = 500;
    public static int SMALL_SLAYER_XP = 55;
    public static int MEDIUM_SLAYER_XP = 175;
    public static int SLAYER_XP = 725;
    public static int MINING_XP = 500;
    public static int PRAYER_XP = 2500;

    public static boolean handleRub(Player player, int item) {
        LampsData lamp = LampsData.forId(item);
        if (lamp == null)
            return false;
        if (!player.getClickDelay().elapsed(600)) {
            player.sendMessage("Slow Down...");
            return false;
        }
        player.getClickDelay().reset();
        if (lamp == LampsData.NECRO_LAMP){
            player.getInventory().delete(lamp.itemId,1);
            player.getSkillManager().addExperience(Skill.NECROMANCY, NECRO_XP);
        }
        if (lamp == LampsData.HERBLORE_LAMP){
            player.getInventory().delete(lamp.itemId,1);
            player.getSkillManager().addExperience(Skill.HERBLORE, HERBLORE_XP);
        }

        if (lamp == LampsData.SMALL_SLAYER){
            player.getInventory().delete(lamp.itemId,1);
            player.getSkillManager().addExperience(Skill.SLAYER, SMALL_SLAYER_XP);
        }
        if (lamp == LampsData.MEDIUMSLAYER){
            player.getInventory().delete(lamp.itemId,1);
            player.getSkillManager().addExperience(Skill.SLAYER, MEDIUM_SLAYER_XP);
        }
        if (lamp == LampsData.SLAYER_LAMP){
            player.getInventory().delete(lamp.itemId,1);
            player.getSkillManager().addExperience(Skill.SLAYER, SLAYER_XP);
        }
        if (lamp == LampsData.MINING_LAMP){
            player.getInventory().delete(lamp.itemId,1);
            player.getSkillManager().addExperience(Skill.MINING, MINING_XP);
        }
        if (lamp == LampsData.PRAYER_LAMP){
            player.getInventory().delete(lamp.itemId,1);
            player.getSkillManager().addExperience(Skill.PRAYER, PRAYER_XP);
        }
        return false;
    }

    public static boolean handleRubAll(Player player, int item) {
        LampsData lamp = LampsData.forId(item);
        if (lamp == null)
            return false;
        if (!player.getClickDelay().elapsed(600)) {
            player.sendMessage("Slow Down...");
            return false;
        }
        if (player.getRubAllLampId() == 0){
            player.setRubAllLampId(lamp.itemId);
            DialogueManager.start(player, 6060);
            player.setDialogueActionId(6060);
            return false;
        }
        player.getClickDelay().reset();
        player.setRubAllLampId(0);
        if (lamp == LampsData.NECRO_LAMP){
            int necro_amount = player.getInventory().getAmount(lamp.itemId);
            player.getInventory().delete(lamp.itemId, necro_amount);
            player.getSkillManager().addExperience(Skill.NECROMANCY, NECRO_XP * necro_amount);
        }
        if (lamp == LampsData.HERBLORE_LAMP){
            int herb_amount = player.getInventory().getAmount(lamp.itemId);
            player.getInventory().delete(lamp.itemId,herb_amount);
            player.getSkillManager().addExperience(Skill.HERBLORE, HERBLORE_XP * herb_amount);
        }

        if (lamp == LampsData.SMALL_SLAYER){
            int slayer_amount = player.getInventory().getAmount(lamp.itemId);
            player.getInventory().delete(lamp.itemId,slayer_amount);
            player.getSkillManager().addExperience(Skill.SLAYER, SMALL_SLAYER_XP * slayer_amount);
        }
        if (lamp == LampsData.MEDIUMSLAYER){
            int slayer_amount = player.getInventory().getAmount(lamp.itemId);
            player.getInventory().delete(lamp.itemId,slayer_amount);
            player.getSkillManager().addExperience(Skill.SLAYER, MEDIUM_SLAYER_XP * slayer_amount);
        }
        if (lamp == LampsData.SLAYER_LAMP){
            int slayer_amount = player.getInventory().getAmount(lamp.itemId);
            player.getInventory().delete(lamp.itemId,slayer_amount);
            player.getSkillManager().addExperience(Skill.SLAYER, SLAYER_XP * slayer_amount);
        }
        if (lamp == LampsData.MINING_LAMP){
            int mining_amount = player.getInventory().getAmount(lamp.itemId);
            player.getInventory().delete(lamp.itemId,mining_amount);
            player.getSkillManager().addExperience(Skill.MINING, MINING_XP * mining_amount);
        }
        if (lamp == LampsData.PRAYER_LAMP){
            int prayer_amount = player.getInventory().getAmount(lamp.itemId);
            player.getInventory().delete(lamp.itemId,prayer_amount);
            player.getSkillManager().addExperience(Skill.PRAYER, PRAYER_XP * prayer_amount);
        }
        return false;
    }

    enum LampsData {
        NECRO_LAMP(20066), HERBLORE_LAMP(20067), SLAYER_LAMP(20068), SMALL_SLAYER(17127), MEDIUMSLAYER(17128), MINING_LAMP(20069), PRAYER_LAMP(20071) ;

        LampsData(int itemId) {
            this.itemId = itemId;
        }
        private int itemId;
        public int getItemId() {
            return this.itemId;
        }
        public static ExpLamps.LampsData forId(int id) {
            for (ExpLamps.LampsData LampsData : ExpLamps.LampsData.values()) {
                if (LampsData != null && LampsData.getItemId() == id)
                    return LampsData;
            }
            return null;
        }
    }

}
