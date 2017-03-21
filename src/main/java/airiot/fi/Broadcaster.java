package airiot.fi;

import com.ibm.icu.impl.UTF32;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static com.igormaznitsa.jbbp.io.JBBPOut.BeginBin;

/**
 * Created by Petri Airio on 21.3.2017.
 */
public class Broadcaster {


    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(-1);
        }

        int UDP_PLUGIN_ADDRESS = 12000;    // server_config.ini: UDP_PLUGIN_ADDRESS :port
        int UDP_PLUGIN_LOCAL_PORT = 11000; // server_config.ini: UDP_PLUGIN_LOCAL_PORT

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(UDP_PLUGIN_ADDRESS);
            InetAddress host = InetAddress.getByName("localhost");
            byte data[] = createBroadCastPacket(args[0]);

            DatagramPacket packet = new DatagramPacket(data, data.length, host, UDP_PLUGIN_LOCAL_PORT);
            socket.send(packet);

        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
        } finally {
            if (socket != null)
                socket.close();
        }

    }

    private static byte[] createBroadCastPacket(String message) throws IOException {
        UTF32 leInstance = UTF32.getLEInstance();
        byte[] header = {(byte) 203, (byte) message.length()};
        byte[] messageBytes = leInstance.toBytes(message);
        return BeginBin().Byte(header).Byte(messageBytes).End().toByteArray();
    }

}
