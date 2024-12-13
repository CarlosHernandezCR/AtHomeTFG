package org.tfg.athometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventosEnDiaRequestDTO {
    private int idCasa;
    private int dia;
    private int mes;
    private int anio;
}
