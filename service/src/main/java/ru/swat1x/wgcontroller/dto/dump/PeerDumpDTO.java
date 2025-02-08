package ru.swat1x.wgcontroller.dto.dump;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeerDumpDTO {

    String publicKey;
    String presharedKey;
    String endpoint;
    String allowedIPs;
    long latestHandshake;

    long receivedBytes;
    long sentBytes;

}
