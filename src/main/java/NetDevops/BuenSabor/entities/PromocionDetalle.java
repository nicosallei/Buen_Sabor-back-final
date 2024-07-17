package NetDevops.BuenSabor.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
//@Audited
public class PromocionDetalle extends Base{
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private ArticuloManufacturado articuloManufacturado;
    @ManyToOne
    @JoinColumn(name = "promocion_id")
    @JsonBackReference
    private Promocion promocion;
    @ManyToOne
    @JoinColumn(name = "imagen_promocion_id")
    private ImagenPromocion imagenPromocion;
}
