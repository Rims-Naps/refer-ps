package com.ruse.world.content.Fairies;

import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueExpression;
import com.ruse.world.content.dialogue.DialogueType;

public class FairyDialogueTwo extends Dialogue {
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
        return new String[]{"Great!", "you'll be able to level your fairy by killing", "any npc in game for a rare chance to gain exp"};
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
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.PLAIN_TALKING;
            }

            @Override
            public String[] dialogue() {
                return new String[] {"Your fairy can have up to 3 different attachments", "1) Damage attachment", "2) Droprate attachment"
                , "3) Exp attachment (2x fairy exp)"};
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
                        return DialogueType.NPC_STATEMENT;

                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.PLAIN_TALKING;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[] {"You'll be able to view your fairy using the fairy wand.", "Good luck!"};
                    }
                    @Override
                    public int id() {
                        return 789;
                    }
                };
            }
        };
    }
}
