package co.edu.cedesistemas.gateway.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
}
