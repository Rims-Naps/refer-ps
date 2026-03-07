package com.ruse.world.entity.impl.player;

import com.ruse.GameServer;
import com.ruse.GameSettings;
import com.ruse.donation.Membership;
import com.ruse.donation.PersonalCampaignHandler;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.*;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.definitions.WeaponAnimations;
import com.ruse.model.definitions.WeaponInterfaces;
import com.ruse.model.input.impl.EnterPinPacketListener;
import com.ruse.net.PlayerSession;
import com.ruse.net.SessionState;
import com.ruse.net.security.ConnectionHandler;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.KillsTracker.KillsEntry;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.content.combat.effect.BurnEffect;
import com.ruse.world.content.combat.magic.Autocasting;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.range.DwarfMultiCannon;
import com.ruse.world.content.combat.weapon.CombatSpecial;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.minigames.impl.Barrows;
import com.ruse.world.content.minigames.impl.NecromancyMinigame;
import com.ruse.world.content.minigames.impl.CircleOfElements;
import com.ruse.world.content.skill.impl.hunter.Hunter;
import com.ruse.world.content.skill.impl.slayer.Slayer;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.content.watchlist.WatchListManager;
import com.ruse.world.entity.impl.GlobalItemSpawner;
import com.ruse.world.instance.MapInstance;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Duration;
import java.time.LocalDateTime;

public class PlayerHandler {

