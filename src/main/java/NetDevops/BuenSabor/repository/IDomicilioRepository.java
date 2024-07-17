package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDomicilioRepository extends JpaRepository<Domicilio, Long> {
}
