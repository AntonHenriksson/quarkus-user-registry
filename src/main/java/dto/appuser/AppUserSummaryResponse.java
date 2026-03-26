package dto.appuser;


public record AppUserSummaryResponse(
        String id,
        String userName,
        String email,
        String firstName
) {
}
