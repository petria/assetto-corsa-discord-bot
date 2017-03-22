package airiot.fi.bot.udp.packets;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPData.*;

/**
 * Created by Petri Airio on 17.3.2017.
 */
public enum UdpPacket {

    NEW_SESSION(ACUDPPacketEnums.UDPPacketName.NEW_SESSION,
            ACUDPPacketEnums.UDPDataField.get("proto_version", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("session_index", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("current_session_index", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("session_count", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("server_name", UTF32_DATA),
            ACUDPPacketEnums.UDPDataField.get("track_name", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("track_config", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("name", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("session_type", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("time", UINT16_DATA),
            ACUDPPacketEnums.UDPDataField.get("laps", UINT16_DATA),
            ACUDPPacketEnums.UDPDataField.get("wait_time", UINT16_DATA),
            ACUDPPacketEnums.UDPDataField.get("ambient_temp", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("track_temp", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("weather_graph", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("elapsed_ms", UINT32_DATA)

    ),

    NEW_CONNECTION(ACUDPPacketEnums.UDPPacketName.NEW_CONNECTION,
            ACUDPPacketEnums.UDPDataField.get("driver_name", UTF32_DATA),
            ACUDPPacketEnums.UDPDataField.get("driver_guid", UTF32_DATA),
            ACUDPPacketEnums.UDPDataField.get("car_id", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("car_model", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("car_skin", ASCII_DATA)
    ),

    CONNECTION_CLOSED(ACUDPPacketEnums.UDPPacketName.CONNECTION_CLOSED,
            ACUDPPacketEnums.UDPDataField.get("driver_name", UTF32_DATA),
            ACUDPPacketEnums.UDPDataField.get("driver_guid", UTF32_DATA),
            ACUDPPacketEnums.UDPDataField.get("car_id", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("car_model", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("car_skin", ASCII_DATA)
    ),

    END_SESSION(ACUDPPacketEnums.UDPPacketName.END_SESSION,
            ACUDPPacketEnums.UDPDataField.get("filename", UTF32_DATA)
    ),

    PROTO_VERSION(ACUDPPacketEnums.UDPPacketName.VERSION,
            ACUDPPacketEnums.UDPDataField.get("proto_version", UINT8_DATA)),

    CHAT(ACUDPPacketEnums.UDPPacketName.CHAT,
            ACUDPPacketEnums.UDPDataField.get("car_id", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("message", UTF32_DATA)
    ),

    CLIENT_LOADED(ACUDPPacketEnums.UDPPacketName.CLIENT_LOADED,
            ACUDPPacketEnums.UDPDataField.get("car_id", UINT8_DATA)
    ),

    SESSION_INFO(ACUDPPacketEnums.UDPPacketName.SESSION_INFO,
            ACUDPPacketEnums.UDPDataField.get("proto_version", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("session_index", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("current_session_index", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("session_count", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("server_name", UTF32_DATA),
            ACUDPPacketEnums.UDPDataField.get("track_name", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("track_config", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("name", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("session_type", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("time", INT16_DATA),
            ACUDPPacketEnums.UDPDataField.get("laps", UINT16_DATA),
            ACUDPPacketEnums.UDPDataField.get("wait_time", UINT16_DATA),
            ACUDPPacketEnums.UDPDataField.get("ambient_temp", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("track_temp", UINT8_DATA),
            ACUDPPacketEnums.UDPDataField.get("weather_graph", ASCII_DATA),
            ACUDPPacketEnums.UDPDataField.get("elapsed_ms", INT32_DATA)
    ),

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


    ),
    GET_SESSION_INFO(ACUDPPacketEnums.UDPPacketName.GET_SESSION_INFO,
            ACUDPPacketEnums.UDPDataField.get("session_index", INT16_DATA)),

    UNKNOWN(ACUDPPacketEnums.UDPPacketName.UNKNOWN,
            ACUDPPacketEnums.UDPDataField.get("type", UINT8_DATA)
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
        return UNKNOWN;
    }
}
