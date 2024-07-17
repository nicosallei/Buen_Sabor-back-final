package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.entities.Empresa;

import java.util.List;

public interface IEmpresaService {
public Empresa save(Empresa empresa) throws Exception;
public Empresa update(Long id, Empresa empresa) throws Exception;
public boolean delete(Long id) throws Exception;
public List<Empresa> traerTodo() throws Exception;
public Empresa traerPorId(Long id) throws Exception;
public boolean reactivate(Long id) throws Exception;
public List<Empresa> traerTodoNoEliminado() throws Exception;



}
