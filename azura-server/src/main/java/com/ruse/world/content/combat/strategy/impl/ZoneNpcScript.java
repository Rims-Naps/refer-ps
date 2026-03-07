
package com.ruse.world.content.combat.strategy.impl;

import com.ruse.model.Animation;
import com.ruse.model.CombatIcon;
import com.ruse.model.Hit;
import com.ruse.model.Hitmask;
import com.ruse.util.Misc;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class ZoneNpcScript implements CombatStrategy {
    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        Player p = (Player) victim;
        boolean inInstances = p.getPosition().getRegionId() == 9019;
        entity.performAnimation(new Animation(422));
        if (p.isAutoRetaliate() && !p.getCombatBuilder().isAttacking()){
            p.getCombatBuilder().attack(entity);
        }

            switch (((NPC) entity).getId()) {
                //EASY FIRE
                case NYTHOR:
                case IGNOX:
                case EMBER:
                case SCORCH:
                    fireAttack(p, (NPC) entity);
                    break;
                //EASY EARTH
                case TERRAN:
                case FERNA:
                case XERCES:
                    earthAttack(p, (NPC) entity);
                    break;
                //EASY WATER
                case AQUALORN:
                case CRYSTALIS:
                case MARINA:
                    waterAttack(p, (NPC) entity);
                    break;
                //MEDIUM FIRE
                case INFERNUS:
                case PYROX:
                case VOLCAR:
                    fireAttack2(p, (NPC) entity);
                    break;
                //MEDIUM EARTH
                case KEZEL:
                case TELLURION:
                case ASTARAN:
                    earthAttack2(p, (NPC) entity);
                    break;
                //MEDIUM WATER
                case HYDRORA:
                case MARINUS:
                case NEREUS:
                    waterAttack2(p, (NPC) entity);
                    break;

                //ELITE TIER/MASTER TIER
                case Lagoon:
                case Abyss:
                case Cerulean:
                case Nautilus:
                case Seabane:
                case Hydrox:
                    waterAttack3(p, (NPC) entity);
                    break;
                case Gemstone:
                    waterAttack5(p, (NPC) entity);
                    break;
                case Incendia:
                case Pyra:
                case Scorch:
                case Volcanus:
                case Scaldor:
                case Moltron:
                    fireAttack3(p, (NPC) entity);
                    break;
                case Terra:
                case Geode:
                case Geowind:
                case Goliath:
                case Quake:
                case Rumble:
                    earthAttack3(p, (NPC) entity);
                    break;
        }
        return true;
    }
    private void fireAttack(Player player, Character victim ) {
        int damage = Misc.random(0,2);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;
            if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
                victim.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                player.dealDamage(new Hit(inInstances ? damage / 4 : damage / 2, Hitmask.FIRE, CombatIcon.NONE));
            }
            if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
                victim.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                player.dealDamage(new Hit(inInstances ? damage / 4 : damage / 2, Hitmask.FIRE, CombatIcon.NONE));
            }
            if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
                player.dealDamage(new Hit(inInstances ? damage / 4 : damage, Hitmask.FIRE, CombatIcon.NONE));
            }
    }

    private void fireAttack2(Player player, Character victim ) {
        int damage = Misc.random(0,6);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;
            if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
                victim.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                player.dealDamage(new Hit(inInstances ? damage / 4 : damage / 2, Hitmask.FIRE, CombatIcon.NONE));
            }
            if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
                victim.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                player.dealDamage(new Hit(inInstances ? damage / 4 : damage / 2, Hitmask.FIRE, CombatIcon.NONE));
            }
            if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
                player.dealDamage(new Hit(inInstances ? damage / 4 : damage, Hitmask.FIRE, CombatIcon.NONE));
        }
    }

    private void fireAttack3(Player player, Character victim ) {
        int damage = Misc.random(0,9);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;

        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
            victim.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 4 : damage / 2, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            victim.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 4 : damage / 2, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            player.dealDamage(new Hit(inInstances ? damage/3 : damage, Hitmask.FIRE, CombatIcon.NONE));
        }
    }


    private void waterAttack(Player player, NPC boss) {
        int damage = Misc.random(0,2);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;

        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
                boss.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                player.dealDamage(new Hit(inInstances ? damage / 5 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
            }
            if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
                boss.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                player.dealDamage(new Hit(inInstances ? damage / 6 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
            }
            if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
                player.dealDamage(new Hit(inInstances ? damage / 4 : damage, Hitmask.WATER, CombatIcon.NONE));
        }
    }

    private void waterAttack2(Player player, NPC boss) {
        int damage = Misc.random(0,6);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;

        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 5 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 6 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            player.dealDamage(new Hit(inInstances ? damage / 4 : damage, Hitmask.WATER, CombatIcon.NONE));
        }
    }

    private void waterAttack3(Player player, NPC boss) {
        int damage = Misc.random(0,9);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;

        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 5 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 6 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            player.dealDamage(new Hit(inInstances ? damage/3 : damage, Hitmask.WATER, CombatIcon.NONE));
        }
    }

    private void waterAttack5(Player player, NPC boss) {
        int damage = Misc.random(5,15);
        boolean inInstances = player.getPosition().getRegionId() == 9019;

        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 5 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 6 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            player.dealDamage(new Hit(inInstances ? damage/3 : damage, Hitmask.WATER, CombatIcon.NONE));
        }
    }
    private void earthAttack(Player player, NPC boss) {
        int damage = Misc.random(0,2);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;

        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
                boss.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                player.dealDamage(new Hit(inInstances ? damage / 5 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
            }
            if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
                boss.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                player.dealDamage(new Hit(inInstances ? damage / 6 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
            }
            if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
                player.dealDamage(new Hit(inInstances ? damage / 4 : damage, Hitmask.EARTH, CombatIcon.NONE));
        }
    }

    private void earthAttack2(Player player, NPC boss) {
        int damage = Misc.random(0,6);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;

        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 5 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 6 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            player.dealDamage(new Hit(inInstances ? damage / 4 : damage, Hitmask.EARTH, CombatIcon.NONE));
        }
    }

    private void earthAttack3(Player player, NPC boss) {
        int damage = Misc.random(0,9);
        int accuracy = Misc.random(0,3);
        boolean inInstances = player.getPosition().getRegionId() == 9019;

        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 6), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 5 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            boss.dealDamage(new Hit(Misc.random(damage, damage * 12), Hitmask.CRITICAL, CombatIcon.DEFLECT));
            player.dealDamage(new Hit(inInstances ? damage / 6 : damage / 3, Hitmask.FIRE, CombatIcon.NONE));
        }
        if (!CurseHandler.isActivated(player, CurseHandler.EBBFLOW) && !CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            player.dealDamage(new Hit(inInstances ? damage/3 : damage, Hitmask.EARTH, CombatIcon.NONE));
        }
    }
    @Override
    public boolean canAttack(Character entity, Character victim) {return victim.isPlayer();}
    @Override
    public CombatContainer attack(Character entity, Character victim) {return null;}

    @Override
    public int attackDelay(Character entity) {return 7;}

    @Override
    public int attackDistance(Character entity) {return 1;}

    @Override
    public CombatType getCombatType() {return CombatType.MIXED;}

    //EASY ZONES
    public static final int NYTHOR = 13747;
    public static final int TERRAN = 1801;
    public static final int AQUALORN = 9027;
    public static final int FERNA = 1802;
    public static final int IGNOX = 13458;
    public static final int CRYSTALIS = 8006;
    public static final int EMBER = 688;
    public static final int XERCES = 350;
    public static final int MARINA = 182;
    public static final int SCORCH = 6330;

    //MEDIUM ZONES
    public static final int KEZEL = 9815;
    public static final int HYDRORA = 1741;
    public static final int INFERNUS = 12228;
    public static final int TELLURION = 9026;
    public static final int MARINUS = 1150;
    public static final int PYROX = 9837;
    public static final int ASTARAN = 9002;
    public static final int NEREUS = 7000;
    public static final int VOLCAR = 1821;


    //ELITE ZONES
    public static final int Lagoon = 1727;
    public static final int Incendia = 1729;
    public static final int Terra = 1730;
    public static final int Abyss = 1731;
    public static final int Pyra = 1735;
    public static final int Geode = 5539;
    public static final int Cerulean = 5547;
    public static final int Scorch = 5533;
    public static final int Geowind = 5553;

    //MASTER ZONES
    public static final int Goliath = 1072;
    public static final int Volcanus = 1073;
    public static final int Nautilus = 1074;
    public static final int Quake = 1075;
    public static final int Scaldor = 1076;
    public static final int Seabane = 1077;
    public static final int Rumble = 1078;
    public static final int Moltron = 1079;
    public static final int Hydrox = 1080;

    public static final int Gemstone = 2114;

}