package pl.pwr.eng.multichoice.domain.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findById(UUID uuid);
}
