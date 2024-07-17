package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.ImagenCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagenClienteRepository extends JpaRepository<ImagenCliente, Long> {
}
