package airiot.fi.bot.udp.packets;

/**
 * Created by Petri Airio on 17.3.2017.
 */
public enum UDPPacketFieldName {

    PROTO_VERSION,
    SESSION_INDEX,
    CURRENT_SESSION_INDEX,
    SESSION_COUNT,
    SERVER_NAME,
    TRACK_NAME,
    TRACK_CONFIG,
    NAME,
    SESSION_TYPE,
    TIME,
    LAPS,
    WAIT_TIME,
    AMBIENT_TEMP,
    TRACK_TEMP,
    WEATHER_GRAPH,
    ELAPSED_MS,

    DRIVER_NAME,
    DRIVER_GUID,
    CAR_ID,
    CAR_MODEL,
    CAR_SKIN,

    FILENAME,

    MESSAGE,

    LAP_TIME,
    CUTS,
    CARS,
    GRIP_LEVEL,

    RCAR_ID,
    RTIME,
    RLAPS,
    HAS_COMPLETED_FLAG;

}
