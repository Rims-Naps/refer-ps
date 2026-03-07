package com.ruse.world.content.forgottenRaids.party;


import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.model.Skill;
import com.ruse.model.input.impl.RaidPartyInvitePrompt;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.KillsTracker;
import com.ruse.world.content.forgottenRaids.ForgottenRaid;
import com.ruse.world.content.forgottenRaids.ForgottenRaidDifficulty;
import com.ruse.world.content.forgottenRaids.data.RaidDataManager;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ForgottenRaidParty {

    public static final Position DEFAULT_LOBBY_POSITION = new Position(3250, 3356, 0);

    private ForgottenRaid raid;

    private ForgottenRaidDifficulty difficulty = ForgottenRaidDifficulty.REGULAR;

    private boolean inRaid = false;

    public void setDifficulty(ForgottenRaidDifficulty difficulty) {
        Player ownerPlayer = World.getPlayerByName(owner);
        if (ownerPlayer == null) return;
        if (players.size() > difficulty.getMaxPlayers()) {
            ownerPlayer.sendMessage("You must have " + difficulty.getMaxPlayers() + " or less in your party to play this difficulty!");
            return;
        }
        this.difficulty = difficulty;
    }

    private String owner;

    private final List<String> players = new ArrayList<>();

    public ForgottenRaidParty(String owner) {
        this.owner = owner;
        this.players.add(owner);
    }

    public void invitePlayer(Player inviter, String target) {
        if (players.size() >= difficulty.getMaxPlayers()) {
            inviter.sendMessage("You can only have " + difficulty.getMaxPlayers() + " players for this difficulty!");
            return;
        }
        Player targetPlayer = World.getPlayerByName(target);
        if (targetPlayer == null) {
            inviter.sendMessage("That player could not be found online!");
            return;
        }
        if (targetPlayer.getForgottenRaidParty() != null) {
            inviter.sendMessage("That player is already in a raid party!");
            return;
        }
        if (targetPlayer.busy()) {
            inviter.sendMessage("That player is currently busy!");
            return;
        }
        targetPlayer.setLastForgottenRaidInvite(inviter.getUsername());
        targetPlayer.setInputHandling(new RaidPartyInvitePrompt());
        targetPlayer.getPA().sendEnterInputPrompt(inviter.getUsername() + " has invited you to their raid party, would you like to join?");
    }

    public void join(Player joiner) {
        if (players.contains(joiner.getUsername())) return;
        if (players.size() >= difficulty.getMaxPlayers()) {
            joiner.sendMessage("Sorry, this party is already full!");
            return;
        }
        if (joiner.getForgottenRaidParty() != null) {
            joiner.sendMessage("You are already in a raid party!");
            return;
        }
        joiner.setLastForgottenRaidInvite(null);
        joiner.setForgottenRaidParty(this);
        joiner.sendMessage("You have joined " + owner + "'s raid party.");
        players.add(joiner.getUsername());
        for (String playerName : players) {
            if (playerName.equalsIgnoreCase(joiner.getUsername())) continue;
            Player player = World.getPlayerByName(playerName);
            if (player != null) {
                player.sendMessage(joiner.getUsername() + " has joined your raid party!");
                player.getForgottenRaidInterface().updateInterface();
            }
        }
    }

    public void leave(Player target) {
        if (inRaid) {
            target.moveTo(DEFAULT_LOBBY_POSITION);
        }
        if (isOwner(target.getUsername())) {
            if (players.size() == 1) disband();
            else selectNewOwner(target.getUsername());
        }
        players.remove(target.getUsername());
        target.setForgottenRaidParty(null);
    }

    private void disband() {
        if (inRaid || raid != null) {
            raid.dispose();
            inRaid = false;
            raid = null;
        }
        players.clear();
    }

    public boolean isOwner(String name) {
        return owner.equalsIgnoreCase(name);
    }

    private void selectNewOwner(String oldOwner) {
        String newOwner = oldOwner;
        while (newOwner.equalsIgnoreCase(oldOwner)) newOwner = Misc.random(players);
        owner = newOwner;
        Player ownerPlayer = World.getPlayerByName(owner);
        if (ownerPlayer != null) {
            ownerPlayer.sendMessage("You are the new owner of the raid party!");
        }
        players.forEach(playerName -> {
            if (!playerName.equalsIgnoreCase(oldOwner) && !playerName.equalsIgnoreCase(owner)) {
                Player player = World.getPlayerByName(playerName);
                if (player != null) player.sendMessage(owner + " is the new owner of your raid party!");
            }
        });
    }

    public void onLogout(Player target) {
        leave(target);
    }

    public void startRaid() {
        if (inRaid) return;
        if (!fillsRequirements().equalsIgnoreCase("success")) {
            World.getPlayerByName(owner).sendMessage("Your party does not meet the requirements to start a raid!");
            return;
        }
        if (difficulty == ForgottenRaidDifficulty.HARD && getPlayers().size() > 3) {
            World.getPlayerByName(owner).sendMessage("Your party cannot have more than 3 players on hard mode!");
            return;
        }
        setRaid(new ForgottenRaid(this));
        getRaid().start();
        inRaid = true;
    }

    private String fillsRequirements() {
        for (String playerName : players) {
            Player player = World.getPlayerByName(playerName);
            if (player != null) {

                if (player.getLocation() != Locations.Location.READYLOBBY) {
                    player.sendMessage("Please enter the lobby area so the raid can be started!");
                    return "Player ("+playerName+") needs to enter the lobby area to start the raid.";
                }

              /*  if (player.getSkillManager().getMaxLevel(Skill.Slayer) < 100) {
                    player.sendMessage("You need level 100 or above in the Slayer skill to participate in raids!");
                    return "Player ("+playerName+") needs level 100 or above in the Slayer skill.";
                }
                if (KillsTracker.getTotalKillsForNpc(9807, player) < 900) {
                    player.sendMessage("You need at least 900 Hokage kills to participate in raids!");
                    return "Player ("+playerName+") needs at least 900 Hokage kills.";
                }*/

                if (difficulty == ForgottenRaidDifficulty.HARD) {
                    if (RaidDataManager.getData(playerName, ForgottenRaidDifficulty.REGULAR).size() < 200) {
                        player.sendMessage("You need to complete at least 200 easy runs to play hard mode!");
                        return "Player ("+playerName+") needs to complete at least 200 easy runs.";
                    }
                    if (player.getSkillManager().getMaxLevel(Skill.SLAYER) < 120) {
                        player.sendMessage("You need level 120 or above in the Slayer skill to play hard mode!");
                        return "Player ("+playerName+") needs level 120 or above in the Slayer skill.";
                    }
                    if (KillsTracker.getTotalKillsForNpc(1704, player) < 1000) {
                        player.sendMessage("You need at least 1000 Ares kills to participate in raids!");
                        return "Player ("+playerName+") needs at least 1000 Ares kills.";
                    }
                    if (KillsTracker.getTotalKillsForNpc(1705, player) < 1000) {
                        player.sendMessage("You need at least 1000 Hermes kills to participate in raids!");
                        return "Player ("+playerName+") needs at least 1000 Hermes kills.";
                    }
                    if (KillsTracker.getTotalKillsForNpc(6330, player) < 1000) {
                        player.sendMessage("You need at least 1000 Demeter kills to participate in raids!");
                        return "Player ("+playerName+") needs at least 1000 Demeter kills.";
                    }
                }

            }
        }
        return "success";
    }
}
