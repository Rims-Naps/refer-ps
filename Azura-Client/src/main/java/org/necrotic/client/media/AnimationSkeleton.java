package org.necrotic.client.media;

import org.necrotic.client.Client;
import org.necrotic.client.net.Stream;

import java.util.HashMap;

public final class AnimationSkeleton {

	private static final int[] instructions = new int[500];
	private static final int[] alter_x = new int[500];
	private static final int[] alter_y = new int[500];
	private static final int[] alter_z = new int[500];

	public static void load(int folder, byte[] data) {
		try {
			Stream buffer = new Stream(data);
			AnimationSkin animationSkin = new AnimationSkin(buffer);
			int k1 = buffer.getUnsignedShort();
			for (int l1 = 0; l1 < k1; l1++) {
				int file = buffer.getUnsignedShort();
				AnimationSkeleton frame = new AnimationSkeleton();
				frame.animationSkin = animationSkin;
				int j2 = buffer.getUnsignedByte();
				int stepcount = 0;
				int k2 = -1;
				for (int i3 = 0; i3 < j2; i3++) {
					int setting = buffer.getUnsignedByte();
					if (setting > 0) {
						if (animationSkin.opcodes[i3] != 0) {
							for (int var4 = i3 - 1; var4 > k2; var4--) {
								if (animationSkin.opcodes[var4] != 0) {
									continue;
								}
								instructions[stepcount] = var4;
								alter_x[stepcount] = 0;
								alter_y[stepcount] = 0;
								alter_z[stepcount] = 0;
								stepcount++;
								break;
							}
						}
						instructions[stepcount] = i3;
						short c = 0;
						if (animationSkin.opcodes[i3] == 3) {
							c = (short) 128;
						}
						if ((setting & 1) != 0) {
							alter_x[stepcount] = (short) buffer.getShort2();
						} else {
							alter_x[stepcount] = c;
						}
						if ((setting & 2) != 0) {
							alter_y[stepcount] = buffer.getShort2();
						} else {
							alter_y[stepcount] = c;
						}
						if ((setting & 4) != 0) {
							alter_z[stepcount] = buffer.getShort2();
						} else {
							alter_z[stepcount] = c;
						}
						k2 = i3;
						stepcount++;
					}
				}
				frame.stepCount = stepcount;
				frame.opCodeTable = new int[stepcount];
				frame.translater_x = new int[stepcount];
				frame.translater_y = new int[stepcount];
				frame.translater_z = new int[stepcount];
				for (int k3 = 0; k3 < stepcount; k3++) {
					frame.opCodeTable[k3] = instructions[k3];
					frame.translater_x[k3] = alter_x[k3];
					frame.translater_y[k3] = alter_y[k3];
					frame.translater_z[k3] = alter_z[k3];
				}
				mapRS2.put((folder << 16) + file, frame);
			}
		} catch (Exception exception) {
			//exception.printStackTrace();
		}
	}

	public static void nullify() {
		mapRS2.clear();
		mapRS2 = null;
	}

	public static AnimationSkeleton forId(int i) {
		try {
			final AnimationSkeleton frames = mapRS2.get(i);
			if (frames != null) {
				return frames;
			}
			Client.instance.onDemandFetcher.requestFileData(1, i >> 16);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isNullFrame(int frame) {
		return frame == -1;
	}

	private AnimationSkeleton() {
	}

	public static HashMap<Integer, AnimationSkeleton> mapRS2 = new HashMap<>();

	public int displayLength;
	public AnimationSkin animationSkin;
	public int stepCount;
	public int[] opCodeTable;
	public int[] translater_x;
	public int[] translater_y;
	public int[] translater_z;
}
