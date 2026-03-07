package com.ruse.world.content.groupironman;

import com.google.gson.*;
import com.ruse.GameServer;
import com.ruse.model.Item;
import com.ruse.model.PlayerRelations;
import com.ruse.model.container.impl.GroupIronmanBank;
import com.ruse.model.input.impl.ChangeGroupIronmanName;
import com.ruse.model.input.impl.CreateIronmanGroup;
import com.ruse.model.input.impl.InviteGroupIronmanPlayer;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class GroupManager {

    public static ArrayList<IronmanGroup> GROUPS = new ArrayList<>();

    public static boolean handleButton(Player player, int buttonID) {
        if (buttonID == 104011) {
            player.setInputHandling(new ChangeGroupIronmanName());
            player.getPacketSender().sendEnterInputPrompt("Change Name");
        } else if (buttonID == 104012) {
            player.setInputHandling(new InviteGroupIronmanPlayer());
            player.getPacketSender().sendEnterInputPrompt("Invite Player");
        }
        return false;
    }

    public static void createGroup(Player player) {
        if (isInGroup(player)) {
            player.sendMessage("You are already in an ironman group.");
            return;
        }
        player.getPacketSender().sendInterfaceRemoval();
        player.setInputHandling(new CreateIronmanGroup());
        player.getPacketSender().sendEnterInputPrompt("Enter a Group Name:");
    }

    public static void createGroup(Player player, String name) {
        if (name.length() < 3 || name.length() > 18) {
            player.sendMessage("Your group name must be between 3-18 characters.");
            return;
        }
        if (getGroup(name) != null) {
            player.sendMessage("This group name is already taken.");
            return;
        }

        IronmanGroup newGroup = new IronmanGroup(name);
        newGroup.setLeader(player.getUsername());
        newGroup.addPlayer(player);
        newGroup.setUniqueId(getNextId());
        //Setting 10 new Bank tabs for a new Group
        newGroup.setBank(new GroupIronmanBank[]{
            new GroupIronmanBank(),
            new GroupIronmanBank(),
            new GroupIronmanBank(),
            new GroupIronmanBank(),
            new GroupIronmanBank(),
            new GroupIronmanBank(),
            new GroupIronmanBank(),
            new GroupIronmanBank(),
            new GroupIronmanBank(),
            new GroupIronmanBank(),
        });
        GROUPS.add(newGroup);
        saveGroup(newGroup);

        player.sendMessage("You made an Ironman group: " + newGroup.getName());
    }

    public static int getNextId() {
        int ID = -1;

        try {
            BufferedReader r = new BufferedReader(new FileReader("./data/saves/groupID.txt"));
            String line = r.readLine();
            ID = Integer.parseInt(line);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader("./data/saves/groupID.txt"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();

            String inputStr = inputBuffer.toString().replace("\n", "").replace("\t", "");
            int number = Integer.parseInt(inputStr) + 1;
            inputStr = number + "";

            FileOutputStream fileOut = new FileOutputStream("./data/saves/groupID.txt");
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }

        return ID;
    }

    public static IronmanGroup getGroup(String name) {
        for (IronmanGroup group : GROUPS) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    public static IronmanGroup getGroup(int groupId) {
        for (IronmanGroup group : GROUPS) {
            if (group.getUniqueId() == groupId) {
                return group;
            }
        }
        return null;
    }

    public static void openInterface(Player player) {
        if (player.getIronmanGroup() != null) {
            player.getPacketSender().sendInterface(104000);

            player.getPacketSender().sendString(104006, "Group Name: @whi@" + player.getIronmanGroup().getName());
            player.getPacketSender().sendString(104008, "Total Level");

            int interfaceId = 104013;
            for (int i = 0; i < 5; i++) {
                if (player.getIronmanGroup().getMembers().size() > i) {
                    String name = player.getIronmanGroup().getMembers().get(i);
                    Player target = World.getPlayerByName(name);
                    player.getPacketSender().sendString(interfaceId++, name);
                    if (target != null) {
                        player.getPacketSender().sendString(interfaceId++, "" + target.getSkillManager().getTotalLevel());
                        player.getPacketSender().sendString(interfaceId++,
                                player.getRelations().getStatus() == PlayerRelations.PrivateChatStatus.ON ? "@gre@Online" : "@red@Offline");
                    } else {
                        target = IronmanPlayerLoading.getResult(name);
                        player.getPacketSender().sendString(interfaceId++, "" + target.getSkillManager().getTotalLevel() );
                        player.getPacketSender().sendString(interfaceId++, "@red@Offline");
                    }
                } else {
                    player.getPacketSender().sendString(interfaceId++, "---");
                    player.getPacketSender().sendString(interfaceId++, "---");
                    player.getPacketSender().sendString(interfaceId++, "---");
                }
            }
        }
    }


    public static boolean isInGroup(Player player) {
        return player.getIronmanGroup() != null && isInGroup(player.getUsername());
    }

    public static boolean isInGroup(String name) {
        for (IronmanGroup group : GROUPS) {
            for (String member : group.getMembers()) {
                if (member.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void loadGroups() {
        GROUPS.clear();
        File[] files = new File(GroupConfig.path).listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {

            IronmanGroup group = new IronmanGroup(file.getName().replace(".json", ""));
            try (FileReader fileReader = new FileReader(file)) {
                JsonParser fileParser = new JsonParser();
                JsonElement element = fileParser.parse(fileReader);
                boolean needsUpdating = false;

                // Check if the element is not null and is a JsonObject
                if (element != null && element.isJsonObject()) {
                    JsonObject reader = element.getAsJsonObject();

                    if (reader.has("Name")) {
                        group.setName(reader.get("Name").getAsString());
                    }
                    if (reader.has("UniqueID")) {
                        group.setUniqueId(reader.get("UniqueID").getAsInt());
                    }
                    if (reader.has("Leader")) {
                        group.setLeader(reader.get("Leader").getAsString());
                    }
                    if (reader.has("Members")) {
                        Gson builder = new GsonBuilder().create();
                        String[] members = builder.fromJson(reader.get("Members").getAsJsonArray(), String[].class);
                        for (String l : members) {
                            group.getMembers().add(l);
                        }
                    }
                    if (reader.has("Bank")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(0, new GroupIronmanBank()).getBank(0)
                            .addItems(builder.fromJson(reader.get("Bank").getAsJsonArray(), Item[].class), true);
                        needsUpdating = true;
                    }
                    if (reader.has("Bank-1")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(1, new GroupIronmanBank()).getBank(1)
                            .addItems(builder.fromJson(reader.get("Bank-1").getAsJsonArray(), Item[].class), true);
                        needsUpdating = false;
                    }
                    if (reader.has("Bank-2")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(2, new GroupIronmanBank()).getBank(2)
                            .addItems(builder.fromJson(reader.get("Bank-2").getAsJsonArray(), Item[].class), true);
                        needsUpdating = false;
                    }
                    if (reader.has("Bank-3")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(3, new GroupIronmanBank()).getBank(3)
                            .addItems(builder.fromJson(reader.get("Bank-3").getAsJsonArray(), Item[].class), true);
                        needsUpdating = false;
                    }
                    if (reader.has("Bank-4")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(4, new GroupIronmanBank()).getBank(4)
                            .addItems(builder.fromJson(reader.get("Bank-4").getAsJsonArray(), Item[].class), true);
                        needsUpdating = false;
                    }
                    if (reader.has("Bank-5")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(5, new GroupIronmanBank()).getBank(5)
                            .addItems(builder.fromJson(reader.get("Bank-5").getAsJsonArray(), Item[].class), true);
                        needsUpdating = false;
                    }
                    if (reader.has("Bank-6")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(6, new GroupIronmanBank()).getBank(6)
                            .addItems(builder.fromJson(reader.get("Bank-6").getAsJsonArray(), Item[].class), true);
                        needsUpdating = false;
                    }
                    if (reader.has("Bank-7")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(7, new GroupIronmanBank()).getBank(7)
                            .addItems(builder.fromJson(reader.get("Bank-7").getAsJsonArray(), Item[].class), true);
                        needsUpdating = false;
                    }
                    if (reader.has("Bank-8")) {
                        Gson builder = new GsonBuilder().create();
                        group.setBank(8, new GroupIronmanBank()).getBank(8)
                            .addItems(builder.fromJson(reader.get("Bank-8").getAsJsonArray(), Item[].class), true);
                        needsUpdating = false;
                    }

                } else {
                    System.out.println("The file " + file.getName() + " does not contain a valid JSON object.");
                }
                if (needsUpdating) {
                    System.out.println("Updated Group: " + group.getName() + " bank items");
                    saveGroup(group);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            GROUPS.add(group);
        }
    }


    public static void saveGroups() {
        for (IronmanGroup group : GROUPS) {
            saveGroup(group);
        }
    }


    public static void saveGroup(IronmanGroup group) {
        File file = new File(GroupConfig.path + group.getName() + ".json");
        try (FileWriter writer = new FileWriter(file)) {

            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject object = new JsonObject();

            object.addProperty("Name", group.getName());
            object.addProperty("UniqueID", group.getUniqueId());
            object.addProperty("Leader", group.getLeader());
            object.add("Members", builder.toJsonTree(group.getMembers()));
            if (group.getBank(0) != null) {
                object.add("Bank", builder.toJsonTree(group.getBank(0).getValidItems()));
            } else {
                group.setBank(0, new GroupIronmanBank());
                object.add("Bank", builder.toJsonTree(group.getBank(0).getValidItems()));
            }

            if (group.getBank(1) != null) {
                object.add("Bank-1", builder.toJsonTree(group.getBank(1).getValidItems()));
            } else {
                group.setBank(1, new GroupIronmanBank());
                object.add("Bank-1", builder.toJsonTree(group.getBank(1).getValidItems()));
            }

            if (group.getBank(2) != null) {
                object.add("Bank-2", builder.toJsonTree(group.getBank(2).getValidItems()));
            } else {
                group.setBank(2, new GroupIronmanBank());
                object.add("Bank-2", builder.toJsonTree(group.getBank(2).getValidItems()));
            }

            if (group.getBank(3) != null) {
                object.add("Bank-3", builder.toJsonTree(group.getBank(3).getValidItems()));
            } else {
                group.setBank(3, new GroupIronmanBank());
                object.add("Bank-3", builder.toJsonTree(group.getBank(3).getValidItems()));
            }

            if (group.getBank(4) != null) {
                object.add("Bank-4", builder.toJsonTree(group.getBank(4).getValidItems()));
            } else {
                group.setBank(4, new GroupIronmanBank());
                object.add("Bank-4", builder.toJsonTree(group.getBank(4).getValidItems()));
            }

            if (group.getBank(5) != null) {
                object.add("Bank-5", builder.toJsonTree(group.getBank(5).getValidItems()));
            } else {
                group.setBank(5, new GroupIronmanBank());
                object.add("Bank-5", builder.toJsonTree(group.getBank(5).getValidItems()));
            }

            if (group.getBank(6) != null) {
                object.add("Bank-6", builder.toJsonTree(group.getBank(6).getValidItems()));
            } else {
                group.setBank(6, new GroupIronmanBank());
                object.add("Bank-6", builder.toJsonTree(group.getBank(6).getValidItems()));
            }

            if (group.getBank(7) != null) {
                object.add("Bank-7", builder.toJsonTree(group.getBank(7).getValidItems()));
            } else {
                group.setBank(7, new GroupIronmanBank());
                object.add("Bank-7", builder.toJsonTree(group.getBank(7).getValidItems()));
            }

            if (group.getBank(8) != null) {
                object.add("Bank-8", builder.toJsonTree(group.getBank(8).getValidItems()));
            } else {
                group.setBank(8, new GroupIronmanBank());
                object.add("Bank-8", builder.toJsonTree(group.getBank(8).getValidItems()));
            }



            writer.write(builder.toJson(object));
        } catch (Exception e) {
            GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a ironman group!", e);
        }
    }


}
