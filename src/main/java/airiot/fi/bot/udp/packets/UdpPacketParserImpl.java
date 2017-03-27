package airiot.fi.bot.udp.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.ibm.icu.impl.UTF32;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPData.STRUCT_DATA;

/**
 * Created by Petri Airio on 17.3.2017.
 */
@Slf4j
public class UdpPacketParserImpl implements UdpPacketParser {

    private ByteArrayDataInput stream;
    private byte[] udpData;

    public UdpPacketParserImpl(byte[] udpData) {
        this.stream = ByteStreams.newDataInput(udpData);
        this.udpData = udpData;
    }

    //    private String parseField(ACUDPPacketEnums.UDPDataField field) {
    private String parseField(ACUDPPacketEnums.UDPData udpData) {
//        ACUDPPacketEnums.UDPData udpData = field.getUDPData();
        String parsed = null;
        switch (udpData) {
            case ASCII_DATA:
                parsed = getASCII(this.stream);
                break;
            case BOOL_DATA:
                parsed = getBool(this.stream);
                break;
            case FLOAT_DATA:
                parsed = getFloat(this.stream);
                break;
            case UINT8_DATA:
                parsed = getUINT8(this.stream);
                break;
            case UINT16_DATA:
                parsed = getUINT16(this.stream);
                break;
            case UINT32_DATA:
                parsed = getUINT32(this.stream);
                break;
            case INT16_DATA:
                parsed = getINT16(this.stream);
                break;
            case INT32_DATA:
                parsed = getINT32(this.stream);
                break;
            case UTF32_DATA:
                parsed = getUTF32(this.stream);
                break;
        }
        return parsed;
    }

    private String parseField(ACUDPPacketEnums.UDPDataField field) {
        String parsed;
        if (field.getUDPData() == STRUCT_DATA) {
            parsed = parseStruct(this.stream, field);
        } else {
            parsed = parseField(field.getUDPData());
        }
        return parsed;
    }

    @Override
    public ParsedUdpPacket parseUdpPacket() {
        int type = stream.readUnsignedByte();
        UdpPacket udpPacket = UdpPacket.getByType(type);
        ParsedUdpPacket parsedUdpPacket = new ParsedUdpPacket(udpPacket.getPacketName());
        if (udpPacket != UdpPacket.UNKNOWN) {

            for (ACUDPPacketEnums.UDPDataField field : udpPacket.getDataFields()) {
                String parsed = parseField(field);
                if (parsed != null) {
                    parsedUdpPacket.setParsed(field, parsed);
                } else {
                    log.error("Not parsed: {} - {}", field.getName(), field.getUDPData().toString());
                }
            }
        } else {
            parsedUdpPacket.setParsed(udpPacket.getDataFields()[0], type + "");
//            log.error("Unknown packet type: {}", type);
        }
        return parsedUdpPacket;

    }

    private String parseStruct(ByteArrayDataInput stream, ACUDPPacketEnums.UDPDataField structField) {
        int structCount = stream.readUnsignedByte();
        List<ACUDPPacketEnums.UDPDataField.StructField> structFields = structField.getStructFields();
        String str = "<" + structField.getName() + " ";
        for (int i = 0; i < structCount; i++) {
            String value = "<";
            for (ACUDPPacketEnums.UDPDataField.StructField sf : structFields) {
                String parsed = parseField(sf.field); // TODO
                value += sf.name + " ='" + parsed + "' ";
            }
            str += value += "> ";
        }
        return str;
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
        byte[] buf = new byte[4];
        stream.readFully(buf, 0, 4);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(buf);

        int t = byteBuffer.getInt(0);
        return "" + t;
    }

    private String getINT32(ByteArrayDataInput stream) {
        return getUINT32(stream);
    }

    private String getUINT16(ByteArrayDataInput stream) {
        byte[] buf = new byte[2];
        stream.readFully(buf, 0, 2);

        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(buf);

        short t = byteBuffer.getShort(0);
        return "" + t;
    }

    private String getINT16(ByteArrayDataInput stream) {
        return getUINT16(stream);
    }


    private String getUINT8(ByteArrayDataInput stream) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte) stream.readUnsignedByte());

        int uint8 = byteBuffer.get(0);
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
