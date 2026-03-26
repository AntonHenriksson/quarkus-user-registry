package mapper;

import domain.AppUser;
import dto.appuser.AppUserRequest;
import dto.appuser.AppUserResponse;
import dto.appuser.AppUserSummaryResponse;
import dto.appuser.AppUserUpdateRequest;

import java.time.LocalDate;

public class AppUserMapper {


    private static String clean(String value) {
        return (value == null) ? null : value.trim().toLowerCase();
    }

    public static AppUser toEntity(AppUserRequest request) {
        AppUser appUser = new AppUser();
        updatingFields(appUser,
                request.userName(),
                request.email(),
                request.firstName(),
                request.lastName(),
                request.address(),
                request.city(),
                request.birth());
        return appUser;
    }

    public static void updateEntity(AppUser appUser, AppUserUpdateRequest request) {
        updatingFields(appUser,
                request.userName(),
                request.email(),
                request.firstName(),
                request.lastName(),
                request.address(),
                request.city(),
                request.birth());
    }

    private static void updatingFields(AppUser appUser,
                                       String userName,
                                       String email,
                                       String firstName,
                                       String lastName,
                                       String address,
                                       String city,
                                       LocalDate birth) {
        if (userName != null) appUser.userName = clean(userName);
        if (email != null) appUser.email = clean(email);
        if (firstName != null) appUser.firstName = clean(firstName);
        if (lastName != null) appUser.lastName = clean(lastName);
        if (address != null) appUser.address = clean(address);
        if (city != null) appUser.city = clean(city);

        if (birth != null) {
            appUser.birth = birth;
        }
    }

    public static AppUserResponse fromEntity(AppUser appUser) {
        return new AppUserResponse(
                appUser.id,
                appUser.userName,
                appUser.email,
                appUser.firstName,
                appUser.lastName,
                appUser.birth,
                appUser.address,
                appUser.city,
                appUser.createdAt
        );
    }

    public static AppUserSummaryResponse fromEntitySummary(AppUser appUser) {
        return new AppUserSummaryResponse(
                appUser.id,
                appUser.userName,
                appUser.email,
                appUser.firstName
        );
    }
}