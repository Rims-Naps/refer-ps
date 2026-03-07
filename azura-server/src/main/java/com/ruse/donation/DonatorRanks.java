package com.ruse.donation;

import com.ruse.model.PlayerRights;
import com.ruse.util.Misc;
import com.ruse.world.entity.impl.player.Player;

public class DonatorRanks {
    private Player player;
    public DonatorRanks(Player player) {
        this.player = player;
    }

    public static final int ADEPT_AMOUNT = 25;
    public static final int ETHEREAL_AMOUNT = 100;
    public static final int MYTHIC_AMOUNT = 200;
    public static final int ARCHON_AMOUNT = 500;
    public static final int CELESTIAL_AMOUNT = 1000;
    public static final int ASCENDANT_AMOUNT = 1500;
    public static final int GLADIATOR_AMOUNT = 2000;

    public static final int COSMIC_AMOUNT = 5000;
    public static final int GUARDIAN_AMOUNT = 7500;
    public static final int CORRUPT_AMOUNT = 10000;

    public static void checkForRankUpdate(Player player) {
        if (player.getRights().isStaff()) {
            return;
        }
        PlayerRights rights = null;
        if (player.getAmountDonated() >= ADEPT_AMOUNT)
            rights = PlayerRights.ADEPT;
        if (player.getAmountDonated() >= ETHEREAL_AMOUNT)
            rights = PlayerRights.ETHEREAL;
        if (player.getAmountDonated() >= MYTHIC_AMOUNT)
            rights = PlayerRights.MYTHIC;
        if (player.getAmountDonated() >= ARCHON_AMOUNT)
            rights = PlayerRights.ARCHON;
        if (player.getAmountDonated() >= CELESTIAL_AMOUNT)
            rights = PlayerRights.CELESTIAL;
        if (player.getAmountDonated() >= ASCENDANT_AMOUNT)
            rights = PlayerRights.ASCENDANT;
        if (player.getAmountDonated() >= GLADIATOR_AMOUNT)
            rights = PlayerRights.GLADIATOR;
        if (player.getAmountDonated() >= COSMIC_AMOUNT)
            rights = PlayerRights.COSMIC;
        if (player.getAmountDonated() >= GUARDIAN_AMOUNT)
            rights = PlayerRights.GUARDIAN;
        if (player.getAmountDonated() >= CORRUPT_AMOUNT)
            rights = PlayerRights.CORRUPT;
        if (rights != null && rights != player.getRights()) {
            player.getPacketSender().sendMessage("You've become a " + Misc.formatText(rights.toString().toLowerCase()) + "! Congratulations!");
            player.setRights(rights);
            player.getPacketSender().sendRights();
        }
    }
}

