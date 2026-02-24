package es.cifpcarlos3.examenRA3.repositories;

import es.cifpcarlos3.examenRA3.entities.Contrasena;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContrasenaRepository extends JpaRepository<Contrasena, Integer> {
    Optional<Contrasena> findByUsuario(String usuario);
}
