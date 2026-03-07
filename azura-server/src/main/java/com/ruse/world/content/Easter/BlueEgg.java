
package com.ruse.world.content;
import com.ruse.GameSettings;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class BlueEgg {

    public static int tick = -365;
    public static Position chestpos = new Position(3096, 3494, 0);
    public static Position[] SPAWN_POINTS = new Position[]{
            new Position(3165, 3533, 0),//home
            new Position(2708, 3100, 0),//slayer
            new Position(3042, 3995, 0),//journeyman mines
            new Position(3027, 3889, 0),//soulbane shop

    };

    public static String locationHints(Position pos) {
        if (pos.equals(new Position(3165, 3533, 0))) { //home
            return "...Home";
        }
        if (pos.equals(new Position(2708, 3100, 0))) { //slayer
            return "...Slayer";
        }
        if (pos.equals(new Position(3042, 3995, 0))) { //journeyman mines
            return "...Journeyman";
        }
        if (pos.equals(new Position(3027, 3889, 0))) { //soulbane shop
            return "...SoulBane";
        }

        return "";
    }

    public static void sequence() {
        tick++;

        // 15 minutes timer
        if (tick % 1500 == 0) {
            System.out.println(tick);

            for (NPC n : World.getNpcs()) {
                if (n != null) {
                    if (n.getId() == 6314) {
                        World.deregister(n);
                        System.out.println("DEREGISTERING: 6414");
                    }
                }
            }

                if (!World.npcIsRegistered(6314)) {
                    Position location = Misc.random(SPAWN_POINTS);
                    chestpos = location;
                    NPC ForgottenChest = new NPC(6314, chestpos);
                    World.register(ForgottenChest);
                    String message = "<shad=0><col=AF70C3>[EASTER] An Easter Egg has appeared" + locationHints(location);
                    World.sendMessage(message);
                    GameSettings.broadcastTime = 100;
                }
            }
        }

    public static void handleChestClick(int objectId, Player player) {
        if (objectId != 6314) {
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