package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.entities.Localidad;

import java.util.List;

public interface ILocalidadService {
    public Boolean eliminar(Long id) throws Exception;
    public Localidad guardar(Localidad entity) throws Exception;
    public Localidad modificar(Long id, Localidad entity) throws Exception;
    public Localidad buscarPorId(Long id) throws Exception;
    public List<Localidad> buscarTodos() throws Exception;
    public Boolean reactivar(Long id) throws Exception;
    public List<Localidad> getLocalidadesByProvinciaId(Long provinciaId);
}
