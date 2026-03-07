package com.ruse.world.content.raids;

import com.ruse.model.Position;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

public class Reward {
    @Getter
    private Position location;
    @Getter
    private Rarity rarity;

    public Reward() {
        this.location = null;
        this.rarity = null;
    }

    public Reward(Position location, Rarity rarity) {
        this.location = location;
        this.rarity = rarity;
    }
}
