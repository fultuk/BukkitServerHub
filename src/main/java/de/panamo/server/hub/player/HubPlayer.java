package de.panamo.server.hub.player;


import de.panamo.server.hub.player.jumpandrun.JumpAndRun;

import java.util.UUID;

public class HubPlayer {

    private UUID uuid;
    private JumpAndRun jumpAndRun;

    HubPlayer(UUID uuid) {
        this.uuid = uuid;
        this.jumpAndRun = new JumpAndRun();
    }

    public UUID getUuid() {
        return uuid;
    }

    public JumpAndRun getJumpAndRun() {
        return jumpAndRun;
    }

}
