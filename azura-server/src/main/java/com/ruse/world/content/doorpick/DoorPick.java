package com.ruse.world.content.doorpick;

import java.util.concurrent.CopyOnWriteArrayList;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Animation;
import com.ruse.model.GameObject;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.CustomObjects;
import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueExpression;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.entity.impl.player.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class DoorPick {

    private final Player player;

    @Getter
    private int doorSelected = 0;

    @Getter
    private boolean inRoom = false;

    @Getter
    @Setter
    private static boolean picksOpen = false;

    public static CopyOnWriteArrayList<Item> commonLoot = new CopyOnWriteArrayList<Item>();

    public static CopyOnWriteArrayList<Item> rareLoot = new CopyOnWriteArrayList<Item>();

    public int doorNumber(int x, int y) {

        if (x == 2809 && y == 2637) {
            return 1;
        } else if (x == 2811 && y == 2650) {
            return 2;
        } else if (x == 2822 && y == 2642) {
            return 3;
        } else if (x == 2830 && y == 2638) {
            return 4;
        } else if (x == 2841 && y == 2639) {
            return 5;
        } else if (x == 2851 && y == 2637) {
            return 6;
        } else if (x == 2863 && y == 2636) {
            return 7;
        } else if (x == 2866 && y == 2648) {
            return 8;
        } else if (x == 2853 && y == 2652) { /// here
            return 9;
        } else if (x == 2831 && y == 2657) {
            return 10;
        } else if (x == 2813 && y == 2663) {
            return 11;
        } else if (x == 2821 && y == 2661) {
            return 12;
        } else if (x == 2826 && y == 2670) {
            return 13;
        } else if (x == 2842 && y == 2669) {
            return 14;
        } else if (x == 2853 && y == 2666) {
            return 15;
        } else if (x == 2862 && y == 2668) {
            return 16;
        }

        return 0;
    }

    public boolean handleObjectClick(int objectId, int objectX, int objectY) {
        if (objectId == 29828) {
            if (doorNumber(objectX, objectY) > 0) {
                if (player.getDoorPicksLeft() <= 0) {
                    player.sendMessage("<col=AF70C3><shad=0>You don't have any pulls remaining!");
                    return true;
                }
                if (!picksOpen) {
                    player.sendMessage("@red@<shad=0>Holes are not active, speak to Iowna...");
                    return true;
                }
                doorSelected = doorNumber(objectX, objectY);
                DialogueManager.start(player, new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return null;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[] {
                                "Are you sure you would like to select Hole " + doorNumber(objectX, objectY) + "?",
                                "You cannot change your mind!", ""};
                    }



                    @Override
                    public Dialogue nextDialogue() {
                        return new Dialogue() {

                            @Override
                            public DialogueType type() {
                                return DialogueType.OPTION;
                            }

                            @Override
                            public DialogueExpression animation() {
                                return null;
                            }

                            @Override
                            public String[] dialogue() {
                                return new String[] { "Proceed through Hole" + doorNumber(objectX, objectY) + ".",
                                        "Choose another Hole." };
                            }

                            @Override
                            public void specialAction() {
                                player.setDialogueActionId(1712);
                            }

                        };
                    }

                });
                return true;
            }
        }

        if (objectId == 434) {
            if (inRoom) {
                if (rareLoot.isEmpty()) {
                    player.sendMessage("@red@<shad=0>Please ask Iowna to fill the Holes ;)");
                    return true;
                }
                Item reward = Misc.randomElement(rareLoot);
                player.getInventory().add(reward);
                World.sendMessage("<shad=00d9d5>[CHEST PULL REWARD] " + player.getName() + " has obtained x"
                        + reward.getAmount() + " " + reward.getDefinition().getName() + "!");
                player.moveTo(new Position(2779, 2657, 0));
                inRoom = false;
                CustomObjects.deleteGlobalObject(new GameObject(434, new Position(2656, 4707), 10, 0));
            }
            return true;
        }

        if (objectId == 710) {
            if (inRoom) {
                if (commonLoot.isEmpty()) {
                    player.sendMessage("@red@<shad=0>Please ask Iowna to fill the Holes ;)");
                    return true;
                }

                Item reward = Misc.randomElement(commonLoot);
                World.sendMessage("<shad=686868>[Common Chest Pull] " + player.getName() + " has obtained x"
                        + reward.getAmount() + " " + reward.getDefinition().getName() + "!");

                player.getInventory().add(reward);
                player.moveTo(new Position(2779, 2657, 0));
                inRoom = false;
                CustomObjects.deleteGlobalObject(new GameObject(710, new Position(2656, 4707), 10, 0));
            }
            return true;
        }

        return false;
    }

    public void openDoor() {
        if (player.getDoorPicksLeft() > 0) {
            if (!picksOpen) {
                player.sendMessage("@red@<shad=0>Hole Picks are currently not active!");
                return;
            }
            if (commonLoot.isEmpty() || rareLoot.isEmpty()) {
                player.sendMessage("@red@<shad=0>Please ask Iowna to fill the Holes ;)");
                return;
            }
            int chance = Misc.random(6);
            TaskManager.submit(new Task(1) {
                int ticks = 0;

                @Override
                protected void execute() {
                    if (ticks == 1) {
                        player.performAnimation(new Animation(9606));
                        player.performGraphic(new Graphic(715));
                    } else if (ticks == 4) {
                        if (chance == 6) {
                            World.sendMessage("<shad=00d9d5>[CHEST PULL] " + player.getName()
                                    + " has received the mega rare chest!");
                        }
                        CustomObjects.spawnGlobalObject(
                                new GameObject(chance == 6 ? 434 : 710, new Position(2656, 4707), 10, 0));
                    } else if (ticks == 6) {
                        inRoom = true;
                        player.performAnimation(new Animation(12450));
                        player.performGraphic(new Graphic(715));
                        player.moveTo(new Position(2656, 4699, 0));
                    } else if (ticks == 10) {
                        stop();
                    }
                    ticks++;
                }
            });
            player.incrementDoorPicks(-1);
        }
    }
}
