package org.tfg.athometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessTokenResponseDTO {
    private String accessToken;
}