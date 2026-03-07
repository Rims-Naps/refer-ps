package com.ruse.engine.task.impl.globalevents;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.world.World;

public class GlobalBOGOTask extends Task {

    public GlobalBOGOTask() {
        super(1, 0, true);
    }

    int timer = 0;

    @Override
    protected void execute() {
        if (timer < 1) {
            timer = 6000;
            World.sendMessage("@mag@[BOGO] Buy one get one on store has been activated for a hour!");
            GameSettings.BOGO = true;
        } else if(timer == 1) {
            World.sendMessage("@mag@[BOGO] Buy one get one on store has been deactivated.");
            GameSettings.BOGO = false;
            stop();
        } else {
            timer--;
        }
    }
}