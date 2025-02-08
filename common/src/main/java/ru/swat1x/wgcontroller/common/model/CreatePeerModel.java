package ru.swat1x.wgcontroller.common.model;

import lombok.experimental.*;
import lombok.*;
import ru.swat1x.wgcontroller.common.util.AllowedIpProvider;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreatePeerModel implements AllowedIpProvider {

    String displayName = "Отображаемое имя";

    String privateKey;

    String presharedKey;

    String DNS = "1.1.1.1";

    Integer keepAlive = 21;

    String allowedIPs;

}
