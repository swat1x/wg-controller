package ru.swat1x.wgcontroller.dto.controller;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreateInterfaceDTO {

    String systemName;

    String address;

    String privateKey;

    Integer listenPort;

}
