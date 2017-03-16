package airiot.fi.bot.udp.packets;


import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPacketField.*;
import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPacketFieldType.*;

/**
 * Created by Petri Airio on 16.3.2017.
 */
public class ACUDPPacketEnums {

    public enum UDPPacketFieldType {
        ASCII,
        UINT8,
        UINT16,
        UINT32,
        UTF32,
    }

    public enum UDPPacketField {

        PROTO_VERSION(UINT8),
        SESSION_INDEX(UINT8),
        CURRENT_SESSION_INDEX(UINT8),
        SESSION_COUNT(UINT8),
        SERVER_NAME(UTF32),
        TRACK_NAME(ASCII),
        TRACK_CONFIG(ASCII),
        NAME(ASCII),
        SESSION_TYPE(UINT8),
        TIME(UINT16),
        LAPS(UINT16),
        WAIT_TIME(UINT16),
        AMBIENT_TEMP(UINT8),
        TRACK_TEMP(UINT8),
        WEATHER_GRAPH(ASCII),
        ELAPSED_MS(UINT32);

        private final UDPPacketFieldType fieldType;

        UDPPacketField(UDPPacketFieldType fieldType) {
            this.fieldType = fieldType;
        }

        public UDPPacketFieldType getFieldType() {
            return fieldType;
        }
    }


    public enum ACUDPPacketType {

        ACSP_NEW_SESSION(50, new UDPPacketField[]{PROTO_VERSION, SESSION_INDEX, CURRENT_SESSION_INDEX, SESSION_COUNT, SERVER_NAME, TRACK_NAME, TRACK_CONFIG, NAME, SESSION_TYPE, TIME, LAPS, WAIT_TIME, AMBIENT_TEMP, TRACK_TEMP, WEATHER_GRAPH, ELAPSED_MS}),
        ACSP_VERSION(56, new UDPPacketField[]{PROTO_VERSION}),
        ACSP_UNKNONW_PACKET(-1, null);

        private final int type;
        private final UDPPacketField[] fields;

        ACUDPPacketType(int type, UDPPacketField[] fields) {
            this.type = type;
            this.fields = fields;
        }

        public static ACUDPPacketType getByType(int type) {
            for (ACUDPPacketType packet : values()) {
                if (packet.getType() == type) {
                    return packet;
                }
            }
            return ACSP_UNKNONW_PACKET;
        }

        public int getType() {
            return type;
        }

        public UDPPacketField[] getFields() {
            return fields;
        }
    }
}
