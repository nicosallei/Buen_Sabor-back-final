package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.UsuarioCliente;
import NetDevops.BuenSabor.entities.UsuarioEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioEmpleadoRepository extends JpaRepository<UsuarioEmpleado, Long> {
    UsuarioEmpleado findByUsername(String username);
    boolean existsByUsername(String username);
}
