package com.ruse.world.content.Fairies;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Companion {
    private final Player p;
    private NPC companion;
    private boolean hasCompanion;
    private myCompanion myCompanion;
    private CompanionType type;

    @Getter
    private static List<CompanionType> VALUES = Collections.unmodifiableList(Arrays.asList(CompanionType.values()));

    private Item treasure = new Item(995);
    public void heal() {
        if(hasCompanion) {
            if(companion != null) {
                companion.forceChat("Take some health.");
                p.heal(15);
                p.performGraphic(new Graphic(1738));
            }
        }
    }

    public void remove() {
        if(hasCompanion) {
            if(companion != null) {
                hasCompanion = false;
                World.deregister(companion);
            }
        }
    }

    public NPC getSummoned() {
        if(companion != null && hasCompanion) {
            return companion;
        } else {
            return null;
        }
    }

    public void findTreasure() {
        int coinAmount = Misc.random(1000,5000);
        if(hasCompanion) {
            if(companion != null) {
                if(type.canFindTreasure) {
                    if(Misc.random(7500) == 1) {
                        p.msgFancyPurp("Your Companion found you " + coinAmount + "X Coins while on your Journey!");
                        p.getInventory().add(995 ,coinAmount);
                    }
                }
            } else {
                return;
            }
        }
    }
    
    public void displayInterface() {
        if(myCompanion == null)
            return;
        AtomicInteger baseDmg = new AtomicInteger();
        AtomicInteger baseDr = new AtomicInteger();

        getVALUES().forEach(companionType -> {
            if(myCompanion.getCompanionId() == companionType.companionID) {
                baseDr.set(companionType.baseDr);;
                baseDmg.set(companionType.baseDmg);
            }
        });
        if(myCompanion.getCompanionLevel() == 5) {
            p.getPacketSender().sendProgressBar1(100, 31095);
        } else {
            VALUES.forEach(companionType -> {
                if(myCompanion.getCompanionId() == companionType.companionID) {
                    int experienceToLevel = companionType.expToLevel;
                    double percent = (((double) myCompanion.getTotalExperience() / (double) experienceToLevel) * 100);
                    if (percent > 100) {
                        percent = 100;
                    }
                    p.getPacketSender().sendProgressBar1((int) percent, 31095);
                }
            });
        }
        if(myCompanion.getAttachmentOne() == 6749) {
            baseDmg.set(baseDmg.get()+5);
        }
        if(myCompanion.getAttachmentThree() == 13151) {
            baseDr.set(baseDr.get()+5);
        }

        p.getPacketSender().sendString(31092, myCompanion.getCompanionName());
        p.getPacketSender().sendString(31093, "Dmg: " + baseDmg.get() + "%");
        p.getPacketSender().sendString(31094, "Dr: " + baseDr.get() + "%");
        p.getPacketSender().sendString(31096, "Lvl: " + myCompanion.getCompanionLevel());

        p.getPA().sendNpcOnInterface(31097, myCompanion.getCompanionId(), 600);

        //p.getPacketSender().sendNpcHeadOnInterface(myCompanion.getCompanionId(), 31097);
        p.getPacketSender().sendItemOnInterface(31102, myCompanion.getAttachmentOne(), 1);
        p.getPacketSender().sendItemOnInterface(31103, myCompanion.getAttachmentTwo(), 1);
        p.getPacketSender().sendItemOnInterface(31104, myCompanion.getAttachmentThree(), 1);
        p.getPacketSender().sendInterface(31090);
    }

    public boolean addAttachment(int itemId) {

        if(itemId == 6749) {
            if(myCompanion == null) {
                p.msgRed("You must have a Companion to use these.");
                return false;
            }
            if(myCompanion.getAttachmentOne() != 6749) {
                myCompanion.setAttachmentOne(6749);
                p.getInventory().delete(6749, 1);
            } else {
                p.msgRed("Your Companion already has the effect of this book.");
            }
            return true;
        }  else if(itemId == 19705) {
            if(myCompanion == null) {
                p.msgRed("You must have a Companion to use these.");
                return false;
            }
            if (myCompanion.getAttachmentTwo() != 19705) {
                myCompanion.setAttachmentTwo(19705);
                p.getInventory().delete(19705, 1);
            } else {
                p.msgRed("Your Companion already has the effect of this book.");
            }
            return true;
        } else if(itemId == 13151) {
            if(myCompanion == null) {
                p.msgRed("You must have a Companion to use these.");
                return false;
            }
            if(myCompanion.getAttachmentThree() != 13151) {
                myCompanion.setAttachmentThree(13151);
                p.getInventory().delete(13151, 1);
            } else {
                p.msgRed("Your Companion already has the effect of this book.");
            }
            return true;
        }
        return false;
    }

    public void reFollow() {
        if(hasCompanion) {
            if(companion != null) {
                if(!companion.getPosition().isWithinDistance(p.getPosition())) {
                    companion.setPosition(new Position(p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ()));
                    companion.performGraphic(new Graphic(1738));
                    companion.setPositionToFace(p.getPosition());
                    companion.setEntityInteraction(p);
                    companion.getMovementQueue().setFollowCharacter(p);
                }
            }
        }
    }

    public void teleToMe() {
        if(hasCompanion) {
            if(companion != null) {
                    companion.setPosition(new Position(p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ()));
                    companion.performGraphic(new Graphic(1738));
                    companion.setPositionToFace(p.getPosition());
                    companion.setEntityInteraction(p);
                    companion.getMovementQueue().setFollowCharacter(p);
            }
        }
    }

    public void receiveCompanion(int choice) {
        if(!hasCompanion) {
            if (choice == 1) {
                myCompanion = new myCompanion(CompanionType.FAIRY_1.companionID, 1, CompanionType.FAIRY_1.companionName, 0, -1, -1, -1);
                p.msgFancyPurp("You have unlocked the Companion " + myCompanion.getCompanionName());
                World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + " just selected their first companion! " + myCompanion.getCompanionName());;
                return;
            }
            if (choice == 2) {
                myCompanion = new myCompanion(CompanionType.IMP_1.companionID, 1, CompanionType.IMP_1.companionName, 0, -1, -1, -1);
                p.msgFancyPurp("You have unlocked the Companion " + myCompanion.getCompanionName());
                World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + " just selected their first companion! " + myCompanion.getCompanionName());;
                return;
            }
            if (choice == 3) {
                myCompanion = new myCompanion(CompanionType.BIRD_1.companionID, 1, CompanionType.BIRD_1.companionName, 0, -1, -1, -1);
                p.msgFancyPurp("You have unlocked the Companion " + myCompanion.getCompanionName());
                World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + " just selected their first companion! " + myCompanion.getCompanionName());;
                return;
            }

        }
    }
    public void getLevel() {
        p.msgFancyPurp("Your Companion level is: " + myCompanion.getCompanionLevel());
    }

    public void checkLevelUp() {
        if (p.levelupRunning){
            return;
        }
        if (myCompanion.getCompanionLevel() == 5){
            p.msgRed("Your pet cannot level up again...");
            return;
        }
        VALUES.forEach(companionType -> {
            if(myCompanion.getCompanionId() == companionType.companionID) {
                if(myCompanion.getTotalExperience() >= companionType.expToLevel) {
                    TaskManager.submit(new Task(1) {
                        int ticks = 0;
                        @Override
                        protected void execute() {
                            p.setLevelupRunning(true);
                            if(ticks == 0) {
                                companion.forceChat("What's happening??");
                            } else if(ticks == 3) {
                                companion.forceChat("I'm growing stronger!");
                            } else if(ticks == 5) {
                                if (type == CompanionType.FAIRY_1){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(2);
                                    myCompanion.setCompanionName(CompanionType.FAIRY_2.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.FAIRY_2.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.FAIRY_2.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.FAIRY_2){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(3);
                                    myCompanion.setCompanionName(CompanionType.FAIRY_3.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.FAIRY_3.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.FAIRY_3.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.FAIRY_3){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(4);
                                    myCompanion.setCompanionName(CompanionType.FAIRY_4.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.FAIRY_4.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.FAIRY_4.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.FAIRY_4){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(5);
                                    myCompanion.setCompanionName(CompanionType.FAIRY_5.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.FAIRY_5.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.FAIRY_5.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }

                                //WATER
                                if (type == CompanionType.IMP_1){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(2);
                                    myCompanion.setCompanionName(CompanionType.IMP_2.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.IMP_2.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.IMP_2.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.IMP_2){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(3);
                                    myCompanion.setCompanionName(CompanionType.IMP_3.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.IMP_3.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.IMP_3.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.IMP_3){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(4);
                                    myCompanion.setCompanionName(CompanionType.IMP_4.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.IMP_4.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.IMP_4.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.IMP_4){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(5);
                                    myCompanion.setCompanionName(CompanionType.IMP_5.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.IMP_5.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.IMP_5.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }


                                //FIRE
                                if (type == CompanionType.BIRD_1){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(2);
                                    myCompanion.setCompanionName(CompanionType.BIRD_2.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.BIRD_2.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.BIRD_2.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.BIRD_2){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(3);
                                    myCompanion.setCompanionName(CompanionType.BIRD_3.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.BIRD_3.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.BIRD_3.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.BIRD_3){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(4);
                                    myCompanion.setCompanionName(CompanionType.BIRD_4.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.BIRD_4.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.BIRD_4.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }
                                if (type == CompanionType.BIRD_4){
                                    World.deregister(companion);
                                    hasCompanion = false;
                                    myCompanion.setCompanionLevel(5);
                                    myCompanion.setCompanionName(CompanionType.BIRD_5.getCompanionName());
                                    myCompanion.setCompanionId(CompanionType.BIRD_5.companionID);
                                    myCompanion.setTotalExperience(0);
                                    spawn(CompanionType.BIRD_5.companionID);
                                    companion.forceChat("I feel even more powerful...");
                                    World.sendMessage("<shad=0><col=AF70C3>" + p.getUsername() + "'s Companion just Evolved to Level " + myCompanion.getCompanionLevel());
                                    stop();
                                    p.setLevelupRunning(false);
                                    return;
                                }

                            }
                            ticks++;
                        }
                    });
                }
            }
        });
    }

    public void addExp(int amount) {
        if (!hasCompanion) {
            p.msgRed("@red@Summon your Companion before doing this!");
            return;
        }

        if (hasCompanion) {
            if (companion != null) {
                if (myCompanion.getAttachmentTwo() == 19705) {
                    amount = amount * 2;
                }
                myCompanion.setTotalExperience(myCompanion.getTotalExperience() + amount);
                checkLevelUp();
            }
        }
    }

    public void spawn(int id) {
        if(getSummoned() != null) {
            p.msgRed("@red@You currently have a Companion summoned.");
            return;
        } else {
            VALUES.forEach(type_ -> {
                if(type_.getCompanionID() == id) {
                    companion = new NPC(type_.getCompanionID() , new Position(p.getPosition().getX(), p.getPosition().getY() + 2, p.getPosition().getZ()));
                    setCompanion(companion);
                    type = type_;
                }
            });
            TaskManager.submit(new Task(1) {
                int ticks = 0;
                @Override
                protected void execute() {
                    if(ticks == 1) {
                        hasCompanion = true;
                        World.register(getCompanion());
                        companion.performGraphic(new Graphic(1738));
                        companion.setPositionToFace(p.getPosition());
                        companion.setEntityInteraction(p);
                        companion.getMovementQueue().setFollowCharacter(p);
                        stop();

                    }
                    ticks++;
                }
            });
        }
    }

    public int baseDmgBoost() {
        int attachmentBoost = 0;
        if(type != null && companion != null){
            if(myCompanion.getAttachmentOne() == 6749) {
                attachmentBoost = 5;
            }
            return type.baseDmg + attachmentBoost;
        }
        else
            return 0;
    }

    public int baseDrBoost() {
        int attachmentBoost = 0;
        if(type != null && companion != null){
            if(myCompanion.getAttachmentThree() == 13151) {
                attachmentBoost = 5;
            }
            return type.baseDr + attachmentBoost;
        }
        else
            return 0;
    }

    @Getter
    public enum CompanionType {
        FAIRY_1(57, "Fairy(1)", 1, true, true, true, false, true, 500000, 1, 1),
        FAIRY_2(567, "Fairy(2)", 2, true, true, true, false, true, 2500000, 3, 3),
        FAIRY_3(6072, "Fairy(3)", 3, true, true, true, true, true, 5000000, 5, 5),
        FAIRY_4(4437, "Fairy(4)", 4, true, true, true, true, true, 10000000, 7, 7),
        FAIRY_5(4433, "Fairy(5)", 5, true, true, true, true, true, 20000000, 10, 10),

        IMP_1(6055, "Imp(1)", 1, true, true, true, false, true, 500000, 1, 1),
        IMP_2(6061, "Imp(2)", 2, true, true, true, false, true, 2500000, 3, 3),
        IMP_3(6063, "Imp(3)", 3, true, true, true, true, true, 5000000, 5, 5),
        IMP_4(6064, "Imp(4)", 4, true, true, true, true, true, 10000000, 7, 7),
        IMP_5(6059, "Imp(5)", 5, true, true, true, true, true, 20000000, 10, 10),

        BIRD_1(5072, "Bird(1)", 1, true, true, true, false, true, 500000, 1, 1),
        BIRD_2(5073, "Bird(2)", 2, true, true, true, false, true, 2500000, 3, 3),
        BIRD_3(5074, "Bird(3)", 3, true, true, true, true, true, 5000000, 5, 5),
        BIRD_4(5075, "Bird(4)", 4, true, true, true, true, true, 10000000, 7, 7),
        BIRD_5(5076, "Bird(5)", 5, true, true, true, true, true, 20000000, 10, 10);


        CompanionType(int companionID, String companionName, int tier, boolean canHeal, boolean canDmgBoost, boolean canDRBoost, boolean canFindTreasure, boolean canStatBoost, int expToLevel, int baseDmg, int baseDr) {
            this.companionID = companionID;
            this.companionName = companionName;
            this.tier = tier;
            this.canHeal = canHeal;
            this.canDmgBoost = canDmgBoost;
            this.canDRBoost = canDRBoost;
            this.canFindTreasure = canFindTreasure;
            this.canStatBoost = canStatBoost;
            this.expToLevel = expToLevel;
            this.baseDmg = baseDmg;
            this.baseDr = baseDr;
        }
        private int companionID;
        private String companionName;
        private int tier;
        private boolean canHeal;
        private boolean canDmgBoost;
        private boolean canDRBoost;
        private boolean canFindTreasure;
        private boolean canStatBoost;
        private int expToLevel;
        private int baseDmg;
        private int baseDr;
    }
}
