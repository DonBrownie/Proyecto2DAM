package es.cifpcarlos3.examenRA3.repositories;

import es.cifpcarlos3.examenRA3.entities.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NominaRepository extends JpaRepository<Nomina, Integer> {
    List<Nomina> findByIdUsu(Integer idUsu);
}
