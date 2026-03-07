package com.ruse.world.content.timers.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.world.content.skill.impl.summoning.SummoningTab;
import com.ruse.world.entity.impl.player.Player;

public class NecromancyTimer {

    Player player;

    private final int INTERFACE_ID = 48308;

    public NecromancyTimer(Player player) {
        this.player = player;
    }

    public boolean isActive() {
        return player.getNecrotimeleft() > 0;
    }

    public void handleLogin() {
        updateTimerDisplay();
    }


    public void addTime(int additionalSeconds) {
        if (isActive()) {
            player.setNecrotimeleft(player.getNecrotimeleft() + additionalSeconds);
        } else {
            player.setNecrotimeleft(additionalSeconds);
            run();
        }
    }
    private void run() {
        TaskManager.submit(new Task(2, true) { // run every second (2 game ticks)

            @Override
            protected void execute() {
                if (!isActive()) {
                    player.getPacketSender().sendWalkableInterface(INTERFACE_ID, false);
                    player.sendMessage("@red@Your Follower Crumbles to the ground");
                    SummoningTab.handleDismiss(player, true, true);
                    stop();
                    updateTimerDisplay();
                    return;
                }
                updateTimerDisplay();
                player.setNecrotimeleft(player.getNecrotimeleft() - 1); // subtract one second
            }
        });
    }

    public void updateTimerDisplay() {
        int totalTimeInSeconds = player.getNecrotimeleft();
        int hours = totalTimeInSeconds / 3600;
        int minutes = (totalTimeInSeconds % 3600) / 60;
        int seconds = totalTimeInSeconds % 60;

        String timeLeft;
        int STRING_ID = 48313;
        if (hours > 0) {
            timeLeft = String.format("%dh %02dm", hours, minutes);
        } else {
            timeLeft = String.format("%02d:%02d", minutes, seconds);
        }
        player.getPacketSender().sendString(STRING_ID, timeLeft);
    }
}
