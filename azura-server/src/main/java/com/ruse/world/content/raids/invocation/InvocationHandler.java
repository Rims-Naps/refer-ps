package com.ruse.world.content.raids.invocation;

import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class InvocationHandler {
    private Player player;

    /**
     * Super to ensure we don't need redundent player calls
     * @param player
     */
    public InvocationHandler(Player player) {
        this.player = player;
    }

    /**
     * Tower Invocations var. Change this when adding invocations(Please don't remove)
     */
    private final int tower_invocations_count = 6;

    /**
     * Unlock tracker for Tower of Ascension
     */
    @Getter@Setter
    private boolean[] tower_invocations = new boolean[tower_invocations_count]; //Change 5 to whatever amount of invocations there are for tower.

    /**
     * Selected invocation tracker for Tower of Ascension
     */
    @Getter@Setter
    private boolean[] tower_invocations_s = new boolean[tower_invocations_count]; //Change 5 to whatever amount of invocations there are for tower.

    private void setTowerInvocation(int slot, boolean value) {
        this.tower_invocations[slot] = value;
    }

    private void setTowerInvocationS(int slot, boolean value) {
        this.tower_invocations_s[slot] = value;
    }

    /**
     * Unlock specific invocation for Tower of Ascension
     */
    public void unlock_invocation(int itemId) {
        if (!player.getInventory().contains(itemId))
            return;
        for (Invocations.TowerInvocations invocation : Invocations.TowerInvocations.values()) {
            if (invocation.getItemId() == itemId) {
                if (is_invocation_unlocked(invocation)) {
                    player.sendMessage("You have already unlocked this invocation");
                    return;
                }

                setTowerInvocation(invocation.ordinal(), true);
                player.sendMessage("You have unlocked the " + invocation.getName() + " invocation!");
                player.getInventory().delete(itemId, 1);
            }
        }
    }

    /**
     * Check if the specified itemId is an item that unlocks a invocation
     */
    public boolean is_correct_item(int itemId) {
        for (Invocations.TowerInvocations invocation : Invocations.TowerInvocations.values()) {
            if (invocation.getItemId() == itemId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a player has unlocked the specific invocation
     */
    public boolean is_invocation_unlocked(Invocations.TowerInvocations invocation) {
        return getTower_invocations()[invocation.ordinal()];
    }

    /**
     * Checks if a player's specific invocation is in use
     */
    public boolean is_invocation_in_use(Invocations.TowerInvocations invocation) {
        return getTower_invocations_s()[invocation.ordinal()];
    }

    /**
     * Code for Selecting the Invocation from the interface
     */
    public void set_invocation_for_raid(int buttonId) {
        Invocations.TowerInvocations invocation = getInvocationForButton(buttonId);
        if (invocation == null)
            return;
        if (is_invocation_unlocked(invocation)) {
            int maxInvocations = 1;
            if (player.getTotal_tower_completions() >= 500) {
                maxInvocations = 2;
            }
            if (player.getTotal_tower_completions() >= 1000) {
                maxInvocations = 3;
            }
            if (invocations_bound() >= maxInvocations) {
                player.sendMessage("You reset your bound invocations as your surpassed your limit.");
                reset_bound_invocations();
            }
            setTowerInvocationS(invocation.ordinal(), true);
            player.sendMessage("You have selected " + invocation.getName() + " to be used");
            player.getPacketSender().sendSpriteChange(invocation.getInterfaceId(), 5761);
            sendNumbers();
        } else {
            player.sendMessage("You need to unlock " + invocation.getName() + " to do this.");
        }
    }

    /**
     * Gets the current count of invocations in use
     * @return
     */
    public int invocations_bound() {
        int boundInvocations = 0;
        for (boolean towerInvocations : tower_invocations_s) {
            if (towerInvocations)
                boundInvocations++;
        }
        return boundInvocations;
    }


    /**
     * Checks if this is a valid button mapped to the interface
     * @param buttonId
     * @return
     */
    public boolean isButton(int buttonId) {
        for (Invocations.TowerInvocations tower : Invocations.TowerInvocations.values()) {
            if (buttonId == tower.getInterfaceId())
                return true;
        }
        return false;
    }

    /**
     * Gets the associated Invocation to a buttonId
     * @param buttonId
     * @return
     */
    public Invocations.TowerInvocations getInvocationForButton(int buttonId) {
        for (Invocations.TowerInvocations tower : Invocations.TowerInvocations.values()) {
            if (buttonId == tower.getInterfaceId())
                return tower;
        }
        return null;
    }

    /**
     * Reset bound invocations when maxed set is used.
     */
    public void reset_bound_invocations() {
        for (Invocations.TowerInvocations tower : Invocations.TowerInvocations.values()) {
            player.getPacketSender().sendSpriteChange(tower.getInterfaceId(), 5762);
        }
        tower_invocations_s = new boolean[tower_invocations_count];
    }

    public void sendNumbers() {
        int maxInvocations = 1;
        if (player.getTotal_tower_completions() >= 500) {
            maxInvocations = 2;
        }
        if (player.getTotal_tower_completions() >= 1000) {
            maxInvocations = 3;
        }
        player.getPacketSender().sendString(88012, invocations_bound() + "/" + maxInvocations);
    }

    public double grabTotalModifier() {
        double modifier = 1.0;
        for (Invocations.TowerInvocations tower : Invocations.TowerInvocations.values()) {
            if (is_invocation_in_use(tower))
                modifier += tower.getModifier();
        }
        return modifier;
    }

    public void redeem_scroll(int itemId) {
        new SelectionDialogue(player,"Use Scroll?",
            new SelectionDialogue.Selection("Yes", 0, p -> {
                unlock_invocation(itemId);
                p.getPacketSender().sendChatboxInterfaceRemoval();
            }),
            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
        ).start();
    }
}
