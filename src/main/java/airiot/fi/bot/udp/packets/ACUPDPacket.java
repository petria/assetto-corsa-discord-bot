package airiot.fi.bot.udp.packets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio on 16.3.2017.
 */
public class ACUPDPacket {

    private ACUDPPacketEnums.ACUDPPacketType type;
    private Map<ACUDPPacketEnums.UDPPacketField, String> packetData = new HashMap<>();

    public ACUPDPacket(ACUDPPacketEnums.ACUDPPacketType type) {
        this.type = type;
    }

    public ACUDPPacketEnums.ACUDPPacketType getType() {
        return type;
    }

    public Map<ACUDPPacketEnums.UDPPacketField, String> getPacketData() {
        return packetData;
    }

    public void setFieldValue(ACUDPPacketEnums.UDPPacketField field, String value) {
        this.packetData.put(field, value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<PACKET(");
        sb.append(type.toString());
        sb.append(")");

        for (ACUDPPacketEnums.UDPPacketField field : ACUDPPacketEnums.UDPPacketField.values()) {
            String value = packetData.get(field);
            if (value != null) {
                sb.append(" ");
                sb.append(field);
                sb.append("='");
                sb.append(value);
                sb.append("'");
            }
        }
        sb.append(" >");
        return sb.toString();
    }
}
