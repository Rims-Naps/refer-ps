package org.necrotic.client.cache.media;

import org.necrotic.client.cache.Archive;
import org.necrotic.client.net.Stream;
import org.necrotic.client.media.renderable.Model;

public final class IdentityKit {

	private static int length;
	public static IdentityKit[] cache;

	public static void unpackConfig(Archive streamLoader) {
		Stream stream = new Stream(streamLoader.getDataForName("idk.dat"));
        int length = stream.getUnsignedShort();
        //System.out.println("Total identityKits:  " + length);
		setLength(length);

		if (cache == null) {
			cache = new IdentityKit[getLength()];
		}

		for (int j = 0; j < getLength(); j++) {
			if (cache[j] == null) {
				cache[j] = new IdentityKit();
			}

			cache[j].readValues(stream);
		}
	}

	private int bodyPartId;
	private int[] modelIds;
	private final int[] anIntArray659;
	private final int[] anIntArray660;
	private final int[] models = {-1, -1, -1, -1, -1};
	private boolean nonSelectable;

	public IdentityKit() {
		setBodyPartId(-1);
		anIntArray659 = new int[6];
		anIntArray660 = new int[6];
		setNonSelectable(false);
	}

	public boolean method537() {
		if (modelIds == null) {
			return true;
		}

		boolean flag = true;

		for (int i : modelIds) {
			if (!Model.isModelFetched(i)) {
				flag = false;
			}
		}

		return flag;
	}

	public Model method538() {
		if (modelIds == null) {
			return null;
		}

		Model[] aclass30_sub2_sub4_sub6s = new Model[modelIds.length];

		for (int i = 0; i < modelIds.length; i++) {
			aclass30_sub2_sub4_sub6s[i] = Model.fetchModel(modelIds[i]);
		}

		Model model;

		if (aclass30_sub2_sub4_sub6s.length == 1) {
			model = aclass30_sub2_sub4_sub6s[0];
		} else {
			model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
		}

		for (int j = 0; j < 6; j++) {
			if (anIntArray659[j] == 0) {
				break;
			}
			model.method476(anIntArray659[j], anIntArray660[j]);
		}

		return model;
	}

	public boolean method539() {
		boolean flag1 = true;

		for (int i = 0; i < 5; i++) {
			if (models[i] != -1 && !Model.isModelFetched(models[i])) {
				flag1 = false;
			}
		}

		return flag1;
	}

	public Model method540() {
		Model[] aclass30_sub2_sub4_sub6s = new Model[5];
		int j = 0;

		for (int k = 0; k < 5; k++) {
			if (models[k] != -1) {
				aclass30_sub2_sub4_sub6s[j++] = Model.fetchModel(models[k]);
			}
		}

		Model model = new Model(j, aclass30_sub2_sub4_sub6s);

		for (int l = 0; l < 6; l++) {
			if (anIntArray659[l] == 0) {
				break;
			}
			model.method476(anIntArray659[l], anIntArray660[l]);
		}

		return model;
	}

	private void readValues(Stream buffer) {
		do {
			int opcode = buffer.getUnsignedByte();

			if (opcode == 0) {
				return;
			}
			if (opcode == 1) {
				setBodyPartId(buffer.getUnsignedByte());
			} else if (opcode == 2) {
				int length = buffer.getUnsignedByte();
				modelIds = new int[length];
				for (int k = 0; k < length; k++) {
					modelIds[k] = buffer.getUnsignedShort();
				}

			} else if (opcode == 3) {
				setNonSelectable(true);
			} else if (opcode >= 40 && opcode < 50) {
				anIntArray659[opcode - 40] = buffer.getUnsignedShort();
			} else if (opcode >= 50 && opcode < 60) {
				anIntArray660[opcode - 50] = buffer.getUnsignedShort();
			} else if (opcode >= 60 && opcode < 70) {
				models[opcode - 60] = buffer.getUnsignedShort();
			} else {
				System.out.println("Error unrecognised config code: " + opcode);
			}
		} while (true);
	}

	public boolean isNonSelectable() {
		return nonSelectable;
	}

	public void setNonSelectable(boolean nonSelectable) {
		this.nonSelectable = nonSelectable;
	}

	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		IdentityKit.length = length;
	}

	public int getBodyPartId() {
		return bodyPartId;
	}

	public void setBodyPartId(int bodyPartId) {
		this.bodyPartId = bodyPartId;
	}

}
