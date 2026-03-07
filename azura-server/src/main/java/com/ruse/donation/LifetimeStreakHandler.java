package com.ruse.donation;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;


public class LifetimeStreakHandler {
    public enum DataSet {
        FIRST(28902, 100, new Item[]{new Item(23058, 1), new Item(17129, 1), new Item(23173, 1), new Item(1447, 3)}),
        SECOND(28905, 500, new Item[]{new Item(17125, 1), new Item(1447, 10) }),
        THIRD(28908,1000, new Item[]{new Item(23059, 2), new Item(17129, 4), new Item(23173, 4), new Item(1447, 25) });

        int interfaceId;
        @Getter
        int maxThreshold;
        @Getter
        Item[] items;

        DataSet(int inter, int max, Item[] items) {
            this.interfaceId = inter;
            this.maxThreshold = max;
            this.items = items;
        }

    }
    public static void attemptGive(Player plr, int amount) {
        updateInterface(plr);
        for (DataSet data : DataSet.values()) {
            plr.incrementLifeTimeStreakVal(data.ordinal(), amount);
            if (plr.getLifetimeStreakVal()[data.ordinal()] >= data.getMaxThreshold()) {
                plr.decrementLifeTimeStreakVal(data.ordinal(), data.getMaxThreshold());
                plr.getBank(0).add(data.getItems());
                if (plr.getLifetimeStreakVal()[data.ordinal()] >= data.getMaxThreshold()) {
                    attemptGive(plr, 0);
                }
            }
        }
    }

    public static void updateInterface(Player plr) {
        Milestone[] milestones = new Milestone[DataSet.values().length];
        int maxVal = 0;
        int highestCurVal = 0;
        for (DataSet data : DataSet.values()) {
            milestones[data.ordinal()] = new Milestone(data.getMaxThreshold(), data.getItems(), Milestone.MilestoneType.ITEMS);
            if (data.getMaxThreshold() > maxVal)
                maxVal = data.getMaxThreshold();
            if (plr.getLifetimeStreakVal()[data.ordinal()] > highestCurVal)
                highestCurVal = plr.getLifetimeStreakVal()[data.ordinal()];
        }
          plr.getPacketSender().sendCustomProgressBarWMilestones(28603, highestCurVal, milestones, maxVal);

    }
}
