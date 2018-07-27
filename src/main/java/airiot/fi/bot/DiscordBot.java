package airiot.fi.bot;


import airiot.fi.bot.udp.packets.ParsedUdpPacket;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.Javacord;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;

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

//    private DiscordAPI api;
    private org.javacord.api.DiscordApi api;
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
//            this.api = Javacord.getApi(TOKEN, true);
            run();
        } else {
            log.error("NO DISCORD BOT TOKEN DEFINED!");
            System.exit(10);
        }
    }

    private void run() {
        // connect
        log.debug("Connecting DISCORD...");
        this.api = new DiscordApiBuilder().setToken(TOKEN).login().join();
        Collection<Channel> channels = api.getChannels();
        MessageCreateListener listener = new MessageCreateListener() {
            @Override
            public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
                log.debug("ffufuf");
            }
        };
        api.addMessageCreateListener(listener);

/*        api.connect(new FutureCallback<DiscordAPI>() {

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
*/
        log.debug("Connecting DONE!");

    }

    @Override
    public void sendMessage(String message) {
        if (connected) {
            log.debug("Send message: {}", message);
            Optional<Channel> channel = api.getChannelById(288971381318877185L);
            channel.get().asTextChannel().get().sendMessage(message);

        } else {
            log.debug("Not sending, connected: {}", connected);
        }
    }

    @Override
    public void handleParsedUdpPacket(ParsedUdpPacket udpPacket) {
        Optional<Channel> channel = this.api.getChannelById(288971381318877185L);
        channel.get().asTextChannel().get().sendMessage(udpPacket.toString());

    }
}
