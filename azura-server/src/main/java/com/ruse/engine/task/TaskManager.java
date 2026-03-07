package com.ruse.engine.task;

import com.ruse.world.entity.impl.player.Player;

import java.util.*;

public final class TaskManager {

	private final static Queue<Task> pendingTasks = new LinkedList<>();

	private final static List<Task> activeTasks = new LinkedList<>();

	private TaskManager() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}

	public static void sequence() {
		try {
			Task t;
			while ((t = pendingTasks.poll()) != null) {
				if (t.isRunning()) {
					activeTasks.add(t);
				}
			}

			Iterator<Task> it = activeTasks.iterator();

			while (it.hasNext()) {
				t = it.next();
				if (!t.tick())
					it.remove();
			}
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	public static void submit(Task task) {
		if (!task.isRunning())
			return;
		if (task.isImmediate()) {
			task.execute();
		}
		pendingTasks.add(task);
	}

	public static void cancelTasks(Object key) {
		try {
			pendingTasks.stream().filter(t -> t.getKey().equals(key)).forEach(t -> t.stop());
			activeTasks.stream().filter(t -> t.getKey().equals(key)).forEach(t -> t.stop());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cancelTasksByObject(Player player, Object key) {
		try {
			pendingTasks.stream().filter(t -> t.getAttachment() != null && t.getAttachment().equals(player) &&
					t.getKey().getClass().getName().equals(key.getClass().getName())).forEach(Task::stop);
			activeTasks.stream().filter(t -> t.getAttachment() != null && t.getAttachment().equals(player) &&
					t.getKey().getClass().getName().equals(key.getClass().getName())).forEach(Task::stop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Optional<Task> getActiveTasksByObject(Player player, Object key) {
		try {
				return activeTasks.stream().filter(t -> t.getAttachment() != null && t.getAttachment().equals(player) &&
						t.getKey().getClass().getName().equals(key.getClass().getName())).findFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public static int getTaskAmount() {
		return (pendingTasks.size() + activeTasks.size());
	}
}
// lol dont do that,
