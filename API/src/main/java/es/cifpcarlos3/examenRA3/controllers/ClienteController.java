package es.cifpcarlos3.examenRA3.controllers;

import es.cifpcarlos3.examenRA3.entities.Cliente;
import es.cifpcarlos3.examenRA3.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar los clientes.
 * Tiene las típicas operaciones de crear, leer, actualizar y borrar (CRUD).
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Devuelve la lista de todos los clientes que tenemos.
     */
    @GetMapping
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su DNI.
     */
    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> getByDni(@PathVariable String dni) {
        return clienteRepository.findById(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un cliente nuevo.
     */
    @PostMapping
    public Cliente create(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza los datos de un cliente que ya existe usando su DNI.
     */
    @PutMapping("/{dni}")
    public ResponseEntity<Cliente> update(@PathVariable String dni, @RequestBody Cliente clienteDetails) {
        return clienteRepository.findById(dni).map(cliente -> {
            cliente.setNombre(clienteDetails.getNombre());
            cliente.setApellidos(clienteDetails.getApellidos());
            return ResponseEntity.ok(clienteRepository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Borra a un cliente de la base de datos por su DNI.
     */
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> delete(@PathVariable String dni) {
        if (clienteRepository.existsById(dni)) {
            clienteRepository.deleteById(dni);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
