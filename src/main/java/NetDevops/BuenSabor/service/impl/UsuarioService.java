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
            userResponse.setRole(cliente.getRol());
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
            userResponse.setRole(empleado.getRol());
            userResponse.setIdUsuario(empleado.getId());

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
        userResponse.setRole(cliente.getRol());
        userResponse.setIdUsuario(cliente.getId());

        return userResponse;
    }

    // Si no se encuentra en UsuarioCliente, intenta encontrarlo en UsuarioEmpleado
    UsuarioEmpleado usuarioEmpleado = usuarioEmpleadoRepository.findByUsername(username);
    if (usuarioEmpleado != null && usuarioEmpleado.getPassword().equals(hashedPassword)) {
        Empleado empleado = empleadoRepository.findByUsuarioEmpleado_Id(usuarioEmpleado.getId());
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setUsername(usuarioEmpleado.getUsername());
        userResponse.setRole(empleado.getRol());
        userResponse.setIdUsuario(empleado.getId());

        return userResponse;
    }

    // Si no se encuentra en ninguno de los dos, lanza una excepción
    throw new InvalidCredentialsException("Invalid username or password");
}

    public UsuarioCliente registrarUsuario(RegistroDto registroDto) throws Exception {
        // Check if the username already exists
        if (usuarioClienteRepository.existsByUsername(registroDto.getUsername())) {
            throw new Exception("Username already exists");
        }

        // Create a new UsuarioCliente and set its properties from registroDto
        UsuarioCliente usuario = new UsuarioCliente();
        usuario.setUsername(registroDto.getUsername());

        // Hash the password before saving it
        String hashedPassword = seguridadService.hashWithSHA256(registroDto.getPassword());
        usuario.setPassword(hashedPassword);

        // Save the new UsuarioCliente to the database
        UsuarioCliente savedUsuario = usuarioClienteRepository.save(usuario);

        // Create a new Cliente and set its properties from registroDto
        Cliente cliente = new Cliente();
        cliente.setNombre(registroDto.getCliente().getNombre());
        cliente.setApellido(registroDto.getCliente().getApellido());
        cliente.setTelefono(registroDto.getCliente().getTelefono());
        cliente.setEmail(registroDto.getCliente().getEmail());
        cliente.setFechaNacimiento(registroDto.getCliente().getFechaNacimiento());
        cliente.setImagen(registroDto.getCliente().getImagen());

        // Set the saved UsuarioCliente to the Cliente
        cliente.setUsuarioCliente(savedUsuario);

        // Save the Cliente to the database
        clienteRepository.save(cliente);

        return savedUsuario;
    }

    public UsuarioEmpleado registrarEmpleado(RegistroDtoEmpleado registroDtoEmpleado) throws Exception {
    // Check if the username already exists
    if (usuarioEmpleadoRepository.existsByUsername(registroDtoEmpleado.getUsername())) {
        throw new Exception("Username already exists");
    }

    // Create a new UsuarioEmpleado and set its properties from registroDto
    UsuarioEmpleado usuario = new UsuarioEmpleado();
    usuario.setUsername(registroDtoEmpleado.getUsername());

    // Hash the password before saving it
    String hashedPassword = seguridadService.hashWithSHA256(registroDtoEmpleado.getPassword());
    usuario.setPassword(hashedPassword);

    // Save the new UsuarioEmpleado to the database
    UsuarioEmpleado savedUsuario = usuarioEmpleadoRepository.save(usuario);

    // Create a new Empleado and set its properties from registroDto
    Empleado empleado = new Empleado();
    empleado.setNombre(registroDtoEmpleado.getEmpleado().getNombre());
    empleado.setApellido(registroDtoEmpleado.getEmpleado().getApellido());
    empleado.setTelefono(registroDtoEmpleado.getEmpleado().getTelefono());
    empleado.setEmail(registroDtoEmpleado.getEmpleado().getEmail());
    empleado.setFechaNacimiento(registroDtoEmpleado.getEmpleado().getFechaNacimiento());

        if (registroDtoEmpleado.getEmpleado().getImagen() != null) {
            String rutaImagen = funcionalidades.guardarImagen(registroDtoEmpleado.getEmpleado().getImagen(), UUID.randomUUID().toString() + ".jpg");
            registroDtoEmpleado.getEmpleado().setImagen(rutaImagen);
        }
    empleado.setImagen(registroDtoEmpleado.getEmpleado().getImagen());
    empleado.setSucursal(registroDtoEmpleado.getEmpleado().getSucursal());
    empleado.setRol(registroDtoEmpleado.getEmpleado().getRol());

    // Set the saved UsuarioEmpleado to the Empleado
    empleado.setUsuarioEmpleado(savedUsuario);

    // Save the Empleado to the database
    empleadoRepository.save(empleado);

    return savedUsuario;
}


}
