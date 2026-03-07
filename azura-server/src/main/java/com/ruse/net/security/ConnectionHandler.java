package com.ruse.net.security;

import com.ruse.GameSettings;
import com.ruse.engine.GameEngine;
import com.ruse.net.login.LoginDetailsMessage;
import com.ruse.net.login.LoginResponses;
import com.ruse.util.Misc;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.entity.impl.player.Player;

import java.io.*;
import java.util.*;

/**
 * A lot of connection-related stuff. Really messy.
 */

public class ConnectionHandler {

	public static void init() {
		loadWhitelist();
		loadHostBlacklist();
		loadUUIDBans();
		loadMacBans();
		loadStarters();
	}

	public static int getResponse(Player player, LoginDetailsMessage msg) {

		String host = msg.getHost();

		if (PlayerPunishment.banned(player.getUsername())) {
			return LoginResponses.LOGIN_DISABLED_ACCOUNT;
		}

		if (isBlocked(host)) {
			return LoginResponses.LOGIN_REJECT_SESSION;
		}

		if (isMacBanned(msg.getMac()) || isUUIDBanned(msg.getSerialNumber())) {
			return LoginResponses.LOGIN_DISABLED_COMPUTER;
		}

	if (msg.getMac().equalsIgnoreCase("badmac" ) | msg.getSerialNumber().equalsIgnoreCase("badmac" )) {
		System.out.println("ATTEMPTED BAD MAC LOGIN CODE : 556677");
		return LoginResponses.NULLED_CONNECTION;
	}

		if (PlayerPunishment.IPBanned(host)) {
			return LoginResponses.LOGIN_DISABLED_IP;
		}

		if (!isLocal(host)) {
			if (CONNECTIONS.get(host) != null) {
				if (CONNECTIONS.get(host) >= GameSettings.CONNECTION_AMOUNT && !isWhitelistedName(host)) {
					System.out.println("Connection limit reached: " + player.getUsername() + ". Host: " + host);
					return LoginResponses.LOGIN_CONNECTION_LIMIT;
				}
			}
		}
		return LoginResponses.LOGIN_SUCCESSFUL;
	}

	private static final String WHITELIST_DIR = "./data/saves/whitelistedhosts.txt";
	private static List<String> WHITELISTED_HOSTS = new ArrayList<>();

	/** BLACKLISTED CONNECTIONS SUCH AS PROXIES **/
	private static final String BLACKLIST_DIR = "./data/saves/blockedhosts.txt";
	private static List<String> BLACKLISTED_HOSTNAMES = new ArrayList<>();

	private static final String BLACKLISTED_MACS_DIR = "./data/saves/BannedMacs.txt";
	private static List<String> BLACKLISTED_MACS = new ArrayList<>();

	private static final String BLACKLISTED_UUIDS_DIR = "./data/saves/BannedUUIDs.txt";
	private static List<String> BLACKLISTED_UUIDS = new ArrayList<>();

	/**
	 * The concurrent map of registered connections.
	 */
	private static final Map<String, Integer> CONNECTIONS = Collections.synchronizedMap(new HashMap<>());

	/** SAVED STARTERS **/
	private static final String STARTER_FILE = "./data/saves/starters.txt";

	/**
	 * The concurrent map of registered connections.
	 */
	private static final Map<String, Integer> STARTERS = Collections.synchronizedMap(new HashMap<>());

	private static void loadHostBlacklist() {
		String word;
		try {
			BufferedReader in = new BufferedReader(new FileReader(BLACKLIST_DIR));
			while ((word = in.readLine()) != null)
				BLACKLISTED_HOSTNAMES.add(word.toLowerCase());
			in.close();
		} catch (final Exception e) {
			System.out.println("Could not load blacklisted hosts.");
		}
	}

	private static void loadWhitelist() {
		String word;
		try {
			BufferedReader in = new BufferedReader(new FileReader(WHITELIST_DIR));
			while ((word = in.readLine()) != null)
				WHITELISTED_HOSTS.add(word.toLowerCase());
			in.close();
		} catch (final Exception e) {
			 System.out.println("Unable to load whitelisted hosts.");
		}
	}

