package com.ruse.model.input.impl;

import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

public class RaidPartyInvitePrompt extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (!syntax.equalsIgnoreCase("yes") && !syntax.equalsIgnoreCase("no")) {
            player.sendMessage("Please enter yes or no!");
            return;
        }
        if (player.getLastForgottenRaidInvite() == null) {
            player.sendMessage("Please ask this player to invite you again!");
            return;
        }
        if (syntax.equalsIgnoreCase("yes")) {
            Player inviter = World.getPlayerByName(player.getLastForgottenRaidInvite());
            if (inviter == null) {
                player.sendMessage("The player who has invited you is no longer online!");
                return;
            }
            if (inviter.getForgottenRaidParty() == null) {
                player.sendMessage("The player who has invited you is no longer in a party!");
                return;
            }
            inviter.getForgottenRaidParty().join(player);
        } else if (syntax.equalsIgnoreCase("no")) {
            player.setLastForgottenRaidInvite(null);
        }

    }

}
