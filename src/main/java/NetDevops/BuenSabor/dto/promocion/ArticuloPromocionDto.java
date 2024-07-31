package NetDevops.BuenSabor.dto.promocion;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.entities.ImagenArticulo;
import NetDevops.BuenSabor.entities.UnidadMedida;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticuloPromocionDto extends BaseDto {
    private String denominacion;
    private String descripcion;
    private Double precioVenta;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    protected Set<ImagenArticulo> imagenes = new HashSet<>();
    protected String codigo;
    protected UnidadMedida unidadMedida;
    private Long cantidadMaximaCompra;

    public void setImagenesConRutaModificada(Set<ImagenArticulo> imagenesOriginales) {
        this.imagenes = imagenesOriginales.stream()
                .map(imagen -> {
                    String urlModificada = imagen.getUrl().replace("src\\main\\resources\\images\\", "");
                    ImagenArticulo imagenModificada = new ImagenArticulo();
                    imagenModificada.setUrl(urlModificada);
                    imagenModificada.setArticulo(imagen.getArticulo());
                    imagenModificada.setId(imagen.getId());
                    return imagenModificada;
                })
                .collect(Collectors.toSet());
    }
}
