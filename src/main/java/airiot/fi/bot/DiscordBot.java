package airiot.fi.bot;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by Petri Airio on 8.3.2017.
 */
@Service
@Slf4j
public class DiscordBot implements  DiscordBroadcaster {

    private static final String TOKEN = "Mjg4OTY0MTQ3NzIxNDA0NDE2.C6FikA.7S0d61a7Tjy_Qx_-fFrQkgqI_04";
    private DiscordAPI api = Javacord.getApi(TOKEN, true);

    public DiscordBot() {
    }

    @PostConstruct
    public void startService() {
        service();
    }

    public void service() {

        // connect
        api.connect(new FutureCallback<DiscordAPI>() {



            @Override
            public void onSuccess(DiscordAPI api) {
                log.debug("connected: {}", api);
                // register listener

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
        log.debug("Sending msg to: {}", next);
        next.sendMessage(message);
    }
}
