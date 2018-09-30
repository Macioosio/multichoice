package pl.pwr.eng.multichoice.domain.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    Area findById(UUID uuid);

    List<Area> findByCourseId(UUID uuid);
}