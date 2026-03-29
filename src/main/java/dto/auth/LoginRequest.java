package dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(

        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z0-9]{4,20}$",
                message = "Must have between 4 and 20 characters.")
        String userName,
        @NotBlank(message = "Password must be at least 8 characters long and include both lowercase and uppercase letters, as well as at least one number.")
        @Pattern(
                regexp = "^(?=.*\\p{Lu})(?=.*\\p{Ll})(?=.*\\d).{8,72}$",
                message = "Password must be at least 8 characters long and include both lowercase and uppercase letters, as well as at least one number."
        )
        String password
) {
}
