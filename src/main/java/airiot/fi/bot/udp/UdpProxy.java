package airiot.fi.bot.udp;

/**
 * Created by Petri Airio on 14.3.2017.
 */
public interface UdpProxy {


    void forwardPacket(byte[] data);

}
