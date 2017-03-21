package airiot.fi.bot.udp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * Created by Petri Airio on 14.3.2017.
 */
@Component
@Slf4j
public class UdpProxyImpl implements UdpProxy, Runnable {

    private final UdpServer udpServer;


    public UdpProxyImpl(UdpServer udpServer) {
        this.udpServer = udpServer;
    }

    @Override
    public void forwardPacket(byte[] data, int size) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress host = InetAddress.getByName("localhost");

            int forwardToPort = 11600;
            DatagramPacket packet = new DatagramPacket(data, size, host, forwardToPort);
            socket.send(packet);
//            log.debug("PROXY Sent to port {} packet, size: {}", forwardToPort, size);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null)
                socket.close();
        }
    }


    public void run() {
        byte[] receiveData = new byte[1024];
        int proxyReceivePort = 11500;
        try {
            DatagramSocket serverSocket = new DatagramSocket(proxyReceivePort);
            log.debug("Starting UDP PROXY receive on port: {}", proxyReceivePort);
            while (true) {
//                log.debug("PROXY waiting UDP data");
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                int size = receivePacket.getLength();
                byte[] dataPacket = Arrays.copyOf(receivePacket.getData(), size);
                int type = dataPacket[0];
                String filename = String.format("%x", type);
                writeBytes(filename, dataPacket, size);
                this.udpServer.addUdpBytePacket(dataPacket);
            }
        } catch (Exception e) {
            log.error("Exception", e);
        }

    }

    private void writeBytes(String filename, byte[] content, int size) {
        OutputStream out = null;
        try {
            File tmpFile = new File(filename);
            out = new FileOutputStream(tmpFile);
            out.write(content, 0, size);
//            log.debug("{} - {] bytes", filename, size);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init() {
        Thread t = new Thread(this);
        t.setName("UdpProxyReceiver");
        t.start();
    }

}
