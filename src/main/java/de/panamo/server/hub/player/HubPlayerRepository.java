package de.panamo.server.hub.player;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HubPlayerRepository {

    private Map<UUID, HubPlayer> hubPlayers = new ConcurrentHashMap<>();

    public HubPlayer get(UUID uuid) {
        return this.hubPlayers.computeIfAbsent(uuid, HubPlayer::new);
    }

    public boolean exists(UUID uuid) {
        return this.hubPlayers.containsKey(uuid);
    }

}
