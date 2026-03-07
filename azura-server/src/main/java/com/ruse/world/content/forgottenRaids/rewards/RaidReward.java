package com.ruse.world.content.forgottenRaids.rewards;

import com.ruse.model.Position;

public class RaidReward {
    private String playerName;
    private RaidRewardRarity rarity;
    private RaidRewardData item;
    private Position loc;

    public RaidReward() {
        this.playerName = "";
        this.rarity = null;
        this.item = null;
        this.loc = null;
    }

    public RaidReward(String playerName, RaidRewardRarity rarity, RaidRewardData item, Position pos) {
        this.playerName = playerName;
        this.rarity = rarity;
        this.item = item;
        this.loc = pos;
    }

    public String getPlayerName() {
        return playerName;
    }

    public RaidRewardData getItemData() {
        return item;
    }

    public RaidRewardRarity getRarity() {
        return rarity;
    }

    public Position getPosition() {
        return loc;
    }
}
