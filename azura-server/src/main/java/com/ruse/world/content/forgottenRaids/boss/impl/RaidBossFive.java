package com.ruse.world.content.forgottenRaids.boss.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.forgottenRaids.ForgottenRaid;
import com.ruse.world.content.forgottenRaids.boss.ForgottenRaidBoss;
import com.ruse.world.entity.Entity;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RaidBossFive extends ForgottenRaidBoss {
    private final int healthIncrease;

    private static final int MINION_ID = 3058;
    private static final int MINION_HEALTH = 3_000_000;

    private static int getOrbId(int currentPrayer) {
        switch (currentPrayer) {
            case PrayerHandler.PROTECT_FROM_MELEE:
                return 1013;
            case PrayerHandler.PROTECT_FROM_MAGIC:
                return 1010;
            case PrayerHandler.PROTECT_FROM_MISSILES:
                return 1000;
            default:
                return -1;
        }
    }

    private boolean orbDelay = false;
    private final Task orbTask = new Task(5, false) {
        int playerIndex = 0;
        int lastAttackedPlayer = 0;

        @Override
        protected void execute() {
            if (getConnectedRaid().getCurrentBoss() instanceof RaidBossFive) {
                if (playerIndex >= getConnectedRaid().getParty().getPlayers().size()) playerIndex = 0;
                if (!orbDelay) {
                    Player target = World.getPlayerByName(getConnectedRaid().getParty().getPlayers().get(playerIndex));
                    if (target != null && target.getPosition().isWithinDistance(getBossNpc().getPosition(), 10)) {
                        Entity source;
                        if (lastAttackedPlayer != playerIndex && lastAttackedPlayer < getConnectedRaid().getParty().getPlayers().size()) {
                            Player lastAttacked = World.getPlayerByName(getConnectedRaid().getParty().getPlayers().get(lastAttackedPlayer));
                            source = lastAttacked != null ? lastAttacked : getBossNpc();
                        } else {
                            source = getBossNpc();
                            new Projectile(getBossNpc(), target, 2101, 44, 3, 43, 43, 0).sendProjectile();
                           // target.forceChat("Pray"+ currentPrayer);
                        }
                        if (!PrayerHandler.isActivated(target, currentPrayer)) {
                            target.dealDamage(new Hit(Misc.random(80, 120)));
                        }
                        new Projectile(source, target, getOrbId(currentPrayer), 44, 3, 43, 43, 0).sendProjectile();
                        lastAttackedPlayer = playerIndex;
                       // target.forceChat("Pray"+ currentPrayer);
                    }
                    playerIndex++;
                }
            } else {
                stop();
            }
        }
    };

    private static final int[] VALID_PRAYERS = new int[]{PrayerHandler.PROTECT_FROM_MELEE, PrayerHandler.PROTECT_FROM_MAGIC, PrayerHandler.PROTECT_FROM_MISSILES};
    private int currentPrayer = Misc.randomElement(VALID_PRAYERS);
    private int orbStage = 0;
    private int icicleStage = 0;


    public RaidBossFive(ForgottenRaid connectedRaid, int healthIncrease) {
        super(connectedRaid);
        this.healthIncrease = healthIncrease;
    }

    @Override
    public void spawn() {
        setBossNpc(new NPC(npcId(), position().copy().setZ(getConnectedRaid().getMyHeight())));
        double multiplier = 1 + (getConnectedRaid().getParty().getPlayers().size()*0.25);
        multiplier *= getConnectedRaid().getDifficulty().getHealthMultiplier();
        int health = ((int) ((double) health() * multiplier));
        health += (int) ((double) health * ((double) healthIncrease / 100D));
        getBossNpc().setDefaultConstitution(health);
        getBossNpc().setConstitution(health);
        World.register(getBossNpc());
        getBossNpc().setStrategy(combatStrategy());
        TaskManager.submit(orbTask);
    }

    @Override
    public int npcId() {
        return 3057;
    }

    @Override
    public int health() {
        return 20_000_000;
    }

    @Override
    public Position position() {
        return new Position(3371, 3483).copy().setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public Position afterDeathPosition() {
        return new Position(3371, 3498).setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public String bossName() {
        return "raid-boss-five";
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
                if (getConnectedRaid().getParty() == null || !(getConnectedRaid().getCurrentBoss() instanceof RaidBossFive))
                    return false;

                int healthPercentage = (int) (((double) getBossNpc().getConstitution() / (double) getBossNpc().getDefaultConstitution()) * 100D);
                if (orbStage == 0 && healthPercentage < 80 || orbStage == 1 && healthPercentage < 60 || orbStage == 2 && healthPercentage < 40 || orbStage == 3 && healthPercentage < 20) {
                    int oldPrayer = currentPrayer;
                    while (currentPrayer == oldPrayer) currentPrayer = Misc.randomElement(VALID_PRAYERS);
                    orbStage++;

                    String quote = getQuote(currentPrayer);
                    getBossNpc().forceChat(quote);
                    orbDelay = true;
                    TaskManager.submit(new Task(10, false) {
                        @Override
                        protected void execute() {
                            orbDelay = false;
                            stop();
                        }
                    });
                }
                if (icicleStage == 0 && healthPercentage < 90 || icicleStage == 1 && healthPercentage < 70 || icicleStage == 2 && healthPercentage < 10) {
                    getBossNpc().forceChat("Feel the cold, deadly touch of the Ice!");
                    TaskManager.submit(new Task(1, true) {
                        int tick = 0;
                        final List<Position> iciclePositions = new ArrayList<>();

                        @Override
                        protected void execute() {
                            if (tick == 0) {
                                getBossNpc().performAnimation(new Animation(11227));
                            }
                            if (tick == 1) {
                                int x = getBossNpc().getPosition().getX() - 7;
                                int y = getBossNpc().getPosition().getY() - 7;
                                for (int startX = x; startX < x + 15; startX++) {
                                    for (int startY = y; startY < y + 15; startY++) {
                                        if (Misc.random(10) == 0) {
                                            for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                                                Player player = World.getPlayerByName(playerName);
                                                if (player != null) {
                                                    player.getPA().sendGraphic(new Graphic(1014), new Position(startX, startY).setZ(getConnectedRaid().getMyHeight()));
                                                }
                                            }
                                            iciclePositions.add(new Position(startX, startY).setZ(getConnectedRaid().getMyHeight()));
                                        }
                                    }
                                }
                            }
                            if (tick == 5) {
                                for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                                    Player player = World.getPlayerByName(playerName);
                                    if (player != null) {
                                        if (iciclePositions.stream().anyMatch(p -> p.equals(player.getPosition()))) {
                                            player.performGraphic(new Graphic(80));
                                            player.dealDamage(new Hit(Misc.random(5, 20)));
                                            player.getMovementQueue().stun(5);
                                            player.sendMessage("The Ice Queen stuns you!");
                                        }
                                    }
                                }
                            }
                            if (tick == 6) {
                                stop();
                            } else {
                                tick++;
                            }
                        }
                    });
                    icicleStage++;
                }
                for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                    Player player = World.getPlayerByName(playerName);
                    if (player != null) {
                        player.dealDamage(new Hit(Misc.random(10, 50)));
                        player.performGraphic(new Graphic(1292));
                    }
                }
                boolean spawnMinions = ((healthPercentage <= 60 && minionsStage == 0) || (healthPercentage <= 5 && minionsStage == 1));
                if (spawnMinions) {
                    String quote = getQuote(currentPrayer);
                    getBossNpc().forceChat(quote);
                    getBossNpc().performAnimation(new Animation(10252));
                    spawnMinions();
                    minionsStage++;
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

    private static String getQuote(int prayer) {
        switch (prayer) {
            case PrayerHandler.PROTECT_FROM_MELEE:
                return "Melee";
            case PrayerHandler.PROTECT_FROM_MAGIC:
                return "Magic";
            case PrayerHandler.PROTECT_FROM_MISSILES:
                return "Ranged";
            default:
                return "";
        }
    }

    public void death(int npcId) {
        if (npcId == npcId()) {
            if (orbTask.isRunning()) orbTask.stop();
            getFightData().setEndTime(System.currentTimeMillis());
            getConnectedRaid().getRaidData().submitFightData(getFightData());
            getConnectedRaid().proceed();
        } else if (npcId == MINION_ID) {
            minionsKilled++;
            if (minionsKilled == 2) {
                minionsAlive = false;
                minionsKilled = 0;
            }
        }
    }

    private boolean minionsAlive = false;
    public boolean killedMinions() {
        return !minionsAlive;
    }
    private int minionsKilled = 0;

    private int minionsStage = 0;

    private void spawnMinions() {
        for (int i = 0; i < 2; i++) {
            Position minionPosition = position().copy();
            minionPosition.setY(i == 0 ? minionPosition.getY() - 2 : minionPosition.getY() + 2);
            NPC npc = new NPC(MINION_ID, minionPosition);
            double multiplier = 1 + (getConnectedRaid().getParty().getPlayers().size()*0.10);
            multiplier *= getConnectedRaid().getDifficulty().getHealthMultiplier();
            int health = (int) ((double) MINION_HEALTH * multiplier);
            npc.setDefaultConstitution(health);
            npc.setAggressive(true);
            npc.setConstitution(health);
            npc.getDefinition().setRespawnTime(-1);
            World.register(npc);
            getExtras().add(npc);
            npc.performGraphic(new Graphic(2104));
        }
        minionsAlive = true;
        TaskManager.submit(new Task(1, true) {
            @Override
            protected void execute() {
                if (killedMinions())
                    stop();
                else {
                    getBossNpc().heal((int) ((double) getBossNpc().getDefaultConstitution() * 0.02D));
                    getBossNpc().performGraphic(new Graphic(606));
                    getExtras().removeIf(n -> n == null || !n.isRegistered());
                    for (NPC npc : getExtras()) {
                        if (npc != null && npc.isRegistered()) {
                            npc.performGraphic(new Graphic(606));
                        }
                    }
                }
            }
        });
    }
}
