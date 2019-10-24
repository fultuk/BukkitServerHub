package de.panamo.server.hub.inventory.item;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * Represents an item which will be placed in an {@link de.panamo.server.hub.inventory.HubInventory}
 */
public class HubItem {

    protected ItemStack content;
    protected int slot;

    public HubItem(ItemStack content, int slot) {
        this.content = content;
        this.slot = slot;
    }

    public void handleLeftClick(HubItemAction hubItemAction) {

    }


    public void handleRightClick(HubItemAction hubItemAction) {

    }


    public ItemStack getContent() {
        return this.content;
    }

    public int getSlot() {
        return this.slot;
    }

    public Material getType() {
        return this.content.getType();
    }


    public static class HubItemAction {

        private Player player;
        private Entity target;
        private Cancellable event;

        public HubItemAction(Player player, Entity target, Cancellable event) {
            this.player = player;
            this.target = target;
            this.event = event;
        }

        public Player getPlayer() {
            return player;
        }

        public Optional<Entity> getTarget() {
            return Optional.ofNullable(this.target);
        }

        public Cancellable getEvent() {
            return event;
        }

    }

}
