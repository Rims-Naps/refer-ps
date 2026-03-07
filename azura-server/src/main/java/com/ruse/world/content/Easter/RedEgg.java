
package com.ruse.world.content;
import com.ruse.GameSettings;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class RedEgg {

    public static int tick = -1300;
    public static Position chestpos = new Position(3096, 3494, 0);
    public static Position[] SPAWN_POINTS = new Position[]{
            new Position(1497, 4954, 1),//RIFT
            new Position(3230, 2964, 0),//WOODCUTTING
            new Position(2657, 3973, 0),//CRYPT
            new Position(3609, 3352, 0),//DONOBOSS
            new Position(2979, 3235, 2),//UPSTAIRS HOME

    };

    public static String locationHints(Position pos) {
        if (pos.equals(new Position(1497, 4954, 1))) {//RIFT
            return "...";
        }
        if (pos.equals(new Position(3230, 2964, 0))) {//WOODCUTTING
            return "...";
        }
        if (pos.equals(new Position(2657, 3973, 0))) {//CRYPT
            return "...";
        }
        if (pos.equals(new Position(3609, 3352, 0))) {//DONOBOSS
            return "...";
        }
        if (pos.equals(new Position(2979, 3235, 2))) {//UPSTAIRS HOME
            return "...";
        }

        return "";
    }

    public static void sequence() {
        tick++;

        // 15 minutes timer
        if (tick % 1000 == 0) {
            System.out.println(tick);

            for (NPC n : World.getNpcs()) {
                if (n != null) {
                    if (n.getId() == 6307) {
                        World.deregister(n);
                        System.out.println("DEREGISTERING: 6307");
                    }
                }
            }

            if (!World.npcIsRegistered(6307)) {
                Position location = Misc.random(SPAWN_POINTS);
                chestpos = location;
                NPC ForgottenChest = new NPC(6307, chestpos);
                World.register(ForgottenChest);
                String message = "<shad=0><col=AF70C3>[EASTER] An Easter Egg has appeared" + locationHints(location);
                World.sendMessage(message);
                GameSettings.broadcastTime = 100;
            }
        }
    }

    public static void handleChestClick(int objectId, Player player) {
        if (objectId != 6307) {
            return;
        }
        Item item = itemRewards[Misc.randomMinusOne(itemRewards.length)];
        player.getInventory().add(item);
        player.msgRed("You found " + item.getAmount() +  "x " + item.getDefinition().getName());
        World.sendMessage("<shad=0><col=AF70C3>[EASTER] An Easter Egg has been Found!");

        if (item.getId() == 720){
            World.sendMessage("<shad=0><col=AF70C3>[EASTER] @red@" + player.getUsername() + " <shad=0><col=AF70C3>found an Egg crate inside an Easter Egg!");
        }
    }

    public static final Item[] itemRewards = {
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),
            new Item(5585, 2),

            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),
            new Item(15667, 2),

            new Item(10945, 1),
            new Item(10945, 1),
            new Item(10945, 1),
            new Item(10945, 1),
            new Item(10945, 1),
            new Item(10945, 1),
            new Item(10945, 1),
            new Item(10945, 1),
            new Item(10945, 1),
            new Item(10945, 1),

            new Item(20109, 5),
            new Item(720, 1),
            new Item(20109, 5),
            new Item(20109, 5),
            new Item(20109, 5),
            new Item(20109, 5),
            new Item(20109, 5),
            new Item(20109, 5),
            new Item(20109, 5),
            new Item(20109, 5),

    };


}