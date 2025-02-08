package ru.swat1x.wgcontroller.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.swat1x.wgcontroller.dto.dump.InterfaceDumpDTO;
import ru.swat1x.wgcontroller.dto.dump.PeerDumpDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class WireGuardNativeService {

    public boolean isInterfaceWorking(String interfaceName) {
        return getWorkingInterfaces().contains(interfaceName);
    }

    public List<String> upInterface(String interfaceName) {
        var commandResponse = execCommand("wg-quick up %s".formatted(interfaceName));
        var response = List.of(commandResponse.split("\n"));

        log.info(" $ Up interface: {}", response);
        response.forEach(line -> log.info(" $ $ {}", line));

        return response;
    }

    public List<String> downInterface(String interfaceName) {
        var commandResponse = execCommand("wg-quick down %s".formatted(interfaceName));
        var response = List.of(commandResponse.split("\n"));

        log.info(" $ Down interface: {}", response);
        response.forEach(line -> log.info(" $ $ {}", line));

        return response;
    }

    public List<String> getWorkingInterfaces() {
        var commandResponse = execCommand("wg show interfaces");
        if (commandResponse.isBlank()) return new ArrayList<>();
        var lines = commandResponse.split(" ");
        return List.of(lines);
    }

    public InterfaceDumpDTO dumpInterface(String interfaceName) {
        var commandResponse = execCommand("wg show %s dump".formatted(interfaceName));
        if (commandResponse.isBlank()) return null;
        return formInterfaceDump(commandResponse);
    }

    private InterfaceDumpDTO formInterfaceDump(String content) {
        assert content != null && !content.isEmpty() : "Content is absent";

        var lines = content.split("\n");
        var interfaceInfo = lines[0].split("\t");

        var privateKey = interfaceInfo[0];
        var publicKey = interfaceInfo[1];
        var port = Integer.parseInt(interfaceInfo[2]);

        Set<PeerDumpDTO> peers = new HashSet<>();
        var peerCount = lines.length - 1;
        if (peerCount >= 1) {
            for (int i = 0; i < peerCount; i++) {
                var peerInfo = lines[i + 1].split("\t");

                var peerPublicKey = peerInfo[0];
                var peerPresharedKey = formatAsNullable(peerInfo[1]);
                var peerEndpoint = formatAsNullable(peerInfo[2]);
                var peerAllowedIPs = peerInfo[3];
                var peerLatestHandshake = Long.parseLong(peerInfo[4]);
                var peerReceivedBytes = Long.parseLong(peerInfo[5]);
                var peerSentBytes = Long.parseLong(peerInfo[6]);

                peers.add(new PeerDumpDTO()
                        .publicKey(peerPublicKey)
                        .presharedKey(peerPresharedKey)
                        .endpoint(peerEndpoint)
                        .allowedIPs(peerAllowedIPs)
                        .latestHandshake(peerLatestHandshake)
                        .receivedBytes(peerReceivedBytes)
                        .sentBytes(peerSentBytes)
                );
            }
        }

        return new InterfaceDumpDTO()
                .privateKey(privateKey)
                .publicKey(publicKey)
                .port(port)
                .peers(peers);
    }

    private String formatAsNullable(String value) {
        return value.equalsIgnoreCase("(none)") ? null : value;
    }

    private String readResponse(Process process) throws IOException, InterruptedException {
        var responseCode = process.waitFor(); // ожидаем ответа
        var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) lines.add(line);
        return String.join("\n", lines);
    }

    private String execCommand(String command) {
        try {
            var process = new ProcessBuilder(command.split(" ")).redirectErrorStream(true).start();
            return readResponse(process);
        } catch (IOException e) {
//            log.error("Error while executing command {}", command, e);
            throw new RuntimeException("Exception while executing command \"%s\"".formatted(command), e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
