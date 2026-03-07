package com.ruse.world.content.groupironman;

import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.entity.impl.player.Player;

public class GroupIronmanInvitation extends Dialogue {

	public GroupIronmanInvitation(Player inviter, Player p) {
		this.inviter = inviter;
		this.p = p;
	}

	private Player inviter, p;

	@Override
	public DialogueType type() {
		return DialogueType.OPTION;
	}


	@Override
	public String[] dialogue() {
		return new String[]{"Join " + inviter.getUsername() + "'s group", "Don't join " + inviter.getUsername() + "'s group."};
	}
	@Override
	public void specialAction() {
		p.setDialogueActionId(670);
		};
	}
