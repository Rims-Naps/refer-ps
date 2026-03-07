//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ruse.world.content.new_raids_system.raids_system;

import com.ruse.GameSettings;
import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.model.Locations.Location;
import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.content.new_raids_system.InterfaceConstants;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.entity.impl.player.Player;
import java.util.Iterator;

public class Raids {
    private Player player;
    private RaidsParty party;

    public Raids(Player player) {
        this.player = player;
    }

    public void processParty() {
        if (this.party != null) {
            this.party.getPlayers().size();
            int memberIndex = 0;

            for(Iterator var3 = this.party.getPlayers().iterator(); var3.hasNext(); ++memberIndex) {
                Player partyMember = (Player)var3.next();
                boolean kickMember = false;
                if (partyMember == null) {
                    kickMember = true;
                } else if (partyMember.getLocation() == Location.CORRUPT_RAID_LOBBY
                        || partyMember.getLocation() != Locations.Location.CORRUPT_RAID_ROOM_1
                        || partyMember.getLocation() != Locations.Location.CORRUPT_RAID_ROOM_2
                        || partyMember.getLocation() != Locations.Location.CORRUPT_RAID_ROOM_3

                        || partyMember.getLocation() != Locations.Location.VOID_RAID_LOBBY
                        || partyMember.getLocation() != Locations.Location.VOID_RAID_ROOM_1
                        || partyMember.getLocation() != Locations.Location.VOID_RAID_ROOM_2
                        || partyMember.getLocation() != Locations.Location.VOID_RAID_ROOM_3
                ){
                    if (!partyMember.isRegistered()) {
                        kickMember = true;
                    }
                } else {
                    kickMember = true;
                }

                if (kickMember) {
                    this.forceOut(memberIndex);
                }
            }

        }
    }


    public void attemptKick(final Player partyMember) {
        if (partyMember != null) {
            if (partyMember.getName().equalsIgnoreCase(this.player.getName())) {
                this.player.sendMessage("@red@You can't kick yourself. Leave the party instead!");
            } else {
                DialogueManager.start(this.player, new Dialogue() {
                    public DialogueType type() {
                        return DialogueType.OPTION;
                    }

                    public String[] dialogue() {
                        return new String[]{"Kick " + partyMember.getName(), "Cancel"};
                    }

                    public boolean action(int option) {
                        Raids.this.player.getPA().closeDialogueOnly();
                        switch (option) {
                            case 1:
                                partyMember.getRaidsOne().getRaidsConnector().leave(Raids.this.player.getLocation() == Location.CORRUPT_RAID_ROOM_1);
                            default:
                                return false;
                        }
                    }
                });
            }
        }
    }

    public void leave(boolean teleport) {
        if (this.party != null) {
            if (teleport) {
                TeleportHandler.teleportPlayer(this.player, new Position(2975,3878, 0), player.getSpellbook().getTeleportType());
            }

            this.player.getPacketSender().sendCameraNeutrality();
            this.player.getMovementQueue().setLockMovement(false);
            this.player.getPacketSender().sendTabInterface(15, 26601);
            this.player.getPacketSender().sendDungeoneeringTabIcon(false);
            this.player.getPacketSender().sendTab(15);
            this.refreshAll();
        }
    }

    public void leaveVoid(boolean teleport) {// TODO LEAVE VOID
        if (this.party != null) {
            if (teleport) {
                TeleportHandler.teleportPlayer(this.player, new Position(2521, 4833, 0), player.getSpellbook().getTeleportType());
            }

            this.player.getPacketSender().sendCameraNeutrality();
            this.player.getMovementQueue().setLockMovement(false);
            this.player.getPacketSender().sendTabInterface(15, 26601);
            this.player.getPacketSender().sendDungeoneeringTabIcon(false);
            this.player.getPacketSender().sendTab(15);
            this.refreshAll();
        }
    }



