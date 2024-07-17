package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Cliente;
import NetDevops.BuenSabor.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByUsuarioCliente_Id(Long idUsuarioCliente);
    Cliente findByEmail(String email);
}
