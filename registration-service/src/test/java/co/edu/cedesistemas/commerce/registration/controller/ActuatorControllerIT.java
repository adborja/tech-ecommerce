package co.edu.cedesistemas.commerce.registration.controller;

import co.edu.cedesistemas.commerce.registration.RegistrationApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RegistrationApp.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ActuatorControllerIT {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void testActuatorInfo() throws Exception {
        mvc.perform(get("/actuator/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.app.name", is("registration-service")))
                .andExpect(jsonPath("$.app.description", is("e-commerce registration service")))
                .andExpect(jsonPath("$.app.version", is("1.0.0")));
    }

    @Test
    public void testActuatorHealth() throws Exception {
        mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")));
    }

    @Test
    public void testActuatorEnv() throws Exception {
        mvc.perform(get("/actuator/env"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeProfiles[0]", is("test")));
    }
}
