package airiot.fi.bot.udp;

import airiot.fi.bot.udp.packets.ACUDPPacketEnums;
import airiot.fi.bot.udp.packets.ACUPDPacket;
import com.google.common.io.ByteArrayDataInput;
import com.ibm.icu.impl.UTF32;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPacketField.*;

/**
 * Created by Petri Airio on 14.3.2017.
 */
public class ByteStreamParser {

    private final ByteArrayDataInput stream;

    public ByteStreamParser(ByteArrayDataInput stream) {
        this.stream = stream;
    }

    private String getFieldValue(ACUDPPacketEnums.UDPPacketField field) {
        String value = null;
        switch (field.getFieldType()) {
            case ASCII:
                value = getASCII(stream);
                break;
            case BOOL:
                value = getBool(stream);
            case FLOAT:
                value = getFloat(stream);
                break;
            case UINT8:
                value = getUINT8(stream);
                break;
            case UINT16:
                value = getUINT16(stream);
                break;
            case UINT32:
                value = getUINT32(stream);
                break;
            case UTF32:
                value = getUTF32(stream);
                break;
            default:
                System.out.printf("not parsed: %s\n", field.getFieldType());
        }
        return value;
    }

    public ACUPDPacket createPacket() {
        int type = stream.readUnsignedByte();
        ACUDPPacketEnums.ACUDPPacketType packetType = ACUDPPacketEnums.ACUDPPacketType.getByType(type);
        ACUPDPacket packet = new ACUPDPacket(packetType);

        for (ACUDPPacketEnums.UDPPacketField field : packetType.getFields()) {
            String value = getFieldValue(field);
            if (value != null) {
                packet.setFieldValue(field, value);
            }
        }

        return packet;
    }

    private String getLeaderBoardDataRows(ByteArrayDataInput stream, ACUDPPacketEnums.UDPPrimitive fieldType) {
        int rowCount = stream.readUnsignedByte();
        String rows = "";
        ACUDPPacketEnums.UDPPacketField[] fields = {RCAR_ID, RTIME, RLAPS, HAS_COMPLETED_FLAG};
        for (int i = 0; i < rowCount; i++) {
            String row = "<";
            for (ACUDPPacketEnums.UDPPacketField field : fields) {
                String value = getFieldValue(field);
                row += " " + field.toString() + "=" + value;
            }
            row += ">";

            rows += row + "\n";
        }

        return rows;
    }

    private String getBool(ByteArrayDataInput stream) {
        int uint8 = stream.readUnsignedByte();
        return "" + (uint8 == 0);
    }

    private String getFloat(ByteArrayDataInput stream) {
        float data = stream.readFloat();
        return data + "";
    }

    private String getUINT32(ByteArrayDataInput stream) {
        int data = stream.readInt();
        return data + "";
    }

    private String getUINT16(ByteArrayDataInput stream) {
        int data = stream.readUnsignedShort();
        return data + "";
    }

    private String getUINT8(ByteArrayDataInput stream) {
        int uint8 = stream.readUnsignedByte();
        return uint8 + "";
    }

    private String getASCII(ByteArrayDataInput stream) {
        int size = stream.readUnsignedByte();
        byte[] bytes = new byte[size];
        int idx = 0;
        for (int count = 0; count < bytes.length; count++) {
            bytes[idx] = (byte) stream.readUnsignedByte();
            idx++;
        }
        String data = new String(bytes);
        return data;
    }

    private String getUTF32(ByteArrayDataInput stream) {
        int size = stream.readUnsignedByte();
        byte[] utfBytes = new byte[size * 4];
        int idx = 0;
        for (int count = 0; count < size * 4; count++) {
            utfBytes[idx] = (byte) stream.readUnsignedByte();
            idx++;
        }
        UTF32 leInstance = UTF32.getLEInstance();
        String data = leInstance.fromBytes(utfBytes);
        return data;
    }

}
