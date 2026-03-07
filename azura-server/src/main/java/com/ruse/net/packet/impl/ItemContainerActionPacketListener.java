package com.ruse.net.packet.impl;

import com.ruse.model.Flag;
import com.ruse.model.Item;
import com.ruse.model.Locations.Location;
import com.ruse.model.PlayerRights;
import com.ruse.model.container.impl.*;
import com.ruse.model.container.impl.Shop.ShopManager;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.definitions.WeaponAnimations;
import com.ruse.model.definitions.WeaponInterfaces;
import com.ruse.model.input.Input;
import com.ruse.model.input.impl.*;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.combat.magic.Autocasting;
import com.ruse.world.content.combat.weapon.CombatSpecial;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.grandexchange.GrandExchange;
import com.ruse.world.content.grandexchange.GrandExchangeOffer;
import com.ruse.world.content.minigames.impl.Dueling;
import com.ruse.world.content.minigames.impl.Dueling.DuelRule;
import com.ruse.world.content.minigames.impl.YesNoDialogue;
import com.ruse.world.content.skill.impl.crafting.Jewelry;
import com.ruse.world.content.tradingpost.PlayerShopRepo;
import com.ruse.world.content.tradingpost.PlayerShops;
import com.ruse.world.entity.impl.player.Player;

import static com.ruse.net.packet.impl.EquipPacketListener.resetWeapon;

public class ItemContainerActionPacketListener implements PacketListener {

