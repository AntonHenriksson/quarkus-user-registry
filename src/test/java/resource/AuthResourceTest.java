package resource;


import domain.AppUser;
import dto.appuser.AppUserRequest;
import dto.auth.LoginRequest;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import mapper.AppUserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class AuthResourceTest {

    static String falsePassword = "falsePassword1";
    static String correctPassword = "resourcePW1";
    static String fakeName = "fakeName";

    @BeforeEach
    @Transactional
    void setup() {
        AppUser appUser = AppUserMapper.toEntity(createDefaultUser());
        appUser.password = BcryptUtil.bcryptHash(correctPassword);
        appUser.persist();
    }

    @AfterEach
    @Transactional
    void deleteData() {
        AppUser.deleteAll();
    }

    @Transactional
    static public AppUserRequest createDefaultUser() {
        return new AppUserRequest(
                "resourceSetupUser"
                , correctPassword
                , "setupmail@mail.se"
                , "resource"
                , "lastResource"
                , LocalDate.of(1981, 9, 10)
                , "address 3"
                , "cityBig"
        );
    }

    @Test
    void loginTest() {
        AppUser appUser = AppUser.findAll().firstResult();


        LoginRequest loginRequest = new LoginRequest(
                appUser.userName,
                correctPassword
        );
        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)


                .when()
                .post("api/auth/login")


                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    //Test if wrong password
    @Test
    void loginTestFailPassword() {

        AppUser appUser = AppUser.findAll().firstResult();


        LoginRequest loginRequest = new LoginRequest(
                appUser.userName,
                falsePassword
        );
        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)


                .when()
                .post("api/auth/login")


                .then()
                .statusCode(401);
    }

    @Test
    void loginTestFailUserName() {

        AppUser appUser = AppUser.findAll().firstResult();


        LoginRequest loginRequest = new LoginRequest(
                fakeName,
                correctPassword
        );
        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)


                .when()
                .post("api/auth/login")


                .then()
                .statusCode(401);
    }


}
