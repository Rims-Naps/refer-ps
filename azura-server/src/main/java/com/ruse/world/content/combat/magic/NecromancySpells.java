package com.ruse.world.content.combat.magic;

import java.util.Optional;

import com.ruse.model.Item;
import com.ruse.model.Skill;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.player.Player;

public enum NecromancySpells {
	DEATHWALKER(new Spell() {

		@Override
		public int spellId() {
			return 27587;
		}

		@Override
		public int levelRequired() {
			return 1;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 2), new Item(20015, 3)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	}),
	ARROWSHADE(new Spell() {

		@Override
		public int spellId() {
			return 27595;
		}

		@Override
		public int levelRequired() {
			return 10;
		}

		@Override
		public int baseExperience() {
			return 15;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 3), new Item(20015, 4)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	}),
	BONEMANCER(new Spell() {

		@Override
		public int spellId() {
			return 27603;
		}

		@Override
		public int levelRequired() {
			return 20;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 4), new Item(20015, 5)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	SKELETAL_SERVANT(new Spell() {

		@Override
		public int spellId() {
			return 27611;
		}

		@Override
		public int levelRequired() {
			return 25;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 5), new Item(20015, 5)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	SHADOWFIEND(new Spell() {

		@Override
		public int spellId() {
			return 27619;
		}

		@Override
		public int levelRequired() {
			return 35;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 3), new Item(20014, 4)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	DEVILSPAWN(new Spell() {

		@Override
		public int spellId() {
			return 27627;
		}

		@Override
		public int levelRequired() {
			return 40;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 4), new Item(20014, 5)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	ABYSSAL_TORMENTOR(new Spell() {

		@Override
		public int spellId() {
			return 27635;
		}

		@Override
		public int levelRequired() {
			return 40;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 5), new Item(20014, 6)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	DEMONIC_SERVANT(new Spell() {

		@Override
		public int spellId() {
			return 27643;
		}

		@Override
		public int levelRequired() {
			return 45;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 5), new Item(20014, 5)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	GRUNT_MAULER(new Spell() {

		@Override
		public int spellId() {
			return 27651;
		}

		@Override
		public int levelRequired() {
			return 55;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 5), new Item(20015, 5), new Item(20012, 2)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	BRUTE_CRUSHER(new Spell() {

		@Override
		public int spellId() {
			return 27659;
		}

		@Override
		public int levelRequired() {
			return 60;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 6), new Item(20015, 6), new Item(20012, 3)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	VINESPLITTER(new Spell() {

		@Override
		public int spellId() {
			return 27667;
		}

		@Override
		public int levelRequired() {
			return 65;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 7), new Item(20015, 8), new Item(20012, 4)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	OGRE_SERVANT(new Spell() {

		@Override
		public int spellId() {
			return 27675;
		}

		@Override
		public int levelRequired() {
			return 70;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 7), new Item(20015, 7), new Item(20012, 3)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	PHANTOM_DRIFTER(new Spell() {

		@Override
		public int spellId() {
			return 27683;
		}

		@Override
		public int levelRequired() {
			return 75;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 6), new Item(20014, 5), new Item(20011, 2)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	WHISPERING_WRAITH(new Spell() {

		@Override
		public int spellId() {
			return 27691;
		}

		@Override
		public int levelRequired() {
			return 80;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 7), new Item(20014, 6), new Item(20011, 4)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	BANSHEE_KING(new Spell() {

		@Override
		public int spellId() {
			return 27699;
		}

		@Override
		public int levelRequired() {
			return 85;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 8), new Item(20014, 7), new Item(20011, 4)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	SPECTRAL_SERVANT(new Spell() {

		@Override
		public int spellId() {
			return 27707;
		}

		@Override
		public int levelRequired() {
			return 90;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20010, 8), new Item(20014, 8), new Item(20011, 3)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	EYE_OF_BEYOND(new Spell() {

		@Override
		public int spellId() {
			return 27715;
		}

		@Override
		public int levelRequired() {
			return 95;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20012, 10), new Item(20011, 10), new Item(20013, 3)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	TALONWING(new Spell() {

		@Override
		public int spellId() {
			return 27723;
		}

		@Override
		public int levelRequired() {
			return 100;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20012, 15), new Item(20011, 15), new Item(20013, 4)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	UMBRAL_BEAST(new Spell() {

		@Override
		public int spellId() {
			return 27731;
		}

		@Override
		public int levelRequired() {
			return 105;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20012, 25), new Item(20011, 25), new Item(20013, 5)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	})
	,
	MASTER_SERVANT(new Spell() {

		@Override
		public int spellId() {
			return 27739;
		}

		@Override
		public int levelRequired() {
			return 112;
		}

		@Override
		public int baseExperience() {
			return 112;
		}

		@Override
		public Optional<Item[]> itemsRequired(Player player) {
			return Optional.of(new Item[] { new Item(20012, 25), new Item(20011, 25), new Item(20013, 5)});
		}

		@Override
		public Optional<Item[]> equipmentRequired(Player player) {
			return Optional.empty();
		}

		@Override
		public void startCast(Character cast, Character castOn) {

		}


	});



	NecromancySpells(Spell spell) {
		this.spell = spell;
	}

	private Spell spell;

	public Spell getSpell() {
		return spell;
	}

	public static NecromancySpells forSpellId(int spellId) {
		for (NecromancySpells spells : NecromancySpells.values()) {
			if (spells.getSpell().spellId() == spellId)
				return spells;
		}
		return null;
	}

	@SuppressWarnings("incomplete-switch")
	public static boolean handleMagicSpells(Player p, int buttonId) {
		NecromancySpells spell = forSpellId(buttonId);
		if (p.getNecrotimeleft() > 0){
			p.sendMessage("You already have a follower summoned...");
			return true;
		}
		if (!spell.getSpell().canCast(p, false))
			return true;


		
		switch (spell) {

		case DEATHWALKER:
			if (!p.isUnlockedSkeletalSpells()){
				p.sendMessage("@red@<shad=0>You do not have Skeletal Spells Unlocked!");
				return true;
			}

			if (p.getNecrotimeleft() > 0){
				p.sendMessage("You already have a follower summoned...");
				return true;
			}
			if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
				p.getPacketSender().sendMessage("You already have a familiar.");
				return true;
			}

			//GFX/ANIM
			/*p.performAnimation(new Animation(4410));
			p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

			//PET NAME
			p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3151), true);
			p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");
            //Sets Last Summoned
            p.setLastNecroSpell(buttonId);
			//SUMMON TIME
            int modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
			p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
			p.getClickDelay().reset();
			break;

			case ARROWSHADE:
				if (!p.isUnlockedSkeletalSpells()){
					p.sendMessage("@red@<shad=0>You do not have Skeletal Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3154), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

				//SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case BONEMANCER:
				if (!p.isUnlockedSkeletalSpells()){
					p.sendMessage("@red@<shad=0>You do not have Skeletal Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3153), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

				//SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case SHADOWFIEND:
				if (!p.isUnlockedDemonicSpells()){
					p.sendMessage("@red@<shad=0>You do not have Demonic Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3155), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;


			case DEVILSPAWN:
				if (!p.isUnlockedDemonicSpells()){
					p.sendMessage("@red@<shad=0>You do not have Demonic Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3156), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case ABYSSAL_TORMENTOR:
				if (!p.isUnlockedDemonicSpells()){
					p.sendMessage("@red@<shad=0>You do not have Demonic Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3158), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;


			case GRUNT_MAULER:
				if (!p.isUnlockedOgreSpells()){
					p.sendMessage("@red@<shad=0>You do not have Ogre Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3159), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case BRUTE_CRUSHER:
				if (!p.isUnlockedOgreSpells()){
					p.sendMessage("@red@<shad=0>You do not have Ogre Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
			/*	p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3160), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case VINESPLITTER:
				if (!p.isUnlockedOgreSpells()){
					p.sendMessage("@red@<shad=0>You do not have Ogre Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3161), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case PHANTOM_DRIFTER:
				if (!p.isUnlockedSpectralSpells()){
					p.sendMessage("@red@<shad=0>You do not have Spectral Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
			/*	p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3162), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case WHISPERING_WRAITH:
				if (!p.isUnlockedSpectralSpells()){
					p.sendMessage("@red@<shad=0>You do not have Spectral Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3163), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case BANSHEE_KING:
				if (!p.isUnlockedSpectralSpells()){
					p.sendMessage("@red@<shad=0>You do not have Spectral Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//GFX/ANIM
				/*p.performAnimation(new Animation(4410));
				p.performGraphic(new Graphic(726, GraphicHeight.HIGH));*/

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3164), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;

			case EYE_OF_BEYOND:
				if (!p.isUnlockedMasterSpells()){
					p.sendMessage("@red@<shad=0>You do not have Master Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3165), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;
				
			case TALONWING:
				if (!p.isUnlockedMasterSpells()){
					p.sendMessage("@red@<shad=0>You do not have Master Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3166), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;
			case UMBRAL_BEAST:
				if (!p.isUnlockedMasterSpells()){
					p.sendMessage("@red@<shad=0>You do not have Master Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3167), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;
			case SKELETAL_SERVANT:
				if (!p.isUnlockedSkeletalSpells()){
					p.sendMessage("@red@<shad=0>You do not have Skeletal Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3700), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;
			case DEMONIC_SERVANT:
				if (!p.isUnlockedDemonicSpells()){
					p.sendMessage("@red@<shad=0>You do not have Demonic Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3701), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

				//SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;
			case OGRE_SERVANT:
				if (!p.isUnlockedOgreSpells()){
					p.sendMessage("@red@<shad=0>You do not have Ogre Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3702), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;
			case SPECTRAL_SERVANT:
				if (!p.isUnlockedSpectralSpells()){
					p.sendMessage("@red@<shad=0>You do not have Spectral Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3703), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;
			case MASTER_SERVANT:
				if (!p.isUnlockedMasterSpells()){
					p.sendMessage("@red@<shad=0>You do not have Master Spells Unlocked!");
					return true;
				}
				if (p.getNecrotimeleft() > 0){
					p.sendMessage("You already have a follower summoned...");
					return true;
				}
				if(p.getSummoning().getFamiliar() != null && p.getSummoning().getFamiliar().isPet()) {
					p.getPacketSender().sendMessage("You already have a familiar.");
					return true;
				}

				//PET NAME
				p.getSummoning().summonPet(BossPets.BossPet.forSpawnId(3704), true);
				p.getPacketSender().sendMessage("You call upon the dead and Summon a follower...");

                //Sets Last Summoned
                p.setLastNecroSpell(buttonId);

                //SUMMON TIME
            modifier = p.getSkillManager().getCurrentLevel(Skill.NECROMANCY) / 10;
			p.getNecro().addTime(100 + (modifier * 30));
				p.getInventory().deleteItemSet(spell.getSpell().itemsRequired(p));
				p.getClickDelay().reset();
				break;
		}
		return true;
	}
}
