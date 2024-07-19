package NetDevops.BuenSabor.dto.promocion;

import NetDevops.BuenSabor.dto.BaseDto;

import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoCantidadDto;
import NetDevops.BuenSabor.entities.ImagenPromocion;
import NetDevops.BuenSabor.entities.Sucursal;
import NetDevops.BuenSabor.enums.TipoPromocion;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PromocionDto extends BaseDto {

        private String denominacion;
        private LocalDate fechaDesde;
        private LocalDate fechaHasta;
        private LocalTime horaDesde;
        private LocalTime horaHasta;
        private String descripcionDescuento;
        private Double precioPromocional;
        private TipoPromocion tipoPromocion;
        private int cantidadMaximaDisponible;
        private Set<PromocionDetalleDto> promocionDetallesDto = new HashSet<>();
        //private Set<ImagenPromocion> imagenes = new HashSet<>();
        private String imagen;
        //private Set<Sucursal> sucursales = new HashSet<>();
        private List<ArticuloManufacturadoCantidadDto> articulosManufacturadosCantidad;
    }



