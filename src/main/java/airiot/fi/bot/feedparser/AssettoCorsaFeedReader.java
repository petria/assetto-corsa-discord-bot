package airiot.fi.bot.feedparser;

import airiot.fi.bot.AssettoCorsaEvent;
import airiot.fi.bot.DiscordBroadcaster;
import airiot.fi.bot.udp.UdpListener;
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
public class AssettoCorsaFeedReader implements Runnable {

    private static Scanner sc = new Scanner(System.in);

    @Autowired
    private DiscordBroadcaster broadcaster;

    @Autowired
    private AssettoCorsaFeedParser feedParser;

    @Autowired
    private AssettoCorsaFeedEventHandler eventHandler;


    @Autowired
    private UdpListener udpListener;

    @Value("${ECHO_INPUT}")
    private boolean echoInput;


    @Override
    public void run() {
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

    @PostConstruct
    public void init() {
        Thread t = new Thread(this);
        t.setName("AssettoCorsaFeedReader");
        t.start();
    }

}
