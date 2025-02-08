package ru.swat1x.wgcontroller.common.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.swat1x.wgcontroller.common.model.inter.IWireGuardInterface;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationRuntimeModel {

    IWireGuardInterface configuration;

    

    Status status;

    public enum Status {

        DISABLED,
        LOADING,
        ENABLED

    }

}
