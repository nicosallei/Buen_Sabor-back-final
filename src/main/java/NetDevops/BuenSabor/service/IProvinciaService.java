package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.entities.Provincia;

import java.util.List;

public interface IProvinciaService {
    public Boolean eliminar(Long id) throws Exception;
    public Provincia guardar(Provincia entity) throws Exception;
    public Provincia modificar(Long id, Provincia entity) throws Exception;
    public Provincia buscarPorId(Long id) throws Exception;
    public List<Provincia> buscarTodos() throws Exception;
    public Boolean reactivar(Long id) throws Exception;
}
