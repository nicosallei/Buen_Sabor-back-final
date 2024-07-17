package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.entities.Domicilio;

import java.util.List;

public interface IDomicilioService {
    public Boolean eliminar(Long id) throws Exception;
    public Domicilio guardar(Domicilio entity) throws Exception;
    public Domicilio modificar(Long id, Domicilio entity) throws Exception;
    public Domicilio buscarPorId(Long id) throws Exception;
    public List<Domicilio> buscarTodos() throws Exception;
    public Boolean reactivar(Long id) throws Exception;

}
