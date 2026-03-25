package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record AppUserRequest(
        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z0-9]{4,20}$",
                message = "Must have between 4 and 20 characters.")
        String userName,
        @NotBlank(message = "Password must be at least 8 characters long and include both lowercase and uppercase letters, as well as at least one number.")
        @Pattern(
                regexp = "^(?=.*\\p{Lu})(?=.*\\p{Ll})(?=.*\\d).{8,}$",
                message = "Password must be at least 8 characters long and include both lowercase and uppercase letters, as well as at least one number."
        )
        String password,
        @NotBlank(message = "Email must be included.")
        @Email(message = "All emails must end for example : @yourmailprovider.com.")
        String email,
        @NotBlank(message = "Firstname must be included.")
        @Pattern(
                regexp = "^[a-zA-ZåäöÅÄÖ\\s-]{2,25}$",
                message = "Must have between 2 and 25 characters.")
        String firstName,
        @NotBlank(message = "Lastname must be included.")
        @Pattern(
                regexp = "^[a-zA-ZåäöÅÄÖ\\s-]{2,25}$",
                message = "Must have between 2 and 25 characters.")
        String lastName,
        LocalDate birth,
        @NotBlank(message = "Address must be included.")
        @Pattern(
                regexp = "^[a-zA-ZåäöÅÄÖ\\s-]{4,25}$",
                message = "Must have between 4 and 25 characters.")
        String address,
        @NotBlank(message = "City must be included.")
        @Pattern(
                regexp = "^[a-zA-ZåäöÅÄÖ\\s-]{2,25}$",
                message = "Must have between 2 and 25 characters.")
        String city
) {
}
