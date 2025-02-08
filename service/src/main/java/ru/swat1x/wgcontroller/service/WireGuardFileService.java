package ru.swat1x.wgcontroller.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.swat1x.wgcontroller.configuration.ServerConfiguration;
import ru.swat1x.wgcontroller.entity.WireGuardInterface;
import ru.swat1x.wgcontroller.entity.WireGuardPeer;
import ru.swat1x.wgcontroller.repository.WireGuardPeerRepository;
import ru.swat1x.wgcontroller.util.ContentBlockBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WireGuardFileService {

    private static final String INTERFACE_TITLE = "Interface";
    private static final String PEER_TITLE = "Peer";

    private static final String ALL_TRAFFIC_IPS = "0.0.0.0/0, ::/0";

    private static final String POST_UP_COMMAND_1 = "iptables -t nat -I POSTROUTING 1 -s %s -o %s -j MASQUERADE";
    private static final String POST_UP_COMMAND_2 = "iptables -I FORWARD -i %i -o %i -j DROP";

    private static final String PRE_DOWN_COMMAND_1 = "iptables -t nat -D POSTROUTING -s %s -o %s -j MASQUERADE";
    private static final String PRE_DOWN_COMMAND_2 = "iptables -D FORWARD -i %i -o %i -j DROP";

    WireGuardPeerRepository peerRepository;

    ServerConfiguration serverConfiguration;

    public void updateServerInterfaceFile(WireGuardInterface configuration) {
        var path = Path.of("/etc/wireguard/%s.conf".formatted(configuration.getSystemName()));
        var configurationContent = generateServerConfigurationContent(configuration);

        try {
            path.toFile().createNewFile();
            Files.writeString(path, configurationContent);
        } catch (IOException e) {
            throw new RuntimeException("Exception while updating server configuration \"%s\"".formatted(configuration.getSystemName()), e);
        }
    }

    public String generateServerConfigurationContent(WireGuardInterface configuration) {
        var peers = peerRepository.findAllByParentInterface(configuration);

        var contentBuilder = new StringBuilder();

        var interfaceBlock = ContentBlockBuilder.create(INTERFACE_TITLE)
                // Variable fields
                .put("Address", configuration.getAddress())
                .put("ListenPort", configuration.getListenPort())
                .put("PrivateKey", configuration.getPrivateKey())
                // Static fields
                .put("PostUp", POST_UP_COMMAND_1.formatted(configuration.getAddress(), System.getenv("OUT_ADAPTER")))
                .put("PostUp", POST_UP_COMMAND_2)
                .put("PreDown", PRE_DOWN_COMMAND_1.formatted(configuration.getAddress(), System.getenv("OUT_ADAPTER")))
                .put("PreDown", PRE_DOWN_COMMAND_2)
                .build();

        contentBuilder.append(interfaceBlock);

        for (var peer : peers) {
            var peerBlock = ContentBlockBuilder.create(PEER_TITLE)
                    // Additional fields
//                    .put("Id", peer.getId())
//                    .put("Name", peer.getDisplayName())
                    // WireGuard fields
                    .put("PublicKey", peer.getPublicKey())
                    .put("PresharedKey", peer.getPresharedKey())
                    .put("AllowedIPs", formatIPsWithMask(peer, 32))
                    .build();

            contentBuilder.append(peerBlock);
        }

        return contentBuilder.toString();
    }

    public String generateClientConfigurationContent(WireGuardPeer peer) {
        var contentBuilder = new StringBuilder();
        var interfaceBlock = ContentBlockBuilder.create(INTERFACE_TITLE)
                // Variable fields
                .put("PrivateKey", peer.getPrivateKey())
                .put("Address", formatIPsWithMask(peer, 24))
                .put("DNS", peer.getDNS())
                .build();
        contentBuilder.append(interfaceBlock);

        var parentInterface = peer.getParentInterface();
        var peerBlock = ContentBlockBuilder.create(PEER_TITLE)
                // Variable fields
                .put("PublicKey", parentInterface.getPublicKey())
                .put("PresharedKey", peer.getPresharedKey())
                .put("AllowedIPs", ALL_TRAFFIC_IPS)
                .put("Endpoint", "%s:%s".formatted(serverConfiguration.getServerEndpoint(), parentInterface.getListenPort()))
                .put("PersistentKeepalive", peer.getKeepAlive())
                .build();
        contentBuilder.append(peerBlock);
        return contentBuilder.toString();
    }

    private String formatIPsWithMask(WireGuardPeer peer, int mask) {
        return Arrays.stream(peer.getAllowedIpsArray())
                .map(("%s/" + mask)::formatted)
                .collect(Collectors.joining(", "));
    }

}
