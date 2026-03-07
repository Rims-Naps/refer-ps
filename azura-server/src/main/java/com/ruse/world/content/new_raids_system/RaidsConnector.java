//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ruse.world.content.new_raids_system;

import com.ruse.world.content.new_raids_system.raids_system.Raids;
import com.ruse.world.entity.impl.player.Player;

public class RaidsConnector {
    private Player player;
    private Raids raidsConnector;

    public RaidsConnector(Player player) {
        this.player = player;
        this.raidsConnector = new Raids(player);
    }

    public Raids getRaidsConnector() {
        return this.raidsConnector;
    }
}
