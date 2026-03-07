package org.necrotic.client.cache.definition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.necrotic.client.collection.MRUNodes;
import org.necrotic.client.Signlink;
import org.necrotic.client.cache.Archive;
import org.necrotic.client.net.Stream;
import org.necrotic.client.media.renderable.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
@JsonAutoDetect(
		fieldVisibility = JsonAutoDetect.Visibility.NONE,
		setterVisibility = JsonAutoDetect.Visibility.NONE,
		getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE,
		creatorVisibility = JsonAutoDetect.Visibility.NONE
)
public final class AnimatedGraphic {

	public static MRUNodes modelCache = new MRUNodes(30);
	public static AnimatedGraphic[] cache;
	public static void load(Archive archive) {
		Stream stream = new Stream(archive.getDataForName("spotanim.dat"));
		int length = stream.getUnsignedShort();
		if (cache == null)
			cache = new AnimatedGraphic[length + CUSTOM_GFX_COUNT];

		FIRST_CUSTOM_GFX_ID = LAST_CUSTOM_GFX_ID = length;
		for (int j = 0; j < length; j++) {
			if (cache[j] == null) {
				cache[j] = new AnimatedGraphic();
			}
			cache[j].id = j;
			cache[j].readValues(stream);
			switch (j) {
			
				case 2959:
					cache[2959].modelId = cache[2114].modelId;
					cache[2959].animationId = cache[2114].animationId;
					cache[2959].animation = cache[2114].animation;
					cache[2959].originalColours = new int[]{127, 49874, -1, -1, -1, -1};
					cache[2959].destColours = new int[]{0, 100, -1, -1, -1, -1};
					//cache[2959].modelId = cache[2114].modelId;
					break;
			}
		}
		custom();
		c();

		try {
			loadCustomGFXes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static void dumpGraphics() {
        File f = new File("graphics.txt");
        //System.out.println("Dumping Item names..");
        // String[] variableNames = new String[] { "name", };
        try {
            f.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i < cache.length; i++) {
                AnimatedGraphic gfx = cache[i];
                if (gfx != null) {
                    bf.write("Graphic ID: " + i);
                    bf.newLine();
                    bf.write("Graphic Model ID: " + gfx.modelId);
                    bf.newLine();
                    bf.write("Graphic Animation ID: " + gfx.animationId);
                    bf.newLine();
                    bf.newLine();
                }
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Dumping Complete!");
    }

	public void removeRecolors() {
		originalColours = new int[6];
		destColours = new int[6];
	}

	public void brighten(double multiplier) {
		contrast *= multiplier;
	}

	public void darken(double multiplier) {
		ambient *= ambient;
	}

	public void rotate(int angleAmount) {
		rotation += angleAmount;
	}

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	private static final CollectionType TYPE = TypeFactory.defaultInstance()
			.constructCollectionType(HashSet.class, AnimatedGraphic.class);

	//private final static Type TYPE = new TypeToken<HashSet<AnimatedGraphic>>() {}.getType();
	private final static Path PATH = Paths.get(Signlink.getCacheDirectory(), "custom_gfx.json");

	private static void loadCustomGFXes() throws IOException {
		File file = PATH.toFile();
		if(!file.exists()) {
			file.createNewFile();
		}
		HashSet<AnimatedGraphic> customGFXs = OBJECT_MAPPER.readValue(file, TYPE);
		for (AnimatedGraphic custom : customGFXs) {
			if (custom != null) {
				custom.cacheAnimation();
				custom.cache();
				if (custom.id > LAST_CUSTOM_GFX_ID)
					LAST_CUSTOM_GFX_ID = custom.id;
			}
		}
		//System.out.println("[AnimatedGraphic]: loaded " + (customGFXs.size()) + " customs.");
	}

	public static void saveCustomGFXes() throws IOException {

		HashSet<AnimatedGraphic> customGFXs = new HashSet<>();
		File file = PATH.toFile();

		if (file.createNewFile()) {
			System.out.println("[AnimatedGraphic] Created a new file for custom gfxes at '" + PATH.toString() + "'");
		}

		for (int i = FIRST_CUSTOM_GFX_ID; i <= LAST_CUSTOM_GFX_ID; i++) {
			AnimatedGraphic custom = cache[i];
			if (custom != null) {
				customGFXs.add(custom);
			}
		}
		OBJECT_MAPPER.writeValue(file, customGFXs);
	}

	public void setMultiplierWidth(double multiplierWidth) {
		this.multiplierWidth = multiplierWidth;
	}

	public void setMultiplierHeight(double multiplierHeight) {
		this.multiplierHeight = multiplierHeight;
	}

	public void recolor(int targetColor, int newColor) {
		int firstFreeSlot = originalColours.length;

		for (int i = 0; i < originalColours.length; i++) {
			if (originalColours[i] == 0) {
				firstFreeSlot = i;
			}
			if (originalColours[i] == targetColor) {
				destColours[i] = newColor;
				return;
			}
		}
		if (firstFreeSlot == originalColours.length) {
			int newLength = firstFreeSlot + 1;
			int targetColors[] = new int[newLength];
			int newColors[] = new int[newLength];
			System.arraycopy(originalColours, 0, targetColors, 0, firstFreeSlot);
			System.arraycopy(destColours, 0, newColors, 0, firstFreeSlot);
			originalColours = targetColors;
			destColours = newColors;
		}
		originalColours[firstFreeSlot] = targetColor;
		destColours[firstFreeSlot] = newColor;

		//System.out.println("Original and dest: " + Arrays.toString(originalColours) + " and " + Arrays.toString(destColours));
	}

	private static void custom() {
		cache[2274].modelId = cache[2281].modelId;
		cache[2274].animationId = cache[2281].animationId;
		cache[2274].rotation = 90;
		cache[2274].animation = cache[2281].animation;
	}
	
	private static void c() {
		int gfxID = 2964;
		cache[gfxID] = new AnimatedGraphic();
		cache[gfxID].contrast = cache[2213].contrast;
		cache[gfxID].ambient = cache[2213].ambient;
		cache[gfxID].modelId =  cache[2213].modelId;
		cache[gfxID].animation = cache[2213].animation;
		cache[gfxID].originalColours = cache[2213].originalColours;
		cache[gfxID].destColours = cache[2213].destColours;
		int size = 300;
		cache[gfxID].resizeX = size;
		cache[gfxID].resizeY = size;
	}

	public void setAnimation(int animationId) {
		this.animationId = animationId;
		this.animation = AnimationDefinition.cache[animationId];
	}

	public void cacheAnimation() {
		if (animationId >= 0) {
			animation = AnimationDefinition.cache[animationId];
		}
	}

	public void cache() {
		cache[getId()] = this;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static AnimatedGraphic createNewGFX() {
		AnimatedGraphic newGfx = new AnimatedGraphic(++LAST_CUSTOM_GFX_ID);
		return newGfx;
	}

	public static AnimatedGraphic createNewGFXFrom(int toCopy) {
		AnimatedGraphic newGfx = cache[toCopy].copy(++LAST_CUSTOM_GFX_ID);
		return newGfx;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public AnimatedGraphic copy(int newId) {
		AnimatedGraphic copied = new AnimatedGraphic(newId);
		copied.setModelId(modelId);
		copied.animationId = animationId;
		copied.animation = animation;
		copied.originalColours = new int[originalColours.length];
		copied.destColours = new int[destColours.length];
		System.arraycopy(originalColours, 0, copied.originalColours, 0, originalColours.length);
		System.arraycopy(destColours, 0, copied.destColours, 0, destColours.length);
		copied.setResizeX(resizeX);
		copied.setResizeY(resizeY);
		copied.rotation = rotation;
		copied.ambient = ambient;
		copied.contrast = contrast;
		return copied;
	}

	private AnimatedGraphic(int id) {
		this.setId(id);
		animationId = -1;
		originalColours = new int[6];
		destColours = new int[6];
		setResizeX(128);
		setResizeY(128);
	}

	public int getResizeX() {
		return (int) (resizeX * multiplierWidth);
	}

	public void setResizeX(int resizeX) {
		this.resizeX = resizeX;
	}

	public int getResizeY() {
		return (int) (resizeY * multiplierHeight);
	}

	public void setResizeY(int resizeY) {
		this.resizeY = resizeY;
	}

	public AnimationDefinition animation;
	@JsonProperty
	private int id;
	@JsonProperty
	public int modelId;
	@JsonProperty
	private int animationId;
	@JsonProperty
	public int resizeX;
	@JsonProperty
	public int resizeY;
	@JsonProperty
	public int rotation;
	@JsonProperty
	public int ambient;
	@JsonProperty
	public int contrast;
	@JsonProperty
	public int[] originalColours;
	@JsonProperty
	public int[] destColours;

	@JsonProperty
	public double multiplierWidth = 1.0D;
	@JsonProperty
	public double multiplierHeight = 1.0D;

	private static final int CUSTOM_GFX_COUNT = 100;
	private static int FIRST_CUSTOM_GFX_ID;
	private static int LAST_CUSTOM_GFX_ID;

	private AnimatedGraphic() {
		animationId = -1;
		originalColours = new int[6];
		destColours = new int[6];
		resizeX = 128;
		resizeY = 128;
	}

	public Model getModel() {
		Model model = (Model) modelCache.insertFromCache(id);

		if (model != null) {
			return model;
		}

		model = Model.fetchModel(modelId);

		if (model == null) {
			return null;
		}

		int length = Math.max(originalColours.length, destColours.length);
		for (int i = 0; i < length; i++) {
			if (i < originalColours.length && i < destColours.length) {
			//	System.out.println("Recoloring " + originalColours[i] + " to " + destColours[i] + " for " + Arrays.toString(model.colors));
				model.recolour(originalColours[i], destColours[i]);
			}
		}

		//System.out.println(Arrays.toString(model.colors));

		modelCache.removeFromCache(model, id);
		return model;
	}

	private void readValues(Stream stream) {
		do {
			int i = stream.getUnsignedByte();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				modelId = stream.getUnsignedShort();
			} else if (i == 2) {
				animationId = stream.getUnsignedShort();
				if (AnimationDefinition.cache != null) {
					animation = AnimationDefinition.cache[animationId];
				}
			} else if (i == 4) {
				resizeX = stream.getUnsignedShort();
			} else if (i == 5) {
				resizeY = stream.getUnsignedShort();
			} else if (i == 6) {
				rotation = stream.getUnsignedShort();
			} else if (i == 7) {
				ambient = stream.getUnsignedByte();
			} else if (i == 8) {
				contrast = stream.getUnsignedByte();
			} else if (i == 40) {
				int j = stream.getUnsignedByte();
				for (int k = 0; k < j; k++) {
					originalColours[k] = stream.getUnsignedShort();
					destColours[k] = stream.getUnsignedShort();
				}
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		} while (true);
	}
}
