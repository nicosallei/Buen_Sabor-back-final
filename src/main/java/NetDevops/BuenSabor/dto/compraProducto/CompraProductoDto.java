package NetDevops.BuenSabor.dto.compraProducto;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.entities.Categoria;
import NetDevops.BuenSabor.entities.ImagenArticulo;
import NetDevops.BuenSabor.entities.Sucursal;
import NetDevops.BuenSabor.entities.UnidadMedida;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompraProductoDto extends BaseDto {

    private String denominacion;
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    private String codigo;
    private Double precioVenta;
    private List<ImagenArticulo> imagenes;
    //private UnidadMedida unidadMedida;
    private Long categoriaId;
    private Long sucursalId;
    private Long cantidadMaximaCompra;


}
