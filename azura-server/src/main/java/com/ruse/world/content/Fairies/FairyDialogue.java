package com.ruse.world.content.Fairies;

import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueExpression;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.entity.impl.player.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FairyDialogue extends Dialogue {
    final Player p;
    @Override
    public int npcId() {
        return 4442;
    }

    @Override
    public DialogueType type() {
        return DialogueType.NPC_STATEMENT;
    }

    @Override
    public DialogueExpression animation() {
        return DialogueExpression.PLAIN_TALKING;
    }

    @Override
    public String[] dialogue() {
        return new String[] {"Welcome!", "I am the Companion Very Wise!", "Are you looking for a fairy as a companion?"};
    }

    @Override
    public Dialogue nextDialogue() {
        return new Dialogue() {
            @Override
            public int npcId() {
                return 4442;
            }

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
                return new String[] {"Yes Please.", "No Thanks."};
            }

            @Override
            public void specialAction() {
                p.setDialogueActionId(919);
            }
        };
    }
}
