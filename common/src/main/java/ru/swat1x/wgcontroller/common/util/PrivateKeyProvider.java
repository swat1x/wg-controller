package ru.swat1x.wgcontroller.common.util;

import net.moznion.wireguard.keytool.InvalidPrivateKeyException;
import net.moznion.wireguard.keytool.WireGuardKey;

import java.util.Objects;

public interface PrivateKeyProvider {

    String getPrivateKey();

    default String getPublicKey() {
        Objects.requireNonNull(getPrivateKey(), "The private key is null");
        try {
            return new WireGuardKey(getPrivateKey()).getBase64PublicKey();
        } catch (InvalidPrivateKeyException e) {
            throw new RuntimeException("Can't generate public key from private key", e);
        }
    }

}
