package com.ruse.world.content.Fairies;

import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueExpression;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.entity.impl.player.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Panel extends Dialogue {
    final Player p;
    @Override
    public DialogueType type() {
        return DialogueType.OPTION;
    }

    @Override
    public DialogueExpression animation() {
        return null;
    }

    @Override
    public String[] dialogue() {
        return new String[] {"Spawn", "Check Exp.", "Check Level", "Check name"};
    }

    @Override
    public void specialAction() {
        p.setDialogueActionId(917);
    }
}
