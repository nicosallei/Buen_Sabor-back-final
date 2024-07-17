package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProvinciaRepository extends JpaRepository<Provincia, Long> {
}
