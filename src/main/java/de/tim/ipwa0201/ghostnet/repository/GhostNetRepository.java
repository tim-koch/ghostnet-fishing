package de.tim.ipwa0201.ghostnet.repository;

import de.tim.ipwa0201.ghostnet.domain.GhostNet;
import de.tim.ipwa0201.ghostnet.domain.GhostNetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {

    List<GhostNet> findAllByStatus(GhostNetStatus status);

    List<GhostNet> findAllByStatusIn(Collection<GhostNetStatus> statuses);

}