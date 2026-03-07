package org.necrotic.client.media.renderable;

import org.necrotic.client.media.VertexNormal;
import org.necrotic.client.collection.NodeSub;

public class Renderable extends NodeSub {

	public void render(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int uid, int newuid, int bufferOffset) {
		Model model = getRotatedModel();
		if (model != null) {
			modelHeight = model.modelHeight;
			model.render(i, j, k, l, i1, j1, k1, l1, uid, newuid, bufferOffset);
		}
	}

	public Model getRotatedModel() {
		return null;
	}

	public Renderable() {
		modelHeight = 1000;
	}

	public VertexNormal[] aVertexNormalArray1425;
	public int modelHeight;
}
