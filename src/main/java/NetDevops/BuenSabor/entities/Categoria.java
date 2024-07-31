package NetDevops.BuenSabor.entities;

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
public class Categoria extends Base{

    @ManyToMany
    private List<Sucursal> sucursales;
    private String denominacion;
    private String urlIcono;


    @OneToMany(mappedBy = "categoria")
    @Builder.Default
    @ToString.Exclude
    private Set<Articulo> articulos = new HashSet<>();
//----------

    @OneToMany(mappedBy = "categoriaPadre", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Categoria> subCategorias;
//------

    @ManyToOne
    @JoinColumn(name = "categoria_padre_id")
    @ToString.Exclude
    private Categoria categoriaPadre;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    public void agregarSubCategoria(Categoria subCategoria) {
        this.subCategorias.add(subCategoria);
    }

}