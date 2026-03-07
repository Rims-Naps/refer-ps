package com.ruse.world.content.dialogue;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruse.model.Animation;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.util.JsonLoader;
import com.ruse.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the loading and start of dialogues.
 * 
 * @author relex lawl
 */

public class DialogueManager {

	/**
	 * Contains all dialogues loaded from said file.
	 */
	public static Map<Integer, Dialogue> dialogues = new HashMap<>();

	/**
	 * Parses the information from the dialogue file.
	 */
	public static JsonLoader parseDialogues() {

		return new JsonLoader("./data/def/json/dialogues.json") {
			@Override
			public void load(JsonObject reader, Gson builder) {

				int id = reader.get("id").getAsInt();
				DialogueType type = DialogueType.valueOf(reader.get("type").getAsString());
				DialogueExpression anim = reader.has("anim")
						? DialogueExpression.valueOf(reader.get("anim").getAsString())
						: null;
				int lines = reader.get("lines").getAsInt();
				String[] dialogueLines = new String[lines];
				for (int i = 0; i < lines; i++) {
					dialogueLines[i] = reader.get("line" + (i + 1)).getAsString();
				}

				final int next = reader.get("next").getAsInt();
				final int npcId = reader.has("npcId") ? reader.get("npcId").getAsInt() : -1;
				
				// Handle item field - can be either an integer or an array [itemId, amount, name]
				int item = -1;
				int itemZoom = 250;
				String itemName = "";
				
				if (reader.has("item")) {
					if (reader.get("item").isJsonArray()) {
						// Handle array format: ["itemId", "amount", "name"]
						com.google.gson.JsonArray itemArray = reader.get("item").getAsJsonArray();
						if (itemArray.size() >= 1) {
							item = Integer.parseInt(itemArray.get(0).getAsString());
						}
						if (itemArray.size() >= 2) {
							itemZoom = Integer.parseInt(itemArray.get(1).getAsString());
						}
						if (itemArray.size() >= 3) {
							itemName = itemArray.get(2).getAsString();
						}
					} else {
						// Handle simple integer format
						item = reader.get("item").getAsInt();
						if (reader.has("itemZoom")) {
							itemZoom = reader.get("itemZoom").getAsInt();
						}
					}
				} else if (reader.has("itemZoom")) {
					itemZoom = reader.get("itemZoom").getAsInt();
				}
				
				final int finalItem = item;
				final int finalItemZoom = itemZoom;
				final String finalItemName = itemName;

				Dialogue dialogue = new Dialogue() {
					@Override
					public int id() {
						return id;
					}

					@Override
					public DialogueType type() {
						return type;
					}

					@Override
					public DialogueExpression animation() {
						return anim;
					}

					@Override
					public String[] dialogue() {
						return dialogueLines;
					}

					@Override
					public int nextDialogueId() {
						return next;
					}

					@Override
					public int npcId() {
						return npcId;
					}

					@Override
					public String[] item() {
						return new String[] {String.valueOf(finalItem), String.valueOf(finalItemZoom), finalItemName};
					}
				};
				dialogues.put(id, dialogue);

			}
		};

	}

	/**
	 * Starts a dialogue gotten from the dialogues map.
	 *
	 * @param player The player to dialogue with.
	 * @param id     The id of the dialogue to retrieve from dialogues map.
	 */
	public static void start(Player player, int id) {
		Dialogue dialogue = dialogues.get(id);
		start(player, dialogue);
	}

	/**
	 * Starts a dialogue.
	 *
	 * @param player   The player to dialogue with.
	 * @param dialogue The dialogue to show the player.
	 */
	public static void start(Player player, Dialogue dialogue) {
		player.setDialogue(dialogue);
		if (player.isBanking() || player.isShopping() || player.getInterfaceId() > 0 && player.getInterfaceId() != 50) {
			if (dialogue != null && dialogue.isClosesMainScreen()) {
				player.getPacketSender().sendInterfaceRemoval();
			}
		}
		if (dialogue == null || dialogue.id() < 0) {
			if (dialogue != null && dialogue.isClosesMainScreen()) {
				player.getPacketSender().sendInterfaceRemoval();
			} else {
				player.getPacketSender().sendChatboxInterfaceRemoval();
			}
		} else {
			showDialogue(player, dialogue);
			dialogue.specialAction();
		}
	}

