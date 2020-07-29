package co.edu.cedesistemas.comerce.orchestrator.client;

import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "registration-service", path = "/api/v1")
public interface RegistrationServiceClient {

    @PostMapping("/users")
    ResponseEntity<String> createUser(@RequestBody User user);

    @DeleteMapping("/users/{id}")
    ResponseEntity<String> deleteUser(@PathVariable String id);

    @Data
    @Builder
    class User {
        private String id;
    }
}
