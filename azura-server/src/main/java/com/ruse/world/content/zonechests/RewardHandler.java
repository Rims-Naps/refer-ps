package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;

public class RewardHandler {

    public static void handleItemReward(Player player, Item item) {
        boolean hasPouch = player.getInventory().contains(18015);
        boolean itemAddedToPouch = false;

        if (hasPouch) {
            switch (item.getId()) {
                case 19062: // Monster Essence
                    int monsterEssenceAmount = item.getAmount();
                    player.setMonsteressence(player.getMonsteressence() + monsterEssenceAmount);
                    itemAddedToPouch = true;
                    player.sendMessage("You have received " + monsterEssenceAmount + " Monster Essence, added to your pouch.");
                    break;
                case 11054: // Fire Essence
                    int fireEssenceAmount = item.getAmount();
                    player.setFireessence(player.getFireessence() + fireEssenceAmount);
                    itemAddedToPouch = true;
                    player.sendMessage("You have received " + fireEssenceAmount + " Fire Essence, added to your pouch.");
                    break;
                case 11056: // Water Essence
                    int waterEssenceAmount = item.getAmount();
                    player.setWateressence(player.getWateressence() + waterEssenceAmount);
                    itemAddedToPouch = true;
                    player.sendMessage("You have received " + waterEssenceAmount + " Water Essence, added to your pouch.");
                    break;
                case 11052: // Earth Essence
                    int earthEssenceAmount = item.getAmount();
                    player.setEarthessence(player.getEarthessence() + earthEssenceAmount);
                    itemAddedToPouch = true;
                    player.sendMessage("You have received " + earthEssenceAmount + " Earth Essence, added to your pouch.");
                    break;
                case 3576: // Slayer Essence
                    int slayerEssenceAmount = item.getAmount();
                    player.setSlayeressence(player.getSlayeressence() + slayerEssenceAmount);
                    itemAddedToPouch = true;
                    player.sendMessage("You have received " + slayerEssenceAmount + " Slayer Essence, added to your pouch.");
                    break;
                case 6466: // Beast Essence
                    int beastEssenceAmount = item.getAmount();
                    player.setBeastEssence(player.getBeastEssence() + beastEssenceAmount);
                    itemAddedToPouch = true;
                    player.sendMessage("You have received " + beastEssenceAmount + " Beast Essence, added to your pouch.");
                    break;
                case 3502: // Corrupt Essence
                    int corruptEssenceAmount = item.getAmount();
                    player.setCorruptEssence(player.getCorruptEssence() + corruptEssenceAmount);
                    itemAddedToPouch = true;
                    player.sendMessage("You have received " + corruptEssenceAmount + " Corrupt Essence, added to your pouch.");
                    break;
            }
        }

        if (!itemAddedToPouch) {
            if (!player.getInventory().canHold(item)) {
                player.depositItemBank(new Item(item.getId(), item.getAmount()));
                player.sendMessage("<shad=0>@red@Your " + item.getDefinition().getName() + " reward has been put in your bank. <shad=0>@red@x" + item.getAmount() + " " + item.getDefinition().getName());
            } else {
                player.getInventory().add(item.getId(), item.getAmount());
                player.sendMessage("<shad=0>@red@Your " + item.getDefinition().getName() + " reward has been added to your inventory.");
            }
        }
    }
}
