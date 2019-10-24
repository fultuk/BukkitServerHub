package de.panamo.server.hub.inventory.item.items;

import de.panamo.server.hub.inventory.item.HubItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GrabberItem extends HubItem {

    public GrabberItem() {
        super(new ItemStack(Material.SADDLE), 2);
    }

    @Override
    public void handleRightClick(HubItemAction hubItemAction) {
        hubItemAction.getTarget().ifPresent(target -> hubItemAction.getPlayer().addPassenger(target));
    }

    @Override
    public void handleLeftClick(HubItemAction hubItemAction) {
        var player = hubItemAction.getPlayer();
        player.getPassengers().stream().findFirst().ifPresent(target -> {
            player.removePassenger(target);
            target.setVelocity(player.getLocation().getDirection().normalize().setY(0.7));
        });
    }

}
