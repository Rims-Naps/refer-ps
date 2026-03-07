package com.ruse.donation;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.content.tradingpost.PlayerShopRepo;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class Membership {
    @Getter@Setter
    public LocalDateTime endDate = LocalDateTime.now().minusDays(1);
    public enum Tiers {
        NONE(),
        BASIC("Tier 1", 2701, 2, 2, 100, 1.05, 1.02, false, false),
        MIDDLE("Tier 2",2702, 3, 3, 85, 1.10, 1.04, false, false),
        HIGH("Tier 3",2703, 4, 4, 75, 1.15, 1.06, true, true),
        END("Tier 4",2704, 7, 7, 55, 1.20, 1.10, true, true),
        MASTER("Royal", 2006, 10, 10, 30, 1.65, 1.3, true, true);

        @Getter
        private String name;
        @Getter
        private int itemId;
        @Getter
        private double crit_chance;
        @Getter
        private double droprate;
        @Getter
        private double save_key_chance;
        @Getter
        private double experience_increase;
        @Getter
        private double extra_points_tickets;
        @Getter
        private boolean infinitePrayer;
        @Getter
        private boolean auto_loot_salvaging;

        /**
         * Constructors
         */
        Tiers() {
            name = "None";
            itemId = -1;
            crit_chance = 0;
            droprate = 0;
            save_key_chance = 0;
            experience_increase = 0;
            extra_points_tickets = 0;
            infinitePrayer = false;
            auto_loot_salvaging = false;
        }

        Tiers(String name, int itemId, double crit, double dr, double save_key, double exp, double extra, boolean infPrayer, boolean autoSalvage) {
            this.name = name;
            this.itemId = itemId;
            this.crit_chance = crit;
            this.droprate = dr;
            this.save_key_chance = save_key;
            this.experience_increase = exp;
            this.extra_points_tickets = extra;
            this.infinitePrayer = infPrayer;
            this.auto_loot_salvaging = autoSalvage;
        }

        private static ImmutableSet<Tiers> MEMBERS = Sets.immutableEnumSet(BASIC, MIDDLE, HIGH, END, MASTER);
        private static ImmutableSet<Tiers> HIGH_TIER_MEMBERS = Sets.immutableEnumSet(HIGH, END, MASTER);


        /**
         * Public accessors
         * @return
         */

        public boolean isMember() {
            return MEMBERS.contains(this);
        }

        public boolean isHighTierMember() {
            return HIGH_TIER_MEMBERS.contains(this);
        }
    }

    public void process(Player plr) {
        if (LocalDateTime.now().isAfter(endDate) && plr.getMembershipTier() != Tiers.NONE) {
            plr.sendMessage("Your " + plr.getMembershipTier().getName() + " tier has ended. Please purchase another to continue your membership");
            plr.setMemberShipTier(Tiers.NONE);
        }
    }

    public boolean usableScroll(int itemId) {
        for (Tiers tier : Tiers.values()) {
            if (tier.getItemId() == itemId)
                return true;
        }
        return false;
    }

    public void appendScroll(Player plr, int itemId) {
        for (Tiers tier : Tiers.values()) {
            if (tier.getItemId() == itemId) {
                if (!plr.getInventory().contains(itemId))
                    return;
                if (plr.getMembershipTier().ordinal() > tier.ordinal()) {
                    plr.sendMessage("You can only upgrade your membership scroll");
                    return;
                }
                if (itemId == 2006) {
                    plr.getInventory().delete(itemId, 1, true);
                    endDate = LocalDateTime.now().plusDays(90);
                    plr.setMemberShipTier(tier);
                    plr.sendMessage("You have just claimed 90 days of " + tier.getName() + " membership!");
                    plr.getPacketSender().sendChatboxInterfaceRemoval();
                } else {
                    plr.getInventory().delete(itemId, 1, true);
                    endDate = LocalDateTime.now().plusDays(30);
                    plr.setMemberShipTier(tier);
                    plr.sendMessage("You have just claimed 30 days of " + tier.getName() + " membership!");
                    plr.getPacketSender().sendChatboxInterfaceRemoval();
                }
            }
        }
    }

    public void redeemMembership(Player plr, int itemId) {
        if (plr.getMembershipTier().isMember() && LocalDateTime.now().isAfter(endDate.minusDays(1))) {
            plr.sendMessage("You are already a member and cannot use another scroll");
            return;
        }
        new SelectionDialogue(plr,"Use Membership Scroll?",
            new SelectionDialogue.Selection("Yes", 0, p -> {
                appendScroll(plr, itemId);
                p.getPacketSender().sendChatboxInterfaceRemoval();
            }),
            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
        ).start();
    }
}
