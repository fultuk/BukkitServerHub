package de.panamo.server.hub.player.listener;

import de.panamo.server.hub.inventory.HubInventory;
import de.panamo.server.hub.setup.LocationConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private static final String SPAWN_LOCATION_KEY = "spawn";

    private LocationConfig locationConfig;
    private HubInventory hubInventory;

    public JoinListener(LocationConfig locationConfig, HubInventory hubInventory) {
        this.locationConfig = locationConfig;
        this.hubInventory = hubInventory;
    }

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        this.locationConfig.getLocation(SPAWN_LOCATION_KEY).ifPresent(player::teleport);
        this.hubInventory.toInventory(event.getPlayer().getInventory());
    }


}
