package ru.swat1x.wgcontroller.common.model.inter;

import ru.swat1x.wgcontroller.common.util.AllowedIpProvider;
import ru.swat1x.wgcontroller.common.util.PrivateKeyProvider;

import java.util.UUID;

public interface IWireGuardPeer extends AllowedIpProvider, PrivateKeyProvider {

    UUID getId();

    String getDisplayName();

    String getPrivateKey();

    String getPresharedKey();

    String getAllowedIPs();

    String getDNS();

    int getKeepAlive();

}
