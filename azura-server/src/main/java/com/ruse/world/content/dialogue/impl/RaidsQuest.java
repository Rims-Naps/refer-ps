/*
package com.ruse.world.content.dialogue.impl;

import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueExpression;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.entity.impl.player.Player;

public class RaidsQuest {

    public static Dialogue get(Player p, int stage) {
        Dialogue dialogue = null;
        switch (stage) {
            case 0:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{
                                "Hello There Travler",
                                "Would you like to help me,",
                                "Defeat this Narnia Raids?",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 0;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, 1);
                    }
                };
                break;
            case 1:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{
                                "You can access all the unique content",
                                "Vanguard has to offer by using the ",
                                "magic book teleports, or by accessing our",
                                "teleport interface."};
                    }

                    @Override
                    public int npcId() {
                        return 0;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, 2);
                    }
                };
                break;
            case 2:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{

                                "To get started killing monsters you",
                                "can access the Starter Zone by using ",
                                "the portal next to the Bank!",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 0;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, 3);
                    }
                };
                break;
            case 3:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.PLAYER_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{ "Great, thank you!" };
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
        }

        return dialogue;
    }

}*/
