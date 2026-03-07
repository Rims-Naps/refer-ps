package com.ruse.world.content.parties;

import com.ruse.model.Locations;
import com.ruse.world.content.combat.Maxhits;
import com.ruse.world.content.tower.TowerController;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.*;

public class PartyInterface {
    public static final int CURRENT_PARTIES_INTERFACE = 48500;

    public static final int VIEWING_PARTIES_INTERFACE = 48800;
    public static final int CURRENT_PARTIES_REFRESH = -16902;
    public static final int CURRENT_PARTIES_MAKE_PARTY = -16901;
    public static final int VIEWING_PARTIES_BACK = -16604;
    public static final int VIEWING_PARTIES_REFRESH = -16603;
    public static final int VIEWING_PARTIES_DISBAND = -16602; //Also referenced as Leave party(Member), Disband(Party owner), Join party(Guest viewing the party)

    public static List<Party> parties_in_view = new ArrayList<>();

    public static void openCurrentParties(Player player, PartyType type) {
        clearCurrentPartiesInterface(player);
        populateCurrentPartiesInterface(player);
        player.getPacketSender().sendInterface(CURRENT_PARTIES_INTERFACE);
    }

    public static void clearCurrentPartiesInterface(Player player) {
        parties_in_view.clear();
        int counter = 0;
        for (int i = 48534; i < 48634; i++) {
            player.getPacketSender().sendString(i, "");
        }
    }

    public static void populateCurrentPartiesInterface(Player player) {
        if (PartyService.totalPartiesRunning() == 0)
            return;
        int offset = 0;
        for (int i = 0; i < PartyService.parties.size(); i++) {
            Party party = PartyService.parties.get(i);
            if (!party.getOwner().getLocation().equals(Locations.Location.TOWER_LOBBY))//Stops parties who's owner is not in the lobby from showing.
                continue;
            if (party.getPlayers().size() == 0) //Stops parties that didn't debind properly from showing
                continue;
            player.getPacketSender().sendString((48534 + offset), "" + party.getOwner().getUsername());
            player.getPacketSender().sendString((48535 + offset), party.getPlayers().size() + " / 5");
            player.getPacketSender().sendString((48536 + offset), party.getOwner().getTotal_tower_completions());
            player.getPacketSender().sendString((48537 + offset), party.getOwner().getBest_tower_wave());
            offset += 4;
            parties_in_view.add(party);
        }
    }

    public static void clear_viewing_parties_members(Player player) {
        int counter = 0;
        for (int i = 48811; i < 48846; i++) {
            if (counter == 0) {
                counter++;
                continue;
            }
            counter++;
            if (counter == 7) {
                counter = 0;
            }
            if (counter == 1)
                player.getPacketSender().sendString(i, "  -");
            else
                player.getPacketSender().sendString(i, "-");
        }
    }

    public static void clear_viewing_parties_applicants(Player player) {
        int counter = 0;
        for (int i = 48847; i < 48931; i++) {
            if (counter == 0) {
                counter++;
                continue;
            }
            counter++;
            if (counter == 7) {
                counter = 0;
            }
            player.getPacketSender().sendString(i, "");
        }
    }

    public static void send_members_list(Player player) {
        int counter = 0;
        Party party = PartyService.getPartyForPlayer(player);
        if (party == null) {
            return;
        }
        for (Player team : party.getPlayers()) {
            player.getPacketSender().sendString(48812 + (counter * 7), team.getUsername());
            player.getPacketSender().sendString(48813 + (counter * 7), team.getSkillManager().getCombatLevel());
            player.getPacketSender().sendString(48814 + (counter * 7), Maxhits.melee(team, team));
            player.getPacketSender().sendString(48815 + (counter * 7), Maxhits.magic(team, team));
            player.getPacketSender().sendString(48816 + (counter * 7), Maxhits.ranged(team, team));
            player.getPacketSender().sendString(48817 + (counter * 7), team.getBest_tower_wave());
            counter++;
        }
    }

