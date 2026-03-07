package com.ruse.world.content.timers.impl;

import com.ruse.model.Skill;
import com.ruse.world.content.Consumables;
import com.ruse.world.content.timers.EffectTimer;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;

import static com.ruse.model.Skill.*;

public class RageTimer extends EffectTimer {
    Player player;

    public RageTimer(Player player) {
        super(player,1323, "Rage");
        this.player = player;
    }
    @Override
    public void addTime(int additionalSeconds) {
        super.addTime(additionalSeconds);
    }
    public void append() {
        int timeToAdd = 300;
        if (player.getNodesUnlocked() != null) {
            if (player.getSkillTree().isNodeUnlocked(Node.ALCHEMICAL_MIND)) {
                timeToAdd = 600;
            } else {
                timeToAdd = 300;
            }
        }
        if (isActive() && getTimeLeft() + timeToAdd > 1500) {
            player.msgRed("You can only have up to 25 minutes of " + getName() + " active");
            return;
        }

        if (isActive()){
            player.msgFancyPurp("Added " + (timeToAdd / 60) + " Minutes of " + getName() + " effect!");
        } else {
            player.msgFancyPurp("You've started " + (timeToAdd / 60) + " Minutes of " + getName() + " effect!");
        }

        player.getInventory().delete(1323,1);
        player.msgFancyPurp("You activated a Rage potion..");
        Consumables.levelIncrease(player, ATTACK, player.getSkillManager().getMaxLevel(ATTACK) + 18);
        Consumables.levelIncrease(player, Skill.STRENGTH, player.getSkillManager().getMaxLevel(STRENGTH) + 18);
        Consumables.levelIncrease(player, Skill.DEFENCE, player.getSkillManager().getMaxLevel(DEFENCE) + 18);
        Consumables.levelIncrease(player, Skill.RANGED, player.getSkillManager().getMaxLevel(RANGED) + 18);
        Consumables.levelIncrease(player, Skill.MAGIC, player.getSkillManager().getMaxLevel(MAGIC) + 18);
        player.getClickDelay().reset();
        addTime(timeToAdd);

    }
}
