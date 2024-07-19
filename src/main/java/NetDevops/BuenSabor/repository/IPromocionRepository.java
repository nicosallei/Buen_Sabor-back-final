package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface IPromocionRepository extends JpaRepository<Promocion, Long> {
    Set<Promocion> findByEliminadoFalse();
    List<Promocion> findBySucursales_Id(Long sucursalId);
    List<Promocion> findByEliminadoFalseAndFechaHastaAfter(LocalDate fecha);

    @Query("SELECT p FROM Promocion p JOIN p.sucursales s WHERE s.id = :sucursalId AND :fechaActual BETWEEN p.fechaDesde AND p.fechaHasta AND p.eliminado = false")
    List<Promocion> findPromocionesValidasPorSucursalYFecha(@Param("sucursalId") Long sucursalId, @Param("fechaActual") LocalDate fechaActual);

}
