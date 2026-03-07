package com.ruse.world.content.skill.impl.mining;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.PlayerDeathTask;
import com.ruse.model.Animation;
import com.ruse.model.GameObject;
import com.ruse.model.Locations;
import com.ruse.model.Skill;
import com.ruse.model.container.impl.Equipment;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.CustomObjects;
import com.ruse.world.content.SkillingPetBonuses;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.entity.impl.player.Player;

public class Mining {

    public static void startMining(final Player player, final GameObject oreObject) {
        int accounts = 0;
        for (Player p : World.getPlayers()) {
            if (p == null)
                continue;
            if (p.getLocation() == Locations.Location.NECROMANCY_GAME_AREA)
                continue;


            if (accounts > 0 && player.getLocation() != Locations.Location.NECROMANCY_GAME_AREA) {
                player.getPacketSender().sendMessage("@red@<shad=0>You already have an account mining!");
                TaskManager.submit(new PlayerDeathTask(player));
                String message = "<col=0><shad=6C1894>[STAFF CHAT]@red@<shad=0> " + player.getUsername() + "@bla@ Tried to Mine with 2 accounts";
                DiscordManager.sendMessage("[ABUSE] " + player.getUsername() + " tried to mine with 2 accounts ", Channels.PUNISHMENTS);
                World.sendStaffMessage(message);
                return;
            } else {
                if (!player.equals(p) && player.getSerialNumber().equals(p.getSerialNumber())) {
                    if (p.getInteractingObject() != null && (p.getInteractingObject().getId() == 7591)) {
                        accounts++;
                    }
                    if (p.getInteractingObject() != null && (p.getInteractingObject().getId() == 38661)) {
                        accounts++;
                    }
                    if (p.getInteractingObject() != null && (p.getInteractingObject().getId() == 38662)) {
                        accounts++;
                    }
                }
            }
        }

        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendInterfaceRemoval();

        if (oreObject.getId() != 7591 && oreObject.getId() != 38662 && oreObject.getId() != 38661 && !Locations.goodDistance(player.getPosition().copy(), oreObject.getPosition(), 2))
            return;


        if (!player.getClickDelay().elapsed(3000)) {
            return;
        }

        if (oreObject.getId() == 2026) {
            if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() != 311) {
                player.msgRed("Equip a Harpoon to fish for Soul Fish..");
                return;
            }
        }

