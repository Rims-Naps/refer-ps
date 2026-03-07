package com.ruse.world.content.ProgressTaskManager;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

public class EliteTasks {

    public static void doProgress(Player player, EliteTaskData eliteTask) {
        doProgress(player, eliteTask, 1);
    }

    public static void doProgress(Player player, EliteTaskData eliteTask, int amount) {
        int currentAmount = player.getEliteTasks().getAmountRemaining(eliteTask.ordinal());
        if (currentAmount < eliteTask.getAmount() && !player.getEliteTasks().isComplete( eliteTask.ordinal())) {
            player.getEliteTasks().setAmountRemaining(eliteTask.ordinal(), currentAmount + amount);
            if ((currentAmount + amount) >= eliteTask.getAmount()) {
                player.msgFancyPurp("[PROGRESSION] @red@" + eliteTask.getDescription()+ " <shad=AF70C3>completed, claim your reward!");
            }
        }
    }

    public static void claimReward(Player player, EliteTaskData eliteTask) {
        if (!player.getEliteTasks().isComplete(eliteTask.ordinal())) {
            int currentAmount = player.getEliteTasks().getAmountRemaining(eliteTask.ordinal());

            if (currentAmount >= eliteTask.getAmount()) {
                if (eliteTask.getRewards() != null) {
                    player.msgPurp("Your task reward(s) has been added to your account.");
                    player.getEliteTasks().setComplete(eliteTask.ordinal(), true);
                    for (Item item : eliteTask.getRewards())
                        player.getInventory().add(item.getId(), item.getAmount());
                    player.getEliteTasks().openInterface();
                }
            } else {
                player.msgRed("You have not completed this task yet!");
            }
        } else {
            player.msgRed("You have already claimed this reward.");
        }
    }

    public static void reset(Player player, EliteTaskData eliteTask) {
        if (!player.getEliteTasks().isComplete(eliteTask.ordinal())) {
            player.getEliteTasks().setAmountRemaining(eliteTask.ordinal(), 0);
        }
    }


    public static int getMaxTasks() {
        return EliteTaskData.values().length;
    }

    @Getter
    public enum EliteTaskData {

        KILL_750_AQUA_GUARDIAN("Kill 750 Aqua Guardian", 750, new Item(23172, 2),  new Item(3578, 1)),

        CLAIM_50_VOTES("Claim 50 Votes", 50,  new Item(23020, 10), new Item(1448, 5)),

        COMPLETE_100_COE("Complete 100 COE", 100,new Item(15669, 5),   new Item(15668, 5), new Item(7306, 5000)),

        COMPLETE_50_ELITE_SLAYER("50 Elite Slayer Tasks", 50, new Item(3576, 5000),  new Item(1304, 25),new Item(1307, 25)),

        UPGRADE_10_ITEMS("Upgrade 10 Items", 10, new Item(1526, 1),new Item(10946, 3)),

        SALVAGE_100_ITEMS("Salvage 100 Items", 100, new Item(19062, 350), new Item(17130, 10)),

        OFFER_25K_ELITE_BONES("Offer 25k Elite Bones", 25_000, new Item(23173, 3),  new Item(15669, 10), new Item(10946, 2)),

        OPEN_100_RIFT_KEYS("Open 100 Rift Keys", 100,  new Item(5585, 50),  new Item(3582, 5)),

        MAKE_50k_EACH_ARROW("Craft 50k Each Arrow", 50_000,  new Item(1446, 15), new Item(1447, 10),new Item(1448, 5)),
        //
        OFFER_5x_T2_TOTEM("Offer 5x T2 Totem", 5, new Item(20082, 1),  new Item(23173, 5)),

        ;

        private final String description;
        private final int amount;
        private final Item[] rewards;

        EliteTaskData( String description, int amount,
                        Item... rewards) {
            this.description = description;
            this.amount = amount;
            this.rewards = new Item[3];

            for (int i = 0 ; i < 3; i ++)
                this.rewards[i] = i < rewards.length ? rewards[i] : new Item(-1, 1);

        }


        public static EliteTaskData getTask(int ordinal) {
            for (EliteTaskData eliteTask : values())
                if (eliteTask.ordinal() == ordinal)
                    return eliteTask;
            return null;
        }

    }
}
