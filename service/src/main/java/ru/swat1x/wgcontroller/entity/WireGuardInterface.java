package ru.swat1x.wgcontroller.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.swat1x.wgcontroller.common.model.inter.IWireGuardInterface;

import java.util.UUID;

@Entity
@Table(name = "wg_interfaces")

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WireGuardInterface implements IWireGuardInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "system_name", unique = true, nullable = false)
    String systemName;

    String address;

    String privateKey;

    Integer listenPort;

    boolean enabled;

}
