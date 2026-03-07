package com.ruse.world.content.AoE;

import com.ruse.model.Item;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

public class AoEToken {
    private Player player;
    @Getter@Setter
    private int aoe_size;

    private final static int TOKEN_ID = 2007;

    public AoEToken(Player player) {
        this.player = player;
    }

    public void increment_aoe() {
        if (aoe_size < 3)
            aoe_size++;
    }

    public void useToken() {
        if (player.getInventory().contains(TOKEN_ID)) {
            if (getAoe_size() == 3) {
                player.sendMessage("You have reached the maximum amount of imbues you can have.");
                return;
            }
            new SelectionDialogue(player,"Use AoE Token?",
                new SelectionDialogue.Selection("Yes", 0, p -> {
                    player.sendMessage("You imbue yourself with the power of the AoE Token");
                    player.getInventory().delete(new Item(TOKEN_ID, 1));
                    increment_aoe();
                    p.getPacketSender().sendChatboxInterfaceRemoval();
                }),
                //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
            ).start();
        }
    }
}
