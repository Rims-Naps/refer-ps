package com.ruse.world.content.staffhelpdesk;

import com.ruse.GameSettings;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerHelpDesk {

    private Player player;

    public PlayerHelpDesk(Player player) {
        this.player = player;
    }

    private int selectedDropdown = -1;
    public String reason = "";

    public boolean handleActions(int buttonId) {
        switch (buttonId) {
            case 98023:
            case 98022:
            case 98021:
            case 98020:
                selectedDropdown = buttonId;
                refreshHelpDetails(selectedDropdown);
                return true;
            case -16532:
                requestHelp();
                return true;
            case -16525:
                editNotes();
                return true;
            case -16524:
                switchToStaffList();
                return true;
            case -16484:
                returnToHelpDesk();
                return true;

        }
        return false;
    }

    public void switchToStaffList() {
        refreshStaffOnlineList();
        player.getPacketSender().sendTabInterface(GameSettings.STAFF_TAB, 49050);
    }

    private void returnToHelpDesk() {
        refreshHelpDetails(selectedDropdown);
        player.getPacketSender().sendTabInterface(GameSettings.STAFF_TAB, 49050);
    }

    public List<String> staffOnline = new ArrayList<>();

    public void refreshStaffOnlineList() {
        staffOnline.clear();
        for(int i = 0; i < 28; i++) {
            player.getPacketSender().sendString(49071 + i, "");
        }

        World.getPlayers().stream().filter(p -> p != null && (p.getRights().isStaff())).forEach(p -> staffOnline.add("<img="+p.getRights().ordinal()+">           " + p.getUsername()));

        for(int i = 0; i < staffOnline.size(); i++) {
            player.getPacketSender().sendString(49071 + i, staffOnline.get(i));
        }
    }

    public void refreshHelpDetails(int selected) {
        switch(selected) {
            case 98020:
                reason = "Reason: I'm stuck.";
                break;
            case 98021:
                reason = "Reason: I've found a bug.";
                break;
            case 98022:
                reason = "Reason: I'm reporting a player.";
                break;
            case 98023:
                reason = "Reason: I have another reason.";
                break;
        }
        player.getPacketSender().sendString(49007, reason);
        player.getPacketSender().sendString(49006, "Player name: " + player.getUsername());
    }

    private void editNotes() {
        player.getPacketSender().sendEnterInputPrompt("Please enter some further information about this issue.");
        player.setInputHandling(new NotesInput(player.getUsername(), reason));
    }

    public void requestHelp() {
        if (NotesInput.notes.isEmpty()) {
            player.sendMessage("You should write some notes so the staff can help you better.");
            return;
        }
        if (selectedDropdown == -1) {
            player.sendMessage("You need to select a reason for help.");
            return;
        }
        World.getPlayers().stream().filter(p -> p != null && (p.getRights().isStaff())).forEach(p ->  {
            p.sendMessage("<col=ff0000>You have received a new help request, select the staff panel to view it.");
            p.getStaffHelpDesk().refreshTickets();
        });
    }
}
