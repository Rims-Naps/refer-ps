package com.ruse.world.content.minigames.impl;

import com.ruse.model.GameObject;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.content.casketopening.BoxLoot;
import com.ruse.world.content.collectionlogs.CollectionLogs;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class CircleOfElements {
    public static final Position TELEPORT_AREA = new Position(2911, 2587, 0);
    public static final int CHEST_ID = 29577;
    public static final Object[][] data = {{"Test", 126507}, {"Test", 126508}, {"Test", 126509}};
    public static Box[] loot = {

            new Box(995, 25, 750, 100D),//COINS

            new Box(15667, 1, 1, 15D),//REWARDS
            new Box(730, 1, 1, 15D),//REWARDS
            new Box(20071, 1, 1, 15D),//REWARDS


            new Box(20030, 1, 1, 10D),
            new Box(20035, 1, 1, 10D),
            new Box(20040, 1, 1, 10D),


            new Box(20031, 1, 1, 5D),
            new Box(20036, 1, 1, 5D),
            new Box(20041, 1, 1, 5D),


            new Box(20032, 1, 1, 3D),
            new Box(20037, 1, 1, 3D),
            new Box(20042, 1, 1, 3D),


            new Box(20033, 1, 1, 2D),
            new Box(20038, 1, 1, 2D),
            new Box(20043, 1, 1, 2D),


            new Box(20034, 1, 1, 1.5D),
            new Box(20039, 1, 1, 1.5D),
            new Box(20044, 1, 1, 1.5D),
            new Box(20046, 1, 1, 0.1D),
            new Box(20045, 1, 1, 0.1D),
    };

    private final Player player;
    public CircleOfElements(Player player) {
        this.player = player;
    }

    public void initArea() {
        TeleportHandler.teleportPlayer(player, CircleOfElements.TELEPORT_AREA.copy().setZ(0), TeleportType.NORMAL);
        resetBarrows(player);
    }

    public void start() {
       // player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.VOID_OF_DARKNESS));
    }


    public static void updateInterface(Player player) {
        for (int i = 0; i < data.length; i++) {
            boolean killed = player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[i][1] == 2;
            String s = killed ? "@gre@" : "@red@";
            player.getPacketSender().sendString((int) data[i][1], "" + s + ""
                    + NpcDefinition.forId(player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[i][0]).getName());
        }

        player.getPacketSender().sendString(126506, "Circle of Elements");
        player.getPacketSender().sendString(126510, "Kills: " + player.getMinigameAttributes().getVoidOfDarknessAttributes().getKillcount());
    }

    public static void resetBarrows(Player player) {
        player.getMinigameAttributes().getVoidOfDarknessAttributes().setKillcount(0);
        player.getPA().sendEntityHintRemoval(true);
        for (int i = 0; i < player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData().length; i++)
            player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[i][1] = 0;
    }

    public static void handleLogin(Player player) {
        updateInterface(player);
    }

    public void handleGameObjects(GameObject object) {
        if (object.getId() == CHEST_ID) {
            if (player.getMinigameAttributes().getVoidOfDarknessAttributes().getKillcount() < 3) {
                player.getPacketSender().sendMessage("You have not searched all the coffins yet.");
                return;
            }
            if (player.getInventory().getFreeSlots() < 1) {
                player.getPacketSender()
                        .sendMessage("You need at least 1 free inventory slot to loot this chest.");
                return;
            }

            resetBarrows(player);
            handleRewards(player);
            updateInterface(player);
            return;
        }

        int rng = Misc.inclusiveRandom(1,3);

        switch (object.getId()) {
            case 41202:
                switch (rng){
                    case 1:
                        searchCoffin(object.getId(), 2, 12239, new Position(2786 + Misc.inclusiveRandom(4), 2646 - Misc.random(4), player.getPosition().getZ()));
                        break;
                    case 2:
                        searchCoffin(object.getId(), 2, 12239, new Position(2778 + Misc.inclusiveRandom(4), 2653 - Misc.random(4), player.getPosition().getZ()));
                        break;
                    case 3:
                        searchCoffin(object.getId(), 2, 12239, new Position(2794 + Misc.inclusiveRandom(4), 2656 - Misc.random(4), player.getPosition().getZ()));
                        break;
                }
                return;
            case 41201:
                switch (rng){
                    case 1:
                        searchCoffin(object.getId(), 1, 9814, new Position(2786 + Misc.inclusiveRandom(4), 2517 - Misc.random(4), player.getPosition().getZ()));
                        break;
                    case 2:
                        searchCoffin(object.getId(), 1, 9814, new Position(2777 + Misc.inclusiveRandom(4), 2527 - Misc.random(4), player.getPosition().getZ()));
                        break;
                    case 3:
                        searchCoffin(object.getId(), 1, 9814, new Position(2794 + Misc.inclusiveRandom(4), 2528 - Misc.random(4), player.getPosition().getZ()));
                        break;
                }
                return;
            case 41200:
                switch (rng){
                    case 1:
                        searchCoffin(object.getId(), 0, 9813, new Position(2850 + Misc.inclusiveRandom(4), 2519 - Misc.random(4), player.getPosition().getZ()));
                        break;
                    case 2:
                        searchCoffin(object.getId(), 0, 9813, new Position(2842 + Misc.inclusiveRandom(4), 2527 - Misc.random(4), player.getPosition().getZ()));
                        break;
                    case 3:
                        searchCoffin(object.getId(), 0, 9813, new Position(2858 + Misc.inclusiveRandom(4), 2523 - Misc.random(4), player.getPosition().getZ()));
                        break;
                }
                return;
        }
    }

    public void searchCoffin(int obj, int coffinId, int npcId, Position spawnPos) {
        player.getPacketSender().sendInterfaceRemoval();
        NPC currentSpawn = player.findSpawnedFor();

        int coffinState = player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[coffinId][1];
        if (coffinState == 0) {
                NPC npc_ = new NPC(npcId, spawnPos);
                World.register(npc_);
                npc_.forceChat(player.getPosition().getZ() == -1 ? "You dare disturb my rest!" : "You dare steal from us!");
                npc_.getCombatBuilder().setAttackTimer(3);
                npc_.setSpawnedFor(player);
                npc_.getMovementQueue().setFollowCharacter(player);
                npc_.getCombatBuilder().attack(player);
                player.getPacketSender().sendEntityHint(npc_);
                player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[coffinId][1] = 1;

        } else if (coffinState == 1 && currentSpawn == null) {// Reset coffin when player has left
            player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[coffinId][1] = 0;
            player.msgRed("The entity within slumbers deeply...");
        } else {
            player.msgRed("You have already searched this Coffin.");
        }
    }

    public void killBarrowsNpc(Player player, NPC n, boolean killed) {
        int arrayIndex = getBarrowsIndex(n.getId());
        if (killed) {
            player.getMinigameAttributes().getVoidOfDarknessAttributes()
                    .setKillcount(player.getMinigameAttributes().getVoidOfDarknessAttributes().getKillcount() + 1);
            if (player.getRegionInstance() != null) {
                player.getRegionInstance().getNpcsList().remove(n);
            }

            player.getPA().sendEntityHintRemoval(true);
            player.setVodKilled(player.getVodKilled() + 1);
            player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[arrayIndex][1] = 2;
        } else player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[arrayIndex][1] = 0;

        updateInterface(player);
    }

    public int getBarrowsIndex(int id) {
        int index = -1;
        for (int i = 0; i < player.getMinigameAttributes().getVoidOfDarknessAttributes()
                .getBarrowsData().length; i++) {
            if (player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()[i][0] == id) {
                index = i;
            }
        }

        return index;
    }

    public static void handleRewards(Player player) {

        Box box = BoxLoot.getLoot(loot);
        int amount = box.getAmount();
        player.setCOECompletions(player.getCOECompletions() + 1);
        Achievements.doProgress(player, Achievements.Achievement.COMPLETE_1_COE, 1);
        Achievements.doProgress(player, Achievements.Achievement.COMPLETE_5_COE, 1);
        Achievements.doProgress(player, Achievements.Achievement.COMPLETE_25_COE, 1);
        Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_COE, 1);
        Achievements.doProgress(player, Achievements.Achievement.COMPLETE_250_COE, 1);
        Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_COE, 1);


        StarterTasks.doProgress(player, StarterTasks.StarterTask.COMPLETE_2_COE, 1);
        if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()){
            MediumTasks.doProgress(player, MediumTasks.MediumTaskData.COMPLETE_25_COE, 1);
        }
        if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()){
            EliteTasks.doProgress(player, EliteTasks.EliteTaskData.COMPLETE_100_COE, 1);
        }

        int valExp = Misc.random(2000, 3000);
        if (player.getBattlePass().getType() == BattlePassType.TIER2 || player.getBattlePass().getType() == BattlePassType.TIER1) {
            player.getBattlePass().addExperience(Misc.random(valExp));
            player.msgFancyPurp("You received " + valExp + " Battle Pass Experience for completing COE!");
        }

        player.msgRed("You have now completed " + player.getCOECompletions() + " runs of COE.");
        player.getInventory().add(box.getId(), amount);
        player.getPacketSender().sendMessage("You have received x" + amount + " " + ItemDefinition.forId(box.getId()).getName());
        player.getCollectionLogManager().addItem(CollectionLogs.CIRCLE_OF_ELEMENTS, new Item(box.getId()));
        player.getPA().sendEntityHintRemoval(true);


        if (box.getId() == 20032 || box.getId() == 20037 || box.getId() == 20042
            || box.getId() == 20033 || box.getId() == 20038 || box.getId() == 20043
            || box.getId() == 20034 || box.getId() == 20039 || box.getId() == 20044 || box.getId() == 20045 || box.getId() == 20046){
            World.sendMessage("<shad=0><col=AF70C3>[COE] @red@" + player.getUsername() + " <col=AF70C3>received a @red@" + ItemDefinition.forId(box.getId()).getName() + " <col=AF70C3>from Circle of Elements!");
        }

        resetBarrows(player);

    }
}




