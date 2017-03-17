package airiot.fi.bot.udp;

import airiot.fi.bot.DiscordBroadcaster;
import airiot.fi.bot.util.FileUtil;
import airiot.fi.bot.util.SystemScript;
import airiot.fi.bot.util.SystemScriptRunnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${UDP_PLUGIN_PORT}")
    private int port;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private SystemScriptRunnerService systemScriptRunnerService;

    @Autowired
    private DiscordBroadcaster discordBroadcaster;

    private String pythonScript;

    @Override
    public void run() {
        byte[] receiveData = new byte[1024];

        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            log.debug("Starting UDP receive on port: {}", port);
            while (true) {
                log.debug("waiting UDP data");
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                int len = receivePacket.getLength();
                byte[] dataPacket = Arrays.copyOf(receivePacket.getData(), len);
                String type = dataPacket[0] + "";
                String bytesFile = fileUtil.copyBytesToTmpFile(type + "_", dataPacket);
                String[] strings = systemScriptRunnerService.runScript(SystemScript.PYTHON_SCRIPT, pythonScript, bytesFile);
                if (strings.length > 0) {
                    String udpEvent = strings[strings.length - 1];
                    discordBroadcaster.sendMessage(udpEvent);
                }
                fileUtil.deleteTmpFile(bytesFile);
            }
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    @PostConstruct
    public void init() {
        StringBuilder sb = new StringBuilder();
        File tmpFile;
        try {
            tmpFile = File.createTempFile("print_events_from_bytes.py", "");
            fileUtil.copyResourceToFile("/print_events_from_bytes.py", tmpFile, sb);
            this.pythonScript = tmpFile.getAbsolutePath();
            Thread t = new Thread(this);
            t.setName("UdpListener");
            t.start();

        } catch (IOException e) {
            log.error("Failed to get metar stations", e);
        }
    }

}
