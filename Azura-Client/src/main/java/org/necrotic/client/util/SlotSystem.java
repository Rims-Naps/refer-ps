package org.necrotic.client.util;

import java.util.List;

import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.util.task.Task;
import org.necrotic.client.util.task.TaskManager;

public class SlotSystem {

    static List<Integer> multipliers;

    static List<Integer> rewards;

    static List<Integer> toDisplay;

    static int tick = 0;

    static boolean justSpun = false;


    private static int getMultiplier(String text) {
        if (text.length() < 5) {
            return Integer.parseInt(text);
        }
        return Integer.parseInt(text.substring(5));
    }

    private static void updateLootTable() {
        int lootTable = 0;
        if (tick == 0 && !justSpun) {
            RSInterface.interfaceCache[43304].message = "None";
        } else {
            if (tick > 0) {
                if (tick <= 33) {
                    lootTable = 0;
                } else if (tick <= 71) {
                    for (int child : RSInterface.interfaceCache[43400].children) {
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                } else if (tick <= 117) {
                    for (int child : RSInterface.interfaceCache[43400].children) {
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 5; i < 10; i++) {
                        int child = RSInterface.interfaceCache[43550].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                } else if (tick <= 176) {
                    for (int child : RSInterface.interfaceCache[43400].children) {
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 5; i < 10; i++) {
                        int child = RSInterface.interfaceCache[43550].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 10; i < 15; i++) {
                        int child = RSInterface.interfaceCache[43700].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                } else if (tick <= 251) {
                    for (int child : RSInterface.interfaceCache[43400].children) {
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 5; i < 10; i++) {
                        int child = RSInterface.interfaceCache[43550].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 10; i < 15; i++) {
                        int child = RSInterface.interfaceCache[43700].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 15; i < 20; i++) {
                        int child = RSInterface.interfaceCache[43850].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                } else if (tick <= 366) {
                    for (int child : RSInterface.interfaceCache[43400].children) {
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 5; i < 10; i++) {
                        int child = RSInterface.interfaceCache[43550].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 10; i < 15; i++) {
                        int child = RSInterface.interfaceCache[43700].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 15; i < 20; i++) {
                        int child = RSInterface.interfaceCache[43850].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 20; i < 25; i++) {
                        int child = RSInterface.interfaceCache[44000].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                    for (int i = 25; i < 30; i++) {
                        int child = RSInterface.interfaceCache[44150].children[i];
                        lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                    }
                }
            } else if (justSpun) {
                for (int child : RSInterface.interfaceCache[43400].children) {
                    lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                }
                for (int i = 5; i < 10; i++) {
                    int child = RSInterface.interfaceCache[43550].children[i];
                    lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                }
                for (int i = 10; i < 15; i++) {
                    int child = RSInterface.interfaceCache[43700].children[i];
                    lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                }
                for (int i = 15; i < 20; i++) {
                    int child = RSInterface.interfaceCache[43850].children[i];
                    lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                }
                for (int i = 20; i < 25; i++) {
                    int child = RSInterface.interfaceCache[44000].children[i];
                    lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                }
                for (int i = 25; i < 30; i++) {
                    int child = RSInterface.interfaceCache[44150].children[i];
                    lootTable += getMultiplier(RSInterface.interfaceCache[child].message);
                }
                Client.getOut().putOpcode(198);
                Client.getOut().putShort(lootTable);
            } // is it the coin amt they high asf
            String message = "@red@No Prize";
            if (lootTable >= 55) {
                message = "@whi@Common";
            }
            if (lootTable >= 75) {
                message = "@cya@Rare";
            }
            if (lootTable >= 85) {
                message = "@mag@Very Rare";
            }
            if (lootTable >= 100) {
                message = "@yel@MEGA!";
            }
            RSInterface.interfaceCache[43304].message = message + " ("+(lootTable)+")";
        }
    }

    public static void testSlotChange() {
        justSpun = false;
        TaskManager.submit(new Task(1, true) {
            @Override
            protected void execute() {
                int speed = (tick <= 33 ? 7
                        : (tick <= 71 ? 6
                        : (tick <= 117 ? 5 : (tick <= 176 ? 4 : (tick <= 251 ? 3 : (tick <= 366 ? 2 : 0))))));
                for (int i = 0; i < 5; i++) {
                    RSInterface.interfaceCache[43400].childY[i] += (tick <= 33 ? (tick <= 32 ? speed : 5) : 0);
                }
                for (int i = 0; i < 10; i++) {
                    RSInterface.interfaceCache[43550].childY[i] += (tick <= 71 ? speed : 0);
                }
                for (int i = 0; i < 15; i++) {
                    RSInterface.interfaceCache[43700].childY[i] += (tick <= 117 ? speed : 0);
                }
                for (int i = 0; i < 20; i++) {
                    RSInterface.interfaceCache[43850].childY[i] += (tick <= 176 ? (tick <= 174 ? speed : 1) : 0);
                }
                for (int i = 0; i < 25; i++) {
                    RSInterface.interfaceCache[44000].childY[i] += (tick <= 251 ? (tick <= 250 ? speed : 3) : 0);
                }
                for (int i = 0; i < 30; i++) {
                    RSInterface.interfaceCache[44150].childY[i] += (tick <= 366 ? (tick <= 365 ? speed : 1) : 0);
                }
                updateLootTable();
                tick++;
                if (tick == 367) {
                    tick = 0;
                    justSpun = true;
                    updateLootTable();
                    stop();
                }
            }
        });
    }

}
