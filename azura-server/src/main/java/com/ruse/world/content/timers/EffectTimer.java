package com.ruse.world.content.timers;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Skill;
import com.ruse.world.content.Consumables;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import static com.ruse.model.Skill.*;

/**
 * @Author Bradley Englehart - November 29, 2023
 */

public abstract class EffectTimer {

    Player player;
    @Getter@Setter
    int timeLeft;
    @Getter@Setter
    int timeForTask;
    @Getter
    int itemId;
    String nameOfEffect;

    public void run() {
        TaskManager.submit(new Task(1, true) { // run every second (2 game ticks)

            @Override
            protected void execute() {
                if (!isActive()) {

                    if (nameOfEffect == "Fury" || nameOfEffect == "Rage")
                        Consumables.resetCombatBoosts(player);

                    player.msgRed("@red@Your " + nameOfEffect + " effect has run out!");
                    stop();
                    return;
                }
                timeForTask = (timeForTask - 1); // subtract one second
                timeLeft = (int) (timeForTask * 0.6);
            }
        });
    }

    public EffectTimer(Player player, int itemId, String effectName){
        this.player = player;
        this.itemId = itemId;
        this.nameOfEffect = effectName;
    }

    public boolean isActive() {
        return timeForTask > 0;
    }

    public void handleLogin() {
        timeLeft = (int) (timeForTask * 0.6);
        if (isActive()) {
            player.getPacketSender().sendEffectsTimer((short) timeLeft, itemId);
            run();
        }
    }

    public void addTime(int additionalSeconds) {
        if (isActive()) {
            timeLeft = (timeLeft + additionalSeconds);
            timeForTask = (int) (timeLeft / 0.6);
            player.getPacketSender().sendEffectsTimer((short) timeLeft, itemId);
        } else {
            timeLeft = (additionalSeconds);
            timeForTask = (int) (timeLeft / 0.6);
            player.getPacketSender().sendEffectsTimer((short) timeLeft, itemId);
            run();
        }
    }

    public String getName() {
        return nameOfEffect;
    }

}
