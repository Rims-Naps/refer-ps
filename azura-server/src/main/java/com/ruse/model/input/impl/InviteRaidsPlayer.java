package com.ruse.model.input.impl;

import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

public class InviteRaidsPlayer extends Input {

	@Override
	public void handleSyntax(Player player, String plrToInvite) {

		player.getPacketSender().sendInterfaceRemoval();
		Player invite = World.getPlayerByName(plrToInvite);
		if(invite == null) {
			player.getPacketSender().sendMessage("That player is currently not online.");
			return;
		}
		if(player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers().contains(invite)) {
			player.getPacketSender().sendMessage("That player is already in your party.");
			return;
		}
		player.sendMessage("Sent attempt Invite to " + plrToInvite);

		player.getMinigameAttributes().getRaidsAttributes().getParty().invite(invite);

	}
}
