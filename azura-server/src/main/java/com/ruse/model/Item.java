package com.ruse.model;

import com.ruse.GameSettings;
import com.ruse.ReducedSellPrice;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.Effigies;
import lombok.Getter;
import lombok.Setter;
import com.ruse.world.entity.impl.player.Player;
/**
 * Represents an item which is owned by a player.
 * 
 * @author relex lawl
 */

public class Item {

	/**
	 * An Item object constructor.
	 * 
	 * @param id     Item id.
	 * @param amount Item amount.
	 */
	public Item(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}
	@Getter
	@Setter
	private int tier;
	/**
	 * An Item object constructor.
	 * 
	 * @param id Item id.
	 */
	public Item(int id) {
		this(id, 1);
	}

	/**
	 * The item id.
	 */
	private int id;

	/**
	 * Gets the item's id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the item's id.
	 * 
	 * @param id New item id.
	 */
	public Item setId(int id) {
		this.id = id;
		return this;
	}

	/**
	 * Amount of the item.
	 */
	private int amount;

	/**
	 * Gets the amount of the item.
	 */
	public int getAmount() {
		return amount;
	}

	private int slot;

	public int getSlot() {
		return this.slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	/**
	 * Sets the amount of the item.
	 */
	public Item setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Gets item's definition.
	 */
	public ItemDefinition getDefinition() {
		return ItemDefinition.forId(id);
	}

	public boolean tradeable() {
		String name = getDefinition().getName().toLowerCase();

		if (name.toLowerCase().contains("apprentice"))
			return false;

		switch (getId()) {

			//APPRENTICE ACC
			case 10069:
			case 10074:
			case 10071:

			case 12828:
			case 12468:
			case 12826:
			case 439:
				//TRINKET PARTS
			case 430:
			case 431:
			case 432:
				//UNIQUES
			case 433:
			case 434:
			case 435:
			case 436:
			case 2950:

			//VOID CRYSTAL ITEMS
			case 777:
			case 778:
			case 779:

			case 760:
			case 761:
			case 762:
			case 18647:
			case 12637:
			case 717:
			case 373:
			case 375:
			case 311:
			case 7784:
			case 7785:
			case 7786:
			case 7766:
			case 7788:
			case 7747:
			case 8044:
			case 8046:
			case 7783:
			case 7782:
			case 7780:
			case 6805:
			case 6806:
			case 6807:
			case 7509:
			//$1 BOND
			//$5 BOND


			//SLAYER GEM
			case 4155:
			//ESSENCE POUCH
			case 18015:
			//RUNE POUCH
			case 19622:
			//URN
			case 20425:
			case 20426:
			//SPADE
			case 952:
			//NECRO MINIGAME ARTIFACTS
			case 20090:
			case 20091:
			case 20092:
			case 20093:
			case 20094:
			//ENCHANTED HEART
			case 2705:
			case 1300:
			case 1301:
			//GAIA UNLOCK
			case 20030:
			//EBB & FLOW UNLOCK
			case 20035:
			//CINDERS UNLOCK
			case 20040:
			case 3500:


				return false;
		}

		for (int i : GameSettings.UNTRADEABLE_ITEMS) {
			if (id == i)
				return false;
		}
		if (Effigies.isEffigy(id))
			return false;
		return true;
	}

	public boolean reducedPrice() {
		for (ReducedSellPrice r : ReducedSellPrice.values()) {
			if (r.getUnNotedId() == id || r.getNotedId() == id) {
				return true;
			}
		}
		return false;
	}

	public boolean sellable() {
		String name = getDefinition().getName().toLowerCase();
		if (name.contains("clue scroll"))
			return false;
		if (name.contains("overload") || name.contains("extreme"))
			return false;
		if (name.toLowerCase().contains("(deg)") || name.toLowerCase().contains("brawling"))
			return false;
		for (int i : GameSettings.UNTRADEABLE_ITEMS) {
			if (id == i)
				return false;
		}
		for (int i : GameSettings.UNSELLABLE_ITEMS) {
			if (id == i)
				return false;
		}
		if (Effigies.isEffigy(id))
			return false;
		return true;
	}

	public static Item getNoted(int id, int amount) {
		int notedItem = id + 1;
		if (ItemDefinition.forId(notedItem).getName().equals(ItemDefinition.forId(id).getName())) {
			return new Item(notedItem, amount);
		}
		return new Item(id, amount);
	}

	public static int getNoted(int id) {
		int noted = id + 1;
		if (id == 11283 || id == 11284) {
			noted = 11285;
		}
		if (ItemDefinition.forId(noted).getName().equals(ItemDefinition.forId(id).getName())) {
			// // System.out.println("getNoted has returned "+noted);
			return noted;
		}
		// // System.out.println("getNoted has not returned anything good.");
		return id;
	}

	public static int getUnNoted(int id) {
		int unNoted = id - 1;
		if (id == 11284 || id == 11285) {
			unNoted = 11283;
		}
		if (ItemDefinition.forId(unNoted).getName().equals(ItemDefinition.forId(id).getName())) {
			return unNoted;
		}
		return id;
	}

	public static boolean tradeable(int item) {
		return new Item(item).tradeable();
	}

	public static boolean sellable(int item) {
		return new Item(item).sellable();
	}

	/**
	 * Copying the item by making a new item with same values.
	 */
	public Item copy() {
		return new Item(id, amount);
	}

	/**
	 * Increment the amount by 1.
	 */
	public void incrementAmount() {
		if ((amount + 1) > Integer.MAX_VALUE) {
			return;
		}
		amount++;
	}

	/**
	 * Decrement the amount by 1.
	 */
	public void decrementAmount() {
		if ((amount - 1) < 0) {
			return;
		}
		amount--;
	}

	/**
	 * Increment the amount by the specified amount.
	 */
	public void incrementAmountBy(int amount) {
		if ((this.amount + amount) > Integer.MAX_VALUE) {
			this.amount = Integer.MAX_VALUE;
		} else {
			this.amount += amount;
		}
	}

	/**
	 * Decrement the amount by the specified amount.
	 */
	public void decrementAmountBy(int amount) {
		if ((this.amount - amount) < 1) {
			this.amount = 0;
		} else {
			this.amount -= amount;
		}
	}

	/** ITEM RARITY **/
	public ItemRarity rarity;

	public Item setRarity(ItemRarity rarity) {
		this.rarity = rarity;
		return this;
	}


	@Override
	public String toString() {
		return "Item{" +
				"id=" + id +
				", amount=" + amount +
				'}';
	}
}