package com.ruse.model.container.new_container;

import com.google.common.collect.Lists;
import com.ruse.model.Item;

import java.util.*;

/**
 * A container holds a group of items.
 * @author Graham Edgecombe
 * @author Stephen Andrews
 * @author Michael Sasse
 */
public class NewItemContainer {
	
	/**
	 * The type of container.
	 * @author Graham Edgecombe
	 *
	 */
	public enum Type {
		
		/**
		 * A standard container such as inventory.
		 */
		STANDARD,
		
		/**
		 * A container which always stacks, e.g. the bank, regardless of the
		 * item_combine.
		 */
		ALWAYS_STACK,
		
	}
	
	/**
	 * The capacity of this container.
	 */
	private int capacity;
	
	/**
	 * The items in this container.
	 */
	protected Item[] items;
	
	/**
	 * A list of listeners.
	 */
	private List<NewContainerListener> listeners = new LinkedList<>();
	
	/**
	 * The container type.
	 */
	private Type type;
	
	/**
	 * Firing events flag.
	 */
	private boolean firingEvents = true;
	
	/**
	 * Creates the container with the specified capacity.
	 * @param type The type of this container.
	 * @param capacity The capacity of this container.
	 */
	public NewItemContainer(Type type, int capacity) {
		this.type = type;
		this.capacity = capacity;
		this.items = new Item[capacity];
	}
	
	/**
	 * Create a new container that always stacks containing the items array
	 * @param items
	 * 			the default items array
	 */
	public NewItemContainer(Item[] items) {
		this.type = Type.ALWAYS_STACK;
		this.capacity = items.length;
		this.items = items;
	}
	
	/**
	 * Sets the firing events flag.
	 * @param firingEvents The flag.
	 */
	public void setFiringEvents(boolean firingEvents) {
		this.firingEvents = firingEvents;
	}
	
	/**
	 * Checks the firing events flag.
	 * @return <code>true</code> if events are fired, <code>false</code> if
	 * not.
	 */
	public boolean isFiringEvents() {
		return firingEvents;
	}
	
	/**
	 * Gets the listeners of this container.
	 * @return The listeners of this container.
	 */
	public Collection<NewContainerListener> getListeners() {
		return Collections.unmodifiableCollection(listeners);
	}
	
	/**
	 * Adds a listener.
	 * @param listener The listener to add.
	 */
	public void addListener(NewContainerListener listener) {
		listeners.add(listener);
		listener.itemsChanged(this);
	}
	
