package es.cifpcarlos3.examenRA3.controllers;

import es.cifpcarlos3.examenRA3.entities.Usuario;
import es.cifpcarlos3.examenRA3.entities.Contrasena;
import es.cifpcarlos3.examenRA3.repositories.ContrasenaRepository;
import es.cifpcarlos3.examenRA3.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.cifpcarlos3.examenRA3.utils.SecurityUtils;
import java.util.Map;

/**
 * Este es el controlador para la autenticación.
 * Se encarga del login y de cambiar la contraseña si el usuario quiere.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ContrasenaRepository contrasenaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * El método de login. Recibe el usuario y la contraseña,
     * los comprueba y si todo está bien devuelve los datos del usuario.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Si falta algo, mandamos un error 400.
        if (username == null || password == null) {
            return ResponseEntity.status(400).body(Map.of("message", "Oye, que faltan el usuario o la contraseña"));
        }

        // Ciframos la contraseña que nos llega para compararla con la de la base de
        // datos.
        String hashedPassword = SecurityUtils.hashPassword(username, password);

        // Buscamos al usuario por su nombre.
        return contrasenaRepository.findByUsuario(username)
                .map(c -> {
                    // Si encontramos al usuario, comparamos los hashes.
                    if (c.getContrasena().equals(hashedPassword)) {
                        // Si coinciden, buscamos su perfil completo y lo devolvemos.
                        return usuarioRepository.findById(c.getIdUsu())
                                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(user))
                                .orElseGet(() -> ResponseEntity.status(401)
                                        .body(Map.of("message", "Error interno: Perfil no encontrado")));
                    } else {
                        // Si no coinciden, fuera.
                        return ResponseEntity.status(401).body(Map.of("message", "Contraseña incorrecta"));
                    }
                })
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("message", "Usuario no encontrado")));
    }

    /**
     * Para cambiar la contraseña.
     * Buscamos por el ID del usuario y guardamos la nueva clave (que ya viene
     * cifrada).
     */
    @PutMapping("/updatePassword/{idUsu}")
    public ResponseEntity<?> updatePassword(@PathVariable Integer idUsu, @RequestBody Map<String, String> body) {
        String newPassword = body.get("newPassword");

        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "La nueva contraseña es obligatoria"));
        }

        return contrasenaRepository.findById(idUsu).map(contrasena -> {
            contrasena.setContrasena(newPassword);
            contrasenaRepository.save(contrasena);
            return ResponseEntity.ok(Map.of("message", "¡Contraseña actualizada!"));
        }).orElseGet(() -> ResponseEntity.status(404).body(Map.of("message", "Usuario no encontrado")));
    }
}
