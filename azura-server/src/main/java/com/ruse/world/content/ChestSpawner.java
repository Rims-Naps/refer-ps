package com.ruse.world.content;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class ChestSpawner {

    public static int tick = -200;
    public static Position chestpos = new Position(3096, 3494, 0);
    public static Position[] SPAWN_POINTS = new Position[]{
            new Position(3088, 3512, 0),//1
            new Position(3099, 3476, 0),//2
            new Position(3124, 3473, 0),//3
            new Position(3115, 3499, 0),//4
            new Position(3890, 2787, 0),//5
            //
            new Position(3048, 3667, 0),//6
            new Position(3095, 3618, 0),//7
            new Position(3143, 3737, 0),//8
            new Position(2143, 5131, 0),//9
            new Position(3036, 3048, 0),//10
            new Position(2094, 3230, 0),//11
            new Position(3041, 2975, 0),//12
            new Position(2473, 3791, 0),//13
            new Position(3173, 3599, 0),//14
            new Position(2151, 5035, 0),//15

            new Position(3018, 3680, 0),//15
            new Position(3057, 3613, 0),//15


    };

    public static String locationHints(Position pos) {
        if (pos.equals(new Position(3088, 3512, 0))) {
            return ".......There's no place like?";
        } else if (pos.equals(new Position(3099, 3476, 0))) {
            return ".......There's no place like?";
        }else if (pos.equals(new Position(3124, 3473, 0))) {
            return ".......There's no place like?";
        }else if (pos.equals(new Position(3115, 3499, 0))) {
            return ".......There's no place like?";
        }else if (pos.equals(new Position(3890, 2787, 0))) {
            return ".......The Sated?";//
        }else if (pos.equals(new Position(3048, 3667, 0))) {
            return ".......Hide and seek?";
        }else if (pos.equals(new Position(3095, 3618, 0))) {
            return ".......Hide and seek?";
        }else if (pos.equals(new Position(3143, 3737, 1))) {
            return ".......The End of Elite Tier?";
        }else if (pos.equals(new Position(2143, 5131, 0))) {
            return ".......The Toxic?";
        }else if (pos.equals(new Position(3036, 3048, 0))) {
            return ".......Travel to a Novice task?";
        }else if (pos.equals(new Position(2094, 3230, 0))) {
            return ".......The Mighty Ogre?";
        }else if (pos.equals(new Position(3041, 2975, 0))) {
            return ".......A Lunar Eclipse?";
        }else if (pos.equals(new Position(2473, 3791, 0))) {
            return ".......Rock Teeth? ";
        }else if (pos.equals(new Position(3173, 3599, 0))) {
            return ".......League of Legends?";
        }else if (pos.equals(new Position(2151, 5035, 0))) {
            return ".......You should be scared? ";
        }
        else if (pos.equals(new Position(3018, 3680, 0))) {
            return ".......Hide and seek?";
        }
        else if (pos.equals(new Position(3057, 3613, 0))) {
            return ".......Hide and seek?";
        }
        return "";
    }

    public static void sequence() {
        tick++;

        // 15 minutes timer
        if (tick % 1500 == 0) {
            if(!World.npcIsRegistered(767)) {
                Position location = Misc.random(SPAWN_POINTS);
                chestpos = location;
                NPC ForgottenChest = new NPC(767, chestpos);
                World.register(ForgottenChest);
                String message = "<col=6C1894>A Athens Chest has Appeared" + locationHints(location);
                World.sendBroadcastMessage(message);
                GameSettings.broadcastTime = 100;

            }
        }
    }

    public static void handleChestClick(int objectId, Player player) {
        if (objectId != 767) {
            return;
        }
        World.sendMessage("<col=AF70C3><shad=0>A Frozen Chest has been Opened!");
        TaskManager.submit(new Task(1, player, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 2) {
                    Item item = itemRewards[Misc.randomMinusOne(itemRewards.length)];
                    player.getInventory().add(item);
                    if (item.getDefinition() != null && item.getDefinition().getName() != null) {
                        player.getPacketSender().sendMessage("..and find " + Misc.anOrA(item.getDefinition().getName()) + " " + item.getDefinition().getName() + "!");
                    } else {
                        player.getPacketSender().sendMessage("...and find an item!");
                        World.sendMessage("<col=AF70C3><shad=0>A Frozen Chest has been Opened!");
                    }
                    stop();
                }
                tick++;
            }
        });
    }

    public static final Item[] itemRewards = {
            new Item(1451, 1),
            new Item(1452, 1),
            new Item(10945, 1),
            new Item(995, 500),
            new Item(1465, 1),
    };


}
