package airiot.fi.bot.udp;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.ibm.icu.impl.UTF32;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 14.3.2017.
 */
public class ByteStreamParser {
    //                                    A                     a                       term
    private static byte[] UTF32_DATA = {0x41, 0x00, 0x00, 0x00, 0x61, 0x00, 0x00, 0x00, 0xf};


    private void parseUTF32fromStream(ByteArrayInputStream stream) {
        int b = stream.read();

        int foo = 0;
    }


    public static byte[] getBytesUntilMarker(ByteArrayDataInput dataInput, int endMarker) {
        boolean found = false;
        List<Integer> bytes = new ArrayList<>();
        while (!found) {
            int uByte = dataInput.readUnsignedByte();
            if (uByte != endMarker) {
                bytes.add(uByte);
            } else {
                break;
            }
        }
        byte[] array = new byte[bytes.size()];
        int idx = 0;
        for (int i : bytes) {
            array[idx] = (byte) i;
            idx++;
        }
        return array;

    }

    public static void main(String[] args) throws IOException {
        ByteStreamParser parser = new ByteStreamParser();
        ByteArrayInputStream stream = new ByteArrayInputStream(UTF32_DATA);

        Path path = Paths.get("50");
        byte[] data = Files.readAllBytes(path);

        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);

        int uByte1 = dataInput.readUnsignedByte();
        int uByte2 = dataInput.readUnsignedByte();
        int uByte3 = dataInput.readUnsignedByte();
        int uByte4 = dataInput.readUnsignedByte();
        int uByte5 = dataInput.readUnsignedByte();
        int uByte6 = dataInput.readUnsignedByte();

        byte[] utf32 = getBytesUntilMarker(dataInput, 0xf);
        UTF32 leInstance = UTF32.getLEInstance();
        String name = leInstance.fromBytes(utf32);
        int foo = 0;

    }

}
