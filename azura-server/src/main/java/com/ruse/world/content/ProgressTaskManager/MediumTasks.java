package com.ruse.world.content.ProgressTaskManager;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

public class MediumTasks {

    public static void doProgress(Player player, MediumTaskData mediumTask) {
        doProgress(player, mediumTask, 1);
    }

    public static void doProgress(Player player, MediumTaskData mediumTask, int amount) {
        int currentAmount = player.getMediumTasks().getAmountRemaining(mediumTask.ordinal());
        if (currentAmount < mediumTask.getAmount() && !player.getMediumTasks().isComplete( mediumTask.ordinal())) {
            player.getMediumTasks().setAmountRemaining(mediumTask.ordinal(), currentAmount + amount);
            if ((currentAmount + amount) >= mediumTask.getAmount()) {
                player.msgFancyPurp("[PROGRESSION] @red@" + mediumTask.getDescription()+ " <shad=AF70C3>completed, claim your reward!");
            }
        }
    }

    public static void claimReward(Player player, MediumTaskData mediumTask) {
        if (!player.getMediumTasks().isComplete(mediumTask.ordinal())) {
            int currentAmount = player.getMediumTasks().getAmountRemaining(mediumTask.ordinal());

            if (currentAmount >= mediumTask.getAmount()) {
                if (mediumTask.getRewards() != null) {
                    player.msgPurp("Your task reward(s) has been added to your account.");
                    player.getMediumTasks().setComplete(mediumTask.ordinal(), true);
                    for (Item item : mediumTask.getRewards())
                    player.getInventory().add(item.getId(), item.getAmount());
                    player.getMediumTasks().openInterface();
                }
            } else {
                player.msgRed("You have not completed this task yet!");
            }
        } else {
            player.msgRed("You have already claimed this reward.");
        }
    }

    public static void reset(Player player, MediumTaskData mediumTask) {
        if (!player.getMediumTasks().isComplete(mediumTask.ordinal())) {
            player.getMediumTasks().setAmountRemaining(mediumTask.ordinal(), 0);
        }
    }


    public static int getMaxTasks() {
        return MediumTaskData.values().length;
    }

    @Getter
    public enum MediumTaskData {
        //
        KILL_3K_INFERNUS("Kill 3k Infernus", 3000, new Item(15668, 2),  new Item(19062, 250)),

        CLAIM_25_VOTES("Claim 25 Votes", 25,  new Item(23020, 5), new Item(10946, 2)),

        COMPLETE_25_COE("Complete 25 COE", 25,new Item(15669, 3),new Item(7305, 10000)),

        COMPLETE_50_MEDIUM_SLAYER("50 Medium Slayer Tasks", 50, new Item(3576, 1500),  new Item(1303, 10),new Item(1306, 10)),

        UPGRADE_5_ITEMS("Upgrade 5 Items", 5, new Item(2706, 1),  new Item(10946, 2), new Item(1448, 2)),

        SALVAGE_50_ITEMS("Salvage 50 Items", 50, new Item(995, 10000), new Item(3580, 3),new Item(1448, 2)),

        OFFER_25K_MEDIUM_BONES("Offer 25k Medium Bones", 25_000, new Item(19065, 2),  new Item(20081, 2)),

        OPEN_50_RIFT_KEYS("Open 50 Rift Keys", 50,  new Item(5585, 20),  new Item(10946, 1)),

        MAKE_25000_ARROWS("Craft 25k Arrows", 25_000,  new Item(1446, 5), new Item(1447, 3),new Item(20049, 50)),
        //
        OFFER_20_T1_TOTEMS("Offer 20 T1 Totems", 20, new Item(20081, 3),  new Item(23173, 2), new Item(621, 2500)),

        ;

        private final String description;
        private final int amount;
        private final Item[] rewards;

        MediumTaskData( String description, int amount,
                     Item... rewards) {
            this.description = description;
            this.amount = amount;
            this.rewards = new Item[3];

            for (int i = 0 ; i < 3; i ++)
                this.rewards[i] = i < rewards.length ? rewards[i] : new Item(-1, 1);

        }


        public static MediumTaskData getTask(int ordinal) {
            for (MediumTaskData mediumTask : values())
                if (mediumTask.ordinal() == ordinal)
                    return mediumTask;
            return null;
        }

    }
}
