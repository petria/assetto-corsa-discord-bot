package airiot.fi.bot;

import airiot.fi.bot.feedparser.KnownLine;

/**
 * Created by Petri Airio on 8.3.2017.
 */
public class AssettoCorsaEvent {

    private KnownLine eventType;
    private String message;


    public AssettoCorsaEvent(KnownLine knownLine) {
        this.eventType = knownLine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public KnownLine getEventType() {
        return eventType;
    }

    public void setEventType(KnownLine eventType) {
        this.eventType = eventType;
    }
}
