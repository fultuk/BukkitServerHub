package de.panamo.server.hub.player;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HubPlayer {
    private static final Map<UUID, HubPlayer> HUB_PLAYERS = new ConcurrentHashMap<>();

    private UUID uuid;
    private JumpAndRun jumpAndRun = new JumpAndRun();

    private HubPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public static HubPlayer get(UUID uuid) {
        return HUB_PLAYERS.computeIfAbsent(uuid, HubPlayer::new);
    }

    public static boolean exists(UUID uuid) {
        return HUB_PLAYERS.containsKey(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public JumpAndRun getJumpAndRun() {
        return jumpAndRun;
    }

}
