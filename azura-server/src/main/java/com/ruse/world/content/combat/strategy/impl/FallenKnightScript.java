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

public class FallenKnightScript implements CombatStrategy {

    public int npcId() {
        return 2097;
    }

    public void regularAttack(Player player, NPC knight) {
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC boss = (NPC) entity;
        Player p = (Player) victim;

        if (!GameSettings.fallenMinionActive) {
            boss.performAnimation(new Animation(422));
        }

        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);

        for(Player otherVictims : World.getNearbyPlayers(boss.getPosition(), 25)) {
            if (!GameSettings.fallenMinionActive) {
                new Projectile(boss, p, 1617, 150, 10, 110, 0, 0).sendProjectile();
            }

            TaskManager.submit(new Task(3, otherVictims, false) {
                @Override
                public void execute() {
                    if (!GameSettings.fallenMinionActive) {
                        if (!otherVictims.getCurseActive()[CurseHandler.BLOCK_MELEE]) {
                            otherVictims.dealDamage(new Hit(Misc.random(5, 15), Hitmask.FIRE, CombatIcon.MELEE));
                        }
                        if (otherVictims.getCurseActive()[CurseHandler.BLOCK_MELEE]) {
                            otherVictims.dealDamage(new Hit(0, Hitmask.FIRE, CombatIcon.BLOCK));
                        }
                    }
                        handlestages(boss, otherVictims, healthPercentage);
                        this.stop();
                    }
            });
        }

        return true;
    }

    private void sendStage(NPC boss, int knightStage) {
        switch (knightStage) {
            case 1:
                GameSettings.fallenMinionActive = true;
                NPC RangeMinion1 = new NPC(2200, new Position(2258, 3167, 0));
                NPC RangeMinion2 = new NPC(2200, new Position(2288, 3167, 0));

                World.register(RangeMinion1);
                World.register(RangeMinion2);
                RangeMinion1.getMovementQueue().setLockMovement(true);
                RangeMinion2.getMovementQueue().setLockMovement(true);
                RangeMinion1.setForceAggressive(true);
                RangeMinion2.setForceAggressive(true);

                for(Player otherVictims : World.getNearbyPlayers(boss.getPosition(), 25)) {
                    for (int x = 2262; x < 2285; x++) {
                        otherVictims.getPA().sendGraphic(new Graphic(1154), new Position(x, 3156, otherVictims.getPosition().getZ()));
                        otherVictims.getPA().sendGraphic(new Graphic(1154), new Position(x, 3178, otherVictims.getPosition().getZ()));
                    }
                    for (int y = 3156; y < 3178; y++) {
                        otherVictims.getPA().sendGraphic(new Graphic(1154), new Position(2262, y, otherVictims.getPosition().getZ()));
                        otherVictims.getPA().sendGraphic(new Graphic(1154), new Position(2285, y, otherVictims.getPosition().getZ()));
                    }
                }
                break;
            case 2:
                GameSettings.fallenMinionActive = true;
                NPC MageMinion1 = new NPC(2201, new Position(2258, 3167, 0));
                NPC MageMinion2 = new NPC(2201, new Position(2288, 3167, 0));
                World.register(MageMinion1);
                World.register(MageMinion2);
                MageMinion1.getMovementQueue().setLockMovement(true);
                MageMinion2.getMovementQueue().setLockMovement(true);
                MageMinion1.setForceAggressive(true);
                MageMinion2.setForceAggressive(true);
                for(Player otherVictims : World.getNearbyPlayers(boss.getPosition(), 25)) {
                    for (int x = 2262; x < 2285; x++) {
                        otherVictims.getPA().sendGraphic(new Graphic(1154), new Position(x, 3156, otherVictims.getPosition().getZ()));
                        otherVictims.getPA().sendGraphic(new Graphic(1154), new Position(x, 3178, otherVictims.getPosition().getZ()));
                    }
                    for (int y = 3156; y < 3178; y++) {
                        otherVictims.getPA().sendGraphic(new Graphic(1154), new Position(2262, y, otherVictims.getPosition().getZ()));
                        otherVictims.getPA().sendGraphic(new Graphic(1154), new Position(2285, y, otherVictims.getPosition().getZ()));
                    }
                }
                break;
        }
    }

    private void handlestages(NPC boss, Player p, int healthPercentage) {
        if (healthPercentage<= 80 && GameSettings.fallenKnightStage == 0) {
            GameSettings.fallenKnightStage = 1;
            sendStage(boss,1);
            boss.forceChat("Rangers Defeat them now!");
            for (Player otherVictims : World.getNearbyPlayers(boss.getPosition(), 25)) {
                otherVictims.getCombatBuilder().reset(true);
                otherVictims.msgRed("Defeat the Fallen Minions in order to defeat the Boss!");
            }
        } else if (healthPercentage <= 40 && GameSettings.fallenKnightStage == 1) {
            sendStage(boss, 2);
            GameSettings.fallenKnightStage = 2;
            boss.forceChat("Mages...Attack");
            for(Player otherVictims : World.getNearbyPlayers(boss.getPosition(), 25)) {
                otherVictims.getCombatBuilder().reset(true);
                otherVictims.msgRed("Defeat the Fallen Minions in order to defeat the Boss!");
            }
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