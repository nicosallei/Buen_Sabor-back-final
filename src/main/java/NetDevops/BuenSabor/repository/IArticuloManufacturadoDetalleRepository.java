package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.ArticuloInsumo;
import NetDevops.BuenSabor.entities.ArticuloManufacturadoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IArticuloManufacturadoDetalleRepository extends JpaRepository<ArticuloManufacturadoDetalle, Long> {
    Set<ArticuloManufacturadoDetalle> findByEliminadoFalse();

    Set<ArticuloManufacturadoDetalle> findByArticuloManufacturado_Id(Long id);


    // Nuevo método para verificar si existe un ArticuloManufacturadoDetalle con un ArticuloInsumo específico y eliminado = false
    boolean existsByArticuloInsumoAndEliminadoFalse(ArticuloInsumo articuloInsumo);

    boolean existsByArticuloInsumo_IdAndEliminadoFalse(Long idArticuloInsumo);
}