    public static void refresh_applicants_list(Player player) {
        int counter = 0;
        Party party = PartyService.getPartyForPlayer(player);
        if (party == null) {
            return;
        }
        for (Player team : party.getApplicants()) {
            player.getPacketSender().sendString(48848 + (counter * 7), team.getUsername());
            player.getPacketSender().sendString(48849 + (counter * 7), team.getSkillManager().getCombatLevel());
            player.getPacketSender().sendString(48850 + (counter * 7), Maxhits.melee(team, team));
            player.getPacketSender().sendString(48851 + (counter * 7), Maxhits.magic(team, team));
            player.getPacketSender().sendString(48852 + (counter * 7), Maxhits.ranged(team, team));
            player.getPacketSender().sendString(48853 + (counter * 7), team.getBest_tower_wave());
            counter++;
        }
    }

    public static void send_members_list(Player player, Party partyToView) {
        int counter = 0;
        Party party = PartyService.getPartyForPlayer(partyToView.getOwner());
        if (party == null) {
            return;
        }
        for (Player team : party.getPlayers()) {
            player.getPacketSender().sendString(48812 + (counter * 7), team.getUsername());
            player.getPacketSender().sendString(48813 + (counter * 7), team.getSkillManager().getCombatLevel());
            player.getPacketSender().sendString(48814 + (counter * 7), Maxhits.melee(team, team));
            player.getPacketSender().sendString(48815 + (counter * 7), Maxhits.magic(team, team));
            player.getPacketSender().sendString(48816 + (counter * 7), Maxhits.ranged(team, team));
            player.getPacketSender().sendString(48817 + (counter * 7), team.getBest_tower_wave());
            counter++;
        }
    }

    public static void openViewingParties(Player player) {
        player.getPacketSender().sendString(48802, player.getTowerParty().getOwner().getUsername() + "'s Party");
        clear_viewing_parties_members(player);
        clear_viewing_parties_applicants(player);
        send_members_list(player);
        player.getPacketSender().sendInterface(VIEWING_PARTIES_INTERFACE);
    }

    public static boolean isKickButton(int id) {
        return id == -16725 || id == -16718 || id == -16711 || id == -16704 || id == -16697;
    }

    private static final Set<Integer> ALLOWED_IDS_1_PLAYER = new HashSet<>(Arrays.asList(-16725));
    private static final Set<Integer> ALLOWED_IDS_2_PLAYERS = new HashSet<>(Arrays.asList(-16725, -16718));
    private static final Set<Integer> ALLOWED_IDS_3_PLAYERS = new HashSet<>(Arrays.asList(-16725, -16718, -16711));
    private static final Set<Integer> ALLOWED_IDS_4_PLAYERS = new HashSet<>(Arrays.asList(-16725, -16718, -16711, -16704));
    private static final Set<Integer> ALLOWED_IDS_5_PLAYERS = new HashSet<>(Arrays.asList(-16725, -16718, -16711, -16704, -16697));

    public static boolean isRequiredSizeForButton(Player player, int id) {
        int size = player.getTowerParty().getPlayers().size();

        switch (size) {
            case 1:
                return ALLOWED_IDS_1_PLAYER.contains(id);
            case 2:
                return ALLOWED_IDS_2_PLAYERS.contains(id);
            case 3:
                return ALLOWED_IDS_3_PLAYERS.contains(id);
            case 4:
                return ALLOWED_IDS_4_PLAYERS.contains(id);
            case 5:
                return ALLOWED_IDS_5_PLAYERS.contains(id);
            default:
                return false;
        }
    }

    public static int getSlotForButton(int id) {
        switch (id) {
            case -16718:
                return 1;
            case -16711:
                return 2;
            case -16704:
                return 3;
            case -16697:
                return 4;
            default:
                return 0;
        }
    }

    public static void kickMember(Player player, int id) {

        if (player.getTowerParty().getOwner() == player) {
            if (!isRequiredSizeForButton(player, id))
                return;
            Player playerToKick = player.getTowerParty().getPlayers().get(getSlotForButton(id));
            if (PartyService.isPlayerPartyOwner(playerToKick)) {
                player.sendMessage("You cannot kick yourself. Please disband the party");
                return;
            }
            player.getTowerParty().kickMember(player, playerToKick);
        }
    }

    public static boolean is_required_size_for_viewing_party(int id) {
        int size = parties_in_view.size() - 1;

        for (CurrentBindings binding : CurrentBindings.values()) {
            if (binding.ordinal() <= size && binding.interfaceId == id) {
                return true;
            }
        }

        return false;
    }

