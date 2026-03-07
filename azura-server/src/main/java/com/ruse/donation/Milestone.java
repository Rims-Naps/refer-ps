package com.ruse.donation;

import com.ruse.model.Item;

public class Milestone {
    public int milestone;
    public Item[] rewards;
    public String description;
    public MilestoneType type;

    public Milestone(int milestoneCost, Item[] rewards, MilestoneType type) {
        this.milestone = milestoneCost;
        this.rewards = rewards;
        this.type = type;
    }

    public Milestone(int milestoneCost, String description, MilestoneType type) {
        this.milestone = milestoneCost;
        this.description = description;
        this.type = type;
    }

    public int getMilestone() {
        return milestone;
    }

    public Item[] getRewards() {
        return rewards;
    }

    public String getDescription() {
        return description;
    }

    public MilestoneType getType() {
        return type;
    }

    public enum MilestoneType {
        DESCRIPTION, ITEMS;
    }

    public void pushData(Milestone milestone) {
        System.out.println("Milestone Data");
        System.out.println("Milestone Amount: " + milestone.getMilestone());
        System.out.println("Milestone Type: " + milestone.getType().toString());
        System.out.println("Milestone Items");
        for (Item item : milestone.rewards) {
            System.out.println("Item Id: " + item.getId());
            System.out.println("Item Amount: " + item.getAmount());
        }
    }
}
