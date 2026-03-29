package service;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TokenServiceTest {

    @Inject
    TokenService tokenService;

    @Inject
    JWTParser jwtParser;

    //Test to generate JWT token
    @Test
    void testGenerateToken() throws Exception {
        //Arrange
        String userName = "anton";
        Set<String> roles = Set.of("ADMIN", "USER");

        //Act
        String token = tokenService.generateToken(userName, roles);

        //Assert
        assertNotNull(token);

        //Unlocking the JWT
        JsonWebToken jwt = jwtParser.parse(token);

        //Assert
        assertEquals("AntonsIssuerPunktSe", jwt.getIssuer());

        //Getting Principal .getName and comparing it to userName
        assertEquals(userName, jwt.getName());
        assertTrue(jwt.getGroups().containsAll(roles));

        //Checking if JWT expiration time is 1 hour
        long now = System.currentTimeMillis() / 1000;
        assertTrue(jwt.getExpirationTime() > now);

    }

}
