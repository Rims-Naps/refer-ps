package com.ruse.world.content.slayercontent;

import com.ruse.model.Locations;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;

import java.util.*;

public class SlayerHelmets {

    public static boolean process(Player player, int keyId) {

        if (player == null)
            return false;

        Map<Set<Integer>, Integer> equipmentChances = new HashMap<>();
        equipmentChances.put(new HashSet<>(Arrays.asList(22000, 22001, 22002)), 32);//T1
        equipmentChances.put(new HashSet<>(Arrays.asList(12462, 12460, 12464)), 30);//T2
        equipmentChances.put(new HashSet<>(Arrays.asList(12463, 12461, 12465)), 28);//T3
        equipmentChances.put(new HashSet<>(Arrays.asList(22003)), 26);//VOID 1
        equipmentChances.put(new HashSet<>(Arrays.asList(12466)), 24);//VOID 2
        equipmentChances.put(new HashSet<>(Arrays.asList(12467)), 22);//VOID 3
        equipmentChances.put(new HashSet<>(Arrays.asList(2677)), 20);//CORRUPT 1
        equipmentChances.put(new HashSet<>(Arrays.asList(2678)), 18);//CORRUPT 2
        equipmentChances.put(new HashSet<>(Arrays.asList(2679)), 15);//CORRUPT 3
        equipmentChances.put(new HashSet<>(Arrays.asList(3010)), 15);//ELE
        equipmentChances.put(new HashSet<>(Arrays.asList(3011)), 15);//ELE
        equipmentChances.put(new HashSet<>(Arrays.asList(3012)), 15);//ELE
        equipmentChances.put(new HashSet<>(Arrays.asList(3013)), 14);//SPECTRAL 1
        equipmentChances.put(new HashSet<>(Arrays.asList(3014)), 13);//SPECTRAL 2
        equipmentChances.put(new HashSet<>(Arrays.asList(3015)), 12);//SPECTRAL 3


        boolean helmetFound = false;

        for (Map.Entry<Set<Integer>, Integer> entry : equipmentChances.entrySet()) {
            Set<Integer> equipmentSet = entry.getKey();
            boolean slayer = player.getLocation() == Locations.Location.SPECTRAL_DUNGEON || player.getPosition().getRegionId() == 9022 || player.getPosition().getRegionId() == 9023 || player.getPosition().getRegionId() == 13376 || player.getPosition().getRegionId() == 13120 || player.getPosition().getRegionId() == 8522 || player.getPosition().getRegionId() == 8522 || player.getPosition().getRegionId() == 8778 || player.getLocation() == Locations.Location.CORRUPT_DUNGEON || player.getLocation() == Locations.Location.ENCHANTED_FOREST || player.getPosition().getRegionId() == 10542 || player.getPosition().getRegionId() == 15403 || player.getPosition().getRegionId() == 14164 || player.getPosition().getRegionId() == 11588 || player.getPosition().getRegionId() == 13910 || player.getPosition().getRegionId() == 14676;
            int chance = slayer ? entry.getValue() : entry.getValue() * 4;


            for (int item : equipmentSet) {
                if (player.getEquipment().contains(item)) {
                    helmetFound = true;
                    if (Misc.getRandom(40) == 0) {
                        player.msgFancyPurp("You received a " + ItemDefinition.forId(keyId).getName() + "!");
                        player.getInventory().add(keyId, 1);
                    }
                    if (player.getSkillTree().isNodeUnlocked(Node.BOUNTY_HUNTER) && Misc.getRandom(40) >= 37) {
                        player.msgFancyPurp("You received a " + ItemDefinition.forId(keyId).getName() + " from your skill perk!");
                        player.getInventory().add(keyId, 1);
                    }
                    if (Misc.getRandom(chance) == 0) {
                        player.msgFancyPurp("You received a " + ItemDefinition.forId(keyId).getName() + "!");
                        player.msgRed("" + ItemDefinition.forId(item).getName() + " increased your key rate to 1/" + chance + ".");
                        player.getInventory().add(keyId, 1);
                        return true;
                    }
                }
            }
        }

        //no helmet
        if (!helmetFound) {
            //player.sendMessage("no helm");
            if (player.getNodesUnlocked() != null) {
                if (player.getSkillTree().isNodeUnlocked(Node.BOUNTY_HUNTER)) {
                    if (Misc.getRandom(40) == 0) {
                        player.msgFancyPurp("You received a " + ItemDefinition.forId(keyId).getName() + " from your skill perk!");
                        player.getInventory().add(keyId, 1);
                    }
                }
            }
            if (Misc.getRandom(40) == 0) {
                player.msgFancyPurp("You received a " + ItemDefinition.forId(keyId).getName() + "!");
                player.getInventory().add(keyId, 1);
            }
        }

        return false;
    }
}
