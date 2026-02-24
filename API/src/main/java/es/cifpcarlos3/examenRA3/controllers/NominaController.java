package es.cifpcarlos3.examenRA3.controllers;

import es.cifpcarlos3.examenRA3.entities.Nomina;
import es.cifpcarlos3.examenRA3.repositories.NominaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las nóminas de los empleados.
 */
@RestController
@RequestMapping("/api/nominas")
public class NominaController {

    @Autowired
    private NominaRepository nominaRepository;

    /**
     * Saca todas las nóminas que hay en el sistema.
     */
    @GetMapping
    public List<Nomina> getAll() {
        return nominaRepository.findAll();
    }

    /**
     * Busca las nóminas de un usuario específico usando su ID.
     */
    @GetMapping("/usuario/{idUsu}")
    public List<Nomina> getByUsuario(@PathVariable Integer idUsu) {
        return nominaRepository.findByIdUsu(idUsu);
    }

    /**
     * Registra una nómina nueva.
     */
    @PostMapping
    public Nomina create(@RequestBody Nomina nomina) {
        return nominaRepository.save(nomina);
    }
}
