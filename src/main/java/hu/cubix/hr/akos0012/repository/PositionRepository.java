package hu.cubix.hr.akos0012.repository;

import hu.cubix.hr.akos0012.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {

    public List<Position> findByName(String name);
}
