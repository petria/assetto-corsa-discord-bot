package airiot.fi.bot.udp;

import airiot.fi.bot.DiscordBroadcaster;
import airiot.fi.bot.udp.packets.ParsedUdpPacket;
import airiot.fi.bot.udp.packets.UdpPacketParserImpl;
import com.ibm.icu.impl.UTF32;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPacketName.BROADCAST_CHAT;
import static com.igormaznitsa.jbbp.io.JBBPOut.BeginBin;

/**
 * Created by Petri Airio on 13.3.2017.
 */
@Service
@Slf4j
public class UdpServerImpl implements Runnable, UdpServer {

    @Value("${UDP_PLUGIN_PORT}")
    private int port;

    @Autowired
    private DiscordBroadcaster discordBroadcaster;

    private final Queue<byte[]> udpPacketQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        byte[] receiveData = new byte[1024];

        try {
            log.debug("Starting UDP receive on port: {}", port);

            DatagramSocket serverSocket = new DatagramSocket(port);
            serverSocket.setSoTimeout(50);

            UdpProxyImpl proxy = new UdpProxyImpl(this);
            proxy.init();

            while (true) {
//                log.debug("waiting UDP data");
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    serverSocket.receive(receivePacket);
                    int len = receivePacket.getLength();

                    proxy.forwardPacket(receivePacket.getData(), len);

                    UdpPacketParserImpl parser = new UdpPacketParserImpl(receivePacket.getData());
                    ParsedUdpPacket udpPacket = parser.parseUdpPacket();
                    if (udpPacket != null) {
                        discordBroadcaster.handleParsedUdpPacket(udpPacket);
                    }

                } catch (SocketTimeoutException ste) {
                    sendPackets(serverSocket);
                }

            }
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    private int serverLocalPort = 11000;

    private void sendPackets(DatagramSocket serverSocket) {
        while (!udpPacketQueue.isEmpty()) {
            byte[] data = udpPacketQueue.poll();
            try {
                InetAddress host = InetAddress.getByName("localhost");
                DatagramPacket packet = new DatagramPacket(data, data.length, host, serverLocalPort);
                serverSocket.send(packet);
//                log.debug("Sent packet, size: {}", data.length);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] createBroadCastPacket(String message) {
        try {
            UTF32 leInstance = UTF32.getLEInstance();
            byte[] header = {(byte) BROADCAST_CHAT.getType(), (byte) message.length()};
            byte[] messageBytes = leInstance.toBytes(message);
            byte[] udpBytes = BeginBin().Byte(header).Byte(messageBytes).End().toByteArray();
            return udpBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostConstruct
    public void init() {
        Thread t = new Thread(this);
        t.setName("UdpServerImpl");
        t.start();
    }

    @Override
    public void addBroadCastMessage(String message) {
        this.udpPacketQueue.add(createBroadCastPacket(message));
    }

    @Override
    public void addUdpBytePacket(byte[] packet) {
        this.udpPacketQueue.add(packet);
    }
}
