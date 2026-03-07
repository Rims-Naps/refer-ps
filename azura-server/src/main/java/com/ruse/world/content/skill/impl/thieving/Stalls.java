package com.ruse.world.content.skill.impl.thieving;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.PlayerDeathTask;
import com.ruse.model.Animation;
import com.ruse.model.GameObject;
import com.ruse.model.Item;
import com.ruse.model.Skill;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.content.casketopening.CasketOpening;
import com.ruse.world.entity.impl.player.Player;

import java.util.HashMap;

import static com.ruse.model.container.impl.Equipment.AMMUNITION_SLOT;

public class Stalls {

     public static Box[] loot1 = new Box[]{
            new Box(5020, 1, 3, 100),

    };

    public static void stealFromStall(Player player, GameObject  gameObject, int lvlreq, int xp, Item reward, String message) {

        if (player.getInventory().getFreeSlots() < 1) {
            player.getPacketSender().sendMessage("You need some more inventory space to do this.");
            return;
        }

        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }

        if (!player.getClickDelay().elapsed(2000))
            return;
        }



    public static void stealFromAFKStall(Player player, int npc, int tier) {
        int accounts = 0;
        for (Player p : World.getPlayers()) {
            if (p == null)
                continue;

            if (accounts > 0) {
                player.getPacketSender().sendMessage("@red@<shad=0>You already have two accounts stealing from the AFK Zone.");
                TaskManager.submit(new PlayerDeathTask(player));
                String message = "<col=0><shad=6C1894>[STAFF CHAT]@red@<shad=0> " + player.getUsername() + "@bla@ Tried to afk with 2 accounts";
                World.sendStaffMessage(message);
                return;
            } else {
                if (!player.equals(p) && player.getSerialNumber().equals(p.getSerialNumber())) {
                    if (p.getInteractingObject() != null && (p.getInteractingObject().getId() == 27306)) {
                        accounts++;
                    }
                }

                player.getSkillManager().stopSkilling();
                player.getPacketSender().sendInterfaceRemoval();
                if (!player.getClickDelay().elapsed(2000))
                    return;
                if (player.getInventory().getFreeSlots() <= 0) {
                    player.getPacketSender().sendMessage("You need some more inventory space to do this.");
                    return;
                }
                if (player.busy() || player.getCombatBuilder().isBeingAttacked() || player.getCombatBuilder().isAttacking()) {
                    player.getPacketSender().sendMessage("You cannot do that right now.");
                    return;
                }
                if (tier == 2 && (player.getAfkStallCount1() < 100000)) {
                    player.sendMessage("@red@You have stolen from AFK (T1)" + "  " + player.getAfkStallCount1() + "/100,000 times");
                    return;
                }
            }
        }


        player.setCurrentTask(new Task(4, player, true) {

            @Override
            public void execute() {

                if (player.getInventory().getFreeSlots() <= 0) {
                    player.getPacketSender().sendMessage("You do not have any free inventory space left.");
                    this.stop();
                    return;
                }

                player.performAnimation(new Animation(9368));
                Box[] loot = loot1;

                if (tier == 1) {
                    player.setAfkStallCount1(player.getAfkStallCount1() + 1);
                }

                Box reward = CasketOpening.getLoot(loot);
                HashMap<String, Integer> tracker = player.getTrackers();
                String name = ItemDefinition.forId(reward.getId()).getName();

                if(tracker.containsKey(name)) {
                    tracker.put(name, tracker.get(name)+1);
                } else {
                    tracker.put(name, 1);
                }

                if (player.getEquipment().get(AMMUNITION_SLOT).getId() == 2736 ||player.getEquipment().get(AMMUNITION_SLOT).getId() == 2738 || player.getEquipment().get(AMMUNITION_SLOT).getId() == 2740 ) {
                    player.getInventory().add(reward.getId(), reward.getMin() + Misc.getRandom(reward.getMax() - reward.getMin()));
                }


                player.getInventory().add(reward.getId(), reward.getMin() + Misc.getRandom(reward.getMax() - reward.getMin()));
                player.getInventory().refreshItems();
                player.getClickDelay().reset();
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }
}
