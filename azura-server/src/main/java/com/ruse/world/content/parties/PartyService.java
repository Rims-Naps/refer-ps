package com.ruse.world.content.parties;

import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Party Service that handles on parties currently established on the server. Allowing for binding, and debinding a party upon creation
 * or deletion. This service will allow for multiple instances of parties that need to be handled by the server.
 */


public class PartyService {
    public static List<Party> parties = new ArrayList<>();

    public static void bindParty(Party party) {
        if (parties.contains(party))
            return;
        parties.add(party);
    }

    public static void unbindParty(Party party) {
        if (!parties.contains(party))
            return;
        parties.remove(party);
    }

    public static int totalPartiesRunning() {
        if (parties != null)
            return parties.size();
        return 0;
    }

    public static boolean playerIsInParty(Player player) {
        for (Party party : parties) {
            for (int i2 = 0; i2 < party.getPlayers().size(); i2++) {
                if (party.getPlayers().get(i2) == player) {
                    return true;
                }
            }
        }
        return false;
    }


    public static Party getPartyForPlayer(Player player) {
        for (Party party : parties) {
            for (int i2 = 0; i2 < party.getPlayers().size(); i2++) {
                if (party.getPlayers().get(i2) == player) {
                    return party;
                }
            }
        }
        return null;
    }

    public static boolean isPlayerPartyOwner(Player player) {
        for (Party party : parties) {
            if (party.getOwner() == player) {
                return true;
            }
        }
        return false;
    }

}
