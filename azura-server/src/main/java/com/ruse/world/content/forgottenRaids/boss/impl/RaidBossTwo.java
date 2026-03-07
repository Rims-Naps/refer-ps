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
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class RaidBossTwo extends ForgottenRaidBoss {

    private static final int MINION_ID = 3055;
    private static final int MINION_HEALTH = 3_000_000;


    public RaidBossTwo(ForgottenRaid connectedRaid) {
        super(connectedRaid);
    }

    @Override
    public void spawn() {
        super.spawn();
    }

    @Override
    public int npcId() {
        return 3302;
    }

    @Override
    public int health() {
        return 15_000_000;
    }

    @Override
    public Position position() {
        return new Position(3277, 3564).copy().setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public Position afterDeathPosition() {
        return new Position(3288, 3556).setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public String bossName() {
        return "raid-boss-two";
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
                NPC boss = (NPC) entity;
                if (getConnectedRaid().getParty() == null || !(getConnectedRaid().getCurrentBoss() instanceof RaidBossTwo))
                    return false;

                int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
                boolean spawnMinions = ((healthPercentage <= 70 && currentStage == 0) || (healthPercentage <= 45 && currentStage == 1) || (healthPercentage <= 10 && currentStage == 2));
                if (spawnMinions) {
                    boss.forceChat("MINIONS, DEFEND ME!!");
                    spawnMinions();
                    currentStage++;
                } else if (killedMinions()) {
                    for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                        Player partyMember = World.getPlayerByName(playerName);
                        if (partyMember != null) {
                            regularAttack(partyMember, boss);
                        }
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

    public void regularAttack(Player player, NPC boss) {
        boolean extraDamage = Misc.random(20) == 0;
        int lowerBound = 125;
        int upperBound = 342 * (extraDamage ? 1 : 2);
        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        player.dealDamage(new Hit(Misc.random(lowerBound, upperBound), Hitmask.NEUTRAL, CombatIcon.RANGED));
        if (extraDamage) {
            player.performGraphic(new Graphic(0));
        }
        new Projectile(boss, player, extraDamage ? 1360 : 270, 44, 3, 43, 43, 0).sendProjectile();
    }

    private void spawnMinions() {
        for (int i = 0; i < 2; i++) {
            Position minionPosition = position().copy();
            minionPosition.setY(i == 0 ? minionPosition.getY() - 2 : minionPosition.getY() + 2);
            NPC npc = new NPC(MINION_ID, minionPosition);
            double multiplier = 1 + (getConnectedRaid().getParty().getPlayers().size()*0.25);
            multiplier *= getConnectedRaid().getDifficulty().getHealthMultiplier();
            int health = (int) ((double) MINION_HEALTH * multiplier);
            npc.setDefaultConstitution(health);
            npc.setAggressive(true);
            npc.setConstitution(health);
            npc.getDefinition().setRespawnTime(-1);
            World.register(npc);
            getExtras().add(npc);
            npc.performGraphic(new Graphic(8));
        }
        minionsAlive = true;
        TaskManager.submit(new Task(4, true) {
            @Override
            protected void execute() {
                if (killedMinions())
                    stop();
                else {
                    if (getBossNpc() != null) {
                        getBossNpc().heal((int) ((double) getBossNpc().getDefaultConstitution() * 0.05D));
                        getBossNpc().performGraphic(new Graphic(1308));
                        getExtras().removeIf(n -> n == null || !n.isRegistered());
                        for (NPC npc : getExtras()) {
                            if (npc != null && npc.isRegistered()) {
                                npc.performGraphic(new Graphic(1308));
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean minionsAlive = false;

    public boolean killedMinions() {
        return !minionsAlive;
    }

    @Override
    public void death(int npcId) {
        if (npcId == npcId()) {
            instantDeath();
        } else if (npcId == MINION_ID) {
            minionsKilled++;
            if (minionsKilled == 2) {
                minionsAlive = false;
                minionsKilled = 0;
            }
        }
    }

    private void instantDeath() {
        TaskManager.submit(new Task(1, false) {
            int tick = 0;

            @Override
            protected void execute() {
                if (tick < 2) {
                    for (int x = 3276; x < 3285; x++) {
                        for (int y = 3557; y < 3569; y++) {
                            for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                                Player player = World.getPlayerByName(playerName);
                                if (player != null) {
                                    player.getPA().sendGraphic(new Graphic(1679), new Position(x, y, player.getPosition().getZ()));
                                }
                            }
                        }
                    }
                } else if (tick == 6) {
                    for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                        Player player = World.getPlayerByName(playerName);
                        if (player != null) {
                            for (int x = 3276; x < 3285; x++) {
                                for (int y = 3558; y < 3570; y++) {
                                    player.getPA().sendGraphic(new Graphic(266), new Position(x, y, player.getPosition().getZ()));
                                }
                            }
                            Position p = player.getPosition();
                            if (p.getX() >= 3276 && p.getX() < 3285 && p.getY() >= 3557 && p.getY() < 3569) {
                                player.dealDamage(new Hit(120));
                            }
                        }
                    }

                } else if (tick == 10) {
                    getFightData().setEndTime(System.currentTimeMillis());
                    getConnectedRaid().getRaidData().submitFightData(getFightData());
                    getConnectedRaid().proceed();
                    stop();
                }
                tick++;
            }
        });
    }

    private int minionsKilled = 0;
    private int currentStage = 0;

}