	/**
	 * Handles the clicking of 'click here to continue', option1, option2 and so on.
	 *
	 * @param player The player who will continue the dialogue.
	 */
	public static void next(Player player) {
		if (player.getDialogue() == null) {
			player.getPacketSender().sendChatboxInterfaceRemoval();
			return;
		}
		switch (player.getDialogue().id()) {
			case 789:
				player.getPacketSender().sendInterfaceRemoval();
			//	player.getCompanion().receiveCompanion();
				player.giveItem(18681, 1);
				player.setDialogue(null);
				return;
		}
		Dialogue next = player.getDialogue().nextDialogue();
		if (next == null) {
			next = dialogues.get(player.getDialogue().nextDialogueId());
		}
		if (next == null || next.id() < 0) {
			player.getPacketSender().sendChatboxInterfaceRemoval();
			player.getDialogue().finish(player);
			return;
		}
		player.getDialogue().onContinue(player);
		start(player, next);
	}

	/**
	 * Configures the dialogue's type and shows the dialogue interface and sets its
	 * child id's.
	 *
	 * @param player   The player to show dialogue for.
	 * @param dialogue The dialogue to show.
	 */
	private static void showDialogue(Player player, Dialogue dialogue) {
		String[] lines = dialogue.dialogue();

		// The animation id
		int animId = -1;

		// If the type is an npc statement
		if (dialogue.type() == DialogueType.NPC_STATEMENT || dialogue.type() == DialogueType.NPC_STATEMENT_TIMED) {
			// The animation
			Animation anim = dialogue.animation().getAnimation();

			// The animation id cheat fix for osrs heads
			animId = anim.getId();
		}

		switch (dialogue.type()) {
			case NPC_STATEMENT:
				int startDialogueChildId = NPC_DIALOGUE_ID[lines.length - 1];
				int headChildId = startDialogueChildId - 2;
				player.getPacketSender().sendNpcHeadOnInterface(dialogue.npcId(), headChildId);
				player.getPacketSender().sendInterfaceAnimation(headChildId, dialogue.animation().getAnimation());
				player.getPacketSender().sendString(startDialogueChildId - 1,
						NpcDefinition.forId(dialogue.npcId()) != null
								? NpcDefinition.forId(dialogue.npcId()).getName().replaceAll("_", " ")
								: "");
				for (int i = 0; i < lines.length; i++) {
					player.getPacketSender().sendString(startDialogueChildId + i, lines[i]);
				}
				player.getPacketSender().sendChatboxInterface(startDialogueChildId - 3);
				break;
			case PLAYER_STATEMENT:
				startDialogueChildId = PLAYER_DIALOGUE_ID[lines.length - 1];
				headChildId = startDialogueChildId - 2;
				player.getPacketSender().sendPlayerHeadOnInterface(headChildId);
				player.getPacketSender().sendInterfaceAnimation(headChildId, dialogue.animation().getAnimation());
				player.getPacketSender().sendString(startDialogueChildId - 1, player.getUsername());
				for (int i = 0; i < lines.length; i++) {
					player.getPacketSender().sendString(startDialogueChildId + i, lines[i]);
				}
				player.getPacketSender().sendChatboxInterface(startDialogueChildId - 3);
				break;
			case ITEM_STATEMENT:
				startDialogueChildId = NPC_DIALOGUE_ID[lines.length - 1];
				headChildId = startDialogueChildId - 2;
				player.getPacketSender().sendInterfaceModel(headChildId, Integer.valueOf(dialogue.item()[0]),
						Integer.valueOf(dialogue.item()[1]));
				player.getPacketSender().sendString(startDialogueChildId - 1, dialogue.item()[2]);
				for (int i = 0; i < lines.length; i++) {
					player.getPacketSender().sendString(startDialogueChildId + i, lines[i]);
				}
				player.getPacketSender().sendChatboxInterface(startDialogueChildId - 3);
				break;
			case STATEMENT:
				sendStatement(player, dialogue.dialogue()[0]);
				break;
			case STATIC_STATEMENT:
				int length = lines.length;
				for (int index = 0; index < length; index++) {
					player.getPacketSender().sendString(STATEMENT_DIALOGUE_ID[length - 1] + index + 1, lines[index]);
				}

				player.getPacketSender().sendChatboxInterface(STATEMENT_DIALOGUE_ID[length - 1]);
				break;
			case DYNAMIC_STATEMENT:
				switch (lines.length) {
					case 1:
						player.getPacketSender().sendString(lines[0], 357);
						player.getPacketSender().sendChatboxInterface(356);
						break;
					case 2:
						player.getPacketSender().sendString(lines[0], 360);
						player.getPacketSender().sendString(lines[1], 361);
						player.getPacketSender().sendChatboxInterface(359);
						break;
					case 3:
						player.getPacketSender().sendString(lines[0], 364);
						player.getPacketSender().sendString(lines[1], 365);
						player.getPacketSender().sendString(lines[2], 366);
						player.getPacketSender().sendChatboxInterface(363);
						break;
					case 4:
						player.getPacketSender().sendString(lines[0], 369);
						player.getPacketSender().sendString(lines[1], 370);
						player.getPacketSender().sendString(lines[2], 371);
						player.getPacketSender().sendString(lines[3], 372);
						player.getPacketSender().sendChatboxInterface(368);
						break;
					case 5:
						player.getPacketSender().sendString(lines[0], 375);
						player.getPacketSender().sendString(lines[1], 376);
						player.getPacketSender().sendString(lines[2], 377);
						player.getPacketSender().sendString(lines[3], 378);
						player.getPacketSender().sendString(lines[4], 379);
						player.getPacketSender().sendChatboxInterface(374);
						break;
					default:
						throw new IllegalArgumentException("Invalid length: " + lines.length);
				}
				break;
			case OPTION:
				int firstChildId = OPTION_DIALOGUE_ID[lines.length - 1];
				player.getPacketSender().sendString(firstChildId - 1, "Choose an option");
				for (int i = 0; i < lines.length; i++) {
					player.getPacketSender().sendString(firstChildId + i, lines[i]);
				}
				player.getPacketSender().sendChatboxInterface(firstChildId - 2);
				break;
			case NPC_STATEMENT_TIMED:
				break;
			default:
				break;

		/*case OPTION:
			int interfaceId = OPTION_DIALOGUE_ID[lines.length - 1][0];
			int firstChildId = OPTION_DIALOGUE_ID[lines.length - 1][1];
			int secondChildId = OPTION_DIALOGUE_ID[lines.length - 1][2];
			boolean farSwords = dialogue.getTitle().length() > 12;

			if (farSwords) {
				player.getPacketSender().sendInterfaceDisplayState(firstChildId, true)
						.sendInterfaceDisplayState(secondChildId, false);
			} else {
				player.getPacketSender().sendInterfaceDisplayState(firstChildId, false)
						.sendInterfaceDisplayState(secondChildId, true);
			}

			player.getPacketSender().sendString(interfaceId - 1, dialogue.getTitle());
			for (int i = 0; i < lines.length; i++) {
				player.getPacketSender().sendString(interfaceId + i, lines[i]);
			}
			player.getPacketSender().sendChatboxInterface(firstChildId - 2);
			break;*/
		}
		if (player.getInterfaceId() <= 0)
			player.setInterfaceId(100);
	}
	private static final int[] OPTION_DIALOGUE_ID = { 13760, 2461, 2471, 2482, 2494, };

