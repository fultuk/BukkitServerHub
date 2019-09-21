package de.panamo.server.hub.inventory;

import de.panamo.server.hub.inventory.item.HubItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.HashSet;

public class HubInventory implements Listener {

    private Collection<HubItem> hubItems = new HashSet<>();
    private String title;
    private int size;

    public HubInventory(String title, int size) {
        this.title = title;
        this.size = size;
    }

    @EventHandler
    public void handleDamage(EntityDamageByEntityEvent event) {
        var entity = event.getDamager();
        var target = event.getEntity();

        if (entity instanceof Player && target instanceof Player) {
            if (this.interactItem((Player) entity, (Player) target, Action.LEFT_CLICK_BLOCK)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleInteract(PlayerInteractEvent event) {
        this.interactItem(event.getPlayer(), null, event.getAction());
    }

    @EventHandler
    public void handleInteract(PlayerInteractEntityEvent event) {
        var target = event.getRightClicked();

        if (target instanceof Player) {
            this.interactItem(event.getPlayer(), (Player) target, Action.RIGHT_CLICK_BLOCK);
        }
    }

    private boolean interactItem(Player player, Player target, Action action) {
        var itemInHand = player.getInventory().getItemInMainHand();

        for (var hubItem : this.hubItems) {
            if (itemInHand.equals(hubItem.getContent())) {

                if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                    if (target != null) {
                        hubItem.handleLeftClick(player, target);
                    } else {
                        hubItem.handleLeftClick(player);
                    }
                } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    if (target != null) {
                        hubItem.handleRightClick(player, target);
                    } else {
                        hubItem.handleRightClick(player);
                    }
                }

                return true;
            }
        }

        return false;
    }

    public void addHubItem(HubItem hubItem) {
        this.hubItems.add(hubItem);
    }

    public Inventory toInventory() {
        return this.toInventory(Bukkit.createInventory(null, this.size, this.title));
    }

    public Inventory toInventory(Inventory toFill) {
        toFill.clear();

        for (var hubItem : this.hubItems) {
            toFill.setItem(hubItem.getSlot(), hubItem.getContent());
        }

        return toFill;
    }

    public Collection<HubItem> getHubItems() {
        return hubItems;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

}
