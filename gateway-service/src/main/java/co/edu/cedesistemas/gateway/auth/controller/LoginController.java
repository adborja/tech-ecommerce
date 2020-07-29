package co.edu.cedesistemas.gateway.auth.controller;

import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import co.edu.cedesistemas.gateway.auth.model.AuthResponse;
import co.edu.cedesistemas.gateway.auth.service.ILoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LoginController {
    private final ILoginService loginService;

    @CrossOrigin("*")
    @PostMapping("/signin")
    public ResponseEntity<Status<?>> login(@RequestHeader("Authentication") String basic) {
        if (!basic.startsWith("Basic ")) {
            return DefaultResponseBuilder.errorResponse("bad authentication header",
                    null, HttpStatus.BAD_REQUEST);
        }
        try {
            byte[] bCreds = Base64.getDecoder().decode(basic.split(" ")[1]);
            String creds = new String(bCreds);
            String[] credentials = creds.split(":");
            String username = credentials[0];
            String password = credentials[1];
            String token = loginService.login(username, password);

            HttpHeaders headers = getHeaders(token);

            AuthResponse response = AuthResponse.builder()
                    .accessToken(token)
                    .build();
            return DefaultResponseBuilder.defaultResponse(response, headers, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin("*")
    @PostMapping("/signout")
    public ResponseEntity<Status<?>> logout (@RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        if (loginService.logout(token)) {
            headers.remove("Authorization");
            return DefaultResponseBuilder.defaultResponse(AuthResponse.builder().accessToken("logged out").build(),
                    headers, HttpStatus.CREATED);
        }
        return DefaultResponseBuilder.errorResponse("logout failed", null, HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/valid/token")
    public Boolean isValidToken(@RequestHeader("Authorization") String token) {
        return true;
    }

    @PostMapping("/signin/token")
    @CrossOrigin("*")
    public ResponseEntity<Status<?>> createNewToken (@RequestHeader(value="Authorization") String token) {
        String newToken = loginService.createNewToken(token);
        HttpHeaders headers = getHeaders(newToken);
        return DefaultResponseBuilder.defaultResponse(AuthResponse.builder().accessToken(newToken).build(),
                headers, HttpStatus.CREATED);
    }

    private static HttpHeaders getHeaders(final String token) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerList = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerList.add("Content-Type");
        headerList.add("Accept");
        headerList.add("X-Requested-With");
        headerList.add("Authorization");
        headers.setAccessControlAllowHeaders(headerList);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", token);
        return headers;
    }
}
