package com.ruse.world.content.ZoneProgression;

import com.ruse.world.World;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.entity.impl.player.Player;

public class TierRequirements {


    public static void checkTierRequirements(Player player, int tier) {
        switch (tier) {
            case 2:
                hasIntermediateReqs(player);
                break;
            case 3:
                hasEliteReqs(player);
                break;
            case 4:
                hasMasterReqs(player);
                break;
            case 5:
                hasChallengerReqs(player);
                break;
        }
    }

    public static boolean hasIntermediateReqs(Player player) {
        boolean hasStaff = player.getInventory().contains(995,15000);
        if (player.unlockedIntermediateZones)
            return false;
        if (!hasStaff) {
            player.msgRed("You must sacrifice 15,000 coins to unlock Intermediate Zones.");
            return false;
        }
        new SelectionDialogue(player, "Unlocked Intermediate", new SelectionDialogue.Selection("Sacrifice 15,000 Coins", 0, p -> {
            if (hasStaff) {
                p.getInventory().delete(995, 15000);
                p.setUnlockedIntermediateZones(true);
                p.getPacketSender().sendChatboxInterfaceRemoval();
                p.msgFancyPurp("You have unlocked Intermediate Zones.");
                World.sendMessage("@red@<shad=0>" + p.getUsername() + "@yel@<shad=0> has unlocked Intermediate Zones!");
                return;
            }

        }),
                new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        return false;
    }

    public static boolean hasEliteReqs(Player player) {
        boolean hasStaff = player.getInventory().contains(995, 200000);
        if (player.unlockedEliteZones)
            return false;

        if (!player.unlockedIntermediateZones) {
            player.msgRed("You must unlock all previous zones first.");
            return false;
        }

        if (!hasStaff) {
            player.msgRed("You must sacrifice 200,000 coins to unlock Elite Zones.");
            return false;
        }

        new SelectionDialogue(player, "Unlocked Elite", new SelectionDialogue.Selection("Sacrifice 200,000 Coins?", 0, p -> {
            if (hasStaff) {
                p.getInventory().delete(995,200000);
                p.setUnlockedEliteZones(true);
                p.getPacketSender().sendChatboxInterfaceRemoval();
                p.msgFancyPurp("You have unlocked Elite Zones.");
                World.sendMessage("@red@<shad=0>" + p.getUsername() + "@yel@<shad=0> has unlocked Elite Zones!");
                return;
            }

        }),
                new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        return false;
    }

    public static boolean hasMasterReqs(Player player) {
        boolean hasStaff = player.getInventory().contains(995,500000);
        if (player.unlockedMasterZones)
            return false;

        if (!player.unlockedIntermediateZones || !player.unlockedEliteZones) {
            player.msgRed("You must unlock all previous zones first.");
            return false;
        }
        if (!hasStaff) {
            player.msgRed("You must sacrifice 500,000 coins to unlock Master Zones.");
            return false;
        }

        new SelectionDialogue(player, "Unlocked Master", new SelectionDialogue.Selection("Sacrifice 500,000 Coins?", 0, p -> {
            if (hasStaff) {
                p.getInventory().delete(995,500000);
                p.setUnlockedMasterZones(true);
                p.getPacketSender().sendChatboxInterfaceRemoval();
                p.msgFancyPurp("You have unlocked Master Zones.");
                World.sendMessage("@red@<shad=0>" + p.getUsername() + "@yel@<shad=0> has unlocked Master Zones!");
                return;
            }

        }),
                new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        return false;
    }

    public static boolean hasChallengerReqs(Player player) {
        boolean hasUnlock = player.getInventory().contains(2057);
        if (player.unlockedSpectralZones)
            return false;

        if (!player.unlockedIntermediateZones || !player.unlockedEliteZones || !player.unlockedMasterZones) {
            player.msgRed("You must unlock all previous zones first.");
            return false;
        }
        if (!hasUnlock) {
            player.msgRed("Sacrifice a Spectral Zones Unlock Scroll!");
            return false;
        }

        new SelectionDialogue(player, "Unlock Challenger", new SelectionDialogue.Selection("Unlock Challenger Tier + The Spectral Realm?", 0, p -> {
            if (hasUnlock) {
                p.getInventory().delete(2057, 1);
                p.setUnlockedSpectralZones(true);
                p.getPacketSender().sendChatboxInterfaceRemoval();
                p.msgFancyPurp("You have unlocked Challenger Zones + The Spectral Realm.");
                World.sendMessage("@red@<shad=0>" + p.getUsername() + "@yel@<shad=0> has unlocked Challenger Zones + The Spectral Realm.");
                return;
            }

        }),
                new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        return false;
    }
}


