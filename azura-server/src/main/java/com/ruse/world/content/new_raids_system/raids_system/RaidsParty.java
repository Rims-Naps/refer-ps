//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ruse.world.content.new_raids_system.raids_system;

import com.ruse.util.Stopwatch;
import com.ruse.world.entity.impl.player.Player;
import java.util.ArrayList;

public class RaidsParty {
    private ArrayList<Player> partyMembers;
    private Player partyLeader;
    private RaidsPartyStatus status;
    private Stopwatch joiningTimer;
    private int floor;

    public RaidsParty(Player partyLeader) {
        this.partyLeader = partyLeader;
        this.partyMembers = new ArrayList(5);
        this.partyMembers.add(partyLeader);
        this.status = RaidsPartyStatus.COLLECTING_MEMBERS;
        this.joiningTimer = new Stopwatch();
        this.floor = partyLeader.getIndex() * 4;
    }

    public Player getPartyLeader() {
        return this.partyMembers.size() == 0 ? null : (Player)this.partyMembers.get(0);
    }

    public void addPlayer(Player toAdd) {
        this.partyMembers.add(toAdd);
    }

    public void setPartyLeader(Player partyLeader) {
        this.partyLeader = partyLeader;
    }

    public ArrayList<Player> getPartyMembers() {
        return this.partyMembers;
    }

    public RaidsPartyStatus getStatus() {
        return this.status;
    }

    public void setStatus(RaidsPartyStatus status) {
        this.status = status;
    }

    public boolean isLeader(Player player) {
        return player.getName().equalsIgnoreCase(this.partyLeader.getName());
    }

    public Stopwatch getJoiningTimer() {
        return this.joiningTimer;
    }

    public int getFloor() {
        return this.floor;
    }
}
