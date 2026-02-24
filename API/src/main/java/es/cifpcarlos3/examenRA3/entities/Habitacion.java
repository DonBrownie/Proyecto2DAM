package es.cifpcarlos3.examenRA3.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Habitacion")
@Data
public class Habitacion {
    @Id
    @Column(name = "id_habitacion")
    private Integer idHabitacion;

    @Column(name = "Disponible", nullable = false)
    private Boolean disponible;
}
