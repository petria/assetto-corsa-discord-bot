package airiot.fi.bot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 29.10.2015.
 * -
 */
@Service
@Slf4j
public class SystemScriptRunnerServiceImpl implements SystemScriptRunnerService {

    private HostOsDetector osDetector = new HostOsDetector();

    @Override
    public String[] runScript(SystemScript systemScript, String... args) {
        HostOS hostOs = osDetector.detectHostOs();
        return runScript(systemScript, hostOs, args);
    }

    public String[] runScript(SystemScript systemScript, HostOS hostOs, String... args) {
        switch (hostOs) {
            case BSD:
            case LINUX:
            case OSX:
            case UNKNOWN_OS:
                return runNixSystemScript(systemScript, args);
            case WINDOWS:
                return runWindowsSystemScript(systemScript, args);
        }
        return new String[]{"N/A"};
    }


    @Override
    public SystemScriptResult runAndGetResult(SystemScript systemScript, String... args) {
        HostOS hostOs = osDetector.detectHostOs();
        String[] output = runScript(systemScript, hostOs, args);
        SystemScriptResult systemScriptResult = new SystemScriptResult();
        systemScriptResult.setOriginalOutput(output);
        if (output.length > 0) {
            systemScriptResult.setFormattedOutput(output[0]);
        }
        return systemScriptResult;
    }

    private String[] runWindowsSystemScript(SystemScript systemScript, String[] args) {
        JarWindowsBatExecutor executor = new JarWindowsBatExecutor(systemScript.getWindowsScript(), "windows-1252");
        return executor.executeJarWindowsBat(args);
    }

    private String[] runNixSystemScript(SystemScript systemScript, String[] args) {
        JarNixScriptExecutor executor = new JarNixScriptExecutor(systemScript.getNixScript(), "UTF-8");
        return executor.executeJarScript(args);
    }

}
