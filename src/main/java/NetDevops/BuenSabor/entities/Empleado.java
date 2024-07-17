package NetDevops.BuenSabor.entities;

import NetDevops.BuenSabor.enums.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
//@Audited
public class Empleado extends Base {

    private String nombre;
    private String apellido;
    private String Telefono;
    @Column(unique = true)
    private String email;
    private LocalDate fechaNacimiento;
    private Rol rol;
    private String imagen;


    @OneToOne
    private UsuarioEmpleado usuarioEmpleado;
    @ManyToOne
    private Sucursal sucursal;
}
