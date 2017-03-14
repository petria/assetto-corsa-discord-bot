package airiot.fi.bot.feedparser;

import airiot.fi.bot.AssettoCorsaEvent;

/**
 * Created by Petri Airio on 13.3.2017.
 */
public interface AssettoCorsaFeedEventHandler {

    void handleChatEvent(AssettoCorsaEvent event);

    void handleCleanExitEvent(AssettoCorsaEvent event);

    void handleDriverAcceptedForEvent(AssettoCorsaEvent event);

    void handleDriverEvent(AssettoCorsaEvent event);

    void handleNewPickupConnectionEvent(AssettoCorsaEvent event);

    void handleRequestCarEvent(AssettoCorsaEvent event);

    void handleWarningEvent(AssettoCorsaEvent event);

}
