package NetDevops.BuenSabor.dto.usuario;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.entities.Empleado;
import NetDevops.BuenSabor.entities.Sucursal;
import NetDevops.BuenSabor.enums.Rol;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistroDtoEmpleado extends BaseDto {
    private String password;
    private String nombre;
    private String apellido;
    private String Telefono;
    private String email;
    private LocalDate fechaNacimiento;
    private Rol rol;
    private String imagen;
    private Sucursal sucursal;

}
