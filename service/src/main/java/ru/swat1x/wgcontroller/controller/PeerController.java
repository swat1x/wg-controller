package ru.swat1x.wgcontroller.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.swat1x.wgcontroller.dto.controller.CreatePeerDTO;
import ru.swat1x.wgcontroller.entity.WireGuardInterface;
import ru.swat1x.wgcontroller.entity.WireGuardPeer;
import ru.swat1x.wgcontroller.service.WireGuardFileService;
import ru.swat1x.wgcontroller.service.WireGuardPeerService;

import java.util.UUID;

@RestController
@RequestMapping("/api/peers")

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PeerController {

    WireGuardPeerService peerService;
    WireGuardFileService fileService;

    @PostMapping("/create")
    public WireGuardPeer createPeer(@RequestParam("interface") WireGuardInterface wireGuardInterface,
                                    @RequestBody CreatePeerDTO createPeerDTO) {
        return peerService.create(wireGuardInterface, createPeerDTO);
    }

    @PostMapping("/get")
    public WireGuardPeer getPeer(@RequestParam("peerId") UUID peerId) {
        return peerService.findPeerById(peerId);
    }

    @PostMapping("/get/content")
    public String getPeerContent(@RequestParam("peerId") WireGuardPeer peer) {
        return fileService.generateClientConfigurationContent(peer);
    }

}