	public static void sendItemStatementDialogue(Player p, int item1, int zoom1, String... text) {
		sendItemStatementDialogue(p, item1, zoom1, 0, -1, text);
	}

	public static void sendItemStatementDialogue(Player p, int item1, int zoom1, int item2, int zoom2, String... text) {
		if (text.length > 1) {
			p.getPacketSender().sendString(6232, text[0]);
			p.getPacketSender().sendString(6233, text.length > 1 ? text[1] : "");
			p.getPacketSender().sendInterfaceModel(6235, zoom1 == -1 ? (item2 == -1 ? 200 : 170) : zoom1, item1);
			p.getPacketSender().sendInterfaceModel(6236, zoom2 == -1 ? 170 : zoom2, item2);
			p.getPacketSender().sendChatboxInterface(6231);
		} else {
			p.getPacketSender().sendString(308, text[0]);
			p.getPacketSender().sendInterfaceModel(307, zoom1 == -1 ? 200 : zoom1, item1);
			p.getPacketSender().sendChatboxInterface(306);
		}
	}

	public static void sendStatement(String statement, Player p) {
		p.getPacketSender().sendString(357, statement);
		p.getPacketSender().sendString(358, "Click here to continue");
		p.getPacketSender().sendChatboxInterface(356);
	}

	public static void sendStatement(Player p, String statement) {
		p.getPacketSender().sendString(357, statement);
		p.getPacketSender().sendString(358, "Click here to continue");
		p.getPacketSender().sendChatboxInterface(356);
	}

	public static void sendStatement(Player p, boolean closesMainScreen, String... statement) {
		p.setDialogue(null);
		start(p, statement(closesMainScreen, statement));
	}

