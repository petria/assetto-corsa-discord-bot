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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    private int UDP_PLUGIN_PORT;

    private String LOG_DIR = "logs/";

    @Autowired
    private DiscordBroadcaster discordBroadcaster;

    private final Queue<byte[]> udpPacketQueue = new ConcurrentLinkedQueue<>();

    private boolean logOk = false;

    public UdpServerImpl() {

    }

    @Override
    public void run() {
        byte[] receiveData = new byte[1024];

        try {
            log.debug("Starting UDP receive on port: {}", UDP_PLUGIN_PORT);

            DatagramSocket serverSocket = new DatagramSocket(UDP_PLUGIN_PORT);
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
                    logUdpPacket(">> ACSERVER", udpPacket);

                    try {
                        discordBroadcaster.handleParsedUdpPacket(udpPacket);
                    } catch (Exception e) {
                        log.error("Exception", e);
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
            } catch (IOException e) {
                log.error("error while purging output queue", e);
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
        checkLogDir();
        Thread t = new Thread(this);
        t.setName("UdpServerImpl");
        t.start();
    }

    private void checkLogDir() {
        File f = new File(LOG_DIR);
        if (!f.exists()) {
            boolean ok = f.mkdirs();
            if (ok) {
                log.debug("Log dir created: {}", f.getAbsolutePath());
                this.logOk = true;
            } else {
                log.warn("Log dir create failed: {}", f.getAbsolutePath());
                log.warn("Loging is disabled!");
            }
        } else {
            this.logOk = true;
        }
    }

    @Override
    public void addBroadCastMessage(String message) {
        this.udpPacketQueue.add(createBroadCastPacket(message));
    }

    @Override
    public void addUdpBytePacket(byte[] packet) {
        addToQueueAndLog("<< PROXY", packet);
    }

    private void addToQueueAndLog(String direction, byte[] udpPacket) {
        this.udpPacketQueue.add(udpPacket);
        logUdpPacket(direction, udpPacket);
    }

    private void logUdpPacket(String direction, byte[] udpPacket) {
        UdpPacketParserImpl parser = new UdpPacketParserImpl(udpPacket);
        ParsedUdpPacket parsedUdpPacket = parser.parseUdpPacket();
        logUdpPacket(direction, parsedUdpPacket);
    }

    private void logUdpPacket(String direction, ParsedUdpPacket parsedUdpPacket) {

        if (this.logOk) {

            String message = String.format("%-12s : %s\n", direction, parsedUdpPacket.toString());

            String log = "logs/packets_log.txt";
            BufferedWriter bw = null;
            FileWriter fw = null;

            try {
                File file = new File(log);
                if (!file.exists()) {
                    file.createNewFile();
                }

                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);

                bw.write(message);

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                try {

                    if (bw != null)
                        bw.close();

                    if (fw != null)
                        fw.close();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }
            }
        }
    }

}
