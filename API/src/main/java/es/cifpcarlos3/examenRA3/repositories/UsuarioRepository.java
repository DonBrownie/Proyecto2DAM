package es.cifpcarlos3.examenRA3.repositories;

import es.cifpcarlos3.examenRA3.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
