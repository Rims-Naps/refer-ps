package com.ruse.world.content.combat.effect;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ruse.engine.task.Task;
import com.ruse.model.CombatIcon;
import com.ruse.model.Hit;
import com.ruse.model.Hitmask;
import com.ruse.model.Item;
import com.ruse.world.entity.impl.Character;

public class BurnEffect extends Task {

	private Character entity;


	public BurnEffect(Character entity) {
		super(3, entity, false);
		this.entity = entity;
	}

	public enum BurnType {
		BASE_PRAYER(20), TIER2_PRAYER(70), SUPER(120), BRIMSTONE_ATTACK(10)
		, BOOSTED_BURN(30) , INFERNO(50);

		private int damage;


		private BurnType(int damage) {
			this.damage = damage;
		}


		public int getDamage() {
			return damage;
		}
	}

	@Override
	public void execute() {

		if (entity == null){
			this.stop();
			return;
		}

		// Stop the task if the entity is unregistered.
		if (!entity.isRegistered() || !entity.isOnFire()) {
			this.stop();
			return;
		}

		// Deal the damage, then try and decrement the damage count.
		entity.dealDamage(new Hit(entity.getAndDecrementBurnDmg(), Hitmask.FIRE, CombatIcon.NONE));
	}

	public static final class BurnData {

		private static final Map<Integer, BurnType> types = new HashMap<>(97);

		public static void init() {
			types.put(817, BurnType.BASE_PRAYER);
		}

		public static Optional<BurnType> getBurnType(Item item) {
			if (item == null || item.getId() < 1 || item.getAmount() < 1)
				return Optional.empty();
			return Optional.ofNullable(types.get(item.getId()));
		}

		private BurnData() {
		}
	}
}
