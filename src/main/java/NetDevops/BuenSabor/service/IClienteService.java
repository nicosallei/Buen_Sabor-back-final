package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.entities.Cliente;
import java.util.List;

public interface IClienteService {
    public Cliente crearCliente(Cliente cliente) throws Exception;
    public Cliente buscarPorId(Long idCliente) throws Exception;
    public Cliente actualizarCliente(Long idCliente,Cliente cliente) throws Exception;
    public boolean eliminarCliente(Long idCliente) throws Exception;
    public List<Cliente> TraerClientes() throws Exception;
    public Cliente buscarPorEmail(String email) throws Exception;

}
