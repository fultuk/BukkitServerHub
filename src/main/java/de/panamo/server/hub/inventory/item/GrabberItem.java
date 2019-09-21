package de.panamo.server.hub.inventory.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GrabberItem extends HubItem {

    public GrabberItem() {
        super(new ItemStack(Material.SADDLE), 0);
    }

    @Override
    public void handleRightClick(HubItemAction hubItemAction) {
        hubItemAction.getTarget().ifPresent(target -> hubItemAction.getPlayer().addPassenger(target));
    }

    @Override
    public void handleLeftClick(HubItemAction hubItemAction) {
        var player = hubItemAction.getPlayer();
        player.getPassengers().stream().findFirst().ifPresent(player::removePassenger);
    }

}
