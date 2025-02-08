package ru.swat1x.wgcontroller.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.moznion.wireguard.keytool.WireGuardKey;
import org.springframework.stereotype.Service;
import ru.swat1x.wgcontroller.dto.controller.CreateInterfaceDTO;
import ru.swat1x.wgcontroller.entity.WireGuardInterface;
import ru.swat1x.wgcontroller.exception.ConfigurationDuplicateException;
import ru.swat1x.wgcontroller.repository.WireGuardInterfaceRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WireGuardInterfaceService {

    WireGuardFileService fileService;
    WireGuardInterfaceRepository interfaceRepository;
    WireGuardNativeService nativeService;

    public List<WireGuardInterface> getActiveInterfaces() {
        return interfaceRepository.findAllByEnabled(true);
    }

    public List<WireGuardInterface> getAllInterfaces() {
        return interfaceRepository.findAll();
    }

    public Map<String, WireGuardInterface> getActiveInterfacesMapByName() {
        return getActiveInterfaces().stream().collect(Collectors.toMap(v -> v.getSystemName(), v -> v));
    }

    public WireGuardInterface createInterface(CreateInterfaceDTO createInterfaceDTO) {
        Objects.requireNonNull(createInterfaceDTO.getSystemName(), "System name cannot be null");
        Objects.requireNonNull(createInterfaceDTO.getAddress(), "Address cannot be null");
        Objects.requireNonNull(createInterfaceDTO.getListenPort(), "Listen port cannot be null");

        var configurationWithSameName = interfaceRepository.findBySystemName(createInterfaceDTO.getSystemName());
        if (configurationWithSameName.isPresent())
            if (!nativeService.getWorkingInterfaces().contains(createInterfaceDTO.getSystemName())) {
                interfaceRepository.delete(configurationWithSameName.get());
            } else {
                throw new ConfigurationDuplicateException(createInterfaceDTO.getSystemName());
            }

        if (createInterfaceDTO.getPrivateKey() == null) {
            createInterfaceDTO.setPrivateKey(WireGuardKey.generate().getBase64PrivateKey());
        }

        var wgInterface = new WireGuardInterface()
                .setSystemName(createInterfaceDTO.getSystemName())
                .setAddress(createInterfaceDTO.getAddress())
                .setListenPort(createInterfaceDTO.getListenPort())
                .setPrivateKey(createInterfaceDTO.getPrivateKey())
                .setEnabled(true);

        log.info("Creating interface \"{}\" ({})", wgInterface.getSystemName(), wgInterface.getId());
        wgInterface = interfaceRepository.save(wgInterface);
        log.info("Configuration \"{}\" ({}) saved!", wgInterface.getSystemName(), wgInterface.getId());


        reloadInterfaceConfiguration(wgInterface);

        return wgInterface;
    }

    public void reloadInterfaceConfiguration(WireGuardInterface wireGuardInterface) {
        fileService.updateServerInterfaceFile(wireGuardInterface);
        if (nativeService.isInterfaceWorking(wireGuardInterface.getSystemName())) {
            nativeService.downInterface(wireGuardInterface.getSystemName());
        }
        nativeService.upInterface(wireGuardInterface.getSystemName());
    }

}
