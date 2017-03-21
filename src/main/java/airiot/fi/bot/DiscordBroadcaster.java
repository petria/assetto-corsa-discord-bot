package airiot.fi.bot;

import airiot.fi.bot.udp.packets.ParsedUdpPacket;

/**
 * Created by Petri Airio on 8.3.2017.
 */
public interface DiscordBroadcaster {

    void sendMessage(String message);

    void handleParsedUdpPacket(ParsedUdpPacket udpPacket);

}
