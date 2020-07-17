package co.edu.cedesistemas.gateway.controller;

import co.edu.cedesistemas.gateway.GatewayApp;
import io.restassured.RestAssured;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = GatewayApp.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class GatewayIT {
    private static final String REGISTRATION_BASE_URL = "http://localhost:8080/cedesistemas/registration/api/v1";
    private static final String COMMERCE_BASE_URL = "http://localhost:8080/cedesistemas/commerce/api/v1";

    @Test
    public void testRouteCommerceCreateStore() {
        Map<String, String> store = new HashMap<>();
        store.put("name", "mystore");
        store.put("phone", "123456789");
        store.put("address", "calle 1");
        store.put("type", "TECHNOLOGY");

        RestAssured.given().contentType("application/json")
                .body(store)
                .when()
                .post(COMMERCE_BASE_URL + "/stores")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testRouteRegistrationCreateUser() {
        Map<String, String> user = new HashMap<>();
        user.put("name", RandomString.make(10));
        user.put("lastName", RandomString.make(10));
        user.put("email", RandomString.make(5) + "@company.com");
        user.put("password", RandomString.make(8));

        RestAssured.given().contentType("application/json")
                .body(user)
                .when()
                .post(REGISTRATION_BASE_URL + "/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }
}
