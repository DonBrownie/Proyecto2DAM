package es.cifpcarlos3.examenRA3.repositories;

import es.cifpcarlos3.examenRA3.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
