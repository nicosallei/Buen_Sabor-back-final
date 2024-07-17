package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Cliente;
import NetDevops.BuenSabor.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findBySucursal_Id(Long sucursalId);

    @Query("SELECT p.fechaPedido as fecha, SUM(p.total) as total FROM Pedido p WHERE p.fechaPedido BETWEEN :startDate AND :endDate AND p.sucursal.id = :sucursalId GROUP BY p.fechaPedido")
    List<Object[]> sumTotalesPedidosPorRangoDeDias(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("sucursalId") Long sucursalId);

    @Query("SELECT EXTRACT(MONTH FROM p.fechaPedido) as month, EXTRACT(YEAR FROM p.fechaPedido) as year, SUM(p.total) as total FROM Pedido p WHERE p.fechaPedido BETWEEN :startDate AND :endDate AND p.sucursal.id = :sucursalId GROUP BY month, year")
List<Object[]> sumTotalesPedidosPorRangoDeMeses(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("sucursalId") Long sucursalId);
}
