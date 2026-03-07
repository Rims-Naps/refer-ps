package org.necrotic.client.media;

import org.necrotic.client.net.Stream;

public final class AnimationSkin {

	public AnimationSkin(Stream stream) {
		int amt = stream.getUnsignedShort();
		opcodes = new int[amt];
		skinList = new int[amt][];
		for (int j = 0; j < amt; j++) {
			opcodes[j] = stream.getUnsignedShort();
		}

		for (int j = 0; j < amt; j++) {
			skinList[j] = new int[stream.getUnsignedShort()];
		}

		for (int j = 0; j < amt; j++) {
			for (int l = 0; l < skinList[j].length; l++) {
				skinList[j][l] = stream.getUnsignedShort();
			}
		}
	}

	public final int[] opcodes;
	public final int[][] skinList;
}
