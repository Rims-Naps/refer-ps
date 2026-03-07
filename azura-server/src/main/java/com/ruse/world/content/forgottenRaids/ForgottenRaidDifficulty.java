package com.ruse.world.content.forgottenRaids;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ForgottenRaidDifficulty {
    REGULAR(5, 1),
    HARD(3, 1.5),

    ;

    private final int maxPlayers;
    private final double healthMultiplier;
}
