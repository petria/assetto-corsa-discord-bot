package airiot.fi;

import airiot.fi.bot.udp.ByteStreamParser;
import airiot.fi.bot.udp.packets.ACUPDPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.ACUDPPacketType.ACSP_NEW_SESSION;
import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.ACUDPPacketType.ACSP_VERSION;

/**
 * Created by Petri Airio on 16.3.2017.
 */
public class UdpPacketTest {

    private byte[] readFileToBytes(String filename) throws IOException {
        File f = new File(filename);
        return Files.readAllBytes(f.toPath());
    }


    @Test
    public void testNewSessionPacket() throws IOException {
        byte[] data = readFileToBytes("50");
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        ByteStreamParser parser = new ByteStreamParser(dataInput);
        ACUPDPacket packet = parser.createPacket();
        System.out.println(packet.toString());
        Assert.assertEquals(ACSP_NEW_SESSION, packet.getType());
    }

    @Test
    public void testVersionPacket() throws IOException {
        byte[] data = readFileToBytes("56");
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        ByteStreamParser parser = new ByteStreamParser(dataInput);
        ACUPDPacket packet = parser.createPacket();
        Assert.assertEquals(ACSP_VERSION, packet.getType());
    }

}
