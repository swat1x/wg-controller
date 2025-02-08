package ru.swat1x.wgcontroller.dto.controller;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreatePeerDTO {

    String displayName = "Отображаемое имя";

    String privateKey;

    String presharedKey;

    String DNS = "1.1.1.1";

    Integer keepAlive = 21;

    String allowedIPs;

}
