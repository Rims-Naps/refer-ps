package com.ruse.world.content.serverperks;

public class Perk {
    private String name;
    private String description;

    private int spriteId;
    private int votes;

    public Perk(String name, String description, int spriteId) {
        this.name = name;
        this.description = description;
        this.spriteId = spriteId;
        this.votes = 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getVotes() {
        return votes;
    }

    public void incrementVotes() {
        this.votes++;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getSpriteId() {
        return spriteId;
    }
}
