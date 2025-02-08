package ru.swat1x.wgcontroller.common.model;

import lombok.experimental.*;
import lombok.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreateConfigurationModel {

    String systemName;

    String address;

    String privateKey;

    Integer listenPort;

}
