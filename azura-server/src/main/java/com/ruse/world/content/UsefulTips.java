package com.ruse.world.content;

import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.model.Position;

public class UsefulTips {

    public static int tick = -55;
    public static Position chestpos = new Position(55, 55, 0);
    public static Position lastLocation = null;
    public static Position[] SPAWN_POINTS = new Position[]{
            new Position(1, 1, 0),
            new Position(1, 2, 0),
            new Position(1, 3, 0),
            new Position(1, 4, 0),
            new Position(1, 5, 0),
            new Position(1, 6, 0),
            new Position(1, 7, 0),
            new Position(1, 8, 0),
            new Position(1, 9, 0),
            new Position(1, 10, 0),
            new Position(1, 11, 0),
            new Position(1, 12, 0),
            new Position(1, 13, 0),
            new Position(1, 14, 0),
            new Position(1, 15, 0),
            new Position(1, 16, 0),
            new Position(1, 17, 0),
            new Position(1, 18, 0),



    };

    public static String Reminders(Position pos) {
        if (pos.equals(new Position(1, 1, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Thanks for Playing Athens!";
        }
        if (pos.equals(new Position(1, 2, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Remember to ::vote every 12 hours!";
        }
        if (pos.equals(new Position(1, 3, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Apprentice weapons can be transformed by operating it while equipped.";
        }
        if (pos.equals(new Position(1, 4, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Almost every monster on Athens can drop Runes.";
        }
        if (pos.equals(new Position(1, 5, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> You can combine Salts with an Elemental vial to forge powerful potions.";
        }
        if (pos.equals(new Position(1, 6, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> You can vote for the active server perk every hour, on the Vote Tab. ";
        }
        if (pos.equals(new Position(1, 7, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> A Void Rift will open for every 1k monster essence salvaged.";
        }
        if (pos.equals(new Position(1, 8, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> You can toggle Drop messages/Timers/Outlines and more on the Settings tab.";
        }
        if (pos.equals(new Position(1, 9, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Want to support Athens? Checkout #donation-deals!";
        }
        if (pos.equals(new Position(1, 10, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Vote now for a 1/15 chance to receive a vote crate!";
        }
        if (pos.equals(new Position(1, 11, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Feeling stuck? Check out our ::wiki for useful guides and info.";
        }
        if (pos.equals(new Position(1, 12, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> All keys can be opened @ ::chests!";
        }
        if (pos.equals(new Position(1, 13, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> If you're just getting started, type ::extras for a small boost.";
        }
        if (pos.equals(new Position(1, 14, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> View all loot/reward tables by typing ::loot";
        }
        if (pos.equals(new Position(1, 15, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> You can keep an account @ ::afk or ::mine while you're away!";
        }
        if (pos.equals(new Position(1, 16, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Grind Rings from raids, Amulets from slayer, Capes from Soulbane.";
        }
        if (pos.equals(new Position(1, 17, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> Obtain Weapon Molds from Raids and Armor Molds from slayer keys/superiors.";
        }
        if (pos.equals(new Position(1, 18, 0))) {
            return "@red@<shad=0>[INFO]@blu@<shad=0> If you're not using ammo, you're missing... out";
        }
        return "";
    }

    public static void sequence() {
        tick++;
        // 15 minutes timer
        if (tick % 900 == 0) {
            Position location = getRandomLocation();
            chestpos = location;
            String message = Reminders(location);
            World.sendMessage(message);
        }
    }

    private static Position getRandomLocation() {
        Position location;

        do {
            location = Misc.random(SPAWN_POINTS);
        } while (location.equals(lastLocation));

        lastLocation = location;
        return location;
    }
}





