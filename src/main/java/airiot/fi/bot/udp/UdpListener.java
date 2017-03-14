package airiot.fi.bot.udp;

import airiot.fi.bot.DiscordBroadcaster;
import airiot.fi.bot.FileUtil;
import airiot.fi.bot.util.SystemScript;
import airiot.fi.bot.util.SystemScriptRunnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * Created by Petri Airio on 13.3.2017.
 */
@Service
@Slf4j
public class UdpListener implements Runnable {

    private int port = 12000;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private SystemScriptRunnerService systemScriptRunnerService;

    @Autowired
    private DiscordBroadcaster discordBroadcaster;

    private String pythonScript;


    @PostConstruct
    public void init() {
        StringBuilder sb = new StringBuilder();
        File tmpFile;
        try {
            tmpFile = File.createTempFile("print_events_from_bytes.py", "");
            fileUtil.copyResourceToFile("/print_events_from_bytes.py", tmpFile, sb);
            this.pythonScript = tmpFile.getAbsolutePath();
            Thread t = new Thread(this);
            t.start();

        } catch (IOException e) {
            log.error("Failed to get metar stations", e);
        }
    }

    public void run() {
        byte[] receiveData = new byte[10240];

        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            log.debug("Starting UDP receive on port: {}", port);
            while (true) {
                log.debug("waiting UDP data");
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                int len = receivePacket.getLength();
                byte[] dataPacket = Arrays.copyOf(receivePacket.getData(), len);
                String bytesFile = fileUtil.copyBytesToTmpFile(dataPacket);
                String[] strings = systemScriptRunnerService.runScript(SystemScript.PYTHON_SCRIPT, this.pythonScript, bytesFile);
                String udpEvent = strings[strings.length - 1];
                discordBroadcaster.sendMessage(udpEvent);

                fileUtil.deleteTmpFile(bytesFile);
            }
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    private String executePython(String bytesFile) {
        return null;
    }

}
