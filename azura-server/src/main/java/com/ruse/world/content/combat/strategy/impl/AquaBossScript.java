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

public class AquaBossScript implements CombatStrategy {
    public int npcId() {
        return 8002;
    }
    public void regularAttack(Player player, NPC boss) {
        boss.performAnimation(new Animation(7769));
        player.dealDamage(new Hit(Misc.random(10, 60), Hitmask.NEUTRAL, CombatIcon.MAGIC));
        new Projectile(boss, player, 500, 75, 10, 110, 0, 0).sendProjectile();
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC boss = (NPC) entity;

        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
        boolean attack1 = (healthPercentage <= 85 && healthPercentage >= 75 && stage == 0);
        boolean attack2 = (healthPercentage <= 30 && stage == 1);

        for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
            if (p != null) {
                if (!attack1 && !attack2) regularAttack(p, boss);
            }
        }

        if (attack1) {
            boss.forceChat("I hope you know how to swim!!");
            sendRandomGraphics(boss, new Graphic(1026, 4), 12, 18);
            for (Player p: World.getNearbyPlayers(boss.getPosition(), 20)) {
                p.getPacketSender().sendCameraShake(3, 2, 3, 2);
                TaskManager.submit(new Task(6, p, false) {
                    @Override
                    public void execute() {
                        sendRandomGraphics(boss, new Graphic(68), 12, 18);
                        p.getPacketSender().sendCameraNeutrality();
                        this.stop();
                    }
                });
            }

            stage++;

        }
        if (attack2) {
            sendRandomGraphics(boss, new Graphic(502, 4), 12, 18);
            boss.forceChat("Muahahahahaha");
            for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
                p.getPacketSender().sendCameraShake(3, 2, 3, 2);
                TaskManager.submit(new Task(6, p, false) {
                    @Override
                    public void execute() {
                        sendRandomGraphics(boss, new Graphic(858 ), 12, 18);
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
        return 50;
    }
    public int stage = 0;

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}