    ////
    public void leaveVoidEntrance() {// TODO LEAVE VOID
        if (this.player.getLocation() == Location.VOID_RAID_LOBBY) {
            this.player.getPacketSender().sendCameraNeutrality();
            this.player.getMovementQueue().setLockMovement(false);
            player.getPacketSender().sendTabInterface(GameSettings.STAFF_TAB, 46343).sendTab(GameSettings.STAFF_TAB);
            this.player.getPacketSender().sendDungeoneeringTabIcon(false);
            this.player.getPacketSender().sendTab(15);
            this.player.getEquipment().refreshItems();
            this.refreshAll();
        }
    }


    ////
    public void leaveRaidsOneEntrance() {
        if (this.player.getLocation() == Location.CORRUPT_RAID_LOBBY) {
            this.player.getPacketSender().sendCameraNeutrality();
            this.player.getMovementQueue().setLockMovement(false);
            player.getPacketSender().sendTabInterface(GameSettings.STAFF_TAB, 46343).sendTab(GameSettings.STAFF_TAB);
            this.player.getPacketSender().sendDungeoneeringTabIcon(false);
            this.player.getPacketSender().sendTab(15);
            this.player.getEquipment().refreshItems();
            this.refreshAll();
        }

    }



    ////
    public void leaveWithMoveVoidRaid() {
        if (this.player.getLocation() == Location.VOID_RAID_LOBBY) {
            this.player.getPacketSender().sendCameraNeutrality();
            this.player.getMovementQueue().setLockMovement(false);
            this.player.getPacketSender().sendDungeoneeringTabIcon(false);
            this.player.getPacketSender().sendTab(15);
            player.getPacketSender().sendTabInterface(GameSettings.STAFF_TAB, 46343).sendTab(GameSettings.STAFF_TAB);
            this.player.moveTo(2521, 4833,0);
            this.player.getEquipment().refreshItems();
            this.refreshAll();
        }
    }

    public void leaveCorruptRaid() {
        if (this.player.getLocation() == Location.CORRUPT_RAID_LOBBY) {
            this.player.getPacketSender().sendCameraNeutrality();
            this.player.getMovementQueue().setLockMovement(false);
            this.player.getPacketSender().sendDungeoneeringTabIcon(false);
            this.player.getPacketSender().sendTab(15);
            player.getPacketSender().sendTabInterface(GameSettings.STAFF_TAB, 46343).sendTab(GameSettings.STAFF_TAB);
            this.player.moveTo(2975,3878,0);
            this.player.getEquipment().refreshItems();
            this.refreshAll();
        }
    }
    ////


    public void forceOut(int memberIndex) {
        if (this.party != null) {
            if (this.party.getPlayers() != null) {
                this.party.getPlayers().remove(memberIndex);
                if (this.party.getPlayers().size() == 0) {
                    return;
                }
            }

            this.refreshAll();
        }
    }

    public void enter(Player player) {
        this.displayParty();
    }

    public void displayParty() {
        this.player.getPacketSender().sendTabInterface(15, InterfaceConstants.TAB_INTERFACE_ID);
        this.player.getPacketSender().sendDungeoneeringTabIcon(false);
        this.player.getPacketSender().sendTab(15);
        this.displayMembers();
    }

    public void displayMembers() {
        this.player.getPacketSender().sendString(58002, "@whi@Athens Raid Party: " + (this.party == null ? "0" : this.party.getPlayers().size()));
        int id = 58017;

        for(int i = 0; i < 12; ++i) {
            Player partyMember = this.party != null && this.party.getPlayers().size() > i ? (Player)this.party.getPlayers().get(i) : null;
            this.player.getPacketSender().sendString(id++, partyMember == null ? "" : partyMember.getName());
            ++id;
        }

    }

