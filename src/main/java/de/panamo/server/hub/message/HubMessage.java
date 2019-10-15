package de.panamo.server.hub.message;


public enum HubMessage {
    SENDER_NO_PLAYER,
    LOCATION_ADDED,
    LOCATION_REMOVED;


    public String get() {
        String key = this.toString().toLowerCase();
        // TODO: add loading from properties file
        return "null";
    }


}
