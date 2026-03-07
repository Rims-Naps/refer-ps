package com.ruse.world.content.groupslayer;

import com.ruse.model.definitions.NpcDefinition;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import lombok.Getter;

@Getter
public class GroupSlayerTask {
    private final int initialAmount;
    private final int xpReceived;
    private final SlayerTasks task;
    private final String taskMessage;
    private int currentAmount;

    public GroupSlayerTask(SlayerTasks task, int amount) {
        this.initialAmount = amount;
        this.task = task;
        this.xpReceived = task.getXP() * amount;
        taskMessage = amount + " " + NpcDefinition.forId(task.getNpcId()).getName();
        this.currentAmount = amount;
    }

    public GroupSlayerTask merge(GroupSlayerTask other) {
        System.out.println("Merged " + this.initialAmount + " and " + other.initialAmount);
        return new GroupSlayerTask(this.task, this.initialAmount + other.initialAmount);
    }

    public boolean isCompleted() {
        return currentAmount == 0;
    }

    public void handleKill() {
        currentAmount--;
    }

    public int getXpReceived() {
        return xpReceived;
    }
}
