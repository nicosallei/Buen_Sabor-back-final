package NetDevops.BuenSabor.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
//@Audited
public class Sucursal extends Base{

    private String nombre;
    private String horaApertura;
    private String horaCierre;

    @ManyToOne
    //@JsonIgnore
    private Empresa empresa;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Domicilio domicilio;
    private String imagen;


}
