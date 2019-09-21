package de.panamo.server.hub.commands;


import de.panamo.server.hub.util.HubMessage;
import de.panamo.server.hub.util.LocationConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {
    private LocationConfig locationConfig;

    public SetupCommand(LocationConfig locationConfig) {
        this.locationConfig = locationConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(HubMessage.SENDER_NO_PLAYER.get());
            return true;
        }

        var player = (Player) sender;

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "addlocation":
                    return this.addLocation(player, args);
                case "removelocation":
                    return this.removeLocation(player, args);
            }
        }

        return false;
    }

    private boolean addLocation(Player player, String[] args) {
        if (args.length < 2) {
            return false;
        }

        var locationName = args[1];
        this.locationConfig.addLocation(locationName, player.getLocation());
        this.locationConfig.save();

        player.sendMessage(HubMessage.LOCATION_ADDED.get());

        return true;
    }

    private boolean removeLocation(Player player, String[] args) {
        if (args.length < 2) {
            return false;
        }

        var locationName = args[1];
        this.locationConfig.removeLocation(locationName);
        this.locationConfig.save();

        player.sendMessage(HubMessage.LOCATION_REMOVED.get());

        return true;
    }

}
