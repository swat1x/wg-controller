package ru.swat1x.wgcontroller.dto.dump;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterfaceDumpDTO {

    String privateKey;
    String publicKey;
    int port;

    Set<PeerDumpDTO> peers;

}
