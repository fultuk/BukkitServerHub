package de.panamo.server.hub.listeners;

import de.panamo.server.hub.ServerHub;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private static final String SPAWN_LOCATION_KEY = "spawn";

    private ServerHub serverHub;

    public JoinListener(ServerHub serverHub) {
        this.serverHub = serverHub;
    }

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        this.serverHub.getLocationConfig().getLocation(SPAWN_LOCATION_KEY).ifPresent(player::teleport);
        this.serverHub.getHubInventory().toInventory(event.getPlayer().getInventory());
    }


}