	/**
	 * Manages an item's first action.
	 *
	 * @param player The player clicking the item.
	 * @param packet The packet to read values from.
	 */
	private static void firstAction(Player player, Packet packet) {
		int interfaceId = packet.readShortA();
		int slot = packet.readShortA();
		int id = packet.readShortA();
		Item item = new Item(id);

		//System.out.println("firstAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
        if (item != null)
//            System.out.println(item.getId());
		if (player.getRights().isDeveloperOnly() || player.getRights() == PlayerRights.OWNER) {
			player.getPacketSender().sendMessage("firstAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
		}

		if (player.getPlayerShops().itemActionPacket(Option.ONE, interfaceId, slot)) {
			return;
		}

		switch (interfaceId) {
			case -23336:
                if (player.getRights().isManagement()) {
					Item ites = player.getInventory().forSlot(slot).copy();
					player.getIslandInterface().addItem(ites);
				}
				break;
			case 30919:
				player.getUpgrade().selection(item);
				break;

			case 30351:
				player.getCustomCombiner().handleSelection(item);
				break;

			case -32090:
				player.getItemUpgrader().handleSelection(slot);
				break;
			case -16815:
				player.getUimBank().withdraw(id, 1);
				break;

			case -13234:
				// System.out.println("Item was: " + item.getId());
				Object[] obj = ShopManager.getCustomShopData(119, item.getId());
				if (obj == null) {
					return;
				}
				int value = (int) ((int) obj[0] * 0.85);
				String name = item.getDefinition().getName();
				player.sendMessage("<col=AF70C3>The shop buys @red@" + name + " for " + value + " Bill Bags");

				break;

			case -3327:
				player.getUpgradeInterface().handleItemAction(slot);
				break;

			case 32621:
				player.getPlayerOwnedShopManager().handleBuy(slot, id, -1);
				break;
			case -31915:
				player.getPlayerOwnedShopManager().handleWithdraw(slot, id, -1);
				break;
			case -28382:
				player.getPlayerOwnedShopManager().handleStore(slot, id, 1);
				break;
			// done.
			case GrandExchange.COLLECT_ITEM_PURCHASE_INTERFACE:
				GrandExchange.collectItem(player, id, slot, GrandExchangeOffer.OfferType.BUYING);
				break;
			case GrandExchange.COLLECT_ITEM_SALE_INTERFACE:
				GrandExchange.collectItem(player, id, slot, GrandExchangeOffer.OfferType.SELLING);
				break;
			case Trading.INTERFACE_ID:
				if (player.getTrading().inTrade()) {
					player.getTrading().tradeItem(id, 1, slot);
				} else if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.getDueling().stakeItem(id, 1, slot);
				} else if (player.getTierUpgrading().isViewing()) {
					player.getTierUpgrading().handleItemClick(id);
				}
				if (player.getGambling().inGamble()) {
					System.out.println("player is in gamble: " + player.getUsername());
					player.getGambling().gambleItem(id, 1, slot);
				}


				if (player.getInterfaceId() == 19307 && player.getGroupIronmanGroup() != null) {
					player.getGroupIronmanGroup().addItem(player, item.getId(), 1);
				}

				if (player.getInterfaceId() == 37500) {
					BalloonDropParty.addItem(player, item);
				}

				player.getUimBank().deposit(id, 1);
				break;
			case -8365:
				if (player.getGambling().inGamble()) {
					player.getGambling().removeGambledItem(id, 1);
				}
				break;
			case Trading.INTERFACE_REMOVAL_ID:
				if (player.getTrading().inTrade())
					player.getTrading().removeTradedItem(id, 1);

				// player.getUimBank().withd
				break;
			case Dueling.INTERFACE_REMOVAL_ID:
				if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.getDueling().removeStakedItem(id, 1);
					return;
				}
				break;
			case Equipment.INVENTORY_INTERFACE_ID:
				if (player.viewingCosmeticTab)
					item = slot < 0 ? null : player.getCosmetics().getItems()[slot];
				else
					item = slot < 0 ? null : player.getEquipment().getItems()[slot];
				if (item == null || item.getId() != id)
					return;
				if (player.viewingCosmeticTab) {
					boolean stackItem = item.getDefinition().isStackable() && player.getInventory().getAmount(item.getId()) > 0;
					int inventorySlot = player.getInventory().getEmptySlot();
					if (inventorySlot != -1) {
						Item itemReplacement = new Item(-1, 0);
						player.getCosmetics().setItem(slot, itemReplacement);
						if (!stackItem)
							player.getInventory().setItem(inventorySlot, item);
						else
							player.getInventory().add(item.getId(), item.getAmount());

						player.getCosmetics().refreshItems();
						player.getInventory().refreshItems();
						player.getUpdateFlag().flag(Flag.APPEARANCE);
					} else {
						player.getInventory().full();
					}
					return;
				}
				if (player.getLocation() == Location.DUEL_ARENA) {
					if (player.getDueling().selectedDuelRules[DuelRule.LOCK_WEAPON.ordinal()]) {
						if (item.getDefinition().getEquipmentSlot() == Equipment.WEAPON_SLOT
								|| item.getDefinition().isTwoHanded()) {
							player.getPacketSender().sendMessage("Weapons have been locked during this duel!");
							return;
						}
					}
				}
				boolean stackItem = item.getDefinition().isStackable() && player.getInventory().getAmount(item.getId()) > 0;
				int inventorySlot = player.getInventory().getEmptySlot();
				if (inventorySlot != -1) {
					Item itemReplacement = new Item(-1, 0);
					player.getEquipment().setItem(slot, itemReplacement);
					if (!stackItem)
						player.getInventory().setItem(inventorySlot, item);
					else
						player.getInventory().add(item.getId(), item.getAmount());
					BonusManager.update(player);
					if (item.getDefinition().getEquipmentSlot() == Equipment.WEAPON_SLOT) {
						WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
						WeaponAnimations.update(player);
						if (player.getAutocastSpell() != null || player.isAutocast()) {
							Autocasting.resetAutocast(player, true);
							player.getPacketSender().sendMessage("Autocast spell cleared.");
						}
						player.setSpecialActivated(false);
						CombatSpecial.updateBar(player);
						if (player.hasStaffOfLightEffect()) {
							player.setStaffOfLightEffect(-1);
							player.getPacketSender()
									.sendMessage("You feel the spirit of the Staff of Light begin to fade away...");
						}
					}
					player.getEquipment().refreshItems();
					player.getInventory().refreshItems();
					player.getUpdateFlag().flag(Flag.APPEARANCE);
				} else {
					player.getInventory().full();
				}
				break;
			case Bank.INTERFACE_ID:
				if (!player.isBanking() || player.getInterfaceId() != 5292)
					break;
                if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK)) {
                    player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).switchItem(player, player.getInventory(), item, slot, true, true);
                    player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).open(player);
                    /**
                     * This return prevents a dupe....
                     * We don't want people to put an item into their group bank and bank right?
                     */
                    return;
                }
				player.getBank(player.getCurrentBankTab()).switchItem(player.getInventory(), item, slot, true, true);
				player.getBank(player.getCurrentBankTab()).open();
				break;
			case Bank.INVENTORY_INTERFACE_ID:
				if (player.isBanking() && player.getViewing().equals(Player.INTERFACES.GROUP_BANK) && player.getInventory().contains(item.getId())){
                    player.setCurrentGroupBankTab(GroupIronmanBank.getTabForItem(player, item.getId()));
					player.getInventory().switchItem(player.getGroupIronmanBank(player.getCurrentGroupBankTab()), item, slot, false, true);
					player.getGroupIronmanBank(player.getCurrentGroupBankTab()).refreshItems(player);
                    for (int i = 0; i < 5; i++) {
                        if (player.getIronmanGroup().getMembers().size() > i) {
                            name = player.getIronmanGroup().getMembers().get(i);
                            Player target = World.getPlayerByName(name);
                            if (target != null) {
                                if (target.getCurrentBankTab() != player.getCurrentGroupBankTab())
                                    continue;
                                target.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).refreshItems(target);
                            }
                        }
                    }
					return;
				}

				if (!player.isBanking() || !player.getInventory().contains(item.getId()) || player.getInterfaceId() != 5292)
					return;
				if (player.getBank(player.getCurrentBankTab()).getFreeSlots() <= 0
						&& !(player.getBank(player.getCurrentBankTab()).contains(item.getId()))) {
					player.setCurrentBankTab(player.getCurrentBankTab() + 1);
					if (player.getCurrentBankTab() > 8) {
						player.setCurrentBankTab(8);
						if (player.getBank(player.getCurrentBankTab()).isFull()) {
							player.sendMessage("Your whole bank is full.");
							return;
						}
					}
					player.getInventory().switchItem(player.getBank(player.getCurrentBankTab()), item, slot, false,
							true);
					player.getPacketSender()
							.sendMessage("Your item has been added to another tab because this tab is full.");
				} else {
					player.setCurrentBankTab(Bank.getTabForItem(player, item.getId()));
					player.getInventory().switchItem(player.getBank(player.getCurrentBankTab()), item, slot, false, true);
				}
				break;
			case Shop.ITEM_CHILD_ID:
			case DonatorShop.ITEM_CHILD_ID_CLICK:
			case CosmeticShop.ITEM_CHILD_ID_CLICK:
				if (player.getShop() != null)
					player.getShop().checkValue(player, slot, false);
				break;
			case Shop.INVENTORY_INTERFACE_ID:
				if (player.getShop() != null)
					player.getShop().checkValue(player, slot, true);
				break;
			case BeastOfBurden.INTERFACE_ID:
				if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
						&& player.getSummoning().getBeastOfBurden() != null) {
					if (item.getDefinition().isStackable()) {
						player.getPacketSender().sendMessage("You cannot store stackable items.");
						return;
					}
					player.getInventory().switchItem(player.getSummoning().getBeastOfBurden(), item, slot, false, true);
				}
				break;
			case PriceChecker.INTERFACE_PC_ID:
				if (player.getInterfaceId() == PriceChecker.INTERFACE_ID && player.getPriceChecker().isOpen()) {
					player.getInventory().switchItem(player.getPriceChecker(), item, slot, false, true);
				}
				break;
			case 4233:
				Jewelry.jewelryMaking(player, "RING", id, 1);
				break;
			case 4239:
				Jewelry.jewelryMaking(player, "NECKLACE", id, 1);
				break;
			case 4245:
				Jewelry.jewelryMaking(player, "AMULET", id, 1);
				break;
			case 1119: // smithing interface row 1
			case 1120: // row 2
			case 1121: // row 3
			case 1122: // row 4
			case 1123: // row 5
				break;
		}

		/*if (player.getItemExchangeInterface().attemptToExchange(interfaceId, item.getId())) {
			return;
		}*/

		if (BeastOfBurden.beastOfBurdenSlot(interfaceId) >= 0) {
			if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
					&& player.getSummoning().getBeastOfBurden() != null) {
				player.getSummoning().getBeastOfBurden().switchItem(player.getInventory(), item,
						BeastOfBurden.beastOfBurdenSlot(interfaceId), false, true);
			}
		} else if (PriceChecker.priceCheckerSlot(interfaceId) >= 0) {
			if (player.getPriceChecker().isOpen()) {
				player.getPriceChecker().switchItem(player.getInventory(), new Item(id, 1),
						PriceChecker.priceCheckerSlot(interfaceId), false, true);
			}
		}
	}

	/**
	 * Manages an item's second action.
	 *
	 * @param player The player clicking the item.
	 * @param packet The packet to read values from.
	 */
	private static void secondAction(Player player, Packet packet) {
		int interfaceId = packet.readLEShortA();
		int id = packet.readLEShortA();
		int slot = packet.readLEShort();
		Item item = new Item(id);
        //System.out.println("secondAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
        if (item != null)
            //System.out.println(item.getId());
		if (player.getRights().isDeveloperOnly()) {
			player.getPacketSender()
					.sendMessage("secondAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
		}

		if (player.getPlayerShops().itemActionPacket(Option.TWO, interfaceId, slot)) {
			return;
		}
		switch (interfaceId) {

			case 19313:
				if (player.getInterfaceId() == 19307 && player.getGroupIronmanGroup() != null) {
					player.getGroupIronmanGroup().removeItem(player, item.getId(), 5);
				}
				break;
			case -16815:
				player.getUimBank().withdraw(id, player.getUimBankItems().get(id));
				break;
			case -31915:
				player.setInputHandling(new Input() {

					@Override
					public void handleLongAmount(Player player, long value) {
						player.getPlayerOwnedShopManager().setCustomPrice(slot, id, value);
					}

				});
				player.getPacketSender().sendEnterLongAmountPrompt("Enter the price for this item:");
				break;
			case -28382:
				player.getPlayerOwnedShopManager().handleStore(slot, id, 5);
				break;
			case Trading.INTERFACE_ID:
				if (player.getTrading().inTrade()) {
					player.getTrading().tradeItem(id, 5, slot);
				} else if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.getDueling().stakeItem(id, 5, slot);
				}
				if (player.getGambling().inGamble()) {
					player.getGambling().gambleItem(id, 5, slot);
				}


				if (player.getInterfaceId() == 19307 && player.getGroupIronmanGroup() != null) {
					player.getGroupIronmanGroup().addItem(player, item.getId(), 5);
				}
				break;

			case -8365:
				if (player.getGambling().inGamble())
					player.getGambling().removeGambledItem(id, 5);
				break;
			case Trading.INTERFACE_REMOVAL_ID:
				if (player.getTrading().inTrade())
					player.getTrading().removeTradedItem(id, 5);
				break;
			case Dueling.INTERFACE_REMOVAL_ID:
				if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.getDueling().removeStakedItem(id, 5);
					return;
				}
				break;
			case Bank.INTERFACE_ID:
				if (!player.isBanking() || item.getId() != id || player.getInterfaceId() != 5292)
					return;
                if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK)) {
                    player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).switchItem(player, player.getInventory(), new Item(id, 5), slot, true, true);
                    player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).open(player);
                    /**
                     * This return prevents a dupe....
                     * We don't want people to put an item into their group bank and bank right?
                     */
                    return;
                }
				player.getBank(player.getCurrentBankTab()).switchItem(player.getInventory(), new Item(id, 5), slot, true,
						true);
				player.getBank(player.getCurrentBankTab()).open();
				break;
			case Bank.INVENTORY_INTERFACE_ID:

				item = player.getInventory().forSlot(slot).copy().setAmount(5).copy();
				if (player.isBanking() && player.getViewing().equals(Player.INTERFACES.GROUP_BANK) && player.getInventory().contains(item.getId())){
                    player.setCurrentGroupBankTab(GroupIronmanBank.getTabForItem(player, item.getId()));
					player.getInventory().switchItem(player.getGroupIronmanBank(player.getCurrentGroupBankTab()), item, slot, false, true);
					player.getGroupIronmanBank(player.getCurrentGroupBankTab()).refreshItems(player);
                    for (int i = 0; i < 5; i++) {
                        if (player.getIronmanGroup().getMembers().size() > i) {
                            String name = player.getIronmanGroup().getMembers().get(i);
                            Player target = World.getPlayerByName(name);
                            if (target != null) {
                                if (target.getCurrentBankTab() != player.getCurrentGroupBankTab())
                                    continue;
                                target.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).refreshItems(target);
                            }
                        }
                    }
					return;
				}
				if (!player.isBanking() || item.getId() != id || !player.getInventory().contains(item.getId())
						|| player.getInterfaceId() != 5292)
					return;
				player.setCurrentBankTab(Bank.getTabForItem(player, item.getId()));
				player.getInventory().switchItem(player.getBank(player.getCurrentBankTab()), item, slot, false, true);
				break;
			case Shop.ITEM_CHILD_ID:
			case DonatorShop.ITEM_CHILD_ID_CLICK:
			case CosmeticShop.ITEM_CHILD_ID_CLICK:
				if (player.getShop() == null)
					return;
				item = player.getShop().forSlot(slot).copy().setAmount(1).copy();
				player.getShop().setPlayer(player).switchItem(player.getInventory(), item, slot, false, true);
				break;
			case Shop.INVENTORY_INTERFACE_ID:
				if (player.isShopping()) {
					player.getShop().sellItem(player, slot, 1);
					return;
				}
				break;
			case BeastOfBurden.INTERFACE_ID:
				if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
						&& player.getSummoning().getBeastOfBurden() != null) {
					if (item.getDefinition().isStackable()) {
						player.getPacketSender().sendMessage("You cannot store stackable items.");
						return;
					}
					player.getInventory().switchItem(player.getSummoning().getBeastOfBurden(), new Item(id, 5), slot, false,
							true);
				}
				break;
			case PriceChecker.INTERFACE_PC_ID:
				if (player.getInterfaceId() == PriceChecker.INTERFACE_ID && player.getPriceChecker().isOpen()) {
					player.getInventory().switchItem(player.getPriceChecker(), new Item(id, 5), slot, false, true);
				}
				break;
			case 4233:
				Jewelry.jewelryMaking(player, "RING", id, 5);
				break;
			case 4239:
				Jewelry.jewelryMaking(player, "NECKLACE", id, 5);
				break;
			case 4245:
				Jewelry.jewelryMaking(player, "AMULET", id, 5);
				break;
			case 1119: // smithing interface row 1
			case 1120: // row 2
			case 1121: // row 3
			case 1122: // row 4
			case 1123: // row 5
				break;
		}

		if (BeastOfBurden.beastOfBurdenSlot(interfaceId) >= 0) {
			if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
					&& player.getSummoning().getBeastOfBurden() != null) {
				player.getSummoning().getBeastOfBurden().switchItem(player.getInventory(), new Item(id, 5),
						BeastOfBurden.beastOfBurdenSlot(interfaceId), false, true);
			}
		} else if (PriceChecker.priceCheckerSlot(interfaceId) >= 0) {
			if (player.getPriceChecker().isOpen()) {
				player.getPriceChecker().switchItem(player.getInventory(), new Item(id, 5),
						PriceChecker.priceCheckerSlot(interfaceId), false, true);
			}
		}
	}

	/**
	 * Manages an item's third action.
	 *
	 * @param player The player clicking the item.
	 * @param packet The packet to read values from.
	 */
	private static void thirdAction(Player player, Packet packet) {
		int interfaceId = packet.readLEShort();
		int id = packet.readShortA();
		int slot = packet.readShortA();
		Item item1 = new Item(id);
        //System.out.println("secondAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
        if (item1 != null)
            //System.out.println(item1.getId());
		if (player.getRights().isDeveloperOnly()) {
			player.getPacketSender()
					.sendMessage("thirdAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
		}

		if (player.getPlayerShops().itemActionPacket(Option.THREE, interfaceId, slot)) {
			return;
		}
		switch (interfaceId) {

			case 19313:
				if (player.getInterfaceId() == 19307 && player.getGroupIronmanGroup() != null) {
					player.getGroupIronmanGroup().removeItem(player, id, 10);
				}
				break;
			case 32621:
				player.getPlayerOwnedShopManager().handleBuy(slot, id, 1);
				break;
			case -31915:
				player.getPlayerOwnedShopManager().handleWithdraw(slot, id, 1);
				break;
			case -28382:
				player.getPlayerOwnedShopManager().handleStore(slot, id, 10);
				break;
			case Equipment.INVENTORY_INTERFACE_ID:
				if (!player.getEquipment().contains(id))
					return;
				if(id >= 23069 && id <= 23074) {
					if(slot == 4) {
						DialogueManager.start(player, new YesNoDialogue(player, "Would you like to teleport to your slayer task", "for the price of 250k?", 668));
					}
				}

				switch (id) {

					case 435:
						switch (player.getMarinaTaskID()) {
							case 0:
								MarinasTasks.assignTask(player);
								break;
							case 1:
								DialogueManager.sendStatement(player, "Dig near a lamp");
								break;
							case 2:
								DialogueManager.sendStatement(player, "Dig near a Bank Chest");
								break;
							case 3:
								DialogueManager.sendStatement(player, "Dig on Charred Bones");
								break;
							case 4:
								DialogueManager.sendStatement(player, "Dig in front of an altar");
								break;
							case 5:
								DialogueManager.sendStatement(player, "Dig on a branch near a Vorpal Tree");
								break;
							case 6:
								DialogueManager.sendStatement(player, "Use Coins the fire wizard at COE.");
								break;
							case 7:
								DialogueManager.sendStatement(player, "Talk to the King, upstairs in the Castle.");
								break;
							case 8:
								DialogueManager.sendStatement(player, "Defeat the Donator Boss");
								break;
							case 9:
								DialogueManager.sendStatement(player, "Complete a Slayer Task");
								break;
							case 10:
								DialogueManager.sendStatement(player, "Defeat Netherveil");
								break;
						}
						break;

					case 436:
						player.holyGrailDroprate = !player.holyGrailDroprate;
						if (player.holyGrailDroprate) {
							player.msgRed("Sacrificing 5% Maxhit for 5% Droprate.");
						}
						if (!player.holyGrailDroprate) {
							player.msgRed("Sacrificing 5% Droprate for 5% Maxhit.");
						}
						break;



					case 2056:
						switch (player.getQuiverMode()){
							case 0:
								player.msgFancyPurp("Now using Vorpal Ammo!");
								player.setQuiverMode(1);
								break;
							case 1:
								player.msgFancyPurp("Now using Bloodstained Ammo!");
								player.setQuiverMode(2);
								break;
							case 2:
								player.msgFancyPurp("Now using Symbiote Ammo!");
								player.setQuiverMode(3);
								break;
							case 3:
								player.msgFancyPurp("Now using Nether Ammo!");
								player.setQuiverMode(4);
								break;
							case 4:
								player.msgFancyPurp("Quiver Toggled Off.");
								player.setQuiverMode(0);
								break;
						}
						break;

						//EARTH APPRENTICE
					case 5000:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5001));
						resetWeapon(player);
						player.updateAppearance();
						break;
					case 5001:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5002));
						resetWeapon(player);
						player.updateAppearance();
						break;
					case 5002:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5000));
						resetWeapon(player);
						player.updateAppearance();
						break;

					//WATER APPRENTICE
					case 5003:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5004));
						resetWeapon(player);
						player.updateAppearance();
						break;
					case 5004:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5005));
						resetWeapon(player);
						player.updateAppearance();
						break;
					case 5005:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5003));
						resetWeapon(player);
						player.updateAppearance();
						break;

					//FIRE APPRENTICE
					case 5006:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5007));
						resetWeapon(player);
						player.updateAppearance();
						break;
					case 5007:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5008));
						resetWeapon(player);
						player.updateAppearance();
						break;
					case 5008:
						player.getPacketSender().sendMessage("You have Transformed your Weapon");
						player.getEquipment().setItem(3, new Item(5006));
						resetWeapon(player);
						player.updateAppearance();
						break;

				}
				break;
			case Trading.INTERFACE_ID:
				if (player.getTrading().inTrade()) {
					player.getTrading().tradeItem(id, 10, slot);
				} else if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.getDueling().stakeItem(id, 10, slot);
				}
				if (player.getGambling().inGamble()) {
					player.getGambling().gambleItem(id, 10, slot);
				}


				if (player.getInterfaceId() == 19307 && player.getGroupIronmanGroup() != null) {
					player.getGroupIronmanGroup().addItem(player, id, 10);
				}

				break;
			case -8365:
				if (player.getGambling().inGamble())
					player.getGambling().removeGambledItem(id, 10);
				break;
			case Trading.INTERFACE_REMOVAL_ID:
				if (player.getTrading().inTrade())
					player.getTrading().removeTradedItem(id, 10);
				break;
			case Dueling.INTERFACE_REMOVAL_ID:
				if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.getDueling().removeStakedItem(id, 10);
					return;
				}
				break;
			case Bank.INTERFACE_ID:
				if (!player.isBanking() || player.getInterfaceId() != 5292)
					return;
                if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK)) {
                    player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).switchItem(player, player.getInventory(), new Item(id, 10), slot, true, true);
                    player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).open(player);
                    /**
                     * This return prevents a dupe....
                     * We don't want people to put an item into their group bank and bank right?
                     */
                    return;
                }
				player.getBank(player.getCurrentBankTab()).switchItem(player.getInventory(), new Item(id, 10), slot, true,
						true);
				player.getBank(player.getCurrentBankTab()).open();
				break;
			case Bank.INVENTORY_INTERFACE_ID:
				Item item = player.getInventory().forSlot(slot).copy().setAmount(10).copy();
				if (player.isBanking() && player.getViewing().equals(Player.INTERFACES.GROUP_BANK) && player.getInventory().contains(item.getId())){
                    player.setCurrentGroupBankTab(GroupIronmanBank.getTabForItem(player, item.getId()));
					player.getInventory().switchItem(player.getGroupIronmanBank(player.getCurrentGroupBankTab()), item, slot, false, true);
					player.getGroupIronmanBank(player.getCurrentGroupBankTab()).refreshItems(player);
                    for (int i = 0; i < 5; i++) {
                        if (player.getIronmanGroup().getMembers().size() > i) {
                            String name = player.getIronmanGroup().getMembers().get(i);
                            Player target = World.getPlayerByName(name);
                            if (target != null) {
                                if (target.getCurrentBankTab() != player.getCurrentGroupBankTab())
                                    continue;
                                target.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).refreshItems(target);
                            }
                        }
                    }
					return;
				}
				if (!player.isBanking() || item.getId() != id || !player.getInventory().contains(item.getId())
						|| player.getInterfaceId() != 5292)
					return;
				player.setCurrentBankTab(Bank.getTabForItem(player, item.getId()));
				player.getInventory().switchItem(player.getBank(player.getCurrentBankTab()), item, slot, false, true);
				break;
			case Shop.ITEM_CHILD_ID:
			case DonatorShop.ITEM_CHILD_ID_CLICK:
			case CosmeticShop.ITEM_CHILD_ID_CLICK:
				if (player.getShop() == null)
					return;
				item = player.getShop().forSlot(slot).copy().setAmount(5).copy();
				player.getShop().setPlayer(player).switchItem(player.getInventory(), item, slot, false, true);
				break;
			case Shop.INVENTORY_INTERFACE_ID:
				if (player.isShopping()) {
					player.getShop().sellItem(player, slot, 5);
					return;
				}
				break;
			case BeastOfBurden.INTERFACE_ID:
				if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
						&& player.getSummoning().getBeastOfBurden() != null) {
					Item storeItem = new Item(id, 10);
					if (storeItem.getDefinition().isStackable()) {
						player.getPacketSender().sendMessage("You cannot store stackable items.");
						return;
					}
					player.getInventory().switchItem(player.getSummoning().getBeastOfBurden(), storeItem, slot, false,
							true);
				}
				break;
			case PriceChecker.INTERFACE_PC_ID:
				if (player.getInterfaceId() == PriceChecker.INTERFACE_ID && player.getPriceChecker().isOpen()) {
					player.getInventory().switchItem(player.getPriceChecker(), new Item(id, 10), slot, false, true);
				}
				break;
		}

		if (BeastOfBurden.beastOfBurdenSlot(interfaceId) >= 0) {
			if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
					&& player.getSummoning().getBeastOfBurden() != null) {
				player.getSummoning().getBeastOfBurden().switchItem(player.getInventory(), new Item(id, 10),
						BeastOfBurden.beastOfBurdenSlot(interfaceId), false, true);
			}
		} else if (PriceChecker.priceCheckerSlot(interfaceId) >= 0) {
			if (player.getPriceChecker().isOpen()) {
				player.getPriceChecker().switchItem(player.getInventory(), new Item(id, 10),
						PriceChecker.priceCheckerSlot(interfaceId), false, true);
			}
		}
	}

	/**
	 * Manages an item's fourth action.
	 *
	 * @param player The player clicking the item.
	 * @param packet The packet to read values from.
	 */
	private static void fourthAction(Player player, Packet packet) {
		int slot = packet.readShortA();
		int interfaceId = packet.readShort();
		int id = packet.readShortA();
        //System.out.println("fourthAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
		if (player.getRights().isDeveloperOnly()) {
			player.getPacketSender()
					.sendMessage("fourthAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
		}

		if (player.getPlayerShops().itemActionPacket(Option.FOUR, interfaceId, slot)) {
			return;
		}
		switch (interfaceId) {
			case Trading.INTERFACE_ID:
				if (player.getTrading().inTrade()) {
					player.getTrading().tradeItem(id, player.getInventory().getAmount(id), slot);
				} else if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.getDueling().stakeItem(id, player.getInventory().getAmount(id), slot);
				}
				if (player.getGambling().inGamble()) {
					player.getGambling().gambleItem(id, player.getInventory().getAmount(id), slot);
				}
				// System.out.println("CALLED HERE for amount: " + player.getInventory().getAmount(id));
				player.getUimBank().deposit(id, player.getInventory().getAmount(id));

				if (player.getInterfaceId() == 19307 && player.getGroupIronmanGroup() != null) {
					player.getGroupIronmanGroup()
							.addItem(player, id, player.getInventory().getAmount(id));
				}

				break;

			case 19313:
				if (player.getInterfaceId() == 19307 && player.getGroupIronmanGroup() != null) {
					player.getGroupIronmanGroup().removeItem(player, id, player.getGroupIronmanGroup().getBank().getOrDefault(id, 0));
				}
				break;

			case -8365:
				if (player.getGambling().inGamble())
					player.getGambling().removeGambledItem(id, player.getInventory().getAmount(id));
				break;
			case 32621:
				player.setInputHandling(new Input() {

					@Override
					public void handleAmount(Player player, int amount) {
						player.getPlayerOwnedShopManager().handleBuy(slot, id, amount);
					}

				});
				player.getPacketSender().sendEnterAmountPrompt("How many would you like to buy?:");
				break;
			case -28382:
				player.getPlayerOwnedShopManager().handleStore(slot, id, Integer.MAX_VALUE);
				break;
			case -31915:
				player.setInputHandling(new Input() {

					@Override
					public void handleAmount(Player player, int amount) {
						player.getPlayerOwnedShopManager().handleWithdraw(slot, id, amount);
					}

				});
				player.getPacketSender().sendEnterAmountPrompt("How many would you like to withdraw?:");
				break;
			case Trading.INTERFACE_REMOVAL_ID:
				if (player.getTrading().inTrade()) {
					for (Item item : player.getTrading().offeredItems) {
						if (item != null && item.getId() == id) {
							player.getTrading().removeTradedItem(id, item.getAmount());
							if (ItemDefinition.forId(id) != null && ItemDefinition.forId(id).isStackable())
								break;
						}
					}
				}
				break;
			case Dueling.INTERFACE_REMOVAL_ID:
				if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					for (Item item : player.getDueling().stakedItems) {
						if (item != null && item.getId() == id) {
							player.getDueling().removeStakedItem(id, item.getAmount());
							if (ItemDefinition.forId(id) != null && ItemDefinition.forId(id).isStackable())
								break;
						}
					}
				}
				break;
			case Bank.INTERFACE_ID:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK)) {
                    if (player.getIronmanGroup().getBank(GroupIronmanBank.getTabForItem(player, id)).getAmount(id) <= 0)
                        return;
                    player.getGroupIronmanBank(player.getCurrentGroupBankTab()).switchItem(player, player.getInventory(),
                        new Item(id, player.getGroupIronmanBank(player.getCurrentGroupBankTab()).getAmount(id)), slot,
                        true, true);
                    player.getGroupIronmanBank(player.getCurrentGroupBankTab()).open(player);
                    /**
                     * This return prevents a dupe....
                     * We don't want people to put an item into their group bank and bank right?
                     */
                    return;
                }
                if (player.getBank(Bank.getTabForItem(player, id)).getAmount(id) <= 0)
                    return;
				player.getBank(player.getCurrentBankTab()).switchItem(player.getInventory(),
						new Item(id, player.getBank(Bank.getTabForItem(player, id)).getAmount(id)), slot, true, true);
				player.getBank(player.getCurrentBankTab()).open();
				break;
			case Bank.INVENTORY_INTERFACE_ID:
				Item item = player.getInventory().forSlot(slot).copy().setAmount(player.getInventory().getAmount(id));
				if (player.isBanking() && player.getViewing().equals(Player.INTERFACES.GROUP_BANK) && player.getInventory().contains(item.getId())){
                    player.setCurrentGroupBankTab(GroupIronmanBank.getTabForItem(player, item.getId()));
					player.getInventory().switchItem(player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()), item, slot, false, true);
					player.getGroupIronmanBank(player.getCurrentGroupBankTab()).refreshItems(player);
                    for (int i = 0; i < 5; i++) {
                        if (player.getIronmanGroup().getMembers().size() > i) {
                            String name = player.getIronmanGroup().getMembers().get(i);
                            Player target = World.getPlayerByName(name);
                            if (target != null) {
                                if (target.getCurrentBankTab() != player.getCurrentGroupBankTab())
                                    continue;
                                target.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).refreshItems(target);
                            }
                        }
                    }
					return;
				}
				player.setCurrentBankTab(Bank.getTabForItem(player, item.getId()));
				player.getInventory().switchItem(player.getBank(player.getCurrentBankTab()), item, slot, false, true);
				break;
			case Shop.ITEM_CHILD_ID:
			case DonatorShop.ITEM_CHILD_ID_CLICK:
			case CosmeticShop.ITEM_CHILD_ID_CLICK:
				if (player.getShop() == null)
					return;
				item = player.getShop().forSlot(slot).copy().setAmount(10).copy();
				player.getShop().setPlayer(player).switchItem(player.getInventory(), item, slot, true, true);
				break;
			case Shop.INVENTORY_INTERFACE_ID:
				if (player.isShopping()) {
					player.getShop().sellItem(player, slot, 10);
					return;
				}
				break;
			case BeastOfBurden.INTERFACE_ID:
				if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
						&& player.getSummoning().getBeastOfBurden() != null) {
					Item storeItem = new Item(id, 29);
					if (storeItem.getDefinition().isStackable()) {
						player.getPacketSender().sendMessage("You cannot store stackable items.");
						return;
					}
					player.getInventory().switchItem(player.getSummoning().getBeastOfBurden(), storeItem, slot, false,
							true);
				}
				break;
			case PriceChecker.INTERFACE_PC_ID:
				if (player.getInterfaceId() == PriceChecker.INTERFACE_ID && player.getPriceChecker().isOpen()) {
					player.getInventory().switchItem(player.getPriceChecker(),
							new Item(id, player.getInventory().getAmount(id)), slot, false, true);
				}
				break;
		}

		if (BeastOfBurden.beastOfBurdenSlot(interfaceId) >= 0) {
			if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
					&& player.getSummoning().getBeastOfBurden() != null) {
				player.getSummoning().getBeastOfBurden().switchItem(player.getInventory(), new Item(id, 29),
						BeastOfBurden.beastOfBurdenSlot(interfaceId), false, true);
			}
		} else if (PriceChecker.priceCheckerSlot(interfaceId) >= 0) {
			if (player.getPriceChecker().isOpen()) {
				player.getPriceChecker().switchItem(player.getInventory(),
						new Item(id, player.getPriceChecker().getAmount(id)),
						PriceChecker.priceCheckerSlot(interfaceId), false, true);
			}
		}
	}

	/**
	 * Manages an item's fifth action.
	 *
	 * @param player The player clicking the item.
	 * @param packet The packet to read values from.
	 */
	private static void fifthAction(Player player, Packet packet) {
		int slot = packet.readLEShort();
		int interfaceId = packet.readShortA();
		int id = packet.readLEShort();
        //System.out.println("fifthAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);

		if (player.getRights().isDeveloperOnly()) {
			player.getPacketSender()
					.sendMessage("fifthAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
		}
		if (player.getPlayerShops().itemActionPacket(Option.FIVE, interfaceId, slot)) {
			return;
		}

		switch (interfaceId) {
			case Trading.INTERFACE_ID:
				if (player.getTrading().inTrade()) {
					player.setInputHandling(new EnterAmountToTrade(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to trade?");
				} else if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.setInputHandling(new EnterAmountToStake(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to stake?");
				} else if (player.getGambling().inGamble()) {
					player.setInputHandling(new EnterAmountToGamble(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to gamble?");
				}
				break;
			case -8365:
				if (player.getGambling().inGamble()) {
					player.setInputHandling(new EnterAmountToRemoveGamble(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
				}
				break;
			case Trading.INTERFACE_REMOVAL_ID:
				if (player.getTrading().inTrade()) {
					player.setInputHandling(new EnterAmountToRemoveFromTrade(id));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
				}
				break;
			case Dueling.INTERFACE_REMOVAL_ID:
				if (Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) {
					player.setInputHandling(new EnterAmountToRemoveFromStake(id));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
				}
				break;
			case Bank.INVENTORY_INTERFACE_ID: // BANK X
			case 12:
				Item item = player.getInventory().forSlot(slot).copy().setAmount(player.getInventory().getAmount(id));
				if (player.isBanking() && player.getViewing().equals(Player.INTERFACES.GROUP_BANK) && player.getInventory().contains(item.getId())){
					player.setInputHandling(new EnterAmountToGroupBank(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to bank?");
					return;
				}

				if (player.isBanking()) {
					player.setInputHandling(new EnterAmountToBank(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to bank?");
				} else {
					item = player.getInventory().forSlot(slot).copy()
							.setAmount(player.getInventory().getAmount(id) - 1);
					if (!player.isBanking() || item.getId() != id || !player.getInventory().contains(item.getId())
							|| player.getInterfaceId() != 5292)
						return;
					player.setCurrentBankTab(Bank.getTabForItem(player, item.getId()));
					player.getInventory().switchItem(player.getBank(player.getCurrentBankTab()), item, slot, false, true);
				}
				break;
			case Bank.INTERFACE_ID:
			case 11:
				if (player.isBanking()) {
					if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK) && player.isBanking()) {
						player.setInputHandling(new EnterAmountToRemoveFromGroupBank(id, slot));
						player.getPacketSender().sendEnterAmountPrompt("How many would you like to withdraw?");
					} else if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK)) {
						if (player.getGroupIronmanBank(player.getCurrentGroupBankTab()).getAmount(id) == 1) {
							player.getGroupIronmanBank(player.getCurrentGroupBankTab()).switchItem(player, player.getInventory(),
									new Item(id, player.getGroupIronmanBank(player.getCurrentGroupBankTab()).getAmount(id)), slot,
									true, true);
						} else {
							player.getGroupIronmanBank(player.getCurrentGroupBankTab()).switchItem(player, player.getInventory(),
									new Item(id, player.getGroupIronmanBank(player.getCurrentGroupBankTab()).getAmount(id) - 1), slot,
									true, true);
						}
						player.getGroupIronmanBank(player.getCurrentGroupBankTab()).open(player);
					} else if (player.isBanking()) {
						player.setInputHandling(new EnterAmountToRemoveFromBank(id, slot));
						player.getPacketSender().sendEnterAmountPrompt("How many would you like to withdraw?");
					} else {
						if (player.getBank(Bank.getTabForItem(player, id)).getAmount(id) == 1) {
							player.getBank(player.getCurrentBankTab()).switchItem(player.getInventory(),
									new Item(id, player.getBank(Bank.getTabForItem(player, id)).getAmount(id)), slot,
									true, true);
						} else {
							player.getBank(player.getCurrentBankTab()).switchItem(player.getInventory(),
									new Item(id, player.getBank(Bank.getTabForItem(player, id)).getAmount(id) - 1), slot,
									true, true);
						}
						player.getBank(player.getCurrentBankTab()).open(player, false);
					}
				}
				break;
			case Shop.ITEM_CHILD_ID:
			case DonatorShop.ITEM_CHILD_ID_CLICK:
			case CosmeticShop.ITEM_CHILD_ID_CLICK:
				if (player.isBanking())
					return;
				if (player.isShopping()) {
					player.setInputHandling(new EnterAmountToBuyFromShop(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to buy?");
					player.getShop().setPlayer(player);
				}
				break;
			case Shop.INVENTORY_INTERFACE_ID:
				if (player.isBanking())
					return;
				if (player.isShopping()) {
					player.setInputHandling(new EnterAmountToSellToShop(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to sell?");
					player.getShop().setPlayer(player);
				}
				break;
			case PriceChecker.INTERFACE_PC_ID:
				if (player.getInterfaceId() == PriceChecker.INTERFACE_ID && player.getPriceChecker().isOpen()) {
					player.setInputHandling(new EnterAmountToPriceCheck(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to pricecheck?");
				}
				break;
			case BeastOfBurden.INTERFACE_ID:
				if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
						&& player.getSummoning().getBeastOfBurden() != null) {
					Item storeItem = new Item(id, 10);
					if (storeItem.getDefinition().isStackable()) {
						player.getPacketSender().sendMessage("You cannot store stackable items.");
						return;
					}
					player.setInputHandling(new EnterAmountToStore(id, slot));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to store?");
				}
				break;
		}

		if (BeastOfBurden.beastOfBurdenSlot(interfaceId) >= 0) {
			if (player.getInterfaceId() == BeastOfBurden.INTERFACE_ID
					&& player.getSummoning().getBeastOfBurden() != null) {
				player.setInputHandling(new EnterAmountToRemoveFromBob(id, slot));
				player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
			}
		} else if (PriceChecker.priceCheckerSlot(interfaceId) >= 0) {
			if (player.getPriceChecker().isOpen()) {
				player.setInputHandling(new EnterAmountToRemoveFromPriceCheck(id, slot));
				player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
			}
		}
	}

	private static void sixthAction(Player player, Packet packet) {
		int interfaceId = packet.readShortA();
		int slot = packet.readShortA();
		int id = packet.readShortA();
        //System.out.println("sixthAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);

		if (player.getRights().isDeveloperOnly()) {
			player.getPacketSender()
					.sendMessage("sixthAction itemContainer. IF: " + interfaceId + " slot: " + slot + ", id: " + id);
		}
		if (player.getPlayerShops().itemActionPacket(Option.SIX, interfaceId, slot)) {
			return;
		}
		switch (interfaceId) {
			case Shop.INVENTORY_INTERFACE_ID:
				if (player.isShopping()) {
					player.getShop().sellItem(player, slot, player.getInventory().getAmount(id));
					return;
				}
				break;
		}
	}

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getConstitution() <= 0)
			return;
		switch (packet.getOpcode()) {
			case FIRST_ITEM_ACTION_OPCODE:
				firstAction(player, packet);
				break;
			case SECOND_ITEM_ACTION_OPCODE:
				secondAction(player, packet);
				break;
			case THIRD_ITEM_ACTION_OPCODE:
				thirdAction(player, packet);
				break;
			case FOURTH_ITEM_ACTION_OPCODE:
				fourthAction(player, packet);
				break;
			case FIFTH_ITEM_ACTION_OPCODE:
				fifthAction(player, packet);
				break;
			case SIXTH_ITEM_ACTION_OPCODE:
				sixthAction(player, packet);
				break;
			case CLICK_TRADE_POST_ITEM_SEARCH_OPCODE:
				int item = packet.readInt();
				if (player.getInterfaceId() != PlayerShops.MAIN_INTERFACE_ID && player.getInterfaceId() != PlayerShops.SEARCH_RESULTS_INTERFACE_ID) {
					return;
				}

				ItemDefinition def = ItemDefinition.forId(item);
				if (def == null) {
					player.message("Item cannot be bought.");
					return;
				}

				player.getPlayerShops().setCurrentSearch(p -> PlayerShopRepo.searchForItemId(player, item));
				PlayerShopRepo.searchForItemId(player, item);
				break;
		}
	}

	public static final int FIRST_ITEM_ACTION_OPCODE = 145;
	public static final int SECOND_ITEM_ACTION_OPCODE = 117;
	public static final int THIRD_ITEM_ACTION_OPCODE = 43;
	public static final int FOURTH_ITEM_ACTION_OPCODE = 129;
	public static final int FIFTH_ITEM_ACTION_OPCODE = 135;
	public static final int SIXTH_ITEM_ACTION_OPCODE = 138;
	public static final int CLICK_TRADE_POST_ITEM_SEARCH_OPCODE = 220;
}
