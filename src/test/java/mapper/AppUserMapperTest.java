package mapper;

import domain.AppUser;
import dto.appuser.AppUserRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class AppUserMapperTest {

    @Test
    void cleanAndLc() {
        AppUserRequest request = new AppUserRequest(
                "  KalleAnka  ",
                "Lösen123!",
                "KALLE@Mail.com",
                "Kalle",
                "Anka",
                LocalDate.of(1990, 1, 1),
                "Gatan 1",
                "Staden"
        );

        AppUser entity = AppUserMapper.toEntity(request);


        assertEquals("kalleanka", entity.userName);
        assertEquals("kalle@mail.com", entity.email);
    }

    @Test
    void testHandleNullUserName() {
        AppUserRequest request = new AppUserRequest(null, "Lösen123!", "mail@mail.com", "Kalle", "Anka", null, "Gatan 1", "Staden");

        AppUser entity = AppUserMapper.toEntity(request);

        assertNull(entity.userName);
    }


}
