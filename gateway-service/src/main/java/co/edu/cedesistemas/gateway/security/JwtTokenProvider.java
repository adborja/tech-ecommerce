package co.edu.cedesistemas.gateway.security;

import co.edu.cedesistemas.gateway.auth.model.JwtToken;
import co.edu.cedesistemas.gateway.auth.model.MongoUserDetails;
import co.edu.cedesistemas.gateway.auth.repository.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private static final String AUTH = "auth";
    private static final String AUTHORIZATION = "Authorization";
    private String secretKey = "c3d3S1sTem@s";

    private final JwtTokenRepository repository;

    public JwtTokenProvider(final JwtTokenRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(final String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTH, roles);
        Date now = new Date();
        long validityInMillis = 3600000; // 1 hour
        Date validity = new Date(now.getTime() + validityInMillis);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        repository.save(new JwtToken(token));
        return token;
    }

    public String resolveToken(final HttpServletRequest req) {
        return req.getHeader(AUTHORIZATION);
    }

    public boolean validateToken(final String token) throws JwtException, IllegalArgumentException {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return true;
    }

    public boolean isTokenPresentInDB (String token) {
        return repository.findById(token).isPresent();
    }

    public UserDetails getUserDetails(final String token) {
        String username = getUsername(token);
        List<String> roleList = getRoleList(token);
        String[] roles = new String[roleList.size()];
        return new MongoUserDetails(username, roleList.toArray(roles));
    }

    public List<String> getRoleList(final String token) {
        return (List<String>) Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get(AUTH, List.class);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(final String token) {
        UserDetails userDetails = getUserDetails(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
