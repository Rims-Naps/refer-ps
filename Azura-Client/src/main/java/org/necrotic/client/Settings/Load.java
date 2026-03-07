package org.necrotic.client.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.necrotic.Configuration;
import org.necrotic.client.Client;
import org.necrotic.client.Signlink;
import org.necrotic.client.cache.media.rsinterface.dropdowns.impl.Keybinding;
import org.necrotic.client.constants.GameFrameConstants;
import org.necrotic.client.util.Encryption;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Brandon on 7/28/2017.
 */
public class Load {

	public static void settings(Client client) {
		Path path = Paths.get(Signlink.getSettingsDirectory(), "/persistable.json");
		File file = path.toFile();

		//if it doesn't exist no use on loading it, wait for saving method to create it.
		if (!file.exists()) {
			return;
		}

		try (FileReader fileReader = new FileReader(file)) {

			JsonParser fileparser = new JsonParser();
			Gson builder = new GsonBuilder().create();
			JsonObject reader = (JsonObject) JsonParser.parseReader(fileReader);

			if (reader.has("username")) {
				Client.instance.myUsername = reader.get("username").getAsString();
			}

			if (reader.has("password")) {
				Client.instance.password = reader.get("password").getAsString();
			}

			if (reader.has("render-distance")) {
				Configuration.RENDER_DISTANCE = reader.get("render-distance").getAsBoolean();
			}

            if (reader.has("animate-textures")) {
                Configuration.ANIMATE_TEXTURES = reader.get("animate-textures").getAsBoolean();
            }

            if (reader.has("render-self")) {
                Configuration.RENDER_SELF = reader.get("render-self").getAsBoolean();
            }

            if (reader.has("render-players")) {
                Configuration.RENDER_PLAYERS = reader.get("render-players").getAsBoolean();
            }

            if (reader.has("render-npcs")) {
                Configuration.RENDER_NPCS = reader.get("render-npcs").getAsBoolean();
            }

            if (reader.has("highlight-username")) {
                Configuration.HIGHLIGHT_USERNAME = reader.get("highlight-username").getAsBoolean();
            }

            if (reader.has("time-stamp")) {
                Configuration.TIME_STAMPS = reader.get("time-stamp").getAsBoolean();
            }

            if (reader.has("smiles-enabled")) {
                Configuration.SMILIES_ENABLED = reader.get("smiles-enabled").getAsBoolean();
            }

            if (reader.has("split-chat-color")) {
                client.splitChatColor = reader.get("split-chat-color").getAsInt();
            }
            if (reader.has("clan-chat-color")) {
                client.clanChatColor = reader.get("clan-chat-color").getAsInt();
            }
            if (reader.has("hitpoints-color")) {
                client.healthpoints_bar_color = reader.get("hitpoints-color").getAsInt();
            }
            if (reader.has("split-chat")) {
                client.variousSettings[502] = client.variousSettings[287] = reader.get("split-chat").getAsInt();
                Client.instance.updateConfig(287);
            }

			if (reader.has("above-head")) {
				Configuration.USERNAMES_ABOVE_HEAD = reader.get("above-head").getAsBoolean();
			}

            if (reader.has("item-outlines")) {
                Configuration.ITEM_OUTLINES = reader.get("item-outlines").getAsBoolean();
            }

            if (reader.has("hp-bars")) {
                Configuration.HP_BAR = reader.get("hp-bars").getAsBoolean();
            }

            if (reader.has("effect-timer")) {
                Configuration.DRAW_EFFECT_TIMERS = reader.get("effect-timer").getAsBoolean();
            }

            if (reader.has("bank-button")) {
                Configuration.activeBankButton = reader.get("bank-button").getAsInt();
            }

			if (reader.has("quick-curses")) {
				String qp = reader.get("quick-curses").getAsString();
				for (int i = 0; i < qp.length(); i++) {
					client.quickCurses[i] = Integer.parseInt(qp.substring(i, i + 1));
				}
			}
            if (reader.has("fkey")) {
                String qp[] = reader.get("fkey").getAsString().split(" ");
                for (int i = 0; i < qp.length; i++) {
                    Keybinding.KEYBINDINGS[i] = Integer.parseInt(qp[i]);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
