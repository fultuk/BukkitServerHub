package de.panamo.server.hub.inventory.item.items;


import de.panamo.server.hub.inventory.item.HubItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class KnockbackStickItem extends HubItem {

    public KnockbackStickItem() {
        super(null, 6);

        ItemStack knockbackStick = new ItemStack(Material.STICK);
        knockbackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        super.content = knockbackStick;
    }

    @Override
    public void handleLeftClick(HubItemAction hubItemAction) {
        hubItemAction.getTarget().ifPresent(target -> {
            if (target instanceof Player) {
                Player targetPlayer = (Player) target;
                Cancellable event = hubItemAction.getEvent();

                if (targetPlayer.getInventory().getItemInMainHand().equals(super.getContent())) {

                    if (event instanceof EntityDamageEvent) {
                        EntityDamageEvent entityDamageEvent = (EntityDamageEvent) event;
                        entityDamageEvent.setDamage(0D);
                    }

                } else {
                    event.setCancelled(true);
                }
            }

        });
    }
}
