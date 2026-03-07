package com.ruse.world.entity.impl.npc;

import com.ruse.model.Locations;
import com.ruse.model.Locations.Location;
import com.ruse.world.content.combat.CombatFactory;
import com.ruse.world.content.skill.impl.old_dungeoneering.Dungeoneering;
import com.ruse.world.entity.impl.player.Player;


public final class NpcAggression {


	public static final int NPC_TOLERANCE_SECONDS = 604800; // 5 mins

	public static void target(Player player) {

		if (player.isPlayerLocked() || player.isGroupIronmanLocked())
			return;

		boolean dung = Dungeoneering.doingOldDungeoneering(player);


		// Loop through all of the aggressive npcs.
		for (NPC npc : player.getLocalNpcs()) {
			if(npc != null && npc.getDefinition() == null) {
				//System.out.println("call");
			}
			if(npc != null && npc.getId()!= 5040 && npc.getId()!= 1853 && npc.getId()!= 1854 && npc.getId()!= 1855) {
				NPCFacing.updateFacing(player, npc);
			}
			if (npc == null || npc.getDefinition() == null || npc.getConstitution() <= 0 || !npc.getDefinition().isAggressive()) {
				continue;
			}
			if (npc.getSpawnedFor() != null && npc.getSpawnedFor() != player)
				continue;

			if (!npc.findNewTarget()) {
				if (npc.getCombatBuilder().isAttacking() || npc.getCombatBuilder().isBeingAttacked()) {
					continue;
				}
			}

			/** GWD **/
		
			// Check if the entity is within distance.
			if (Locations.goodDistance(npc.getPosition(), player.getPosition(), npc.getAggressionDistance()) || npc.isForceAggressive()) {

				boolean multi = Location.inMulti(player);

				if (player.isTargeted()) {
					if (!player.getCombatBuilder().isBeingAttacked()) {
						player.setTargeted(false);
					} else if (!multi) {
						break;
					}
				}

				if (!npc.isForceAggressive()) {
					if (player.getSkillManager().getCombatLevel() > (npc.getDefinition().getCombatLevel() * 2)
							&& player.getLocation() != Location.WILDERNESS && !dung) {
						continue;
					}
				}

				if(Location.ignoreFollowDistance(npc)  ||
	 npc.getDefaultPosition().getDistance(player.getPosition()) < 7 +
				 npc.getMovementCoordinator().getCoordinator().getRadius() || dung) {
				if (Location.ignoreFollowDistance(npc)|| npc.isForceAggressive()
						|| npc.getDefaultPosition().getDistance(player.getPosition()) < 7
								+ npc.getMovementCoordinator().getCoordinator().getRadius()
						|| dung) {

					if (CombatFactory.checkHook(npc, player)) {
						player.setTargeted(true);
						npc.getCombatBuilder().attack(player);
						npc.setFindNewTarget(false);
						break;
					}
				}
			}
		}
	}
	}
}
