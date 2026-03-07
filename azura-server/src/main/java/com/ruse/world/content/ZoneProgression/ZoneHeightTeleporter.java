package com.ruse.world.content.ZoneProgression;

import com.ruse.model.Position;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.Player;

public class ZoneHeightTeleporter {
    public static void handleObjectAction(Player player) {

        Position playerPosition = new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());

        if (player.getPosition().getRegionId() == 8010) {
            switch (playerPosition.getZ()) {
                case 0:
                    player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(), 4));
                    break;
                case 4:
                    player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(), 0));
                    break;
            }
        }
        if (player.getPosition().getRegionId() == 8520) {
            switch (playerPosition.getZ()) {
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 5), TeleportType.NORMAL);
                    break;
                case 5:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 9), TeleportType.NORMAL);
                    break;
                case 9:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 13), TeleportType.NORMAL);
                    break;
                case 13:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 1), TeleportType.NORMAL);
                    break;
            }
        }
        //TERRAN
        if (player.getPosition().getRegionId() == 12352) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //AQUALORN
        if (player.getPosition().getRegionId() == 8512) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //FERNA
        if (player.getPosition().getRegionId() == 11840) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //IGNOX
        if (player.getPosition().getRegionId() == 8518) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //CRYSTALIS
        if (player.getPosition().getRegionId() == 8510) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //EMBER
        if (player.getPosition().getRegionId() == 7749) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //XERCES
        if (player.getPosition().getRegionId() == 12610) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //MARINA
        if (player.getPosition().getRegionId() == 8252) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }


        //SCORCH
        if (player.getPosition().getRegionId() == 9289) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //KEZEl
        if (player.getPosition().getRegionId() == 11842) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //HYDRORA
        if (player.getPosition().getRegionId() == 8506) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //INFERNUS
        if (player.getPosition().getRegionId() == 10804) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //TELLURION
        if (player.getPosition().getRegionId() == 10023) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //MARINUS
        if (player.getPosition().getRegionId() == 14895) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //PYROX
        if (player.getPosition().getRegionId() == 10034) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //ASTARAN
        if (player.getPosition().getRegionId() == 10281) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //NEREUS
        if (player.getPosition().getRegionId() == 15405) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //VOLCAR
        if (player.getPosition().getRegionId() == 10546) {
            switch (playerPosition.getZ()) {
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 5), TeleportType.NORMAL);
                    break;
                case 5:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 9), TeleportType.NORMAL);
                    break;
                case 9:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 13), TeleportType.NORMAL);
                    break;
                case 13:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 1), TeleportType.NORMAL);
                    break;
            }
        }

        //GEOWIND
        if (player.getPosition().getRegionId() == 10539) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //LAGOON
        if (player.getPosition().getRegionId() == 10029) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //INCENDIA
        if (player.getPosition().getRegionId() == 9285) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //TERRA
        if (player.getPosition().getRegionId() == 7756) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //ABYSS
        if (player.getPosition().getRegionId() == 11051) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //PYRA
        if (player.getPosition().getRegionId() == 6989) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //GEODE
        if (player.getPosition().getRegionId() == 7243) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //CERULEAN
        if (player.getPosition().getRegionId() == 6741) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //GOLIATH
        if (player.getPosition().getRegionId() == 10321) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }


        //VOLCANUS
        if (player.getPosition().getRegionId() == 9300) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //NAUTILUS
        if (player.getPosition().getRegionId() == 11854) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //QUAKE
        if (player.getPosition().getRegionId() == 9553) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //SCALDOR
        if (player.getPosition().getRegionId() == 10318) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //SEABANE
        if (player.getPosition().getRegionId() == 11341) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //RUMBLE
        if (player.getPosition().getRegionId() == 9811) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //MOLTRON
        if (player.getPosition().getRegionId() == 10830) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }
        //GEMSTONE
        if (player.getPosition().getRegionId() == 12181) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }

        //HYDROX
        if (player.getPosition().getRegionId() == 9806) {
            switch (playerPosition.getZ()) {
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 4), TeleportType.NORMAL);
                    break;
                case 4:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 8), TeleportType.NORMAL);
                    break;
                case 8:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 12), TeleportType.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(player.getPosition().getX(), player.getPosition().getY(), 0), TeleportType.NORMAL);
                    break;
            }
        }
    }
}
