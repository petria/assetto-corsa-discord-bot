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

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.ACUDPPacketType.*;

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
    public void testNewConnectionPacket() throws IOException {
        byte[] data = readFileToBytes("packets/51_998706441100862361");
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        ByteStreamParser parser = new ByteStreamParser(dataInput);
        ACUPDPacket packet = parser.createPacket();
        System.out.println(packet.toString());
        Assert.assertEquals(ACSP_NEW_CONNECTION, packet.getType());
    }

    @Test
    public void testConnectionClosedPacket() throws IOException {
        byte[] data = readFileToBytes("packets/52_7670980051013914962");
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        ByteStreamParser parser = new ByteStreamParser(dataInput);
        ACUPDPacket packet = parser.createPacket();
        System.out.println(packet.toString());
        Assert.assertEquals(ACSP_CONNECTION_CLOSED, packet.getType());
    }

    @Test
    public void testEndSessionPacket() throws IOException {
        byte[] data = readFileToBytes("packets/55_5509519819115648313");
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        ByteStreamParser parser = new ByteStreamParser(dataInput);
        ACUPDPacket packet = parser.createPacket();
        System.out.println(packet.toString());
        Assert.assertEquals(ACSP_END_SESSION, packet.getType());
    }


    @Test
    public void testVersionPacket() throws IOException {
        byte[] data = readFileToBytes("packets/56_5445773002710142849");
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        ByteStreamParser parser = new ByteStreamParser(dataInput);
        ACUPDPacket packet = parser.createPacket();
        System.out.println(packet.toString());
        Assert.assertEquals(ACSP_VERSION, packet.getType());
    }

    @Test
    public void testChatPacket() throws IOException {
        byte[] data = readFileToBytes("packets/57_1003815470007676071");
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        ByteStreamParser parser = new ByteStreamParser(dataInput);
        ACUPDPacket packet = parser.createPacket();
        System.out.println(packet.toString());
        Assert.assertEquals(ACSP_CHAT, packet.getType());
    }

    @Test
    public void testClientLoaded() throws IOException {
        byte[] data = readFileToBytes("packets/58_5611524326012262458");
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        ByteStreamParser parser = new ByteStreamParser(dataInput);
        ACUPDPacket packet = parser.createPacket();
        System.out.println(packet.toString());
        Assert.assertEquals(ACSP_CLIENT_LOADED, packet.getType());
    }

/*    @Test
    public void testLapCompleted() throws IOException {
        {
            byte[] data = readFileToBytes("packets/73_690026479316835395");
            ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
            ByteStreamParser parser = new ByteStreamParser(dataInput);
            ACUPDPacket packet = parser.createPacket();
            System.out.println(packet.toString());
            Assert.assertEquals(ACSP_LAP_COMPLETED, packet.getType());
        }
        {
            byte[] data = readFileToBytes("packets/73_3929932163472833326");
            ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
            ByteStreamParser parser = new ByteStreamParser(dataInput);
            ACUPDPacket packet = parser.createPacket();
            System.out.println(packet.toString());
            Assert.assertEquals(ACSP_LAP_COMPLETED, packet.getType());

        }

    }
*/

}
