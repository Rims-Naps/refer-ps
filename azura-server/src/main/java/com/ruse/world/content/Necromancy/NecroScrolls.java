package com.ruse.world.content.ExpLamps;

import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

public class NecroScrolls {
    public static boolean handleClaim(Player player, int item) {
        ScrollData scroll = ScrollData.forId(item);
        if (scroll == null)
            return false;
        if (!player.getClickDelay().elapsed(600)) {
            player.sendMessage("Slow Down...");
            return false;
        }
        player.getClickDelay().reset();

        if (scroll == ScrollData.SKELETAL){
            if (player.isUnlockedSkeletalSpells()){
                player.sendMessage("@red@<shad=0>You already have Skeletal Spells Unlocked!");
                return false;
            }
            player.setUnlockedSkeletalSpells(true);
            player.getInventory().delete(scroll.itemId,1);
            World.sendMessage("<col=AF70C3><shad=0>[NECRO] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>just unlocked Skeletal Spells!");

        }
        if (scroll == ScrollData.DEMONIC){
            if (player.isUnlockedDemonicSpells()){
                player.sendMessage("@red@<shad=0>You already have Demonic Spells Unlocked!");
                return false;
            }
            player.setUnlockedDemonicSpells(true);
            player.getInventory().delete(scroll.itemId,1);
            World.sendMessage("<col=AF70C3><shad=0>[NECRO] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>just unlocked Demonic Spells!");

        }
        if (scroll == ScrollData.OGRE){
            if (player.isUnlockedOgreSpells()){
                player.sendMessage("@red@<shad=0>You already have Ogre Spells Unlocked!");
                return false;
            }
            player.setUnlockedOgreSpells(true);
            player.getInventory().delete(scroll.itemId,1);
            World.sendMessage("<col=AF70C3><shad=0>[NECRO] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>just unlocked Ogre Spells!");

        }
        if (scroll == ScrollData.SPECTRAL){
            if (player.isUnlockedSpectralSpells()){
                player.sendMessage("@red@<shad=0>You already have Spectral Spells Unlocked!");
                return false;
            }
            player.setUnlockedSpectralSpells(true);
            player.getInventory().delete(scroll.itemId,1);
            World.sendMessage("<col=AF70C3><shad=0>[NECRO] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>just unlocked Spectral Spells!");

        }
        if (scroll == ScrollData.MASTER){
            if (player.isUnlockedMasterSpells()){
                player.sendMessage("@red@<shad=0>You already have Master Spells Unlocked!");
                return false;
            }
            player.setUnlockedMasterSpells(true);
            player.getInventory().delete(scroll.itemId,1);
            World.sendMessage("<col=AF70C3><shad=0>[NECRO] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>just unlocked Master Spells!");

        }
        return false;
    }

    enum ScrollData {
        SKELETAL(20060), DEMONIC(20061), OGRE(20062), SPECTRAL(20063), MASTER(20064) ;

        ScrollData(int itemId) {
            this.itemId = itemId;
        }
        private int itemId;
        public int getItemId() {
            return this.itemId;
        }
        public static NecroScrolls.ScrollData forId(int id) {
            for (NecroScrolls.ScrollData ScrollData : NecroScrolls.ScrollData.values()) {
                if (ScrollData != null && ScrollData.getItemId() == id)
                    return ScrollData;
            }
            return null;
        }
    }

}
