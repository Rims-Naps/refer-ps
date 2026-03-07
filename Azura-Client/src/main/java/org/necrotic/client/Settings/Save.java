package org.necrotic.client.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.necrotic.Configuration;
import org.necrotic.client.Client;
import org.necrotic.client.Signlink;
import org.necrotic.client.cache.media.rsinterface.dropdowns.impl.Keybinding;
import org.necrotic.client.util.Encryption;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Brandon on 7/28/2017.
 */
public class Save {

	public static void settings(Client client) {
//    File file = new File(Signlink.getSettingsDirectory() + "/settings.json");
		Path path = Paths.get(Signlink.getSettingsDirectory(), "/persistable.json");
		File file = path.toFile();
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		try (FileWriter writer = new FileWriter(file)) {
			Gson builder = new GsonBuilder().setPrettyPrinting().create();
			JsonObject object = new JsonObject();

			object.addProperty("username", Client.instance.myUsername == null || !Configuration.SAVE_ACCOUNTS ? "" : Client.instance.myUsername);
			object.addProperty("password", Client.instance.password == null || !Configuration.SAVE_ACCOUNTS ? "" : Client.instance.password);

			object.addProperty("render-distance", Configuration.RENDER_DISTANCE);
            object.addProperty("animate-textures", Configuration.ANIMATE_TEXTURES);
            object.addProperty("render-self", Configuration.RENDER_SELF);
            object.addProperty("render-players", Configuration.RENDER_PLAYERS);
            object.addProperty("render-npcs", Configuration.RENDER_NPCS);

            object.addProperty("highlight-username", Configuration.HIGHLIGHT_USERNAME);
            object.addProperty("time-stamp", Configuration.TIME_STAMPS);
            object.addProperty("smiles-enabled", Configuration.SMILIES_ENABLED);
            object.addProperty("split-chat-color", client.splitChatColor);
            object.addProperty("clan-chat-color", client.clanChatColor);
            object.addProperty("hitpoints-color", client.healthpoints_bar_color);
            object.addProperty("split-chat", client.variousSettings[502]);

            object.addProperty("above-head", Configuration.USERNAMES_ABOVE_HEAD);
            object.addProperty("item-outlines", Configuration.ITEM_OUTLINES);
            object.addProperty("hp-bars", Configuration.HP_BAR);
            object.addProperty("effect-timer", Configuration.DRAW_EFFECT_TIMERS);
            object.addProperty("bank-button", Configuration.activeBankButton);
			StringBuilder stringSave = new StringBuilder();
			for (int i = 0; i < client.quickCurses.length; i++) {
				stringSave.append(client.quickCurses[i]);
			}
			object.add("quick-curses", builder.toJsonTree(stringSave.toString()));
            stringSave = new StringBuilder();
            for (int i = 0; i < Keybinding.KEYBINDINGS.length; i++) {
                stringSave.append(Keybinding.KEYBINDINGS[i]).append(" ");
            }
            object.add("fkey", builder.toJsonTree(stringSave.toString()));
			writer.write(builder.toJson(object));
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}