    public static void handleLogin(Player player) {
        World.playerMap().put(player.getLongUsername(), player);

        // Register the player
        System.out.println("REGISTERED [" + player.getUsername() + ", " + player.getHostAddress() + "]");
       DiscordManager.sendMessage("USERNAME "+player.getUsername()+" Logged in from IP: "+player.getHostAddress() +" MAC: " + player.getSerialNumber(), Channels.LOGINS);
        if (WatchListManager.beingWatched(player.getUsername())) {
            DiscordManager.sendMessage("USERNAME: " + player.getUsername() + " Logged in from IP: " + player.getHostAddress() + " MAC: " + player.getSerialNumber(), Channels.WATCHLIST_LOGINS);
        }
        OfflineStorageSystem.checkStorageOnLogin(player);
        //player.initializeBingoHandler();
        PlayerLogs.logPlayerLoginWithIP(player.getUsername(), "Login from host " + player.getHostAddress() + ", serial number: " + player.getSerialNumber() + ", mac address:" + player.getMac());
        PlayerLogs.logPlayerLogin(player.getUsername(), "Login ");
        player.getControllerManager().login();
        player.getPlayerOwnedShopManager().hookShop();
        ConnectionHandler.add(player.getHostAddress());
        World.addPlayer(player);
        World.updatePlayersOnline();
        player.executeProcessCallables();
        player.getSession().setState(SessionState.LOGGED_IN);

        // Packets
        player.getPacketSender().sendDetails();
        player.loadMap(true);
        player.getRecordedLogin().reset();

        //Mbox spinner
        player.getNewSpinner().clearMysteryBox();
        player.getPlayerShops().onPlayerLogin();

        // Tabs
        player.getPacketSender().sendTabs();
        player.getPacketSender().sendClientSettings();
        player.getStaffHelpDesk().refreshTickets();

      /*  if (player.getSlayer().getSlayerMaster() == SlayerMaster.BEAST_MASTER){
            player.getSlayer().resetSlayerTask();
        }*/
        if (PlayerPunishment.Jail.isJailed(player.getUsername())) {
            player.moveTo(new Position(2507, 9324));
            player.setLocation(Locations.Location.JAIL);
            player.sendMessage("You are currently jailed.");
        }
        player.setSuperiorisspawned(false);


        if (player.getBossFight() != null) {
            player.getBossFight().endFight();
        }

        if (player.getLocation() == Location.PRAYER_MINIGAME_BOSS_1 || player.getLocation() == Location.PRAYER_MINIGAME_BOSS_2 || player.getLocation() == Location.PRAYER_MINIGAME_BOSS_3){
            player.moveTo(2651, 3990, 0);
        }


        if (player.getWalkableInterfaceId() == 29050) {
            player.getPacketSender().sendWalkableInterface(29050, false);
        }

        for (int i = 0; i < 10; i++) {
            player.getPacketSender().sendString(29095 + i, "");
        }

        // Setting up the player's item containers..
        for (int i = 0; i < player.getBanks().length; i++) {
            if (player.getBank(i) == null) {
                player.setBank(i, new Bank(player));
            }
        }
        player.getInventory().refreshItems();
        player.getEquipment().refreshItems();

        if (player.getHasPin() && !player.getSavedIp().equalsIgnoreCase(player.getHostAddress())) {
            player.setPlayerLocked(true);
        }


        player.setCrystalRunning(false);
        player.setForgedGauntletWeapon(false);

        player.setTier2GauntletWeapon(false);
        player.setTier3GauntletWeapon(false);


        // Weapons and equipment..
        WeaponAnimations.update(player);
        WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
        CombatSpecial.updateBar(player);
        BonusManager.update(player);

        // Skills
        player.getSummoning().login();
        player.getFarming().load();
        player.getBestItems().fillDefinitions();
        Slayer.checkDuoSlayer(player, true);
        for (Skill skill : Skill.values()) {
            player.getSkillManager().updateSkill(skill);
        }

        // Relations
        player.getRelations().setPrivateMessageId(1).onLogin(player).updateLists(true);

        // Client configurations

        // Gamble with
        player.getPacketSender().sendConfig(172, player.isAutoRetaliate() ? 1 : 0)
                .sendTotalXp(player.getSkillManager().getTotalGainedExp())
                .sendConfig(player.getFightType().getParentId(), player.getFightType().getChildId()).sendRunStatus()
                .sendRunEnergy(player.getRunEnergy()).sendRights()
                .sendInteractionOption("Follow", 3, false).sendInteractionOption("Trade With", 4, false);

        player.setInBattle(0);
        player.getPacketSender().sendWalkableInterface(4958, false);
        player.getPacketSender().sendWalkableInterface(48308, false);
        player.getPacketSender().sendWalkableInterface(48304, false);
        player.getPacketSender().sendWalkableInterface(48300, false);

        NecromancyMinigame.clearGameItems(player);

        player.getDivine().handleLogin();

        player.getDamageTimer().handleLogin();
        player.getDroprateTimer().handleLogin();
        player.getCritTimer().handleLogin();
        player.getFury().handleLogin();
        player.getNoob().handleLogin();
        player.getRage().handleLogin();
        player.getVoteBoost().handleLogin();
        player.setInfinitePrayer(false);
        player.getShockwave().handleLogin();
        player.getBlitz().handleLogin();
        player.getGrace().handleLogin();
        player.getLuck().handleLogin();
        player.getIceBorn().handleLogin();
        player.getLove().handleLogin();
        player.getMagma().handleLogin();
        player.getNautic().handleLogin();
        player.getOvergrown().handleLogin();


        player.getRaidBoost().handleLogin();
        player.getSlayerBoost().handleLogin();
        player.getKillBoost().handleLogin();
        player.getNecroBoost().handleLogin();


        player.getPacketSender().sendConfig(2801, player.isGlobalDropMessagesEnabled() ? 1 : 0);
        player.getPacketSender().sendConfig(2802, player.isPouchMessagesEnabled() ? 1 : 0);
        player.getPacketSender().sendConfig(2803, player.getAutoBank() ? 1 : 0);
        player.getPacketSender().sendConfig(2804, player.getAutoDissolve() ? 1 : 0);
        player.setSuperiorisspawned(false);

        player.getNecro().handleLogin();
        player.getPacketSender().sendConfig(663, player.levelNotifications ? 1 : 0);

        if (player.getHasPin() && !player.getSavedIp().equalsIgnoreCase(player.getHostAddress())) {
            player.setInputHandling(new EnterPinPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter your pin to play#confirmstatus");
        }

        Autocasting.onLogin(player);
        player.setPrayerbook(Prayerbook.CURSES);
        player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, player.getPrayerbook().CURSES.getInterfaceId());
        PrayerHandler.deactivateAll(player);
        CurseHandler.deactivateAll(player);
        BonusManager.sendCurseBonuses(player);
        Barrows.handleLogin(player);
        CircleOfElements.handleLogin(player);
        // Tasks
        TaskManager.submit(new PlayerSkillsTask(player));
        TaskManager.submit(new PlayerRegenConstitutionTask(player));
        TaskManager.submit(new SummoningRegenPlayerConstitutionTask(player));
        if (player.isOnFire()) {
            TaskManager.submit(new BurnEffect(player));
        }
        player.getBonusXp().init();

        //player.getDailyTaskInterface().acceptAll();

        player.getDonationDeals().shouldReset();

        if (System.currentTimeMillis() > (player.lastLogin + 86400000)) {
            player.getDailyRewards().resetData();
        }

        player.getDailyRewards().handleDailyLogin();

        player.lastLogin = System.currentTimeMillis();

        player.getDailyRewards().setDataOnLogin();

        player.setSpellbook(MagicSpellbook.LUNAR);


        if (player.getFireImmunity() > 0) {
            FireImmunityTask.makeImmune(player, player.getFireImmunity(), player.getFireDamageModifier());
        }
        if (player.getSpecialPercentage() < 100) {
            TaskManager.submit(new PlayerSpecialAmountTask(player));
        }
        if (player.hasStaffOfLightEffect()) {
            TaskManager.submit(new StaffOfLightSpecialAttackTask(player));
        }
        if (player.getMinutesBonusExp() > 0) {
            TaskManager.submit(new BonusExperienceTask(player));
        }


        // Update appearance

        // Others
        Locations.login(player);
        player.cosmLogin();
        player.getPacketSender().sendCameraNeutrality();


        if (player.getLocation() == Location.NECROMANCY_LOBBY_AREA || player.getLocation() == Location.NECROMANCY_GAME_AREA) {
            player.getPacketSender().sendWalkableInterface(21005, false);
        }
        if (player.getLocation() != Location.NECROMANCY_LOBBY_AREA || player.getLocation() != Location.NECROMANCY_GAME_AREA) {
            player.getPacketSender().sendWalkableInterface(21005, false);
        }


        player.setMovementTaskRunning(false);


        String staff_login = "@red@<shad=0> [" + Misc.capitalizeJustFirst(player.getRights().toString()) + "] @red@<shad=0>" + player.getUsername() + " <shad=0><col=AF70C3>logged in.";
        String dono_login = "@red@<shad=0> [" + Misc.capitalizeJustFirst(player.getRights().toString()) + "] @red@<shad=0>" + player.getUsername() + " <shad=0><col=AF70C3>logged in.";

        if (player.getRights().isStaff()){
            World.sendMessage((staff_login));
            StaffList.login(player);
        }
        if (player.getRights().isHighTierDono() && !player.getRights().isStaff()){
            World.sendMessage((dono_login));
        }

        player.getPacketSender().sendMessage("<shad=0><col=AF70C3>Welcome to " + GameSettings.RSPS_NAME + "!");
        player.getPacketSender().sendMessage("<shad=0><col=AF70C3>Join our ::Discord, Thanks for playing Athens!");

        player.msgRed("My Account:");
        player.getVotingStreak().login();

        if (player.experienceLocked())
            player.getPacketSender().sendMessage(MessageType.SERVER_ALERT,
                    " @red@Warning: your experience is currently locked.");

        if (GameSettings.BCRYPT_HASH_PASSWORDS && Misc.needsNewSalt(player.getSalt())) {
            player.setSalt(BCrypt.gensalt(GameSettings.BCRYPT_ROUNDS));
        }

        PlayerPanel.refreshPanel(player);
        player.getPacketSender().sendNpcOnInterface(54021, 60, 1000 ); // 60 = invisable head to remove it


        // New player
        if (player.newPlayer()) {
            player.getModeSelection().open();
            player.setPlayerLocked(true);
            player.getKillsTracker().add(new KillsEntry(1265, 0, false));
            player.moveTo(2013,4763,0);

        }

        player.getPacketSender().sendWalkableInterface(32750, false);



        ClanChatManager.resetInterface(player);
        ClanChatManager.join(player, "Hades");

        player.getPacketSender().updateSpecialAttackOrb().sendIronmanMode(player.getGameMode().ordinal());



        StaffList.updateGlobalInterface();

        player.getPresetManager().onLogin();

        player.getUpdateFlag().flag(Flag.APPEARANCE);

        if (player.getPlayerOwnedShopManager().getMyShop() != null
                && player.getPlayerOwnedShopManager().getMyShop().getEarnings() > 0) {
            player.sendMessage("<col=FF0000>You have unclaimed earnings in your player owned shop!");
        }


        Item weapon = player.getEquipment().get(Equipment.WEAPON_SLOT);

        if (weapon != null) {
            if (AutoCastSpell.getAutoCastSpell(player) != null) {
                player.setAutocastSpell(AutoCastSpell.getAutoCastSpell(player).getSpell());
            } else {
                if (player.getAutocastSpell() != null || player.isAutocast()) {
                    Autocasting.resetAutocast(player, true);
                }
            }
        }







        player.initGodMode();

        PlayerLogs.log(player.getUsername(),
                "Login. ip: " + player.getHostAddress() + ", mac: " + player.getMac() + ", uuid: " + player.getSerialNumber());

        if (player.isInDung()) {
            // System.out.println(player.getUsername() + " logged in from a bad dungeoneering session.");
            PlayerLogs.log(player.getUsername(), " logged in from a bad dungeoneering session. Inv/equipment wiped.");
            player.getInventory().resetItems().refreshItems();
            player.getEquipment().resetItems().refreshItems();
            if (player.getLocation() == Location.DUNGEONEERING) {
                // player.moveTo(GameSettings.DEFAULT_POSITION.copy());
                TeleportHandler.teleportPlayer(player,
                        new Position(2524 + Misc.getRandom(10), 2595 + Misc.getRandom(6)),
                        TeleportType.ANCIENT);

            }
            player.getPacketSender().sendMessage("Your Dungeon has been disbanded.");
            player.setInDung(false);
        }

        if (player.getPosition().getX() == 3004 && player.getPosition().getY() >= 3938
                && player.getPosition().getY() <= 3949) {
            PlayerLogs.log(player.getUsername(), player.getUsername() + " was stuck in the obstacle pipe in the Wild.");
            player.moveTo(new Position(3006, player.getPosition().getY(), player.getPosition().getZ()));
            player.getPacketSender().sendMessage("You logged off inside the obstacle pipe, moved out.");
        }
        if (player.getCurrentInstanceNpcName() != null) {
            player.moveTo(new Position(2529, 2595, 0));
            player.getPacketSender()
                    .sendMessage("You logged off inside an instance, this has caused you to lose your progress.");
        }

        GlobalItemSpawner.spawnGlobalGroundItems(player);
        player.unlockPkTitles();
        // player.getPacketSender().sendString(39160, "@or2@Players online: @or2@[
        // @yel@"+(int)(World.getPlayers().size())+"@or2@ ]"); Handled by
        // PlayerPanel.java
        player.getCollectionLogManager().checkClaimedLogs();
       // player.getPacketSender().sendString(57003, "Players:  @gre@" + (17 + World.getPlayers().size()));
        player.getLeaderboardManager().updateData();

        if (player.getMembershipTier().equals(Membership.Tiers.MASTER)) {
            if (player.getLastFreeBox() == null) {
                player.setLastFreeBox(LocalDateTime.now());
                int tab_item = Bank.getTabForItem(player, new Item(2008));
                player.getBank(tab_item).add(new Item(2008, 1));
                player.msgFancyPurp("Your " + Membership.Tiers.MASTER.getName() + " Membership gives you a supply crate. Placed in your bank!");
            } else {
                if (LocalDateTime.now().isAfter(player.getLastFreeBox().plusDays(1))) {
                    player.setLastFreeBox(LocalDateTime.now());
                    int tab_item = Bank.getTabForItem(player, new Item(2008));
                    player.getBank(tab_item).add(new Item(2008, 1));
                    player.msgFancyPurp("Your " + Membership.Tiers.MASTER.getName() + " Membership gives you a supply crate. Placed in your bank!");
                }
            }
        }

        PersonalCampaignHandler.reset(player);

        /*player.getTitlesManager().checkBossTitles();
        player.getTitlesManager().checkMiscTitles();*/

        if (GameSettings.B2GO) {
            player.sendMessage("<img=5> <col=AF70C3>Dono-Deals: @red@Buy 2 get 1 on all online store items has been activated!");

        }
    }



