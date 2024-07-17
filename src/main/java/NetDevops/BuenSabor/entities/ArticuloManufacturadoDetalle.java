package NetDevops.BuenSabor.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@ToString
@SuperBuilder
//@Audited
public class ArticuloManufacturadoDetalle extends Base{

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "articulo_insumo_id")
    private ArticuloInsumo articuloInsumo;
    @ManyToOne
    @JoinColumn(name = "articulo_manufacturado_id")
    @JsonBackReference
    private ArticuloManufacturado articuloManufacturado;
}
