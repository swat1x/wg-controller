package ru.swat1x.wgcontroller.common.model;

import lombok.experimental.*;
import lombok.*;
import ru.swat1x.wgcontroller.common.model.inter.IWireGuardPeer;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class WireGuardPeerModel implements IWireGuardPeer {

    UUID id;

    String displayName;

    String privateKey;

    String presharedKey;

    String allowedIPs;

    String DNS;

    int keepAlive;

}
