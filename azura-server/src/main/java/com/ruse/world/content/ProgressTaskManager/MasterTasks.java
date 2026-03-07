package com.ruse.world.content.ProgressTaskManager;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

public class MasterTasks {

    public static void doProgress(Player player, MasterTaskData masterTask) {
        doProgress(player, masterTask, 1);
    }

    public static void doProgress(Player player, MasterTaskData masterTask, int amount) {
        int currentAmount = player.getMasterTasks().getAmountRemaining(masterTask.ordinal());
        if (currentAmount < masterTask.getAmount() && !player.getMasterTasks().isComplete( masterTask.ordinal())) {
            player.getMasterTasks().setAmountRemaining(masterTask.ordinal(), currentAmount + amount);
            if ((currentAmount + amount) >= masterTask.getAmount()) {
                player.msgFancyPurp("[PROGRESSION] @red@" + masterTask.getDescription()+ " <shad=AF70C3>completed, claim your reward!");
            }
        }
    }

    public static void claimReward(Player player, MasterTaskData masterTask) {
        if (!player.getMasterTasks().isComplete(masterTask.ordinal())) {
            int currentAmount = player.getMasterTasks().getAmountRemaining(masterTask.ordinal());

            if (currentAmount >= masterTask.getAmount()) {
                if (masterTask.getRewards() != null) {
                    player.msgPurp("Your task reward(s) has been added to your account.");
                    player.getMasterTasks().setComplete(masterTask.ordinal(), true);
                    for (Item item : masterTask.getRewards())
                        player.getInventory().add(item.getId(), item.getAmount());
                    player.getMasterTasks().openInterface();
                }
            } else {
                player.msgRed("You have not completed this task yet!");
            }
        } else {
            player.msgRed("You have already claimed this reward.");
        }
    }

    public static void reset(Player player, MasterTaskData masterTask) {
        if (!player.getMasterTasks().isComplete(masterTask.ordinal())) {
            player.getMasterTasks().setAmountRemaining(masterTask.ordinal(), 0);
        }
    }


    public static int getMaxTasks() {
        return MasterTaskData.values().length;
    }

    @Getter
    public enum MasterTaskData {

        KILL_35K_INFERNUS("Kill 35k Infernus", 35_000, new Item(17586, 1),  new Item(17584, 1), new Item(17582, 1)),

        CLAIM_25_VOTES("Claim 25 Votes", 25,  new Item(5585, 3), new Item(15667, 2), new Item(20104, 3)),

        KILL_5_EACH_PRAYER_BOSS("Kill 5 Mayhem bosses", 5,new Item(10946, 1),   new Item(23020, 4), new Item(10258, 20)),

        COMPLETE_50_MEDIUM_SLAYER("50 Medium Slayer Tasks", 50, new Item(20049, 10),  new Item(15667, 2),new Item(17490, 3)),

        UPGRADE_3_ITEMS("Upgrade 3 Items", 3, new Item(17128, 3),  new Item(9719, 5), new Item(10946, 1)),

        SALAGE_50_ITEMS("Salvage 50 Items", 50, new Item(20104, 5), new Item(15667, 2),new Item(17490, 2)),

        OFFER_25K_BONES("Offer 25k Bones", 25_000, new Item(621, 1000),  new Item(20082, 1), new Item(20066, 3)),

        OPEN_50_RIFT_KEYS("Open 50 Rift Keys", 50,  new Item(17586, 3),  new Item(17584, 3), new Item(17582, 3)),

        MAKE_25000_ARROWS("Craft 25k Arrows", 25_000,  new Item(10946, 1), new Item(20071, 3),new Item(534, 100)),
        //
        OFFER_20_T1_TOTEMS("Offer 20 T1 Totems", 20, new Item(10946, 2),  new Item(19062, 75), new Item(17490, 5)),

        ;

        private final String description;
        private final int amount;
        private final Item[] rewards;

        MasterTaskData( String description, int amount,
                       Item... rewards) {
            this.description = description;
            this.amount = amount;
            this.rewards = new Item[3];

            for (int i = 0 ; i < 3; i ++)
                this.rewards[i] = i < rewards.length ? rewards[i] : new Item(-1, 1);

        }


        public static MasterTaskData getTask(int ordinal) {
            for (MasterTaskData masterTask : values())
                if (masterTask.ordinal() == ordinal)
                    return masterTask;
            return null;
        }

    }
}
