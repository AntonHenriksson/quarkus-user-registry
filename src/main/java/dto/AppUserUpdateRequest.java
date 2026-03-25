package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record AppUserUpdateRequest(
        @Pattern(
                regexp = "^[a-zA-Z0-9]{4,20}$",
                message = "Must have between 4 and 20 characters.")
        String userName,
        @Email(message = "All emails must end for example : @yourmailprovider.com.")
        String email,
        @Pattern(
                regexp = "^[a-zA-ZåäöÅÄÖ\\s-]{2,25}$",
                message = "Must have between 2 and 25 characters.")
        String firstName,
        @Pattern(
                regexp = "^[a-zA-ZåäöÅÄÖ\\s-]{2,25}$",
                message = "Must have between 2 and 25 characters.")
        String lastName,
        LocalDate birth,
        @Pattern(
                regexp = "^[a-zA-ZåäöÅÄÖ\\s-]{4,25}$",
                message = "Must have between 4 and 25 characters.")
        String address,
        @Pattern(
                regexp = "^[a-zA-ZåäöÅÄÖ\\s-]{2,25}$",
                message = "Must have between 2 and 25 characters.")
        String city) {
}
