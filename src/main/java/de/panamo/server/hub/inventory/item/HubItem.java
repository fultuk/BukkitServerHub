package de.panamo.server.hub.inventory.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HubItem {

    private ItemStack content;
    private int slot;

    public HubItem(ItemStack content, int slot) {
        this.content = content;
        this.slot = slot;
    }

    public void handleLeftClick(Player player, Player target) {
        this.handleLeftClick(player);
    }

    public void handleLeftClick(Player player) {

    }

    public void handleRightClick(Player player, Player target) {
        this.handleRightClick(player);
    }

    public void handleRightClick(Player player) {

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

}