    public boolean isButton(int button) {
        if (!this.inArea()) {
            return false;
        } else if (button >= 19480 && button <= 19524) {
            if (this.party == null) {
                return true;
            } else {
                int memberId = (19524 - button) / 4;
                Player partyMember = this.party.getPlayers().size() > memberId ? (Player)this.party.getPlayers().get(memberId) : null;
                if (partyMember != null) {
                    this.attemptKick(partyMember);
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public void attemptInvite(final Player toInvite) {
        DialogueManager.start(this.player, new Dialogue() {
            public DialogueType type() {
                return DialogueType.OPTION;
            }

            public String[] dialogue() {
                return new String[]{"Invite " + toInvite.getName(), "Cancel"};
            }

            public boolean action(int option) {
                Raids.this.player.getPA().closeDialogueOnly();
                switch (option) {
                    case 1:
                        Raids.this.invite(toInvite);
                        Raids.this.player.getPacketSender().sendInterfaceRemoval();
                        break;
                    case 2:
                        Raids.this.player.getPacketSender().sendInterfaceRemoval();
                }

                return false;
            }
        });
    }

    public void invite(final Player toInvite) {
        if (toInvite == null) {
            this.player.sendMessage("@red@You have attempted to invite an invalid player.");
        } else if (toInvite.getRaidsOne().getRaidsConnector().inEntrance() || toInvite.getRaidsOne().getRaidsConnector().inEntrance() ) {
            DialogueManager.start(toInvite, new Dialogue() {
                public DialogueType type() {
                    return DialogueType.OPTION;
                }

                public String[] dialogue() {
                    return new String[]{"Join " + Raids.this.player.getName() + "'s Raid Party", "Cancel"};
                }

                public boolean action(int option) {
                    toInvite.getPA().closeDialogueOnly();
                    Raids.this.player.getPacketSender().sendInterfaceRemoval();
                    switch (option) {
                        case 1:
                            if (Raids.this.player == null) {
                                toInvite.sendMessage("@red@The party leader that invited you has now logged out.");
                                return false;
                            }

                            toInvite.getRaidsPartyConnector().getRaidsPartyConnector().invite(Raids.this.player);
                            Raids.this.player.getPacketSender().sendInterfaceRemoval();
                            break;
                        case 2:
                            Raids.this.player.getPacketSender().sendInterfaceRemoval();
                    }

                    return false;
                }
            });
        } else {
            this.player.sendMessage(toInvite.getName() + " isn't at the .");
        }
    }

    public void refreshAll() {
        if (this.party != null) {
            Iterator var2 = this.party.getPlayers().iterator();

            while(var2.hasNext()) {
                Player partyMember = (Player)var2.next();
                if (partyMember != null) {
                    partyMember.getRaidsOne().getRaidsConnector().displayMembers();
                }
            }
        }

    }


    //CORRUPT RAIDS
    public boolean inEntrance() {
        return this.player.getLocation() == Location.CORRUPT_RAID_LOBBY;
    }
    public boolean inCorruptPhaseOne() {
        return this.player.getLocation() == Location.CORRUPT_RAID_ROOM_1;
    }
    public boolean inCorruptPhaseTwo() {return this.player.getLocation() == Location.CORRUPT_RAID_ROOM_2;}
    public boolean inCorruptPhaseThree() {
        return this.player.getLocation() == Location.CORRUPT_RAID_ROOM_3;
    }


    //VOID RAIDS
    public boolean inVoidEntrance() {
        return this.player.getLocation() == Location.VOID_RAID_LOBBY;
    }
    public boolean inVoidPhaseOne() {
        return this.player.getLocation() == Location.VOID_RAID_ROOM_1;
    }
    public boolean inVoidPhaseTwo() {
        return this.player.getLocation() == Location.VOID_RAID_ROOM_2;
    }
    public boolean inVoidPhaseThree() {
        return this.player.getLocation() == Location.VOID_RAID_ROOM_3;
    }



    public boolean inArea() {
        return this.inEntrance() || this.inCorruptPhaseOne() || this.inCorruptPhaseTwo() || this.inCorruptPhaseThree()
            || this.inVoidEntrance() || this.inVoidPhaseOne() || this.inVoidPhaseTwo() || this.inVoidPhaseThree();
    }
}
