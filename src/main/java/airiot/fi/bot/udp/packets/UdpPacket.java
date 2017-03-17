package airiot.fi.bot.udp.packets;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPData.*;

/**
 * Created by Petri Airio on 17.3.2017.
 */
public enum UdpPacket {

    PROTO_VERSION(ACUDPPacketEnums.UDPPacketName.VERSION, ACUDPPacketEnums.UDPDataField.get("proto_version", UINT8_DATA)),

    LAP_COMPLETED(ACUDPPacketEnums.UDPPacketName.LAP_COMPLETED,
            ACUDPPacketEnums.UDPDataField.get("car_id", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("lap_time", UINT32_DATA),
            ACUDPPacketEnums.UDPDataField.get("cuts", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("cars", STRUCT_DATA)
                    .addStructField("rcar_id", UINT8_DATA)
                    .addStructField("rtime", UINT32_DATA)
                    .addStructField("rlaps", UINT16_DATA)
                    .addStructField("has_completed_flag", BOOL_DATA),
            ACUDPPacketEnums.UDPDataField.get("grip_level", FLOAT_DATA)
    );


    private final ACUDPPacketEnums.UDPDataField[] dataFields;
    private final ACUDPPacketEnums.UDPPacketName packetName;

    UdpPacket(ACUDPPacketEnums.UDPPacketName packetName, ACUDPPacketEnums.UDPDataField... dataFields) {
        this.packetName = packetName;
        this.dataFields = dataFields;
    }

    public ACUDPPacketEnums.UDPDataField[] getDataFields() {
        return dataFields;
    }

    public ACUDPPacketEnums.UDPPacketName getPacketName() {
        return packetName;
    }

    public static UdpPacket getByType(int type) {
        for (UdpPacket packet : values()) {
            if (packet.getPacketName().getType() == type) {
                return packet;
            }
        }
        return null;
    }
}
