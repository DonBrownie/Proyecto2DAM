package es.cifpcarlos3.examenRA3.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculoResumenDTO {
    private String matricula;
    private String modelo;
    private String fabricante;
    private int numServicios;

}
