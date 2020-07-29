package co.edu.cedesistemas.gateway.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Integer id;
    private String role;
}
