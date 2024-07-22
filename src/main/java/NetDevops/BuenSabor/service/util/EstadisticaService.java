package NetDevops.BuenSabor.service.util;


import NetDevops.BuenSabor.dto.articuloInsumo.InsumoStockDto;
import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoVendidoDto;
import NetDevops.BuenSabor.dto.cliente.ClientePedidosDto;
import NetDevops.BuenSabor.entities.ArticuloManufacturado;
import NetDevops.BuenSabor.entities.ArticuloManufacturadoDetalle;
import NetDevops.BuenSabor.entities.Pedido;
import NetDevops.BuenSabor.entities.PedidoDetalle;
import NetDevops.BuenSabor.enums.Estado;
import NetDevops.BuenSabor.repository.IAriticuloInsumoRepository;
import NetDevops.BuenSabor.repository.IPedidoDetalleReposiroty;
import NetDevops.BuenSabor.repository.IPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class EstadisticaService {

    @Autowired
    private IPedidoRepository pedidoRepository;
    @Autowired
    private IPedidoDetalleReposiroty pedidoDetalleRepository;
    @Autowired
    private IAriticuloInsumoRepository articuloInsumoRepository;

    public Map<LocalDate, Double> getIngresosPorRangoDeDias(LocalDate startDate, LocalDate endDate, Long sucursalId) {
        List<Object[]> results = pedidoRepository.sumTotalesPedidosPorRangoDeDias(startDate, endDate, sucursalId);
        Map<LocalDate, Double> ingresosPorDia = new HashMap<>();
        for (Object[] result : results) {
            LocalDate fecha = (LocalDate) result[0];
            double total = (double) result[1];
            ingresosPorDia.put(fecha, total);
        }
        return ingresosPorDia;
    }

  public Map<YearMonth, Double> getIngresosPorRangoDeMeses(YearMonth startMonth, YearMonth endMonth, Long sucursalId) {
    // Convert YearMonth to LocalDate
    LocalDate startDate = startMonth.atDay(1);
    LocalDate endDate = endMonth.atEndOfMonth();

    List<Object[]> results = pedidoRepository.sumTotalesPedidosPorRangoDeMeses(startDate, endDate, sucursalId);
    Map<YearMonth, Double> ingresosPorMes = new HashMap<>();
    for (Object[] result : results) {
        int month = (int) result[0];
        int year = (int) result[1];
        double total = (double) result[2];
        ingresosPorMes.put(YearMonth.of(year, month), total);
    }
    return ingresosPorMes;
}

    public List<InsumoStockDto> obtenerInsumosConStockPorSucursal(Long sucursalId) {
        return articuloInsumoRepository.findInsumosConStockPorSucursal(sucursalId).stream()
                .map(insumo -> new InsumoStockDto(insumo.getDenominacion(), insumo.getStockActual()))
                .collect(Collectors.toList());
    }

    public List<ArticuloManufacturadoVendidoDto> obtenerArticulosManufacturadosVendidosPorSucursal(Long sucursalId) {
        return pedidoDetalleRepository.findArticulosManufacturadosVendidosPorSucursal(sucursalId);
    }

//    public Map<String, ArticuloManufacturadoVendidoDto> obtenerArticulosManufacturadosPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId) {
//       List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetweenAndSucursal_Id( fechaInicio,fechaFin,sucursalId);
//        Map<String, ArticuloManufacturadoVendidoDto> articulosConCantidad = new HashMap<>();
//
//        for (Pedido pedido : pedidos) {
//            for (PedidoDetalle detalle : pedido.getPedidoDetalle()) {
//                if (detalle.getArticulo() instanceof ArticuloManufacturado) {
//                    ArticuloManufacturado articulo = (ArticuloManufacturado) detalle.getArticulo();
//                    String denominacion = articulo.getDenominacion();
//                    ArticuloManufacturadoVendidoDto dto = articulosConCantidad.getOrDefault(denominacion, new ArticuloManufacturadoVendidoDto(denominacion, 0L));
//                    dto.setCantidadVendida(dto.getCantidadVendida() + detalle.getCantidad());
//                    articulosConCantidad.put(denominacion, dto);
//                }
//            }
//        }
//
//        return articulosConCantidad;
//    }

   public List<ArticuloManufacturadoVendidoDto> obtenerArticulosManufacturadosPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId) {
    List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetweenAndSucursal_Id( fechaInicio,fechaFin,sucursalId);
    Map<String, ArticuloManufacturadoVendidoDto> articulosConCantidad = new HashMap<>();

    for (Pedido pedido : pedidos) {
        for (PedidoDetalle detalle : pedido.getPedidoDetalle()) {
            if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                ArticuloManufacturado articulo = (ArticuloManufacturado) detalle.getArticulo();
                String denominacion = articulo.getDenominacion();
                ArticuloManufacturadoVendidoDto dto = articulosConCantidad.getOrDefault(denominacion, new ArticuloManufacturadoVendidoDto(denominacion, 0L));
                dto.setCantidadVendida(dto.getCantidadVendida() + detalle.getCantidad());
                articulosConCantidad.put(denominacion, dto);
            }
        }
    }

    return new ArrayList<>(articulosConCantidad.values());
}

    public List<ClientePedidosDto> obtenerPedidosPorClienteYRango(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId) {
        return pedidoRepository.contarPedidosPorClienteEnRangoYEstado(fechaInicio, fechaFin, sucursalId);
    }


    public Map<YearMonth, Double> getGananciasPorRangoDeMeses(YearMonth startMonth, YearMonth endMonth, Long sucursalId) {
    LocalDate startDate = startMonth.atDay(1);
    LocalDate endDate = endMonth.atEndOfMonth();
    List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetweenAndSucursal_IdAndEstado(startDate, endDate, sucursalId, Estado.ENTREGADO);
    Map<YearMonth, Double> gananciasPorMes = new HashMap<>();

    for (Pedido pedido : pedidos) {
        double gananciaPorPedido = 0;
        for (PedidoDetalle detalle : pedido.getPedidoDetalle()) {
            if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                ArticuloManufacturado manufacturado = (ArticuloManufacturado) detalle.getArticulo();
                double costoProduccion = 0;
                for (ArticuloManufacturadoDetalle amd : manufacturado.getArticuloManufacturadoDetalles()) {
                    costoProduccion += amd.getArticuloInsumo().getPrecioCompra() * amd.getCantidad();
                }
                double precioVenta = manufacturado.getPrecioVenta(); // Asumiendo que existe este campo
                double ganancia = (precioVenta - costoProduccion) * detalle.getCantidad();
                gananciaPorPedido += ganancia;
            }
        }
        YearMonth yearMonth = YearMonth.from(pedido.getFechaPedido());
        gananciasPorMes.merge(yearMonth, gananciaPorPedido, Double::sum);
    }
    return gananciasPorMes;
}

}
