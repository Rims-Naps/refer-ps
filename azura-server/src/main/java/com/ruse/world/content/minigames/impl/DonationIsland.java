package com.ruse.world.content.minigames.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Animation;
import com.ruse.model.Position;
import com.ruse.model.RegionInstance;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

public class DonationIsland {
//DODIS rework rewards
    public static int[] RARE_REWARDS = new int[] {23058};
    public static int [] UNCOMMON_REWARDS = new int[] {3687};
    public static int[] COMMON = new int[] {10946, 10834};
    public static int[] SUPER_RARE_REWARDS = new int[] {23058};
    public static int[] JACKPOT = new int[] {17704, 23092};

    public static void generateReward(Player player) {
        Random rng = new Random();

        //int seed = player.getPosition().getX() + player.getPosition().getY() + player.getPosition().getZ() + rng.nextInt(1000);
        //.setSeed(seed);
        int roll = rng.nextInt(100);

        if (roll == 0) {

            if (rng.nextInt(2) == 0){
                player.getInventory().add(JACKPOT[rng.nextInt(JACKPOT.length)], 1);
                player.sendMessage("@yel@You hit the JACKPOT!!!");
                World.sendMessage("@red@[ANNOUNCEMENT] - " + player.getUsername() + " just hit the jackpot at the Digsite!");
            } else {
                player.getInventory().add(RARE_REWARDS[rng.nextInt(RARE_REWARDS.length)], 1);
                player.sendMessage("@red@You almost hit the jackpot!!");
            }

        } else if (roll < 11){
            player.getInventory().add(SUPER_RARE_REWARDS[rng.nextInt(SUPER_RARE_REWARDS.length)], 1);
            player.sendMessage("@red@You got a super rare reward!");

        } else if (roll < 31) {
            player.getInventory().add(RARE_REWARDS[rng.nextInt(RARE_REWARDS.length)], 1);
            player.sendMessage("@red@You got a rare reward!");

        } else if (roll < 61) {
            player.getInventory().add(UNCOMMON_REWARDS[rng.nextInt(UNCOMMON_REWARDS.length)], 1);
            player.sendMessage("@red@You got a common reward!");

        } else {
            player.getInventory().add(COMMON[rng.nextInt(COMMON.length)], 1);
            player.sendMessage("@red@You got a basic reward!");
        }
        player.getInventory().delete(5586,1);
        player.moveTo(new Position(3095, 3500));
    }

    public static void teleportPlayer(Player player){
        player.sendMessage("@red@Welcome to the Digsite minigame!");
        player.sendMessage("@red@Choose a spot to dig wisely!");
        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.DONORISLE));
        player.moveTo(new Position(2912, 3744, player.getIndex() * 4));
        if (!player.getInventory().contains(5586)) {
            player.getInventory().add(5586, 1);
        }

    }

    public static void digSpot(Player player){
        if (!player.getClickDelay().elapsed(2000))
            return;
        player.getMovementQueue().reset();
        player.getPacketSender().sendMessage("You start digging..");
        player.performAnimation(new Animation(830));
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {

                if (player.getRegionInstance() == null ){ // || player.getLocation() != Locations.Location.DONORISLE)
                    System.out.println("Player instance == null or player is not at donation island map");
                    return;
                }
                System.out.println("Generating reward...");
                DonationIsland.generateReward(player);
                stop();
            }
        });
        player.getClickDelay().reset();
    }
}
