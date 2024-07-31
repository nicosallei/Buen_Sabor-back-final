package NetDevops.BuenSabor.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
//@Audited


public abstract class Articulo extends Base{


    protected String denominacion;
    protected String descripcion;
    protected String codigo;
    protected Double precioVenta;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "articulo")
    @Builder.Default
    protected Set<ImagenArticulo> imagenes = new HashSet<>();
    @ManyToOne
    protected UnidadMedida unidadMedida;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @ToString.Exclude
    protected Categoria categoria;

    @ManyToOne
    private Sucursal sucursal;



}
