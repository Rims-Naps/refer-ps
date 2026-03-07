package com.ruse.model.container.new_container;

/**
 * Listens to events from a container.
 * @author Graham Edgecombe
 *
 */
public interface NewContainerListener {
	
	/**
	 * Called when an item_combine is changed.
	 * @param container 
	 * 			The container.
	 * @param slot 
	 * 			The slot that was changed.
	 */
	void itemChanged(NewItemContainer container, int slot);
	
	/**
	 * Called when a group of items are changed.
	 * @param container The container.
	 * @param slots The slots that were changed.
	 */
	void itemsChanged(NewItemContainer container, int[] slots);
	
	/**
	 * Called when all the items change.
	 * @param container 
	 * 			The container.
	 */
	void itemsChanged(NewItemContainer container);

}