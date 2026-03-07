package com.ruse.world.content.forgottenRaids.boss.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.forgottenRaids.ForgottenRaid;
import com.ruse.world.content.forgottenRaids.boss.ForgottenRaidBoss;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RaidBossOne extends ForgottenRaidBoss {
    private List<String> consumedRockCake = new ArrayList<>();

    public RaidBossOne(ForgottenRaid connectedRaid) {
        super(connectedRaid);
    }

    @Override
    public void spawn() {

        super.spawn();
    }

    private void spawnRockCakes() {

    }


    @Override
    public int npcId() {
        return 3301;
    }

    @Override
    public int health() {
        return 15_000_000;
    }

    @Override
    public Position position() {
        return new Position(3277, 3495).copy().setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public Position afterDeathPosition() {
        return new Position(3277, 3479).setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public String bossName() {
        return "raid-boss-one";
    }


    public void regularAttack(Player player, NPC boss) {
        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        player.dealDamage(new Hit(Misc.random(12, 34), Hitmask.NEUTRAL, CombatIcon.RANGED));
        new Projectile(boss, player, 405, 44, 3, 43, 43, 0).sendProjectile();
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
                if (getConnectedRaid().getParty() == null || !(getConnectedRaid().getCurrentBoss() instanceof RaidBossOne))
                    return false;

                int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
                boolean rockAttack = ((healthPercentage <= 95 && currentStage == 0) || (healthPercentage <= 70 && currentStage == 1) || (healthPercentage <= 50 && currentStage == 2) || (healthPercentage <= 25 && currentStage == 3) || (healthPercentage <= 5 && currentStage == 4));
                if (rockAttack) {
                    boss.performAnimation(new Animation(2588));
                    boss.forceChat("I WILL CRUSH YOU!");
                    currentStage++;
                }
                for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                    Player partyMember = World.getPlayerByName(playerName);
                    if (partyMember != null) {
                        if (rockAttack) rockFall(partyMember);
                        else regularAttack(partyMember, boss);
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

    private void rockFall(Player victim) {
        Position initialPosition = victim.getPosition().copy();

        TaskManager.submit(new Task(1, true) {
            int tick = 0;

            @Override
            protected void execute() {
                if (tick == 3) {
                    victim.getForgottenRaidParty().getPlayers().forEach(playerName -> {
                        Player player = World.getPlayerByName(playerName);

                        if (player != null) {
                            player.getPA().sendGraphic(new Graphic(406), initialPosition);
                            //player.getPA().sendGraphic(new Graphic(405), initialPosition);

                        }
                    });
                }
                if (tick == 4) {
                    if (victim.getPosition().equals(initialPosition)) {
                        victim.dealDamage(new Hit(Misc.random(80, 100), Hitmask.DARK_CRIT, CombatIcon.NONE));
                        victim.performGraphic(new Graphic(2451));

                        victim.getPA().sendCameraShake(3, 2, 3, 10);
                        if (!consumedRockCake.contains(victim.getUsername())) {
                            victim.getMovementQueue().stun(3);
                            victim.performGraphic(new Graphic(542));
                            victim.forceChat("Ouch...That really Hurt");
                           // victim.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, Prayerbook.HOLY.getInterfaceId());
                            PrayerHandler.deactivateAll(victim);
                            CurseHandler.deactivateAll(victim);
                        }
                    }
                }
                if (tick == 7) {
                    victim.getPA().sendCameraNeutrality();
                    stop();
                } else {
                    tick++;
                }
            }
        }.bind(victim));
    }

    @Override
    public void death(int npcId) {
        if (npcId == npcId()) {
            for (String playerName : getConnectedRaid().getParty().getPlayers()) {
                Player partyMember = World.getPlayerByName(playerName);
                if (partyMember != null) {
                    rockFall(partyMember);
                }
            }
            TaskManager.submit(new Task(10, false) {
                @Override
                protected void execute() {
                    getFightData().setEndTime(System.currentTimeMillis());
                    getConnectedRaid().getRaidData().submitFightData(getFightData());
                    getConnectedRaid().proceed();
                    stop();
                }
            });
        }
    }

    private int currentStage = 0;
}