    public static boolean is_view_party_button(int id) {
        for (CurrentBindings binding : CurrentBindings.values()) {
            if (binding.interfaceId == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean is_required_size_for_applicants(Player player, int id) {
        int size = player.getTowerParty().getApplicants().size() - 1;

        for (ApplicantBindings binding : ApplicantBindings.values()) {
            if (binding.ordinal() <= size && binding.interfaceId == id) {
                return true;
            }
        }

        return false;
    }

    public static boolean is_applicants_button(int id) {
        for (ApplicantBindings binding : ApplicantBindings.values()) {
            if (binding.interfaceId == id) {
                return true;
            }
        }
        return false;
    }


    public static void view_party(Player player, int id) {
        if (is_required_size_for_viewing_party(id)) {
            int slot = -1;
            for (CurrentBindings binding : CurrentBindings.values()) {
                if (binding.interfaceId == id) {
                    slot = binding.ordinal();
                }
            }
            if (slot == -1)
                return;
            Party partyToView = parties_in_view.get(slot);
            player.setViewingParty(partyToView);
            player.getPacketSender().sendString(48802, partyToView.getOwner().getUsername() + "'s Party");
            clear_viewing_parties_members(player);
            clear_viewing_parties_applicants(player);
            send_members_list(player, partyToView);
            player.getPacketSender().sendInterface(VIEWING_PARTIES_INTERFACE);
            if (PartyService.playerIsInParty(player) && !PartyService.isPlayerPartyOwner(player)) {
                player.getPacketSender().sendString(48934, "Leave Party");
            } else if (PartyService.isPlayerPartyOwner(player)) {
                player.getPacketSender().sendString(48934, "Disband Party");
            } else {
                player.getPacketSender().sendString(48934, "Apply to Party");
            }
        }
    }

    public static void accept_applicants(Player player, int id) {
        if (is_required_size_for_applicants(player, id)) {
            if (!PartyService.isPlayerPartyOwner(player))
                return;
            int slot = -1;
            for (ApplicantBindings binding : ApplicantBindings.values()) {
                if (binding.interfaceId == id) {
                    slot = binding.ordinal();
                }
            }
            if (slot == -1)
                return;
            if (PartyService.playerIsInParty(player.getTowerParty().getApplicants().get(slot))) {
                return;
            }
            player.getTowerParty().acceptInvite(slot);
            player.sendMessage("You have accepted the request to join");

        }
    }

    public static void send_invite(Player player, int id) {
        player.getViewingParty().addInvite(player);
        player.sendMessage("You have applied to join the party!");
    }

    private enum CurrentBindings {
        SLOT_0(-17027),
        SLOT_1(-17026),
        SLOT_2(-17025),
        SLOT_3(-17024),
        SLOT_4(-17023),
        SLOT_5(-17022),
        SLOT_6(-17021),
        SLOT_7(-17020),
        SLOT_8(-17019),
        SLOT_9(-17018),
        SLOT_10(-17017),
        SLOT_11(-17016),
        SLOT_12(-17015),
        SLOT_13(-17014),
        SLOT_14(-17013),
        SLOT_15(-17012),
        SLOT_16(-17011),
        SLOT_17(-17010),
        SLOT_18(-17009),
        SLOT_19(-17008),
        SLOT_20(-17007),
        SLOT_21(-17006),
        SLOT_22(-17005),
        SLOT_23(-17004),
        SLOT_24(-17003);

        @Getter
        private int interfaceId;


        CurrentBindings(int id) {
            this.interfaceId = id;
        }
    }

    private enum ApplicantBindings {
        SLOT_0(-16689),
        SLOT_1(-16682),
        SLOT_2(-16675),
        SLOT_3(-16668),
        SLOT_4(-16661),
        SLOT_5(-16654),
        SLOT_6(-16647),
        SLOT_7(-16640),
        SLOT_8(-16633),
        SLOT_9(-16626),
        SLOT_10(-16619),
        SLOT_11(-16612),;

        @Getter
        private int interfaceId;


        ApplicantBindings(int id) {
            this.interfaceId = id;
        }
    }
}
