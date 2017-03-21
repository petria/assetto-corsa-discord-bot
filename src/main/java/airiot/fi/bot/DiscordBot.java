package airiot.fi.bot;


import airiot.fi.bot.udp.packets.ParsedUdpPacket;
import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by Petri Airio on 8.3.2017.
 */
@Service
@Slf4j
public class DiscordBot implements DiscordBroadcaster {

    @Value("${TOKEN}")
    private String TOKEN;

    @Value("${CHANNEL_NAME}")
    private String channelName;

    private DiscordAPI api;
    private boolean connected = false;

    public DiscordBot() {
    }

    @PostConstruct
    public void startService() {
        if (channelName == null || channelName.length() == 0) {
            log.error("NO BROADCAST CHANNEL_NAME DEFINED!");
            System.exit(10);
        }
        if (TOKEN != null) {
            log.debug("Connecting to Discord...");
            this.api = Javacord.getApi(TOKEN, true);
            run();
        } else {
            log.error("NO DISCORD BOT TOKEN DEFINED!");
            System.exit(10);
        }
    }

    private void run() {
        // connect
        log.debug("Connecting DISCORD...");
        api.connect(new FutureCallback<DiscordAPI>() {

            @Override
            public void onSuccess(DiscordAPI api) {

                log.debug("connected: {}", api);
                // register listener
                connected = true;
                api.registerListener(new MessageCreateListener() {
                    @Override
                    public void onMessageCreate(DiscordAPI api, Message message) {
                        log.debug("message: {}", message);

                        // check the content of the message
                        if (message.getContent().equalsIgnoreCase("ping")) {
                            // reply to the message
                            message.reply("pong");
                        }
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        log.debug("Connecting DONE!");

    }

    @Override
    public void sendMessage(String message) {
        if (connected) {
            Collection<Channel> channels = api.getChannels();
            Channel next = channels.iterator().next();

            log.debug("Sending msg to: {}", next);
            next.sendMessage(message);
        } else {
            log.debug("Not sending, connected: {}", connected);
        }
    }

    @Override
    public void handleParsedUdpPacket(ParsedUdpPacket udpPacket) {

    }
}
