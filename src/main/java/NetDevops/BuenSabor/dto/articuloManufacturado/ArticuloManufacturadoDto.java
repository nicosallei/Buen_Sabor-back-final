package NetDevops.BuenSabor.dto.articuloManufacturado;

import NetDevops.BuenSabor.dto.BaseDto;

import NetDevops.BuenSabor.dto.categoria.CategoriaDto;
import NetDevops.BuenSabor.entities.Categoria;
import NetDevops.BuenSabor.entities.ImagenArticulo;
import NetDevops.BuenSabor.entities.Sucursal;
import NetDevops.BuenSabor.entities.UnidadMedida;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticuloManufacturadoDto extends BaseDto {
    private String denominacion;
    private String descripcion;
    private Double precioVenta;
    private String codigo;
    private String imagen;
    private Set<ImagenArticulo> imagenes = new HashSet<>();
    private UnidadMedida unidadMedida;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    private CategoriaDto categoria;
    private Sucursal sucursal;
    private Set<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetallesDto = new HashSet<>();


}
