package de.panamo.server.hub.inventory;

import de.panamo.server.hub.inventory.item.HubItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents an Inventory which is filled with interactable {@link HubItem}s
 */
public class HubInventory implements Listener {

    private Collection<HubItem> hubItems = new HashSet<>();
    private String title;
    private int size;


    public HubInventory(String title, int size) {
        this.title = title;
        this.size = size;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handleDamage(EntityDamageByEntityEvent event) {
        var entity = event.getDamager();
        var target = event.getEntity();

        if (entity instanceof Player) {
            event.setCancelled(this.interactItem((Player) entity, target, Action.LEFT_CLICK_BLOCK) || event.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handleInteract(PlayerInteractEvent event) {
        // not using the shorter form because there are two things that might be cancelled
        if (this.interactItem(event.getPlayer(), null, event.getAction())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handleInteract(PlayerInteractEntityEvent event) {
        event.setCancelled(this.interactItem(event.getPlayer(), event.getRightClicked(), Action.RIGHT_CLICK_BLOCK) || event.isCancelled());
    }

    /**
     * Calls the action-handler of the hubItem used, if one is used
     *
     * @param player the player who uses the hubItem
     * @param target the entity who has been clicked on
     * @param action the action of the item-use
     * @return if the action and it's event should be cancelled
     */
    private boolean interactItem(Player player, Entity target, Action action) {
        var itemInHand = player.getInventory().getItemInMainHand();

        for (var hubItem : this.hubItems) {
            if (itemInHand.equals(hubItem.getContent())) {

                HubItem.HubItemAction hubItemAction = new HubItem.HubItemAction(player, target);

                if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                    hubItem.handleLeftClick(hubItemAction);
                } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    hubItem.handleRightClick(hubItemAction);
                }

                return hubItemAction.isCancelled();
            }
        }

        return false;
    }

    public void addHubItem(HubItem hubItem) {
        this.hubItems.add(hubItem);
    }

    /**
     * Creates a new inventory and fills it with the hubItems
     *
     * @return the new inventory
     */
    public Inventory toInventory() {
        return this.toInventory(Bukkit.createInventory(null, this.size, this.title));
    }

    /**
     * Fills an existing inventory with the hubItems
     *
     * @param toFill the inventory to clear and fill
     * @return the given inventory
     */
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
