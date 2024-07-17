package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPaisRepository  extends JpaRepository<Pais, Long> {
    Optional<Pais> findByNombre(String nombre);
}
