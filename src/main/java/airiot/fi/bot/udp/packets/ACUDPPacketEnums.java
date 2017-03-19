package airiot.fi.bot.udp.packets;


import java.util.ArrayList;
import java.util.List;

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
        NEW_CONNECTION(51),
        CONNECTION_CLOSED(52),
        CAR_UPDATE(53),
        CAR_INFO(54),
        END_SESSION(55),
        VERSION(56),
        CHAT(57),
        CLIENT_LOADED(58),
        SESSION_INFO(59),
        ERROR(60),
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

}
