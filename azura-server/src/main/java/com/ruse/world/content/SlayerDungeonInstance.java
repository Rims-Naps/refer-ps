package com.ruse.world.content;

import com.ruse.model.Position;
import com.ruse.model.RegionInstance;
import com.ruse.world.World;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.instance.DestroyMode;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class SlayerDungeonInstance {

    public static void moveToInstance(Player player, Position position) {
        TeleportHandler.teleportPlayer(player, position, TeleportType.ANCIENT);
    }


    public static void destroySlayerDungeonInstanceAndWait(Player player) {
        CountDownLatch latch = new CountDownLatch(1);

        destroySlayerDungeonInstance(player);

        latch.countDown();

        try {

            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void destroySlayerDungeonInstance(Player player) {


        if (player == null || player.getRegionInstance() == null) {
            return;
        }

        RegionInstance instance = player.getRegionInstance();


        for (NPC npc : instance.getNpcsList()) {
            if (npc != null && npc.getPosition().getZ() == player.getPosition().getZ()) {
                if (npc.getPosition().getRegionId() == player.getPosition().getRegionId()) {
                    World.deregister(npc);
                }
            }
        }

        instance.getNpcsList().clear();



        player.setRegionInstance(null);
        player.sendMessage("Your Dungeon has Ended..");
        player.moveTo(3088,3509,0);

    }


    public static void enterSlayerDungeon(Player player) {
        RegionInstance instance = player.getRegionInstance();
        if (instance != null) {
            destroySlayerDungeonInstance(player);
        }

        final int height = player.getIndex() * 4;

        SlayerMaster master = player.getSlayer().getSlayerMaster();
        if (master == null) {
            return;
        }

        Position dungeonCenter = null;
        switch (master) {

        }

        if (instance == null) {
            instance = new RegionInstance(player, RegionInstance.RegionInstanceType.SLAYERTASK);
            player.setRegionInstance(instance);

            Random rng = new Random();
            int minX = dungeonCenter.getX() - 9;
            int minY = dungeonCenter.getY() - 9;
            int maxX = dungeonCenter.getX() + 8;
            int maxY = dungeonCenter.getY() + 8;
            for (int i = 0; i < player.getSlayer().getAmountToSlay(); i++) {
                Position npcPosition = null;
                boolean isValidPosition = false;
                while (!isValidPosition) {
                    int x = minX + rng.nextInt(maxX - minX + 1);
                    int y = minY + rng.nextInt(maxY - minY + 1);
                    npcPosition = new Position(x, y, height);
                    isValidPosition = true;
                    for (NPC otherNpc : instance.getNpcsList()) {
                        if (otherNpc == null || otherNpc.getPosition() == null) {
                            continue;
                        }


                    }
                }
                NPC npc = new NPC(player.getSlayer().getSlayerTask().getNpcId(), npcPosition);
                npc.setRegionInstance(instance);
                instance.getNpcsList().add(npc);
                World.register(npc);
                //player.sendMessage("NPC " + npc.getId() + " spawned at " + npcPosition);
            }

            moveToInstance(player, dungeonCenter);
        }
    }


}
