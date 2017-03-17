package airiot.fi;

import airiot.fi.bot.udp.UdpPacketParserImpl;
import airiot.fi.bot.udp.packets.ACUDPPacketEnums;
import airiot.fi.bot.udp.packets.ParsedUdpPacket;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPacketName.LAP_COMPLETED;
import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPacketName.VERSION;

/**
 * Created by Petri Airio on 17.3.2017.
 */
public class UdpPacketParseTest {

    private byte[] readFileToBytes(String filename) throws IOException {
        File f = new File(filename);
        return Files.readAllBytes(f.toPath());
    }


    @Test
    public void testVersion() throws IOException {
        byte[] data = readFileToBytes("packets/56_5445773002710142849");
        UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
        ParsedUdpPacket udpPacket = parser.parseUdpPacket();
        System.out.println(udpPacket.toString());

        ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
        Assert.assertEquals(VERSION, packetName);
    }

    @Test
    public void testLapCompleted() throws IOException {
        byte[] data = readFileToBytes("packets/73_690026479316835395");
        UdpPacketParserImpl parser = new UdpPacketParserImpl(data);
        ParsedUdpPacket udpPacket = parser.parseUdpPacket();
        System.out.println(udpPacket.toString());

        ACUDPPacketEnums.UDPPacketName packetName = udpPacket.getPacketName();
        Assert.assertEquals(LAP_COMPLETED, packetName);
    }


}
