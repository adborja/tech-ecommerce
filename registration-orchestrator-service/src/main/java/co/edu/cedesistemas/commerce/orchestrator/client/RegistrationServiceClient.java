package co.edu.cedesistemas.commerce.orchestrator.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@FeignClient(name = "registration-service", path = "/api/v1")
public interface RegistrationServiceClient {

    @PostMapping("/users")
    ResponseEntity<String> createCommerceUser(@RequestBody User user);

    @DeleteMapping("/users/{id}")
    ResponseEntity<String> deleteCommerceUser(@RequestParam String id);

    @Data
    @Builder
    class User {
        private String id;
        private String name;
        private String lastName;
        private String address;
        private String email;
        private String password;
        private LocalDate birthday;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Status status;

        public enum Status {
            ACTIVE,
            INACTIVE,
            BLOCKED
        }
    }
}
