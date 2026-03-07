package com.ruse.model.input.impl;

import com.ruse.model.input.Input;
import com.ruse.world.content.DropsInterface;
import com.ruse.world.entity.impl.player.Player;

public class EnterSyntaxToSearchItemDrops extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (syntax == null || syntax.trim().isEmpty() || syntax.length() > 30) {
            player.getPacketSender().sendMessage("Invalid input. Please try again.");
            return;
        }

        DropsInterface.handleItemSearchInput(player, syntax.trim());
    }
}
