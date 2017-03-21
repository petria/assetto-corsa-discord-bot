package airiot.fi.bot.udp;

/**
 * Created by Petri Airio on 21.3.2017.
 */
public interface UdpServer {


    void addBroadCastMessage(String message);

    void addUdpBytePacket(byte[] packet);

}
