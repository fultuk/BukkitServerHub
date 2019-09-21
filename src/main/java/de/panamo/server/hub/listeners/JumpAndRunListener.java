package de.panamo.server.hub.listeners;


import de.panamo.server.hub.player.HubPlayer;
import de.panamo.server.hub.util.LocationConfig;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class JumpAndRunListener implements Listener {

    /**
     * The distance a player can have to the jumpAndRun-blocks before the {@link de.panamo.server.hub.player.JumpAndRun} is marked as failed
     */
    private static final int FAILURE_DISTANCE = 7;
    private static final String JUMP_AND_RUN_LOCATION_KEY = "jumpandrun";

    private LocationConfig locationConfig;

    public JumpAndRunListener(LocationConfig locationConfig) {
        this.locationConfig = locationConfig;
    }

    @EventHandler
    public void handleJumpAndRun(PlayerMoveEvent event) {
        var player = event.getPlayer();
        var playerLocation = player.getLocation().toBlockLocation();

        var jumpAndRun = HubPlayer.get(player.getUniqueId()).getJumpAndRun();

        if (jumpAndRun.isActive()) {

            // failed to jump on the next block
            if (jumpAndRun.getCurrentBlockLocation().distance(playerLocation) > FAILURE_DISTANCE
                    || jumpAndRun.getNextBlockLocation().distance(playerLocation) > FAILURE_DISTANCE) {
                jumpAndRun.failed();
                player.playSound(playerLocation, Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
                // succeeded to jump on the next block
            } else if (playerLocation.subtract(0, 1, 0).equals(jumpAndRun.getNextBlockLocation())) {
                jumpAndRun.nextBlockReached();
                player.playSound(playerLocation, Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
            }

        } else {
            this.locationConfig.getLocation(JUMP_AND_RUN_LOCATION_KEY).ifPresent(location -> {
                if (location.distance(playerLocation) < 1) {
                    jumpAndRun.start(location.toBlockLocation());

                    var startLocation = jumpAndRun.getCurrentBlockLocation().clone().add(0, 1, 0);
                    // makes the player look straight forward
                    startLocation.setPitch(0f);

                    player.teleport(startLocation);
                    player.playSound(startLocation, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                }
            });
        }
    }


}
