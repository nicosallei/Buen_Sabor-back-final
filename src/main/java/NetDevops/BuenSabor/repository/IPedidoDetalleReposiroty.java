package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoVendidoDto;
import NetDevops.BuenSabor.entities.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPedidoDetalleReposiroty extends JpaRepository<PedidoDetalle, Long> {

    @Query("SELECT new NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoVendidoDto(am.denominacion, SUM(pd.cantidad)) " +
            "FROM PedidoDetalle pd " +
            "JOIN pd.articulo am " +
            "WHERE pd.pedido.sucursal.id = :sucursalId " +
            "AND pd.pedido.estado <> NetDevops.BuenSabor.enums.Estado.CANCELADO " +
            "AND TYPE(am) = NetDevops.BuenSabor.entities.ArticuloManufacturado " +
            "GROUP BY am.denominacion")
    List<ArticuloManufacturadoVendidoDto> findArticulosManufacturadosVendidosPorSucursal(Long sucursalId);


}
