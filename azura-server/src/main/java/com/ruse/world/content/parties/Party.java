package com.ruse.world.content.parties;

import com.ruse.world.content.parties.impl.TowerParty;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import javax.mail.Part;
import java.util.ArrayList;
import java.util.List;

public abstract class Party {

    private Player myPlayer;

    @Getter
    private Player owner;

    @Getter
    private final List<Player> players = new ArrayList<>();

    @Getter
    private final List<Player> applicants = new ArrayList<>();

    @Getter
    private PartyType type;

    public Party(PartyType type) {
        this.type = type;
    }

    public void createParty(Player player) {
        player.sendMessage("You create a party");
        this.owner = player;
        this.players.add(player);
        this.myPlayer = player;
        player.setTowerParty(this);
        PartyService.bindParty(this);
        PartyInterface.openViewingParties(player);
    }

    public void disbandParty() {
        myPlayer.sendMessage("You disband the party.");
        this.owner = null;
        myPlayer.getPacketSender().closeAllWindows();
        myPlayer.setTowerParty(new TowerParty());
        this.myPlayer = null;
        players.clear();
        applicants.clear();
        PartyService.unbindParty(this);
    }

    public void leaveParty(Player player) {
        this.players.remove(player);
        player.sendMessage("You leave the party!");
        myPlayer.sendMessage(player.getUsername() + " has left the party!");
        player.setTowerParty(new TowerParty());
    }

    public void kickMember(Player playerKicking, Player memberToKick) {
        if (this.owner != playerKicking)
            return;
        this.players.remove(memberToKick);
        myPlayer.sendMessage("You have kicked " + memberToKick.getUsername());
        memberToKick.setTowerParty(new TowerParty());
    }

    public void acceptInvite(int slot) {
        if (this.players.size() > 5) {
            myPlayer.sendMessage("You have reached the maximum size of the party!");
            return;
        }
        if (applicants.size() == 0)
            return;
        Player invite = applicants.get(slot);
        if (invite == null)
            return;
        this.players.add(invite);
        invite.setTowerParty(this);
        myPlayer.sendMessage("You have accepted " + invite.getUsername() + " into your party");
        applicants.remove(invite);
    }

    public void addInvite(Player player) {
        if (applicants.contains(player)) {
            player.sendMessage("You have already requested to join this party!");
            return;
        }
        applicants.add(player);
        player.sendMessage("You have requested to join the party!");
        PartyInterface.clear_viewing_parties_applicants(myPlayer);
        PartyInterface.refresh_applicants_list(myPlayer);
        myPlayer.sendMessage(player.getUsername() + " has requested to join your party");
    }


    public void isInBounds() {

    }


}
