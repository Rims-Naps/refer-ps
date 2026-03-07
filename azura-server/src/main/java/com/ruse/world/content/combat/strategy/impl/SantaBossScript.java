package com.ruse.world.content.combat.strategy.impl;
import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class SantaBossScript implements CombatStrategy {

    public int npcId() {
        return 1061;
    }

    public void regularAttack(Player player, NPC megaman) {
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC boss = (NPC) entity;
        Player p = (Player) victim;

        boss.performAnimation(new Animation(422));

        boss.getMovementQueue().setLockMovement(true);

        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);

        for(Player otherVictims : World.getNearbyPlayers(boss.getPosition(), 25)) {
            new Projectile(boss, otherVictims, 1209, 75, 10, 110, 0, 0).sendProjectile();
            boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
            TaskManager.submit(new Task(3, otherVictims, false) {
                @Override
                public void execute() {
                    if (!otherVictims.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                        otherVictims.dealDamage(new Hit(Misc.random(5, 15), Hitmask.WATER, CombatIcon.MAGIC));
                    }
                    if (otherVictims.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                        otherVictims.dealDamage(new Hit(Misc.random(0), Hitmask.WATER, CombatIcon.BLOCK));
                    }
                    handlestages(boss, otherVictims, healthPercentage);
                    this.stop();
                }
            });
        }
        return true;
    }



    private void sendStage(NPC boss, int execStage) {

        switch (execStage) {
            case 0:
                GameSettings.santaStage = 3;
                for (Player victims : World.getNearbyPlayers(boss.getPosition(), 20)) {
                    victims.performGraphic(new Graphic(1014));
                    boss.forceChat("HO HO HO!");
                }
                break;
            case 1:
                GameSettings.santaStage = 1;
                for (Player victims : World.getNearbyPlayers(boss.getPosition(), 20)) {
                    victims.performGraphic(new Graphic(396));
                    boss.forceChat("Let it Snow!");
                }
                break;
            case 2:
                GameSettings.santaStage = 2;
                for (Player victims : World.getNearbyPlayers(boss.getPosition(), 20)) {
                    victims.performGraphic(new Graphic(2146));
                    boss.forceChat("Merry Christmas!");
                }
                break;
        }
    }

    private void handlestages(NPC boss, Player p, int healthPercentage) {
        if (boss.getConstitution() >= 80 && GameSettings.santaStage == 0) {
            sendStage(boss,1);
        } else if (healthPercentage <= 50 && GameSettings.santaStage == 1) {
            sendStage(boss, 2);
        } else if (healthPercentage <= 25 && GameSettings.santaStage == 2) {
            sendStage(boss, 0);
        }
    }

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return true;
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public int attackDelay(Character entity) {
        return 5;
    }

    @Override
    public int attackDistance(Character entity) {
        return 50;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
