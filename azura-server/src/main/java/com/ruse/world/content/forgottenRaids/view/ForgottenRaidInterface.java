package com.ruse.world.content.forgottenRaids.view;

import com.ruse.model.Item;
import com.ruse.model.input.impl.RaidInvitePlayerPrompt;
import com.ruse.world.content.forgottenRaids.ForgottenRaidDifficulty;
import com.ruse.world.content.forgottenRaids.party.ForgottenRaidParty;
import com.ruse.world.entity.impl.player.Player;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class ForgottenRaidInterface {

    private static final Item[] REWARDS_TO_DISPLAY =
            new Item[] {
            new Item(3490, 1),
            new Item(3495, 1),
            new Item(3500, 1),
            new Item(3491, 1),
            new Item(3496, 1),
            new Item(3501, 1),
            new Item(3492, 1),
            new Item(3497, 1),
            new Item(3502, 1),
            new Item(3493, 1),
            new Item(3498, 1),
            new Item(3503, 1),
            new Item(3494, 1),
            new Item(3499, 1),
            new Item(3504, 1)};

    private final Player p;

    public void display() {
        p.getPA().sendInterface(13107);
        updateInterface();
    }

    public void updateInterface() {
        if (p.getForgottenRaidParty() != null) {
            for (int i = 0; i < 5; i++) {
                if (i >= p.getForgottenRaidParty().getPlayers().size()) {
                    p.getPA().sendString(13111 + i, "@red@None");
                } else {
                    p.getPA().sendString(13111 + i, "@gre@"+p.getForgottenRaidParty().getPlayers().get(i));
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                p.getPA().sendString(13111+i, "@red@None");
            }
        }
        p.getPA().sendInterfaceItems(13118, REWARDS_TO_DISPLAY);
        p.getPA().sendString(13133, p.getForgottenRaidParty() != null ? ((p.getForgottenRaidParty().getDifficulty() == ForgottenRaidDifficulty.REGULAR ? "-> " : "") + "Easy") : "");
        p.getPA().sendString(13134, p.getForgottenRaidParty() != null ? ((p.getForgottenRaidParty().getDifficulty() == ForgottenRaidDifficulty.HARD ? "-> " : "") + "Hard") : "");
        p.getPA().sendString(13129, p.getForgottenRaidParty() != null ? "Invite Player" : "Create Party");
    }

    public boolean handleButton(int id) {
        switch (id) {
            case CREATE_INVITE_BUTTON:
                if (p.getForgottenRaidParty() != null) {
                    p.setInputHandling(new RaidInvitePlayerPrompt());
                    p.getPA().sendEnterInputPrompt("Please enter the name of the player you would like to invite:");
                } else {
                        p.setForgottenRaidParty(new ForgottenRaidParty(p.getUsername()));
                        p.sendMessage("You have created a new raid party!");
                }
                updateInterface();
                return true;
            case LEAVE_PARTY_BUTTON:
                if (p.getForgottenRaidParty() != null) {
                    p.getForgottenRaidParty().leave(p);
                    p.sendMessage("You have left your raid party.");
                } else {
                    p.sendMessage("You are not currently in a raid party!");
                }
                updateInterface();
                return true;
            case START_RAID_BUTTON:
                if (p.getForgottenRaidParty() != null) {
                    if (p.getForgottenRaidParty().isOwner(p.getUsername())) {
                        p.getForgottenRaidParty().startRaid();
                    } else {
                        p.sendMessage("Only the owner can start a raid!");
                    }
                } else {
                    p.sendMessage("Please create a party first!");
                }
                updateInterface();
                return true;
            case REGULAR_DIFFICULTY_BUTTON:
                if (p.getForgottenRaidParty() != null) {
                    if (p.getForgottenRaidParty().isOwner(p.getUsername())) {
                        p.getForgottenRaidParty().setDifficulty(ForgottenRaidDifficulty.REGULAR);
                    } else {
                        p.sendMessage("Only the owner can change the difficulty!");
                    }
                } else {
                    p.sendMessage("Please create a party first!");
                }
                updateInterface();
                return true;
            case HARD_DIFFICULTY_BUTTON:
                if (p.getForgottenRaidParty() != null) {
                    if (p.getForgottenRaidParty().isOwner(p.getUsername())) {
                        p.getForgottenRaidParty().setDifficulty(ForgottenRaidDifficulty.HARD);
                    } else {
                        p.sendMessage("Only the owner can change the difficulty!");
                    }
                } else {
                    p.sendMessage("Please create a party first!");
                }
                updateInterface();
                return true;
        }
        return false;
    }


    private static final int CREATE_INVITE_BUTTON = 13119;
    private static final int LEAVE_PARTY_BUTTON = 13122;
    private static final int START_RAID_BUTTON = 13125;
    private static final int REGULAR_DIFFICULTY_BUTTON = 13133;
    private static final int HARD_DIFFICULTY_BUTTON = 13134;

}