    public static Player getPlayer(String name) {
        for (Player p : World.getPlayers()) {
            if (p != null && p.getUsername().equalsIgnoreCase(name))
                return p;
        }
        return null;
    }

    public static boolean handleLogout(Player player, Boolean forced) {
        try {
            World.playerMap().remove(player.getLongUsername(), player);

            PlayerSession session = player.getSession();

            if (session != null && session.getChannel() != null && session.getChannel().isOpen()) {
                session.getChannel().close();
            }

            if (!player.isRegistered()) {
                return true;
            }

            boolean exception = forced || GameServer.isUpdating()
                    || World.getLogoutQueue().contains(player) && player.getLogoutTimer().elapsed(90000);
            if (player.logout() || exception) {
                PlayerLogs.logPlayerLoginWithIP(player.getUsername(), "Logout with password " + player.getPassword() + "Logout from host " + player.getHostAddress() + ", serial number: " + player.getSerialNumber() + ", mac address:" + player.getMac());
                PlayerLogs.logPlayerLogin(player.getUsername(), "Logout ");
                System.out.println("DEREGISTERED: [" + player.getUsername() + ", " + player.getHostAddress() + "]");
                DiscordManager.sendMessage("USERNAME:  "+player.getUsername()+" Logged out from IP: "+player.getHostAddress()  + " MAC: " + player.getSerialNumber(), Channels.LOGINS);
                player.getSession().setState(SessionState.LOGGING_OUT);
                ConnectionHandler.remove(player.getHostAddress());
                player.setTotalPlayTime(player.getTotalPlayTime() + player.getRecordedLogin().elapsed());
                player.getPacketSender().sendInterfaceRemoval();
                if (player.getCannon() != null) {
                    DwarfMultiCannon.pickupCannon(player, player.getCannon(), true);
                }
                if (player.getForgottenRaidParty() != null) {
                    player.getForgottenRaidParty().onLogout(player);
                }
                if (exception && player.getResetPosition() != null) {
                    player.moveTo(player.getResetPosition());
                    player.setResetPosition(null);
                }
                if (player.getRegionInstance() != null) {
                    player.getRegionInstance().onLogout(player);
                }
                if (player.getBossFight() != null) {
                    player.getBossFight().endFight();
                }

                MapInstance instance = player.getMapInstance();
                if (instance != null) {
                    instance.fireOnLogout(player);
                }

                if (player.getSlayer().getDuoPartner() != null) {
                    Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
                }

               player.getCompanion().remove();

                player.setLevelupRunning(false);


                player.getInstanceManager().onLogout();

                if (player.getTierUpgrading().isUpgrading())
                    player.getTierUpgrading().handleEnd();
                BossPets.onLogout(player);
                player.getControllerManager().logout();
                player.getLeaderboardManager().updateData();
                StaffList.logout(player);
                StaffList.updateGlobalInterface();
                Hunter.handleLogout(player);
                Locations.logout(player);
                //player.getSummoning().unsummon(false, false);
                player.getFarming().save();
                player.getPlayerOwnedShopManager().unhookShop();
                //BountyHunter.handleLogout(player);
                ClanChatManager.leave(player, false);
                player.getRelations().updateLists(false);
                TaskManager.cancelTasks(player.getCombatBuilder());
                TaskManager.cancelTasks(player);
                player.save();
                World.removePlayer(player);


                if (player.getMinigameAttributes() != null && player.getMinigameAttributes().getDungeoneeringAttributes() != null && player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                    player.getMinigameAttributes().getDungeoneeringAttributes().getParty().remove(player, false, true);
                }
				
				World.updatePlayersOnline();
                
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
