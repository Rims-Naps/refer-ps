package com.ruse.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ruse.model.Item;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Suic
 * @since 29.06.2019
 */

public final class StringUtils {

	public static String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	public static String capitalizeEachFirst(String str) {

		String[] words = str.split(" ");

		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
		}
		return str = String.join(" ", words);
	}

	public static String usToSpace(String str) {
		return str.replaceAll("_", " ");
	}

	public static String percentToSymbol(String str) {
		return str.toLowerCase().replaceAll("percent", "%");
	}

	/**
	 * Capitalized all words split by a space char.
	 *
	 * @param name
	 *            The string to format.
	 */
	public static String capitalizeWords(String name) {
		StringBuilder builder = new StringBuilder(name.length());
		String[] words = name.split("\\s");
		for (int i = 0, l = words.length; i < l; ++i) {
			if (i > 0)
				builder.append(" ");
			if (words[i].length() > 0) {
				builder.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1));
			}
		}
		return builder.toString();
	}

	/**
	 * Capitalizes the first letter in said string.
	 *
	 * @param name
	 *            The string to capitalize.
	 * @return The string with the first char capitalized.
	 */
	public static String capitalize(String name) {
		if (name.length() < 1)
			return "";
		StringBuilder builder = new StringBuilder(name.length());
		char first = Character.toUpperCase(name.charAt(0));
		builder.append(first).append(name.toLowerCase().substring(1));
		return builder.toString();
	}

	/**
	 * Pluralizes a given word by appending an "s" at the end if the word is not already plural.
	 *
	 * @param word       The word to pluralize.
	 * @param capitalize If the "s" should be capitalized or not.
	 * @return The word with an "s" appended to the end if needed. If the word is blank, a blank string is returned.
	 */
	public static String pluralize(String word, boolean capitalize) {
		if (word.length() < 1) {
			return "";
		}
		return word.toLowerCase().charAt(word.length() - 1) == 's' ? word : (word + (capitalize ? 'S' : 's'));
	}

	/**
	 * Formats the name by checking if it starts with a vowel.
	 *
	 * @param name
	 *            The string to format.
	 */
	public static String getVowelFormat(String name) {
		char letter = name.charAt(0);
		boolean vowel = letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u';
		String other = vowel ? "an" : "a";
		return other + " " + name;
	}

	/**
	 * Checks if a name is valid according to the
	 * {@code VALID_PLAYER_CHARACTERS} array.
	 *
	 * @param name
	 *            The name to check.
	 * @return The name is valid.
	 */
	public static boolean isValidName(String name) {
		return formatNameForProtocol(name).matches("[a-z0-9_]+");
	}

	/**
	 * Converts a name to a long value.
	 *
	 * @param string
	 *            The string to convert to long.
	 * @return The long value of the string.
	 */
	public static long stringToLong(String string) {
		long l = 0L;
		for (int i = 0; i < string.length() && i < 12; i++) {
			char c = string.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z')
				l += (1 + c) - 65;
			else if (c >= 'a' && c <= 'z')
				l += (1 + c) - 97;
			else if (c >= '0' && c <= '9')
				l += (27 + c) - 48;
		}
		while (l % 37L == 0L && l != 0L)
			l /= 37L;
		return l;
	}

	/**
	 * Converts a long to a string.
	 *
	 * @param l
	 *            The long value to convert to a string.
	 * @return The string value.
	 */
	public static String longToString(long l) {
		int i = 0;
		char ac[] = new char[12];
		while (l != 0L) {
			long l1 = l;
			l /= 37L;
			ac[11 - i++] = VALID_CHARACTERS[(int) (l1 - l * 37L)];
		}
		return new String(ac, 12 - i, i);
	}

	/**
	 * Formats a name for use in the protocol.
	 *
	 * @param name
	 *            The name to format.
	 * @return The formatted name.
	 */
	public static String formatNameForProtocol(String name) {
		return name.toLowerCase().replace(" ", "_");
	}

	/**
	 * Formats a name for in-game display.
	 *
	 * @param name
	 *            The name to format.
	 * @return The formatted name.
	 */
	public static String formatName(String name) {
		return fixName(name.toLowerCase().replace(" ", "_"));
	}

	/**
	 * Formats a player's name, i.e sets upper case letters after a space.
	 *
	 * @param name
	 *            The name to format.
	 * @return The formatted name.
	 */
	private static String fixName(String name) {
		if (name.length() > 0) {
			final char ac[] = name.toCharArray();
			for (int j = 0; j < ac.length; j++)
				if (ac[j] == '_') {
					ac[j] = ' ';
					if ((j + 1 < ac.length) && (ac[j + 1] >= 'a') && (ac[j + 1] <= 'z')) {
						ac[j + 1] = (char) ((ac[j + 1] + 65) - 97);
					}
				}

			if ((ac[0] >= 'a') && (ac[0] <= 'z')) {
				ac[0] = (char) ((ac[0] + 65) - 97);
			}
			return new String(ac);
		} else {
			return name;
		}
	}

	public static boolean contains(String[] source, String... strings) {
		Search:
		for (String searchString : strings) {
			for (String sourceString : source) {
				if (sourceString.equalsIgnoreCase(searchString)) {
					continue Search;
				}
			}
			return false;
		}
		return true;
	}
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static <T> T fromJson(Path path, Class<T> clazz) {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			return (T) gson.fromJson(reader, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);

	public static String formatNumber(long num) {
		return numberFormatter.format(num);
	}

	public static String decodeMessage(byte[] message) {
		char[] decodedMessage = new char[4096];
		ByteArrayInputStream in = new ByteArrayInputStream(message);
		int j = 0;
		int k = -1;
		while (in.available() > 0) {
			int i1 = in.read();
			int j1 = i1 >> 4 & 0xf;
			if (k == -1) {
				if (j1 < 13)
					decodedMessage[j++] = VALID_CHAT_CHACATERS[j1];
				else
					k = j1;
			} else {
				decodedMessage[j++] = VALID_CHAT_CHACATERS[((k << 4) + j1) - 195];
				k = -1;
			}
			j1 = i1 & 0xf;
			if (k == -1) {
				if (j1 < 13)
					decodedMessage[j++] = VALID_CHAT_CHACATERS[j1];
				else
					k = j1;
			} else {
				decodedMessage[j++] = VALID_CHAT_CHACATERS[((k << 4) + j1) - 195];
				k = -1;
			}
		}

		boolean flag1 = true;
		for (int k1 = 0; k1 < j; k1++) {
			char c = decodedMessage[k1];
			if (flag1 && c >= 'a' && c <= 'z') {
				decodedMessage[k1] += '\uFFE0';
				flag1 = false;
			}
			if (c == '.' || c == '!' || c == '?')
				flag1 = true;
		}
		return new String(decodedMessage, 0, j);
	}

	public static byte[] encodeMessage(String message) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		if (message.length() > 80) {
			message = message.substring(0, 80);
		}
		message = message.toLowerCase();

		int i = -1;
		for (int j = 0; j < message.length(); j++) {
			char c = message.charAt(j);
			int k = 0;
			for (int l = 0; l < VALID_CHAT_CHACATERS.length; l++) {
				if (c != VALID_CHAT_CHACATERS[l]) {
					continue;
				}
				k = l;
				break;
			}
			if (k > 12) {
				k += 195;
			}
			if (i == -1) {
				if (k < 13) {

					i = k;
				} else {
					out.write(k);

				}
			} else if (k < 13) {
				out.write((i << 4) + k);
				i = -1;
			} else {
				out.write((i << 4) + (k >> 4));
				i = k & 0xf;
			}
		}

		if (i != -1) {
			out.write(i << 4);
		}

		return out.toByteArray();
	}

	public static String concatItemNames(List<Item> items) {
		return concatItemNames(items.toArray(new Item[0]));
	}

	public static String concatItemNames(Item... items) {
		return concatItemNames("and", items);
	}

	public static String concatItemNames(String conjunction, List<Item> items) {
		return concatItemNames(conjunction, items.toArray(new Item[0]));
	}

	public static String concatItemNames(String conjunction, Item... items) {
		return concatItemNames(conjunction, true, items);
	}

	public static String concatItemNames(String conjunction, boolean showAmounts, List<Item> items) {
		return concatItemNames(conjunction, showAmounts, items.toArray(new Item[0]));
	}

	public static String concatItemNames(String conjunction, boolean showAmounts, Item... items) {
		StringBuilder itemsMessage = new StringBuilder();
		for (int i = 0; i < items.length; i++) {
			int amount = items[i].getAmount();
			String name = items[i].getDefinition().getName();
			String pluralName = StringUtils.pluralize(name, false);
			String and = i != 0 ? i == items.length - 1 ? items.length > 2 ? ", " + conjunction + " " : " " + conjunction + " " : items.length > 2 ? ", " : " " : "";
			itemsMessage.append(and).append(showAmounts ? amount + " " : "").append(showAmounts ? amount == 1 ? name : pluralName : pluralName);
		}
		return itemsMessage.toString();
	}

	/**
	 * Splits a given string into multiple strings based on the line limit.
	 * @param message The message to split.
	 * @param lineLimit The maximum limit for a line to have. It is best to calculate this by using the "W" character
	 *                     sequentially (as W is the widest character in the alphabet) and "c" character (as c is the
	 *                     smallest character in the alphabet
	 * @return The split string
	 */
	public static String[] splitStringIntelligently(String message, int lineLimit) {
		List<String> messageSplit = new ArrayList<>();
		StringBuilder currentLine = new StringBuilder();
		int chars = 0;
		for (String word : message.split(" ")) {
			word = word.replace("<br>", "");
			int length = word.length() + 2;
			if (word.startsWith("<col=")) {
				length -= 12; // cuz its always <col=######>
			}
			if (word.contains("</col>")) {
				length -= 6;
			}
			if (word.startsWith("<img=")) { // take into account crowns, which are typically of size WW
				length = 4;
			}
			if (word.contains("\n")) {
				chars = lineLimit * (messageSplit.size() + 1);
				messageSplit.add(currentLine + " " + word.replace("\n", ""));
				currentLine = new StringBuilder();
			} else {
				if (length + chars >= lineLimit * (messageSplit.size() + 1)) {
					messageSplit.add(currentLine.toString());
					currentLine = new StringBuilder(word);
				} else {
					currentLine.append(chars == 0 ? "" : " ").append(word);
				}
				chars += length;
			}
		}
		if (currentLine.length() > 0) {
			messageSplit.add(currentLine.toString());
		}
		return messageSplit.toArray(new String[0]);
	}

	public static String putCommasInNumber(long number) {
		String string = "" + number;
		return putCommasInNumber(string);
	}

	public static String putCommasInNumber(String string) {
		for (int i = string.length() - 3; i > 0; i -= 3) {
			string = string.substring(0, i) + "," + string.substring(i);
		}

		return string;
	}

	public static final char[] VALID_CHAT_CHACATERS = {' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm',
			'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=',
			'\243', '$', '%', '"', '[', ']'};

	/**
	 * An array containing valid player name characters.
	 */
	public static final char[] VALID_NAME_CHARACTERS = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '[', ']', '/', '-', ' ' };

	/**
	 * An array containing valid characters that may be used on the server.
	 */
	public static final char[] VALID_CHARACTERS = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=', ':', ';', '.', '>', '<',
			',', '"', '[', ']', '|', '?', '/', '`' };

}
