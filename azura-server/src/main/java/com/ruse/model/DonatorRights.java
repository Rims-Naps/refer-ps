/*

package com.ruse.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public enum DonatorRights {

    // All donator ranks
    //REGULAR_PLAYER(-1, "<col=AF70C3><shad=0>", 1, 1),
   // BETA_DONATOR(-1, "<col=AF70C3><shad=0>", 1, 1),
    //ALPHA_DONATOR(-1, "@gre@<shad=0>", 1, 1),
    //OMEGA_DONATOR(-1, "@red@<shad=0>", 1, 1),
    SUPERIOR_DONATOR(-1, "@whi@<shad=0>", 1, 1),
    INVESTOR(-1, "@bla@<shad=0>", 1, 1);

    DonatorRights(int yellDelaySeconds, String yellHexColorPrefix, double loyaltyPointsGainModifier,
                  double experienceGainModifier) {
        this.yellDelay = yellDelaySeconds;
        this.yellHexColorPrefix = yellHexColorPrefix;
        this.loyaltyPointsGainModifier = loyaltyPointsGainModifier;
        this.experienceGainModifier = experienceGainModifier;
    }

    private int yellDelay;
    private String yellHexColorPrefix;
    private double loyaltyPointsGainModifier;
    private double experienceGainModifier;


    // Getters
    public int getYellDelay() {
        return yellDelay;
    }

    public String getYellPrefix() {
        return yellHexColorPrefix;
    }

    public double getLoyaltyPointsGainModifier() {
        return loyaltyPointsGainModifier;
    }

    public double getExperienceGainModifier() {
        return experienceGainModifier;
    }


    // ImmutableSet for each donator rank
    private static final ImmutableSet<DonatorRights> SAPPHIRE_ONLY = Sets.immutableEnumSet(BETA_DONATOR, ALPHA_DONATOR,
            OMEGA_DONATOR, SUPERIOR_DONATOR, INVESTOR);

    private static final ImmutableSet<DonatorRights> EMERALD_ONLY = Sets.immutableEnumSet(ALPHA_DONATOR,
            OMEGA_DONATOR, SUPERIOR_DONATOR,  INVESTOR);

    private static final ImmutableSet<DonatorRights> RUBY_ONLY = Sets.immutableEnumSet(OMEGA_DONATOR,
            SUPERIOR_DONATOR,  INVESTOR);

    private static final ImmutableSet<DonatorRights> DIAMOND_ONLY = Sets.immutableEnumSet(SUPERIOR_DONATOR,
            INVESTOR);

    private static final ImmutableSet<DonatorRights> ONYX_ONLY = Sets.immutableEnumSet(INVESTOR);

} */