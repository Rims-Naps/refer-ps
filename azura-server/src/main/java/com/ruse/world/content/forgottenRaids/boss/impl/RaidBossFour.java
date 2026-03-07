package com.ruse.world.content.forgottenRaids.boss.impl;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Position;
import com.ruse.model.Prayerbook;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.forgottenRaids.ForgottenRaid;
import com.ruse.world.content.forgottenRaids.boss.ForgottenRaidBoss;
import com.ruse.world.content.forgottenRaids.party.ForgottenRaidParty;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

public class RaidBossFour extends ForgottenRaidBoss {

    @Getter
    private int failedKeys = 0;
    public static final Position[] minionPositions = new Position[]{new Position(3360, 3572), new Position(3359, 3568), new Position(3364, 3564), new Position(3369, 3567), new Position(3374, 3569), new Position(3374, 3565), new Position(3371, 3562), new Position(3368, 3559), new Position(3370, 3555), new Position(3371, 3551)};
    public final static int[] KEY_IDS = new int[]{10188, 10190, 10192, 10194};

    private final int requiredKey = Misc.randomElement(KEY_IDS);
    @Getter
    private int healthIncrease = 0;

    public RaidBossFour(ForgottenRaid connectedRaid) {
        super(connectedRaid);
    }

    @Override
    public void spawn() {
        healthTask();
        for (Position p : minionPositions) {
            NPC npc = new NPC(npcId(), new Position(p.getX(), p.getY(), getConnectedRaid().getMyHeight()));
            double multiplier = 1 + (getConnectedRaid().getParty().getPlayers().size() * 0.25);
            multiplier *= getConnectedRaid().getDifficulty().getHealthMultiplier();
            int health = (int) ((double) health() * multiplier);
            npc.setDefaultConstitution(health);
            npc.setConstitution(health);
            World.register(npc);
            getExtras().add(npc);
        }
    }

    private void healthTask() {
        TaskManager.submit(new Task(100, false) {
            @Override
            protected void execute() {
                if (getConnectedRaid().getCurrentBoss() instanceof RaidBossFour) healthIncrease += 10;
                else stop();
            }
        }.bind(this));
    }

    @Override
    public int npcId() {
        return 1233;
    }

    @Override
    public int health() {
        return 900_000;
    }


    @Override
    public Position position() {
        return null;
    }

    @Override
    public Position afterDeathPosition() {
        return new Position(3358, 3570).setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public String bossName() {
        return "raid-boss-four";
    }

    @Override
    public CombatStrategy combatStrategy() {
        return null;
    }

    public void keyClick(Player player, int keyId) {

        if (!player.getInventory().contains(keyId)) return;
        if (keyId == requiredKey) {
            player.getInventory().delete(keyId, 1);
            getFightData().setEndTime(System.currentTimeMillis());
            getConnectedRaid().getRaidData().submitFightData(getFightData());
            player.getForgottenRaidParty().getRaid().proceed();
        } else {
            player.getInventory().delete(keyId, 1);
            player.sendMessage("You have used an incorrect key!");
            player.moveTo(new Position(3368, 3565).setZ(getConnectedRaid().getMyHeight()));
            failedKeys++;
        }
    }

    public void death(Player player, int npcId) {
        if (npcId == npcId()) {
            if (Misc.random(1) == 0) {
                int key = Misc.randomElement(KEY_IDS);
                player.giveItem(key, 1);
                player.sendMessage("You receive a " + ItemDefinition.forId(key).getName() + " from killing the mob!");
            }
        }
    }

    public void death(int npcId) {
        if (npcId == npcId()) {
            if (Misc.random(25) == 0) {
                Player owner = World.getPlayerByName(getConnectedRaid().getParty().getOwner());
                if (owner != null) owner.giveItem(Misc.randomElement(KEY_IDS), 1);
            }
        }
    }
}
