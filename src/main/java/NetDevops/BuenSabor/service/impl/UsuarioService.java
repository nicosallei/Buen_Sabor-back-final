package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.dto.usuario.*;
import NetDevops.BuenSabor.entities.Cliente;
import NetDevops.BuenSabor.entities.Empleado;
import NetDevops.BuenSabor.entities.UsuarioCliente;
import NetDevops.BuenSabor.entities.UsuarioEmpleado;
import NetDevops.BuenSabor.repository.IClienteRepository;
import NetDevops.BuenSabor.repository.IEmpleadoRepository;
import NetDevops.BuenSabor.repository.IUsuarioClienteRepository;
import NetDevops.BuenSabor.repository.IUsuarioEmpleadoRepository;
import NetDevops.BuenSabor.service.IUsuarioService;
import NetDevops.BuenSabor.service.funcionalidades.Funcionalidades;
import NetDevops.BuenSabor.service.funcionalidades.SeguridadService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioClienteRepository usuarioClienteRepository;
    @Autowired
    private IUsuarioEmpleadoRepository usuarioEmpleadoRepository;
    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private IEmpleadoRepository empleadoRepository;
    @Autowired
    private SeguridadService seguridadService;
    @Autowired
    private Funcionalidades funcionalidades;

    @Override
    public UsuarioCliente crearUsuarioCliente(UsuarioCliente usuario) throws Exception {
        try {
            String plainPassword = usuario.getPassword();
            String hashedPassword = seguridadService.hashWithSHA256(plainPassword);
            usuario.setPassword(hashedPassword);
            return usuarioClienteRepository.save(usuario);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public UserResponseDto loginCliente(String username, String password) throws InvalidCredentialsException {
        UsuarioCliente usuario = usuarioClienteRepository.findByUsername(username);
        String hashedPassword = seguridadService.hashWithSHA256(password);
        if (usuario != null && usuario.getPassword().equals(hashedPassword)) {
            Cliente cliente = clienteRepository.findByUsuarioCliente_Id(usuario.getId());
            UserResponseDto userResponse = new UserResponseDto();
            userResponse.setUsername(usuario.getUsername());
            userResponse.setRol(cliente.getRol());
            userResponse.setIdUsuario(cliente.getId());

            return userResponse;
        } else {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }


    @Override
    public UsuarioEmpleado crearUsuarioEmpleado(UsuarioEmpleado usuario) throws Exception {
        try {
            String plainPassword = usuario.getPassword();
            String hashedPassword = seguridadService.hashWithSHA256(plainPassword);
            usuario.setPassword(hashedPassword);
            return usuarioEmpleadoRepository.save(usuario);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public UserResponseDto loginEmpleado(String username, String password) throws InvalidCredentialsException {
        UsuarioEmpleado usuario = usuarioEmpleadoRepository.findByUsername(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            Empleado empleado = empleadoRepository.findByUsuarioEmpleado_Id(usuario.getId());
            UserResponseDto userResponse = new UserResponseDto();
            userResponse.setUsername(usuario.getUsername());
            userResponse.setRol(empleado.getRol());
            userResponse.setIdUsuario(empleado.getId());
            userResponse.setIdSucursal(empleado.getSucursal().getId());
            userResponse.setIdEmpresa(empleado.getSucursal().getEmpresa().getId());

            return userResponse;
        } else {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    public UserResponseDto login(String username, String password) throws InvalidCredentialsException {

    UsuarioCliente usuarioCliente = usuarioClienteRepository.findByUsername(username);
    String hashedPassword = seguridadService.hashWithSHA256(password);
    if (usuarioCliente != null && usuarioCliente.getPassword().equals(hashedPassword)) {
        Cliente cliente = clienteRepository.findByUsuarioCliente_Id(usuarioCliente.getId());
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setUsername(usuarioCliente.getUsername());
        userResponse.setRol(cliente.getRol());
        userResponse.setIdUsuario(cliente.getId());
        userResponse.setIdCliente(cliente.getId());

        return userResponse;
    }


    UsuarioEmpleado usuarioEmpleado = usuarioEmpleadoRepository.findByUsername(username);
    if (usuarioEmpleado != null && usuarioEmpleado.getPassword().equals(hashedPassword)) {
        Empleado empleado = empleadoRepository.findByUsuarioEmpleado_Id(usuarioEmpleado.getId());
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setUsername(usuarioEmpleado.getUsername());
        userResponse.setRol(empleado.getRol());
        userResponse.setIdUsuario(empleado.getId());
        userResponse.setIdEmpleado(empleado.getId());
        userResponse.setIdSucursal(empleado.getSucursal().getId());
        userResponse.setIdEmpresa(empleado.getSucursal().getEmpresa().getId());

        return userResponse;
    }


    throw new InvalidCredentialsException("Invalid username or password");
}

    public UsuarioCliente registrarUsuario(RegistroDto registroDto) throws Exception {

       if (clienteRepository.existsByEmail(registroDto.getCliente().getEmail()) ||
    empleadoRepository.existsByEmail(registroDto.getCliente().getEmail())) {
    throw new Exception("Username or email already exists");
}


        UsuarioCliente usuario = new UsuarioCliente();
        usuario.setUsername(registroDto.getCliente().getEmail());


        String hashedPassword = seguridadService.hashWithSHA256(registroDto.getPassword());
        usuario.setPassword(hashedPassword);


        UsuarioCliente savedUsuario = usuarioClienteRepository.save(usuario);


        Cliente cliente = new Cliente();
        cliente.setNombre(registroDto.getCliente().getNombre());
        cliente.setApellido(registroDto.getCliente().getApellido());
        cliente.setTelefono(registroDto.getCliente().getTelefono());
        cliente.setEmail(registroDto.getCliente().getEmail());
        cliente.setFechaNacimiento(registroDto.getCliente().getFechaNacimiento());
        cliente.setImagen(registroDto.getCliente().getImagen());


        cliente.setUsuarioCliente(savedUsuario);

        clienteRepository.save(cliente);

        return savedUsuario;
    }

    public UsuarioEmpleado registrarEmpleado(RegistroDtoEmpleado registroDtoEmpleado) throws Exception {

        if (clienteRepository.existsByEmail(registroDtoEmpleado.getEmail()) ||
                empleadoRepository.existsByEmail(registroDtoEmpleado.getEmail())) {
            throw new Exception("Username or email already exists");
        }

    UsuarioEmpleado usuario = new UsuarioEmpleado();
    usuario.setUsername(registroDtoEmpleado.getEmail());

    String hashedPassword = seguridadService.hashWithSHA256(registroDtoEmpleado.getPassword());
    usuario.setPassword(hashedPassword);

    UsuarioEmpleado savedUsuario = usuarioEmpleadoRepository.save(usuario);

    Empleado empleado = new Empleado();
    empleado.setNombre(registroDtoEmpleado.getNombre());
    empleado.setApellido(registroDtoEmpleado.getApellido());
    empleado.setTelefono(registroDtoEmpleado.getTelefono());
    empleado.setEmail(registroDtoEmpleado.getEmail());
    empleado.setFechaNacimiento(registroDtoEmpleado.getFechaNacimiento());

        if (registroDtoEmpleado.getImagen() != null) {
            String rutaImagen = funcionalidades.guardarImagen(registroDtoEmpleado.getImagen(), UUID.randomUUID().toString() + ".jpg");
            registroDtoEmpleado.setImagen(rutaImagen);
        }
    empleado.setImagen(registroDtoEmpleado.getImagen());
    empleado.setSucursal(registroDtoEmpleado.getSucursal());
    empleado.setRol(registroDtoEmpleado.getRol());


    empleado.setUsuarioEmpleado(savedUsuario);

    empleadoRepository.save(empleado);

    return savedUsuario;
}

    public boolean actualizarPasswordCliente(Long clienteId, String nuevaPassword) throws Exception {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new Exception("Cliente no encontrado con id: " + clienteId));
        Long usuarioId = cliente.getUsuarioCliente().getId();
        UsuarioCliente usuario = usuarioClienteRepository.findById(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado con id: " + usuarioId));
        String hashedPassword = seguridadService.hashWithSHA256(nuevaPassword);
        usuario.setPassword(hashedPassword);
        usuarioClienteRepository.save(usuario);
        return true;
    }

    public boolean actualizarPasswordEmpleado(Long empleadoId, String nuevaPassword) throws Exception {
    Empleado empleado = empleadoRepository.findById(empleadoId)
            .orElseThrow(() -> new Exception("Empleado no encontrado con id: " + empleadoId));
    Long usuarioId = empleado.getUsuarioEmpleado().getId();
    UsuarioEmpleado usuario = usuarioEmpleadoRepository.findById(usuarioId).orElseThrow(() -> new Exception("Usuario no encontrado con id: " + usuarioId));
    String hashedPassword = seguridadService.hashWithSHA256(nuevaPassword);
    usuario.setPassword(hashedPassword);
    usuarioEmpleadoRepository.save(usuario);
    return true;
}


public boolean cambiarPasswordCliente(String username, String passwordActual, String nuevaPassword) throws Exception {

    UsuarioCliente usuarioCliente = usuarioClienteRepository.findByUsername(username);
    if (usuarioCliente == null) {
        throw new Exception("Usuario no encontrado con username: " + username);
    }


    String hashedPasswordActual = seguridadService.hashWithSHA256(passwordActual);


    if (!usuarioCliente.getPassword().equals(hashedPasswordActual)) {
        throw new InvalidCredentialsException("La contraseña actual es incorrecta");
    }


    String hashedNuevaPassword = seguridadService.hashWithSHA256(nuevaPassword);


    usuarioCliente.setPassword(hashedNuevaPassword);


    usuarioClienteRepository.save(usuarioCliente);

    return true;
}

public boolean cambiarPasswordEmpleado(String username, String passwordActual, String nuevaPassword) throws Exception {

    UsuarioEmpleado usuarioEmpleado = usuarioEmpleadoRepository.findByUsername(username);
    if (usuarioEmpleado == null) {
        throw new Exception("Empleado no encontrado con username: " + username);
    }


    String hashedPasswordActual = seguridadService.hashWithSHA256(passwordActual);


    if (!usuarioEmpleado.getPassword().equals(hashedPasswordActual)) {
        throw new InvalidCredentialsException("La contraseña actual es incorrecta");
    }


    String hashedNuevaPassword = seguridadService.hashWithSHA256(nuevaPassword);


    usuarioEmpleado.setPassword(hashedNuevaPassword);


    usuarioEmpleadoRepository.save(usuarioEmpleado);

    return true;
}
    public UserResponseDto buscarUsuarioPorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente != null) {
            UserResponseDto usuarioDto = new UserResponseDto();
            usuarioDto.setIdUsuario(cliente.getId());
            usuarioDto.setUsername(cliente.getEmail());
            usuarioDto.setRol(cliente.getRol());
            usuarioDto.setIdCliente(cliente.getId());
            return usuarioDto;

        }

        Empleado empleado = empleadoRepository.findByEmail(email);
        if (empleado != null) {
            UserResponseDto usuarioDto = new UserResponseDto();
            usuarioDto.setIdUsuario(empleado.getId());
            usuarioDto.setIdEmpleado(empleado.getId());
            usuarioDto.setUsername(empleado.getEmail());
            usuarioDto.setRol(empleado.getRol());
            usuarioDto.setIdEmpresa(empleado.getSucursal().getEmpresa().getId());
            usuarioDto.setIdSucursal(empleado.getSucursal().getId());
            return usuarioDto;
        }

        return null;
    }

}
