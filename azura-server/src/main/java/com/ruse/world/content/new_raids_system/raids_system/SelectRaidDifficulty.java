/*
package com.ruse.world.content.new_raids_system.raids_system;

import com.ruse.model.Locations;
import com.ruse.model.input.Input;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.entity.impl.player.Player;

public class SelectRaidDifficulty extends Input {

    @Override
    public void handleSyntax(Player player, String difficultyInput) {
        player.getPacketSender().sendInterfaceRemoval();
        String difficulty = difficultyInput.trim();
        Locations.Location playerLocation = player.getLocation();
        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
        RaidDifficultyManager difficultyManager = new RaidDifficultyManager();

        if (difficulty.equalsIgnoreCase("EASY")) {
            difficultyManager.handleEasyMode(player, playerLocation);
        } else if (difficulty.equalsIgnoreCase("HARD")) {
            if (difficultyManager.handleHardMode(player, party)) {
                difficultyManager.startRaid(player, playerLocation, "HARD");
            }
        } else if (difficulty.equalsIgnoreCase("INSANE")) {
            if (difficultyManager.handleInsaneMode(player, party)) {
                difficultyManager.startRaid(player, playerLocation, "INSANE");
            }
        } else {
            player.sendMessage("@red@<shad=0>Invalid input. Please enter Easy, Hard, or Insane");
        }
    }
}

*/
