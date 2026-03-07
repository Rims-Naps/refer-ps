package com.ruse.world.content.staffhelpdesk;

import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.PlayerHandler;

import java.io.File;

public class StaffHelpInterface {

    private Player player;

    public StaffHelpInterface(Player player) {
        this.player = player;
    }

    public boolean handleActions(int buttonId) {
        if (buttonId <= -18346 && buttonId >= -18415) {
            refreshInterface();
            return true;
        }
        switch(buttonId) {
            case -16271:
                player.sendMessage("This function is currently unavailable.");
                return true;
            case -16273:
                finishTicket();
                return true;
            case -16283:
                handleTeleportToPlayer();
                return true;
            case -16281:
                handleTeleportToStaff();
                return true;
            case -16279:
                handleKickPlayer();
                return true;
        }
        return false;
    }

    private void clearInterface() {
        player.getPacketSender().sendString(49260, "Staff Online: " + World.getPlayers().stream().filter(p -> p != null && (p.getRights().isStaff())).count());
        player.getPacketSender().sendString(49261, "Helping player: ");
        player.getPacketSender().sendString(49262, "Reason: ");
        player.getPacketSender().sendString(49252, "");
    }

    public void refreshInterface() {
        player.getPacketSender().sendString(49260, "Staff Online: " + World.getPlayers().stream().filter(p -> p != null && (p.getRights().isStaff())).count());
        player.getPacketSender().sendString(49261, "Helping player: " + player.getStaffHelpDesk().name);
        player.getPacketSender().sendString(49262, player.getStaffHelpDesk().reason);
        player.getPacketSender().sendString(49252, player.getStaffHelpDesk().notes);
        player.getPacketSender().sendInterface(49250);
    }

    private void handleTeleportToPlayer() {
        Player target = World.getPlayerByName(player.getStaffHelpDesk().name);
        if (target == null) {
            player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
        } else {
            TeleportHandler.teleportPlayer(player, target.getPosition().copy(), TeleportType.NORMAL);
            player.getPacketSender().sendConsoleMessage("Teleporting to player: " + target.getUsername());
        }
    }

    private void handleTeleportToStaff() {
        Player target = World.getPlayerByName(player.getStaffHelpDesk().name);
        if (target == null) {
            player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
        } else {
            TeleportHandler.teleportPlayer(target, player.getPosition().copy(), TeleportType.NORMAL);
            target.getPacketSender().sendMessage("You are being teleported by a staff member: " + player.getUsername());
        }
    }

    private void handleKickPlayer() {
        Player target = World.getPlayerByName(player.getStaffHelpDesk().name);
        try {
                World.deregister(target);
                PlayerHandler.handleLogout(target, false);
                player.getPacketSender().sendConsoleMessage("Kicked " + target.getUsername() + ".");
                PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " just kicked " + target.getUsername() + "!");
        } catch (Exception e) {
            player.getPacketSender().sendConsoleMessage("Player " + target.getUsername() + " couldn't be found on Platinum.");
        }
    }

    public void finishTicket() {
        Player target = World.getPlayerByName(player.getStaffHelpDesk().name);
        File myObj = new File("./data/requests/" + player.getStaffHelpDesk().name + ".json");

        if (myObj.delete()) {
            player.sendMessage("The help request for " + player.getStaffHelpDesk().name + " has been finished.");
            player.sendMessage("If the ticker requesters name does not vanish, please relog.");
            if (target != null) {
                target.sendMessage("Your ticket has been completed. If you require more help then submit another.");
            }
        } else {
            player.sendMessage("There was an error deleting the request for " + player.getStaffHelpDesk().name + ", contact an administrator.");
        }
        clearInterface();
        World.getPlayers().stream().filter(p -> p != null && (p.getRights().isStaff())).forEach(x -> x.getStaffHelpDesk().refreshTickets());
    }

}
