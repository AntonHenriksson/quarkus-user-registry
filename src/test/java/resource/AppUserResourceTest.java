package resource;

import domain.AppUser;
import dto.appuser.AppUserRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import mapper.AppUserMapper;
import org.junit.jupiter.api.*;


import java.time.LocalDate;

import static io.restassured.RestAssured.given;


@QuarkusTest
public class AppUserResourceTest {

    @BeforeEach
    @Transactional
    void setup() {
        AppUser.persist(AppUserMapper.toEntity(createDefaultUser()));
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
                , "resourcePW1"
                , "setupmail@mail.se"
                , "resource"
                , "lastResource"
                , LocalDate.of(1981, 9, 10)
                , "address 3"
                , "cityBig"
        );
    }


    @Test
    @Transactional
    void createUser() {
        AppUserRequest request = new AppUserRequest(
                "resourceUser"
                , "resourcePW1"
                , "mail@mail.se"
                , "resource"
                , "lastResource"
                , LocalDate.of(1981, 9, 10)
                , "address 3"
                , "cityBig"
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/users/register")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    //Test for get user with id as ADMIN
    @Test
    @Transactional
    @TestSecurity(user = "testAdmin", roles = {"ADMIN"})
    void getUser() {
        AppUser appUser = AppUser.findAll().firstResult();


        given()
                .get("/api/users/id/" + appUser.id)
                .then()
                .statusCode(200);

    }

    //Test that get user by Id fails if not ADMIN
    @Test
    @Transactional
    @TestSecurity(user = "notAdmin", roles = {"USER"})
    void getUserFail() {
        AppUser appUser = AppUser.findAll().firstResult();

        given()
                .get("/api/users/id/" + appUser.id)
                .then()
                .statusCode(403);

    }

}
