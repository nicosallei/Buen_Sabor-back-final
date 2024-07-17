package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.entities.UnidadMedida;

import java.util.List;
import java.util.Set;

public interface IUnidadMedidaService {
    public UnidadMedida buscarPorId(Long id) throws Exception;
    public UnidadMedida cargar(UnidadMedida unidadMedida) throws Exception;
    public UnidadMedida actualizar(Long id, UnidadMedida unidadMedida) throws Exception;
    public boolean deleteById(Long id) throws Exception;
    public Set<UnidadMedida> mostrarLista() throws Exception;
    public boolean reactivate(Long id) throws Exception;
    public List<UnidadMedida> traerTodo() throws Exception;
}
