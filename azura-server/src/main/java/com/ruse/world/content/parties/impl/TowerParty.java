package com.ruse.world.content.parties.impl;

import com.ruse.world.content.parties.Party;
import com.ruse.world.content.parties.PartyType;
import com.ruse.world.entity.impl.player.Player;

public class TowerParty extends Party {
    public TowerParty() {
        super(PartyType.TOWER_OF_ASCENSION);
    }

    @Override
    public void createParty(Player player) {
        super.createParty(player);
    }

    @Override
    public void acceptInvite(int slot) {
        super.acceptInvite(slot);
    }
}
