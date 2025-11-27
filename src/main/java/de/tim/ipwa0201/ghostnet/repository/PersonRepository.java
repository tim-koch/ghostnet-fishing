package de.tim.ipwa0201.ghostnet.repository;

import de.tim.ipwa0201.ghostnet.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
