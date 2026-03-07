package com.ruse.world.content.NewDaily;

import com.ruse.world.entity.impl.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DailyEntry {
    private int taskId;
    private int progress;
    private String taskName;
    private boolean hasStarted;
    private boolean hasReward;
    private int npcId;
    private int amountNeeded;
    private com.ruse.world.content.NewDaily.DailyTaskInterface.Category catagory;

    private boolean completed;

    public void submit(Player p) {
        p.getDailyEntries().add(this);
    }

    public void remove(Player p) {
        p.getDailyEntries().remove(this);
    }
}
