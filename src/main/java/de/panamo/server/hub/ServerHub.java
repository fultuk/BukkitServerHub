package de.panamo.server.hub;


import de.panamo.server.hub.inventory.HubInventory;
import de.panamo.server.hub.inventory.item.GrabberItem;
import de.panamo.server.hub.player.HubPlayerRepository;
import de.panamo.server.hub.player.jumpandrun.JumpAndRunListener;
import de.panamo.server.hub.player.listener.JoinListener;
import de.panamo.server.hub.setup.LocationConfig;
import de.panamo.server.hub.setup.SetupCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class ServerHub extends JavaPlugin {

    private LocationConfig locationConfig;

    @Override
    public void onEnable() {
        super.getDataFolder().mkdirs();
        this.locationConfig = new LocationConfig(new File(super.getDataFolder(), "locations.yml"));

        try {
            this.locationConfig.load();
        } catch (IOException exception) {
            super.getLogger().log(Level.SEVERE, "Unable to load locations!", exception);
            return;
        }

        HubInventory hubInventory = new HubInventory("Player", 9);
        hubInventory.addHubItem(new GrabberItem());

        Bukkit.getPluginManager().registerEvents(hubInventory, this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this.locationConfig, hubInventory), this);
        Bukkit.getPluginManager().registerEvents(new JumpAndRunListener(new HubPlayerRepository(), this.locationConfig), this);

        Objects.requireNonNull(super.getCommand("setup"), "Command is not registered!").setExecutor(new SetupCommand(this.locationConfig));
    }

    @Override
    public void onDisable() {
        this.locationConfig.save();
    }
}
