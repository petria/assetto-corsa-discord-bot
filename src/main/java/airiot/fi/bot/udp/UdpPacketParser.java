package airiot.fi.bot.udp;

import airiot.fi.bot.udp.packets.ParsedUdpPacket;

/**
 * Created by Petri Airio on 17.3.2017.
 */
public interface UdpPacketParser {

    ParsedUdpPacket parseUdpPacket();

}
