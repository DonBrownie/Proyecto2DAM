package es.cifpcarlos3.examenRA3.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Contrasena")
@Data
public class Contrasena {
    @Id
    @Column(name = "id_usu")
    private Integer idUsu;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String contrasena;
}
