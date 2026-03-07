package com.ruse.world.content.combat.strategy.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class SoulBaneMinionScript implements CombatStrategy {

    public void regularAttack(Player player, NPC boss) {

        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        new Projectile(boss, player, 2188, 75, 10, 110, 0, 0).sendProjectile();
        TaskManager.submit(new Task(3, player, false) {
            @Override
            public void execute() {
                player.dealDamage(new Hit(Misc.random(1, 7), Hitmask.VOID, CombatIcon.NONE));
                this.stop();
            }
        });
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC boss = (NPC) entity;

        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
        boolean attack1 = (healthPercentage <= 85 && healthPercentage >= 75 && stage == 0);
        boolean attack2 = (healthPercentage <= 30 && stage == 1);

        for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
            if (p != null) {
                regularAttack(p, boss);
            }
        }

        if (attack1) {
            boss.forceChat("You dare challenge Soul Bane!!!");
            sendRandomGraphics(boss, new Graphic(2189  , 4), 20, 20);
            for (Player p: World.getNearbyPlayers(boss.getPosition(), 20)) {
                TaskManager.submit(new Task(6, p, false) {
                    @Override
                    public void execute() {
                        sendRandomGraphics(boss, new Graphic(266), 12, 18);
                        this.stop();
                    }
                });
            }

            stage++;

        }
        if (attack2) {
            sendRandomGraphics(boss, new Graphic(2189  , 4), 20, 20);
            boss.forceChat("Muahahahahaha");
            for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
                TaskManager.submit(new Task(6, p, false) {
                    @Override
                    public void execute() {
                        p.getPacketSender().sendCameraNeutrality();
                        this.stop();
                    }
                });
            }
            stage--;

        }
        return true;
    }

    public void sendRandomGraphics(NPC boss, Graphic graphic, int radius, int count) {
        for (int i = 0; i < count; i++) {
            int x = boss.getPosition().getX() + Misc.random(-radius, radius);
            int y = boss.getPosition().getY() + Misc.random(-radius, radius);
            Position position = new Position(x, y);
            for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
                p.getPA().sendGraphic(graphic, position);
            }
        }
    }

    private void defaultcheck(Player victim, NPC boss) {
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
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 25;
    }
    public int stage = 0;

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}




