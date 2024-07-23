package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.entities.Cliente;
import NetDevops.BuenSabor.entities.Empleado;
import NetDevops.BuenSabor.repository.IClienteRepository;
import NetDevops.BuenSabor.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {
    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public Cliente crearCliente(Cliente cliente) throws Exception {
        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Cliente buscarPorId(Long idCliente) throws Exception {
        try {
            return clienteRepository.findById(idCliente).orElse(null);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Cliente actualizarCliente(Long idCliente, Cliente cliente) throws Exception {
        try {
            Cliente clienteActual = clienteRepository.findById(idCliente).orElse(null);
            if (clienteActual == null) {
                return null;
            }
            cliente.setId(idCliente);
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean eliminarCliente(Long idCliente) throws Exception {
        try {
            clienteRepository.deleteById(idCliente);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Cliente> TraerClientes() throws Exception {
        try {
            return clienteRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }




}
