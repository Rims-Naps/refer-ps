package com.ruse.world.content.forgottenRaids.boss.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.forgottenRaids.ForgottenRaid;
import com.ruse.world.content.forgottenRaids.boss.ForgottenRaidBoss;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RaidBossThree extends ForgottenRaidBoss {

    private static final int TILE_START_X = 3314;
    private static final int TILE_START_Y = 3495;
    private static final int TILE_END_X = 3321;
    private static final int TILE_END_Y = 3509;

    @Getter
    private final List<MarkedTile> markedTileList = new ArrayList<>();

    @Override
    public void spawn() {
        super.spawn();
        calculateTilePositions();
    }

    private void calculateTilePositions() {
        markedTileList.forEach(tile -> {
            tile.tileTask.stop();
        });
        markedTileList.clear();
        int maxX = TILE_END_X - TILE_START_X;
        int maxY = TILE_END_Y - TILE_START_Y;
        for (int i = 0; i < 5; i++) {
            MarkedTile markedTile = new MarkedTile(TILE_START_X + (Misc.random(0, maxX)), TILE_START_Y + (Misc.random(0, maxY)));
            while (markedTileList.contains(markedTile))
                markedTile = new MarkedTile(TILE_START_X + (Misc.random(0, maxX)), TILE_START_Y + (Misc.random(0, maxY)));
            markedTileList.add(markedTile);
        }
        for (MarkedTile markedTile : markedTileList)
            TaskManager.submit(markedTile.tileTask);
    }

    public RaidBossThree(ForgottenRaid connectedRaid) {
        super(connectedRaid);
    }

    @Override
    public int npcId() {
        return 3056;
    }

    @Override
    public int health() {
        return 15_000_000;
    }

    @Override
    public Position position() {
        return new Position(3315, 3501).copy().setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public Position afterDeathPosition() {
        return new Position(3304, 3506).setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public String bossName() {
        return "raid-boss-three";
    }

    @Override
    public CombatStrategy combatStrategy() {
        return new CombatStrategy() {
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
                if (getConnectedRaid().getParty() == null || !(getConnectedRaid().getCurrentBoss() instanceof RaidBossThree))
                    return false;

                int healthPercentage = (int) (((double) getBossNpc().getConstitution() / (double) getBossNpc().getDefaultConstitution()) * 100D);
                boolean rotateTiles = ((healthPercentage <= 90 && currentStage == 0) || (healthPercentage <= 60 && currentStage == 1) || (healthPercentage <= 30 && currentStage == 2));
                boolean waveOfDamage = ((healthPercentage <= 75 && currentWave == 0) || (healthPercentage <= 45 && currentWave == 1) || (healthPercentage <= 15 && currentWave == 2));
                if (rotateTiles) {
                    calculateTilePositions();
                    currentStage++;
                } else if (waveOfDamage) {
                    getBossNpc().forceChat("FEEL MY WRATH!");
                    getBossNpc().performAnimation(new Animation(7853));
                    currentWave++;
                    waveOfDamage();
                } else {
                    for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                        Player partyMember = World.getPlayerByName(playerName);
                        if (partyMember != null)
                            regularAttack(partyMember);
                    }
                }
                return true;
            }

            @Override
            public int attackDelay(Character entity) {
                return 4;
            }

            @Override
            public int attackDistance(Character entity) {
                return 40;
            }

            @Override
            public CombatType getCombatType() {
                return CombatType.RANGED;
            }


        };
    }


    public void regularAttack(Player player) {
        getBossNpc().performAnimation(new Animation(getBossNpc().getDefinition().getAttackAnimation()));
        player.dealDamage(new Hit(Misc.random(23, 42), Hitmask.DARK_GREEN, CombatIcon.MAGIC));
        new Projectile(getBossNpc(), player, 2101, 44, 3, 43, 43, 0).sendProjectile();
    }

    private void waveOfDamage() {
        TaskManager.submit(new Task(1, false) {
            int tick = 0;

            @Override
            protected void execute() {
                for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                    Player player = World.getPlayerByName(playerName);
                    if (player != null) {
                        int y = currentWave == 1 || currentWave == 3 ? tick + 3495 : 3506 - tick;
                        for (int x = 3319; x >= 3312; x--) {
                            player.getPA().sendGraphic(new Graphic(197), new Position(x, y).setZ(getConnectedRaid().getMyHeight()));
                            if (player.getPosition().getX() == x && player.getPosition().getY() == y) {
                                player.dealDamage(new Hit(90, Hitmask.DARK_RED, CombatIcon.MAGIC));
                            }
                        }

                    }
                }
                if (tick == 11) {
                    stop();
                } else {
                    tick++;
                }
            }
        });
    }

    @Override
    public void death(int npcId) {
        markedTileList.forEach(tile -> {
            tile.tileTask.stop();
        });
        markedTileList.clear();
        if (npcId == npcId()) {
            getFightData().setEndTime(System.currentTimeMillis());
            getConnectedRaid().getRaidData().submitFightData(getFightData());
            getConnectedRaid().proceed();
        }
    }

    public class MarkedTile {
        private final int tileX;
        private final int tileY;

        private final Task tileTask;

        public MarkedTile(int tileX, int tileY) {
            this.tileX = tileX;
            this.tileY = tileY;
            tileTask = new Task(1, true) {
                @Override
                protected void execute() {
                    if (!(getConnectedRaid().getCurrentBoss() instanceof RaidBossThree))
                        stop();
                    else for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                        Player player = World.getPlayerByName(playerName);
                        if (player != null) {
                            player.getPA().sendGraphic(new Graphic(currentStage == 0 ? 6 : currentStage == 1 ? 606 : currentStage == 2 ? 482  : 606, 0, GraphicHeight.LOW), new Position(tileX, tileY, player.getPosition().getZ()));
                        }
                    }
                }
            };
            TaskManager.submit(tileTask);
        }

        public boolean onTile(Player player) {
            return player.getPosition().getX() == tileX && player.getPosition().getY() == tileY;
        }
    }


    private int currentStage = 0;

    private int currentWave = 0;
}
