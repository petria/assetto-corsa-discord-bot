package airiot.fi;

import airiot.fi.bot.DiscordBroadcaster;
import airiot.fi.bot.udp.UdpServer;
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
public class CommandReader implements Runnable {

    private static Scanner sc = new Scanner(System.in);

    @Autowired
    private DiscordBroadcaster broadcaster;

    @Autowired
    private UdpServer udpServer;

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
        }
    }

    public void parseLine(String line) {
        if (line.startsWith("!broad ")) {
            String message = line.replaceFirst("!broad ", "");
            if (message.length() > 0) {
                udpServer.addBroadCastMessage(message);
            }
        }
    }


    @PostConstruct
    public void init() {
        Thread t = new Thread(this);
        t.setName("CommandReader");
        t.start();
    }

}
