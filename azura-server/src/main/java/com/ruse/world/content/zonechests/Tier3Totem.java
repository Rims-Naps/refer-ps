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

public class Tier3Totem {
    private static final int TIER_3_TOTEM_ID = 20082;
    public static final  int[] CAPE_RARE = new int[] {15713, 15710, 15715};

    public static final  int[] SUPER_RARE = new int[] { 20008, 20009,20061 ,20062 ,20063,20064};
    public static final int[] RARE = new int[] {23173, 10258, 17130, 15667,15670};
    public static final int[] UNCOMMON = new int[] {10258, 20066, 20066, };
    public static final int COMMON = 995;

    public static void handleChest(Player p) {
        if (!p.getClickDelay().elapsed(3000))
            return;
        if (!p.getInventory().contains(TIER_3_TOTEM_ID)) { //key id
            p.getPacketSender().sendMessage("<shad=0>@red@You need a totem to make an offering!");
            return;
        }
        p.performAnimation(new Animation(827));
        p.performGraphic(new Graphic(1322));
        p.getInventory().delete(TIER_3_TOTEM_ID, 1);
        p.getPacketSender().sendMessage("<shad=0><col=AF70C3>You sacrifice your totem.");
        p.setTotalKeysOpened(p.getTotalKeysOpened() + 1);
        p.getSkillManager().addExperience(Skill.NECROMANCY, 25000);
        p.getDailyTaskInterface().MiscTasksCompleted(0, 1);
        p.getBoxTracker().add(new BoxesTracker.BoxEntry(TIER_3_TOTEM_ID, 1));
        p.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(TIER_3_TOTEM_ID, p) + " " + ItemDefinition.forId(TIER_3_TOTEM_ID).getName());

        generateReward(p);
    }

    public static void generateReward(Player player){
        int player_save_chance = (int) player.getMembershipTier().getSave_key_chance();
        if (Misc.random(player_save_chance) == 1 && player.getMembershipTier().isMember()) {
            player.msgFancyPurp("Your Membership Saved your Totem upon opening!");
            player.getInventory().addDropIfFull(TIER_3_TOTEM_ID, 1);
        }
        Random rng = new Random();

        if (player.getNodesUnlocked() != null) {
            if (player.getSkillTree().isNodeUnlocked(Node.DEFT_HANDS)) {
                if (Misc.random(85 ) == 1) {
                    player.msgFancyPurp("Your skill tree perk saved a key");
                    player.getInventory().addDropIfFull(TIER_3_TOTEM_ID, 1);
                }
            }
        }

        if (Misc.getRandom(50) == 1) {
            int cape_rares = getRandomItem(CAPE_RARE);
            player.getInventory().addDropIfFull(cape_rares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[SOULBANE]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>received a <shad=0>@red@" +  ItemDefinition.forId(cape_rares).getName() + " <col=AF70C3><shad=0>from the Soulbane Event!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(cape_rares).getName() + " @bla@<shad=0>from the Event!");
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(cape_rares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }

        else if (Misc.getRandom(30) == 1) {
            int superrares = getRandomItem(SUPER_RARE);
            player.getInventory().addDropIfFull(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[SOULBANE]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>received a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Soulbane Event!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the Event!");
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        } else if (Misc.getRandom(20) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(rares));
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(rares).getName() + " @bla@<shad=0>from the Event!");
        } else if (Misc.getRandom(10) == 1) {
            int uncommon = getRandomItem(UNCOMMON);
            player.getInventory().addDropIfFull(uncommon, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(uncommon));
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(uncommon).getName() + " @bla@<shad=0>from the Event!");
        } else {
            player.getInventory().addDropIfFull(COMMON, 1500);
            player.getCollectionLogManager().addItem(CollectionLogs.SOULBANE, new Item(COMMON));
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(COMMON).getName() + " @bla@<shad=0>from the Event!");
        }
    }

    public static int getRandomItem(int[] array) {
        return array[Misc.getRandom(array.length - 1)];
    }

    public static final Item[] itemRewards = {
            new Item(10258, 1),
            new Item(20066, 1),
            new Item(23173, 1),
            new Item(10258, 1),
            new Item(17130, 1),
            new Item(15667, 1),
            new Item(15670, 1),
            new Item(20008, 1),
            new Item(20009, 1),
            new Item(20061, 1),
            new Item(20062, 1),
            new Item(20063, 1),
            new Item(20064, 1),
            new Item(15713, 1),
            new Item(15710, 1),
            new Item(15715, 1),

    };
}
