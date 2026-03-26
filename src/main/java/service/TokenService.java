package service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class TokenService {

    public String generateToken(String username, Set<String> roles) {
        return Jwt.issuer("AntonsIssuerPunktSe")
                .upn(username)
                .groups(roles)
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}
