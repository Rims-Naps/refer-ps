package com.ruse.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A utility class that provides functions for parsing <code>.json</code> files.
 * 
 * @author lare96
 */
public abstract class JsonLoader {

	/**
	 * The path to the .json file being parsed
	 */
	private final String path;

	/**
	 * Allows the user to read and/or modify the parsed data.
	 * 
	 * @param reader  the reader instance.
	 * @param builder the builder instance.
	 */
	public abstract void load(JsonObject reader, Gson builder) throws IOException;

	public JsonLoader(String path) {
		this.path = path;
	}

	/**
	 * Loads the parsed data. How the data is loaded is defined by
	 * <code>load(JsonObject j, Gson g)</code>.
	 *
	 * @return the loader instance, for chaining.
	 * @throws Exception if any exception occur while loading the parsed data.
	 */
	public JsonLoader load(boolean multiple) {
		if (multiple) {
			try (Stream<Path> walk = Files.walk(Paths.get(path))) {

				List<String> result = walk.map(Path::toString)
						.filter(f -> f.endsWith(".json")).collect(Collectors.toList());

				for (String s : result) {
					loadJson(s);
				}

			} catch (IOException e) {
				System.out.println(path);
				e.printStackTrace();
			}
		} else {
			try {
				loadJson(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this;
	}

	public void loadJson(String file) throws IOException {
		Path path = Paths.get(file);
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		try (JsonReader jsonReader = new JsonReader(new FileReader(path.toFile()))) {
			jsonReader.setLenient(true);
			Gson builder = new GsonBuilder().create();
			JsonArray array = builder.fromJson(jsonReader, JsonArray.class);
			for (int i = 0; i < array.size(); i++) {
				JsonObject reader = (JsonObject) array.get(i);
				load(reader, builder);
			}
		} catch (Exception e) {
			System.out.println(file);
			e.printStackTrace();
		}
	}
}