	public static void sendStatement(Player p, String[] statement, Dialogue nextDialogue) {
		p.setDialogue(null);
		start(p, new Dialogue() {

			@Override
			public DialogueType type() {
				return DialogueType.DYNAMIC_STATEMENT;
			}

			@Override
			public DialogueExpression animation() {
				return null;
			}

			@Override
			public String[] dialogue() {
				return statement;
			}

			@Override
			public Dialogue nextDialogue() {
				return nextDialogue;
			}
		});
	}

	public static void sendStatement(Player p, String[] statement, Runnable nextAction) {
		p.setDialogue(null);
		start(p, new Dialogue() {

			@Override
			public DialogueType type() {
				return DialogueType.DYNAMIC_STATEMENT;
			}

			@Override
			public DialogueExpression animation() {
				return null;
			}

			@Override
			public String[] dialogue() {
				return statement;
			}

			@Override
			public void specialAction() {
				nextAction.run();
			}
		});
	}

	public static Dialogue statement(boolean closesMain, String... statement) {
		Dialogue dialogue = new Dialogue() {

			@Override
			public DialogueType type() {
				return DialogueType.DYNAMIC_STATEMENT;
			}

			@Override
			public DialogueExpression animation() {
				return null;
			}

			@Override
			public String[] dialogue() {
				return statement;
			}

		};
		dialogue.setClosesMainScreen(closesMain);
		return dialogue;
	}

	/**
	 * Gets an empty id for a dialogue.
	 *
	 * @return An empty index from the map or the map's size itself.
	 */
	public static int getDefaultId() {
		int id = dialogues.size();
		for (int i = 0; i < dialogues.size(); i++) {
			if (dialogues.get(i) == null) {
				id = i;
				break;
			}
		}
		return id;
	}

	/**
	 * Retrieves the dialogues map.
	 *
	 * @return dialogues.
	 */
	public static Map<Integer, Dialogue> getDialogues() {
		return dialogues;
	}

	/**
	 * This array contains the child id where the dialogue statement starts for npc
	 * and item dialogues.
	 */
	private static final int[] NPC_DIALOGUE_ID = { 4885, 4890, 4896, 4903 };

	/**
	 * This array contains the child id where the dialogue statement starts for
	 * player dialogues.
	 */
	private static final int[] PLAYER_DIALOGUE_ID = { 971, 976, 982, 989 };

	/**
	 * This array contains the child id where the dialogue statement starts for
	 * option dialogues.
	 */
	//private static final int[][] OPTION_DIALOGUE_ID = {{13760, 13764, 13767}, {2461, 2465, 2468},
	//	{2471, 2476, 2479}, {2482, 2488, 2489}, {2494, 2501, 2502},};

	/**
	 * This array contains the child id's for dialogue statement.
	 */
	protected static final int[] STATEMENT_DIALOGUE_ID = {12788, 12790, 12793, 12797, 12802};

	public static Dialogue npc(int npc, String... text) {
		return npc(npc, null, text);
	}

	public static Dialogue npc(int npc, Dialogue next, String... text) {
		return new Dialogue() {

			@Override
			public DialogueType type() {
				return DialogueType.NPC_STATEMENT;
			}

			@Override
			public DialogueExpression animation() {
				return DialogueExpression.CALM;
			}

			@Override
			public int npcId() {
				return npc;
			}

			@Override
			public String[] dialogue() {
				return text;
			}

			@Override
			public Dialogue nextDialogue() {
				return next;
			}
		};
	}

	public static void sendNpc(Player player, int npc, Dialogue next, String... text) {
		DialogueManager.start(player, npc(npc, next, text));
	}

	public static void sendNpcAction(Player player, int npc, Runnable next, String... text) {
		DialogueManager.start(player, new Dialogue() {

			@Override
			public DialogueType type() {
				return DialogueType.NPC_STATEMENT;
			}

			@Override
			public DialogueExpression animation() {
				return DialogueExpression.CALM;
			}

			@Override
			public int npcId() {
				return npc;
			}

			@Override
			public String[] dialogue() {
				return text;
			}

			@Override
			public void finish(Player player) {
				next.run();
			}
		});
	}

	private static final int[] components = {4907, 4899, 4892, 4886};

	public static void hideClickToContinue(Player player, boolean hide) {
		for (int component : components) {
			player.getPacketSender().sendInterfaceComponentMoval(hide ? -512 : 0, hide ? -512 : 0, component);
		}
	}
}
