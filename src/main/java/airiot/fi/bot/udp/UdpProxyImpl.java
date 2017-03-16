package airiot.fi.bot.udp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Petri Airio on 14.3.2017.
 */
@Component
@Slf4j
public class UdpProxyImpl implements UdpProxy {


    private int forwardPort = 13000;

    @Override
    public void forwardPacket(byte[] data) {
        DatagramSocket socket = null;
        try {
            InetAddress host = InetAddress.getByName("localhost");
            socket = new DatagramSocket();

            DatagramPacket packet = new DatagramPacket(data, data.length, host, forwardPort);
            socket.send(packet);
            log.debug("Sent packet, size: {}", data.length);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null)
                socket.close();
        }

    }


    public void startProxyListener() {

    }

}
