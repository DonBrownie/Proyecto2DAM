package es.cifpcarlos3.examenRA3.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Reservas")
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer idReserva;

    @Column(name = "dni_cliente", nullable = false)
    private String dniCliente;

    @Transient
    private String nombreCliente;

    @Column(name = "numero_habitacion")
    private Integer numeroHabitacion;

    @Column(name = "fecha_inicio", nullable = false)
    private String fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private String fechaFin;
}
