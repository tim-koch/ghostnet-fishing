package de.tim.ipwa0201.ghostnet.service;

import de.tim.ipwa0201.ghostnet.domain.GhostNet;
import de.tim.ipwa0201.ghostnet.domain.GhostNetStatus;
import de.tim.ipwa0201.ghostnet.domain.Person;
import de.tim.ipwa0201.ghostnet.repository.GhostNetRepository;
import de.tim.ipwa0201.ghostnet.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class GhostNetService {

    private final GhostNetRepository ghostNetRepository;
    private final PersonRepository personRepository;

    public GhostNetService(GhostNetRepository ghostNetRepository, PersonRepository personRepository) {
        this.ghostNetRepository = ghostNetRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public GhostNet createNet(double lat, double lon, String sizeEstimate, boolean anonymous,
                              String reporterName, String phone) {

        Person reporter = null;
        if (!anonymous) {
            reporter = new Person(reporterName, phone);
            personRepository.save(reporter);
        }

        GhostNet net = new GhostNet(lat, lon, sizeEstimate, GhostNetStatus.GEMELDET, reporter);
        return ghostNetRepository.save(net);
    }

    public Iterable<GhostNet> findAll() {
        return ghostNetRepository.findAll();
    }

    public GhostNet findById(Long id) {
        return ghostNetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("GhostNet mit ID " + id + " nicht gefunden"));
    }

    @Transactional
    public GhostNet assignSalvager(Long netId, String name, String phoneNumber) {

        GhostNet net = ghostNetRepository.findById(netId)
                .orElseThrow(() -> new IllegalArgumentException("GhostNet mit ID " + netId + " nicht gefunden"));

        if (net.getStatus() != GhostNetStatus.GEMELDET) {
            throw new IllegalStateException("Nur Netze mit Status GEMELDET können übernommen werden");
        }

        Person salvager = new Person(name, phoneNumber);
        personRepository.save(salvager);

        net.setSalvager(salvager);
        net.setStatus(GhostNetStatus.BERGUNG_BEVORSTEHEND);

        return ghostNetRepository.save(net);
    }

    public List<GhostNet> findOpenNets() {
        return ghostNetRepository.findAllByStatusIn(
                Arrays.asList(
                        GhostNetStatus.GEMELDET,
                        GhostNetStatus.BERGUNG_BEVORSTEHEND
                )
        );
    }

    @Transactional
    public GhostNet markAsRecovered(Long id) {
        GhostNet net = findById(id);

        if (net.getStatus() != GhostNetStatus.BERGUNG_BEVORSTEHEND) {
            throw new IllegalStateException("Nur Netze mit Status BERGUNG_BEVORSTEHEND können als geborgen markiert werden");
        }

        net.setStatus(GhostNetStatus.GEBORGEN);
        return ghostNetRepository.save(net);
    }

    @Transactional
    public GhostNet markAsLost(Long id, String name, String phoneNumber) {
        GhostNet net = findById(id);

        if (net.getStatus() != GhostNetStatus.GEMELDET &&
                net.getStatus() != GhostNetStatus.BERGUNG_BEVORSTEHEND) {
            throw new IllegalStateException("Nur Netze mit Status GEMELDET oder BERGUNG_BEVORSTEHEND können als verschollen markiert werden");
        }

        if (net.getReporter() == null) {
            Person reporter = new Person(name, phoneNumber);
            personRepository.save(reporter);
            net.setReporter(reporter);
        }

        net.setStatus(GhostNetStatus.VERSCHOLLEN);
        return ghostNetRepository.save(net);
    }
}
