package com.ruse.world.content.staffhelpdesk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class StaffHelpDesk {

    private Player player;

    public StaffHelpDesk(Player player) {
        this.player = player;
    }

    public boolean handleActions(int buttonId) {
        if (buttonId <= -16346 && buttonId >= -16415) {
            if (!Arrays.stream(fileList).findAny().isPresent()) {
                player.sendMessage("No ticket was found for that name.");
                return true;
            }
            name = fileList[buttonId + 16415].getName();
            handlePlayerClick();
            return true;
        }
        if (buttonId == -16430) {
            refreshTickets();
            return true;
        }
        return false;
    }

    public void refreshTickets() {
        for (int i = 49121; i < 49191; i++) {
            player.getPacketSender().sendString(i, "");
        }
        for (int i = 47121; i < 47191; i++) {
            player.getPacketSender().sendString(i, "");
        }

        if (!name.isEmpty()) {
            if (fileList != null) {
                for (int i = 0; i < fileList.length; i++) {
                    player.getPacketSender().sendString(49121 + i, "@yel@" + fileList[i].getName().replace(".json", ""));
                }
                for (int i = 0; i < fileList.length; i++) {
                    player.getPacketSender().sendString(47121 + i, "@yel@" + fileList[i].getName().replace(".json", ""));
                }
            }
        } else {
            if (fileList != null) {
                for (int i = 0; i < fileList.length; i++) {
                    player.getPacketSender().sendString(49121 + i, fileList[i].getName().replace(".json", ""));
                }
                for (int i = 0; i < fileList.length; i++) {
                    player.getPacketSender().sendString(47121 + i, fileList[i].getName().replace(".json", ""));
                }
            }
        }
    }

    public void loadJsonDetails(String jsonDirectory) {
        Path path = Paths.get(jsonDirectory);
        File file = path.toFile();

        try (FileReader fileReader = new FileReader(file)) {
            JsonParser fileParser = new JsonParser();
            JsonObject reader = (JsonObject) fileParser.parse(fileReader);

            if (reader.has("map")) {
                name = reader.get("map").getAsJsonObject().get("PlayerName").getAsString();
                reason = reader.get("map").getAsJsonObject().get("Reason").getAsString();
                notes = reader.get("map").getAsJsonObject().get("Notes").getAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File directory = new File("./data/requests/");
    public File[] fileList = directory.listFiles();
    public String name = "";
    public String reason;
    public String notes;

    private void handlePlayerClick() {
        if (!name.isEmpty()) {
            Player helped = World.getPlayerByName(name);
            if (helped != null) {
                helped.sendMessage("<col=ff0000>Your ticket is currently being viewed by a member of staff.");
            }
            loadJsonDetails("./data/requests/" + name);
            refreshTickets();
            player.getStaffHelpInterface().refreshInterface();
        }
    }

}
