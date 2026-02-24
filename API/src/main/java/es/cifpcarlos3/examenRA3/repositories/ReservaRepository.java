package es.cifpcarlos3.examenRA3.repositories;

import es.cifpcarlos3.examenRA3.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
