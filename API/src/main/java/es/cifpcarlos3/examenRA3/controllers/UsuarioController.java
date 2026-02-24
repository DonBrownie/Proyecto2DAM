package es.cifpcarlos3.examenRA3.controllers;

import es.cifpcarlos3.examenRA3.entities.Usuario;
import es.cifpcarlos3.examenRA3.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar los usuarios (los empleados del hotel).
 * Aquí es donde se manejan los perfiles de la gente que trabaja con nosotros.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Saca la lista de todos los empleados.
     */
    @GetMapping
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca a un empleado por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un perfil de usuario nuevo.
     */
    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los datos de un empleado (nombre, apellidos, puesto, teléfono...).
     * Lo usamos por ejemplo cuando alguien cambia su número de teléfono.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setApellido1(usuarioDetails.getApellido1());
            usuario.setApellido2(usuarioDetails.getApellido2());
            usuario.setPuesto(usuarioDetails.getPuesto());
            usuario.setDni(usuarioDetails.getDni());
            usuario.setTelefono(usuarioDetails.getTelefono());
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Borra un usuario del sistema por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
