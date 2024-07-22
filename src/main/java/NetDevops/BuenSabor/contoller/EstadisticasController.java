package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.dto.articuloInsumo.InsumoStockDto;
import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoVendidoDto;
import NetDevops.BuenSabor.dto.cliente.ClientePedidosDto;
import NetDevops.BuenSabor.service.util.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticaService estadisticaService;

    @GetMapping("/ingresos/dias")
    public Map<LocalDate, Double> getIngresosPorRangoDeDias(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                            @RequestParam Long sucursalId) {
        return estadisticaService.getIngresosPorRangoDeDias(startDate, endDate, sucursalId);
    }

    @GetMapping("/ingresos/meses")
    public Map<YearMonth, Double> getIngresosPorRangoDeMeses(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth startMonth,
                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth endMonth,
                                                             @RequestParam Long sucursalId) {
        return estadisticaService.getIngresosPorRangoDeMeses(startMonth, endMonth, sucursalId);
    }


    @GetMapping("/insumosConStock/{sucursalId}")
    public ResponseEntity<List<InsumoStockDto>> obtenerInsumosConStockPorSucursal(@PathVariable Long sucursalId) {
        List<InsumoStockDto> insumos = estadisticaService.obtenerInsumosConStockPorSucursal(sucursalId);
        return ResponseEntity.ok(insumos);
    }

    @GetMapping("/articulosManufacturadosVendidos/{sucursalId}")
    public ResponseEntity<List<ArticuloManufacturadoVendidoDto>> obtenerArticulosManufacturadosVendidosPorSucursal(@PathVariable Long sucursalId) {
        List<ArticuloManufacturadoVendidoDto> articulos = estadisticaService.obtenerArticulosManufacturadosVendidosPorSucursal(sucursalId);
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/articulos-manufacturados/vendidos-por-sucursal")
    public List<ArticuloManufacturadoVendidoDto> obtenerArticulosManufacturadosPorRangoFechaYSucursal(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam("sucursalId") Long sucursalId) {
        return estadisticaService.obtenerArticulosManufacturadosPorRangoFecha(fechaInicio, fechaFin, sucursalId);
    }

    @GetMapping("/pedidos-por-cliente-y-rango")
    public List<ClientePedidosDto> obtenerPedidosPorClienteYRango(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam("sucursalId") Long sucursalId) {
        return estadisticaService.obtenerPedidosPorClienteYRango(fechaInicio, fechaFin, sucursalId);
    }

}