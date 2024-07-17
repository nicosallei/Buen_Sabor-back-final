package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.entities.Pais;

import java.util.List;

public interface IPaisService {
    public Boolean eliminar(Long id) throws Exception;
    public Pais guardar(Pais entity) throws Exception;
    public Pais modificar(Long id, Pais entity) throws Exception;
    public Pais buscarPorId(Long id) throws Exception;
    public List<Pais> buscarTodos() throws Exception;
    public Boolean reactivar(Long id) throws Exception;
}
