package airiot.fi.bot.util;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 29.10.2015.
 * -
 */
public enum SystemScript {

    PYTHON_SCRIPT("/python.sh", "/python.bat");

    private final String nixScript;
    private final String windowsScript;

    SystemScript(String nixScript, String windowsScript) {
        this.nixScript = nixScript;
        this.windowsScript = windowsScript;
    }

    public String getNixScript() {
        return nixScript;
    }

    public String getWindowsScript() {
        return windowsScript;
    }

}
