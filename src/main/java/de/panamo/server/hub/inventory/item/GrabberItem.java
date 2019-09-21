package de.panamo.server.hub.inventory.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GrabberItem extends HubItem {

    public GrabberItem() {
        super(new ItemStack(Material.SADDLE), 0);
    }

    @Override
    public void handleRightClick(Player player, Player target) {
        player.addPassenger(target);
    }

    @Override
    public void handleLeftClick(Player player, Player target) {
        if (player.getPassengers().contains(target)) {
            player.removePassenger(target);
            target.setVelocity(player.getVelocity().multiply(3).normalize());
        }
    }

}
