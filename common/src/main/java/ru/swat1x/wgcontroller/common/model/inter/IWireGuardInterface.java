package ru.swat1x.wgcontroller.common.model.inter;

import ru.swat1x.wgcontroller.common.util.PrivateKeyProvider;

import java.util.UUID;

public interface IWireGuardInterface extends PrivateKeyProvider {

    UUID getId();

    String getSystemName();

    String getAddress();

    String getPrivateKey();

    Integer getListenPort();

}
