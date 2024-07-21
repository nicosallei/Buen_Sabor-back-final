package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.dto.usuario.RegistroDto;
import NetDevops.BuenSabor.dto.usuario.RegistroDtoEmpleado;
import NetDevops.BuenSabor.dto.usuario.UserResponseDto;
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
    // Primero, intenta encontrar el usuario en UsuarioCliente
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

    // Si no se encuentra en UsuarioCliente, intenta encontrarlo en UsuarioEmpleado
    UsuarioEmpleado usuarioEmpleado = usuarioEmpleadoRepository.findByUsername(username);
    if (usuarioEmpleado != null && usuarioEmpleado.getPassword().equals(hashedPassword)) {
        Empleado empleado = empleadoRepository.findByUsuarioEmpleado_Id(usuarioEmpleado.getId());
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setUsername(usuarioEmpleado.getUsername());
        userResponse.setRol(empleado.getRol());
        userResponse.setIdUsuario(empleado.getId());

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


}