	/**
	 * Removes a listener.
	 * @param listener The listener to remove.
	 */
	public void removeListener(NewContainerListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Removes all listeners.
	 */
	public void removeAllListeners() {
		listeners.clear();
	}
	
	/**
	 * Shifts all items to the top left of the container leaving no gaps.
	 */
	public void shift() {
		Item[] old = items;
		items = new Item[capacity];
		int newIndex = 0;
		for(int i = 0; i < items.length; i++) {
			if(old[i] != null) {
				items[newIndex] = old[i];
				newIndex++;
			}
		}
		if(firingEvents) {
			fireItemsChanged();
		}
	}
	
	/**
	 * Shifts all items to the top left of the container leaving no gaps.
	 * @param start
	 * 			the shift start index
	 * @param length
	 * 			the shift length
	 */
	public void shift(int start, int length) {
		Item[] old = items.clone();
		
		for (int i = start; i < start + length; i++)
			items[i] = null;
		
		int newIndex = start;
		for(int i = start; i < start + length; i++) {
			if(old[i] != null) {
				items[newIndex] = old[i];
				newIndex++;
			}
		}
		
		if(firingEvents) {
			fireItemsChanged();
		}
	}
	
	/**
	 * Gets the next free slot.
	 * @return The slot, or <code>-1</code> if there are no available slots.
	 */
	public int freeSlot() {
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Gets the next free slot.
	 * 
	 * @param start
	 * 			the index to start checking for a free slots
	 * @param length
	 * 			the amount of slots to check
	 * 
	 * @return The slot, or <code>-1</code> if there are no available slots.
	 */
	public int freeSlot(int start, int length) {
		for(int i = start; i < start + length; i++) {
			if(items[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Add multiple items to this container
	 * @param items
	 * 			the items to add
	 */
	public void add(Item...items) {
		for (Item i : items) {
			add(i);
		}
	}

	
	/**
	 * Add an item_combine
	 * @param id
	 * 			the item_combine id
	 * @param count
	 * 			the item_combine count
	 * @return
	 * 		true if the item_combine was added successfully
	 */
	public boolean add(int id, int count) {
		return add(new Item(id, count));
	}
	
	/**
	 * Add an item_combine
	 * @param id
	 * 			the item_combine id
	 * @param count
	 * 			the item_combine count
	 * @param start
	 * 			the item_combine index to start adding
	 * @param length
	 * 			the length of items to consider to add an item_combine, from <code>start</code> to <code>start + length</code>
	 * @return
	 * 		true if the item_combine was added successfully
	 */
	public boolean add(int id, int count, int start, int length) {
		return add(new Item(id, count), start, length);
	}

	/**
	 * Attempts to add an item_combine into the next free slot.
	 * @param item The item_combine.
	 * @return <code>true</code> if the item_combine was added,
	 * <code>false</code> if not.
	 */
	public boolean add(Item item) {
		return addReturnSlot(item) != -1;
	}

	/**
	 * Adds an item.
	 * @param item the item.
	 * @return the slot the item was added to
	 */
	public int addReturnSlot(Item item) {
		if (item.getAmount() <= 0)
			return -1;
		if(type.equals(Type.ALWAYS_STACK) || item.getDefinition().isStackable()) {
			for(int i = 0; i < items.length; i++) {
				if(items[i] != null && items[i].getId() == item.getId()) {
					long totalCount = (long) item.getAmount() + items[i].getAmount();
					if(totalCount >= Integer.MAX_VALUE) {
						return -1;
					}
					set(i, new Item(items[i].getId(), items[i].getAmount() + item.getAmount()));
					return i;
				}
			}
			int slot = freeSlot();
			if(slot == -1) {
				return -1;
			} else {
				set(slot, item);
				return slot;
			}
		} else {
			int slots = freeSlots();
			if(slots >= item.getAmount()) {
				boolean b = firingEvents;
				firingEvents = false;
				try {
					int theSlot = -1;
					for(int i = 0; i < item.getAmount(); i++) {
						theSlot = freeSlot();
						set(theSlot, new Item(item.getId()));
					}
					if(b) {
						fireItemsChanged();
					}
					return theSlot;
				} finally {
					firingEvents = b;
				}
			} else {
				return -1;
			}
		}
	}

	/**
	 * Attempts to add an item_combine into the next free slot.
	 * @param item The item_combine.
	 * @return <code>true</code> if the item_combine was added,
	 * <code>false</code> if not.
	 *
	 * @param startIndex
	 * 			the index to start checking
	 * @param length
	 * 			the length of slots we're going to try to add to
	 */
	public boolean add(Item item, int startIndex, int length) {
		return add(item, startIndex, length, false);
	}

	/**
	 * Attempts to add an item_combine into the next free slot.
	 * @param item The item_combine.
	 * @return <code>true</code> if the item_combine was added,
	 * <code>false</code> if not.
	 * 
	 * @param startIndex
	 * 			the index to start checking
	 * @param length
	 * 			the length of slots we're going to try to add to
	 * @param allowZero
	 * 			allow an item with zero amount to be added
	 */
	public boolean add(Item item, int startIndex, int length, boolean allowZero) {
		if (!allowZero && item.getAmount() <= 0)
			return false;
		if(type.equals(Type.ALWAYS_STACK) || item.getDefinition().isStackable()) {
			for(int i = startIndex; i < startIndex + length; i++) {
				if(items[i] != null && items[i].getId() == item.getId()) {
					long totalCount = (long) item.getAmount() + items[i].getAmount();
					if(totalCount >= Integer.MAX_VALUE) {
						return false;
					}
					set(i, new Item(items[i].getId(), items[i].getAmount() + item.getAmount()));
					return true;
				}
			}
			int slot = freeSlot(startIndex, length);
			if(slot == -1) {
				return false;
			} else {
				set(slot, item);
				return true;
			}
		} else {
			int slots = freeSlots();
			if(slots >= item.getAmount()) {
				boolean b = firingEvents;
				firingEvents = false;
				try {
					for(int i = 0; i < item.getAmount(); i++) {
						set(freeSlot(startIndex, length), new Item(item.getId()));
					}
					if(b) {
						fireItemsChanged();
					}
					return true;
				} finally {
					firingEvents = b;
				}
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Gets the number of free slots.
	 * @return The number of free slots.
	 */
	public int freeSlots() {
		return capacity - size();
	}

	/**
	 * @return
	 * 		true if the container contains no items
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Gets an item_combine.
	 * @param index The position in the container.
	 * @return The item_combine.
	 */
	public Item get(int index) {
		if (index < 0 || index >= items.length)
			return null;
		return items[index];
	}
	
	/**
	 * Gets an item_combine by id.
	 * @param id The id.
	 * @return The item_combine, or <code>null</code> if it could not be found.
	 */
	public Item getById(int id) {
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) {
				continue;
			}
			if(items[i].getId() == id) {
				return items[i];
			}
		}
		return null;
	}

	public int getSlotById(int id) {
		return getSlotById(id, true);
	}

	/**
	 * Gets a slot by id.
	 * @param id The id.
	 * @return The slot, or <code>-1</code> if it could not be found.
	 */
	public int getSlotById(int id, boolean include0) {
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) {
				continue;
			}
			if(items[i].getId() == id && (include0 || items[i].getAmount() > 0)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Sets an item_combine.
	 * @param index The position in the container.
	 * @param item The item_combine.
	 */
	public void set(int index, Item item) {
		items[index] = item;
		if(firingEvents) {
			fireItemChanged(index);
		}
	}
	
	/**
	 * Gets the capacity of this container.
	 * @return The capacity of this container.
	 */
	public int capacity() {
		return capacity;
	}
	
	/**
	 * Gets the size of this container.
	 * @return The size of this container.
	 */
	public int size() {
		int size = 0;
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null) {
				size++;
			}
		}
		return size;
	}
	
	/**
	 * Clears this container.
	 */
	public void clear() {
		items = new Item[items.length];
		if(firingEvents) {
			fireItemsChanged();
		}
	}

	/**
	 * Returns an array representing this container.
	 * @return The array.
	 */
	public List<Item> getNonNullItems() {
		ArrayList<Item> nonNulls = Lists.newArrayList();
		for (Item item : items) {
			if (item != null && item.getId() > -1) {
				nonNulls.add(item);
			}
		}
		return nonNulls;
	}

	/**
	 * Returns an array representing this container.
	 * @return The array.
	 */
	public Item[] toArray() {
		return items;
	}
	
	/**
	 * Checks if a slot is used.
	 * @param slot The slot.
	 * @return <code>true</code> if an item_combine is present, <code>false</code> otherwise.
	 */
	public boolean isSlotUsed(int slot) {
		return items[slot] != null;
	}
	
	/**
	 * Checks if a slot is free.
	 * @param slot The slot.
	 * @return <code>true</code> if an item_combine is not present, <code>false</code> otherwise.
	 */
	public boolean isSlotFree(int slot) {
		return items[slot] == null;
	}

	/**
	 * Removes an item_combine.
	 * @param item The item_combine to remove.
	 * @return The number of items removed.
	 */
	public int remove(Item item) {
		return remove(-1, item);
	}
	
	/**
	 * Removes an item_combine.
	 * @param id 
	 * 		the item_combine id
	 * @param count
	 * 			the item_combine count
	 * @return The number of items removed.
	 */
	public int remove(int id, int count) {
		return remove(new Item(id, count));
	}

	/**
	 * Removes an item_combine.
	 * @param preferredSlot The preferred slot.
	 * @param item The item_combine to remove.
	 * @return The number of items removed.
	 */
	public int remove(int preferredSlot, Item item) {
		return remove(preferredSlot, item, false);
	}

	/**
	 * Removes an item_combine.
	 * @param preferredSlot The preferred slot.
	 * @param item The item_combine to remove.
	 * @param allowZero Allow the item to reach zero without deletion.
	 * @return The number of items removed.
	 */
	public int remove(int preferredSlot, Item item, boolean allowZero) {
		if (!allowZero && item.getAmount() == 0)
			return 0;
		int removed = 0;
		if(item.getDefinition().isStackable() || type.equals(Type.ALWAYS_STACK)) {
			int slot = getSlotById(item.getId());
			Item stack = get(slot);
			if(stack.getAmount() > item.getAmount()) {
				removed = item.getAmount();
				set(slot, new Item(stack.getId(), stack.getAmount() - item.getAmount()));
			} else {
				removed = stack.getAmount();
				if (allowZero) {
					set(slot, new Item(stack.getId(), 0));
				} else {
					set(slot, null);
				}
			}
		} else {
			for(int i = 0; i < item.getAmount(); i++) {
				int slot = getSlotById(item.getId());
				if(i == 0 && preferredSlot != -1) {
					Item inSlot = get(preferredSlot);
					if(inSlot.getId() == item.getId()) {
						slot = preferredSlot;
					}
				}
				if(slot != -1) {
					removed++;
					if (allowZero) {
						set(slot, new Item(item.getId(), 0));
					} else {
						set(slot, null);
					}
				} else {
					break;
				}
			}
		}
		
		if (allowZero && item.getAmount() == 0 || removed > 0) {
			fireItemsChanged();
		}
		
		if (removed == 0 && !(allowZero && item.getAmount() == 0)) {
			//throw new IllegalArgumentException("No items were removed, exception thrown to avoid duplication glitch.");
			
		}
		
		return removed;
	}

	/**
	 * Transfers an item_combine from one container to another.
	 * @param from The container to transfer from.
	 * @param to The container to transfer to.
	 * @param fromSlot The slot in the original container.
	 * @param id The item_combine id.
	 * @return A flag indicating if the transfer was successful.
	 */
	public static boolean transfer(NewItemContainer from, NewItemContainer to, int fromSlot, int id) {
		Item fromItem = from.get(fromSlot);
		if(fromItem == null || fromItem.getId() != id) {
			return false;
		}
		if(to.add(fromItem)) {
			from.set(fromSlot, null);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Swaps two items.
	 * @param fromSlot From slot.
	 * @param toSlot To slot.
	 */
	public void swap(int fromSlot, int toSlot) {
		Item temp = get(fromSlot);
		boolean b = firingEvents;
		firingEvents = false;
		try {
			set(fromSlot, get(toSlot));
			set(toSlot, temp);
			if(b) {
				fireItemsChanged(new int[] { fromSlot, toSlot });
			}
		} finally {
			firingEvents = b;
		}
	}

	/**
	 * Gets the total amount of an item_combine, including the items in stacks.
	 * @param item The item.
	 * @return The amount.
	 */
	public int getCount(Item item) {
		return getCount(item.getId());
	}

	/**
	 * Gets the total amount of an item_combine, including the items in stacks.
	 * @param id The id.
	 * @return The amount.
	 */
	public int getCount(int id) {
		int total = 0;
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null) {
				if(items[i].getId() == id) {
					total += items[i].getAmount();
				}
			}
		}
		return total;
	}
	
	/**
	 * Get amount of gold in container
	 * @return
	 */
	public int getGold() {
		return getCount(995);
	}

	/**
	 * Inserts an item_combine.
	 * @param fromSlot The old slot.
	 * @param toSlot The new slot.
	 */
	public void insert(int fromSlot, int toSlot) {
		if (fromSlot == toSlot)
			return;
		if (get(fromSlot) == null/* || get(toSlot) == null*/)
			return;
		Item item = get(fromSlot);
		items[fromSlot] = null;
		
		if (fromSlot < toSlot) {
			for (int slot = fromSlot + 1; slot <= toSlot; slot++) {
				items[slot - 1] = items[slot];
				items[slot] = null;
			}
		} else {
			for (int slot = fromSlot - 1; slot >= toSlot; slot--) {
				items[slot + 1] = items[slot];
				items[slot] = null;
			}
		}
		
		items[toSlot] = item;
		
		if(firingEvents) {
			fireItemsChanged();
		}
	}
	
	/**
	 * Fires an item_combine changed event.
	 * @param slot The slot that changed.
	 */
	public void fireItemChanged(int slot) {
		for(Iterator<NewContainerListener> i = listeners.iterator(); i.hasNext();) {
			NewContainerListener listener = i.next();
			listener.itemChanged(this, slot);
		}
	}
	
	/**
	 * Fires an items changed event.
	 */
	public void fireItemsChanged() {
		for(Iterator<NewContainerListener> i = listeners.iterator(); i.hasNext();) {
			NewContainerListener listener = i.next();
			listener.itemsChanged(this);
		}
	}
	
	/**
	 * Fires an items changed event.
	 * @param slots The slots that changed.
	 */
	public void fireItemsChanged(int[] slots) {
		for(Iterator<NewContainerListener> i = listeners.iterator(); i.hasNext();) {
			NewContainerListener listener = i.next();
			listener.itemsChanged(this, slots);
		}
	}
	
	/**
	 * Checks if the container contains the specified item_combine.
	 * @param id The item_combine id.
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean contains(int id) {
		return contains(id, false);
	}

	public boolean contains(int id, boolean include0) {
		return getSlotById(id, include0) != -1;
	}
	
	/**
	 * Checks if the slot contains the specified item_combine.
	 * @param id The item_combine id.
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean slotContains(int slot, int id) {
		return items[slot] != null && items[slot].getId() == id;
	}
	
	/**
	 * Does the container have these items
	 * @param items
	 * 			the items
	 * @return
	 * 		true if the container contains these items
	 */
	public boolean hasItems(Item...items) {
		for (Item i : items) {
			if (!hasItem(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the container contains the specified item_combine, supports quantity checking.
	 * @param item The item_combine to check.
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean hasItem(Item item) {	
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getId() == item.getId()) {
				if (items[i].getAmount() >= item.getAmount()) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean hasItemExactAmount(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getId() == item.getId()) {
				if (items[i].getAmount() == item.getAmount()) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Does the container have enough slots to hold these items
	 * @param items
	 * 			the items
	 * @return
	 * 		true if the container has enough slots to hold the items
	 */
	public boolean hasRoomFor(Item...items){
		
		
		int slots = 0;
		for (Item item : items) {
			if (item != null) {
				if (item.getDefinition().isStackable()) {
					if (contains(item.getId())) {
						if (!hasRoomFor(item)) {
							return false;
						}
					} else {
						slots++;
					}
				} else {
					slots++;
				}
			}
		}
		
		return freeSlots() >= slots;
	}

	/**
	 * Checks if there is room in the inventory for an item_combine for a default {@code Integer.MAX_VALUE} capacity.
	 * @param item The item_combine.
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean hasRoomFor(Item item) {
		return hasRoomFor(item, 0, items.length, Integer.MAX_VALUE);
	}

	/**
	 * Checks if there is room in the inventory for an item_combine for a given maximum capacity.
	 * @param item The item_combine.
	 * @param capacity The maximum amount of an item for it to be deemed as having "room" (exclusive).
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean hasRoomFor(Item item, int capacity) {
		return hasRoomFor(item, 0, items.length, capacity);
	}
	
	/**
	 * Checks if there is room in the inventory for an item_combine for a default {@code Integer.MAX_VALUE} capacity.
	 * @param item The item_combine.
	 * @param start The starting index of the item container to look from.
	 * @param length The amount of items to count from the start.
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean hasRoomFor(Item item, int start, int length) {
		return hasRoomFor(item, start, length, Integer.MAX_VALUE);
	}

	/**
	 * Checks if there is room in the inventory for an item_combine for a default {@code Integer.MAX_VALUE} capacity.
	 * @param item The item_combine.
	 * @param start The starting index of the item container to look from.
	 * @param length The amount of items to count from the start.
	 * @param capacity The maximum amount of an item for it to be deemed as having "room" (exclusive).
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean hasRoomFor(Item item, int start, int length, int capacity) {
		if(item.getDefinition().isStackable() || type.equals(Type.ALWAYS_STACK)) {
			for(int i = start; i < start + length; i++) {
				if(items[i] != null && items[i].getId() == item.getId()) {
					long totalCount = (long) item.getAmount() + items[i].getAmount();
					return totalCount < capacity;
				}
			}
			int slot = freeSlot(start, length);
			return slot != -1 && item.getAmount() < capacity;
		} else {
			if (freeSlots() >= item.getAmount()) {
				return true;
			}
		}/* else {
			throw new IllegalStateException("Function not supported for this container type!");
		}*/
		return false;
	}

	/**
	 * @return
	 * 		the items, cloned
	 */
	public Item[] array() {
		return items.clone();
	}
}