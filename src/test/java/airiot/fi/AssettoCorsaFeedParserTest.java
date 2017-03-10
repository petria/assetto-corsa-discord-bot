package airiot.fi;

import airiot.fi.bot.AssettoCorsaEvent;
import airiot.fi.bot.AssettoCorsaFeedParser;
import airiot.fi.bot.AssettoCorsaFeedParserImpl;
import org.junit.Assert;
import org.junit.Test;

import static airiot.fi.bot.KnownLine.DRIVER_ACCEPTED_FOR;
import static airiot.fi.bot.KnownLine.NEW_PICKUP_CONNECTION;
import static airiot.fi.bot.KnownLine.REQUEST_CAR;

public class AssettoCorsaFeedParserTest {

    private AssettoCorsaFeedParser feedParser = new AssettoCorsaFeedParserImpl();

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

}
