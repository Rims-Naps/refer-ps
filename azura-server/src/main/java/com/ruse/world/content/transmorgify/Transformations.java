package com.ruse.world.content.transmorgify;

import lombok.Getter;

@Getter
public enum Transformations {

    VOTE_BOSS(2341, -1),
    DONO_BOSS(2342, -1),

    PIRATE(5665, -1),
    GODZILLA(9984, -1),
    ;

    private final int npcId, walkIndex;

    Transformations(int npcId, int walkIndex){
        this.npcId = npcId;
        this.walkIndex = walkIndex;
    }
}
