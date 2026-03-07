package com.ruse.npcspawneditor;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ruse.model.Direction;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.npc.NPCMovementCoordinator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NpcSpawnEditor {

    private static final ObjectMapper mapper;
    private static final String FILE_PATH = "./data/def/json/world_npcs.json";

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature());
    }

    private static final Map<String, List<String>> code = new HashMap<>();
    private static final List<NPC> temporaryNpcs = new ArrayList<>();

    public static void addSpawnDefinition(int id, int directionValue, int radius, int x, int y, int z, String format) {
        Direction direction = Direction.numberToDirection(directionValue);
        Position position = new Position(x, y, z);
        format = format.trim().toLowerCase();
        System.out.println(format + " | " + format.toCharArray().length);
        WalkingPolicy walkingPolicy = new WalkingPolicy(radius > 0, radius);
        SpawnDefinition spawnDefinition = new SpawnDefinition(id, direction, position, walkingPolicy);
        NPC.getSpawnDefinitions().add(spawnDefinition);
        spawnNpc(spawnDefinition);
        save();
    }

    public static void clearCode() {
        code.clear();
    }

    public static void copyCode() {
        String formatted = String.join(", ", code.get("raid"));
        System.out.println(formatted);
    }

    public static void clearTemporaryNpcs() {
        temporaryNpcs.forEach(World::deregister);
        temporaryNpcs.clear();
    }

    public static void clearLastTemporary() {
        NPC removed = temporaryNpcs.remove(temporaryNpcs.size() - 1);
        World.deregister(removed);
    }

    public static void removeSpawnDefinition(int index) {
        NPC npc = World.getNpcs().get(index);
        NPC.getSpawnDefinitions()
                .remove(new SpawnDefinition(npc.getId(), null, npc.getDefaultPosition(), null));
        World.deregister(npc);
        save();
    }

    public static void save() {
        try {
            mapper.writeValue(new File(FILE_PATH), NPC.getSpawnDefinitions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void spawnNpc(SpawnDefinition spawnDefinition) {
        NPC npc = new NPC(spawnDefinition.getNpcId(), spawnDefinition.getPosition());
        npc.getMovementCoordinator()
                .setCoordinator(new NPCMovementCoordinator.Coordinator(spawnDefinition.getWalkingPolicy()
                        .isCoordinate(), spawnDefinition.getWalkingPolicy().getRadius()));
        npc.setDirection(spawnDefinition.getFace());
        World.register(npc);
    }
}