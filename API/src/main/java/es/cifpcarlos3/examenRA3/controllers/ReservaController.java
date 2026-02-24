package es.cifpcarlos3.examenRA3.controllers;

import es.cifpcarlos3.examenRA3.entities.Reserva;
import es.cifpcarlos3.examenRA3.repositories.ReservaRepository;
import es.cifpcarlos3.examenRA3.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para las reservas de habitaciones del hotel.
 * Aquí manejamos todo el lío de quién se queda en qué habitación y cuándo.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Devuelve todas las reservas.
     * Además, intentamos poner el nombre del cliente para que sea más fácil de
     * leer.
     */
    @GetMapping
    public List<Reserva> getAll() {
        List<Reserva> reservas = reservaRepository.findAll();
        // Recorremos cada reserva para buscar el nombre del cliente por su DNI.
        reservas.forEach(this::populateNombreCliente);
        return reservas;
    }

    /**
     * Busca una reserva por su ID único.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getById(@PathVariable Integer id) {
        return reservaRepository.findById(id)
                .map(r -> {
                    populateNombreCliente(r);
                    return ResponseEntity.ok(r);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea una reserva nueva en la base de datos.
     */
    @PostMapping
    public Reserva create(@RequestBody Reserva reserva) {
        Reserva saved = reservaRepository.save(reserva);
        populateNombreCliente(saved);
        return saved;
    }

    /**
     * Actualiza los datos de una reserva (fechas, habitación, cliente...).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> update(@PathVariable Integer id, @RequestBody Reserva details) {
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setDniCliente(details.getDniCliente());
            reserva.setNumeroHabitacion(details.getNumeroHabitacion());
            reserva.setFechaInicio(details.getFechaInicio());
            reserva.setFechaFin(details.getFechaFin());
            Reserva updated = reservaRepository.save(reserva);
            populateNombreCliente(updated);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Este método privado sirve para buscar el nombre y apellidos del cliente
     * usando su DNI.
     * Así en la lista de reservas no solo vemos un número raro de DNI.
     */
    private void populateNombreCliente(Reserva r) {
        if (r.getDniCliente() != null) {
            clienteRepository.findById(r.getDniCliente()).ifPresent(c -> {
                r.setNombreCliente(c.getNombre() + " " + c.getApellidos());
            });
        }
    }

    /**
     * Borra la reserva por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
