package airiot.fi;

import airiot.fi.bot.udp.packets.ACUDPPacketEnums;
import airiot.fi.bot.udp.packets.ParsedUdpPacket;
import airiot.fi.bot.udp.packets.UdpPacketParserImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPacketName.*;

/**
 * Created by Petri Airio on 17.3.2017.
 */
public class UdpPacketParseTest {

    private byte[] readFileToBytes(String filename) throws IOException {
        File f = new File(filename);
        return Files.readAllBytes(f.toPath());
    }

    private File[] getMatchingFiles(String directory, String pattern) {
        File dir = new File(directory);
        return dir.listFiles((d, name) -> name.matches(pattern));
    }

    @Test
    public void testNewSession() throws IOException {
        File[] files = getMatchingFiles("packets/", "50_.*");
        for (File file : files) {
            byte[] data = Files.readAllBytes(file.toPath());
            UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
            ParsedUdpPacket udpPacket = parser.parseUdpPacket();
            System.out.println(udpPacket.toString());

            ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
            Assert.assertEquals(NEW_SESSION, packetName);
        }
    }

    @Test
    public void testNewConnection() throws IOException {
        File[] files = getMatchingFiles("packets/", "51_.*");
        for (File file : files) {
            byte[] data = Files.readAllBytes(file.toPath());
            UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
            ParsedUdpPacket udpPacket = parser.parseUdpPacket();
            System.out.println(udpPacket.toString());

            ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
            Assert.assertEquals(NEW_CONNECTION, packetName);
        }
    }

    @Test
    public void testConnectionClosed() throws IOException {
        File[] files = getMatchingFiles("packets/", "52_.*");
        for (File file : files) {
            byte[] data = Files.readAllBytes(file.toPath());

            UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
            ParsedUdpPacket udpPacket = parser.parseUdpPacket();
            System.out.println(udpPacket.toString());

            ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
            Assert.assertEquals(CONNECTION_CLOSED, packetName);
        }
    }

    @Test
    public void testEndSession() throws IOException {
        File[] files = getMatchingFiles("packets/", "55_.*");
        for (File file : files) {
            byte[] data = Files.readAllBytes(file.toPath());
            UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
            ParsedUdpPacket udpPacket = parser.parseUdpPacket();
            System.out.println(udpPacket.toString());

            ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
            Assert.assertEquals(END_SESSION, packetName);
        }
    }

    @Test
    public void testVersion() throws IOException {
        File[] files = getMatchingFiles("packets/", "56_.*");
        for (File file : files) {
            byte[] data = Files.readAllBytes(file.toPath());
            UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
            ParsedUdpPacket udpPacket = parser.parseUdpPacket();
            System.out.println(udpPacket.toString());

            ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
            Assert.assertEquals(VERSION, packetName);
        }
    }

    @Test
    public void testChat() throws IOException {
        File[] files = getMatchingFiles("packets/", "57_.*");
        for (File file : files) {
            byte[] data = Files.readAllBytes(file.toPath());
            UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
            ParsedUdpPacket udpPacket = parser.parseUdpPacket();
            System.out.println(udpPacket.toString());

            ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
            Assert.assertEquals(CHAT, packetName);
        }
    }


    @Test
    public void testClientLoaded() throws IOException {
        File[] files = getMatchingFiles("packets/", "58_.*");
        for (File file : files) {
            byte[] data = Files.readAllBytes(file.toPath());
            UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
            ParsedUdpPacket udpPacket = parser.parseUdpPacket();
            System.out.println(udpPacket.toString());

            ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
            Assert.assertEquals(CLIENT_LOADED, packetName);
        }
    }

    @Test
    public void testLapCompleted() throws IOException {
        File[] files = getMatchingFiles("packets/", "73_.*");
        for (File file : files) {
            byte[] data = Files.readAllBytes(file.toPath());
            UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
            ParsedUdpPacket udpPacket = parser.parseUdpPacket();
            System.out.println(udpPacket.toString());

            ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
            Assert.assertEquals(LAP_COMPLETED, packetName);
        }
    }


}