        if (oreObject.getId() == 49806 || oreObject.getId() == 49780 ){
            if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() != 8044) {
                player.msgRed("Equip a Soulstone Pickaxe to mine this Rock");
                return;
            }
        }

            if (!MiningData.isHoldingPickaxe(player)) {
                if (oreObject.getId() == 2062){
                    player.getPacketSender().sendMessage("<col=AF70C3><shad=0>Equip a Harpoon to fish for Soul fish.");
                }
                if (oreObject.getId() != 2062) {
                    player.getPacketSender().sendMessage("<col=AF70C3><shad=0>Equip a pickaxe to mine this rock.");
                }
                player.getSkillManager().stopSkilling();
                player.getPacketSender().sendClientRightClickRemoval();
                return;
        }

        if (player.busy() ) {
            player.getPacketSender().sendMessage("You cannot do that right now.");
            return;
        }
        if (player.getInventory().getFreeSlots() == 0) {
            player.getPacketSender().sendMessage("You do not have any free inventory space left.");
            return;
        }
        player.setInteractingObject(oreObject);
        player.setPositionToFace(oreObject.getPosition());
        player.getClickDelay().reset();

        final MiningData.Ores o = MiningData.forRock(oreObject.getId());
        final int pickaxe = MiningData.getPickaxe(player);
        final MiningData.Pickaxe p = MiningData.forPick(pickaxe);
        if (o != null) {
            final int miningLevel = player.getSkillManager().getCurrentLevel(Skill.MINING);


                if (pickaxe > 0) {
                    if (miningLevel < o.getLevelReq()) {
                        player.getPacketSender().sendMessage("You need a Mining level of at least " + p.getReq() + " to use this pickaxe.");
                        return;
                }
            }

                    if (miningLevel < p.getReq()) {
                        player.getPacketSender().sendMessage("You need a Mining level of at least " + o.getLevelReq() + " to mine this rock.");
                        return;

                }

        if (MiningData.isHoldingPickaxe(player)) {
            player.getMovementQueue().reset();
            if (oreObject.getId() == 2026){
                player.performAnimation(new Animation(618));
            }
            if (oreObject.getId() != 2026) {
                player.performAnimation(new Animation(12003));
            }
        } else {
            player.getMovementQueue().reset();
            if (oreObject.getId() == 2026){
                player.performAnimation(new Animation(618));
            }
            if (oreObject.getId() != 2026) {
                player.performAnimation(new Animation(12003));
            }
        }

                        player.setCurrentTask(new Task(1, player, false) {
                            int cycle = 0;

                            final int oreLifeCycle = 1000000; // 5 minutes in ticks
                            final int extraore = p.getExtraOre();
                            @Override
                            public void execute() {
                                    if (!MiningData.isHoldingPickaxe(player)) {
                                        if (oreObject.getId() == 2062){
                                            player.getPacketSender().sendMessage("<col=AF70C3><shad=0>Equip a Harpoon to fish for Soul fish.");
                                        }
                                        if (oreObject.getId() != 2062) {
                                            player.getPacketSender().sendMessage("<col=AF70C3><shad=0>Equip a pickaxe to mine this rock.");
                                        }
                                        player.getSkillManager().stopSkilling();
                                        player.getPacketSender().sendClientRightClickRemoval();
                                        stop();
                                        return;
                                    }

                                if (player.getInteractingObject() == null || player.getInteractingObject().getId() != oreObject.getId()) {
                                    player.getSkillManager().stopSkilling();
                                    player.performAnimation(new Animation(65535));
                                    player.getMovementQueue().reset();
                                    stop();
                                    return;
                                }

                                if (player.getInventory().getFreeSlots() == 0) {
                                    player.getPacketSender().sendMessage("You do not have any free inventory space left.");
                                    player.performAnimation(new Animation(65535));
                                    player.getMovementQueue().reset();
                                    stop();
                                    return;
                                }

                                cycle++;
                                int tutorialminedamount = Misc.random(100,200);

                                if (Misc.random(3) == 0) {
                                    if (o == MiningData.Ores.GAUNTLET_FISH) {
                                        if (Misc.random(1) == 0) {
                                            player.getSkillManager().addExperience(Skill.FISHING, (int) (o.getXpAmount()));
                                            SkillingPetBonuses.checkSkillingPet(player,25);
                                            player.getInventory().add(o.getItemId(), extraore);
                                            player.getPacketSender().sendMessage("You caught some Soul Fish");
                                            if (player.getNecroBoost().isActive()){
                                                SkillingPetBonuses.checkSkillingPet(player,25);
                                            }
                                        }
                                    }
                                    if (o == MiningData.Ores.FRACTAL_ROCK || o == MiningData.Ores.GLARITE_ROCK) {
                                        if (Misc.random(2) == 0) {
                                            player.getSkillManager().addExperience(Skill.MINING, (int) (o.getXpAmount()));
                                            SkillingPetBonuses.checkSkillingPet(player,100);
                                            player.getInventory().add(o.getItemId(), extraore);
                                            player.getPacketSender().sendMessage("You mine some ore");
                                            if (player.getNecroBoost().isActive()){
                                                SkillingPetBonuses.checkSkillingPet(player,100);
                                            }
                                        }
                                    }

                                    if (o != MiningData.Ores.FRACTAL_ROCK && o != MiningData.Ores.GLARITE_ROCK && o != MiningData.Ores.GAUNTLET_FISH) {

                                        if (player.completedtutorial) {
                                            int chance_for_rare_gems = Misc.random(0, 50);
                                            int rare_gem_amount = Misc.random(1, 3);
                                            int rare_gem = 20049;
                                            int amountmined = Misc.random(10, 50);

                                            if (PerkManager.currentPerk != null) {
                                                if (PerkManager.currentPerk.getName().equalsIgnoreCase("Skilling")) {
                                                    amountmined *= 1.5;
                                                    rare_gem_amount = Misc.random(1, 5);
                                                }
                                            }

                                            if (chance_for_rare_gems == 0) {
                                                player.getInventory().add(rare_gem, rare_gem_amount);
                                                player.getPacketSender().sendMessage("You found some rare gems while mining...");
                                            }

                                            player.getSkillManager().addExperience(Skill.MINING, (int) (o.getXpAmount()));
                                            player.getInventory().add(o.getItemId(), extraore);
                                            player.getInventory().add(o.getItemId(), amountmined);
                                            player.getPacketSender().sendMessage("You mine some salt...");
                                            Achievements.doProgress(player, Achievements.Achievement.MINE_5K_SALT, amountmined + extraore);
                                            Achievements.doProgress(player, Achievements.Achievement.MINE_25K_SALT, amountmined + extraore);
                                            Achievements.doProgress(player, Achievements.Achievement.MINE_100K_SALT, amountmined + extraore);
                                            Achievements.doProgress(player, Achievements.Achievement.MINE_250K_SALT, amountmined + extraore);
                                            Achievements.doProgress(player, Achievements.Achievement.MINE_500K_SALT, amountmined + extraore);
                                            Achievements.doProgress(player, Achievements.Achievement.MINE_1M_SALT, amountmined + extraore);
                                            StarterTasks.doProgress(player, StarterTasks.StarterTask.MINE_10k_SALT, amountmined + extraore);
                                        }
                                    }
                                }

                                if (cycle >= oreLifeCycle) { // after rock's life expires
                                    player.performAnimation(new Animation(65535));
                                    oreRespawn(player, oreObject, o);
                                    player.getMovementQueue().reset();
                                    stop();
                                } else {
                                    if (MiningData.isHoldingPickaxe(player)) {
                                        if (o == MiningData.Ores.GAUNTLET_FISH) {
                                            player.performAnimation(new Animation(5));
                                        }
                                        if (o != MiningData.Ores.GAUNTLET_FISH) {
                                            player.performAnimation(new Animation(12003));
                                        }
                                        player.getMovementQueue().reset();
                                    } else {
                                        if (o == MiningData.Ores.GAUNTLET_FISH) {
                                            player.performAnimation(new Animation(618));
                                        }
                                        if (o != MiningData.Ores.GAUNTLET_FISH) {
                                            player.performAnimation(new Animation(12003));
                                        }
                                        player.getMovementQueue().reset();
                                    }
                                }
                            }
                        });
                        TaskManager.submit(player.getCurrentTask());
            }
        }


    public static void oreRespawn(Player player, GameObject oldOre, MiningData.Ores o) {

        if (oldOre == null || oldOre.getPickAmount() >= o.getRespawn()) {
            return;
        }

        oldOre.setPickAmount(o.getRespawn());
        for (Player players : player.getLocalPlayers()) {
            if (players == null) {
                continue;
            }

            if (players.getInteractingObject() != null && players.getInteractingObject().getPosition().equals(player.getInteractingObject().getPosition().copy())) {
                players.getSkillManager().stopSkilling();
                players.getPacketSender().sendClientRightClickRemoval();
            }
        }

        player.getPacketSender().sendClientRightClickRemoval();
        player.getSkillManager().stopSkilling();
        CustomObjects.globalObjectRespawnTask(new GameObject(452, oldOre.getPosition().copy(), 10, 0), oldOre, o.getRespawn() / 100); // Assuming 'respawn' is in ticks
    }
}
