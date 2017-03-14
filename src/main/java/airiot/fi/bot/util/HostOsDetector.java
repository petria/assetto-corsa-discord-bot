package airiot.fi.bot.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Petri Airio on 10.3.2016.
 * -
 */
@Slf4j
public class HostOsDetector {

    public HostOS detectHostOs() {
        String OS = System.getProperty("os.name").toLowerCase();
        HostOS hostOS;
        if (OS.contains("win")) {
            hostOS = HostOS.WINDOWS;
        } else if (OS.contains("freebsd")) {
            hostOS = HostOS.BSD;
        } else if (OS.contains("mac")) {
            hostOS = HostOS.OSX;
        } else if (OS.contains("linux")) {
            hostOS = HostOS.LINUX;
        } else {
            hostOS = HostOS.UNKNOWN_OS;
        }
        log.debug("Detected OS: {}", hostOS.toString());
        return hostOS;
    }

}
