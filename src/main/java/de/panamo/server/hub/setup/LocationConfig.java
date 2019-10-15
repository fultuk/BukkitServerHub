package de.panamo.server.hub.setup;


import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Holds all locations used in this plugin and manages the saving/loading
 */
public class LocationConfig {

    private static final String LOCATIONS_KEY = "locations";

    private Map<String, Object> locations = new HashMap<>();
    private File file;
    private FileConfiguration configuration;

    public LocationConfig(File file) {
        this.file = file;
    }

    public void load() throws IOException {
        if (!this.file.exists()) {
            this.file.createNewFile();
        }

        this.configuration = YamlConfiguration.loadConfiguration(file);

        var locationSection = this.configuration.getConfigurationSection(LOCATIONS_KEY);
        if (locationSection != null) {
            this.locations = locationSection.getValues(false);
        }
    }

    public Optional<Location> getLocation(String key) {
        return Optional.ofNullable((Location) this.locations.get(key.toLowerCase()));
    }

    public void addLocation(String key, Location location) {
        this.locations.put(key.toLowerCase(), location);
    }

    public void removeLocation(String key) {
        this.locations.remove(key.toLowerCase());
    }

    public void save() {
        this.configuration.set(LOCATIONS_KEY, this.locations);
        try {
            this.configuration.save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
