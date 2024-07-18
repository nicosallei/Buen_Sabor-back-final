package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.dto.usuario.RegistroDto;
import NetDevops.BuenSabor.dto.usuario.RegistroDtoEmpleado;
import NetDevops.BuenSabor.dto.usuario.UserResponseDto;
import NetDevops.BuenSabor.dto.usuario.UsuarioDto;
import NetDevops.BuenSabor.entities.UsuarioCliente;
import NetDevops.BuenSabor.entities.UsuarioEmpleado;
import NetDevops.BuenSabor.errores.ApiError;
import NetDevops.BuenSabor.service.impl.UsuarioService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// GET http://localhost:8080/api/usuario/

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/cliente")
    public ResponseEntity<?> crearCliente(@RequestBody UsuarioCliente usuarioCliente) {
        try {
            return ResponseEntity.ok().body(usuarioService.crearUsuarioCliente(usuarioCliente));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @GetMapping("/cliente/login")
    public ResponseEntity<?> loginCliente(@RequestParam String username, @RequestParam String password) {
        try {
            return ResponseEntity.ok().body(usuarioService.loginCliente(username, password));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<?> crearEmpleado(@RequestBody UsuarioEmpleado usuarioEmpleado) {
        try {
            return ResponseEntity.ok().body(usuarioService.crearUsuarioEmpleado(usuarioEmpleado));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @GetMapping("/empleado/login")
    public ResponseEntity<?> loginEmpleado(@RequestParam String username, @RequestParam String password) {
        try {
            return ResponseEntity.ok().body(usuarioService.loginEmpleado(username, password));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDto userDto) {
        try {
            UserResponseDto userResponse = usuarioService.login(userDto.getUsername(), userDto.getPassword());
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/registro/usuario-cliente")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDto registroDto) {
        try {
            UsuarioCliente usuario = usuarioService.registrarUsuario(registroDto);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/registro/usuario-empleado")
    @PreAuthorize(" hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> registrarEmpleado(@RequestBody RegistroDtoEmpleado registroDtoEmpleado) {
        try {
            UsuarioEmpleado usuario = usuarioService.registrarEmpleado(registroDtoEmpleado);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }


}
