package airiot.fi.bot;

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
public class DiscordBot implements  DiscordBroadcaster {

    @Value("${TOKEN}")
    private String TOKEN;

    private DiscordAPI api;
    private static boolean connected = false;

    public DiscordBot() {
    }

    @PostConstruct
    public void startService() {
        if (TOKEN != null) {
            this.api = Javacord.getApi(TOKEN, true);
            service();
        } else {
            log.error("NO DISCORD BOT TOKEN DEFINED!");
        }
    }

    public void service() {

        // connect
        api.connect(new FutureCallback<DiscordAPI>() {



            @Override
            public void onSuccess(DiscordAPI api) {

                log.debug("connected: {}", api);
                // register listener
                DiscordBot.connected = true;
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
    }

    @Override
    public void sendMessage(String message) {
        Collection<Channel> channels = api.getChannels();
        Channel next = channels.iterator().next();
        if (DiscordBot.connected) {
            log.debug("Sending msg to: {}", next);
            next.sendMessage(message);
        } else {
            log.debug("Not sending, connected: {}", DiscordBot.connected);
        }
    }
}
