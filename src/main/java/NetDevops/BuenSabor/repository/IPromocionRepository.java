package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface IPromocionRepository extends JpaRepository<Promocion, Long> {
    Set<Promocion> findByEliminadoFalse();
    List<Promocion> findBySucursales_Id(Long sucursalId);
    List<Promocion> findByEliminadoFalseAndFechaHastaAfter(LocalDate fecha);
}
