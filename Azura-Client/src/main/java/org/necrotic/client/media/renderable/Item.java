package org.necrotic.client.media.renderable;

import org.necrotic.client.cache.definition.ItemDef;

public final class Item extends Renderable {

    public int amount;
	public int id;

	@Override
	public final Model getRotatedModel() {
		ItemDef definition = ItemDef.get(id);
		return definition.getInventoryModel(amount);
	}

}
