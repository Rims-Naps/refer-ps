package com.ruse.util;

import com.ruse.GameSettings;
import com.ruse.world.World;
import com.ruse.world.content.groupironman.GroupManager;

public class playerSavingTimer {

	public static long massSaveTimer = System.currentTimeMillis();

	public static void massSaving() {
		if (System.currentTimeMillis() - massSaveTimer > 15 * 60000) {
			World.savePlayers();
			GroupManager.saveGroups();
			// System.out.println("Players saved...");
			massSaveTimer = System.currentTimeMillis();
		}
	}

}
