package airiot.fi.bot.util;


/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 29.10.2015.
 * -
 */
public interface SystemScriptRunnerService {

    String[] runScript(SystemScript systemScript, String... args);

    SystemScriptResult runAndGetResult(SystemScript systemScript, String... args);

}
