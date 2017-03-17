package airiot.fi.bot.udp.packets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio on 17.3.2017.
 */
public class ParsedUdpPacket {

    private final ACUDPPacketEnums.UDPPacketName packetName;
    private Map<ACUDPPacketEnums.UDPDataField, String> packetData = new HashMap<>();

    public ParsedUdpPacket(ACUDPPacketEnums.UDPPacketName packetName) {
        this.packetName = packetName;
    }

    public void setParsed(ACUDPPacketEnums.UDPDataField field, String parsed) {
        packetData.put(field, parsed);
    }

    public ACUDPPacketEnums.UDPPacketName getPacketName() {
        return packetName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<PACKET(");
        sb.append(packetName.toString());
        sb.append(")");

        for (ACUDPPacketEnums.UDPDataField field : packetData.keySet()) {
            String value = packetData.get(field);
            sb.append(" ");
            sb.append(field.getName());
            sb.append("='");
            sb.append(value);
            sb.append("'");

        }
        sb.append(" >");
        return sb.toString();
    }

}
