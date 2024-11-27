package org.tfg.inhometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetEventosMesRequestDTO {
    private int idCasa;
    private int mes;
    private int anio;
}
