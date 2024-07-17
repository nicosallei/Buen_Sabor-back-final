package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Cliente;
import NetDevops.BuenSabor.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
    Empleado findByUsuarioEmpleado_Id(Long idUsuarioCliente);
    List<Empleado> findBySucursalId(Long sucursalId);
    Empleado findByEmail(String email);
}
