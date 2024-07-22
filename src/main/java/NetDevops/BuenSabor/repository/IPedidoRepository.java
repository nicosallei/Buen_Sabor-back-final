package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.dto.cliente.ClientePedidosDto;
import NetDevops.BuenSabor.entities.Cliente;
import NetDevops.BuenSabor.entities.Pedido;
import NetDevops.BuenSabor.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findBySucursal_Id(Long sucursalId);

    @Query("SELECT p.fechaPedido as fecha, SUM(p.total) as total FROM Pedido p WHERE p.fechaPedido BETWEEN :startDate AND :endDate AND p.sucursal.id = :sucursalId GROUP BY p.fechaPedido")
    List<Object[]> sumTotalesPedidosPorRangoDeDias(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("sucursalId") Long sucursalId);

    @Query("SELECT EXTRACT(MONTH FROM p.fechaPedido) as month, EXTRACT(YEAR FROM p.fechaPedido) as year, SUM(p.total) as total FROM Pedido p WHERE p.fechaPedido BETWEEN :startDate AND :endDate AND p.sucursal.id = :sucursalId GROUP BY month, year")
List<Object[]> sumTotalesPedidosPorRangoDeMeses(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("sucursalId") Long sucursalId);

    List<Pedido> findByCliente_Id(Long clienteId);
    List<Pedido> findBySucursalIdAndEstado(Long sucursalId, Estado estado);
    List<Pedido> findByFechaPedidoBetweenAndSucursal_Id(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId);

@Query("SELECT new NetDevops.BuenSabor.dto.cliente.ClientePedidosDto(c.nombre, c.apellido, c.id, COUNT(p)) " +
       "FROM Pedido p JOIN p.cliente c " +
       "WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin " +
       "AND p.sucursal.id = :sucursalId " +
       "AND p.estado = NetDevops.BuenSabor.enums.Estado.ENTREGADO " +
       "GROUP BY c.nombre, c.apellido, c.id")
List<ClientePedidosDto> contarPedidosPorClienteEnRangoYEstado(@Param("fechaInicio") LocalDate fechaInicio,
                                                              @Param("fechaFin") LocalDate fechaFin,
                                                              @Param("sucursalId") Long sucursalId);
    List<Pedido> findByFechaPedidoBetweenAndSucursal_IdAndEstado(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId, Estado estado);

}
