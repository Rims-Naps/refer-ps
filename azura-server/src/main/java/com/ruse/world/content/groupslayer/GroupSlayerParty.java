package com.ruse.world.content.groupslayer;

import com.ruse.model.Skill;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.world.content.PlayerPanel;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.slayer.SlayerTaskData;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupSlayerParty {

    @Getter
    private final Player owner;
    @Getter
    private final List<Player> members;

    private final Map<Integer, GroupSlayerTask> tasks;

    private static final int STARTING_POINT = 21382;

    private int currentPointsReceived = 0;
    private int currentXpReceived = 0;
    @Setter
    private SlayerMaster slayerMaster = null;


    public GroupSlayerParty(Player owner) {
        this.owner = owner;
        members = new ArrayList<>();
        members.add(owner);
        tasks = new HashMap<>();
    }

    public static void openInterface(Player player) {
        player.getPacketSender().sendInterface(STARTING_POINT);
        if (player.getGroupSlayerParty() != null) {
            player.getGroupSlayerParty().updateInterface(player);
        } else {
            clearNames(player);
        }
    }

    private int computeLevel() {
        int amount = owner.getGroupSlayerCompletions();
        return (int) (1 + log(Math.max(1, amount), 5));
    }

    private int getMaxMembersAllowed() {
        int level = computeLevel();
        return 3 + (level * 2);
    }

    private int computeLevelBonus() {
        int level = computeLevel();
        int levelBonus = (level - 1) * 10;
        if (members.size() == getMaxMembersAllowed()) {
            levelBonus += (level - 1) * 5;
        }
        return levelBonus;
    }

    private double log(double x, double base) {
        return Math.log(x) / Math.log(base);
    }

    public void join(Player player) {
        if (isOwner(player)) {
            player.sendMessage("Can't invite urself");
            return;
        }
        if (members.size() == getMaxMembersAllowed()) {
            player.sendMessage("The party is already full");
            return;
        }

        if (tasks.size() != 0) {
            player.sendMessage("The group has already started in this party");
            return;
        }

        members.add(player);
        player.setGroupSlayerParty(this);
        player.sendMessage("You have joined " + owner.getUsername() + "'s group slayer party");
        owner.sendMessage(player.getUsername() + " has joined your group slayer party");
        members.forEach(this::updateInterface);
    }

    public void leave(Player player) {
        members.remove(player);
        player.setGroupSlayerParty(null);
        boolean isOwner = isOwner(player);
        if (!isOwner) {
            player.sendMessage("You have left " + owner.getUsername() + "'s group slayer party");
        }
        owner.sendMessage(player.getUsername() + " has left your group slayer party");
        if (isOwner) {
            delete(player);
        }

        clearNames(player);

        members.forEach(this::updateInterface);
    }


    public void assignTasks(Player player) {
        if (!isOwner(player)) {
            player.sendMessage("Only the owner of the party can start group slayer");
            return;
        }
        if (!tasks.isEmpty()) {
            owner.sendMessage("You already have an active task");
            return;
        }

        currentPointsReceived = 0;
        currentXpReceived = 0;

        int taskCount = members.size();
        for (int i = 0; i < taskCount; i++) {
            SlayerMaster slayerMaster = owner.getSlayer().getSlayerMaster();
            SlayerTaskData taskData = SlayerTasks.getNewTaskData(slayerMaster, owner);
            SlayerTasks taskToSet = taskData.getTask();
            GroupSlayerTask task = new GroupSlayerTask(taskToSet, taskData.getSlayerTaskAmount());
            tasks.merge(taskToSet.getNpcId(), task, GroupSlayerTask::merge);

        }
        members.forEach(member -> member.moveTo(3095,3500,0));

        members.forEach(member -> member.sendMessage("Group slayer has started"));

        members.forEach(this::updateInterface);

    }

    public void handleKill(int npcId) {
        GroupSlayerTask task = tasks.get(npcId);
        if (task == null) {
            return;
        }

        task.handleKill();
        if (task.isCompleted()) {
            sendGroupMessage("Task " + task.getTaskMessage() + " has been completed");
            int points = getPointsReceived(owner, task);
            int xpReceived = task.getXpReceived();
            currentPointsReceived += points;
            currentXpReceived += xpReceived;
            tasks.remove(npcId);
        }

        if (tasks.size() == 0) {
            sendGroupMessage("Task completed");
            int memberCount = members.size();
            int levelBonus = (currentPointsReceived * computeLevelBonus()) / 100;
            int pointsPerMember = (currentPointsReceived + levelBonus) / memberCount;
            int xpPerMember = currentXpReceived / memberCount;
            members.forEach(member -> {
                member.getPointsHandler().setSlayerPoints(pointsPerMember, true);
                member.getSkillManager().addExperience(Skill.SLAYER, xpPerMember);
                member.sendMessage("You have received " + pointsPerMember + " points and " + xpPerMember + " slayer xp");
            });

            int completions = determineCompletions();
            owner.incrementGroupSlayerCompletions(completions);
        }
    }

    private int determineCompletions() {
        switch (slayerMaster) {
            case BEGINNER_SLAYER:
                return 1;
        }

        return 0;
    }

    private int getPointsReceived(Player player, GroupSlayerTask task) {
        int pointsReceived = 2;
        switch (task.getTask().getTaskMaster()) {
            case BEGINNER_SLAYER:
                pointsReceived = 5;
                break;
        }

        if (player.getSlayer().getTaskStreak() % 10 == 0) {
            player.getInventory().addDropIfFull(5023, 50);
            player.getPacketSender().sendMessage("You receive 50 Bonus Tickets for Completing a 10 Task Streak");
        } else if (player.getSlayer().getTaskStreak() % 5 == 0) {
            player.getInventory().addDropIfFull(5023, 25);
            player.getPacketSender().sendMessage("You receive 25 Bonus Tickets for Completing a 5 Task Streak");
        }

        PlayerPanel.refreshPanel(player);
        return pointsReceived;
    }

    private void sendGroupMessage(String message) {
        members.forEach(member -> member.sendMessage(message));
    }


    public void delete(Player player) {
        GroupSlayerParty party = player.getGroupSlayerParty();
        if (party == null) {
            return;
        }

        if (!party.isOwner(player)) {
            player.sendMessage("Only the owner can delete the party");
            return;
        }

        party.getMembers().stream().filter(p -> !p.equals(player)).forEach(member -> {
            clearNames(member);
            member.setGroupSlayerParty(null);
            member.sendMessage(player.getUsername() + " has deleted their group slayer party");
        });
        tasks.clear();
        party.getMembers().clear();
        player.setGroupSlayerParty(null);
        player.sendMessage("You have deleted your group slayer party");
        clearNames(player);
    }

    public void displayMembers() {
        System.out.println("Owner is: " + owner.getUsername());
        System.out.println("Members: " + members.stream()
                .map(Player::getUsername)
                .collect(Collectors.toList()));
    }

    public boolean isOwner(Player player) {
        return owner.getUsername().equals(player.getUsername());
    }

    public int getCurrentState() {
        switch (slayerMaster) {
            case BEGINNER_SLAYER:
                return 0;
        }

        return 0;
    }

    public void updateInterface(Player player) {
        clearNames(player);
        for (int i = 0; i < members.size(); i++) {
            Player member = members.get(i);
            player.getPacketSender().sendString(STARTING_POINT + 32 + i, member.getUsername());
        }

        int i = 0;
        for (GroupSlayerTask task : tasks.values()) {
            player.getPacketSender()
                    .sendString(STARTING_POINT + 60 + i, task.getCurrentAmount() + " " + NpcDefinition.forId(task.getTask()
                            .getNpcId()).getName());
            i++;
        }
    }

    private static void clearNames(Player player) {
        for (int i = 0; i < 10; i++) {
            player.getPacketSender().sendString(STARTING_POINT + 32 + i, "");
        }

        for (int i = 0; i < 20; i++) {
            player.getPacketSender().sendString(STARTING_POINT + 60 + i, "");
        }
    }
}
