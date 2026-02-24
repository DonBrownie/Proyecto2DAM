package es.cifpcarlos3.examenRA3.controllers;

import es.cifpcarlos3.examenRA3.entities.Habitacion;
import es.cifpcarlos3.examenRA3.repositories.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para ver y actualizar el estado de las habitaciones del hotel.
 */
@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionRepository habitacionRepository;

    /**
     * Saca la lista de todas las habitaciones.
     */
    @GetMapping
    public List<Habitacion> getAll() {
        return habitacionRepository.findAll();
    }

    /**
     * Busca una habitación por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> getById(@PathVariable Integer id) {
        return habitacionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza si una habitación está disponible o no.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> update(@PathVariable Integer id, @RequestBody Habitacion details) {
        return habitacionRepository.findById(id).map(hab -> {
            hab.setDisponible(details.getDisponible());
            return ResponseEntity.ok(habitacionRepository.save(hab));
        }).orElse(ResponseEntity.notFound().build());
    }
}
