package com.ruse.world.content.dialogue.impl;

import com.ruse.model.Position;
import com.ruse.model.RegionInstance;
import com.ruse.world.World;
import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueExpression;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

/**
 * Represents a Dungeoneering party invitation dialogue
 *
 * @author Gabriel Hannason
 */

public class Xmasquest {


    public static int XMAS_BOSS_ID = 5188;

    public static void spawnXmasBoss(Player player){
        player.moveTo(new Position(3032, 3994, player.getIndex() * 4));
        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.XMASBOSS));

        if (player.getRegionInstance().getType() == RegionInstance.RegionInstanceType.XMASBOSS){
            NPC xmasboss = new NPC(XMAS_BOSS_ID, new Position(3034, 4002, player.getIndex() * 4)).setSpawnedFor(player);
            World.register(xmasboss);
            player.getRegionInstance().getNpcsList().add(xmasboss);
        }


    }

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
                                "",
                                "You're not an imposter are you?",
                                "",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 1716;
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
                        return DialogueType.PLAYER_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{
                                "",
                                "Huh? What are you talking about",
                                "",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 1716;
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

                                "",
                                "Krampus sent all these phony Santas here to kill my vibe.",
                                " and his Evil Yeti stole all the Christmas presents...",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 1716;
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
                        return new String[]{ "Is there anything I can do to help?" };
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, 4);
                    }
                };
                break;
            case 4:
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

                                "",
                                "If you can collect enough christmas spirit",
                                "You might be able to defeat the Evil Yeti",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 1716;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, 5);
                    }
                };
                break;

            case 5:
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
                        return new String[]{

                                "",
                                "Alright, I'll see what I can do",
                                "",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 1716;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, -1);
                    }
                };
                break;

            case 6:
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

                                "",
                                "What is this? It's like you're not even trying...",
                                "Come back when you have 20 Christmas Shards",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 1716;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, -1);
                    }
                };
                break;

            case 7:
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

                                "",
                                "Wow you managed to defeat him?",
                                "Enjoy that Scythe, you can keep it.",
                                ""};
                    }

                    @Override
                    public int npcId() {
                        return 1716;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, -1);
                    }
                };
                break;
        }

        return dialogue;
    }

}
