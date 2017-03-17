package airiot.fi.bot.udp.packets;


import java.util.ArrayList;
import java.util.List;

import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPacketField.*;
import static airiot.fi.bot.udp.packets.ACUDPPacketEnums.UDPPrimitive.*;

/**
 * Created by Petri Airio on 16.3.2017.
 */
public class ACUDPPacketEnums {

    public enum UDPPrimitive {
        ASCII,
        BOOL,
        FLOAT,
        STRUCT,
        UINT8,
        UINT16,
        UINT32,
        UTF32;

        UDPPrimitive() {
        }

    }

    public enum UDPData {
        ASCII_DATA(ASCII),
        BOOL_DATA(BOOL),
        FLOAT_DATA(FLOAT),
        STRUCT_DATA(STRUCT),
        UINT8_DATA(UINT8),
        UINT16_DATA(UINT16),
        UINT32_DATA(UINT32),
        UTF32_DATA(UTF32);

        private final UDPPrimitive[] primitives;

        UDPData(UDPPrimitive... primitives) {
            this.primitives = primitives;
        }

        public UDPPrimitive[] getPrimitives() {
            return primitives;
        }

    }

    public enum UDPPacketName {
        NEW_SESSION(50),
        VERSION(56),
        LAP_COMPLETED(73);

        private final int type;

        UDPPacketName(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    static public class UDPDataField {

        public static class StructField {
            public String name;
            public UDPData field;
            public ACUDPPacketEnums.UDPDataField udpDataField;
        }

        private String name;
        private UDPData dataValue;
        private List<StructField> structFields;

        UDPDataField(String name, UDPData dataValue) {
            this.name = name;
            this.dataValue = dataValue;
            this.structFields = new ArrayList<>();
        }


        public static UDPDataField get(String name, UDPData dataValue) {
            return new UDPDataField(name, dataValue);
        }

        public UDPDataField addStructField(String name, UDPData dataField) {
            StructField structField = new StructField();
            structField.name = name;
            structField.field = dataField;
            structFields.add(structField);
            return this;
        }

        public String getName() {
            return name;
        }

        public UDPData getUDPData() {
            return dataValue;
        }

        public List<StructField> getStructFields() {
            return structFields;
        }

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
        ELAPSED_MS(UINT32),

        DRIVER_NAME(UTF32),
        DRIVER_GUID(UTF32),
        CAR_ID(UINT8),
        CAR_MODEL(ASCII),
        CAR_SKIN(ASCII),

        FILENAME(UTF32),

        MESSAGE(UTF32),

        LAP_TIME(UINT32),
        CUTS(UINT8),
        //        CARS(LEADERBOARD_DATA_ROW),
        GRIP_LEVEL(FLOAT),

        RCAR_ID(UINT8),
        RTIME(UINT32),
        RLAPS(UINT16),
        HAS_COMPLETED_FLAG(BOOL);

        private final UDPPrimitive fieldType;

        UDPPacketField(UDPPrimitive fieldType) {
            this.fieldType = fieldType;
        }

        public UDPPrimitive getFieldType() {
            return fieldType;
        }
    }


    public enum LeaderboardEntry {
        ENTRY_ROW(RCAR_ID, RTIME, RLAPS, HAS_COMPLETED_FLAG);

        private final UDPPacketField[] fields;

        LeaderboardEntry(UDPPacketField... fields) {
            this.fields = fields;
        }
    }

    public enum ACUDPPacketType {

        ACSP_NEW_SESSION(50, PROTO_VERSION, SESSION_INDEX, CURRENT_SESSION_INDEX, SESSION_COUNT, SERVER_NAME, TRACK_NAME, TRACK_CONFIG, NAME, SESSION_TYPE, TIME, LAPS, WAIT_TIME, AMBIENT_TEMP, TRACK_TEMP, WEATHER_GRAPH, ELAPSED_MS),
        ACSP_NEW_CONNECTION(51, DRIVER_NAME, DRIVER_GUID, CAR_ID, CAR_MODEL, CAR_SKIN),
        ACSP_CONNECTION_CLOSED(52, DRIVER_NAME, DRIVER_GUID, CAR_ID, CAR_MODEL, CAR_SKIN),
        ACSP_END_SESSION(55, FILENAME),
        ACSP_VERSION(56, PROTO_VERSION),
        ACSP_CHAT(57, CAR_ID, MESSAGE),
        ACSP_CLIENT_LOADED(58, CAR_ID),
        //        ACSP_LAP_COMPLETED(73, CAR_ID, LAP_TIME, CUTS, CARS, GRIP_LEVEL),
        ACSP_UNKNONW_PACKET(-1);

        private final int type;
        private final UDPPacketField[] fields;

        ACUDPPacketType(int type, UDPPacketField... fields) {
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
