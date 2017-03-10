package airiot.fi.bot;

/**
 * Created by Petri Airio on 8.3.2017.
 */
public enum KnownLine {

    // NEW PICKUP CONNECTION from
    CHAT("CHAT (.*)", "Chat: %s"),
    CLEAN_EXIT("Clean exit, driver disconnected:(.*)", "Clean exit: %s"),
    DRIVER_ACCEPTED_FOR("DRIVER ACCEPTED FOR CAR (.*)", "Driver accepted for car: %s"),
    DRIVER("DRIVER: (.*)", "Driver: %s"),
    LAP("LAP (.*)", "Lap: %s"),
    LAP_WITH_CUTS("LAP WITH CUTS:(.*)", "Lap with cuts: %s"),
    NEW_PICKUP_CONNECTION("NEW PICKUP CONNECTION from (.*)", "New pickup connection from: %s"),
    REQUEST_CAR("REQUESTED CAR: (.*)", "Requested car: %s");

    private final String pattern;
    private final String format;

    KnownLine(String pattern, String format) {
        this.pattern = pattern;
        this.format = format;
    }

    public String getPattern() {
        return pattern;
    }

    public String getFormat() {
        return format;
    }
}
