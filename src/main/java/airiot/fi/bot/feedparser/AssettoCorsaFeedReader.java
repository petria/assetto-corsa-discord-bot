package airiot.fi.bot.feedparser;

import airiot.fi.bot.AssettoCorsaEvent;
import airiot.fi.bot.DiscordBroadcaster;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${ECHO_INPUT}")
    private boolean echoInput;

    @PostConstruct
    public void readSystemIn() {
        log.debug("Ready for input!");
        while (true) {
            String line = sc.nextLine();
            if (echoInput) {
                System.out.println(line);
            }
            AssettoCorsaEvent event = feedParser.parseFeedLine(line);
            if (event != null) {
                broadcaster.sendMessage(event.getMessage());
            }
        }
    }

}
