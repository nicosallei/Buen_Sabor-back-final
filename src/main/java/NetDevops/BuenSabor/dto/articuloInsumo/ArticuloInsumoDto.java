package NetDevops.BuenSabor.dto.articuloInsumo;

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
public class ArticuloInsumoDto extends BaseDto{

    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMaximo;
    private Boolean esParaElaborar;
    private Integer stockMinimo;
    private String denominacion;
    private String descripcion;
    private String codigo;
    private Double precioVenta;
    private Set<ImagenArticulo> imagenes = new HashSet<>();
    private UnidadMedida unidadMedida;
    private CategoriaDto categoria;
    private Sucursal sucursal;
}
