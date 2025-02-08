package ru.swat1x.wgcontroller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.swat1x.wgcontroller.entity.WireGuardInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface WireGuardInterfaceRepository extends JpaRepository<WireGuardInterface, UUID> {

    default Map<UUID, WireGuardInterface> findAllAsMap() {
        return findAll().stream()
                .collect(Collectors.toMap(v -> v.getId(), v -> v));
    }

    Optional<WireGuardInterface> findBySystemName(String systemName);

    List<WireGuardInterface> findAllByEnabled(boolean enabled);

    long countAllByEnabled(boolean enabled);

}
