
package co.edu.cedesistemas.commerce.orchestrator.client;

import co.edu.cedesistemas.common.model.Status;
import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "registration-service", path = "/api/v1")
public interface RegistrationServiceClient {

    @DeleteMapping("/{userId}")
    ResponseEntity<Status<?>> deleteUser(@PathVariable String userId);

    @Data
    @Builder
    class User {
        private String id;
    }
}