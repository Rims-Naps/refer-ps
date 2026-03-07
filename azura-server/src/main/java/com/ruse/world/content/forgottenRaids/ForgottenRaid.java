package com.ruse.world.content.forgottenRaids;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Position;
import com.ruse.model.Prayerbook;
import com.ruse.model.input.impl.EnterPinPacketListener;
import com.ruse.world.World;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.forgottenRaids.boss.ForgottenRaidBoss;
import com.ruse.world.content.forgottenRaids.boss.impl.*;
import com.ruse.world.content.forgottenRaids.data.RaidData;
import com.ruse.world.content.forgottenRaids.party.ForgottenRaidParty;
import com.ruse.world.content.forgottenRaids.rewards.RaidRewardsRoom;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

@Getter
public class ForgottenRaid {

    private RaidData raidData;
    private final static Position startPosition = new Position(3278, 3464);
    private static int currentHeight = 0;
    private int myHeight = 0;
    private ForgottenRaidParty party;
    private ForgottenRaidDifficulty difficulty;

    public ForgottenRaid(ForgottenRaidParty party) {
        this.party = party;
        this.difficulty = party.getDifficulty();
        raidData = new RaidData(this);
    }
    private ForgottenRaidBoss currentBoss = new RaidBossOne(this);

    public void start() {
        party.getPlayers().forEach(playerName -> {
            Player player = World.getPlayerByName(playerName);
            if (player != null) {
                player.getPA().sendInterfaceRemoval();
                player.moveTo(startPosition.copy().setZ(currentHeight));
            }
        });
        myHeight = currentHeight;
        currentBoss.spawn();
        currentHeight += 4;
        raidData.startRaid();
    }

    public void proceed() {
        if (currentBoss != null) {
            currentBoss.dispose();
            if (currentBoss instanceof RaidBossOne) {
                currentBoss = new RaidBossTwo(this);
                currentBoss.spawn();
                party.getPlayers().forEach(playerName -> {
                    Player player = World.getPlayerByName(playerName);
                    if (player != null) {
                        player.performAnimation(new Animation(8939));
                        player.performGraphic(new Graphic(1577));
                        TaskManager.submit(new Task(1, player, true) {
                            @Override
                            protected void execute() {
                                player.moveTo(3290,3554, getMyHeight());
                                stop();
                                player.performAnimation(new Animation(8941));
                            }
                        });
                    }
                });
            } else if (currentBoss instanceof RaidBossTwo) {
                currentBoss = new RaidBossThree(this);
                currentBoss.spawn();
                party.getPlayers().forEach(playerName -> {
                    Player player = World.getPlayerByName(playerName);
                    if (player != null) {
                        player.performAnimation(new Animation(8939));
                        player.performGraphic(new Graphic(1577));
                        TaskManager.submit(new Task(1, player, true) {
                            @Override
                            protected void execute() {
                                player.moveTo(3306,3514, getMyHeight());
                                stop();
                                player.performAnimation(new Animation(8941));

                            }
                        });
                    }
                });
            } else if (currentBoss instanceof RaidBossThree) {
                currentBoss = new RaidBossFour(this);
                currentBoss.spawn();
                party.getPlayers().forEach(playerName -> {
                    Player player = World.getPlayerByName(playerName);
                    if (player != null) {
                        player.performAnimation(new Animation(8939));
                        player.performGraphic(new Graphic(1577));
                        TaskManager.submit(new Task(1, player, true) {
                            @Override
                            protected void execute() {
                                player.moveTo(3353,3571, getMyHeight());
                                stop();
                                player.performAnimation(new Animation(8941));

                            }
                        });
                    }
                });
            } else if (currentBoss instanceof RaidBossFour) {
                int healthIncrease = ((RaidBossFour) currentBoss).getHealthIncrease();
                currentBoss = new RaidBossFive(this, healthIncrease);
                currentBoss.spawn();
                party.getPlayers().forEach(playerName -> {
                    Player player = World.getPlayerByName(playerName);
                    if (player != null) {
                        player.moveTo(3371,3518, getMyHeight());
                        player.sendMessage("Your Team has made it through the gate!");
                        player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, Prayerbook.NORMAL.getInterfaceId());
                        PrayerHandler.deactivateAll(player);
                        CurseHandler.deactivateAll(player);
                        player.setPrayerbook(Prayerbook.NORMAL);
                        player.getInventory().delete(10188, 28);
                        player.getInventory().delete(10190, 28);
                        player.getInventory().delete(10192, 28);
                        player.getInventory().delete(10194, 28);
                    }
                });
            } else if (currentBoss instanceof RaidBossFive){
                raidData.endRaid();
                raidData = null;
                currentBoss = new RaidRewardsRoom(this);
                currentBoss.spawn();
            } else if (currentBoss instanceof RaidRewardsRoom){
                end();

            }
        }
    }

    private void end() {
        getParty().setInRaid(false);
        dispose();
        getParty().setRaid(null);
    }

    public void handleDeath(Player killer, int npc) {
        if (currentBoss != null) {
            if (currentBoss instanceof RaidBossFour)
                ((RaidBossFour)currentBoss).death(killer, npc);
            else {
                currentBoss.death(npc);
            }
        }
    }

    public void dispose() {
        if (currentBoss != null) {
            currentBoss.dispose();
        }
        currentBoss = null;
        party = null;
        difficulty = null;
    }
}
