package es.cifpcarlos3.examenRA3.controllers;

import es.cifpcarlos3.examenRA3.entities.Usuario;
import es.cifpcarlos3.examenRA3.entities.Contrasena;
import es.cifpcarlos3.examenRA3.entities.Cliente;
import es.cifpcarlos3.examenRA3.repositories.UsuarioRepository;
import es.cifpcarlos3.examenRA3.repositories.ContrasenaRepository;
import es.cifpcarlos3.examenRA3.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Este controlador es solo para administradores.
 * Sirve para dar de alta a nuevos empleados (usuarios) y clientes.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // Inyectamos los repositorios para guardar cosas en la base de datos.
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContrasenaRepository contrasenaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Este método sirve para crear un nuevo usuario (empleado).
     * Recibe los datos, crea el perfil del usuario y también le asigna su
     * contraseña.
     */
    @PostMapping("/usuarios")
    public ResponseEntity<?> createUsuario(@RequestBody Map<String, Object> data) {
        // Creamos el objeto Usuario con los datos personales que nos mandan.
        Usuario u = new Usuario();
        u.setNombre((String) data.get("nombre"));
        u.setApellido1((String) data.get("apellido1"));
        u.setPuesto((String) data.get("puesto"));
        u.setDni((String) data.get("dni"));
        u.setTelefono((Integer) data.get("telefono"));

        // Guardamos el usuario y nos quedamos con el objeto guardado para saber su ID.
        Usuario savedUser = usuarioRepository.save(u);

        // Ahora creamos la entrada en la tabla de contraseñas asociada a ese usuario.
        Contrasena c = new Contrasena();
        c.setIdUsu(savedUser.getId());
        c.setUsuario((String) data.get("username"));
        c.setContrasena((String) data.get("password")); // El cliente ya nos la manda cifrada.

        contrasenaRepository.save(c);

        return ResponseEntity.ok().build(); // Si todo va bien, mandamos un OK.
    }

    /**
     * Método para dar de alta a un cliente nuevo.
     */
    @PostMapping("/clientes")
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        clienteRepository.save(cliente);
        return ResponseEntity.ok().build();
    }
}
