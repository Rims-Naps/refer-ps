package com.ruse.world.content.zonechests;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.Skill;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BoxesTracker;
import com.ruse.world.content.collectionlogs.CollectionLogs;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

public class EnhancedTotem {
    private static final int ENHANCED_TOTEM_ID = 3781;
    public static final  int[] SUPER_RARE = new int[] { 20006,20007,20008, 20009};
    public static final int[] RARE = new int[] {23173, 10258, 15668, 15667};
    public static final int[] UNCOMMON = new int[] {621};
    public static final int COMMON = 995;

    public static void handleChest(Player p) {
        if (!p.getClickDelay().elapsed(3000))
            return;
        if (!p.getInventory().contains(ENHANCED_TOTEM_ID)) { //key id
            p.getPacketSender().sendMessage("<shad=0>@red@You need a totem to make an offering!");
            return;
        }
        p.performAnimation(new Animation(827));
        p.performGraphic(new Graphic(1322));
        p.getInventory().delete(ENHANCED_TOTEM_ID, 1);
        p.getPacketSender().sendMessage("<shad=0><col=AF70C3>You sacrifice your totem.");
        p.setTotalKeysOpened(p.getTotalKeysOpened() + 1);
        p.getSkillManager().addExperience(Skill.NECROMANCY, 150000);
        p.getDailyTaskInterface().MiscTasksCompleted(0, 1);
        p.getBoxTracker().add(new BoxesTracker.BoxEntry(ENHANCED_TOTEM_ID, 1));
        p.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(ENHANCED_TOTEM_ID, p) + " " + ItemDefinition.forId(ENHANCED_TOTEM_ID).getName());

        generateReward(p);
    }

    public static void generateReward(Player player){
        int player_save_chance = (int) player.getMembershipTier().getSave_key_chance();
        if (Misc.random(player_save_chance) == 1 && player.getMembershipTier().isMember()) {
            player.msgFancyPurp("Your Membership Saved your Totem upon opening!");
            player.getInventory().add(ENHANCED_TOTEM_ID, 1);
        }
        Random rng = new Random();

        if (player.getNodesUnlocked() != null) {
            if (player.getSkillTree().isNodeUnlocked(Node.DEFT_HANDS)) {
                if (Misc.random(85 ) == 1) {
                    player.msgFancyPurp("Your skill tree perk saved a Totem");
                    player.getInventory().add(ENHANCED_TOTEM_ID, 1);
                }
            }
        }

        if (Misc.getRandom(6) == 1) {
            int superrares = getRandomItem(SUPER_RARE);
            player.getInventory().add(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[SOULBANE]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>received a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Soulbane Event!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the Event!");
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(superrares));
        } else if (Misc.getRandom(4) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().add(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(rares));
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(rares).getName() + " @bla@<shad=0>from the Event!");
        } else if (Misc.getRandom(3) == 1) {
            int uncommon = getRandomItem(UNCOMMON);
            player.getInventory().add(uncommon, Misc.random(500,2000));
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(uncommon));
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(uncommon).getName() + " @bla@<shad=0>from the Event!");
        } else {
            player.getInventory().add(COMMON, 10000);
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(COMMON));
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(COMMON).getName() + " @bla@<shad=0>from the Event!");
        }
    }

    public static int getRandomItem(int[] array) {
        return array[Misc.getRandom(array.length - 1)];
    }

    public static final Item[] itemRewards = {
            new Item(995, 1),
            new Item(995, 1),
            new Item(995, 1),
            new Item(995, 1),
            new Item(995, 1),
            new Item(995, 1),
            new Item(995, 1),
            new Item(995, 1),
            new Item(995, 1),
            new Item(995, 1),

    };
}
