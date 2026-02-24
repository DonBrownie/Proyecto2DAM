package es.cifpcarlos3.examenRA3.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "apellido_1", nullable = false)
    private String apellido1;

    @Column(name = "apellido_2")
    private String apellido2;

    @Column(nullable = false)
    private String puesto;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false, unique = true)
    private Integer telefono;
}
