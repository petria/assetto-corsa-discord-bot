package airiot.fi;

import airiot.fi.bot.AssettoCorsaEvent;
import airiot.fi.bot.feedparser.AssettoCorsaFeedParser;
import airiot.fi.bot.feedparser.AssettoCorsaFeedParserImpl;
import org.junit.Assert;
import org.junit.Test;

import static airiot.fi.bot.feedparser.KnownLine.*;

public class AssettoCorsaFeedParserTest {

    private AssettoCorsaFeedParser feedParser = new AssettoCorsaFeedParserImpl();

    /*
        CHAT("CHAT (.*)", "Chat: %s"),
        CLEAN_EXIT("Clean exit, driver disconnected:(.*)", "Clean exit: %s"),
        DRIVER_ACCEPTED_FOR("DRIVER ACCEPTED FOR CAR (.*)", "Driver accepted for car: %s"),
        DRIVER("DRIVER: (.*)", "Driver: %s"),
        LAP("LAP (.*)", "Lap: %s"),
        LAP_WITH_CUTS("LAP WITH CUTS:(.*)", "Lap with cuts: %s"),
        NEW_PICKUP_CONNECTION("NEW PICKUP CONNECTION from (.*)", "New pickup connection from: %s"),
        REQUEST_CAR("REQUESTED CAR: (.*)", "Requested car: %s");

     */
    @Test
    public void testChat() {
        String line = "CHAT chat message";
        AssettoCorsaEvent event = feedParser.parseFeedLine(line);
        Assert.assertEquals(event.getEventType(), CHAT);
    }

    @Test
    public void testCleanExit() {
        String line = "Clean exit, driver disconnected: driver";
        AssettoCorsaEvent event = feedParser.parseFeedLine(line);
        Assert.assertEquals(event.getEventType(), CLEAN_EXIT);
    }

    @Test
    public void testDriverAcceptedForNum() {
        String line = "DRIVER ACCEPTED FOR CAR 20";
        AssettoCorsaEvent event = feedParser.parseFeedLine(line);
        Assert.assertEquals(event.getEventType(), DRIVER_ACCEPTED_FOR);
    }


    @Test
    public void testDriverAcceptedForName() {
        String line = "DRIVER ACCEPTED FOR CAR Petri Airio";
        AssettoCorsaEvent event = feedParser.parseFeedLine(line);
        Assert.assertEquals(event.getEventType(), DRIVER_ACCEPTED_FOR);
    }

    @Test
    public void testDriver() {
        String line = "DRIVER: John Doe";
        AssettoCorsaEvent event = feedParser.parseFeedLine(line);
        Assert.assertEquals(event.getEventType(), DRIVER);
    }

    @Test
    public void testLap() {
        String line = "LAP lap finishes";
        AssettoCorsaEvent event = feedParser.parseFeedLine(line);
        Assert.assertEquals(event.getEventType(), LAP);
    }

    @Test
    public void testNewPickupConnection() {
        String line = "NEW PICKUP CONNECTION from  84.251.186.226:64209";
        AssettoCorsaEvent event = feedParser.parseFeedLine(line);
        Assert.assertEquals(event.getEventType(), NEW_PICKUP_CONNECTION);
    }

    @Test
    public void testRequestedCar() {
        String line = "REQUESTED CAR: ks_porsche_911_gt3_r_2016*";
        AssettoCorsaEvent event = feedParser.parseFeedLine(line);
        Assert.assertEquals(event.getEventType(), REQUEST_CAR);
    }



}
