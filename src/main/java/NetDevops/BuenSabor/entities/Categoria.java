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
    private Set<Articulo> articulos = new HashSet<>();
//----------

    @OneToMany(mappedBy = "categoriaPadre", cascade = CascadeType.ALL)
    private List<Categoria> subCategorias;
//------

    @ManyToOne
    @JoinColumn(name = "categoria_padre_id")
    private Categoria categoriaPadre;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    public void agregarSubCategoria(Categoria subCategoria) {
        this.subCategorias.add(subCategoria);
    }

}