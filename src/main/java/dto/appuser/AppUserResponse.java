package dto.appuser;

import java.time.Instant;
import java.time.LocalDate;

public record AppUserResponse(
        String id,
        String userName,
        String email,
        String firstName,
        String lastName,
        LocalDate birth,
        String address,
        String city,
        Instant createdAt
) {
}
