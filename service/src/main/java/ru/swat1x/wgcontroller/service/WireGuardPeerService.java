package ru.swat1x.wgcontroller.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.moznion.wireguard.keytool.WireGuardKey;
import org.springframework.stereotype.Service;
import ru.swat1x.wgcontroller.common.model.CreatePeerModel;
import ru.swat1x.wgcontroller.dto.controller.CreatePeerDTO;
import ru.swat1x.wgcontroller.entity.WireGuardInterface;
import ru.swat1x.wgcontroller.entity.WireGuardPeer;
import ru.swat1x.wgcontroller.exception.PeerNotFoundException;
import ru.swat1x.wgcontroller.repository.WireGuardPeerRepository;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WireGuardPeerService {

    WireGuardInterfaceService interfaceService;
    WireGuardPeerRepository repository;

    public WireGuardPeer findPeerById(UUID peerId) {
        return repository.findById(peerId).orElseThrow(() -> new PeerNotFoundException(peerId));
    }

    public WireGuardPeer create(WireGuardInterface wireGuardInterface,
                                CreatePeerDTO createPeerDTO) {
        Objects.requireNonNull(createPeerDTO.getDisplayName(), "DisplayName cannot be null");
        Objects.requireNonNull(createPeerDTO.getDNS(), "DNS cannot be null");
        Objects.requireNonNull(createPeerDTO.getKeepAlive(), "KeepAlive port cannot be null");
        Objects.requireNonNull(createPeerDTO.getAllowedIPs(), "AllowedIPs cannot be null");

        if (createPeerDTO.getPrivateKey() == null) {
            createPeerDTO.setPrivateKey(WireGuardKey.generate().getBase64PrivateKey());
        }

        var keyPair = new WireGuardKey(createPeerDTO.getPrivateKey());

        var peer = new WireGuardPeer()
                .setParentInterface(wireGuardInterface)

                .setDisplayName(createPeerDTO.getDisplayName())
                .setDNS(createPeerDTO.getDNS())
                .setAllowedIPs(createPeerDTO.getAllowedIPs())
                .setKeepAlive(createPeerDTO.getKeepAlive())

                .setPrivateKey(createPeerDTO.getPrivateKey())
                .setPresharedKey(createPeerDTO.getPresharedKey());

        peer = repository.save(peer);

        interfaceService.reloadInterfaceConfiguration(wireGuardInterface);

        return peer;
    }

}
