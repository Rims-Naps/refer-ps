package com.ruse.world.content.skill.skillable;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.AnimationLoop;
import com.ruse.model.Item;
import com.ruse.model.RequiredItem;
import com.ruse.model.Skill;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.SkillingBossSpawner;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemCreationSkillable extends DefaultSkillable {

    /**
     * A {@link List} containing all the {@link RequiredItem}s.
     */
    private final List<RequiredItem> requiredItems;

    /**
     * The item we're making.
     */
    private final Item product;
    /**
     * The {@link AnimationLoop} the player will perform whilst performing this
     * skillable.
     */
    private final Optional<AnimationLoop> animLoop;
    /**
     * The level required to make this item.
     */
    private final int requiredLevel;
    /**
     * The experience a player will receive in the said skill for making this item.
     */
    private final int experience;
    /**
     * The skill to reward the player experience in.
     */
    private final Skill skill;
    /**
     * The amount to make.
     */
    private int amount;

    public ItemCreationSkillable(List<RequiredItem> requiredItems, Item product, int amount,
                                 Optional<AnimationLoop> animLoop, int requiredLevel, int experience, Skill skill) {
        this.requiredItems = requiredItems;
        this.product = product;
        this.amount = amount;
        this.animLoop = animLoop;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.skill = skill;
    }

    @Override
    public void startAnimationLoop(Player player) {
        if (!animLoop.isPresent()) {
            return;
        }
        Task animLoopTask = new Task(animLoop.get().getLoopDelay(), player, false) {
            @Override
            protected void execute() {
                player.performAnimation(animLoop.get().getAnim());
            }
        };
        TaskManager.submit(animLoopTask);
        getTasks().add(animLoopTask);
    }

    @Override
    public int cyclesRequired(Player player) {
        return 2;
    }

    @Override
    public void onCycle(Player player) {

    }

    @Override
    public void finishedCycle(Player player) {
        // Decrement amount to make and stop if we hit 0.
        if (amount-- <= 0) {
            cancel(player);
        }

        // Delete items required..
        filterRequiredItems(r -> r.isDelete()).forEach(r -> player.getInventory().delete(r.getItem()));

        // Add product..
        player.getInventory().add(product);

        // Add exp..
        player.getSkillManager().addExperience(skill, experience);

        // Send message..
        String name = product.getDefinition().getName();
        String amountPrefix = Misc.anOrA(name);
        if (product.getAmount() > 1) {
            if (!name.endsWith("s")) {
                name += "s";
            }
            amountPrefix = Integer.toString(product.getAmount());
        }

        if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()) {
            MediumTasks.doProgress(player, MediumTasks.MediumTaskData.MAKE_25000_ARROWS, product.getAmount());
        }
        if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()) {
            EliteTasks.doProgress(player, EliteTasks.EliteTaskData.MAKE_50k_EACH_ARROW, product.getAmount());
        }
        
        GameSettings.EMERALD_CHAMP_AMOUNT++;
        int emeraldChampAmount = GameSettings.EMERALD_CHAMP_AMOUNT;

        switch (emeraldChampAmount) {
            case 500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] 4500 resources remaining to summon the Emerald Champion!");
                break;
            case 1500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] 3500 resources remaining to summon the Emerald Champion!");
                break;
            case 2500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] Only 2500 resources remaining to summon the Emerald Champion!");
                break;
            case 3500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] Only 1500 resources remaining to summon the Emerald Champion!");
                DiscordManager.sendMessage("[SKILLING] Only 1500 resources remaining to summon the Emerald Champion! <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
                break;
            case 4500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] Only 500 resources remaining to summon the Emerald Champion!");
                break;
            default:
                if (emeraldChampAmount >= 5000) {
                    SkillingBossSpawner.startSkillingBossEvent(player);
                    GameSettings.EMERALD_CHAMP_AMOUNT = 0;
                }
        }

        player.getPacketSender().sendMessage("You make " + amountPrefix + " " + name + ".");
    }

    @Override
    public boolean hasRequirements(Player player) {
        // Validate amount..
        if (amount <= 0) {
            return false;
        }

        // Check if we have required stringing level..
        if (player.getSkillManager().getCurrentLevel(skill) < requiredLevel) {
            player.getPacketSender().sendMessage("You need a " + skill.getName() + " level of at least "
                + Integer.toString(requiredLevel) + " to do this.");
            return false;
        }

        // Validate required items..
        // Check if we have the required ores..
        boolean hasItems = true;
        for (RequiredItem item : requiredItems) {
            if (!player.getInventory().contains(item.getItem())) {
                String prefix = item.getItem().getAmount() > 1 ? Integer.toString(item.getItem().getAmount()) : "some";
                player.getPacketSender().sendMessage("You " + (!hasItems ? "also need" : "need") + " " + prefix + " "
                    + item.getItem().getDefinition().getName() + ".");
                hasItems = false;
            }
        }
        if (!hasItems) {
            return false;
        }

        return super.hasRequirements(player);
    }

    @Override
    public boolean loopRequirements() {
        return true;
    }

    @Override
    public boolean allowFullInventory() {
        return true;
    }

    public void decrementAmount() {
        amount--;
    }

    public int getAmount() {
        return amount;
    }

    public List<RequiredItem> filterRequiredItems(Predicate<RequiredItem> criteria) {
        return requiredItems.stream().filter(criteria).collect(Collectors.<RequiredItem>toList());
    }

    public List<RequiredItem> getRequiredItems() {
        return requiredItems;
    }
}
