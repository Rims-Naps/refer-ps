/*
package com.ruse.world.content.new_raids_system.raids_system;

import com.ruse.model.Locations;
import com.ruse.world.content.new_raids_system.instances.CorruptRaid;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.entity.impl.player.Player;

public class RaidDifficultyManager {

    public void handleEasyMode(Player player, Locations.Location playerLocation) {
        player.sendMessage("<shad=0>You have selected Easy mode.");
        if (playerLocation == Locations.Location.VENGEANCE_ENTRANCE) {
            player.setCorruptRaidMedium(false);
            player.setCorruptRaidHard(false);
            CorruptRaid.startRaid(player, false, false);
            player.sendMessage("@gre@<shad=0>VENGEANCE EASYMODE");
        }
    }

    public boolean handleHardMode(Player player, RaidsParty party) {
        for (Player partyMember : party.getPlayers()) {
            String playerName = partyMember.getUsername();
            if (partyMember.getLocation() == Locations.Location.VENGEANCE_ENTRANCE) {
                int EasyRuns = 200 - partyMember.getCorruptCompletions();
                if (EasyRuns > 0) {
                    for (Player member : party.getPlayers()) {
                        member.sendMessage(" @red@<shad=0> " + playerName + "@blu@ must complete " + EasyRuns + " more CorruptRaid Raids to unlock Hard difficulty!");
                    }
                    return false;
                }
            }
        }
        player.sendMessage("<shad=0>You have selected Hard mode.");
        return true;
    }


    public boolean handleInsaneMode(Player player, RaidsParty party) {
        for (Player partyMember : party.getPlayers()) {
            String playerName = partyMember.getUsername();
            if (partyMember.getLocation() == Locations.Location.VENGEANCE_ENTRANCE) {
                int EasyRuns = 500 - partyMember.getCorruptCompletions();
                int HardRuns = 250 - partyMember.getCorruptMediumCompletions();
                if (EasyRuns > 0 || HardRuns > 0) {
                    for (Player member : party.getPlayers()) {
                        if (EasyRuns > 0) {
                            member.sendMessage(" @red@<shad=0> " + playerName + "@blu@ must complete " + EasyRuns + " more normal CorruptRaid Raids to unlock Insane difficulty!");
                        }
                        if (HardRuns > 0) {
                            member.sendMessage(" @red@<shad=0> " + playerName + "@blu@ must complete " + HardRuns + " more hard CorruptRaid Raids to unlock Insane difficulty!");
                        }
                    }
                    return false;
                }
            }
        }
        player.sendMessage("<shad=0>You have selected Insane mode.");
        return true;
    }

    public void startRaid(Player player, Locations.Location playerLocation, String difficulty) {
        if (playerLocation == Locations.Location.VENGEANCE_ENTRANCE) {
            CorruptRaid.startRaid(player, difficulty.equalsIgnoreCase("HARD"), difficulty.equalsIgnoreCase("INSANE"));
            player.sendMessage("@red@<shad=0>VENGEANCE " + difficulty.toUpperCase() + " MODE");
        } else {
           // ToyStory.startRaid(player, difficulty.equalsIgnoreCase("HARD"), difficulty.equalsIgnoreCase("INSANE"));
            player.sendMessage("@red@<shad=0>TOYSTORY " + difficulty.toUpperCase() + " MODE");
        }
    }
}
*/
