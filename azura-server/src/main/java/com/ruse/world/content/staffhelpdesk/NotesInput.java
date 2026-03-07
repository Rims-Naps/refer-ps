package com.ruse.world.content.staffhelpdesk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NotesInput extends Input {

    private final String name;
    private final String reason;
    public static String notes = "";

    public NotesInput(String name, String reason) {
        this.name = name;
        this.reason = reason;
    }

    @Override
    public void handleSyntax(Player player, String syntax) {
        Path path = Paths.get("./data/requests/" + name + ".json");
        File file = path.toFile();
        try (FileWriter writer = new FileWriter(file)) {
            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JSONObject obj = new JSONObject();

            obj.put("PlayerName", name);
            obj.put("Reason", reason);
            obj.put("Notes", syntax);
            notes = syntax;
            player.getPacketSender().sendString(49008, syntax);
            writer.write(builder.toJson(obj));
            player.sendMessage("<col=ff0000>Your help request has been saved. Click the request help button to alert a member of");
            player.sendMessage("<col=ff0000>staff, Thank you.");

            player.getPacketSender().sendString(49005, "Update Request");
            World.getPlayers().stream().filter(p -> p != null && (p.getRights().isStaff())).forEach(x -> x.getStaffHelpDesk().refreshTickets());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
