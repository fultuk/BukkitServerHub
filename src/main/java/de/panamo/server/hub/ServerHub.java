package de.panamo.server.hub;


import de.panamo.server.hub.commands.SetupCommand;
import de.panamo.server.hub.inventory.HubInventory;
import de.panamo.server.hub.inventory.item.GrabberItem;
import de.panamo.server.hub.listeners.JoinListener;
import de.panamo.server.hub.listeners.JumpAndRunListener;
import de.panamo.server.hub.util.LocationConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class ServerHub extends JavaPlugin {

    private LocationConfig locationConfig;
    private HubInventory hubInventory = new HubInventory("Player", 9);

    @Override
    public void onEnable() {
        super.getDataFolder().mkdirs();
        this.locationConfig = new LocationConfig(new File(super.getDataFolder(), "locations.yml"));

        this.hubInventory.addHubItem(new GrabberItem());

        Bukkit.getPluginManager().registerEvents(this.hubInventory, this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JumpAndRunListener(this.locationConfig), this);

        Objects.requireNonNull(super.getCommand("setup"), "Command is not registered!").setExecutor(new SetupCommand(this.locationConfig));
    }

    @Override
    public void onDisable() {
        this.locationConfig.save();
    }

    public LocationConfig getLocationConfig() {
        return locationConfig;
    }

    public HubInventory getHubInventory() {
        return hubInventory;
    }
}
