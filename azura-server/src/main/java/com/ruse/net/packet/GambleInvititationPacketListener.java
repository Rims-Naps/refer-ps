package com.ruse.net.packet;

import com.ruse.engine.task.impl.WalkToTask;
import com.ruse.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.ruse.model.Locations;
import com.ruse.model.Locations.Location;
import com.ruse.world.World;
import com.ruse.world.content.gamblinginterface.GamblingInterface;
import com.ruse.world.entity.impl.player.Player;

public class GambleInvititationPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getConstitution() <= 0)
			return;
		if (player.isTeleporting())
			return;
		player.getSkillManager().stopSkilling();

		int index = packet.getOpcode() == GAMBLE_OPCODE ? (packet.readShort() & 0xFF) : packet.readLEShort();
		// System.out.println("Index from client: " + index);
		if (index < 0 || index > World.getPlayers().capacity())
			return;
		Player target = World.getPlayers().get(index);

		// // System.out.println("Index: " + index);
		// // System.out.println("Name: " + target.getUsername());
		if (target == null || !Locations.goodDistance(player.getPosition(), target.getPosition(), 13))
			return;
		player.setWalkToTask(
				new WalkToTask(player, target.getPosition(), target.getSize(), () -> {
					if (target.getIndex() != player.getIndex()) {

							if (player.getGamblingPass() == 1) {
								if (target.getGamblingPass() == 1) {
									if (player.getLocation() == Location.GAMBLE && target.getLocation() == Location.GAMBLE) {
										player.getGambling().setGambleWith(target.getIndex());
										target.getGambling().setGambleRequested(true);
										player.getGambling().requestGamble(target);
										//System.out.println("Player: " + player.getUsername() + " | index: " + player.getIndex() + " | gambleWith: " + player.getGambling().getGambleWith());
										//System.out.println("Target: " + target.getUsername() + " | index: " + target.getIndex() + " | gambleWith: " + target.getGambling().getGambleWith());

									} else {
										player.getPacketSender().sendMessage("@red@You can only gamble at the gambling zone!");
									}
								} else {
									player.getPacketSender().sendMessage("@red@Your opponent needs to claim a gambling pass before you can gamble.");
								}
							} else {
								player.getPacketSender().sendMessage("@red@You need to claim a gambling pass to gamble. Visit our ::store");
							}

							//player.getGambling().setGambleWith(target.getIndex()); // this delete

					}
				}));
	}

	public static final int GAMBLE_OPCODE = 191;
	public static final int CHATBOX_GAMBLE_OPCODE = 193;
}
