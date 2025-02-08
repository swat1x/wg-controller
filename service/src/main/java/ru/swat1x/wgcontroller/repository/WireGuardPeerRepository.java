package ru.swat1x.wgcontroller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.swat1x.wgcontroller.entity.WireGuardInterface;
import ru.swat1x.wgcontroller.entity.WireGuardPeer;

import java.util.List;
import java.util.UUID;

@Repository
public interface WireGuardPeerRepository extends JpaRepository<WireGuardPeer, UUID> {

    List<WireGuardPeer> findAllByParentInterface(WireGuardInterface parent);

}
