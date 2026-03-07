package com.ruse.world.content;

import com.ruse.world.World;

public class ServerLevel {

    public static boolean xpMessage = false;
    public static boolean drMessage = false;
    public static boolean dmgMessage = false;
    public static boolean xpMessageOff = false;
    public static boolean drMessageOff = false;
    public static boolean dmgMessageOff = false;

    public static int checkPlayerCount(){
        if (World.getPlayers().size() >= 110){ // Dmg boost
            if (!dmgMessage){
                World.sendMessage("@bla@<shad=9B0CFF>[SERVER] Athens has reached Level 4. Dmg boost is now active");
            }
            dmgMessage = true;
            dmgMessageOff = true;
            return 3;
        } else if (World.getPlayers().size() >= 75){ // Dr boost
            if (!drMessage){
                World.sendMessage("@bla@<shad=9B0CFF>[SERVER] Athens has reached Level 3. Dr boost is now active");
            }
            if (dmgMessageOff){
                World.sendMessage("@bla@<shad=9B0CFF>[SERVER] Athens has dropped to Level 3 . DMG Boost is no longer active");
                dmgMessageOff = false;
                dmgMessage = false;
            }
            drMessage = true;
            drMessageOff = true;
            return 2;
        } else if (World.getPlayers().size() >= 25){ // Exp boost
            if (!xpMessage){
                World.sendMessage("@bla@<shad=9B0CFF>[SERVER] Athens has reached level 2. Xp boost is now active");
            }
            if (drMessageOff){
                World.sendMessage("@bla@<shad=9B0CFF>[SERVER] Athens has dropped to level 2. DR boost is no longer active");
                drMessageOff = false;
                drMessage = false;
            }
            xpMessageOff = true;
            xpMessage = true;
            return 1;
        } else { // No boost
            if (xpMessageOff){
                World.sendMessage("@bla@<shad=9B0CFF>[SERVER] Athens has dropped to level 1. Xp boost is no longer active");
                xpMessageOff = false;
            }
            xpMessage = false;
            drMessage = false;
            dmgMessage = false;
            return 0;
        }
    }

}