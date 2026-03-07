package com.ruse.world.content.ExpLamps;

import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.content.dialogue.SelectionDialogue;

public class PrayerUnlockScrolls {

    public static boolean handlePrayerClaim(Player player, int item) {
        PrayerScrollData scroll = PrayerScrollData.forId(item);
        if (scroll == null)
            return false;
        if (!player.getClickDelay().elapsed(600)) {
            player.sendMessage("Slow Down...");
            return false;
        }
        player.getClickDelay().reset();

        // Define the confirmation dialogue
        new SelectionDialogue(player, "Are you sure you want to unlock " + scroll.name() + "?",
                new SelectionDialogue.Selection("Yes", 0, plr -> {
                    unlockPrayer(plr, scroll);  // Call the method to unlock the prayer
                    plr.getPacketSender().sendChatboxInterfaceRemoval();
                }),
                new SelectionDialogue.Selection("No", 1, plr -> plr.getPacketSender().sendChatboxInterfaceRemoval())
        ).start();

        return true;
    }

    private static void unlockPrayer(Player player, PrayerScrollData scroll) {
        switch (scroll) {
            case GAIAS_BLESSING:
                if (player.isUnlockedGaiablessingPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Gaia's Blessing Unlocked!");
                    return;
                }
                player.setUnlockedGaiablessingPrayer(true);
                break;
            case SERENITY:
                if (player.isUnlockedSerenityPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Serenity Unlocked!");
                    return;
                }
                player.setUnlockedSerenityPrayer(true);
                break;
            case ROCKHEART:
                if (player.isUnlockedRockheartPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Rockheart Unlocked!");
                    return;
                }
                player.setUnlockedRockheartPrayer(true);
                break;
            case GEOVIGOR:
                if (player.isUnlockedGeovigorPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Geovigor Unlocked!");
                    return;
                }
                player.setUnlockedGeovigorPrayer(true);
                break;
            case STONEHAVEN:
                if (player.isUnlockedStonehavenPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Stonehaven Unlocked!");
                    return;
                }
                player.setUnlockedStonehavenPrayer(true);
                break;
            case EBB_FLOW:
                if (player.isUnlockedEbbflowPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Ebb&Flow Unlocked!");
                    return;
                }
                player.setUnlockedEbbflowPrayer(true);
                break;
            case AQUASTRIKE:
                if (player.isUnlockedAquastrikePrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Aquastrike Unlocked!");
                    return;
                }
                player.setUnlockedAquastrikePrayer(true);
                break;
            case RIPTIDE:
                if (player.isUnlockedRiptidePrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Riptide Unlocked!");
                    return;
                }
                player.setUnlockedRiptidePrayer(true);
                break;
            case SEASLICER:
                if (player.isUnlockedSeaslicerPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have SeaSlicer Unlocked!");
                    return;
                }
                player.setUnlockedSeaslicerPrayer(true);
                break;
            case SWIFTIDE:
                if (player.isUnlockedSwifttidePrayer()) {
                    player.sendMessage("@red@<shad=0>You already have SwiftTide Unlocked!");
                    return;
                }
                player.setUnlockedSwifttidePrayer(true);
                break;
            case CINDERS_TOUCH:
                if (player.isUnlockedCindersTouch()) {
                    player.sendMessage("@red@<shad=0>You already have Cinder's Touch Unlocked!");
                    return;
                }
                player.setUnlockedCindersTouch(true);
                break;
            case EMBERBLAST:
                if (player.isUnlockedEmberblastPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have EmberBlast Unlocked!");
                    return;
                }
                player.setUnlockedEmberblastPrayer(true);
                break;
            case INFERNIFY:
                if (player.isUnlockedInfernifyPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Infernify Unlocked!");
                    return;
                }
                player.setUnlockedInfernifyPrayer(true);
                break;
            case BLAZEUP:
                if (player.isUnlockedBlazeupPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have BlazeUp Unlocked!");
                    return;
                }
                player.setUnlockedBlazeupPrayer(true);
                break;
            case INFERNO:
                if (player.isUnlockedInfernoPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Inferno Unlocked!");
                    return;
                }
                player.setUnlockedInfernoPrayer(true);
                break;
            case MALEVOLENCE:
                if (player.isUnlockedMalevolencePrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Malevolence unlocked!");
                    return;
                }
                player.setUnlockedMalevolencePrayer(true);
                break;
            case DESOLATION:
                if (player.isUnlockedDesolationPrayer()) {
                    player.sendMessage("@red@<shad=0>You already have Desolation unlocked!");
                    return;
                }
                player.setUnlockedDesolationPrayer(true);
                break;
        }

        player.getInventory().delete(scroll.itemId, 1);
        World.sendMessage("<col=AF70C3><shad=0>[PRAYER] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>just unlocked " + scroll.name().replace("_", " ") + "!");
    }

    enum PrayerScrollData {
        // EARTH
        GAIAS_BLESSING(20030), SERENITY(20031), ROCKHEART(20032), GEOVIGOR(20033), STONEHAVEN(20034),
        // WATER
        EBB_FLOW(20035), AQUASTRIKE(20036), RIPTIDE(20037), SEASLICER(20038), SWIFTIDE(20039),
        // FIRE
        CINDERS_TOUCH(20040), EMBERBLAST(20041), INFERNIFY(20042), BLAZEUP(20043), INFERNO(20044),
        // VOID
        MALEVOLENCE(20045), DESOLATION(20046),;

        private int itemId;

        PrayerScrollData(int itemId) {
            this.itemId = itemId;
        }

        public int getItemId() {
            return this.itemId;
        }

        public static PrayerUnlockScrolls.PrayerScrollData forId(int id) {
            for (PrayerUnlockScrolls.PrayerScrollData PrayerScrollData : PrayerUnlockScrolls.PrayerScrollData.values()) {
                if (PrayerScrollData != null && PrayerScrollData.getItemId() == id)
                    return PrayerScrollData;
            }
            return null;
        }
    }
}
