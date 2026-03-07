package com.ruse.world.content.startertasks;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

public class StarterTasks {

    public static void doProgress(Player player, StarterTask startertask) {
        doProgress(player, startertask, 1);
    }

    public static void doProgress(Player player, StarterTask startertask, int amount) {
            int currentAmount = player.getStarterTasks().getAmountRemaining(startertask.ordinal());
            if (currentAmount < startertask.getAmount() && !player.getStarterTasks().isComplete( startertask.ordinal())) {
                player.getStarterTasks().setAmountRemaining(startertask.ordinal(), currentAmount + amount);
                if ((currentAmount + amount) >= startertask.getAmount()) {
                    player.msgFancyPurp("[PROGRESSION] @red@" + startertask.getDescription()+ " <shad=AF70C3>completed, claim your reward!");
                }
            }
    }

    public static void claimReward(Player player, StarterTask startertask) {
            if (!player.getStarterTasks().isComplete(startertask.ordinal())) {
                int currentAmount = player.getStarterTasks().getAmountRemaining(startertask.ordinal());

                if (currentAmount >= startertask.getAmount()) {
                    if (startertask.getRewards() != null) {
                        player.msgPurp("Your task reward(s) has been added to your account.");
                        player.getStarterTasks().setComplete(startertask.ordinal(), true);
                        for (Item item : startertask.getRewards())
                            player.getInventory().add(item.getId(), item.getAmount());
                        player.getStarterTasks().openInterface();
                    }
                } else {
                    player.msgRed("You have not completed this task yet!");
                }
            } else {
                player.msgRed("You have already claimed this reward.");
            }
    }

    public static void reset(Player player, StarterTask startertask) {
            if (!player.getStarterTasks().isComplete(startertask.ordinal())) {
                player.getStarterTasks().setAmountRemaining(startertask.ordinal(), 0);
            }
    }


    public static int getMaxTasks() {
        return StarterTask.values().length;
    }

    @Getter
    public enum StarterTask {

        KILL_100_NYTHOR("Kill 100 Nythor", 100, new Item(1448, 2),  new Item(20104, 25), new Item(15667, 10)),

        KILL_4_RIFT_BOSSES("Kill 4 Rift Bosses", 4,  new Item(5585, 5), new Item(15667, 5), new Item(20104, 25)),

        VOTE_FOR_VANGUARD("Claim 5 Votes", 5,new Item(10946, 2),   new Item(23020, 4), new Item(10258, 100)),

        MINE_10k_SALT("Mine 10k Salts", 10000, new Item(20049, 10),  new Item(15667, 2),new Item(17490, 3)),

        COMPLETE_5_EASY_TASKS("Complete 5 Easy Tasks", 5, new Item(16415, 1),  new Item(9719, 5), new Item(10946, 1)),

        SALAGE_5_ITEMS("Salvage 5 Items", 5, new Item(20104, 25), new Item(15667, 2),new Item(17490, 2)),

        COMPLETE_5_SOULBANE("Complete 5 SoulBane runs", 5, new Item(621, 1000),  new Item(20082, 1), new Item(20066, 3)),

        KILL_1000_MONSTERS("Kill 1k Monsters", 1000,  new Item(1448, 3),  new Item(15668, 1), new Item(20104, 50)),

        COMPLETE_2_COE("Complete 2 COE", 2,  new Item(10946, 1), new Item(15669, 1),new Item(534, 100)),
        //
        REACH_120_COMBAT("Reach 120 in any Combat Skill", 1, new Item(10946, 2),  new Item(19062, 25), new Item(995, 5000)),

        ;

        private final String description;
        private final int amount;
        private final Item[] rewards;

        StarterTask( String description, int amount,
                    Item... rewards) {
            this.description = description;
            this.amount = amount;
            this.rewards = new Item[3];

            for (int i = 0 ; i < 3; i ++)
                this.rewards[i] = i < rewards.length ? rewards[i] : new Item(-1, 1);

        }


        public static StarterTask getTask(int ordinal) {
            for (StarterTask startertask : values())
                if (startertask.ordinal() == ordinal)
                    return startertask;
            return null;
        }

    }
}
