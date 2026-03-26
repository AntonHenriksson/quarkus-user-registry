package dto.appuser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AppUserUpdatePasswordRequest(
        @NotBlank(message = "Password must be at least 8 characters long and include both lowercase and uppercase letters, as well as at least one number.")
        // The regex pattern here allows for Unicode letters
        @Pattern(
                regexp = "^(?=.*\\p{Lu})(?=.*\\p{Ll})(?=.*\\d).{8,}$",
                message = "Password must be at least 8 characters long and include both lowercase and uppercase letters, as well as at least one number."
        )
        String password
) {
}
