package ru.swat1x.wgcontroller.common.model;

import lombok.experimental.*;
import lombok.*;
import ru.swat1x.wgcontroller.common.model.inter.IWireGuardInterface;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class WireGuardInterfaceModel implements IWireGuardInterface {


    UUID id;

    String systemName;

    String address;

    String privateKey;

    Integer listenPort;


}
