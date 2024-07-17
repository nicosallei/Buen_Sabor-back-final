package NetDevops.BuenSabor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
//@Audited
public class Empresa extends Base{
private String nombre;
private String razonSocial;
private Long cuil;
private String imagen;




}
