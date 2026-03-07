package com.ruse.world.content.slot;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Item;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueExpression;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.entity.impl.player.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class SlotSystem {

    private final Player p;

    @Getter
    @Setter
    private int dailyGambles;


    int[][] multipliers = new int[6][30];

    int currentTotal = 0;

    boolean spinning = false;

    public void open() {
        p.getPA().sendInterface(43300);
        if (!spinning) {
            resetStrings();
            p.getPA().sendSlotSpin(0);
        }
        p.getPA().sendString(43302, "@yel@Athens's Slots!");
        p.getPA().sendItemOnInterface(43322, 995, 15000);
        p.getPA().sendItemOnInterface(43324, 23058, 1);
        p.getPA().sendItemOnInterface(43326, 6833, 3);
        p.getPA().sendItemOnInterface(43328, 23173, 5);
        p.getPA().sendItemOnInterface(43330, 8018, 5);
        p.getPA().sendItemOnInterface(43332, 13150, 1);
    }

    public void handleCompletion(int total) {
        if (!spinning) {
            return;
        }
        currentTotal = 0;
        for (int i = 0; i < 5; i++) {
            currentTotal += multipliers[0][i];

        }
        for (int i = 5; i < 10; i++) {
            currentTotal += multipliers[1][i];

        }
        for (int i = 10; i < 15; i++) {
            currentTotal += multipliers[2][i];

        }
        for (int i = 15; i < 20; i++) {
            currentTotal += multipliers[3][i];

        }
        for (int i = 20; i < 25; i++) {
            currentTotal += multipliers[4][i];

        }
        for (int i = 25; i < 30; i++) {
            currentTotal += multipliers[5][i];
        }

        boolean mega = currentTotal >= 100;
        boolean vRare = currentTotal >= 85;
        boolean rare = currentTotal >= 75;
        boolean common = currentTotal >= 55;


        if (mega) {//mega rewards
            if (Misc.random(12) == 0) {
                p.getInventory().add(13150, 1);
                World.sendMessage("@mag@<shad=0>[JACKPOT] @red@" + p.getUsername() + " @mag@Just hit the Mega Slots Jackpot! Won an Owner Chest!");

            } else {
                p.getInventory().add(23173, 5);
                World.sendMessage("@mag@<shad=0>[SLOTS] @red@" + p.getUsername() + " @mag@Just won 5 Athens Chests from Slots!");
            }
        } else if (vRare) {
            if (Misc.random(10) == 0) {
                p.getInventory().add(15357, 4);
                World.sendMessage("@mag@<shad=0>[SLOTS] @red@" + p.getUsername() + " @mag@Just won 4 Double Damage Scrolls from Slots!");


            } else {
                p.getInventory().add(8018, 5);
                World.sendMessage("@mag@<shad=0>[SLOTS] @red@" + p.getUsername() + " @mag@Just won 5 Plunder teleports from Slots!");

            }

        } else if (rare) {
            if (Misc.random(4) == 0) {
                p.getInventory().add(23058, 1);
                World.sendMessage("@mag@<shad=0>[SLOTS] @red@" + p.getUsername() + " @mag@Just won a $25 Bond from Slots!");

            } else {
                p.getInventory().add(6833, 3);
            }
        } else if (common) {
            if (Misc.random(2) == 0) {
                p.getInventory().add(995, 15000);
            } else {
                p.getInventory().add(995, 7500);
            }
        }

        //K so here handle rewards, base it on currentTotal. Conventionally higher total = better reward.


        if (currentTotal != total) {
            p.sendMessage("Error handling! Report to Polo (Code SS1).");
        }

        spinning = false;
    }

    public void randomizeMultipliers() {
        double multiplier = 1;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 30; j++) {
                if (Misc.random(10) == 0) {
                    multipliers[i][j] = 1;
                } else if (Misc.random(5) == 0) {
                    multipliers[i][j] = 2;
                } else if (Misc.random((int) (40 / multiplier)) == 25) {
                    multipliers[i][j] = 10;
                } else if (Misc.random((int) (70 / multiplier)) == 20) {
                    multipliers[i][j] = 9;
                } else if (Misc.random((int) (60 / multiplier)) == 15) {
                    multipliers[i][j] = 8;
                } else if (Misc.random((int) (50 / multiplier)) == 10) {
                    multipliers[i][j] = 7;
                } else if (Misc.random((int) (40 / multiplier)) == 5) {
                    multipliers[i][j] = 6;
                } else if (Misc.random((int) (30 / multiplier)) == 4) {
                    multipliers[i][j] = 5;
                } else if (Misc.random((int) (20 / multiplier)) == 2) {
                    multipliers[i][j] = 4;
                } else if (Misc.random(3) == 2) {
                    multipliers[i][j] = 3;
                } else if (Misc.random(5) == 1) {
                    multipliers[i][j] = 2;
                } else {
                    multipliers[i][j] = 1;
                }

            }
        }
    }

    public static String getColor(int amount) {

        switch (amount) {
            case 1:
            case 2:
                return "@whi@";
            case 3:
            case 4:
                return "@lre@";
            case 5:
            case 6:
                return "@gre@";
            case 7:
            case 8:
                return "@cya@";
            case 9:
                return "@mag@";
            case 10:
                return "@yel@";
        }

        return "";
    }

    public boolean handleButton(int id) {

        switch (id) {
            case -22218:

                return true;
            case -22230:
                if (spinning) {
                    p.sendMessage("Slots are already Spinning!");
                    return true;
                }
                if (dailyGambles >= 20) {
                    p.sendMessage("You have already used your  20 daily spins!");
                    return true;
                }

                boolean contains = p.getInventory().contains(19051, 1); //Set to check for required item

                if (!contains) {
                    p.sendMessage("You Need a Slots Ticket to Gamble!");
                    return true;
                }
                p.getInventory().delete(19051, 1);
                spinMachine();
                return true;

        }

        return false;
    }

    private void resetStrings() {
        for (int i = 0; i < 5; i++) {
            p.getPA().sendString(43402 + i, "0");

        }
        for (int i = 0; i < 10; i++) {
            p.getPA().sendString(43552 + i, "0");

        }
        for (int i = 0; i < 15; i++) {
            p.getPA().sendString(43702 + i, "0");

        }
        for (int i = 0; i < 20; i++) {
            p.getPA().sendString(43852 + i, "0");

        }
        for (int i = 0; i < 25; i++) {
            p.getPA().sendString(44002 + i, "0");

        }
        for (int i = 0; i < 30; i++) {
            p.getPA().sendString(44152 + i, "0");

        }
    }

    public void spinMachine() {
        if (spinning) {
            return;
        }
        resetStrings();
        p.getPA().sendSlotSpin(0);
        dailyGambles += 1;
        spinning = true;
        p.sendMessage("Daily slot spins remaining - " + (20 - dailyGambles) + ".");

        TaskManager.submit(new Task(1, true) {
            int tick = 0;

            @Override
            public void execute() {

                if (tick == 1) {
                    randomizeMultipliers();
                }

                if (tick == 2) {
                    for (int i = 0; i < 5; i++) {
                        p.getPA().sendString(43402 + i, getColor(multipliers[0][i]) + multipliers[0][i]);

                    }
                    for (int i = 0; i < 10; i++) {
                        p.getPA().sendString(43552 + i, getColor(multipliers[1][i]) + multipliers[1][i]);

                    }
                    for (int i = 0; i < 15; i++) {
                        p.getPA().sendString(43702 + i, getColor(multipliers[2][i]) + multipliers[2][i]);

                    }
                    for (int i = 0; i < 20; i++) {
                        p.getPA().sendString(43852 + i, getColor(multipliers[3][i]) + multipliers[3][i]);

                    }
                    for (int i = 0; i < 25; i++) {
                        p.getPA().sendString(44002 + i, getColor(multipliers[4][i]) + multipliers[4][i]);

                    }
                    for (int i = 0; i < 30; i++) {
                        p.getPA().sendString(44152 + i, getColor(multipliers[5][i]) + multipliers[5][i]);

                    }
                }
                if (tick == 3) {
                    p.getPA().sendSlotSpin(1);
                    stop();
                }
                tick++;
            }
        });

    }

}
