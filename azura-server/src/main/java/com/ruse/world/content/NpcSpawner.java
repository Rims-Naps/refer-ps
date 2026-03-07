
package com.ruse.world.content;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.io.FileWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NpcSpawner {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private int lastNpcId;
    private Player player;
    private int npcId;

    public NpcSpawner(Player player) {
        this.player = player;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }

    public int getNpcId() {
        return npcId;
    }

    public void spawnNpcWithId(String idString) {
        try {
            int id = Integer.parseInt(idString);
            lastNpcId = id; // Store the ID for later use
            EXECUTOR_SERVICE.submit(() -> spawnNpc(id));
        } catch (Exception e) {
            player.sendMessage("Wrong Syntax! Use as ::npc 1");
        }
    }

    public void spawnNpcWithLastId() {
        if (lastNpcId == 0) {
            player.sendMessage("No previous NPC ID found.");
            return;
        }
        EXECUTOR_SERVICE.submit(() -> spawnNpc(lastNpcId));
    }

    private void spawnNpc(int id) {
        NPC npc = new NPC(id, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
        World.register(npc);

        try {
            JsonObject npcObject = new JsonObject();
            npcObject.addProperty("npcId", id);
            npcObject.addProperty("face", "SOUTH");

            JsonObject position = new JsonObject();
            position.addProperty("x", player.getPosition().getX());
            position.addProperty("y", player.getPosition().getY());
            position.addProperty("z", player.getPosition().getZ());
            npcObject.add("position", position);

            JsonObject coordinator = new JsonObject();
            coordinator.addProperty("coordinate", true);
            coordinator.addProperty("radius", 2);
            npcObject.add("coordinator", coordinator);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            EXECUTOR_SERVICE.submit(() -> {
                try (FileWriter file = new FileWriter("./data/def/json/perm_npc_dump.json", true)) {
                    file.write(gson.toJson(npcObject));
                    file.write(",");
                    player.sendMessage("Success");
                } catch (Exception e) {
                    player.sendMessage("Failed");
                }
            });
        } catch (Exception e) {
            player.sendMessage("Failed");
        }
    }
}
