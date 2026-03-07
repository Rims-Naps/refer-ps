package com.ruse.model.input.impl;

import com.ruse.model.input.Input;
import com.ruse.world.entity.impl.player.Player;

public class RenamePreset extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (syntax.length() > 10) {
            player.sendMessage("Your preset name must not be longer than 10 characters!");
            return;
        }
        player.getPresetManager().renamePreset(syntax);

    }
}
