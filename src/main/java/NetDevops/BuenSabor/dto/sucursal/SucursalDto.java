package NetDevops.BuenSabor.dto.sucursal;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.entities.Empresa;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SucursalDto extends BaseDto {

    private String nombre;
    private String horaApertura;
    private String horaCierre;
    private String calle;
    private String numero;
    private String cp;
    private String piso;
    private String nroDepto;
    private String localidad;
    private String provincia;
    private String Pais;
    private String idEmpresa;
       private String imagen;

}
