package es.cifpcarlos3.examenRA3.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Nomina")
@Data
public class Nomina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nomina")
    private Integer idNomina;

    @Column(name = "id_usu", nullable = false)
    private Integer idUsu;

    private Float pago;

    @Column(name = "fecha", insertable = false, updatable = false)
    private LocalDateTime fecha;
}
