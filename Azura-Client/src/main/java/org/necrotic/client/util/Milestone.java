package org.necrotic.client.util;

public class Milestone {
    public int milestone;
    public DummyItem[] rewards;
    public String description;
    public MilestoneType type;
    public int milestoneGoal;

    public Milestone(int milestoneCost, DummyItem[] rewards, MilestoneType type, int milestoneGoal) {
        this.milestone = milestoneCost;
        this.rewards = rewards;
        this.type = type;
        this.milestoneGoal = milestoneGoal;
    }

    public Milestone(int milestoneCost, String description, MilestoneType type, int milestoneGoal) {
        this.milestone = milestoneCost;
        this.description = description;
        this.type = type;
        this.milestoneGoal = milestoneGoal;
    }

    public int getMilestone() {
        return milestone;
    }

    public DummyItem[] getRewards() {
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

    public int getMilestoneGoal() {
        return milestoneGoal;
    }

    public void pushData(Milestone milestone) {
        System.out.println("Milestone Data");
        System.out.println("Milestone Goal: " + milestone.getMilestoneGoal());
        System.out.println("Milestone Amount: " + milestone.getMilestone());
        System.out.println("Milestone Type: " + milestone.getType().toString());
        System.out.println("Milestone Items");
        for (DummyItem item : milestone.rewards) {
            System.out.println("Item Id: " + item.getId());
            System.out.println("Item Amount: " + item.getAmount());
        }
    }
}
