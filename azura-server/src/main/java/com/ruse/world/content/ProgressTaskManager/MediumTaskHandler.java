package com.ruse.world.content.ProgressTaskManager;

import com.ruse.world.entity.impl.player.Player;

public class MediumTaskHandler {
    private static final int MAXIMUM_TIER_ACHIEVEMENTS = 20;
    private final Player player;
    private int[] amountRemaining = new int[MAXIMUM_TIER_ACHIEVEMENTS];
    private boolean[] completed = new boolean[MAXIMUM_TIER_ACHIEVEMENTS];

    public MediumTaskHandler(Player player) {
        this.player = player;
    }

    public void openInterface() {
        int index = 148052;
        for (MediumTasks.MediumTaskData data : MediumTasks.MediumTaskData.values()) {

            player.getPacketSender().updateInterfaceVisibility(index++, completed[data.ordinal()] ? false : true);
            player.getPacketSender().sendString(index++, completed[data.ordinal()] ? "@red@Complete" : "@gre@Claim");

            player.getPacketSender().sendString(index++, data.getDescription());

            int amount = getAmountRemaining(data.ordinal());//Grabbing the amount completed
            int percentage = (int) ((100 * (double) amount) / (double) data.getAmount());//percentage calculator
            if (amount > data.getAmount()) {
                amount = data.getAmount();
                percentage = 100;
            }

            player.getPacketSender().sendString(148003, "Medium Tasks");

            player.getPacketSender().sendProgressBar(index++, 0, percentage, 0);
            player.getPacketSender().sendString(index++, "(" + amount + "/" + data.getAmount() + ")");
            player.getPacketSender().sendItemOnInterface(index++, data.getRewards()[0].getId(), data.getRewards()[0].getAmount());
            player.getPacketSender().sendItemOnInterface(index++, data.getRewards()[1].getId(), data.getRewards()[1].getAmount());
            player.getPacketSender().sendItemOnInterface(index++, data.getRewards()[2].getId(), data.getRewards()[2].getAmount());
            index += 1;
        }
        player.getPacketSender().setScrollBar(148050, 2 + (MediumTasks.MediumTaskData.values().length * 60));
        player.getPacketSender().sendInterface(148000);

    }

    public boolean hasCompletedAll() {
        int amount = 0;
        for (MediumTasks.MediumTaskData mediumTask : MediumTasks.MediumTaskData.values()) {
            if (isComplete(mediumTask.ordinal()))
                amount++;
        }
        return amount == MediumTasks.getMaxTasks();
    }

    public boolean isComplete(int index) {
        return completed[index];
    }

    public void setComplete(int index, boolean state) {
        this.completed[index] = state;
    }

    public boolean[] getCompleted() {
        return completed;
    }

    public void setCompleted(boolean[] completed) {
        this.completed = completed;
    }

    public int getAmountRemaining(int index) {
        return amountRemaining[index];
    }

    public int[] getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(int[] amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public void setAmountRemaining(int index, int amountRemaining) {
        this.amountRemaining[index] = amountRemaining;
    }

    public boolean handleButtonClick(int buttonId) {
        if (buttonId >= 148052 && buttonId <= 148200) {
            complete((buttonId - 148052) / 9);
            return true;
        }
        return false;
    }

    private void complete(int mediumTask) {
        for (MediumTasks.MediumTaskData ach : MediumTasks.MediumTaskData.values()) {
            if (mediumTask == ach.ordinal()) {
                MediumTasks.claimReward(player, ach);
            }
        }

    }

}
