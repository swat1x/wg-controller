package ru.swat1x.wgcontroller.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swat1x.wgcontroller.dto.controller.CreateInterfaceDTO;
import ru.swat1x.wgcontroller.entity.WireGuardInterface;
import ru.swat1x.wgcontroller.service.WireGuardInterfaceService;

@RestController
@RequestMapping("/api/interfaces")

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InterfaceController {

    WireGuardInterfaceService interfaceService;

    @PostMapping("/create")
    public WireGuardInterface createInterface(@RequestBody CreateInterfaceDTO createInterfaceDTO) {
        return interfaceService.createInterface(createInterfaceDTO);
    }

}
