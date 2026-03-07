package com.ruse.world.content;

import com.ruse.model.Skill;
import com.ruse.util.Misc;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.entity.impl.player.Player;

public class SkillingPetBonuses {

    public static void checkSkillingPet(Player player, int baseXp) {
        if (player.getSummoning() == null || player.getSummoning().getFamiliar() == null){
            baseXp *= 1.05;
            player.getSkillManager().addExperience(Skill.NECROMANCY, baseXp);
            return;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SKELETAL_SERVANT.npcId) {
            baseXp *= 1.15;
            player.getSkillManager().addExperience(Skill.NECROMANCY, baseXp);
            return;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEMONIC_SERVANT.npcId) {
            baseXp *= 1.25;
            player.getSkillManager().addExperience(Skill.NECROMANCY, baseXp);
            return;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.OGRE_SERVANT.npcId) {
            baseXp *= 1.35;
            player.getSkillManager().addExperience(Skill.NECROMANCY, baseXp);
            return;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SPECTRAL_SERVANT.npcId) {
            baseXp *= 1.45;
            player.getSkillManager().addExperience(Skill.NECROMANCY, baseXp);
            return;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.MASTER_SERVANT.npcId) {
            baseXp *= 1.55;
            player.getSkillManager().addExperience(Skill.NECROMANCY, baseXp);
            return;
        }
    }
}
