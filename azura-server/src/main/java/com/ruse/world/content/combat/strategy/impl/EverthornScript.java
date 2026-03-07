package com.ruse.world.content.combat.strategy.impl;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.bossfights.impl.EverthornFight;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class EverthornScript implements CombatStrategy {
    
    public int npcId() {
        return 2018;
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
    public boolean customContainerAttack(Character entity, Character victim) {

        NPC boss = (NPC) entity;
        Player player = (Player) victim;
        if (player.getBossFight() == null || !(player.getBossFight() instanceof EverthornFight))
            return false;
        if (player.everthornattackrunning) {
            return false;
        }

        int currentStage = player.getEverthornStage();
        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
        if (healthPercentage <= 75 && currentStage == 1) {
            player.getBossFight().setCurrentStage(2);
            currentStage = 2;
        } else if (healthPercentage <= 50 && currentStage == 2) {
            player.getBossFight().setCurrentStage(3);
            currentStage = 3;
        }

        int random = Misc.random(0, 3);
        switch (currentStage) {
            case 1:
                if (random == 0) {
                    islandAttack(player, boss);
                } else if (random == 1 || random == 2) {
                    regularattack(player, boss);
                } else if (random == 3) {
                    groundattack(player, boss);
                }
                break;
            case 2:
                if (random == 0) {
                    groundattack(player, boss);
                } else if (random == 1 || random == 2) {
                    regularattack(player, boss);
                } else if (random == 3) {
                    islandAttack(player, boss);
                }
                break;
            case 3:
                if (random == 0) {
                    regularattack(player, boss);
                } else if (random == 1 || random == 2) {
                    groundattack(player, boss);
                } else if (random == 3) {
                    islandAttack(player, boss);
                }
                break;
        }
        return true;
    }


    private void islandAttack(Player player, NPC boss) {
        int randomarea = Misc.random(1, 4);

        if (player.gfxattackrunning){
            return;
        }
        if (player.everthornattackrunning){
            return;
        }

        player.setEverthornattackrunning(true);
        player.setSafearea(randomarea);


        if (player.getSafearea() == 1){
            boss.performGraphic(new Graphic(1));
            boss.forceChat("BLUE ISLAND WILL BE SAFE");
            player.sendMessage("@blu@<shad=0>BLUE ISLAND WILL BE SAFE");
            boss.performGraphic(new Graphic(1296));
        }
        if (player.getSafearea() == 2 ){
            boss.performGraphic(new Graphic(2));
            boss.forceChat("BROWN ISLAND WILL BE SAFE");
            player.sendMessage("@or2@<shad=0>BROWN ISLAND WILL BE SAFE");
            boss.performGraphic(new Graphic(1311));
        }
        if (player.getSafearea() == 3){
            boss.performGraphic(new Graphic(3));
            boss.forceChat("BLACK ISLAND WILL BE SAFE");
            player.sendMessage("@bla@<shad=0>BLACK ISLAND WILL BE SAFE");
            boss.performGraphic(new Graphic(984));

        }
        if (player.getSafearea() == 4){
            boss.performGraphic(new Graphic(4));
            boss.forceChat("PURPLE ISLAND WILL BE SAFE");
            player.sendMessage("@mag@<shad=0>PURPLE ISLAND WILL BE SAFE");
            boss.performGraphic(new Graphic(1011));

        }

        Position BLUE_PORTAL_POS = new Position(2905, 4385, player.getPosition().getZ());
        Position BROWN_PORTAL_POS = new Position(2922, 4385, player.getPosition().getZ());
        Position BLACK_PORTAL_POS = new Position(2922, 4377, player.getPosition().getZ());
        Position PURPLE_PORTAL_POS = new Position(2905, 4377, player.getPosition().getZ());

        NPC blueportal = new NPC(7347, BLUE_PORTAL_POS);
        NPC brownportal = new NPC(7365, BROWN_PORTAL_POS);
        NPC blackportal = new NPC(7359, BLACK_PORTAL_POS);
        NPC purpleportal = new NPC(6804, PURPLE_PORTAL_POS);

        World.register(blueportal);
        World.register(brownportal);
        World.register(blackportal);
        World.register(purpleportal);
        blueportal.performGraphic(new Graphic(7));
        brownportal.performGraphic(new Graphic(7));
        blackportal.performGraphic(new Graphic(7));
        purpleportal.performGraphic(new Graphic(7));

        TaskManager.submit(new Task(12, player, false) {
            @Override
            public void execute() {
                for (int x = 2891; x < 2899; x++) {
                    for (int y = 4398; y < 4403; y++) {
                        player.getPA().sendGraphic(new Graphic(6), new Position(x, y, player.getPosition().getZ()));
                    }
                }

                for (int x = 2928; x < 2935; x++) {
                    for (int y = 4400; y < 4404; y++) {
                        player.getPA().sendGraphic(new Graphic(6), new Position(x, y, player.getPosition().getZ()));
                    }
                }

                for (int x = 2922; x < 2928; x++) {
                    for (int y = 4357; y < 4361; y++) {
                        player.getPA().sendGraphic(new Graphic(6), new Position(x, y, player.getPosition().getZ()));
                    }
                }

                for (int x = 2884; x < 2889; x++) {
                    for (int y = 4359; y < 4366; y++) {
                        player.getPA().sendGraphic(new Graphic(6), new Position(x, y, player.getPosition().getZ()));
                    }
                }

                if (player.getSafearea() == 1 && player.getLocation() != Locations.Location.THORN_BLUE
                 || player.getSafearea() == 2 && player.getLocation() != Locations.Location.THORN_BROWN
                 || player.getSafearea() == 3 && player.getLocation() != Locations.Location.THORN_BLACK
                 || player.getSafearea() == 4 && player.getLocation() != Locations.Location.THORN_PURPLE){
                    if (boss.isRegistered()) {
                        player.dealDamage(new Hit(120, Hitmask.CRITICAL, CombatIcon.NONE));
                        player.sendMessage("<shad=0>@red@You failed to move to the correct island.");
                    }
                    World.deregister(blueportal);
                    World.deregister(brownportal);
                    World.deregister(blackportal);
                    World.deregister(purpleportal);
                    player.setEverthornattackrunning(false);
                    this.stop();
                    return;
                }
                player.performGraphic(new Graphic(1177));
                World.deregister(blueportal);
                World.deregister(brownportal);
                World.deregister(blackportal);
                World.deregister(purpleportal);
                player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                player.sendMessage("<shad=0>@gre@You chose the correct island.");
                player.moveTo(2914, 4377, player.getPosition().getZ());
                player.setEverthornattackrunning(false);
                this.stop();
            }
        });
    }


    private void regularattack(Player player, NPC boss) {
        if (player.gfxattackrunning){
            return;
        }
        if (player.everthornattackrunning){
            return;
        }
        new Projectile(boss, player, 1243, 250, 5, 75, 10, 0).sendProjectile();
        boss.performAnimation(new Animation(7853));
        if (!player.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
            player.dealDamage(new Hit(Misc.random(0, 120), Hitmask.DARK_PURPLE, CombatIcon.MAGIC));
        }
    }
    ArrayList<Position> pos = new ArrayList<>();
    private int delay = 0;
    private void groundattack(Player player, NPC boss) {
        if (player.gfxattackrunning){
            return;
        }
        if (player.everthornattackrunning){
            return;

        }
        player.setGfxattackrunning(true);
        boss.forceChat("FEEL GAIA's STRENGTH!!!");
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            int xOffset = random.nextInt(6) - 3;
            int yOffset = random.nextInt(6) - 3;
            pos.add(new Position(2914 + xOffset + i, 4379 + yOffset, player.getPosition().getZ()));
            pos.add(new Position(2914 - xOffset - i, 4379 + yOffset, player.getPosition().getZ()));
            pos.add(new Position(2914 + xOffset, 4379 + yOffset + i, player.getPosition().getZ()));
            pos.add(new Position(2914 + xOffset, 4379 - yOffset - i, player.getPosition().getZ()));
        }
        pos.forEach(position -> player.getPacketSender().sendGraphic(new Graphic(552), position));
        new Projectile(boss, player, 550, 50, 3, 75, 2, 0).sendProjectile();
        boss.performAnimation(new Animation(7853));
        player.dealDamage(new Hit(Misc.random(0, 20), Hitmask.EARTH, CombatIcon.NONE));
        TaskManager.submit(new Task(1) {
            int ticks = 0;

            @Override
            protected void execute() {
                if (ticks == 5) {
                    if (player != null) {
                            pos.forEach(position -> player.getPacketSender().sendGraphic(new Graphic(266), position));
                        if (pos.contains(player.getPosition())) {
                            if (boss.isRegistered())
                            player.dealDamage(new Hit(120, Hitmask.CRITICAL, CombatIcon.NONE));
                        }
                    }
                    stop();
                }
                ticks++;
            }
            @Override
            public void stop() {
                ticks = 0;
                player.setGfxattackrunning(false);
                delay = 0;
                pos.clear();
                setEventRunning(false);
            }
        });
    }

    @Override
    public int attackDelay(Character entity) {
        return 16; // SET BOSS ATTACK SPEED
    }

    @Override
    public int attackDistance(Character entity) {
        return 12; // SET BOSS ATTACK DISTANCE
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.RANGED;
    }
}
