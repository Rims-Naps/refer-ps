package com.ruse.model.input.impl;

import com.ruse.model.input.Input;
import com.ruse.world.entity.impl.player.Player;

public class RaidInvitePlayerPrompt extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (player.getForgottenRaidParty() == null) {
            player.sendMessage("You are no longer in a raid party!");
            return;
        }
        player.getForgottenRaidParty().invitePlayer(player, syntax);
    }

}
