package com.ruse.world.content.tpinterface;

import com.ruse.GameSettings;
import com.ruse.donation.DonatorRanks;
import com.ruse.donation.ServerCampaignHandler;
import com.ruse.model.Item;
import com.ruse.model.PlayerRights;
import com.ruse.model.Position;
import com.ruse.model.Skill;
import com.ruse.model.definitions.NPCDrops;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.net.packet.impl.CommandPacketListener;
import com.ruse.world.content.KillsTracker;
import com.ruse.world.content.ZoneProgression.NpcRequirements;
import com.ruse.world.content.ZoneProgression.TierRequirements;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.Player;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class TPInterface {

    private final Player p;

    private TPTier selectedTier;

    private TPData selectedData;

    private int currentPage = 0;
    private List<TPData> displayedData = new ArrayList<>();

    private TPData lastTeleport;

    public void display() {
        selectTier(0, 0); // Pass the initial page number, 0
        currentPage = 0;
        //System.out.println("sending interface");
        p.getPA().sendInterface(6542);
    }



    private void selectTier(int index, int page) {
        int tiersPerPage = 8;
        int startingIndex = page * tiersPerPage;
        int endingIndex = startingIndex + tiersPerPage;

        if (index >= TPTier.values().length || startingIndex >= TPTier.values().length)
            return;
        TPTier tier = TPTier.values()[startingIndex + index];
        if (selectedTier == tier)
            return;
        selectedTier = tier;

        for (int i = 0; i < tiersPerPage; i++) {
            if (startingIndex + i >= TPTier.values().length)
                p.getPA().sendString(6568 + i, "");
            else
                p.getPA().sendString(6568 + i, TPTier.values()[startingIndex + i].getName());
        }
        p.getPA().sendString(6568 + selectedTier.ordinal() % tiersPerPage, "@or2@" + selectedTier.getName());

        displayedData = TPData.forTier(tier);
        selectData(0);
        String pageLabel = getPageLabel(page);
        p.getPA().sendString(6565, pageLabel);
        p.getPA().sendString(6545, pageLabel); // Update the "Paths" text with the current page label
    }


    private void selectData(int index) {
        if (index >= displayedData.size())
            return;
        TPData data = displayedData.get(index);
        if (selectedData == data)
            return;
        selectedData = data;
        for (int i = 0; i < 20; i++) {
            if (i >= displayedData.size())
                p.getPA().sendString(6601 + i, "");
            else
                p.getPA().sendString(6601 + i, displayedData.get(i).getName());
        }
        p.getPA().sendString(6601 + index, "@or2@" + selectedData.getName());




        NPCDrops drops = NPCDrops.forId(selectedData.getNpcId());
        if (drops != null) {
            CopyOnWriteArrayList<Item> item = new CopyOnWriteArrayList<>();
            for (NPCDrops.NpcDropItem dropItem : drops.getDropList())
                item.add(new Item(dropItem.getId(), dropItem.getCount()[0]));
            p.getPA().sendInterfaceItems(6549, item);
        }

        for (int i = 0; i < 3; i++) {
            if (selectedData.getInfo() == null || i >= selectedData.getInfo().length)
                p.getPA().sendString(6550 + i, "");
            else
                p.getPA().sendString(6550 + i, selectedData.getInfo()[i]);
        }
        p.getPA().sendNpcOnInterface(6547, selectedData.getNpcId(), selectedData.getModelZoom());
    }


    private String getPageLabel(int page) {
        // Add your custom page labels here
        String[] pageLabels = {"Zones", "Other"};
        if (page >= 0 && page < pageLabels.length) {
            return pageLabels[page];
        }
        return "Page " + (page + 1);
    }

    private void teleport(TPData data) {
        if (data == null || data.getPosition() == null)
            return;

        for (TPData tier2Data : TPData.forTier(TPTier.TIER_2)) {
            if (data == tier2Data && !p.unlockedIntermediateZones) {
                TierRequirements.checkTierRequirements(p, 2);
                return;
            }
        }
        for (TPData tier3Data : TPData.forTier(TPTier.TIER_3)) {
            if (data == tier3Data && !p.unlockedEliteZones) {
                TierRequirements.checkTierRequirements(p, 3);
                return;
            }
        }
        for (TPData tier4Data : TPData.forTier(TPTier.TIER_4)) {
            if (data == tier4Data && !p.unlockedMasterZones) {
                TierRequirements.checkTierRequirements(p, 4);
                return;
            }
        }
        for (TPData tier5Data : TPData.forTier(TPTier.TIER_5)) {
            if (data == tier5Data && !p.unlockedSpectralZones) {
                TierRequirements.checkTierRequirements(p, 5);
                return;
            }
        }
        if (data == TPData.SPECTRAL_RAID) {
            if (!p.isUnlockedSpectralZones()){
                p.msgRed("You must unlock Challenger Zones to access this Minigame.");
                return;
            }
            if (p.getCorruptHardCompletions() < 25){
                p.msgRed("You must Complete 25 Hard Corrupt Raids first. ");
                return;
            }
        }

            if (data == TPData.ARCHON) {
            if (p.getAmountDonated() <= DonatorRanks.ARCHON_AMOUNT) {
                p.msgRed("Reach Archon Rank($500) to access this zone.");
                return;
            }
        }

        if (data == TPData.CELESTIAL) {
            if (p.getAmountDonated() <= DonatorRanks.CELESTIAL_AMOUNT) {
                p.msgRed("Reach Celestial Rank($1000) to access this zone.");
                return;
            }
        }

        if (data == TPData.ASCENDENT) {
            if (p.getAmountDonated() <= DonatorRanks.ASCENDANT_AMOUNT) {
                p.msgRed("Reach Ascendant Rank($1500) to access this zone.");
                return;
            }
        }

        if (data == TPData.GLADIATOR) {
            if (p.getAmountDonated() <= DonatorRanks.GLADIATOR_AMOUNT) {
                p.msgRed("Welcome to the Gladiator Donator Zone.");
                return;
            }
        }




            if (data == TPData.TOWER_OF_ASCENSION) {
            boolean requirements = p.getRights().isManagement();
            if (!requirements) {
                boolean denied = false;
                if (p.getSkillManager().getMaxLevel(Skill.NECROMANCY) < 70) {
                    denied = true;
                }
                if (p.getSkillManager().getMaxLevel(Skill.SLAYER) < 70) {
                    denied = true;
                }
                if (KillsTracker.getTotalKills(p) < 10000) {
                    denied = true;
                }
                if (denied) {
                    p.sendMessage("You require level: 70 Necromancy, 70 Slayer, and 10,000 total kills to access the tower.");
                    return;
                }
            } else
                p.sendMessage("Requirements Bypassed!");
        }

        if (p.getRights() != PlayerRights.OWNER && p.getRights() != PlayerRights.CO_OWNER && p.getRights() != PlayerRights.YOUTUBER && p.getRights() != PlayerRights.MANAGER  ) {
            for (NpcRequirements req : NpcRequirements.values()) {
                if (data.getNpcId() == req.getNpcId()) {
                    if (req.getKillCount() > 0) {
                        if (p.getPointsHandler().getNPCKILLCount() < req.getKillCount()) {
                            p.sendMessage("@red@<shad=1>You need atleast " + req.getKillCount() + "@red@<shad=1>NPC kills to teleport here. (@red@<shad=1>" + p.getPointsHandler().getNPCKILLCount() + "/"
                                    + req.getKillCount() + ")");
                            return;
                        }
                    } else {
                        int npc = req.getRequireNpcId();
                        int total = KillsTracker.getTotalKillsForNpc(npc, p);
                        if (total < req.getAmountRequired()) {
                            p.sendMessage("@red@<shad=1>You need atleast " + req.getAmountRequired() + " "
                                    + NpcDefinition.forId(npc).getName() + "@red@<shad=1> kills to teleport here. (" + total + "@red@<shad=1>/"
                                    + req.getAmountRequired() + "@red@<shad=1>)");
                            return;
                        }
                    }
                    break;
                }
            }
        }

        if (p.getRights() == PlayerRights.OWNER|| p.getRights() == PlayerRights.MANAGER || p.getRights() == PlayerRights.CO_OWNER || p.getRights() == PlayerRights.YOUTUBER ) {
            p.sendMessage("<col=AF70C3><shad=0>You have bypassed requirements with rights: @red@<shad=0>" + p.getRights());
        }
        lastTeleport = data;
        p.getPA().sendInterfaceRemoval();
        TeleportHandler.teleportPlayer(p, data.getPosition(), p.getSpellbook().getTeleportType());
    }



    public boolean handleButton(int id) {
        if (currentPage == 0) {// ZONES PAGE
            if (id == 6573 || id == 6574 || id == 6575) {
                p.sendMessage("@bla@<shad=9B0CFF>Coming Soon.");
                return true;
            }
        }

        if (currentPage == 1) {// OTHER PAGE
            if (id == 6572 || id == 6573 || id == 6574) {
                p.sendMessage("@bla@<shad=9B0CFF>Coming Soon.");
                return true;
            }
        }

        if (currentPage == 1) {
            if (id == 6605){
                p.getPA().sendString(6551, "Every " + CommandPacketListener.voteCount + "/50");
            }
            if (id == 6606){
                p.getPA().sendString(6551, "Every " + ServerCampaignHandler.getCurrentDonoBossVal() + "/500");
            }
            if (id == 6607){
                p.getPA().sendString(6551, "Every " + GameSettings.KINGS_BONES + "/25");
            }
            if (id == 6608){
                p.getPA().sendString(6551, "Every " + GameSettings.EMERALD_CHAMP_AMOUNT + "/5000");
            }
        }




            if (id == 6676) {
            if (currentPage - 1 >= 0) {
                currentPage -= 1;
            } else {
                currentPage = (int) Math.ceil((double) TPTier.values().length / 6) - 1; // Set to the last page
            }
            selectTier(selectedTier.ordinal() % 6, currentPage);
            return true;
        }
        if (id == 6673) {
            int maxPages = (int) Math.ceil((double) TPTier.values().length / 6);
            if (currentPage + 1 < maxPages) {
                currentPage += 1;
            } else {
                currentPage = 0; // Set to the first page
            }
            selectTier(selectedTier.ordinal() % 6, currentPage);
            return true;
        }

        if (id >= 6568 && id <= 6573) {
            int index = id - 6568;
            selectTier(index, currentPage);
            return true;
        }
        if (id >= 6601 && id <= 6620) {
            int index = id-6601;
            selectData(index);
            NpcRequirements[] requirements = NpcRequirements.values();
            for (int i = 0; i < requirements.length - 1; i++) {
                if (selectedData.getNpcId() == requirements[i].getNpcId() && currentPage == 0) {
                    NpcRequirements nextRequirement = requirements[i + 1];
                    int totalKills = KillsTracker.getTotalKillsForNpc(selectedData.getNpcId(), p);
                    String killsString = totalKills >= 1000 ? (totalKills / 1000) + "K" : String.valueOf(totalKills);
                    String requiredString = nextRequirement.getAmountRequired() >= 1000 ? (nextRequirement.getAmountRequired() / 1000) + "K" : String.valueOf(nextRequirement.getAmountRequired());
                    p.getPA().sendString(6552, "Kills: " + killsString + "/" + requiredString);
                    break;
                }
            }

            return true;
        }

        switch (id) {
            case 6553:
                teleport(selectedData);
                return true;
            case 6557:
                teleport(lastTeleport);
                return true;
        }
        return false;
    }

}
