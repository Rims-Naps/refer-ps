package com.ruse.world.content.donoisland;

import com.ruse.model.Position;
import lombok.Getter;

public class IslandPortal {
    @Getter
    private int npcId;
    @Getter
    private Position spawnLocation;
    @Getter
    private int faceDirection;
    @Getter
    private boolean isWinningPort;

    public IslandPortal(int npcId, Position spawnLocation, int faceDirection, boolean isWinningPort) {
        this.npcId = npcId;
        this.spawnLocation = spawnLocation;
        this.faceDirection = faceDirection;
        this.isWinningPort = isWinningPort;
    }
}
