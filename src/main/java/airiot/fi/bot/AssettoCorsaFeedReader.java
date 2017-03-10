package airiot.fi.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Scanner;

/**
 * Created by Petri Airio on 8.3.2017.
 */
@Component
@Slf4j
public class AssettoCorsaFeedReader {

    private static Scanner sc = new Scanner(System.in);

    @Autowired
    private DiscordBroadcaster broadcaster;

    @Autowired
    private AssettoCorsaFeedParser feedParser;

    @PostConstruct
    public void readSystemIn() {
        log.debug("Ready for input!");
        while (true) {
            String line = sc.nextLine();
            AssettoCorsaEvent event = feedParser.parseFeedLine(line);
            if (event != null) {
                broadcaster.sendMessage(event.getMessage());
            }
        }
    }

}