	public static void addToWhitelist(String host) {
		if (WHITELISTED_HOSTS.contains(host))
			return;
		WHITELISTED_HOSTS.add(host);
		GameEngine.submit(() -> {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(WHITELIST_DIR, true));
				writer.write(host);
				writer.newLine();
				writer.close();
				System.out.println("Added connection address: " + host + " to the whitelist.");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed to add connection to whitelist.");
			}
		});
	}

	public static boolean isWhitelistedName(String host) {
		return WHITELISTED_HOSTS.contains(host);
	}

	public static boolean isBlocked(String host) {
		return BLACKLISTED_HOSTNAMES.contains(host.toLowerCase());
	}

	public static boolean isMacBanned(String mac) {
		return BLACKLISTED_MACS.contains(mac.toLowerCase());
	}

	public static boolean isUUIDBanned(String uuid) {
		return BLACKLISTED_UUIDS.contains(uuid.toLowerCase());
	}

	private static void loadMacBans() {
		String line;
		try {
			Misc.createFilesIfNotExist(BLACKLISTED_MACS_DIR, false);
			BufferedReader in = new BufferedReader(new FileReader(BLACKLISTED_MACS_DIR));
			while ((line = in.readLine()) != null) {
				if (line.contains("="))
					BLACKLISTED_MACS.add(line.substring(line.indexOf("=") + 1));
			}
			in.close();
			in = null;
		} catch (final Exception e) {
			e.printStackTrace();
			// System.out.println("Could not load blacklisted hadware numbers.");
		}
	}

	public static void banMac(String playername, String mac) {
		if (BLACKLISTED_MACS.contains(mac))
			return;
		BLACKLISTED_MACS.add(mac);
		GameEngine.submit(() -> {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(BLACKLISTED_MACS_DIR, true));
				writer.write(playername + "=" + mac);
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void reloadMACBans() {
		BLACKLISTED_MACS.clear();
		loadMacBans();
	}

	private static void loadUUIDBans() {
		String line;
		try {
			Misc.createFilesIfNotExist(BLACKLISTED_UUIDS_DIR, false);
			BufferedReader in = new BufferedReader(new FileReader(BLACKLISTED_UUIDS_DIR));
			while ((line = in.readLine()) != null) {
				if (line.contains("="))
					BLACKLISTED_UUIDS.add(line.substring(line.indexOf("=") + 1));
			}
			in.close();
		} catch (final Exception e) {
			e.printStackTrace();
			// System.out.println("Could not load blacklisted hadware numbers.");
		}
	}

	public static void banUUID(String playername, String uuid) {
		if (BLACKLISTED_UUIDS.contains(uuid))
			return;
		BLACKLISTED_UUIDS.add(uuid);
		GameEngine.submit(() -> {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(BLACKLISTED_UUIDS_DIR, true));
				writer.write(playername + "=" + uuid);
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void reloadUUIDBans() {
		BLACKLISTED_UUIDS.clear();
		loadUUIDBans();
	}

	public static void loadStarters() {
		try {
			Misc.createFilesIfNotExist(STARTER_FILE, false);
			BufferedReader r = new BufferedReader(new FileReader(STARTER_FILE));
			while (true) {
				String line = r.readLine();
				if (line == null) {
					break;
				} else {
					line = line.trim();
				}
				addStarter(line, false);
			}
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void add(String host) {
		if (!isLocal(host)) {
			if (CONNECTIONS.get(host) == null) {
				CONNECTIONS.put(host, 1);
			} else {
				int amt = CONNECTIONS.get(host) + 1;
				CONNECTIONS.put(host, amt);
			}
		}
	}

	public static void remove(String host) {
		if (!isLocal(host)) {
			if (CONNECTIONS.get(host) != null) {
				int amt = CONNECTIONS.get(host) - 1;
				if (amt == 0) {
					CONNECTIONS.remove(host);
				} else {
					CONNECTIONS.put(host, amt);
				}
			}
		}
	}

	public static int getStarters(String host) {
		if (host == null) {
			return GameSettings.MAX_STARTERS_PER_IP;
		}
		if (!ConnectionHandler.isLocal(host)) {
			if (STARTERS.get(host) != null) {
				return STARTERS.get(host);
			}
		}
		return 0;
	}

	public static void addStarter(String host, boolean write) {
		if (!ConnectionHandler.isLocal(host)) {
			if (STARTERS.get(host) == null) {
				STARTERS.put(host, 1);
			} else {
				int amt = STARTERS.get(host) + 1;
				STARTERS.put(host, amt);
			}
			if (write) {
				GameEngine.submit(() -> {
					try {
						BufferedWriter writer = new BufferedWriter(new FileWriter(STARTER_FILE, true));
						writer.write(host + "");
						writer.newLine();
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}

	/**
	 * Determines if the specified host is connecting locally.
	 *
	 * @param host the host to check if connecting locally.
	 * @return {@code true} if the host is connecting locally, {@code false}
	 *         otherwise.
	 */
	public static boolean isLocal(String host) {
		return false;
	}
}
