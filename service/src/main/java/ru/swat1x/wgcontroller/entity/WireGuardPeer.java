package ru.swat1x.wgcontroller.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.swat1x.wgcontroller.common.model.inter.IWireGuardPeer;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "wg_peers")

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WireGuardPeer implements IWireGuardPeer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String displayName;

    String privateKey;

    String presharedKey;

    String allowedIPs;

    String DNS;

    int keepAlive;

    public String[] getDNSArray() {
        Objects.requireNonNull(allowedIPs, "DNS is null");
        return DNS.replace(" ", "").split(",");
    }

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_interface")
    WireGuardInterface parentInterface;

}
